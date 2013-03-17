package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;

import model.Config;
import model.GameInstance;
import model.GameInstance.PlayerColor;

import components.Castle;
import components.Castle.CastleRank;
import components.GameComponents;
import components.Tile;
import components.Tile.TileType;

/**
 * The GameView class displays the game on the screen in graphical format.
 * This class can render a GameInstance object and create elements on the screen accordingly.
 * @author Team B
 *
 */
public class GameView extends JFrame implements PropertyChangeListener {

	private static final long serialVersionUID = 1L;
	private BoardArea board;
    private ScoringArea scoringArea;
    private PlayerInfoArea playerInfoArea;
    private GameInfoArea gameInfoArea;
    
    private final int NO_OF_COLS, NO_OF_ROWS;
    
    private GameInstance game;
    private Config gameConfig;
    
    public String statusDisplayFreq = "";
	
    /**
     * Constructor. Takes a GameInstance as a parameter and renders the game according to the this GameInsance.
     * @param gi The GameInstance to render.
     */
	public GameView(GameInstance gi) {
    	
		this.setGame(gi);
		
		NO_OF_COLS = game.gameBoard.length;
		NO_OF_ROWS = game.gameBoard[0].length;
		
		initGameWindow();
        
        setTitle("Kingdoms");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        pack();
        
        gi.addChangeListener(this);
    }
	
	/**
	 * Constructor. Takes a GameInstance as a parameter and renders the game according to the this GameInsance.
	 * @param gi The GameInstance to render.
	 * @param statusDisplayFreq The frequency to display the game status.
	 */
	public GameView(GameInstance gi, String statusDisplayFreq){
		
		this.setGame(gi);
		
		NO_OF_COLS = game.gameBoard.length;
		NO_OF_ROWS = game.gameBoard[0].length;
		
		initGameWindow();
        
        setTitle("Kingdoms");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        pack();
        
        gi.addChangeListener(this);
        
        this.statusDisplayFreq = statusDisplayFreq;
	}
	
	/**
	 * Initializes the game window (the javax.swing components)
	 */
	private void initGameWindow(){
		//Initialize the three major areas of the game screen
        board = new BoardArea();
        scoringArea = new ScoringArea();
        playerInfoArea = new PlayerInfoArea();
        gameInfoArea = new GameInfoArea();
        
        //Set the layout
        setLayout(new BorderLayout());
        add(scoringArea, BorderLayout.EAST);
        add(board, BorderLayout.CENTER);
        add(playerInfoArea, BorderLayout.SOUTH);
        add(gameInfoArea, BorderLayout.WEST);
	}
	
	/**
	 * Gets the current GameInstace of the view.
	 * @return The current GameInstace of the view.
	 */
	public GameInstance getGame() {
		return game;
	}
    
	/**
	 * Sets the current GameInstance to another GameInstance as desired. 
	 * @param game The new GameInstance object that will become the views current GameInstance.
	 */
	public void setGame(GameInstance game) {
		this.game = game;
		this.gameConfig= game.getGameConfig();
	}
    
	/**
	 * The listener method for the observable property changes of the game instance. 
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		
//		System.out.println("Changed property: " + event.getPropertyName() + " old:"
//				+ event.getOldValue() + " new: " + event.getNewValue());
//		
		String changedPropertyName = event.getPropertyName();
		
		if(this.statusDisplayFreq == "EveryTurn" && changedPropertyName == "turnCounter"){
			displayGameStatus();
		}
		
		if(this.statusDisplayFreq == "EveryRound" && changedPropertyName == "roundCounter"){
			displayGameStatus();
		}
		
		if(this.statusDisplayFreq == "EveryEpoch" && changedPropertyName == "epochCounter"){
			displayGameStatus();
		}
		
		if(this.statusDisplayFreq == "GameEnd" && changedPropertyName == "gameEndStatus"){
			displayGameStatus();
		}

		try {
			Thread.sleep(500);
		} catch (InterruptedException ie) {
			//Handle exception
		}
		
		this.board = new BoardArea();
		add(board, BorderLayout.CENTER);
		
		this.gameInfoArea = new GameInfoArea();
		add(gameInfoArea, BorderLayout.WEST);
		
		this.pack();
	}
	
	/**
	 * Displays the status of the game.
	 */
    private void displayGameStatus() {
    	System.out.println("-----------------------------------");
    	System.out.println("Displaying Game Status.");
    	System.out.println("-----------------------------------");
    	
    	displayGameBoardStatus();
    	displayPlayerStatus();
    	System.out.println("Turn:" + game.getTurnCounter() + " | Round :" + game.getRoundCounter());
    	
    	System.out.println("-----------------------------------");
    	System.out.println("Finished Displaying Game Status.");
    	System.out.println("-----------------------------------");
	}
    
    /**
     * Displays the status of the players.
     */
    private void displayPlayerStatus() {
    	System.out.println("-----------------------------------");
    	System.out.println("Displaying Player Status.");
    	System.out.println("-----------------------------------");
    	
		for(int i = 0; i < game.players.length ; i++){
			System.out.println(game.players[i].getStatusDescription());
		}
	}
    
    /**
     * Displays the status of the game board.
     */
	private void displayGameBoardStatus(){
    	System.out.println("Displaying Board Status.");
    	System.out.println("-----------------------------------");
    	for (int i = 0; i < NO_OF_COLS; ++i) {
            for (int j = 0; j < NO_OF_ROWS; ++j) {
            	System.out.print("ROW-" + j + " COL-" + i + ":");           	
            	System.out.print(game.gameBoard[i][j].description());
            	System.out.print(" | ");
            
            }
            
            System.out.println();
        }
    }

	/**
     * This class defines the board area of the game.
     * This is the 5 by 6 playing area of the game window.
     *
     */
    private class BoardArea extends JPanel {
    	MyListener listener = new MyListener();
		private static final long serialVersionUID = 1L;
		
		public Field[][] fields = new Field[NO_OF_COLS][NO_OF_ROWS];
		
		/**
		 * Constructor.
		 */
        public BoardArea() {
            setLayout(new GridLayout(NO_OF_COLS, NO_OF_ROWS));
            setMinimumSize(new Dimension(600, 600));
            setPreferredSize(new Dimension(600, 600));
            setBackground(Color.decode(gameConfig.BOARD_AREA_COLOR));
            fillBoard();
            fillBoardWithGameState();
        }
        
        /**
         * Method to initialize the board.
         */
        private void fillBoard() {
            for (int i = 0; i < NO_OF_COLS; ++i) {
                for (int j = 0; j < NO_OF_ROWS; ++j) {
                    fields[i][j] = new Field(i, j);
                    fields[i][j].setTransferHandler(new TransferHandler("icon"));
                    fields[i][j].addMouseListener(listener);
                    add(fields[i][j]);
                }
            }
        }
        
        /**
         * Method to load the game board with game components.
         */
        private void fillBoardWithGameState(){
        	for (int i = 0; i < NO_OF_COLS; ++i) {
                for (int j = 0; j < NO_OF_ROWS; ++j) {
                    if(game.gameBoard[i][j] != null){
                    	fields[i][j].drawCompononent(game.gameBoard[i][j]);
                    }
                }
            }
        }
    }
    
    /**
     * This class is to represent each field or place on the game board.
     * Each square place of the game board is a "Field"
     *
     */
    class Field extends JLabel implements MouseListener{

		private static final long serialVersionUID = 1L;
		private int x = 0, y = 0;
        public Field(int x, int y) {
            this.x = x;
            this.y = y;
            
            setOpaque(true);
            setMinimumSize(new Dimension(75, 75));
            setPreferredSize(new Dimension(75, 75));
            
            if ((x + y) % 2 == 0) {
                setBackground(Color.decode(gameConfig.BOARD_AREA_FIELD_COLOR1));
            } else {
                setBackground(Color.decode(gameConfig.BOARD_AREA_FIELD_COLOR2));
            }
        }
        
        /**
         * Method to draw the image icon of a game component on screen.
         * 
         * @param gc The game component whose icon file is to be drawn on screen.
         */
        private void drawCompononent(GameComponents gc){
        	
        	String iconFile = gc.displayIcon();
        	if(iconFile == gc.getClass().getName()){

        		if(gc instanceof components.Castle){
        			iconFile = calculateCastleIconFileName(gc);
        		}
        		
        		if(gc instanceof components.Tile){
        			iconFile = calculateTileIconFileName(gc);
        		}

        		if(gc instanceof components.Castle){
        			iconFile = calculateCastleIconFileName(gc);
        		}
        		
        		if(gc instanceof components.Tile){
        			iconFile = calculateTileIconFileName(gc);
        		}

        	}
        	
        	ImageIcon icon = new ImageIcon(iconFile); 
    		this.setIcon(icon);
        	
        }

        /**
         * Calculates the icon file name for castles with specific naming conventions.
         * @param gc The game component (castle) whose icon file is to be determined.
         * @return Returns the icon file name for the castle.
         */
        private String calculateCastleIconFileName(GameComponents gc){
        	Castle castle = (Castle)gc;
			PlayerColor color = castle.getColor();
			CastleRank rank = castle.getRank();
			
			String iconFileName = "images/castle_" + color + "_rank_" + rank + ".png";
			return iconFileName.toLowerCase();
        }
        
        /**
         * Calculates the icon file name for tiles with specific naming conventions.
         * @param gc The game component (tile) whose icon file is to be determined.
         * @return Returns the icon file name for the tile.
         */
        private String calculateTileIconFileName(GameComponents gc){
        	Tile tile = (Tile)gc;
			TileType type = tile.getType();
			int value = Math.abs(tile.getValue());
			
			String iconFileName = "";
			if(type == TileType.RESOURCES || type == TileType.HAZARD){
				iconFileName = "images/" + type + "_" + value + ".png";
			}
			else{
				iconFileName = "images/" + type + ".png";
			}
			
			return iconFileName.toLowerCase();
        }

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		/**
		 * Method to handle drag and drop events.
		 */
		public void mouseReleased(MouseEvent evt) {
			// TODO Auto-generated method stub
			JComponent comp = (JComponent) evt.getSource();
	        TransferHandler th = comp.getTransferHandler();
	        th.exportAsDrag(comp, evt, TransferHandler.COPY);
		}
        
    }
    
    /**
     * This class holds the scoring area of the game screen.
     *
     */
    class ScoringArea extends JPanel {

		private static final long serialVersionUID = 1L;

		/**
		 * Constructor. The scores of the players is to be displayed here.     
		 */
        public ScoringArea() {
            setLayout(new GridLayout(NO_OF_COLS, NO_OF_ROWS));
            setMinimumSize(new Dimension(0, 600));
            setPreferredSize(new Dimension(0, 600));
            setBackground(Color.decode(gameConfig.SCORING_AREA_COLOR));
        }
    }
    
    /**
     * This class holds the player information panel - i.e. the list of players and their resources, etc.
     *
     */
    class PlayerInfoArea extends JPanel {
   
		private static final long serialVersionUID = 1L;
		
		/**
		 * Constructor.		   
		 */
		public PlayerInfoArea(){
			
			MyListener listener = new MyListener();
			final int NO_OF_PLAYER = game.players.length;
			
			setLayout(new GridLayout(0,(NO_OF_PLAYER +1)));
			setMinimumSize(new Dimension(900, 200));
			setOpaque(true);
			setPreferredSize(new Dimension(900, 198));
			setBackground(Color.decode(gameConfig.PLAYER_INFO_AREA_COLOR));

			JPanel settingsPanel = new JPanel();
			settingsPanel.setMinimumSize(new Dimension(180, 200));
			settingsPanel.setPreferredSize(new Dimension(180, 198));
			settingsPanel.setLayout(null);
			settingsPanel.setBorder(BorderFactory.createLineBorder(Color.black));

			JButton add = new JButton("ADD PLAYER");
			add.setBounds(5,5,160,40);
			settingsPanel.add(add);
			add.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {

				}
			});

			JButton save = new JButton("SAVE GAME");
			save.setBounds(5,55,160,40);
			settingsPanel.add(save);
			save.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {

				}
			});

			JButton load = new JButton("LOAD GAME");
			load.setBounds(5,105,160,40);
			load.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {

				}
			});
			
			settingsPanel.add(load);
			JButton exit = new JButton("  EXIT  ");
			exit.setBounds(5,155,160,40);
			settingsPanel.add(exit);
			exit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					System.exit(EXIT_ON_CLOSE);
				}
			});
			
			add(settingsPanel);

			ImageIcon icon = new ImageIcon();
			String path = "";
			JPanel[] player = new JPanel[NO_OF_PLAYER];
			JLabel[] playerName = new JLabel[NO_OF_PLAYER];
			
			JLabel[] rank1Label = new JLabel[NO_OF_PLAYER];
			JLabel[] rank2Label = new JLabel[NO_OF_PLAYER];
			JLabel[] rank3Label = new JLabel[NO_OF_PLAYER];
			JLabel[] rank4Label = new JLabel[NO_OF_PLAYER];
			JLabel[] coinsLabel = new JLabel[NO_OF_PLAYER];
			JLabel[] tileLabel = new JLabel[NO_OF_PLAYER];
			
			JLabel[] noOfRank1Castles = new JLabel[NO_OF_PLAYER];
			JLabel[] noOfRank2Castles = new JLabel[NO_OF_PLAYER];
			JLabel[] noOfRank3Castles = new JLabel[NO_OF_PLAYER];
			JLabel[] noOfRank4Castles = new JLabel[NO_OF_PLAYER];
			
			JLabel[] noOfCoins = new JLabel[NO_OF_PLAYER];
			JLabel[] rank1Icon = new JLabel[NO_OF_PLAYER];
			JLabel[] rank2Icon = new JLabel[NO_OF_PLAYER];
			JLabel[] rank3Icon = new JLabel[NO_OF_PLAYER];
			JLabel[] rank4Icon = new JLabel[NO_OF_PLAYER];
			JLabel[] coinsIcon = new JLabel[NO_OF_PLAYER];
			JLabel[] playerTiles = new JLabel[NO_OF_PLAYER];
			
			for (int i = 0; i < NO_OF_PLAYER; i++){

				player[i] = new JPanel();
				player[i].setMinimumSize(new Dimension(180, 190));
				player[i].setPreferredSize(new Dimension(180, 190));
				player[i].setBorder(BorderFactory.createLineBorder(Color.black));
				player[i].setLayout(null);	

				String playerNames = game.players[i].getName() + " :" + game.players[i].getPlayerColor();
				playerName[i] = new JLabel(playerNames,JLabel.CENTER);
				playerName[i].setBounds(10, 5, 120, 20);

				rank1Label[i] = new JLabel("Rank 1 Castles:", JLabel.CENTER);
				rank1Label[i].setBounds(10, 50, 140, 20);
				rank2Label[i] = new JLabel("Rank 2 Castles:", JLabel.CENTER);
				rank2Label[i].setBounds(10, 75, 140, 20);
				rank3Label[i] = new JLabel("Rank 3 Castles:", JLabel.CENTER);
				rank3Label[i].setBounds(10, 100, 140, 20);
				rank4Label[i] = new JLabel("Rank 4 Castles:", JLabel.CENTER);
				rank4Label[i].setBounds(10, 125, 140, 20);
				coinsLabel[i] = new JLabel("Value of Coins:", JLabel.CENTER);
				coinsLabel[i].setBounds(15, 150, 140, 20);
				tileLabel[i] = new JLabel("Player Tiles:", JLabel.CENTER);
				tileLabel[i].setBounds(15, 175, 140, 20);

				noOfRank1Castles[i] = new JLabel(Integer.toString(game.players[i].rank1Castles.size()));
				noOfRank1Castles[i].setBounds(160, 50, 20, 20);
				
				noOfRank2Castles[i] = new JLabel(Integer.toString(game.players[i].rank2Castles.size()));
				noOfRank2Castles[i].setBounds(160, 75, 20, 20);
				
				noOfRank3Castles[i] = new JLabel(Integer.toString(game.players[i].rank3Castles.size()));
				noOfRank3Castles[i].setBounds(160, 100, 20, 20);
				
				noOfRank4Castles[i] = new JLabel(Integer.toString(game.players[i].rank4Castles.size()));
				noOfRank4Castles[i].setBounds(160, 125, 20, 20);
				
				noOfCoins[i] = new JLabel(Integer.toString(game.players[i].playerCoins.firstElement().getValue()));
				noOfCoins[i].setBounds(160, 150, 20, 20);
				
				String playerTileString = "";
				for(int j = 0 ; j < game.players[i].playerTiles.size() ; j++){
					playerTileString += game.players[i].playerTiles.get(j).getType().toString();
					if(j > 0){
						playerTileString += ",";
					}
				}
				
				if(playerTileString == ""){
					playerTileString = "None/On Board";
				}
				
				playerTiles[i] = new JLabel(playerTileString);
				playerTiles[i].setBounds(160, 175, 100, 20);

//				path = game.players[i].rank1Castles.firstElement().displayIcon();
				icon = new ImageIcon(path);
				rank1Icon[i] = new JLabel(icon);
				rank1Icon[i].setBounds(185, 50, 30, 20);

//				path = game.players[i].rank2Castles.firstElement().displayIcon();
				icon = new ImageIcon(path);
				rank2Icon[i] = new JLabel(icon);
				rank2Icon[i].setBounds(185, 75, 30, 20);

//				path = game.players[i].rank3Castles.firstElement().displayIcon();
				icon = new ImageIcon(path);
				rank3Icon[i] = new JLabel(icon);
				rank3Icon[i].setBounds(185, 100, 30, 20);


//				path = game.players[i].rank4Castles.firstElement().displayIcon();
				icon = new ImageIcon(path);
				rank4Icon[i] = new JLabel(icon);
				rank4Icon[i].setBounds(185, 125, 30, 20);
				coinsIcon[i] = new JLabel();
				coinsIcon[i].setBounds(185, 150, 20, 20);	

				rank1Icon[i].setTransferHandler(new TransferHandler("icon"));
				rank2Icon[i].setTransferHandler(new TransferHandler("icon"));
				rank3Icon[i].setTransferHandler(new TransferHandler("icon"));
				rank4Icon[i].setTransferHandler(new TransferHandler("icon"));

				rank1Icon[i].addMouseListener(listener);
				rank2Icon[i].addMouseListener(listener);
				rank3Icon[i].addMouseListener(listener);
				rank4Icon[i].addMouseListener(listener);
				
				add(player[i]);
				player[i].add(playerName[i]);
				player[i].add(rank1Label[i]);
				player[i].add(rank2Label[i]);
				player[i].add(rank3Label[i]);
				player[i].add(rank4Label[i]);
				player[i].add(coinsLabel[i]);
				
				player[i].add(noOfRank1Castles[i]);
				player[i].add(noOfRank2Castles[i]);
				player[i].add(noOfRank3Castles[i]);
				player[i].add(noOfRank4Castles[i]);
				
				player[i].add(noOfCoins[i]);
				player[i].add(rank1Icon[i]);
				player[i].add(rank2Icon[i]);
				player[i].add(rank3Icon[i]);
				player[i].add(rank4Icon[i]);
				player[i].add(coinsIcon[i]);
				player[i].add(tileLabel[i]);
				player[i].add(playerTiles[i]);
				
			} 
		 	   
		}
		
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
    }
    
    /**
     * This class holds the game information panel - i.e. the current epoch no, whose turn it is, etc.
     *
     */
    class GameInfoArea extends JPanel {
		
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor.		   
		 */
		public GameInfoArea(){
			setLayout(new GridLayout(0,1));
			setMinimumSize(new Dimension(300, 600));
			setOpaque(true);
			setPreferredSize(new Dimension(300, 600));
			setBackground(Color.decode(gameConfig.GAME_INFO_AREA_COLOR));
			
			JLabel currentEpoch = new JLabel("Current Epoch:" + game.getCurrentEpoch().getEpochNo());
			
			int currentPlayerIndex = game.getCurrentPlayerIndex();
			String currentPlayerName = game.players[currentPlayerIndex].getName();
			JLabel currentPlayer = new JLabel("Current turn is for :" + currentPlayerName);
			
			add(currentEpoch);
			add(currentPlayer);
		} 
		 	   
	}
} 

/**
 * This is a listener class that listens to mouse events.
 * This is to facilitate drag and drop of game components onto the game board.
 *
 */
class MyListener extends MouseAdapter{

	public void mousePressed(MouseEvent evt) {


	}

}
