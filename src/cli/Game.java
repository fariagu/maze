package cli;

import logic.Dragon;
import logic.Hero;
import logic.MazeBuilder;
import logic.Sword;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private static MazeBuilder maze;

    private static int dirToInt(String dir) {
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

    /**
     * Sets the game mode.
     * <blockquote>The game modes are:</blockquote>
     * <p>1 - Dragon doesn't move</p>
     * <p>2 - Dragon moves randomly</p>
     * 3 - Dragon move and sleeps randomly
     *
     * @return int representing the game mode
     */
    private static int gamemode() {
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
            switch (gameMode) {
                case "1":
                    mode = 1;
                    gameSet = true;
                    break;
                case "2":
                    mode = 2;
                    gameSet = true;
                    break;
                case "3":
                    mode = 3;
                    gameSet = true;
                    break;
                default:
                    System.out.println("Try again. Press 1, 2 or 3 and then Enter.");
                    break;
            }
        }
        System.out.println();
        return mode;
    }

    private static void endGame(Hero h) {
        if (!h.isAlive()) {
            maze.printMaze();
            System.out.println("Game Over");
        }

        if (h.isFinished()) {
            maze.printMaze();
            System.out.println("Success!");
        }
    }

    public static void main(String args[]) {
        int mode = gamemode();
        Scanner input = new Scanner(System.in);
        maze = new MazeBuilder(13);//must be an odd number TODO exception handling
        Hero h = new Hero(maze);
        ArrayList<Dragon> dragon_list = new ArrayList<>();
        dragon_list.add(new Dragon(maze));
        dragon_list.add(new Dragon(maze));
        dragon_list.add(new Dragon(maze));
        Sword s = new Sword(maze);

        while (!h.isFinished() && h.isAlive()) {
            maze.printMaze();
            String dir = input.nextLine();
            if (dir.equals("q"))
                break;
            h.setDir(dirToInt(dir));
            h.move(maze);

            if (!s.isCollected())
                s.heroOverlap(h);

            s.setOverlapped(false);
            boolean areDragonsAlive = false;
            for (Dragon dragon : dragon_list)
                if (dragon.isAlive()) {
                    areDragonsAlive = true;
                    dragon.fight(h, maze);
                    if (mode == 2 || mode == 3) {
                        if (mode == 2)
                            dragon.move(maze);
                        if (mode == 3)
                            dragon.moveOrSleep(maze);
                        s.dragonOverlap(dragon, maze);
                        dragon.fight(h, maze);
                    }
                }

            if(!areDragonsAlive)
                h.setMapCleared();
        }

        endGame(h);

        input.close();
    }
}