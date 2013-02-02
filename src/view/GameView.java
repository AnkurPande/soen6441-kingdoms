package view;

import gameinstance.GameInstance;
import gameinstance.GameInstance.Config;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Window;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import components.Castle;
import components.GameComponents;
import components.Tile;
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
    
    public GameView() {
    	
    	//Initialize the game instance
    	game = new GameInstance();
    	
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
                new GameView();
            }
        });
    }
    
    class BoardArea extends JPanel {
    	
		private static final long serialVersionUID = 1L;
		public Field[][] fields = new Field[Config.NO_OF_COLS][Config.NO_OF_ROWS];

        public BoardArea() {
            setLayout(new GridLayout(Config.NO_OF_COLS, Config.NO_OF_ROWS));
            setMinimumSize(new Dimension(600, 600));
            setPreferredSize(new Dimension(600, 600));
            setBackground(Color.decode(Config.BOARD_AREA_COLOR));
            fillBoard();
            fillBoardWithGameState();
        }
        
        private void fillBoard() {
            for (int i = 0; i < Config.NO_OF_COLS; ++i) {
                for (int j = 0; j < Config.NO_OF_ROWS; ++j) {
                    fields[i][j] = new Field(i, j);
                    add(fields[i][j]);
                }
            }
        }
        
        private void fillBoardWithGameState(){
        	for (int i = 0; i < Config.NO_OF_COLS; ++i) {
                for (int j = 0; j < Config.NO_OF_ROWS; ++j) {
                    if(game.gameBoard[i][j] != null){
                    	fields[i][j].drawCompononent(game.gameBoard[i][j]);
                    }
                }
            }
        }
    }
    
    
    class Field extends JLabel {

		private static final long serialVersionUID = 1L;
		private int x, y;
        public Field(int x, int y) {
            this.x = x;
            this.y = y;
            
            setOpaque(true);
            setMinimumSize(new Dimension(75, 75));
            setPreferredSize(new Dimension(75, 75));
            
            if ((x + y) % 2 == 0) {
                setBackground(Color.decode(Config.BOARD_AREA_FIELD_COLOR1));
            } else {
                setBackground(Color.decode(Config.BOARD_AREA_FIELD_COLOR2));
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
            setLayout(new GridLayout(Config.NO_OF_COLS, Config.NO_OF_ROWS));
            setMinimumSize(new Dimension(300, 600));
            setPreferredSize(new Dimension(300, 600));
            setBackground(Color.decode(Config.SCORING_AREA_COLOR));
            
        }
    }
    
    class PlayerInfoArea extends JPanel {
   
		private static final long serialVersionUID = 1L;

		public PlayerInfoArea() {
            setLayout(new GridLayout(Config.NO_OF_COLS, Config.NO_OF_ROWS));
            setMinimumSize(new Dimension(900, 200));
            setPreferredSize(new Dimension(900, 200));
            setBackground(Color.decode(Config.PLAYER_INFO_AREA_COLOR));
            
        }
    }
}
