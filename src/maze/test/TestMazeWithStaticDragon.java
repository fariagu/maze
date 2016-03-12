package maze.test;

import static org.junit.Assert.*;
import org.junit.Test;
import maze.logic.*;

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
			{'X', ' ', ' ', 'H', 'S'},
			{'X', ' ', 'X', ' ', 'X'},
			{'X', 'E', ' ', 'D', 'X'},
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

	@Test
	public void testMoveHeroToFreeCell() {
		MazeBuilder maze = new MazeBuilder(m1);
		Hero h = new Hero(1, 3, maze);
		Point p1 = new Point(1, 3);

		assertEquals(p1.getX(), h.getX());
		assertEquals(p1.getY(), h.getY());
		//assertEquals(new Point(1, 3), h.getPos());

		h.setDir(2);//left
		h.move(maze);
		Point p2 = new Point(1, 2);
		assertEquals(p2.getX(), h.getX());
		assertEquals(p2.getY(), h.getY());		
		//assertEquals(new Point(1, 1), h.getPos());
	}

	@Test
	//herói tenta mover-se sem sucesso contra uma parede
	public void testWall() {
		MazeBuilder maze = new MazeBuilder(m1);
		Hero h 	= new Hero(1, 1, maze);
		Hero h1 = new Hero(1, 1, maze);
		h1.setX(h.getX());
		h1.setY(h.getY());

		h.setDir(0);//up
		h.move(maze);		
		assertEquals(h1.getX(), h.getX());
		assertEquals(h1.getY(), h.getY());
		//assertEquals(h, h1);
	}

	@Test
	//herói move-se para a posição da espada e apanha a espada
	public void testSword() {
		MazeBuilder maze = new MazeBuilder();
		Hero h = new Hero(1, 3, maze);
		Sword s = new Sword(3, 1, maze);

		h.setDir(2);//left
		h.move(maze);
		h.move(maze);
		h.setDir(1);//down
		h.move(maze);
		h.move(maze);
//		System.out.print(h.isArmed());
//		System.out.print(maze.getMaze(1,2));
		assertEquals(true, h.isArmed());
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
	//herói armado move-se para uma posição adjacente ao dragão e mata-o
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
	//herói move-se para a saída após apanhar a espada e matar o dragão (vitória)
	public void testWin() {
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
		h.move(maze);
		h.setDir(0);//up
		h.move(maze);
		h.move(maze);
		h.setDir(3);//right
		h.move(maze);
		assertEquals(true, h.isMapCleared());
		assertEquals(true, h.isFinished());
	}

	@Test
	//herói tenta mover-se sem sucesso para a saída sem ter apanhado a espada
	public void testFailExit() {
		MazeBuilder maze = new MazeBuilder(m1);
		Hero h = new Hero(1, 3, maze);

		h.setDir(3);//right
		h.move(maze);
//		System.out.print(h.isArmed());
//		System.out.print(h.isMapCleared());
//		System.out.print(h.isFinished());
//		System.out.print(h.x);
//		System.out.print(h.y);
		assertEquals(false, h.isMapCleared());
		assertEquals(false, h.isFinished());
	}
	
	@Test
	//herói tenta mover-se sem sucesso para a saída armado mas sem ter morto o dragão
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
//		System.out.print(h.isArmed());
//		System.out.print(maze.getMaze(1,2));
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