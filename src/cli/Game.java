package cli;

import logic.Dragon;
import logic.Hero;
import logic.MazeBuilder;
import logic.Sword;

import java.util.Scanner;

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

    public static int gamemode() {
        Scanner input = new Scanner(System.in);
        boolean gameSet = false;
        String gameMode;
        int mode = 0;

        System.out.println("Choose the Game Mode:");
        System.out.println("1 - Dragon doesn't move;");
        System.out.println("2 - Dragon moves randomly;");
        System.out.println("3 - Dragon move and sleeps randomly.");

        while (!gameSet) {
            gameMode = input.nextLine();

            if (gameMode.equals("1")) {
                mode = 1;
                gameSet = true;
            } else if (gameMode.equals("2")) {
                mode = 2;
                gameSet = true;
            } else if (gameMode.equals("3")) {
                mode = 3;
                gameSet = true;
            } else {
                System.out.println("Try again. Press 1, 2 or 3 and then Enter.");
            }
        }
        System.out.println("...");
        return mode;
    }

    public static void main(String args[]) {
        int mode = 0;
        mode = gamemode();

        Scanner input = new Scanner(System.in);
        MazeBuilder maze = new MazeBuilder(13);//must be an odd number TODO exception handling
        Hero h = new Hero(maze);
        Dragon d = new Dragon();
        d.multipleDragons(3, maze);
        Sword s = new Sword(maze);
        String dir;

        while (finished == false) {
            maze.printMaze();
            dir = input.nextLine();
            h.setDir(dirToInt(dir));
            h.move(maze);

            if (!s.isCollected()) {
                s.heroOverlap(h, maze);
            }

            s.setOverlapped(false);
            for (int i = 0; i < d.getDragons().size(); i++) {
                if (d.getDragons().get(i).isAlive()) {
                    d.getDragons().get(i).fight(h, maze);

                    if (mode == 2 || mode == 3) {
                        if (mode == 2) {
                            d.getDragons().get(i).move(maze);
                        }
                        if (mode == 3) {
                            d.getDragons().get(i).moveOrSleep(maze);
                        }
                        s.dragonOverlap(d.getDragons().get(i), maze);
                        d.getDragons().get(i).fight(h, maze);
                    }
                }
            }

            if (dir.equals("q")) {
                break;
            }
            if (!h.isAlive()) {
                maze.printMaze();
                System.out.println("Game Over");
                break;
            }
            if (h.isFinished()) {
                maze.printMaze();
                System.out.println("Success!");
                break;
            }
        }
        input.close();
    }
}