package view;

import gameinstance.GameInstance;
import gameinstance.GameInstance.Config;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Window;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

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
        playerInfoArea.createPlayersPanel();
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
                    if(game.gameComponentsOnBoard[i][j] != null){
                    	fields[i][j].drawCompononent(game.gameComponentsOnBoard[i][j]);
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
           setLayout(new GridLayout(0,5));
           setMinimumSize(new Dimension(900, 200));
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
			
        }
		public  void createPlayersPanel(){
			
	 	    JPanel[] player = new JPanel[4];
	 	    JLabel[] label = new JLabel[4];
			for (int i=0; i<=3; i++){
				   player[i] = new JPanel();
				   label[i] = new JLabel("        PLAYER  "+(i+1));
				   player[i].setMinimumSize(new Dimension(180, 190));
				   player[i].setPreferredSize(new Dimension(180, 190));
		 	       player[i].setBorder(BorderFactory.createLineBorder(Color.black));
		 	       player[i].setLayout(null);
		 	       label[i].setBounds(0, 0, 280, 40);
		 	       label[i].setOpaque(true);
		 	       
		 	       label[i].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		 	       this.add(player[i]);
		 	       player[i].add(label[i]);
		 	       
		 	       
			}
		}
		
    }
}
