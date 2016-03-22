package maze.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
//import javax.swing.JFrame;
import javax.swing.JPanel;

import maze.logic.MazeBuilder;

public class Panel extends JPanel{
	private int length = 15;
	private int x = 0, y = 0;
	private BufferedImage s_hero, ns_hero, dragon, s_dragon, sword, dsword, wall, exit, empty;
	private char[][] maze;
	private int dir;
	private Labirinto l;

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	public char[][] getMaze() {
		return maze;
	}

	public void setMaze(char[][] maze) {
		this.maze = maze;
	}
	
	public void initMaze(MazeBuilder m){
		this.maze = new char[m.getSize()][m.getSize()];
		this.maze = m.getFullMaze();
	}

	public Panel(/*MazeBuilder m*/){
		try {
			s_hero = ImageIO.read(new File("Images/s_hero.jpg"));
			ns_hero = ImageIO.read(new File("Images/ns_hero.jpg"));
			dragon = ImageIO.read(new File("Images/dragon.jpg"));
			s_dragon = ImageIO.read(new File("Images/s_dragon.jpg"));
			sword = ImageIO.read(new File("Images/sword.jpg"));
			dsword = ImageIO.read(new File("Images/dsword.jpg"));
			wall = ImageIO.read(new File("Images/wall.jpg"));
			exit = ImageIO.read(new File("Images/exit.jpg"));
			empty = ImageIO.read(new File("Images/empty.jpg"));
		} catch (IOException e){
			e.printStackTrace();
		}
		//maze = new char[m.getSize()][m.getSize()];
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);

		for (int i = 0; i < maze.length; i++){
			for (int j = 0; j < maze[i].length; j++){
				
				switch(maze[i][j]){
				case 'X':
					g.drawImage(wall, x, y, length, length, null);
					break;
				case ' ':
					g.drawImage(empty, x, y, length, length, null);
					break;
				case 'D':
					g.drawImage(dragon, x, y, length, length, null);
					break;
				case 'd':
					g.drawImage(s_dragon, x, y, length, length, null);
					break;
				case 'A':
					g.drawImage(s_hero, x, y, length, length, null);
					break;
				case 'H':
					g.drawImage(ns_hero, x, y, length, length, null);
					break;
				case 'S':
					g.drawImage(exit, x, y, length, length, null);
					break;
				case 'E':
					g.drawImage(sword, x, y, length, length, null);
					break;
				case 'F':
					g.drawImage(dsword, x, y, length, length, null);
					break;
				default:
					break;
				}
				x+=length;
			}
			x=0;
			y+=length;
		}
		x = 0;
		y = 0;
		//g.drawImage(square, x, y, length, length, 0, 0, square.getWidth(), square.getHeight(), null);
		//g.fillRect(x, y, length, length);
	}
}
