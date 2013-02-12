package view;

import components.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import components.GameComponents;
import controller.GameInstance;
/**
 * This class basically contains the game view.
 * 
 * @author
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
        //addMouseListener(this);
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
            setLayout(new GridLayout(game.gameConfig.NO_OF_COLS, game.gameConfig.NO_OF_ROWS));
            setMinimumSize(new Dimension(300, 600));
            setPreferredSize(new Dimension(300, 600));
            setBackground(Color.decode(game.gameConfig.SCORING_AREA_COLOR));
            
            ImageIcon icon = new ImageIcon("images/castle_blue_rank1.jpg");
            JLabel label = new JLabel(icon);
            scorePanel.add(label);
            
            
            
            scorePanel.setSize(600, 600);
            scorePanel.setBackground(Color.DARK_GRAY);
            add(scorePanel);
            add(goldAreaPanel);
            add(tileAreaPanel);
        }
    }
    
    class PlayerInfoArea extends JPanel {
   
		private static final long serialVersionUID = 1L;

		public PlayerInfoArea(){
		   int NO_OF_PLAYER = 4;
		   setLayout(new GridLayout(0,(NO_OF_PLAYER +1)));
           setMinimumSize(new Dimension(900, 200));
           setOpaque(true);
           setPreferredSize(new Dimension(900, 198));
           setBackground(Color.decode(Config.PLAYER_INFO_AREA_COLOR));
           JPanel settingsPanel = new JPanel();
           settingsPanel.setMinimumSize(new Dimension(180, 200));
           settingsPanel.setPreferredSize(new Dimension(180, 198));
           settingsPanel.setLayout(null);
           settingsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
	 	    
           JButton add = new JButton("ADD PLAYER");
           add.setBounds(5,5,160,40);
           settingsPanel.add(add);
	 	    
           JButton save = new JButton("SAVE GAME");
           save.setBounds(5,55,160,40);
           settingsPanel.add(save);
           add(settingsPanel);
           
           JButton load = new JButton("LOAD GAME");
	 	   load.setBounds(5,105,160,40);
	 	   settingsPanel.add(load);
	 	   
	 	   JButton exit = new JButton("  EXIT  ");
	 	   exit.setBounds(5,155,160,40);
	 	   settingsPanel.add(exit);
		  
	    
	 	   
	 	   
	 	   
	 	  JPanel[] player = new JPanel[NO_OF_PLAYER];
	 	  JLabel[] playerLabel = new JLabel[NO_OF_PLAYER];
	 	  JLabel[] rank1Label = new JLabel[NO_OF_PLAYER];
	 	  JLabel[] rank2Label = new JLabel[NO_OF_PLAYER];
	 	  JLabel[] rank3Label = new JLabel[NO_OF_PLAYER];
	 	  JLabel[] rank4Label = new JLabel[NO_OF_PLAYER];
	 	  JLabel[] coinsLabel = new JLabel[NO_OF_PLAYER];
	 	  JLabel[] rank1IconField = new JLabel[NO_OF_PLAYER];;
	 	  JLabel[] rank2IconField = new JLabel[NO_OF_PLAYER];
	 	  JLabel[] rank3IconField = new JLabel[NO_OF_PLAYER];
	 	  JLabel[] rank4IconField = new JLabel[NO_OF_PLAYER];
	 	  JLabel[] coinsField = new JLabel[NO_OF_PLAYER];
	 	  
			for (int i=0; i<NO_OF_PLAYER; i++){
				  
				   player[i] = new JPanel();
				   player[i].setMinimumSize(new Dimension(180, 190));
				   player[i].setPreferredSize(new Dimension(180, 190));
		 	       player[i].setBorder(BorderFactory.createLineBorder(Color.black));
		 	       player[i].setLayout(null);	
		 	       
			       
				   playerLabel[i] = new JLabel("PLAYER    "+(i+1), JLabel.CENTER);
				   playerLabel[i].setBounds(0, 5, 280, 20);
				   rank1Label[i] = new JLabel("TOTAL NO OF RANK1 CASTLE", JLabel.CENTER);
				   rank1Label[i].setBounds(0, 50, 130, 20);
				   rank2Label[i] = new JLabel("TOTAL NO OF RANK2 CASTLE ", JLabel.CENTER);
				   rank2Label[i].setBounds(0, 75, 180, 20);
				   rank3Label[i] = new JLabel("TOTAL NO OF RANK3 CASTLE ", JLabel.CENTER);
				   rank3Label[i].setBounds(0, 100, 180, 20);
				   rank4Label[i] = new JLabel("TOTAL NO OF RANK4 CASTLE ", JLabel.CENTER);
				   rank4Label[i].setBounds(0, 125, 180, 20);
				   coinsLabel[i] = new JLabel("TOTAL NO OF COINS", JLabel.CENTER);
				   coinsLabel[i].setBounds(0, 150, 180, 20);
				   rank1IconField[i] = new JLabel();
				   rank1IconField[i].setBounds(140, 50, 40, 40);
				   rank2IconField[i] = new JLabel();
				   rank2IconField[i].setBounds(200, 75, 30, 20);
				   rank3IconField[i] = new JLabel();
				   rank3IconField[i].setBounds(200, 100, 30, 20);
				   rank4IconField[i] = new JLabel();
				   rank4IconField[i].setBounds(200, 125, 30, 20);
				   coinsField[i] = new JLabel();
				   coinsField[i].setBounds(200, 150, 30, 20);
				   
				   this.add(player[i]);
				   player[i].add(playerLabel[i]);
				   player[i].add(rank1Label[i]);
				   player[i].add(rank2Label[i]);
				   player[i].add(rank3Label[i]);
				   player[i].add(rank4Label[i]);
				   player[i].add(coinsLabel[i]);
				   player[i].add(rank1IconField[i]);
				   player[i].add(rank2IconField[i]);
				   player[i].add(rank3IconField[i]);
				   player[i].add(rank4IconField[i]);
				   player[i].add(coinsField[i]);
			}   
		}
			
         
       
    }
} 