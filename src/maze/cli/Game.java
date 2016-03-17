package maze.cli;

import java.util.Scanner;
import maze.logic.*;

public class Game {
	private static boolean finished = false;

	public boolean isFinished() {
		return finished;
	}

	public static void setFinished(boolean finished) {
		Game.finished = finished;
	}

	public static int dirToInt(String dir) {
		switch (dir) {
		case "w":
			return 0;
		case "s":
			return 1;
		case "a":
			return 2;
		case "d":
			return 3;
		default:
			return -1;
		}
	}

	public static void main(String args[]){
		Scanner input = new Scanner(System.in);
		String gameMode;
		int mode = 0;
		boolean gameSet = false;
		
		System.out.println("Choose the Game Mode:");
		System.out.println("1 - Dragon doesn't move;");
		System.out.println("2 - Dragon moves randomly;");
		System.out.println("3 - Dragon move and sleeps randomly.");
		
		while (!gameSet) {
			gameMode = input.nextLine();

			if (gameMode.equals("1")) {
				mode = 1;
				gameSet = true;
			}
			else if (gameMode.equals("2")){
				mode = 2;
				gameSet = true;
			}
			else if (gameMode.equals("3")){
				mode = 3;
				gameSet = true;
			}
			else {
				System.out.println("Try again. Press 1, 2 or 3 and then Enter.");
			}
		}
		System.out.println("...");

		MazeBuilder maze = new MazeBuilder(7);//must be an odd number TODO exception handling
		Hero h = new Hero(maze);
		Dragon d = new Dragon(maze);
		Sword s = new Sword(maze);
		String dir;
		
		while (finished == false){
			maze.printMaze();
			dir = input.nextLine();
			h.setDir(dirToInt(dir));
			h.move(maze);
			if (!s.isCollected()){
				s.heroOverlap(h, maze);
			}
			if (d.isAlive()) {
				d.fight(h, maze);
				
				if (mode == 2 || mode == 3){
					if (mode == 2){
						d.move(maze);
					}
					if (mode == 3){
						d.moveOrSleep(maze);
					}
					s.dragonOverlap(d, maze);
					d.fight(h, maze);
				}
			}
			
			if (dir.equals("q")){
				break;
			}
			if (!h.isAlive()) {
				maze.printMaze();
				System.out.println("Game Over");
				break;
			}
			if (h.isFinished()){
				maze.printMaze();
				System.out.println("Success!");
				break;
			}
		}
		input.close();
	}
}