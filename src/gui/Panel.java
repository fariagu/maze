package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Panel extends JPanel {
    private int x = 0, y = 0;
    private BufferedImage s_hero, ns_hero, dragon, s_dragon, sword, dsword, wall, exit, empty;
    private char[][] maze;

    public char[][] getMaze() {
        return maze;
    }

    public void setMaze(char[][] maze) {
        this.maze = maze;
    }

    Panel() {
        try {
            s_hero = ImageIO.read(new File("res/s_hero.jpg"));
            ns_hero = ImageIO.read(new File("res/ns_hero.jpg"));
            dragon = ImageIO.read(new File("res/dragon.jpg"));
            s_dragon = ImageIO.read(new File("res/s_dragon.jpg"));
            sword = ImageIO.read(new File("res/sword.jpg"));
            dsword = ImageIO.read(new File("res/dsword.jpg"));
            wall = ImageIO.read(new File("res/wall.jpg"));
            exit = ImageIO.read(new File("res/exit.jpg"));
            empty = ImageIO.read(new File("res/empty.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paintComponent(Graphics g) {
        int size = 400 / maze.length;
        super.paintComponent(g);

        for (char[] maze_row : maze) {
            for (char maze_col : maze_row) {
                switch (maze_col) {
                    case 'X':
                        g.drawImage(wall, x, y, size, size, null);
                        break;
                    case ' ':
                        g.drawImage(empty, x, y, size, size, null);
                        break;
                    case 'D':
                        g.drawImage(dragon, x, y, size, size, null);
                        break;
                    case 'd':
                        g.drawImage(s_dragon, x, y, size, size, null);
                        break;
                    case 'A':
                        g.drawImage(s_hero, x, y, size, size, null);
                        break;
                    case 'H':
                        g.drawImage(ns_hero, x, y, size, size, null);
                        break;
                    case 'S':
                        g.drawImage(exit, x, y, size, size, null);
                        break;
                    case 'E':
                        g.drawImage(sword, x, y, size, size, null);
                        break;
                    case 'F':
                        g.drawImage(dsword, x, y, size, size, null);
                        break;
                    default:
                        break;
                }
                x += size;
            }
            x = 0;
            y += size;
        }
        x = 0;
        y = 0;
    }
}
