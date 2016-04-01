package maze.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import maze.logic.Dragon;
import maze.logic.Hero;
import maze.logic.MazeBuilder;
import maze.logic.Sword;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.Window.Type;

public class Labirinto {

	private JFrame frmLabirinto;
	private JTextField dimension;
	private JTextField nDragons;
	private JComboBox comboBox;
	private JPanel gamePanel;
	private JLabel status;
	private MazeBuilder maze; //o nosso maze. Ainda se tem que ver a privacidade
	private Hero h;
	private Dragon d;
	private Sword s;
	private int mode;

	private JButton leftButton;
	private JButton rightButton;
	private JButton upButton;
	private JButton downButton;


	public JPanel getGamePanel() {
		return gamePanel;
	}
	public void setGamePanel(JPanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	public JLabel getStatus() {
		return status;
	}
	public void setStatus(JLabel status) {
		this.status = status;
	}
	public MazeBuilder getMaze() {
		return maze;
	}
	public void setMaze(MazeBuilder maze) {
		this.maze = maze;
	}	
	public Hero getH() {
		return h;
	}
	public void setH(Hero h) {
		this.h = h;
	}	
	public JButton getLeftButton() {
		return leftButton;
	}
	public void setLeftButton(JButton leftButton) {
		this.leftButton = leftButton;
	}
	public JButton getRightButton() {
		return rightButton;
	}
	public void setRightButton(JButton rightButton) {
		this.rightButton = rightButton;
	}
	public JButton getUpButton() {
		return upButton;
	}
	public void setUpButton(JButton upButton) {
		this.upButton = upButton;
	}
	public JButton getDownButton() {
		return downButton;
	}
	public void setDownButton(JButton downButton) {
		this.downButton = downButton;
	}

	public void turn(int direcao){
		h.setDir(direcao);
		h.move(maze);
		if (!s.isCollected()){
			s.heroOverlap(h, maze);
		}
		for (int i = 0; i < Dragon.getDragons().size(); i++){
			if (Dragon.getDragons().get(i).isAlive()) {
				Dragon.getDragons().get(i).fight(h, maze);

				if (mode == 2 || mode == 3){
					if (mode == 2){
						Dragon.getDragons().get(i).move(maze);
					}
					if (mode == 3){
						Dragon.getDragons().get(i).moveOrSleep(maze);
					}
					Dragon.getDragons().get(i).fight(h, maze);
					s.dragonOverlap(Dragon.getDragons().get(i), maze);
				}
			}
		}
		
		if (!h.isAlive()) {
			System.out.println("Game Over");
			gamePanel.setFocusable(false);
		}
		if (h.isFinished()){
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
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Labirinto window = new Labirinto();
					window.frmLabirinto.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void convGameMode(){
		String s = (String) comboBox.getSelectedItem();

		switch (s){
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

	public void moveLeft(){
		status.setText("Moveu-se para a esquerda");
		turn(2);
		//textArea.setText(maze.printMaze2String());
		((Panel) gamePanel).setMaze(maze.getFullMaze());
		gamePanel.repaint();

		if (!h.isAlive() || h.isFinished()){
			maze.printMaze();
			leftButton.setEnabled(false);
			downButton.setEnabled(false);
			rightButton.setEnabled(false);
			upButton.setEnabled(false);

			if (!h.isAlive()){
				status.setText("Perdeu");
			}
			if (h.isFinished()){
				status.setText("Ganhou");
			}
		}
	}

	public void moveRight(){
		status.setText("Moveu-se para a direita");
		turn(3);
		//textArea.setText(maze.printMaze2String());
		((Panel) gamePanel).setMaze(maze.getFullMaze());
		gamePanel.repaint();

		if (!h.isAlive() || h.isFinished()){
			maze.printMaze();
			leftButton.setEnabled(false);
			downButton.setEnabled(false);
			rightButton.setEnabled(false);
			upButton.setEnabled(false);

			if (!h.isAlive()){
				status.setText("Perdeu");
			}
			if (h.isFinished()){
				status.setText("Ganhou");
			}
		}
	}

	public void moveUp(){
		status.setText("Moveu-se para cima");
		turn(0);
		//textArea.setText(maze.printMaze2String());
		((Panel) gamePanel).setMaze(maze.getFullMaze());
		gamePanel.repaint();

		if (!h.isAlive() || h.isFinished()){
			maze.printMaze();
			leftButton.setEnabled(false);
			downButton.setEnabled(false);
			rightButton.setEnabled(false);
			upButton.setEnabled(false);

			if (!h.isAlive()){
				status.setText("Perdeu");
			}
			if (h.isFinished()){
				status.setText("Ganhou");
			}
		}
	}

	public void moveDown(){
		status.setText("Moveu-se para baixo");
		turn(1);
		//textArea.setText(maze.printMaze2String());
		((Panel) gamePanel).setMaze(maze.getFullMaze());
		gamePanel.repaint();

		if (!h.isAlive() || h.isFinished()){
			maze.printMaze();
			leftButton.setEnabled(false);
			downButton.setEnabled(false);
			rightButton.setEnabled(false);
			upButton.setEnabled(false);

			if (!h.isAlive()){
				status.setText("Perdeu");
			}
			if (h.isFinished()){
				status.setText("Ganhou");
			}
		}
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLabirinto = new JFrame();
		frmLabirinto.setType(Type.UTILITY);
		frmLabirinto.setTitle("Settings");
		frmLabirinto.setBounds(100, 100, 650, 425);
		frmLabirinto.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLabirinto.getContentPane().setLayout(null);

		status = new JLabel("Gerar labirinto");//diz o estado atual do jogo
		status.setBounds(10, 400, 186, 20);
		frmLabirinto.getContentPane().add(status);

/*		final JTextArea textArea = new JTextArea();//onde se desenha o labirinto
		textArea.setEditable(false);
		textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
		textArea.setBounds(238, 36, 186, 214);
		frmLabirinto.getContentPane().add(textArea);
*/

		final JButton ExitButton = new JButton("Exit");//sair do programa, alternativa ao X da janela
		ExitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				status.setText("Adeus");//nao se ve, fecha logo
				System.exit(0);
			}
		});
		ExitButton.setBounds(109, 227, 89, 23);
		frmLabirinto.getContentPane().add(ExitButton);

		leftButton = new JButton("<");
		downButton = new JButton("v");
		rightButton = new JButton(">");
		upButton = new JButton("^");

		//botao para a esquerda
		leftButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveLeft();
			}
		});
		leftButton.setEnabled(false);
		leftButton.setBounds(10, 174, 60, 23);
		frmLabirinto.getContentPane().add(leftButton);

		//botao para a direita
		rightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveRight();
			}
		});
		rightButton.setEnabled(false);
		rightButton.setBounds(151, 174, 60, 23);
		frmLabirinto.getContentPane().add(rightButton);

		//botao para cima
		upButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveUp();
			}
		});
		upButton.setEnabled(false);
		upButton.setBounds(81, 140, 60, 23);
		frmLabirinto.getContentPane().add(upButton);

		//botao para baixo
		downButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveDown();
			}
		});
		downButton.setEnabled(false);
		downButton.setBounds(81, 174, 60, 23);
		frmLabirinto.getContentPane().add(downButton);


		comboBox = new JComboBox();//modo de jogo
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Useless", "Sleepy", "Beast Mode"}));
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

		dimension = new JTextField();//dimensao do labirinto NxN
		dimension.setText("11");
		dimension.setHorizontalAlignment(SwingConstants.RIGHT);
		dimension.setBounds(129, 36, 99, 20);
		frmLabirinto.getContentPane().add(dimension);
		dimension.setColumns(10);

		nDragons = new JTextField();//numero de dragoes presentes no labirinto
		nDragons.setHorizontalAlignment(SwingConstants.RIGHT);
		nDragons.setText("1");
		nDragons.setBounds(129, 61, 99, 20);
		frmLabirinto.getContentPane().add(nDragons);
		nDragons.setColumns(10);


		JButton StartButton = new JButton("Start");//=======================================
		StartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//criar o maze
				try {
					int mSize = Integer.parseInt(dimension.getText());
					
					if (mSize > 49){
						JOptionPane.showMessageDialog(frmLabirinto, "Max size is 49");
						dimension.setText("49");
						mSize = 49;
					}
					
					if (mSize < 5){
						JOptionPane.showMessageDialog(frmLabirinto, "Min size is 5");
						dimension.setText("5");
						mSize = 5;
					}
					
					if (mSize % 2 == 0){
						JOptionPane.showMessageDialog(frmLabirinto, 
								"Must be an odd number.\n Size will be " + ++mSize);
						dimension.setText(Integer.toString(mSize));
					}
					
					maze = new MazeBuilder(mSize);
					
					
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				}
				h = new Hero(maze);
				s = new Sword(maze);
				d = new Dragon();
				d.multipleDragons(Integer.parseInt(nDragons.getText()), maze);

				gamePanel = new Panel(/*maze*/);
				gamePanel.setBounds(238, 10, 400, 400);
				gamePanel.setVisible(true);
				gamePanel.setFocusable(true);
				gamePanel.addKeyListener(new KeyListener() {

					@Override
					public void keyPressed(KeyEvent e) {
						switch(e.getKeyCode()){
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
					public void keyReleased(KeyEvent e) {}

					@Override
					public void keyTyped(KeyEvent e) {}
					
				});
				frmLabirinto.getContentPane().add(gamePanel);
				gamePanel.requestFocus();

				convGameMode();
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
			}
		});
		StartButton.setBounds(10, 227, 89, 23);
		frmLabirinto.getContentPane().add(StartButton);
	}
}
