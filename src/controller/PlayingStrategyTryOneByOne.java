package controller;

import components.Castle;

/**
 * The strategy makes the player choose moves one after the other first place first tile, then place castle and finally draw and place tiles
 * - whichever succeeds first.
 * @author Team B
 *
 */
public class PlayingStrategyTryOneByOne implements PlayingStrategy {
	
	/**
	 * This method determines the move a player will make next.
	 */
	@Override
	public void selectAndMakeMove(GameController gc) {
		int[] firstVacantSpace = gc.nextVacantSpaceOnBoard();
		int row = firstVacantSpace[0];
		int col = firstVacantSpace[1];
		
		int currentPlayerIndex = gc.getGame().getCurrentPlayerIndex();
		
		boolean placeFirstTileAttempt = false, drawAndPlaceTileAttempt = false, placeCastleAttempt = false;
		
		placeFirstTileAttempt = gc.placeTileAndDraw(row, col);
		
		Castle.CastleRank nextAvailableCastleRank = gc.nextAvailableCastleRank(currentPlayerIndex);
		if(!placeFirstTileAttempt && nextAvailableCastleRank != null){
			placeCastleAttempt =  gc.placeCastle(currentPlayerIndex, nextAvailableCastleRank, row, col);
		}
		
		if(!placeCastleAttempt && !placeFirstTileAttempt){
			drawAndPlaceTileAttempt =  gc.placeTileAndDraw(row, col);
		}
		
		if(!drawAndPlaceTileAttempt){
			//pass
		}
		
		if(!placeFirstTileAttempt && !drawAndPlaceTileAttempt && !placeCastleAttempt){
			gc.getGame().gameActionLog += "Player " + currentPlayerIndex + " could not make any moves - passing on to the next player.";
		}

	}

}
