package maze.logic;

import java.util.Random;

/**  
 * Sword.java - Classe da Sword.  
 * @author Diogo Cruz
 * @author Gustavo Faria
 */
public class Sword extends Point{	
	private boolean collected;
	private boolean overlapped;

	/**
	 * Construtor de uma Sword na posicao (x, y) em m.
	 * @param x Variavel do tipo int.
	 * @param y Variavel do tipo int.
	 * @param m Variavel do tipo MazeBuilder.
	 */
	public Sword(int x, int y, MazeBuilder m) {
		this.x = x;
		this.y = y;
		collected = false;
		m.printSword(this);
	}

	/**
	 * Construtor de uma Sword em m numa posicao aleatoria.
	 * @param m Maze na forma de MazeBuilder.
	 */
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

	/**
	 * Construtor de uma Sword em m numa posicao aleatoria.
	 * @param m Maze na forma de char[][].
	 */
	public Sword(char[][] m){
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
		m[x][y] = 'E';
	}

	/**
	 * Retorna o valor de collected.
	 * Serve para saber se a espada ja foi apanhada.
	 * @see Sword#heroOverlap(Hero h, MazeBuilder m)
	 * @return Variavel do tipo boolean.
	 */
	public boolean isCollected() {
		return collected;
	}

	/**
	 * Altera o valor de collected.
	 * @see Sword#heroOverlap(Hero h, MazeBuilder m)
	 * @param collected Variavel do tipo boolean.
	 */
	public void setCollected(boolean collected) {
		this.collected = collected;
	}

	/**
	 * Retona o valor de overlapped.
	 * @see Sword#dragonOverlap(Dragon d, MazeBuilder m)
	 * @return Variavel do tipo boolean.
	 */
	public boolean isOverlapped(){
		return overlapped;
	}
	
	/**
	 * Altera o valor de overlapped.
	 * @see Sword#dragonOverlap(Dragon d, MazeBuilder m)
	 * @param overlapped Variavel do tipo boolean.
	 */
	public void setOverlapped(boolean overlapped) {
		this.overlapped = overlapped;
	}

	/**
	 * Calcula se d esta na mesma posicao que a Sword.
	 * @param d Variavel do tipo Dragon.
	 * @param m Maze na forma de MazeBuilder.
	 */
	public void dragonOverlap(Dragon d, MazeBuilder m) {
		if (!this.isCollected()){
			if (this.x == d.getX() && this.y == d.getY())
				overlapped = true;
			m.printSword(this);
		}
	}

	/**
	 * Calcula se h esta na mesma posicao que a Sword.
	 * @param h Variavel do tipo Hero.
	 * @param m Maze na forma de MazeBuilder.
	 */
	public void heroOverlap(Hero h, MazeBuilder m) {
		if (this.x == h.x && this.y == h.y) {
			this.setCollected(true);
			h.setArmed(true);
		}
	}
}