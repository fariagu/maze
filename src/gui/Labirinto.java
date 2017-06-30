package gui;

import logic.Dragon;
import logic.Hero;
import logic.MazeBuilder;
import logic.Sword;

import javax.swing.*;
import java.awt.*;
import java.awt.Window.Type;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

public class Labirinto implements PropertyChangeListener {

    private JFrame frmLabirinto;
    private JComboBox<String> comboBox;
    private JPanel gamePanel;
    private JLabel status;
    private MazeBuilder maze;
    private Hero h;
    private Dragon d;
    private Sword s;
    private int mode;

    private JButton leftButton;
    private JButton rightButton;
    private JButton upButton;
    private JButton downButton;

    private JFormattedTextField dimension;
    private JFormattedTextField nDragons;
    private NumberFormat nFormat = NumberFormat.getNumberInstance();
    private int nDim = 11;
    private int nDra = 1;


    JFrame getFrmLabirinto() {
        return frmLabirinto;
    }

    JPanel getGamePanel() {
        return gamePanel;
    }

    void setGamePanel(JPanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public MazeBuilder getMaze() {
        return maze;
    }

    public void setMaze(MazeBuilder maze) {
        this.maze = maze;
    }

    void setH(Hero h) {
        this.h = h;
    }

    public void setD(Dragon d) {
        this.d = d;
    }

    void setS(Sword s) {
        this.s = s;
    }

    JButton getLeftButton() {
        return leftButton;
    }

    JButton getRightButton() {
        return rightButton;
    }

    JButton getUpButton() {
        return upButton;
    }

    JButton getDownButton() {
        return downButton;
    }

    private void turn(int direcao) {
        h.setDir(direcao);
        h.move(maze);
        if (!s.isCollected())
            s.heroOverlap(h, maze);

        s.setOverlapped(false);
        for (int i = 0; i < Dragon.getDragons().size(); i++)
            if (Dragon.getDragons().get(i).isAlive()) {
                Dragon.getDragons().get(i).fight(h, maze);
                if (mode == 2 || mode == 3) {
                    if (mode == 2)
                        Dragon.getDragons().get(i).move(maze);
                    else if (mode == 3)
                        Dragon.getDragons().get(i).moveOrSleep(maze);

                    Dragon.getDragons().get(i).fight(h, maze);
                    s.dragonOverlap(Dragon.getDragons().get(i), maze);
                }
            }

        if (!h.isAlive()) {
            System.out.println("Game Over");
            gamePanel.setFocusable(false);
        }
        if (h.isFinished()) {
            System.out.println("Success!");
            gamePanel.setFocusable(false);
        }

        maze.printMaze();
        gamePanel.requestFocus();
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Labirinto window = new Labirinto();
                window.frmLabirinto.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    void convGameMode(String s) {
        // s = (String) comboBox.getSelectedItem();

        switch (s) {
            case "Useless":
                mode = 1;
                break;
            case "Sleepy":
                mode = 3;
                break;
            case "Beast Mode":
                mode = 2;
                break;
            default:
                mode = 1;
        }
    }

    /**
     * Create the application.
     */
    public Labirinto() {
        //addKeyListener(this);
        initialize();
    }

    void moveLeft() {
        status.setText("Moveu-se para a esquerda");
        turn(2);
        //textArea.setText(maze.printMaze2String());
        ((Panel) gamePanel).setMaze(maze.getFullMaze());
        gamePanel.repaint();
        checkEndGame();
    }

    void moveRight() {
        status.setText("Moveu-se para a direita");
        turn(3);
        //textArea.setText(maze.printMaze2String());
        ((Panel) gamePanel).setMaze(maze.getFullMaze());
        gamePanel.repaint();
        checkEndGame();
    }

    void moveUp() {
        status.setText("Moveu-se para cima");
        turn(0);
        //textArea.setText(maze.printMaze2String());
        ((Panel) gamePanel).setMaze(maze.getFullMaze());
        gamePanel.repaint();
        checkEndGame();
    }

    void moveDown() {
        status.setText("Moveu-se para baixo");
        turn(1);
        //textArea.setText(maze.printMaze2String());
        ((Panel) gamePanel).setMaze(maze.getFullMaze());
        gamePanel.repaint();
        checkEndGame();
    }

    /**
     * Checks if the game has ended.
     * The game has ended if the hero is dead, or if the hero has reached the exit.
     */
    private void checkEndGame() {
        if (!h.isAlive() || h.isFinished()) {
            maze.printMaze();
            leftButton.setEnabled(false);
            downButton.setEnabled(false);
            rightButton.setEnabled(false);
            upButton.setEnabled(false);

            if (!h.isAlive())
                status.setText("Perdeu");
            else if (h.isFinished())
                status.setText("Ganhou");
        }
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmLabirinto = new JFrame();
        frmLabirinto.setType(Type.UTILITY);
        frmLabirinto.setTitle("Maze");
        frmLabirinto.setBounds(100, 100, 650, 450);
        frmLabirinto.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frmLabirinto.getContentPane().setLayout(null);

        status = new JLabel("Gerar labirinto");//diz o estado atual do jogo
        status.setBounds(10, 360, 186, 20);
        frmLabirinto.getContentPane().add(status);

		/*
        final JTextArea textArea = new JTextArea();//onde se desenha o labirinto
        textArea.setEditable(false);
		textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
		textArea.setBounds(238, 36, 186, 214);
		frmLabirinto.getContentPane().add(textArea);
		*/

        final JButton ExitButton = new JButton("Exit");//sair do programa, alternativa ao X da janela
        ExitButton.addActionListener(e -> {
            status.setText("Adeus");//nao se ve, fecha logo
            System.exit(0);
        });
        ExitButton.setBounds(109, 227, 89, 23);
        frmLabirinto.getContentPane().add(ExitButton);

        final JButton createMaze = new JButton("Create Maze");
        createMaze.addActionListener(e -> new MazeCreator(Labirinto.this));
        createMaze.setBounds(10, 300, 150, 23);
        frmLabirinto.getContentPane().add(createMaze);

        leftButton = new JButton("<");
        downButton = new JButton("v");
        rightButton = new JButton(">");
        upButton = new JButton("^");

        //botao para a esquerda
        leftButton.addActionListener(e -> moveLeft());
        leftButton.setEnabled(false);
        leftButton.setBounds(10, 174, 60, 23);
        frmLabirinto.getContentPane().add(leftButton);

        //botao para a direita
        rightButton.addActionListener(e -> moveRight());
        rightButton.setEnabled(false);
        rightButton.setBounds(151, 174, 60, 23);
        frmLabirinto.getContentPane().add(rightButton);

        //botao para cima
        upButton.addActionListener(e -> moveUp());
        upButton.setEnabled(false);
        upButton.setBounds(81, 140, 60, 23);
        frmLabirinto.getContentPane().add(upButton);

        //botao para baixo
        downButton.addActionListener(e -> moveDown());
        downButton.setEnabled(false);
        downButton.setBounds(81, 174, 60, 23);
        frmLabirinto.getContentPane().add(downButton);


        comboBox = new JComboBox<>();//modo de jogo
        String[] dragon_species = new String[]{"Useless", "Sleepy", "Beast Mode"};
        DefaultComboBoxModel<String> dragons_species_box = new DefaultComboBoxModel<>(dragon_species);
        comboBox.setModel(dragons_species_box);
        comboBox.setBounds(129, 86, 99, 20);
        frmLabirinto.getContentPane().add(comboBox);

        JLabel lblNewLabel_1 = new JLabel("Dificulty");
        lblNewLabel_1.setBounds(10, 89, 109, 14);
        frmLabirinto.getContentPane().add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("No. of Dragons");
        lblNewLabel_2.setBounds(10, 64, 109, 14);
        frmLabirinto.getContentPane().add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Maze Size");
        lblNewLabel_3.setBounds(10, 39, 113, 14);
        frmLabirinto.getContentPane().add(lblNewLabel_3);

        //dimension = new JTextField();//dimension velha //dimensao do labirinto NxN
        //dimension.setText("11");
        //nova dimension usando JFormattedTextField para nao aceitar letras
        dimension = new JFormattedTextField(nFormat);
        dimension.setValue(nDim);//valor default
        dimension.addPropertyChangeListener("value", this);//muda o valor de nDim no momento em que se escreve
        dimension.setHorizontalAlignment(SwingConstants.RIGHT);
        dimension.setColumns(10);
        dimension.setBounds(129, 36, 99, 20);
        frmLabirinto.getContentPane().add(dimension);

        //nDragons = new JTextField();//nDragons velho //numero de dragoes presentes no labirinto
        //nDragons.setText("1");
        nDragons = new JFormattedTextField(nFormat);
        nDragons.setValue(nDra);//valor default
        nDragons.addPropertyChangeListener("value", this);//muda o valor de nDra no momento em que se escreve
        nDragons.setHorizontalAlignment(SwingConstants.RIGHT);
        nDragons.setColumns(10);
        nDragons.setBounds(129, 61, 99, 20);
        frmLabirinto.getContentPane().add(nDragons);

        JButton StartButton = new JButton("Start");
        StartButton.addActionListener(arg0 -> {
            //criar o maze
            try {
                int mSize = nDim;

                if (mSize > 49) {
                    JOptionPane.showMessageDialog(frmLabirinto, "Maximum size is 49");
                    dimension.setValue(49);
                    mSize = 49;
                } else if (mSize < 5) {
                    JOptionPane.showMessageDialog(frmLabirinto, "Minimum size is 5");
                    dimension.setValue(5);
                    mSize = 5;
                }

                if (mSize % 2 == 0) {
                    mSize++;
                    JOptionPane.showMessageDialog(frmLabirinto, "Must be an odd number.\n Size will be " + mSize);
                    dimension.setValue(mSize);
                }

                maze = new MazeBuilder(mSize);
            } catch (NumberFormatException e1) {
                JOptionPane.showMessageDialog(frmLabirinto, "Input must be a number");
                e1.printStackTrace();
            }

            h = new Hero(maze);
            s = new Sword(maze);
            d = new Dragon();

            String[] parts = nDragons.getText().split("\\.");
            int dNum = Integer.parseInt(parts[0]);

            if (dNum < 1) {
                JOptionPane.showMessageDialog(frmLabirinto, "There must be at least one dragon");
                nDragons.setValue(1);
                dNum = 1;
            }

            if (dNum > maze.getBlankSpaces() - 2) {
                JOptionPane.showMessageDialog(frmLabirinto, "There isn't enough space for all those dragons");
                dNum = maze.getBlankSpaces() - 2;
                nDragons.setValue(dNum);
            }

            d.multipleDragons(dNum, maze);

            gamePanel = new Panel();
            gamePanel.setBounds(238, 10, 400, 400);
            gamePanel.setVisible(true);
            gamePanel.setFocusable(true);
            gamePanel.addKeyListener(new KeyListener() {

                @Override
                public void keyPressed(KeyEvent e) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_LEFT:
                            moveLeft();
                            break;

                        case KeyEvent.VK_RIGHT:
                            moveRight();
                            break;

                        case KeyEvent.VK_UP:
                            moveUp();
                            break;

                        case KeyEvent.VK_DOWN:
                            moveDown();
                            break;
                        case KeyEvent.VK_ESCAPE:
                            System.exit(0);
                            break;
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                }

                @Override
                public void keyTyped(KeyEvent e) {
                }

            });
            frmLabirinto.getContentPane().add(gamePanel);
            gamePanel.requestFocus();

            convGameMode((String) comboBox.getSelectedItem());
            //tornar os botoes de direcao utilizaveis
            leftButton.setEnabled(true);
            downButton.setEnabled(true);
            rightButton.setEnabled(true);
            upButton.setEnabled(true);
            //atualizar o estado do jogo
            status.setText("A jogar...");
            //imprimir o labirinto na textArea
            //textArea.setText(maze.printMaze2String());
            ((Panel) gamePanel).setMaze(maze.getFullMaze());
            gamePanel.repaint();
        });
        StartButton.setBounds(10, 227, 89, 23);
        frmLabirinto.getContentPane().add(StartButton);
    }

    public void propertyChange(PropertyChangeEvent e) {//ativado quando muda o texto
        Object source = e.getSource();
        //getvalue retorna Object, preciso altera lo
        if (source == dimension)
            nDim = ((Number) dimension.getValue()).intValue();
        else if (source == nDragons)
            nDra = ((Number) nDragons.getValue()).intValue();
    }

}
