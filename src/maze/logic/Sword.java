package maze.logic;

import java.util.Random;

public class Sword extends Point{	
	private boolean collected;
/*
	public Sword(MazeBuilder m) {
		x = 8;
		y = 1;
		collected = false;
		m.printSword(this);
	}
*/	
	public Sword(int x, int y, MazeBuilder m) {
		this.x = x;
		this.y = y;
		collected = false;
		m.printSword(this);
	}
	
	public Sword(MazeBuilder m){
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
		m.printSword(this);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public boolean isCollected() {
		return collected;
	}
	public void setCollected(boolean collected) {
		this.collected = collected;
	}

/*	public void print() {
		if (!this.collected){
			Maze.setMaze(x, y, 'E');
		}	
	}
*/	
	public void dragonOverlap(Dragon d, MazeBuilder m) {
		if (this.x == d.x && this.y == d.y) {
			m.setMaze(this.x, this.y, 'F');
		}
		else if (!this.isCollected()){
			m.setMaze(this.x, this.y, 'E');
		}
	}
	
	public void heroOverlap(Hero h, MazeBuilder m) {
		if (this.x == h.x && this.y == h.y) {
			this.setCollected(true);
			h.setArmed(true);
		}
	}
}