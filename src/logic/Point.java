package logic;

/**
 * Point.java - Classe da Point.
 *
 * @author Diogo Cruz
 * @author Gustavo Faria
 */
public class Point {
    int x;
    int y;

    /**
     * Construtor de uma coordenada na forma de (x, y).
     *
     * @param x Variavel do tipo int para a linha.
     * @param y Variavel do tipo int para a coluna.
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Construtor generico de um Point.
     */
    Point() {
        this.x = 1;
        this.y = 1;
    }

    /**
     * Retorna a linha em que se encontra.
     *
     * @return Variavel do tipo int que representa a linha.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Retorna a coluna em que se encontra.
     *
     * @return Variavel do tipo int que representa a coluna.
     */
    public int getY() {
        return this.y;
    }

//	/**
//	 * Compara 2 Point pelas suas coordenadas x e y.
//	 * @param p2 Point a comparar.
//	 * @return Variavel to tipo Boolean.
//	 */
//	public boolean equals(Point p2) {
//		if(this.x == p2.getX() && this.y == p2.getY())
//			return true;
//		return false;
//	}
}