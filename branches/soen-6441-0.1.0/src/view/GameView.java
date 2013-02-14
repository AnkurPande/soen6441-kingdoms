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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;

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
    	MyListener listener = new MyListener();
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
                    fields[i][j].setTransferHandler(new TransferHandler("icon"));
                    fields[i][j].addMouseListener(listener);
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
		public void mouseReleased(MouseEvent evt) {
			// TODO Auto-generated method stub
			JComponent comp = (JComponent) evt.getSource();
	        TransferHandler th = comp.getTransferHandler();
	        th.exportAsDrag(comp, evt, TransferHandler.COPY);
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
        }
    }
    
    class PlayerInfoArea extends JPanel {
   
		private static final long serialVersionUID = 1L;
				   
		public PlayerInfoArea(){
			   MyListener listener = new MyListener();
			   int NO_OF_PLAYER =game.players.length;
			   setLayout(new GridLayout(0,(NO_OF_PLAYER +1)));
			   setMinimumSize(new Dimension(900, 200));
			   setOpaque(true);
			   setPreferredSize(new Dimension(900, 198));
			   setBackground(Color.decode(game.gameConfig.PLAYER_INFO_AREA_COLOR));
	           
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
		 	   JLabel[] No_Of_rank1_Castle = new JLabel[NO_OF_PLAYER];
		 	   JLabel[] No_Of_rank2_Castle = new JLabel[NO_OF_PLAYER];
		 	   JLabel[] No_Of_rank3_Castle = new JLabel[NO_OF_PLAYER];
		 	   JLabel[] No_Of_rank4_Castle = new JLabel[NO_OF_PLAYER];
		 	   JLabel[] No_Of_Coins = new JLabel[NO_OF_PLAYER];
		 	   JLabel[] rank1Icon = new JLabel[NO_OF_PLAYER];
		 	   JLabel[] rank2Icon = new JLabel[NO_OF_PLAYER];
		 	   JLabel[] rank3Icon = new JLabel[NO_OF_PLAYER];
		 	   JLabel[] rank4Icon = new JLabel[NO_OF_PLAYER];
		 	   JLabel[] coinsIcon = new JLabel[NO_OF_PLAYER];
		 	   JLabel[] playerColor = new JLabel[NO_OF_PLAYER];
		 	   for (int i=0; i<NO_OF_PLAYER; i++){
					  
					   player[i] = new JPanel();
					   player[i].setMinimumSize(new Dimension(180, 190));
					   player[i].setPreferredSize(new Dimension(180, 190));
			 	       player[i].setBorder(BorderFactory.createLineBorder(Color.black));
			 	       player[i].setLayout(null);	
			 	       
				       String playerNames = game.players[i].getName();
					   playerName[i] = new JLabel(playerNames,JLabel.CENTER);
					   playerName[i].setBounds(10, 5, 60, 20);
					   playerColor[i] = new JLabel("game.players[i].playerColor");
					   playerColor[i].setBounds(70, 5, 60, 20);
					  
					   rank1Label[i] = new JLabel("NO OF RANK1 CASTLE", JLabel.CENTER);
					   rank1Label[i].setBounds(10, 50, 140, 20);
					   rank2Label[i] = new JLabel("NO OF RANK2 CASTLE ", JLabel.CENTER);
					   rank2Label[i].setBounds(10, 75, 140, 20);
					   rank3Label[i] = new JLabel("NO OF RANK3 CASTLE ", JLabel.CENTER);
					   rank3Label[i].setBounds(10, 100, 140, 20);
					   rank4Label[i] = new JLabel("NO OF RANK4 CASTLE ", JLabel.CENTER);
					   rank4Label[i].setBounds(10, 125, 140, 20);
					   coinsLabel[i] = new JLabel("NO OF COINS", JLabel.LEFT);
					   coinsLabel[i].setBounds(15, 150, 140, 20);
					   		           
			           No_Of_rank1_Castle[i] = new JLabel(Integer.toString(game.players[i].rank1Castles.length));
			           No_Of_rank1_Castle[i].setBounds(160, 50, 20, 20);
			           No_Of_rank2_Castle[i] = new JLabel(Integer.toString(game.players[i].rank2Castles.length));
					   No_Of_rank2_Castle[i].setBounds(160, 75, 20, 20);
					   No_Of_rank3_Castle[i] = new JLabel(Integer.toString(game.players[i].rank3Castles.length));
					   No_Of_rank3_Castle[i].setBounds(160, 100, 20, 20);
					   No_Of_rank4_Castle[i] = new JLabel(Integer.toString(game.players[i].rank4Castles.length));
					   No_Of_rank4_Castle[i].setBounds(160, 125, 20, 20);
					   No_Of_Coins[i] = new JLabel(Integer.toString(game.players[i].playerCoins[0].getValue()));
					   No_Of_Coins[i].setBounds(160, 150, 20, 20);
					   
					   path = game.players[0].rank1Castles[0].displayIcon();
			           icon = new ImageIcon(path);
					   rank1Icon[i] = new JLabel(icon);
					   rank1Icon[i].setBounds(185, 50, 30, 20);
					    
					   
					   path = game.players[0].rank2Castles[0].displayIcon();
			           icon = new ImageIcon(path);
					   rank2Icon[i] = new JLabel(icon);
					   rank2Icon[i].setBounds(185, 75, 30, 20);
					   
					   
					   path = game.players[0].rank3Castles[0].displayIcon();
			           icon = new ImageIcon(path);
					   rank3Icon[i] = new JLabel(icon);
					   rank3Icon[i].setBounds(185, 100, 30, 20);
					 
					   
					   path = game.players[0].rank4Castles[0].displayIcon();
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
					   player[i].add(No_Of_rank1_Castle[i]);
					   player[i].add(No_Of_rank2_Castle[i]);
					   player[i].add(No_Of_rank3_Castle[i]);
			
					   player[i].add(No_Of_rank4_Castle[i]);
					   player[i].add(No_Of_Coins[i]);
					   player[i].add(rank1Icon[i]);
					   player[i].add(rank2Icon[i]);
					   player[i].add(rank3Icon[i]);
					   player[i].add(rank4Icon[i]);
					   player[i].add(coinsIcon[i]);
					  
		 	       					   
		 	   } 
		 	   
		}
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
    }
} 
		class MyListener extends MouseAdapter{
			
			public void mousePressed(MouseEvent evt) {
			      //  if(i!= 0)
				//if (SwingUtilities.isLeftMouseButton(evt))
				//{
			    	JComponent comp = (JComponent) evt.getSource();
			        TransferHandler th = comp.getTransferHandler();
			        //System.out.println("hi");
			       th.exportAsDrag(comp, evt, TransferHandler.COPY);
			         
			      }
			
		}
