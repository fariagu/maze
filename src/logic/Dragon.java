package logic;

import java.util.Random;

/**
 * Dragon.java - Classe do Dragon.
 *
 * @author Diogo Cruz
 * @author Gustavo Faria
 */
public class Dragon extends Point {
    private boolean alive;
    private boolean sleeping;
    private int moveCounter;
    private int fallAsleepCounter;
    private int sleepCounter;
    private int wakeUpCounter;

    /**
     * Constroi um Dragon na posicao (x, y) em m.
     *
     * @param x Variavel do tipo int para a linha.
     * @param y Variavel do tipo int para a coluna.
     * @param m Maze na forma de MazeBuilder.
     */
    public Dragon(int x, int y, MazeBuilder m) {
        this.x = x;
        this.y = y;
        alive = true;
        m.printDragon(this);
    }

    /**
     * Construtor de Dragon em m num Point aleatorio.
     * Garante que nao esta perto do heroi.
     * Serve para quando se quer ter mais que um Dragon no maze
     *
     * @param m Maze na forma de MazeBuilder.
     */
    public Dragon(MazeBuilder m) {
        int size = m.getSize();
        int x, y;
        Random r = new Random();

        while (true) {
            x = r.nextInt(size - 1) + 1;
            y = r.nextInt(size - 1) + 1;

            if (m.getMaze(x, y) == ' ')
                if (m.getMaze(x - 1, y) != 'H')
                    if (m.getMaze(x + 1, y) != 'H')
                        if (m.getMaze(x, y - 1) != 'H')
                            if (m.getMaze(x, y + 1) != 'H') {
                                this.x = x;
                                this.y = y;
                                break;
                            }
        }
        alive = true;
        m.printDragon(this);
    }

    /**
     * Construtor de um Dragon em m.
     * Garante que nao esta perto do heroi.
     *
     * @param m Maze na forma de char[][].
     */
    public Dragon(char[][] m) {
        int size = m.length;
        int x, y;
        Random r = new Random();

        while (true) {
            x = r.nextInt(size - 1) + 1;
            y = r.nextInt(size - 1) + 1;

            if (m[x][y] == ' ') {

                if (m[x - 1][y] != 'H') {
                    if (m[x + 1][y] != 'H') {
                        if (m[x][y - 1] != 'H') {
                            if (m[x][y + 1] != 'H') {
                                this.x = x;
                                this.y = y;
                                break;
                            }
                        }
                    }
                }
            }
        }
        alive = true;
        m[x][y] = 'D';
    }

    /**
     * Retorna o estado alive do Dragon.
     * Usado para confirmar que todos os Dragons estao mortos e o Hero pode acabar o jogo.
     *
     * @return Um boolean.
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Retorna do estado sleeping do Dragon.
     *
     * @return Um boolean.
     */
    public boolean isSleeping() {
        return sleeping;
    }

    /**
     * Faz a luta entre h e os Dragons.
     * So ha luta se o Dragon estiver perto do Hero.
     *
     * @param h Variavel do tipo Hero.
     * @param m Maze na forma de MazeBuilder.
     */
    public void fight(Hero h, MazeBuilder m) {
        if ((this.x == h.x && this.y == (h.y + 1)) || (this.x == h.x && this.y == (h.y - 1)) || (this.y == h.y && this.x == (h.x + 1)) || (this.y == h.y && this.x == (h.x - 1)) || (this.y == h.y && this.x == h.x)) {
            if (h.isArmed()) {
                this.alive = false;
                m.setMaze(this.x, this.y, ' ');
            } else if (!sleeping) {
                h.setDead();
            }
        }
    }

    /**
     * Muda a posicao do Dragon de forma aleatoria.
     *
     * @param m Maze na forma de MazeBuilder.
     */
    public void move(MazeBuilder m) {
        Random r = new Random();
        int dir = 4;
        boolean canContinue = false;

        while (!canContinue) {
            dir = r.nextInt(5);

            switch (dir) {
                case 0:
                    if (m.getMaze(this.x - 1, this.y) != 'X' && m.getMaze(this.x - 1, this.y) != 'S') {
                        canContinue = true;
                    }
                    break;
                case 1:
                    if (m.getMaze(this.x + 1, this.y) != 'X' && m.getMaze(this.x + 1, this.y) != 'S') {
                        canContinue = true;
                    }
                    break;
                case 2:
                    if (m.getMaze(this.x, this.y - 1) != 'X' && m.getMaze(this.x, this.y - 1) != 'S') {
                        canContinue = true;
                    }
                    break;
                case 3:
                    if (m.getMaze(this.x, this.y + 1) != 'X' && m.getMaze(this.x, this.y + 1) != 'S') {
                        canContinue = true;
                    }
                    break;
                default:
                    canContinue = true;
            }
        }

        switch (dir) {
            case 0:
                if (m.getMaze(x - 1, y) == ' ' || m.getMaze(x - 1, y) == 'E') {
                    m.setMaze(x, y, ' ');
                    x--;
                    m.printDragon(this);
                }
                break;
            case 1:
                if (m.getMaze(x + 1, y) == ' ' || m.getMaze(x + 1, y) == 'E') {
                    m.setMaze(x, y, ' ');
                    x++;
                    m.printDragon(this);
                }
                break;
            case 2:
                if (m.getMaze(x, y - 1) == ' ' || m.getMaze(x, y - 1) == 'E') {
                    m.setMaze(x, y, ' ');
                    y--;
                    m.printDragon(this);
                }
                break;
            case 3:
                if (m.getMaze(x, y + 1) == ' ' || m.getMaze(x, y + 1) == 'E') {
                    m.setMaze(x, y, ' ');
                    y++;
                    m.printDragon(this);
                }
                break;
            default:
                break;

        }
    }

    /**
     * Muda o valor de moveCounter para o default.
     *
     * @see Dragon#moveOrSleep(MazeBuilder m)
     */
    private void resetMoveCounter() {
        moveCounter = -1;
    }

    /**
     * Muda o valor de fallAsleepCounter para o default.
     *
     * @see Dragon#moveOrSleep(MazeBuilder m)
     */
    private void resetFallAsleepCounter() {
        fallAsleepCounter = -1;
    }

    /**
     * Muda o valor de sleepCounter para o default.
     *
     * @see Dragon#moveOrSleep(MazeBuilder m)
     */
    private void resetSleepCounter() {
        sleepCounter = -1;
    }

    /**
     * Muda o valor de wakeUpCounter para o default.
     *
     * @see Dragon#moveOrSleep(MazeBuilder m)
     */
    private void resetWakeUpCounter() {
        wakeUpCounter = -1;
    }

    /**
     * Move o Dragon ou adormece-o aleatoriamente.
     * Ao fim de alguns movimentos o Dragon ira parar ou adormecer ou acordar.
     *
     * @param m Maze na forma de MazeBuilder.
     */
    public void moveOrSleep(MazeBuilder m) {
        Random r = new Random();

        if (moveCounter >= 0) {
            if (moveCounter < 5) {
                move(m);
                moveCounter++;
            } else {//moveCounter == 5
                fallAsleepCounter = r.nextInt(3);
                resetMoveCounter();
            }
        }

        if (fallAsleepCounter >= 0) {
            if (fallAsleepCounter == 0) {
                sleeping = true;
                resetFallAsleepCounter();
                sleepCounter = 0;
                m.printDragon(this);
            } else {
                move(m);
                fallAsleepCounter--;
            }
        }

        if (sleepCounter >= 0) {
            if (sleepCounter < 2) {
                sleepCounter++;
            } else {//sleepCounter == 2
                wakeUpCounter = r.nextInt(3);
                resetSleepCounter();
            }
        }

        if (wakeUpCounter >= 0) {
            if (wakeUpCounter == 0) {
                sleeping = false;
                resetWakeUpCounter();
                moveCounter = 0;
                m.printDragon(this);
            } else {
                wakeUpCounter--;
            }
        }
    }
}