package logic;

import java.util.ArrayList;
import java.util.Random;

/**
 * Hero.java - Classe do Hero.
 *
 * @author Diogo Cruz
 * @author Gustavo Faria
 */
public class Hero extends Point {
    private boolean armed = false;
    private boolean alive = true;
    private boolean mapCleared = false;
    private boolean finished = false;
    private int dir;

    /**
     * Construtor do Hero nas coordenadas (x, y) do maze m.
     *
     * @param x Variavel do tipo int para a linha.
     * @param y Variavel do tipo int para a coluna.
     * @param m Maze na forma de MazeBuilder.
     */
    public Hero(int x, int y, MazeBuilder m) {
        this.x = x;
        this.y = y;
        m.printHero(this);
    }

    /**
     * Construtor do Hero em coordenadas aleatorias do maze m.
     *
     * @param m Maze na forma de MazeBuilder.
     */
    public Hero(MazeBuilder m) {
        int size = m.getSize();
        int x, y;
        Random r = new Random();

        while (true) {
            x = r.nextInt(size - 1) + 1;
            y = r.nextInt(size - 1) + 1;

            if (m.getMaze(x, y) == ' ') {
                this.x = x;
                this.y = y;
                break;
            }
        }

        m.printHero(this);
    }

    /**
     * Construtor do Hero em coordenadas aleatorias do maze m.
     *
     * @param m Maze na forma de char[][].
     */
    public Hero(char[][] m) {
        int size = m.length;
        int x, y;
        Random r = new Random();

        while (true) {
            x = r.nextInt(size - 1) + 1;
            y = r.nextInt(size - 1) + 1;

            if (m[x][y] == ' ') {
                this.x = x;
                this.y = y;
                break;
            }
        }

        m[x][y] = 'H';
    }

    /**
     * Retorna o valor de Armed.
     * Serve para saber se ja apanhou a Sword e pode matar Dragons.
     *
     * @return Variavel do tipo boolean.
     */
    public boolean isArmed() {
        return armed;
    }

    /**
     * Altera o valor de armed.
     *
     * @param armed Variavel do tipo boolean.
     * @see Hero#isArmed()
     */
    public void setArmed(boolean armed) {
        this.armed = armed;
    }

    /**
     * Retorna o valor de alive.
     *
     * @return Variavel do tipo boolean.
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Coloca o Hero no estado morto.
     */
    void setDead() {
        this.alive = false;
    }

    /**
     * Retorna o valor de mapCleared.
     * Serve para saber se o Hero ja matou todos os Dragons.
     *
     * @return Variavel do tipo boolean.
     * @see Hero#checkMapCleared()
     */
    public boolean isMapCleared() {
        return mapCleared;
    }

    /**
     * Altera o valor de mapCleared para true.
     *
     * @see Hero#checkMapCleared()
     */
    public void setMapCleared() {
        this.mapCleared = true;
    }

    /**
     * Retorna o valor de finished.
     * Serve para saber se o jogo acabou.
     *
     * @return Variavel do tipo boolean.
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Altera o valor de finished.
     *
     * @param finished Variavel do tipo boolean.
     * @see Hero#isFinished()
     */
    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    /**
     * Altera a direcao para onde o Hero se vai deslocar.
     *
     * @param dir Variavel do tipo int.
     * @see Hero#move(MazeBuilder m)
     */
    public void setDir(int dir) {
        this.dir = dir;
    }

    /**
     * Calcula se todos os Dragons foram mortos.
     * Se sim, o jogador esta em condicoes de ganhar o jogo e pode dirigir se para a saida.
     */
    void checkMapCleared() {
        ArrayList<Dragon> dragon_list = Dragon.getDragons();

        for (Dragon d : dragon_list)
            if (d.isAlive())
                return;
        this.mapCleared = true;
    }

    /**
     * Verifica se a Sword está nas posições ortogonais ao Hero.
     * Não é preciso o Hero ir para a posição da espada para a apanhar, basta estar numa posição adjacente.
     *
     * @param m Maze atual
     */
    private void checkSword(MazeBuilder m) {
        if (m.getMaze(x - 1, y) == 'E') {//up
            this.armed = true;
            m.setMaze(x - 1, y, ' ');
        } else if (m.getMaze(x + 1, y) == 'E') {//down
            this.armed = true;
            m.setMaze(x + 1, y, ' ');
        } else if (m.getMaze(x, y - 1) == 'E') {//left
            this.armed = true;
            m.setMaze(x, y - 1, ' ');
        } else if (m.getMaze(x, y + 1) == 'E') {//right
            this.armed = true;
            m.setMaze(x, y + 1, ' ');
        }
    }

    /**
     * Atualiza o estado do Hero.
     * Primeiro verifica se é uma posição livre, se for move-se para lá.
     * Caso não seja, verifica se é a saída e se já reuniu as condições para se deslocar para lá.
     *
     * @param m Maze atual
     * @param ix Incremento em x
     * @param iy Incremento em y
     */
    private void updateHero(MazeBuilder m, int ix, int iy) {
        if (m.getMaze(x + ix, y + iy) == ' ') {//se a posição está livre
            m.setMaze(x, y, ' ');
            x += ix;
            y += iy;
            m.printHero(this);
        } else if (m.getMaze(x + ix, y + iy) == 'S' && this.armed && this.mapCleared) {//ir para a saída
            m.setMaze(x, y, ' ');
            x += ix;
            y += iy;
            m.printHero(this);
            this.setFinished(true);
        }
    }

    /**
     * Move o Hero na direcao dir.
     *
     * @param m Maze na forma de MazeBuilder
     */
    public void move(MazeBuilder m) {//0=up, 1=down, 2=left, 3=right
        checkSword(m);
        switch (dir) {
            case 0://up
                updateHero(m, -1, 0);
                break;
            case 1://down
                updateHero(m, 1, 0);
                break;
            case 2://left
                updateHero(m, 0, -1);
                break;
            case 3://right
                updateHero(m, 0, 1);
                break;
            default:
                break;
        }
    }
}