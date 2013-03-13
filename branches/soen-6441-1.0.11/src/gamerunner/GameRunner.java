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
		
//		gameController.loadAndSetGame("test_file1.xml");
		
//		gameController.playEpoch(1);
		
		GameView gv = new GameView(gameController.getGame());
		
		gameController.playEpoch(1);
		
		//gameController.resetGameAtEpochEnd();
		
//		gameController.saveGame("test_file2.xml");
//		
//		gameController.playEpoch(2);
//		GameView gv1 = new GameView(gameController.getGame());
//		gameController.resetGameAtEpochEnd();
//		
//		gameController.playEpoch(3);
//		GameView gv3 = new GameView(gameController.getGame());
//		
//		gameController.saveGame("test_file3.xml");
		
		System.out.println(gameController.getGame().gameActionLog.replace('.','\n'));
		
		//gameController.loadAndSetGame("test_file10.xml");

	}

}