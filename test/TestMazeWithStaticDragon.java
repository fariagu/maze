import logic.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMazeWithStaticDragon {

	//	public boolean assertEquals(Point p1, Point p2) {
	//		return ((p1.x==p2.x) && (p1.y==p2.y));
	//	}
	//	
	//	public boolean assertEquals(Boolean boo1, Boolean boo2) {
	//		return (boo1.equals(boo2));
	//	}
	//
	//	public boolean assertEquals(Object obj1, Object obj2) {
	//		return (obj1 == obj2);
	//	}

	char [][] m1 = {
			{'X', 'X', 'X', 'X', 'X'},
			{'X', ' ', ' ', ' ', 'S'},
			{'X', ' ', ' ', ' ', 'X'},
			{'X', ' ', ' ', ' ', 'X'},
			{'X', 'X', 'X', 'X', 'X'}
	};
	char [][] m2 = {
			{'X', 'X', 'X', 'X'},
			{'X', 'H', 'E', 'S'},
			{'X', ' ', 'D', 'X'},
			{'X', 'X', 'X', 'X'}
	};
	char [][] m3 = {
			{'X', 'X', 'X', 'X'},
			{'X', ' ', 'D', 'S'},
			{'X', 'H', ' ', 'X'},
			{'X', 'X', 'X', 'X'}
	};
	char [][] m4 = {
			{'X', 'X', 'X', 'X'},
			{'X', 'D', ' ', 'S'},
			{'X', ' ', 'H', 'X'},
			{'X', 'X', 'X', 'X'}
	};

	char[][] m5 = {
			{'X', 'S', 'X'},
			{'S', ' ', 'S'},
			{'X', 'S', 'x'}
	};

	char[][] m6 = {
			{'X', 'X', 'X'},
			{'X', ' ', 'X'},
			{'X', 'X', 'x'}
	};

	@Test
	public void testMoveHeroToFreeCell() {
		MazeBuilder maze = new MazeBuilder(m1);
		Hero h = new Hero(2, 2, maze);
		Point p1 = new Point(2, 2);

		assertEquals(p1.getX(), h.getX());
		assertEquals(p1.getY(), h.getY());
		//assertEquals(new Point(1, 3), h.getPos());

		h.setDir(0);//up
		h.move(maze);
		Point p2 = new Point(1, 2);
		assertEquals(p2.getX(), h.getX());
		assertEquals(p2.getY(), h.getY());		
		//assertEquals(new Point(1, 1), h.getPos());
	}

	@Test
	//herói tenta mover-se sem sucesso contra uma parede
	public void testWall() {
		MazeBuilder maze = new MazeBuilder(m6);
		Hero h 	= new Hero(1, 1, maze);
		int x = h.getX();
		int y = h.getY();

		h.setDir(0);//up
		h.move(maze);		
		assertEquals(h.getX(), x);
		assertEquals(h.getY(), y);

		h.setDir(1);//down
		h.move(maze);		
		assertEquals(h.getX(), x);
		assertEquals(h.getY(), y);

		h.setDir(2);//left
		h.move(maze);		
		assertEquals(h.getX(), x);
		assertEquals(h.getY(), y);

		h.setDir(3);//right
		h.move(maze);		
		assertEquals(h.getX(), x);
		assertEquals(h.getY(), y);

	}

	@Test
	//herói move-se para a posição da espada e apanha a espada
	public void testSword() {
		MazeBuilder maze = new MazeBuilder(m1);
		Hero h = new Hero(2, 2, maze);
		Sword s1 = new Sword(1, 2, maze);
		Sword s2 = new Sword(3, 2, maze);
		Sword s3 = new Sword(2, 1, maze);
		Sword s4 = new Sword(2, 3, maze);

		//picks up top sword
		h.setDir(0);//up
		h.move(maze);
		s1.heroOverlap(h, maze);
		assertEquals(true, s1.isCollected());

		//picks up bottom sword
		h.setDir(1);//down
		h.move(maze);
		h.move(maze);
		s1.heroOverlap(h, maze);
		assertEquals(true, s1.isCollected());

		//picks up left sword
		h.setDir(0);//left
		h.move(maze);
		h.setDir(2);
		h.move(maze);
		s1.heroOverlap(h, maze);
		assertEquals(true, s1.isCollected());

		//picks up right sword
		h.setDir(3);//right
		h.move(maze);
		h.move(maze);
		s1.heroOverlap(h, maze);
		assertEquals(true, s1.isCollected());
	}

	@Test
	public void testHeroDies() {
		MazeBuilder maze = new MazeBuilder(m1);
		Hero h = new Hero(1, 3, maze);
		Dragon d = new Dragon(3, 3, maze);

		d.fight(h, maze);
		assertEquals(true, h.isAlive());

		h.setDir(1);//go down
		h.move(maze);
		d.fight(h, maze);

		assertEquals(false, h.isAlive());

		MazeBuilder maze1 = new MazeBuilder(m2);
		Hero h1 = new Hero(1, 1, maze1);
		Dragon d1 = new Dragon(2, 2, maze1);

		h1.setDir(1);//down
		h1.move(maze1);
		d1.fight(h1, maze1);
		assertEquals(false, h1.isAlive());
		assertEquals(true, d1.isAlive());

		MazeBuilder maze2 = new MazeBuilder(m4);
		Hero h2 = new Hero(2, 2, maze2);
		Dragon d2 = new Dragon(1, 1, maze2);

		h2.setDir(0);//up
		h2.move(maze2);
		d2.fight(h2, maze2);
		assertEquals(false, h2.isAlive());
		assertEquals(true, d2.isAlive());

		MazeBuilder maze3 = new MazeBuilder(m4);
		Hero h3 = new Hero(2, 2, maze3);
		Dragon d3 = new Dragon(1, 1, maze3);

		h3.setDir(2);//left
		h3.move(maze3);
		d3.fight(h3, maze3);
		assertEquals(false, h3.isAlive());
		assertEquals(true, d3.isAlive());
	}

	@Test
	//heroi armado move-se para uma posicao adjacente ao dragao e mata-o
	public void testKill() {
		MazeBuilder maze = new MazeBuilder(m1);
		Hero h = new Hero(1, 3, maze);
		Sword s = new Sword(3, 1, maze);
		Dragon d = new Dragon(3, 3, maze);

		h.setDir(2);//left
		h.move(maze);
		h.move(maze);
		h.setDir(1);//down
		h.move(maze);
		h.move(maze);
		h.setDir(3);//right
		h.move(maze);
		d.fight(h, maze);
		assertEquals(true, h.isAlive());
		assertEquals(false, d.isAlive());		
	}

	@Test
	//heroi move-se para a saida apos apanhar a espada e matar o dragao (vitoria)
	public void testExit() {
		MazeBuilder maze = new MazeBuilder(m5);
		Hero h = new Hero(1, 1, maze);

		//armed = false && mapCleared = false

		h.setDir(0);
		h.move(maze);
		assertEquals(false, h.isFinished());

		h.setDir(1);
		h.move(maze);
		assertEquals(false, h.isFinished());

		h.setDir(1);
		h.move(maze);
		assertEquals(false, h.isFinished());

		h.setDir(1);
		h.move(maze);
		assertEquals(false, h.isFinished());

		//armed = true && mapCleared = false
		h.setArmed(true);

		h.setDir(0);
		h.move(maze);
		assertEquals(false, h.isFinished());

		h.setDir(1);
		h.move(maze);
		assertEquals(false, h.isFinished());

		h.setDir(1);
		h.move(maze);
		assertEquals(false, h.isFinished());

		h.setDir(1);
		h.move(maze);
		assertEquals(false, h.isFinished());

		//armed = false && mapCleared = true
		h.setArmed(false);
		h.setMapCleared(true);

		h.setDir(0);
		h.move(maze);
		assertEquals(false, h.isFinished());

		h.setDir(1);
		h.move(maze);
		assertEquals(false, h.isFinished());

		h.setDir(1);
		h.move(maze);
		assertEquals(false, h.isFinished());

		h.setDir(1);
		h.move(maze);
		assertEquals(false, h.isFinished());

		//armed = true && mapCleared = true
		h.setArmed(true);

		h.setDir(0);
		h.move(maze);
		assertEquals(true, h.isFinished());

		h.setFinished(false);
		h.setDir(1);
		h.move(maze);
		h.move(maze);
		assertEquals(true, h.isFinished());

		h.setFinished(false);
		h.setDir(0);
		h.move(maze);
		h.setDir(2);
		h.move(maze);
		assertEquals(true, h.isFinished());

		h.setFinished(false);
		h.setDir(3);
		h.move(maze);
		h.move(maze);
		assertEquals(true, h.isFinished());
	}

	@Test
	//heroi tenta mover-se sem sucesso para a saida armado mas sem ter morto o dragao
	public void testSwordFailExit() {
		MazeBuilder maze = new MazeBuilder();
		Hero h = new Hero(1, 3, maze);
		Sword s = new Sword(3, 1, maze);
		Dragon d = new Dragon(3, 3, maze);

		h.setDir(2);//left
		h.move(maze);
		h.move(maze);
		h.setDir(1);//down
		h.move(maze);
		h.move(maze);
		//System.out.print(h.isArmed());
		//System.out.print(maze.getMaze(1,2));
		assertEquals(true, h.isArmed());
		s.heroOverlap(h, maze);
		assertEquals(true, s.isCollected());
		h.setDir(0);//up
		h.move(maze);
		h.move(maze);
		h.setDir(3);//right
		h.move(maze);
		h.move(maze);
		h.move(maze);
		assertEquals(false, h.isMapCleared());
		assertEquals(false, h.isFinished());
		assertEquals(true, d.isAlive());
	}

}