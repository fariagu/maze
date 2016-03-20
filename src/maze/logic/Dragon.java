package maze.logic;

import java.util.Random;
import java.util.ArrayList;

public class Dragon extends Point{
	private boolean alive;
	private boolean sleeping;
	private int moveCounter;
	private int fallAsleepCounter;
	private int sleepCounter;
	private int wakeUpCounter;
	static private ArrayList<Dragon> dragons;
	
/*
	public Dragon(MazeBuilder m) {
		x = 3;
		y = 1;
		alive = true;
		sleeping = false;
		moveCounter = 0;
		fallAsleepCounter = -1;
		sleepCounter = -1;
		wakeUpCounter = -1;
		m.printDragon(this);
	}*/

	public Dragon(int x, int y, MazeBuilder m) {
		this.x = x;
		this.y = y;
		alive = true;
		m.printDragon(this);
	}
	
	public Dragon(MazeBuilder m){
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
		
		alive = true;
		m.printDragon(this);
		
		
		
	}
	
	public Dragon(){
		dragons = new ArrayList<Dragon>();
	}
	
	public void multipleDragons(int n, MazeBuilder m){
		for (int i = 0; i < n; i++){
			dragons.add(new Dragon(m));
		}
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public boolean isSleeping() {
		return sleeping;
	}
	
	public static ArrayList<Dragon> getDragons() {
		return dragons;
	}

	public void setDragons(ArrayList<Dragon> dragons) {
		this.dragons = dragons;
	}

	/*
	public void setSleeping(boolean sleeping) {
		this.sleeping = sleeping;
	}

	public int getMoveCounter() {
		return this.moveCounter;
	}

	public void setMoveCounter(int moveCounter) {
		this.moveCounter = moveCounter;
	}

	public int getFallAsleepCounter() {
		return fallAsleepCounter;
	}

	public void setFallAsleepCounter(int fallAsleepCounter) {
		this.fallAsleepCounter = fallAsleepCounter;
	}

	public int getSleepCounter() {
		return sleepCounter;
	}

	public void setSleepCounter(int sleepCounter) {
		this.sleepCounter = sleepCounter;
	}

	public int getWakeUpCounter() {
		return wakeUpCounter;
	}

	public void setWakeUpCounter(int wakeUpCounter) {
		this.wakeUpCounter = wakeUpCounter;
	}
*/
	/*	public void print() {
		if (alive){
			if (!sleeping){
				Maze.setMaze(x, y, 'D');
			}
			else {
				Maze.setMaze(x, y, 'd');
			}

		}
	}
	 */
	public void fight(Hero h, MazeBuilder m){
		if (	((this.x == h.x)&&(this.y == (h.y + 1))) ||
				((this.x == h.x)&&(this.y == (h.y - 1))) ||
				((this.y == h.y)&&(this.x == (h.x + 1))) ||
				((this.y == h.y)&&(this.x == (h.x - 1))) ||
				((this.y == h.y)&&(this.x == h.x))) {
			if (h.isArmed()){
				this.alive = false;
				h.checkMapCleared();
				m.setMaze(this.x, this.y, ' ');
			}
			else if (sleeping==false){
				h.setAlive(false);
			}
		}
	}

	public void move(MazeBuilder m){
		Random r = new Random();
		int dir = 4;
		boolean canContinue = false;

		while(canContinue==false) {
			dir = r.nextInt(5);
			//System.out.println(dir);

			switch(dir) {
			case 0:
				if (m.getMaze(this.x-1, this.y) != 'X' && m.getMaze(this.x-1, this.y) != 'S'){
					canContinue = true;
				}
				break;
			case 1:
				if (m.getMaze(this.x+1, this.y) != 'X' && m.getMaze(this.x+1, this.y) != 'S'){
					canContinue = true;
				}
				break;
			case 2:
				if (m.getMaze(this.x, this.y-1) != 'X' && m.getMaze(this.x, this.y-1) != 'S'){
					canContinue = true;
				}
				break;
			case 3:
				if (m.getMaze(this.x, this.y+1) != 'X' && m.getMaze(this.x, this.y+1) != 'S'){
					canContinue = true;
				}
				break;
			default:
				canContinue = true;
			}
		}

		switch(dir) {
		case 0:
			if (m.getMaze(x-1, y) == ' ' || m.getMaze(x-1, y) == 'E') {
				m.setMaze(x, y, ' ');
				x--;
				m.printDragon(this);
			}
			break;
		case 1:
			if (m.getMaze(x+1, y) == ' ' || m.getMaze(x+1, y) == 'E') {
				m.setMaze(x, y, ' ');
				x++;
				m.printDragon(this);
			}
			break;
		case 2:
			if (m.getMaze(x, y-1) == ' ' || m.getMaze(x, y-1) == 'E') {
				m.setMaze(x, y, ' ');
				y--;
				m.printDragon(this);
			}
			break;
		case 3:
			if (m.getMaze(x, y+1) == ' ' || m.getMaze(x, y+1) == 'E') {
				m.setMaze(x, y, ' ');
				y++;
				m.printDragon(this);
			}
			break;
		default:
			break;

		}
	}

	public void resetMoveCounter(){
		moveCounter = -1;
	}

	public void resetFallAsleepCounter(){
		fallAsleepCounter = -1;
	}

	public void resetSleepCounter(){
		sleepCounter = -1;
	}

	public void resetWakeUpCounter(){
		wakeUpCounter = -1;
	}

	public void moveOrSleep(MazeBuilder m){
		Random r = new Random();

		if (moveCounter >= 0){
			if(moveCounter < 5){
				move(m);
				moveCounter++;
			}
			else {//moveCounter == 5
				fallAsleepCounter = r.nextInt(3);
				resetMoveCounter();
			}
		}

		if (fallAsleepCounter >= 0){
			if (fallAsleepCounter == 0){
				sleeping = true;
				resetFallAsleepCounter();
				sleepCounter = 0;
				m.printDragon(this);
			}
			else {
				move(m);
				fallAsleepCounter--;
			}
		}

		if (sleepCounter >= 0){
			if (sleepCounter < 2){
				sleepCounter++;
			}
			else{//sleepCounter == 2
				wakeUpCounter = r.nextInt(3);
				resetSleepCounter();
			}
		}

		if (wakeUpCounter >= 0){
			if (wakeUpCounter == 0){
				sleeping = false;
				resetWakeUpCounter();
				moveCounter = 0;
				m.printDragon(this);
			}
			else {
				wakeUpCounter--;
			}
		}
	}
}