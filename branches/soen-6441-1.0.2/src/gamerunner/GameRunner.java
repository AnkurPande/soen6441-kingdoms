package gamerunner;

import model.Config;
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
		
		Config conf = new Config(4);
		
		GameController gameController = new GameController(conf);
	
		gameController.saveGame("test_file1.xml");
		
		//gameController.loadAndSetGame("test_file10.xml");
		
		GameView gv = new GameView(gameController.getGame());

	}

}