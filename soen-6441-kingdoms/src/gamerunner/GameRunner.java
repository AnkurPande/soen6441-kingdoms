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
		
		GameView gv = new GameView(gameController.getGame(),"EveryRound");
		
		gameController.saveGame("test_file1.xml");
		gameController.playEpoch(1);
		gameController.saveGame("test_file1.xml");
		gameController.loadAndSetGame("test_file1.xml");
		gameController.resetGameAtEpochEnd();
		
		gameController.playEpoch(2);
		gameController.saveGame("test_file2.xml");
		gameController.loadAndSetGame("test_file2.xml");
		gameController.resetGameAtEpochEnd();
		
		gameController.playEpoch(3);
		gameController.saveGame("test_file3.xml");
		gameController.loadAndSetGame("test_file3.xml");
		//gameController.resetGameAtEpochEnd();
		
		System.out.println(gameController.getGame().gameActionLog.replace('.','\n'));
		

	}

}