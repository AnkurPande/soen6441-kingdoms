package view;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import components.GameComponents;
import controller.GameInstance;
/**
 * This class basically contains the game view.
 * 
 * @author Ankur
 *
 */


public class GameView extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BoardArea board;
    private ScoringArea scoringArea;
    private PlayerInfoArea playerInfoArea;
    
    private GameInstance game;
    
    public GameInstance getGame() {
		return game;
	}
	public void setGame(GameInstance game) {
		this.game = game;
	}
	public GameView(GameInstance gi) {
    	
		this.setGame(gi);
		
    	//Initialize the three major areas of the game screen
        board = new BoardArea();
        scoringArea = new ScoringArea();
        playerInfoArea = new PlayerInfoArea();
        
        //Set the layout
        setLayout(new BorderLayout());
        add(scoringArea, BorderLayout.WEST);
        add(board, BorderLayout.CENTER);
        add(playerInfoArea, BorderLayout.SOUTH);
        
        setTitle("Kingdoms");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }
    /**
     * This is main function.
     * It returns arguments.
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //new GameView();
            }
        });
    }
    
    class BoardArea extends JPanel {
    	
		private static final long serialVersionUID = 1L;
		public Field[][] fields = new Field[game.gameConfig.NO_OF_COLS][game.gameConfig.NO_OF_ROWS];

        public BoardArea() {
            setLayout(new GridLayout(game.gameConfig.NO_OF_COLS, game.gameConfig.NO_OF_ROWS));
            setMinimumSize(new Dimension(600, 600));
            setPreferredSize(new Dimension(600, 600));
            setBackground(Color.decode(game.gameConfig.BOARD_AREA_COLOR));
            fillBoard();
            fillBoardWithGameState();
        }
        
        private void fillBoard() {
            for (int i = 0; i < game.gameConfig.NO_OF_COLS; ++i) {
                for (int j = 0; j < game.gameConfig.NO_OF_ROWS; ++j) {
                    fields[i][j] = new Field(i, j);
                    add(fields[i][j]);
                }
            }
        }
        
        private void fillBoardWithGameState(){
        	for (int i = 0; i < game.gameConfig.NO_OF_COLS; ++i) {
                for (int j = 0; j < game.gameConfig.NO_OF_ROWS; ++j) {
                    if(game.gameBoard[i][j] != null){
                    	fields[i][j].drawCompononent(game.gameBoard[i][j]);
                    }
                }
            }
        }
    }
    
    
    class Field extends JLabel {

		private static final long serialVersionUID = 1L;
		private int x = 0, y = 0;
        public Field(int x, int y) {
            this.x = x;
            this.y = y;
            
            setOpaque(true);
            setMinimumSize(new Dimension(75, 75));
            setPreferredSize(new Dimension(75, 75));
            
            if ((x + y) % 2 == 0) {
                setBackground(Color.decode(game.gameConfig.BOARD_AREA_FIELD_COLOR1));
            } else {
                setBackground(Color.decode(game.gameConfig.BOARD_AREA_FIELD_COLOR2));
            }
        }
        
        private void drawCompononent(GameComponents gc){
        	
        	String iconFile = gc.displayIcon();
        	if(iconFile == gc.getClass().getName()){
        		this.setText(iconFile);
        	}
        	else{
        		ImageIcon icon = new ImageIcon(iconFile); 
        		this.setIcon(icon);
        	}
        	
        }
        
    }
    
    class ScoringArea extends JPanel {

		private static final long serialVersionUID = 1L;

		// Player score is displayed here.     
        public ScoringArea() {
            setLayout(new GridLayout(3,0));
            setMinimumSize(new Dimension(300, 600));
            setPreferredSize(new Dimension(300, 600));
            setBackground(Color.decode(game.gameConfig.SCORING_AREA_COLOR));
            JPanel scorePanel = new JPanel();
            JPanel goldareaPanel = new JPanel();
            JPanel tileareaPanel = new JPanel();
            
            ImageIcon icon = new ImageIcon("images/player score.png");
            ImageIcon icon2 = new ImageIcon("images/gold bank.png");
            
            JLabel scoreLable = new JLabel(icon);
            JLabel goldLabel = new JLabel(icon2);
            add(scorePanel);
            add(goldareaPanel);
            add(tileareaPanel);
            scorePanel.add(scoreLable);
            goldareaPanel.add(goldLabel);
            
            
            
        }
    }
    
    class PlayerInfoArea extends JPanel {
   
		private static final long serialVersionUID = 1L;

		public PlayerInfoArea() {
            setLayout(new GridLayout(game.gameConfig.NO_OF_COLS, game.gameConfig.NO_OF_ROWS));
            setMinimumSize(new Dimension(900, 200));
            setPreferredSize(new Dimension(900, 200));
            setBackground(Color.decode(game.gameConfig.PLAYER_INFO_AREA_COLOR));
            
        }
    }
}
