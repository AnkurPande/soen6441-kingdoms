package gamerunner;

import view.GameView;
import controller.GameController;

/**
 * This class is like a driver class used to run the game.
 * @author Team B
 */
public class GameRunner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		GameController gameController = new GameController();
		gameController.saveGame("test_file1.xml");
		
		gameController.loadGame("test_file3.xml");
		
		GameView gv = new GameView(gameController.getGame());

	}

}