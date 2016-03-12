package maze.logic;

public class MazeBuilder implements IMazeBuilder {
	private char defaultMaze[][] = {
			{'X','X','X','X','X','X','X','X','X','X'},
			{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'},
			{'X',' ','X','X',' ','X',' ','X',' ','X'},
			{'X',' ','X','X',' ','X',' ','X',' ','X'},
			{'X',' ','X','X',' ','X',' ','X',' ','X'},
			{'X',' ',' ',' ',' ',' ',' ','X',' ','S'},
			{'X',' ','X','X',' ','X',' ','X',' ','X'},
			{'X',' ','X','X',' ','X',' ','X',' ','X'},
			{'X',' ','X','X',' ',' ',' ',' ',' ','X'},
			{'X','X','X','X','X','X','X','X','X','X'}
	};
	private char maze[][];

	public MazeBuilder(){
		maze = defaultMaze;
	}

	public MazeBuilder(int n){
		maze = new char[n][n];
	}
	
	public MazeBuilder(char[][] m){
		maze = m;
	}

	public char getMaze(int x, int y) {
		return maze[x][y];
	}
	
	public void setMaze(int x, int y, char val) {
		maze[x][y] = val;
	}
	
	public void setMaze(Point p, char val) {
		maze[p.x][p.y] = val;
	}

	public void printMaze(){
		for (int i = 0; i < maze.length; i++){
			for (int j = 0; j < maze[i].length; j++){
				System.out.print(maze[i][j] + " ");
			}
			System.out.println();
		}
	}

	public void printHero(Hero h) {
		if (h.isArmed()){
			this.setMaze(h.x, h.y, 'A');
		}
		else {
			this.setMaze(h.x, h.y, 'H');
		}

	}

	public void printDragon(Dragon d){
		if (d.isSleeping()) {
			this.setMaze(d.x, d.y, 'd');
		}
		else {
			this.setMaze(d.x, d.y, 'D');
		}
	}

	public void printSword(Sword s){
		if (!s.isCollected()){
			this.setMaze(s.getX(), s.getY(), 'E');
		}
	}

	@Override
	public char[][] buildMaze(int size) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
}