package maze.logic;

import java.util.ArrayList;
import java.util.Random;

/**  
 * Hero.java - Classe do Hero.  
 * @author Diogo Cruz
 * @author Gustavo Faria
 */
public class Hero extends Point{
	private boolean armed=false;
	private boolean alive=true;
	private boolean mapCleared=false;
	private boolean finished=false;
	private int dir;

	public Hero(int x, int y, MazeBuilder m) {
		this.x = x;
		this.y = y;
		m.printHero(this);
	}

	public Hero(MazeBuilder m){
		int size = m.getSize();
		int x, y;
		Random r = new Random();

		while (true){
			x = r.nextInt(size -1) + 1;
			y = r.nextInt(size -1) + 1;

			if (m.getMaze(x, y) == ' '){
				this.x = x;
				this.y = y;
				break;
			}
		}

		m.printHero(this);		
	}

	public Hero(char[][] m){
		int size = m.length;
		int x, y;
		Random r = new Random();

		while (true){
			x = r.nextInt(size -1) + 1;
			y = r.nextInt(size -1) + 1;

			if (m[x][y] == ' '){
				this.x = x;
				this.y = y;
				break;
			}
		}

		m[x][y]	= 'H';	
	}

	public boolean isArmed() {
		return armed;
	}
	public void setArmed(boolean armed) {
		this.armed = armed;
	}
	public boolean isAlive() {
		return alive;
	}
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	public boolean isMapCleared() {
		return mapCleared;
	}
	public void setMapCleared(boolean mapCleared) {
		this.mapCleared = mapCleared;
	}
	public boolean isFinished() {
		return finished;
	}
	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	public void print(MazeBuilder m) {
		if (this.armed)
			m.setMaze(this, 'A');
		else
			m.setMaze(x, y, 'H');
	}

	public void checkMapCleared(){
		ArrayList<Dragon> d = Dragon.getDragons();

		for (int i = 0; i < d.size(); i++){
			if(d.get(i).isAlive()){
				return;
			}
		}
		this.mapCleared = true;
	}

	public void move(MazeBuilder m) {//0=up, 1=down, 2=left, 3=right
		switch(dir) {
		case 0://up
			if (m.getMaze(x-1, y) == 'E'){
				this.armed = true;
				m.setMaze(x-1, y, ' ');
			}
			if (m.getMaze(x-1, y) == ' ') {
				m.setMaze(x, y, ' ');
				x--;
				print(m);
			}
			else if (m.getMaze(x-1, y) == 'S' && this.armed && this.mapCleared){
				m.setMaze(x, y, ' ');
				x--;
				print(m);
				this.setFinished(true);
			}
			break;
		case 1://down
			if (m.getMaze(x+1, y) == 'E'){
				this.armed = true;
				m.setMaze(x+1, y, ' ');
			}
			if (m.getMaze(x+1, y) == ' ') {
				m.setMaze(x, y, ' ');
				x++;
				m.printHero(this);
			}
			else if (m.getMaze(x+1, y) == 'S' && this.armed && this.mapCleared){
				m.setMaze(x, y, ' ');
				x++;
				m.printHero(this);
				this.setFinished(true);
			}
			break;
		case 2://left
			if (m.getMaze(x, y-1) == 'E'){
				this.armed = true;
				m.setMaze(x, y-1, ' ');
			}
			if (m.getMaze(x, y-1) == ' ') {
				m.setMaze(x, y, ' ');
				y--;
				m.printHero(this);	
			}
			else if (m.getMaze(x, y-1) == 'S' && this.armed && this.mapCleared){
				m.setMaze(x, y, ' ');
				y--;
				m.printHero(this);
				this.setFinished(true);
			}
			break;
		case 3://right
			if (m.getMaze(x, y+1) == 'E'){
				this.armed = true;
				m.setMaze(x, y+1, ' ');
			}
			if (m.getMaze(x, y+1) == ' ') {
				m.setMaze(x, y, ' ');
				y++;
				m.printHero(this);
			}
			else if (m.getMaze(x, y+1) == 'S' && this.armed && this.mapCleared){
				m.setMaze(x, y, ' ');
				y++;
				m.printHero(this);
				this.setFinished(true);
			}
			break;
		default:
			break;
		}
	}
}