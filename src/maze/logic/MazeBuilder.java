package maze.logic;

import java.util.Random;
import java.util.Stack;

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
	private int size;

	public MazeBuilder(){
		maze = defaultMaze;
	}

	public MazeBuilder(int s){
		this.size = s;
		maze = randomMaze();
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

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void printMaze(){
		for (int i = 0; i < maze.length; i++){
			for (int j = 0; j < maze[i].length; j++){
				System.out.print(maze[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public String printMaze2String(){
		String string="";
		for (int i = 0; i < maze.length; i++){
			for (int j = 0; j < maze[i].length; j++){
				string +=  (maze[i][j] + " ");
			}
			string += "\n";
		}
		return string;
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

	public boolean check(char[][] m) {
		for (int i = 0; i < m.length; i++)
			for (int j = 0; j < m.length; j++) {
				if (m[i][j] != '+')
					return false;
			}
		return true;
	}

	public char[][] randomMaze() {

		char[][] tmp = new char[size][size];
		int vcsize = (size-1) / 2;
		char[][] visitedCells = new char[vcsize][vcsize];
		int pointMX = 0, pointMY = 0;
		int vcx = vcsize - 1;
		int vcy = 0;
		int mazeX = size - 2;
		int mazeY = 1;
		int xExit = 0;
		int yExit = 0;
		Stack<Integer> stackMov = new Stack<Integer>();

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if ((i % 2 != 0) && (j % 2 != 0))
					tmp[i][j] = ' ';
				else
					tmp[i][j] = 'X';
			}
		}
		//creating exit

		Random r = new Random();
		int num = r.nextInt(2);
		if (num == 0) { // horizontal
			xExit = r.nextInt(2) * size;
			yExit = r.nextInt(size - 2) + 1;
			if (xExit == 0) {
				pointMX = 1;

			} else
				pointMX = size - 2;
			pointMY = yExit;
		}
		if (num == 1) { // vertical
			xExit = r.nextInt(size - 2) + 1;
			yExit = r.nextInt(2) * size;
			if (yExit == 0) {
				pointMY = 1;
			} else
				pointMY = size - 2;
			pointMX = xExit;
		}

		if (xExit == size){
			xExit--;
		}
		if (yExit == size){
			yExit--;
		}
		tmp[yExit][xExit] = 'S';
		tmp[pointMY][pointMX] = ' ';


		for (int i = 0; i < visitedCells.length; i++)
			for (int j = 0; j < visitedCells.length; j++)
				visitedCells[i][j] = '.';
		visitedCells[vcy][vcx] = '+';
		boolean flag1 = false;
		boolean flag2 = false;
		boolean nextTo = false;

		while (!flag2) {
			while (!flag1) {
				Random rand = new Random();
				int mov = rand.nextInt(4);
				switch (mov) {
				case 0://up
					if (vcy < 1) {
						flag1 = false;
					} else if (visitedCells[vcy - 1][vcx] == '+') {
						flag1 = false;
					} else {
						vcy--;
						visitedCells[vcy][vcx] = '+';
						tmp[mazeY-1][mazeX] = ' ';
						tmp[mazeY-2][mazeX] = ' ';
						mazeY -= 2;
						flag1 = true;
						stackMov.push(0);
					}
					break;
				case 1://down
					if (vcy > (vcsize - 2)) {
						flag1 = false;
					} else if (visitedCells[vcy + 1][vcx] == '+') {
						flag1 = false;
					} else {
						vcy++;
						visitedCells[vcy][vcx] = '+';
						tmp[mazeY][mazeX] = ' ';
						tmp[mazeY + 1][mazeX] = ' ';
						tmp[mazeY + 2][mazeX] = ' ';
						//mazeY++;
						mazeY += 2;
						flag1 = true;
						stackMov.push(1);
					}
					break;
				case 2://left
					if (vcx < 1) {
						flag1 = false;
					} else if (visitedCells[vcy][vcx - 1] == '+') {
						flag1 = false;
					} else {
						vcx--;
						visitedCells[vcy][vcx] = '+';
						tmp[mazeY][mazeX] = ' ';
						tmp[mazeY][mazeX - 1] = ' ';
						tmp[mazeY][mazeX - 2] = ' ';
						//mazeX--;
						mazeX -= 2;
						flag1 = true;
						stackMov.push(2);
					}
					break;
				case 3://right
					if (vcx > (vcsize - 2)) {
						flag1 = false;
					} else if (visitedCells[vcy][vcx + 1] == '+') {
						flag1 = false;
					} else {
						vcx++;
						visitedCells[vcy][vcx] = '+';
						tmp[mazeY][mazeX] = ' ';
						tmp[mazeY][mazeX + 1] = ' ';
						tmp[mazeY][mazeX + 2] = ' ';
						//mazeX++;
						mazeX +=2;
						flag1 = true;
						stackMov.push(3);
					}
					break;
				}

			}
			flag1 = false;
			if (!check(visitedCells)){

				while (!nextTo) {
					switch (0) {
					case 0://up
						if (vcy >= 1) {
							if (visitedCells[vcy - 1][vcx] == '+') {

							} else {
								nextTo = true;
								break;
							} 
						}
					case 1://down
						if (vcy < (vcsize-1)) {
							if (visitedCells[vcy + 1][vcx] == '+') {

							} else {
								nextTo = true;
								break;
							}
						}
					case 2://left
						if (vcx >= 1) {
							if (visitedCells[vcy][vcx - 1] == '+') {

							} else {
								nextTo = true;
								break;
							}
						}
					case 3://right
						if (vcx < (vcsize-1)) {
							if (visitedCells[vcy][vcx + 1] == '+') {

							} else {
								nextTo = true;
								break;
							}
						}
					}

					if (!nextTo){

						int k = stackMov.pop();
						switch (k) {
						case 0://go up
							vcy++;
							mazeY += 2;
							break;
						case 1://go down
							vcy--;
							mazeY -= 2;
							break;
						case 2://go left
							vcx++;
							mazeX += 2;
							break;
						case 3://go right
							vcx--;
							mazeX -= 2;
							break;
						}

					}
				}
				nextTo = false;
			}
			else{
				flag2 = true;
			}

		}
		return tmp;
	}



	@Override
	public char[][] buildMaze(int size) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
}