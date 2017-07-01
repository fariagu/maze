package logic;

/**
 * IMazeBuilder.java - Interface do maze.
 *
 * @author Diogo Cruz
 * @author Gustavo Faria
 */
public interface IMazeBuilder {
    char[][] buildMaze(int size) throws IllegalArgumentException;
}