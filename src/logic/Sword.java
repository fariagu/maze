package logic;

import java.util.Random;

/**
 * Sword.java - Classe da Sword.
 *
 * @author Diogo Cruz
 * @author Gustavo Faria
 */
public class Sword extends Point {
    private boolean collected;
    private boolean overlapped;

    /**
     * Construtor de uma Sword na posicao (x, y) em m.
     *
     * @param x Variavel do tipo int para a linha.
     * @param y Variavel do tipo int para a coluna.
     * @param m Maze na forma de MazeBuilder.
     */
    public Sword(int x, int y, MazeBuilder m) {
        this.x = x;
        this.y = y;
        collected = false;
        m.printSword(this);
    }

    /**
     * Construtor de uma Sword em m numa posicao aleatoria.
     *
     * @param m Maze na forma de MazeBuilder.
     */
    public Sword(MazeBuilder m) {
        int size = m.getSize();
        int x = 0, y = 0;
        Random r = new Random();

        while (m.getMaze(x, y) != ' ') {
            x = r.nextInt(size);
            y = r.nextInt(size);

            if (m.getMaze(x, y) == ' ') {
                this.x = x;
                this.y = y;
            }
        }
        m.printSword(this);
    }

    /**
     * Construtor de uma Sword em m numa posicao aleatoria.
     *
     * @param m Maze na forma de char[][].
     */
    public Sword(char[][] m) {
        int size = m.length;
        int x, y;
        Random r = new Random();

        while (true) {
            x = r.nextInt(size);
            y = r.nextInt(size);

            if (m[x][y] == ' ') {
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
     *
     * @return Variavel do tipo boolean.
     * @see #heroOverlap(Hero)
     */
    public boolean isCollected() {
        return collected;
    }

    /**
     * Altera o valor de collected.
     *
     * @see #heroOverlap(Hero)
     */
    private void setCollected() {
        this.collected = true;
    }

    /**
     * Retona o valor de overlapped.
     *
     * @return Variavel do tipo boolean.
     * @see Sword#dragonOverlap(Dragon d, MazeBuilder m)
     */
    boolean isOverlapped() {
        return overlapped;
    }

    /**
     * Altera o valor de overlapped.
     *
     * @param overlapped Variavel do tipo boolean.
     * @see Sword#dragonOverlap(Dragon d, MazeBuilder m)
     */
    public void setOverlapped(boolean overlapped) {
        this.overlapped = overlapped;
    }

    /**
     * Calcula se d esta na mesma posicao que a Sword.
     *
     * @param d Qualquer Dragon
     * @param m Maze na forma de MazeBuilder
     */
    public void dragonOverlap(Dragon d, MazeBuilder m) {
        if (!this.isCollected()) {
            if (this.x == d.getX() && this.y == d.getY())
                setOverlapped(true);
            m.printSword(this);
        }
    }

    /**
     * Calcula se Hero está na mesma posição que a Sword.
     *
     * @param h Hero
     */
    public void heroOverlap(Hero h) {
        if (this.x == h.x && this.y == h.y) {
            this.setCollected();
            h.setArmed(true);
        }
    }
}