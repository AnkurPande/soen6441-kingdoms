package gamerunner;

import model.GameInstance;
import view.GameView;
import controller.GameController;

public class GameRunner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		GameInstance gi = new GameInstance();
		
		GameController gameController = new GameController();
		gameController.saveGame("test_file1.xml");
		
		gameController.setGame(gameController.loadGame("test_file3.xml"));
		
//		gi.controller.saveGame("test_file1.xml");
//		
//		gi = gi.controller.loadGame("test_file3.xml");
		
		GameView gv = new GameView(gameController.getGame());

	}

}