package gui;

import logic.Dragon;
import logic.Hero;
import logic.MazeBuilder;
import logic.Sword;

import javax.swing.*;
import java.awt.Window.Type;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

public class MazeCreator implements PropertyChangeListener {
    private JFrame editorFrame;

    private JRadioButton empty;
    private JRadioButton wall;
    private JRadioButton exit;
    private JRadioButton hero;
    private JRadioButton dragon;
    private JRadioButton sword;
    private JButton done;
    private JButton cancel;
    private JButton start;
    private JFormattedTextField dimension;
    private JComboBox<String> gameMode;
    private JLabel label;
    private JLabel modeLabel;
    private JPanel gamePanel;
    private MazeBuilder maze;

    private String selected = "Wall";
    private int mazeSize = 11;
    private boolean hasHero = false;
    private boolean hasExit = false;
    private boolean hasSword = false;

    private void initElements(Labirinto l) {
        for (int i = 1; i < maze.getSize() - 1; i++) {
            for (int j = 1; j < maze.getSize() - 1; j++) {
                if (maze.getMaze(i, j) == 'D') {
                    l.setD(new Dragon(i, j, maze));
                } else if (maze.getMaze(i, j) == 'H') {
                    l.setH(new Hero(i, j, maze));
                } else if (maze.getMaze(i, j) == 'E') {
                    l.setS(new Sword(i, j, maze));
                }
            }
        }
    }

    MazeCreator(final Labirinto l) {
        editorFrame = new JFrame();
        editorFrame.setType(Type.UTILITY);
        editorFrame.setTitle("Create Maze");
        editorFrame.setBounds(150, 150, 660, 440);
        editorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editorFrame.getContentPane().setLayout(null);
        editorFrame.setVisible(true);

        label = new JLabel("Maze Size:");
        label.setBounds(20, 20, 200, 20);
        editorFrame.getContentPane().add(label);

        NumberFormat nFormat = NumberFormat.getNumberInstance();
        dimension = new JFormattedTextField(nFormat);
        dimension.setValue(mazeSize);
        dimension.addPropertyChangeListener("value", this);
        dimension.setHorizontalAlignment(SwingConstants.RIGHT);
        dimension.setColumns(10);
        dimension.setBounds(140, 20, 100, 20);
        editorFrame.getContentPane().add(dimension);

        modeLabel = new JLabel("Game Mode:");
        modeLabel.setBounds(20, 50, 200, 20);
        editorFrame.getContentPane().add(modeLabel);

        gameMode = new JComboBox<>();
        String[] dragon_species = new String[]{"Useless", "Sleepy", "Beast Mode"};
        DefaultComboBoxModel<String> dragons_species_box = new DefaultComboBoxModel<>(dragon_species);
        gameMode.setModel(dragons_species_box);
        gameMode.setBounds(140, 50, 100, 20);
        editorFrame.getContentPane().add(gameMode);

        cancel = new JButton("Cancel");
        cancel.addActionListener(e -> editorFrame.dispose());
        cancel.setBounds(110, 227, 89, 23);
        editorFrame.getContentPane().add(cancel);

        start = new JButton("Start");
        start.addActionListener(arg0 -> {
            try {
                int mSize = mazeSize;

                if (mSize > 49) {
                    JOptionPane.showMessageDialog(editorFrame, "Maximum size is 49");
                    dimension.setValue(49);
                    mSize = 49;
                } else if (mSize < 5) {
                    JOptionPane.showMessageDialog(editorFrame, "Minimum size is 5");
                    dimension.setValue(5);
                    mSize = 5;
                }

                if (mSize % 2 == 0) {
                    mSize++;
                    JOptionPane.showMessageDialog(editorFrame, "Must be an odd number.\n Size will be " + mSize);
                    dimension.setValue(mSize);
                }
                maze = new MazeBuilder(mazeSize, 0);

                start.setEnabled(false);
                start.setVisible(false);
                gameMode.setEnabled(false);
                gameMode.setVisible(false);
                modeLabel.setEnabled(false);
                modeLabel.setVisible(false);

                editorFrame.getContentPane().remove(dimension);
                editorFrame.repaint();

                label.setBounds(20, 20, 200, 40);
                label.setText("<html>Choose what element<br>you want to add:</html>");
                editorFrame.getContentPane().add(label);

                cancel.setBounds(10, 250, 100, 40);
                editorFrame.getContentPane().add(cancel);

                empty = new JRadioButton("Empty Space");
                empty.setBounds(10, 70, 200, 20);
                editorFrame.getContentPane().add(empty);

                wall = new JRadioButton("Wall");
                wall.setSelected(true);
                wall.setBounds(10, 90, 100, 20);
                editorFrame.getContentPane().add(wall);

                exit = new JRadioButton("Exit");
                exit.setBounds(10, 110, 100, 20);
                editorFrame.getContentPane().add(exit);

                hero = new JRadioButton("Hero");
                hero.setBounds(10, 130, 100, 20);
                editorFrame.getContentPane().add(hero);

                dragon = new JRadioButton("Dragon");
                dragon.setBounds(10, 150, 100, 20);
                editorFrame.getContentPane().add(dragon);

                sword = new JRadioButton("Sword");
                sword.setBounds(10, 170, 100, 20);
                editorFrame.getContentPane().add(sword);

                empty.addItemListener(e -> selected = "Empty");

                wall.addItemListener(e -> selected = "Wall");

                exit.addItemListener(e -> selected = "Exit");

                hero.addItemListener(e -> selected = "Hero");

                dragon.addItemListener(e -> selected = "Dragon");

                sword.addItemListener(e -> selected = "Sword");

                ButtonGroup elements = new ButtonGroup();
                elements.add(empty);
                elements.add(wall);
                elements.add(exit);
                elements.add(hero);
                elements.add(dragon);
                elements.add(sword);

                done = new JButton("Done");
                done.addActionListener(e -> {
                    if (hasExit && hasHero && hasSword) {
                        l.setMaze(maze);
                        initElements(l);
                        l.setGamePanel(new Panel());
                        l.getGamePanel().setBounds(238, 10, 400, 400);
                        l.getGamePanel().setVisible(true);
                        l.getGamePanel().setFocusable(true);

                        l.getGamePanel().addKeyListener(new KeyListener() {

                            @Override
                            public void keyPressed(KeyEvent e) {
                                switch (e.getKeyCode()) {
                                    case KeyEvent.VK_LEFT:
                                        l.moveLeft();
                                        break;

                                    case KeyEvent.VK_RIGHT:
                                        l.moveRight();
                                        break;

                                    case KeyEvent.VK_UP:
                                        l.moveUp();
                                        break;

                                    case KeyEvent.VK_DOWN:
                                        l.moveDown();
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

                        l.getLabirintoFrame().getContentPane().add(l.getGamePanel());
                        l.getGamePanel().requestFocus();

                        l.convGameMode((String) gameMode.getSelectedItem());

                        l.getLeftButton().setEnabled(true);
                        l.getDownButton().setEnabled(true);
                        l.getRightButton().setEnabled(true);
                        l.getUpButton().setEnabled(true);

                        ((Panel) l.getGamePanel()).setMaze(maze.getFullMaze());
                        l.getGamePanel().repaint();
                        editorFrame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(editorFrame, "The maze must have exactly one exit, one sword and one hero");
                    }
                });
                done.setBounds(10, 200, 100, 40);
                editorFrame.getContentPane().add(done);

                gamePanel = new Panel();
                gamePanel.setBounds(250, 10, 400, 400);
                gamePanel.setVisible(true);
                gamePanel.setFocusable(true);
                gamePanel.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        int cell = 400 / maze.getSize();//57
                        int i = e.getX() / cell;
                        int j = e.getY() / cell;

                        if (i < maze.getSize() - 1 && i > 0 && j < maze.getSize() - 1 && j > 0) {
                            switch (selected) {
                                case "Wall":
                                    if (hasHero && maze.getMaze(j, i) == 'H') {
                                        hasHero = false;

                                    }
                                    if (hasSword && maze.getMaze(j, i) == 'E') {
                                        hasSword = false;
                                    }
                                    maze.setMaze(j, i, 'X');
                                    break;
                                case "Empty":
                                    if (hasHero && maze.getMaze(j, i) == 'H') {
                                        hasHero = false;

                                    }
                                    if (hasSword && maze.getMaze(j, i) == 'E') {
                                        hasSword = false;
                                    }
                                    maze.setMaze(j, i, ' ');
                                    break;
                                case "Hero":
                                    if (maze.getMaze(j - 1, i) != 'D'
                                            && maze.getMaze(j + 1, i) != 'D'
                                            && maze.getMaze(j, i - 1) != 'D'
                                            && maze.getMaze(j, i + 1) != 'D') {
                                        if (!hasHero) {
                                            if (hasSword && maze.getMaze(j, i) == 'E') {
                                                hasSword = false;
                                            }
                                            hasHero = true;
                                            maze.setMaze(j, i, 'H');
                                        }
                                    }
                                    break;
                                case "Dragon":
                                    if (maze.getMaze(j - 1, i) != 'H'
                                            && maze.getMaze(j + 1, i) != 'H'
                                            && maze.getMaze(j, i - 1) != 'H'
                                            && maze.getMaze(j, i + 1) != 'H') {
                                        if (hasHero && maze.getMaze(j, i) == 'H') {
                                            hasHero = false;

                                        }
                                        if (hasSword && maze.getMaze(j, i) == 'E') {
                                            hasSword = false;
                                        }
                                        maze.setMaze(j, i, 'D');
                                    }
                                    break;
                                case "Sword":
                                    if (!hasSword) {
                                        if (hasHero && maze.getMaze(j, i) == 'H') {
                                            hasHero = false;
                                        }
                                        hasSword = true;
                                        maze.setMaze(j, i, 'E');
                                    }
                                    break;
                                default:
                                    break;
                            }
                        } else if (i == 0 || i == maze.getSize() - 1 || j == 0 || j == maze.getSize() - 1) {
                            if (!((i == 0 && j == 0) || (i == maze.getSize() - 1 && j == maze.getSize() - 1) || (i == 0 && j == maze.getSize() - 1) || (i == maze.getSize() - 1 && j == 0))) {
                                switch (selected) {
                                    case "Wall":
                                        if (hasExit && maze.getMaze(j, i) == 'S') {
                                            hasExit = false;
                                            maze.setMaze(j, i, 'X');
                                        }
                                        break;
                                    case "Exit":
                                        if (!hasExit) {
                                            hasExit = true;
                                            maze.setMaze(j, i, 'S');
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }

                        gamePanel.repaint();

                    }

                    @Override
                    public void mouseEntered(MouseEvent arg0) {
                    }

                    @Override
                    public void mouseExited(MouseEvent arg0) {
                    }

                    @Override
                    public void mousePressed(MouseEvent arg0) {
                    }

                    @Override
                    public void mouseReleased(MouseEvent arg0) {
                    }

                });

                editorFrame.getContentPane().add(gamePanel);
                gamePanel.requestFocus();

                ((Panel) gamePanel).setMaze(maze.getFullMaze());
            } catch (NumberFormatException e1) {
                e1.printStackTrace();
            }
        });
        start.setBounds(10, 227, 89, 23);
        editorFrame.getContentPane().add(start);


    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Object source = evt.getSource();
        //getvalue retorna Object, preciso altera lo
        if (source == dimension)
            mazeSize = ((Number) dimension.getValue()).intValue();
    }

}
