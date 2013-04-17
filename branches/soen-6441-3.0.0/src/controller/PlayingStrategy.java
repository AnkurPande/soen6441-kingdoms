package controller;

/**
 * This is the interface for determining the playing strategy of a player.
 * 
 * @author Team B
 *
 */
public interface PlayingStrategy {
	
	/**
	 * This method determines the move a player will make next.
	 * 
	 * @param gc The game controller which is running the game.
	 */
	public void selectAndMakeMove(GameController gc);
}

