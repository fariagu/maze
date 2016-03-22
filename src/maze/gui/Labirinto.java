package maze.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
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
import java.awt.event.ActionEvent;
import java.awt.Window.Type;

public class Labirinto {

	private JFrame frmLabirinto;
	private JTextField dimension;
	private JTextField numberofdragon;
	private JComboBox comboBox;
	private JPanel gamePanel;
	public MazeBuilder maze; //o nosso maze. Ainda se tem que ver a privacidade
	public Hero h;
	public Dragon d;
	public Sword s;
	public int mode;

	private void turno(int direcao){
		h.setDir(direcao);
		h.move(maze);
		if (!s.isCollected()){
			s.heroOverlap(h, maze);
		}
		for (int i = 0; i < d.getDragons().size(); i++){
			if (d.getDragons().get(i).isAlive()) {
				d.getDragons().get(i).fight(h, maze);

				if (mode == 2 || mode == 3){
					if (mode == 2){
						d.getDragons().get(i).move(maze);
					}
					if (mode == 3){
						d.getDragons().get(i).moveOrSleep(maze);
					}
					s.dragonOverlap(d.getDragons().get(i), maze);
					d.getDragons().get(i).fight(h, maze);
				}
			}
		}

		if (!h.isAlive()) {
			maze.printMaze();
			System.out.println("Game Over");
		}
		if (h.isFinished()){
			maze.printMaze();
			System.out.println("Success!");
		}
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
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLabirinto = new JFrame();
		frmLabirinto.setType(Type.UTILITY);
		frmLabirinto.setTitle("Labirinto");
		frmLabirinto.setBounds(100, 100, 450, 300);
		frmLabirinto.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLabirinto.getContentPane().setLayout(null);

		final JLabel Status = new JLabel("Gerar labirinto");//diz o estado atual do jogo
		Status.setBounds(238, 11, 186, 14);
		frmLabirinto.getContentPane().add(Status);

/*		final JTextArea textArea = new JTextArea();//onde se desenha o labirinto
		textArea.setEditable(false);
		textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
		textArea.setBounds(238, 36, 186, 214);
		frmLabirinto.getContentPane().add(textArea);
*/
		
		
		final JButton ExitButton = new JButton("Exit");//sair do programa, alternativa ao X da janela
		ExitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Status.setText("Adeus");//nao se ve, fecha logo
				System.exit(0);
			}
		});
		ExitButton.setBounds(109, 227, 89, 23);
		frmLabirinto.getContentPane().add(ExitButton);

		final JButton LeftButton = new JButton("<");
		final JButton DownButton = new JButton("v");
		final JButton RightButton = new JButton(">");
		final JButton UpButton = new JButton("^");

		//botao para a esquerda
		LeftButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Status.setText("Moveu-se para a esquerda");
				turno(2);
				//textArea.setText(maze.printMaze2String());
				((Panel) gamePanel).setMaze(maze.getFullMaze());
				gamePanel.repaint();
				if (!h.isAlive()) {
					maze.printMaze();
					Status.setText("Perdeu");
					LeftButton.setEnabled(false);
					DownButton.setEnabled(false);
					RightButton.setEnabled(false);
					UpButton.setEnabled(false);
				}
				if (h.isFinished()){
					maze.printMaze();
					Status.setText("Ganhou");
					LeftButton.setEnabled(false);
					DownButton.setEnabled(false);
					RightButton.setEnabled(false);
					UpButton.setEnabled(false);
				}
			}
		});
		LeftButton.setEnabled(false);
		LeftButton.setBounds(10, 174, 60, 23);
		frmLabirinto.getContentPane().add(LeftButton);

		//botao para baixo
		DownButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Status.setText("Moveu-se para baixo");
				turno(1);
				//textArea.setText(maze.printMaze2String());
				((Panel) gamePanel).setMaze(maze.getFullMaze());
				gamePanel.repaint();
				if (!h.isAlive()) {
					maze.printMaze();
					Status.setText("Perdeu");
					LeftButton.setEnabled(false);
					DownButton.setEnabled(false);
					RightButton.setEnabled(false);
					UpButton.setEnabled(false);
				}
				if (h.isFinished()){
					maze.printMaze();
					Status.setText("Ganhou");
					LeftButton.setEnabled(false);
					DownButton.setEnabled(false);
					RightButton.setEnabled(false);
					UpButton.setEnabled(false);
				}
			}
		});
		DownButton.setEnabled(false);
		DownButton.setBounds(81, 174, 60, 23);
		frmLabirinto.getContentPane().add(DownButton);

		//botao para a direita
		RightButton.setEnabled(false);
		RightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Status.setText("Moveu-se para a direita");
				turno(3);
				//textArea.setText(maze.printMaze2String());
				((Panel) gamePanel).setMaze(maze.getFullMaze());
				gamePanel.repaint();
				if (!h.isAlive()) {
					maze.printMaze();
					Status.setText("Perdeu");
					LeftButton.setEnabled(false);
					DownButton.setEnabled(false);
					RightButton.setEnabled(false);
					UpButton.setEnabled(false);
				}
				if (h.isFinished()){
					maze.printMaze();
					Status.setText("Ganhou");
					LeftButton.setEnabled(false);
					DownButton.setEnabled(false);
					RightButton.setEnabled(false);
					UpButton.setEnabled(false);
				}

			}
		});
		RightButton.setBounds(151, 174, 60, 23);
		frmLabirinto.getContentPane().add(RightButton);

		//botao para cima
		UpButton.setEnabled(false);
		UpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Status.setText("Moveu-se para cima");
				turno(0);
				//textArea.setText(maze.printMaze2String());
				((Panel) gamePanel).setMaze(maze.getFullMaze());
				gamePanel.repaint();
				if (!h.isAlive()) {
					maze.printMaze();
					Status.setText("Perdeu");
					LeftButton.setEnabled(false);
					DownButton.setEnabled(false);
					RightButton.setEnabled(false);
					UpButton.setEnabled(false);
				}
				if (h.isFinished()){
					maze.printMaze();
					Status.setText("Ganhou");
					LeftButton.setEnabled(false);
					DownButton.setEnabled(false);
					RightButton.setEnabled(false);
					UpButton.setEnabled(false);
				}

			}
		});
		UpButton.setBounds(81, 140, 60, 23);
		frmLabirinto.getContentPane().add(UpButton);


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

		dimension = new JTextField();//dimens�o do labirinto NxN
		dimension.setText("11");
		dimension.setHorizontalAlignment(SwingConstants.RIGHT);
		dimension.setBounds(129, 36, 99, 20);
		frmLabirinto.getContentPane().add(dimension);
		dimension.setColumns(10);

		numberofdragon = new JTextField();//numero de drag�es presentes no labirinto
		numberofdragon.setHorizontalAlignment(SwingConstants.RIGHT);
		numberofdragon.setText("1");
		numberofdragon.setBounds(129, 61, 99, 20);
		frmLabirinto.getContentPane().add(numberofdragon);
		numberofdragon.setColumns(10);

		JButton StartButton = new JButton("Start");
		StartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//criar o maze
				maze = new MazeBuilder(Integer.parseInt(dimension.getText()));
				h = new Hero(maze);
				s = new Sword(maze);
				d = new Dragon();
				d.multipleDragons(Integer.parseInt(numberofdragon.getText()), maze);
				
				gamePanel = new Panel(maze);
				gamePanel.setBounds(238, 36, 195, 214);
				gamePanel.setVisible(true);
				frmLabirinto.getContentPane().add(gamePanel);
				
				convGameMode();
				//tornar os botoes de direcao utilizaveis
				LeftButton.setEnabled(true);
				DownButton.setEnabled(true);
				RightButton.setEnabled(true);
				UpButton.setEnabled(true);
				//atualizar o estado do jogo
				Status.setText("A jogar...");
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
