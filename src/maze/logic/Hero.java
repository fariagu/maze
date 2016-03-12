package maze.logic;

import maze.logic.MazeBuilder;

public class Hero extends Point{
	private boolean armed=false;
	private boolean alive=true;
	private boolean mapCleared=false;
	private boolean finished=false;
	private int dir;

	public Hero(MazeBuilder m) {
		this.x = 1;
		this.y = 1;
		m.printHero(this);
	}

	public Hero(int x, int y, MazeBuilder m) {
		this.x = x;
		this.y = y;
		m.printHero(this);
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

	public int getDir() {
		return dir;
	}
	public void setDir(int dir) {
		this.dir = dir;
	}

	public void print(MazeBuilder m) {
		if (this.armed)
			//m.setMaze(x, y, 'A');
		m.setMaze(this, 'A');
		else
			m.setMaze(x, y, 'H');
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
				finished = true;
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
				finished = true;
			}
			break;
		case 2://left
			if (m.getMaze(x, y-1) == 'E'){
				this.armed = true;
				m.setMaze(x, y-1, ' ');
				this.y--;
			}
			else if (m.getMaze(x, y-1) == ' ') {
				m.setMaze(x, y, ' ');
				this.y--;
				m.printHero(this);	
			}
			else if (m.getMaze(x, y-1) == 'S' && this.armed && this.mapCleared){
				m.setMaze(x, y, ' ');
				this.y--;
				m.printHero(this);
				finished = true;
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
				finished = true;
			}
			break;
		default:
			break;
		}
	}


}