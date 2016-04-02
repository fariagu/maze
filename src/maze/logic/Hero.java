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

	/**
	 * Construtor do Hero nas coordenadas (x, y) do maze m.
	 * @param x Variavel do tipo int.
	 * @param y Variavel do tipo int.
	 * @param m Maze na forma de MazeBuilder.
	 */
	public Hero(int x, int y, MazeBuilder m) {
		this.x = x;
		this.y = y;
		m.printHero(this);
	}

	/**
	 * Construtor do Hero em coordenadas aleatorias do maze m.
	 * @param m Maze na forma de MazeBuilder.
	 */
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

	/**
	 * Construtor do Hero em coordenadas aleatorias do maze m.
	 * @param m Maze na forma de char[][].
	 */
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

	/**
	 * Retorna o valor de Armed.
	 * Serve para saber se ja apanhou a Sword e pode matar Dragons.
	 * @return Variavel do tipo boolean.
	 */
	public boolean isArmed() {
		return armed;
	}
	
	/**
	 * Altera o valor de armed.
	 * @see Hero#isArmed()
	 * @param armed Variavel do tipo boolean.
	 */
	public void setArmed(boolean armed) {
		this.armed = armed;
	}
	
	/**
	 * Retorna o valor de alive.
	 * @return Variavel do tipo boolean.
	 */
	public boolean isAlive() {
		return alive;
	}
	
	/**
	 * Altera o valor de alive.
	 * @param alive Variavel do tipo boolean.
	 */
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	/**
	 * Retorna o valor de mapCleared.
	 * Serve para saber se o Hero ja matou todos os Dragons.
	 * @see Hero#checkMapCleared()
	 * @return Variavel do tipo boolean.
	 */
	public boolean isMapCleared() {
		return mapCleared;
	}
	
	/**
	 * Altera o valor de mapCleared.
	 * @see Hero#checkMapCleared()
	 * @param mapCleared Variavel do tipo boolean.
	 */
	public void setMapCleared(boolean mapCleared) {
		this.mapCleared = mapCleared;
	}
	
	/**
	 * Retorna o valor de finished.
	 * Serve para saber se o jogo acabou.
	 * @return Variavel do tipo boolean.
	 */
	public boolean isFinished() {
		return finished;
	}
	
	/**
	 * Altera o valor de finished.
	 * @see Hero#isFinished()
	 * @param finished Variavel do tipo boolean.
	 */
	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	/**
	 * Altera a direcao para onde o Hero se vai deslocar.
	 * @see Hero#move(MazeBuilder m)
	 * @param dir Variavel do tipo boolean.
	 */
	public void setDir(int dir) {
		this.dir = dir;
	}

	/**
	 * Imprime no maze o valor do Hero.
	 * @param m Maze na forma de MazeBuilder.
	 */
	public void print(MazeBuilder m) {
		if (this.armed)
			m.setMaze(this, 'A');
		else
			m.setMaze(x, y, 'H');
	}

	/**
	 * Calcula se todos os Dragons foram mortos.
	 * Se sim, o jogador esta em condicoes de ganhar o jogo e pode dirigir se para a saida.
	 */
	public void checkMapCleared(){
		ArrayList<Dragon> d = Dragon.getDragons();
		
		for (int i = 0; i < d.size(); i++)
			if(d.get(i).isAlive())
				return;
		this.mapCleared = true;
	}

	/**
	 * Move o Hero na direcao dir.
	 * @param m Maze na forma de MazeBuilder
	 */
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