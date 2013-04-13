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
		
		GameController gameController = new GameController();
		
		int noOfPlayers = gameController.getUserInputNoOfPlayers();
		Config conf = new Config(noOfPlayers);
		gameController = new GameController(conf);
		
		GameView gv = new GameView(gameController.getGame(),"EveryTurn");
		
		//gameController.playAllEpochs();
		
		//gameController.saveGame("test_file1.xml");
		gameController.playEpoch(1);
		gameController.saveGame("test_file1.xml");
		gameController.loadAndSetGame("test_file1.xml");
		gv.setGame(gameController.getGame());
		gameController.getGame().addChangeListener(gv);
		gameController.calculateScore();
		gameController.resetGameAtEpochEnd();
		gameController.saveGame("test_file1r.xml");
		
		gameController.playEpoch(2);
		gameController.saveGame("test_file2.xml");
		gameController.loadAndSetGame("test_file2.xml");
		gv.setGame(gameController.getGame());
		gameController.getGame().addChangeListener(gv);
		gameController.calculateScore();
		gameController.resetGameAtEpochEnd();
		gameController.saveGame("test_file2r.xml");
		
		gameController.playEpoch(3);
		gameController.saveGame("test_file3.xml");
		gameController.loadAndSetGame("test_file3.xml");
		gv.setGame(gameController.getGame());
		gameController.getGame().addChangeListener(gv);
		gameController.calculateScore();
		gameController.saveGame("junit_test.xml");
		gameController.resetGameAtEpochEnd();
		gameController.saveGame("test_file3r.xml");
		
		System.out.println(gameController.getGame().gameActionLog.replace('.','\n'));
		

	}

}