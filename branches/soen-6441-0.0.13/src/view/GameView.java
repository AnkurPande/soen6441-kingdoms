package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

import components.Castle;
import components.GameComponents;

import controller.GameInstance;

/**
 * This class basically contains the game view.
 * 
 * @author
 * 
 */

public class GameView extends JFrame implements MouseInputListener{
	 /* 
	 */
	private static final long serialVersionUID = 1L;
	private BoardArea board;
	private ScoringArea scoringArea;
	private PlayerInfoArea playerInfoArea;
	private JButton butCastleBlue; 
	private JButton butCastleGreen;
	GameComponents component;
	public List<Field> fields = new ArrayList<Field>();
	private GameInstance game;

	public GameInstance getGame() {
		return game;
	}

	public void setGame(GameInstance game) {
		this.game = game;
	}

	public GameView(GameInstance gi) {

		this.setGame(gi);

		// Initialize the three major areas of the game screen
		board = new BoardArea();
		scoringArea = new ScoringArea();
		playerInfoArea = new PlayerInfoArea();

		// Set the layout
		setLayout(new BorderLayout());
		add(scoringArea, BorderLayout.WEST);
		add(board, BorderLayout.CENTER);
		add(playerInfoArea, BorderLayout.SOUTH);
		
		setTitle("Kingdoms");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		pack();
		addMouseListener(this);
		butCastleBlue.setPreferredSize(new Dimension(60,50));
		
		butCastleBlue.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//butCastleBlue.setPreferredSize(new Dimension(60,50));
				//JOptionPane.showMessageDialog(null,"Bitch its button!");
				component = new Castle(GameInstance.PlayerColor.BLUE,Castle.CastleRank.ONE);
				butCastleGreen.setEnabled(false);
			}
		});
		butCastleGreen.setSize(new Dimension(60,50));
		butCastleGreen.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//JOptionPane.showMessageDialog(null,"Bitch its button!");
				component = new Castle(GameInstance.PlayerColor.GREEN,Castle.CastleRank.ONE);
				butCastleBlue.setEnabled(false);
			}
		});
	}

	/**
	 * This is main function. It returns arguments.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// new GameView();
			}
		});
	}
 
	/**
	 * 
	 * @author maharishi
	 * BoardArea extends JLayeredPane and it contains the List of fields.
	 * GridLayout is set in LayeredPane
	 */
	class BoardArea extends JLayeredPane {

		private static final long serialVersionUID = 1L;
		//fields is a type of List, single dimension
		

		public BoardArea() {
			super();
			GridLayout grid = new GridLayout(5, 6);
			this.setLayout(grid);
			this.setSize(new Dimension(600, 600));
			this.fillBoard();
			this.setVisible(true);
			
			 //fillBoardWithGameState();

		}

		private void fillBoard() {
			for (int i = 0; i < 30; i++) {
				Field f = new Field();
				f.id = i;
				if (i % 2 == 0)
					f.setBackground(Color.WHITE);
				else
					f.setBackground(Color.GRAY);
				fields.add(f);

				this.add(f);

			}
		}

		private void fillBoardWithGameState() {
			for (int i = 0; i < game.gameConfig.NO_OF_COLS; ++i) {
				for (int j = 0; j < game.gameConfig.NO_OF_ROWS; ++j) {
					if (game.gameBoard[i][j] != null) {
						//fields[i][j].drawCompononent(game.gameBoard[i][j]);
					}
				}
			}
		}

		
	}

	class Field extends JPanel {
		public int id;
		Field f;
		Field() {
			super();			
			this.setBorder(BorderFactory.createLineBorder(Color.black));
			this.setSize(100, 120);
			this.setVisible(true);
			f = this;
			this.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e){
					//Iterator i = fields.iterator();
						//while(i.hasNext()){
							//Field f = (Field)i.next();
							if(f.contains(e.getPoint()) && butCastleBlue.isEnabled()){
								f.setBackground(Color.BLUE);
								f.add(new Castle(GameInstance.PlayerColor.BLUE,Castle.CastleRank.ONE));
								
								}
							else if(f.contains(e.getPoint()) && butCastleGreen.isEnabled()){

								f.setBackground(Color.GREEN);
								f.add(new Castle(GameInstance.PlayerColor.GREEN,Castle.CastleRank.ONE));
								
							}
						//}
						butCastleGreen.setEnabled(true);					
						butCastleBlue.setEnabled(true);
					}				
			});
			
		}	
	}

	class ScoringArea extends JPanel {

		private static final long serialVersionUID = 1L;

		// Player score is displayed here.
		public ScoringArea() {
			super();
			butCastleBlue = new JButton(new ImageIcon("C:/Users/maharishi/workspace/concordia/soen-6441-kingdoms/images/castle_blue_rank1.jpg"));
			butCastleGreen = new JButton(new ImageIcon("C:/Users/maharishi/workspace/concordia/soen-6441-kingdoms/images/castle_green_rank1.jpg"));
			
			setLayout(new GridLayout(/*game.gameConfig.NO_OF_COLS,
					game.gameConfig.NO_OF_ROWS*/5,5));
			this.add(butCastleBlue);
			this.add(butCastleGreen);
			
			setMinimumSize(new Dimension(300, 600));
			setPreferredSize(new Dimension(300, 600));
			setBackground(Color.decode(game.gameConfig.SCORING_AREA_COLOR));
		}
	}

	class PlayerInfoArea extends JPanel {

		private static final long serialVersionUID = 1L;

		public PlayerInfoArea() {
			setLayout(new GridLayout(game.gameConfig.NO_OF_COLS,
					game.gameConfig.NO_OF_ROWS));
			setMinimumSize(new Dimension(900, 200));
			setPreferredSize(new Dimension(900, 200));
			setBackground(Color.decode(game.gameConfig.PLAYER_INFO_AREA_COLOR));

		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
