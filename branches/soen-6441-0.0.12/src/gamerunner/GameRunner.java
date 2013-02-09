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
		GameView gv = new GameView(gi);
		gi.saveGameState();

	}

}
