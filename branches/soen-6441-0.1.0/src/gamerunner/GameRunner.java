package gamerunner;

import view.GameView;
import controller.GameInstance;

public class GameRunner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		GameInstance gi = new GameInstance();
		
		//gi.saveGame("test_file1.xml");
		
		//gi = gi.loadGame("test_file3.xml");
		GameView gv = new GameView(gi);

	}

}