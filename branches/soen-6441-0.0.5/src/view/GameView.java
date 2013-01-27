package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;



public class GameView extends JFrame {
    private Board board;
    protected GameView() {
        board = new Board();
        setLayout(new FlowLayout());
        add(board);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameView();
            }
        });
    }
    class Board extends JPanel {
        public Field[][] fields = new Field[5][6];
        public Board() {
            setLayout(new GridLayout(5, 6));
            setMinimumSize(new Dimension(600, 600));
            setPreferredSize(new Dimension(600, 600));
            setBackground(Color.RED);
            fillBoard();
        }
        private void fillBoard() {
            for (int i = 0; i < 5; ++i) {
                for (int j = 0; j < 6; ++j) {
                    fields[i][j] = new Field(i, j);
                    add(fields[i][j]);
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
}
