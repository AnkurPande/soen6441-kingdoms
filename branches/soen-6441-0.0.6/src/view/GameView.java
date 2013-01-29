package view;

import gameinstance.GameInstance;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
/**
 * This class basically contains the game view.
 * 
 * @author Ankur
 *
 */


public class GameView extends JFrame {
    private BoardArea board;
    private ScoringArea scoringArea;
    private PlayerInfoArea playerInfoArea;
    
    private GameInstance game;
    
    public GameView() {
    	
    	game = new GameInstance();
    	
        board = new BoardArea();
        scoringArea = new ScoringArea();
        playerInfoArea = new PlayerInfoArea();
        
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
        public Field[][] fields = new Field[5][6];
      /**
       * This function deals with creating the board area.  
       */
        public BoardArea() {
            setLayout(new GridLayout(5, 6));
            setMinimumSize(new Dimension(600, 600));
            setPreferredSize(new Dimension(600, 600));
            setBackground(Color.RED);
            fillBoard();
            fillBoardWithGameState();
        }
        
        private void fillBoard() {
            for (int i = 0; i < 5; ++i) {
                for (int j = 0; j < 6; ++j) {
                    fields[i][j] = new Field(i, j);
                    add(fields[i][j]);
                }
            }
        }
        
        private void fillBoardWithGameState(){
        	for (int i = 0; i < 5; ++i) {
                for (int j = 0; j < 6; ++j) {
                    if(game.gameComponentsOnBoard[i][j] != null) 
                    	fields[i][j].setText(game.gameComponentsOnBoard[i][j].displayIcon());
                }
            }
        }
    }
    
    
    class Field extends JLabel {
        private int x, y;
        public Field(int x, int y) {
            this.x = x;
            this.y = y;
            setOpaque(true);
            setMinimumSize(new Dimension(75, 75));
            setPreferredSize(new Dimension(75, 75));
            if ((x + y) % 2 == 0) {
                setBackground(Color.DARK_GRAY);
            } else {
                setBackground(Color.RED);
            }
        }
    }
    
    class ScoringArea extends JPanel {
   // Player score is displayed here.     
        public ScoringArea() {
            setLayout(new GridLayout(5, 6));
            setMinimumSize(new Dimension(300, 600));
            setPreferredSize(new Dimension(300, 600));
            setBackground(Color.GREEN);
            
        }
    }
    
    class PlayerInfoArea extends JPanel {
        public PlayerInfoArea() {
            setLayout(new GridLayout(5, 6));
            setMinimumSize(new Dimension(900, 200));
            setPreferredSize(new Dimension(900, 200));
            setBackground(Color.CYAN);
            
        }
    }
}
