package logic;

import java.util.Random;
import java.util.Stack;

/**
 * MazeBuilder.java - Class do maze.
 *
 * @author Diogo Cruz
 * @author Gustavo Faria
 */
public class MazeBuilder implements IMazeBuilder {
    private char defaultMaze[][] = {
            {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X'},
            {'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X'},
            {'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', 'X', ' ', 'S'},
            {'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X'},
            {'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X'},
            {'X', ' ', 'X', 'X', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'}
    };
    private char maze[][];
    private int size;

    /**
     * Returna o maze como uma matriz.
     *
     * @return Maze na forma char[][].
     */
    public char[][] getFullMaze() {
        return maze;
    }

    /**
     * Construtor pre-definido do maze.
     */
    public MazeBuilder() {
        maze = defaultMaze;
    }

    /**
     * Construtor do maze de dimensao s.
     *
     * @param s Uma variavel do tipo int.
     */
    public MazeBuilder(int s) {
        this.size = s;
        maze = randomMaze();
    }

    /**
     * Construtor do maze com o rato de dimensao s.
     * Serve para o utilizador construir o tabuleiro com o rato.
     * TODO Arranjar melhor solução para detetar que se quer um tabuleiro vazio
     *
     * @param s Variavel do tipo int
     * @param i Variavel para escolher o rato
     */
    public MazeBuilder(int s, int i) {
        this.size = s;
        maze = emptyMaze();
    }

    /**
     * Construtor do maze direto, tem que se dar um maze.
     *
     * @param m Maze na forma char[][].
     */
    public MazeBuilder(char[][] m) {
        maze = m;
    }

    /**
     * Retorna o valor na posicao (x, y) do maze.
     *
     * @param x Variavel do tipo int que representa o numero da linha pretendida.
     * @param y Variavel do tipo int que representa o numero da coluna pretendida.
     * @return O valor presente na coordenada (x, y) do maze.
     */
    public char getMaze(int x, int y) {
        return maze[x][y];
    }

    /**
     * Altera o valor na posicao (x, y) do maze para o valor de val.
     *
     * @param x   Variavel do tipo int que representa o numero da linha pretendida.
     * @param y   Variavel do tipo int que representa o numero da coluna pretendida.
     * @param val Variavel do tipo char que sera o novo valor da posicao (x, y) do maze.
     */
    public void setMaze(int x, int y, char val) {
        maze[x][y] = val;
    }

    /**
     * Altera o valor na posicao p do maze para o valor de val.
     * TODO Alterar os setMaze para esta função com Point
     *
     * @param p   Variavel do tipo Point que representa a posicao no maze.
     * @param val Variavel do tipo char que sera o novo valor da posicao p do maze.
     */
    void setMaze(Point p, char val) {
        maze[p.x][p.y] = val;
    }

    /**
     * Retorna a dimensao do maze.
     *
     * @return Uma variavel do tipo int.
     */
    public int getSize() {
        return size;
    }

    /**
     * Escreve na consola o estado atual do maze.
     */
    public void printMaze() {
        for (char[] maze_row : maze) {
            for (char maze_col : maze_row)
                System.out.print(maze_col + " ");
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Escreve o estado atual do maze para uma string.
     * TODO guardar o tabuleiro para voltar a jogar no futuro
     *
     * @return Uma variavel do tipo String.
     */
    public String printMaze2String() {
        StringBuilder string = new StringBuilder();
        for (char[] maze_row : maze) {
            for (char maze_col : maze_row) {
                string.append(maze_col).append(" ");
            }
            string.append("\n");
        }
        return string.toString();
    }

    /**
     * Escreve o valor de h no maze.
     *
     * @param h Variavel do tipo Hero.
     */
    void printHero(Hero h) {
        if (h.isArmed()) {
            this.setMaze(h.x, h.y, 'A');
        } else {
            this.setMaze(h.x, h.y, 'H');
        }

    }

    /**
     * Escreve o valor de d no maze.
     *
     * @param d Variavel do tipo Dragon.
     */
    void printDragon(Dragon d) {
        if (d.isSleeping()) {
            this.setMaze(d.x, d.y, 'd');
        } else {
            this.setMaze(d.x, d.y, 'D');
        }
    }

    /**
     * Escreve o valor de s no maze.
     * Se j� tiver sido apanhada nao sera desenhada.
     * Tem um caso especial para quando tem um Dragon em cima da Sword.
     *
     * @param s Variavel do tipo Sword.
     */
    void printSword(Sword s) {
        if (!s.isCollected())
            if (s.isOverlapped())
                this.setMaze(s.getX(), s.getY(), 'F');
            else
                this.setMaze(s.getX(), s.getY(), 'E');
    }

    /**
     * Verifica quais as posicoes do maze que ja foram visitadas.
     *
     * @param maze Maze do tipo char[][].
     * @return Variavel do tipo boolean.
     * @see MazeBuilder#randomMaze()
     */
    private boolean check(char[][] maze) {
        for (char[] maze_row : maze)
            for (int j = 0; j < maze.length; j++) {
                if (maze_row[j] != '+')
                    return false;
            }
        return true;
    }

    /**
     * Retorna o numero de espacos em branco do maze.
     *
     * @return Variavel do tipo int.
     */
    public int getBlankSpaces() {
        int res = 0;
        for (int i = 1; i < maze.length - 1; i++) {
            for (int j = 1; j < maze[i].length - 1; j++) {
                if (maze[i][j] == ' ') {
                    res++;
                }
            }
        }
        return res;
    }

    /**
     * Cria um Maze vazio mas com as bordas.
     *
     * @return Maze na forma de char[][].
     */
    private char[][] emptyMaze() {
        char[][] m = new char[size][size];

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (i == 0 || i == size - 1 || j == 0 || j == size - 1)
                    m[i][j] = 'X';
                else
                    m[i][j] = ' ';
        return m;
    }

    /**
     * Construtor do maze aleatorio.
     *
     * @return Maze do tipo char[][].
     */
    private char[][] randomMaze() {
        char[][] tmp = new char[size][size];
        int vcsize = (size - 1) / 2;
        char[][] visitedCells = new char[vcsize][vcsize];
        int pointMX = 0, pointMY = 0;
        int vcx = vcsize - 1;
        int vcy = 0;
        int mazeX = size - 2;
        int mazeY = 1;
        int xExit = 0;
        int yExit = 0;
        Stack<Integer> stackMov = new Stack<>();

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if ((i % 2 != 0) && (j % 2 != 0))
                    tmp[i][j] = ' ';
                else
                    tmp[i][j] = 'X';

        //creating exit
        Random r = new Random();
        int num = r.nextInt(2);
        if (num == 0) { // horizontal
            xExit = r.nextInt(2) * size;
            yExit = r.nextInt(size - 2) + 1;
            if (xExit == 0)
                pointMX = 1;
            else
                pointMX = size - 2;
            pointMY = yExit;
        }
        if (num == 1) { // vertical
            xExit = r.nextInt(size - 2) + 1;
            yExit = r.nextInt(2) * size;
            if (yExit == 0)
                pointMY = 1;
            else
                pointMY = size - 2;
            pointMX = xExit;
        }
        if (xExit == size)
            xExit--;
        if (yExit == size)
            yExit--;
        tmp[yExit][xExit] = 'S';
        tmp[pointMY][pointMX] = ' ';

        for (int i = 0; i < visitedCells.length; i++)
            for (int j = 0; j < visitedCells.length; j++)
                visitedCells[i][j] = '.';
        visitedCells[vcy][vcx] = '+';
        boolean flag1 = false;
        boolean flag2 = false;
        boolean nextTo = false;

        while (!flag2) {
            while (!flag1) {
                Random rand = new Random();
                int mov = rand.nextInt(4);
                switch (mov) {
                    case 0://up
                        if (vcy < 1)
                            flag1 = false;
                        else if (visitedCells[vcy - 1][vcx] == '+')
                            flag1 = false;
                        else {
                            vcy--;
                            visitedCells[vcy][vcx] = '+';
                            tmp[mazeY - 1][mazeX] = ' ';
                            tmp[mazeY - 2][mazeX] = ' ';
                            mazeY -= 2;
                            flag1 = true;
                            stackMov.push(0);
                        }
                        break;
                    case 1://down
                        if (vcy > (vcsize - 2))
                            flag1 = false;
                        else if (visitedCells[vcy + 1][vcx] == '+')
                            flag1 = false;
                        else {
                            vcy++;
                            visitedCells[vcy][vcx] = '+';
                            tmp[mazeY][mazeX] = ' ';
                            tmp[mazeY + 1][mazeX] = ' ';
                            tmp[mazeY + 2][mazeX] = ' ';
                            //mazeY++;
                            mazeY += 2;
                            flag1 = true;
                            stackMov.push(1);
                        }
                        break;
                    case 2://left
                        if (vcx < 1)
                            flag1 = false;
                        else if (visitedCells[vcy][vcx - 1] == '+')
                            flag1 = false;
                        else {
                            vcx--;
                            visitedCells[vcy][vcx] = '+';
                            tmp[mazeY][mazeX] = ' ';
                            tmp[mazeY][mazeX - 1] = ' ';
                            tmp[mazeY][mazeX - 2] = ' ';
                            //mazeX--;
                            mazeX -= 2;
                            flag1 = true;
                            stackMov.push(2);
                        }
                        break;
                    case 3://right
                        if (vcx > (vcsize - 2))
                            flag1 = false;
                        else if (visitedCells[vcy][vcx + 1] == '+')
                            flag1 = false;
                        else {
                            vcx++;
                            visitedCells[vcy][vcx] = '+';
                            tmp[mazeY][mazeX] = ' ';
                            tmp[mazeY][mazeX + 1] = ' ';
                            tmp[mazeY][mazeX + 2] = ' ';
                            //mazeX++;
                            mazeX += 2;
                            flag1 = true;
                            stackMov.push(3);
                        }
                        break;
                }
            }
            flag1 = false;
            if (!check(visitedCells)) {
                while (!nextTo) {
                    switch (0) {
                        case 0://up
                            if (vcy >= 1)
                                if (visitedCells[vcy - 1][vcx] != '+') {
                                    nextTo = true;
                                    break;
                                }
                        case 1://down
                            if (vcy < (vcsize - 1))
                                if (visitedCells[vcy + 1][vcx] != '+') {
                                    nextTo = true;
                                    break;
                                }
                        case 2://left
                            if (vcx >= 1)
                                if (visitedCells[vcy][vcx - 1] != '+') {
                                    nextTo = true;
                                    break;
                                }
                        case 3://right
                            if (vcx < (vcsize - 1))
                                if (visitedCells[vcy][vcx + 1] != '+') {
                                    nextTo = true;
                                    break;
                                }
                    }
                    if (!nextTo) {
                        int k = stackMov.pop();
                        switch (k) {
                            case 0://go up
                                vcy++;
                                mazeY += 2;
                                break;
                            case 1://go down
                                vcy--;
                                mazeY -= 2;
                                break;
                            case 2://go left
                                vcx++;
                                mazeX += 2;
                                break;
                            case 3://go right
                                vcx--;
                                mazeX -= 2;
                                break;
                        }
                    }
                }
                nextTo = false;
            } else {
                flag2 = true;
            }
        }
        return tmp;
    }

    /**
     * Construtor do maze.
     */
    @Override
    public char[][] buildMaze(int size) throws IllegalArgumentException {
        return new MazeBuilder(size).getFullMaze();
    }
}