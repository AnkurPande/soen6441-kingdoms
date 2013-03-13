package controller;

import components.Castle;
/**
 * this class is design for the playing strategy one by one
 * @author TEAM B
 *
 */
public class PlayingStrategyTryOneByOne implements PlayingStrategy {
	
	@Override
	/**
	 * select and make move according to the first vacant space in row and column 
	 * for current player index.
	 * 
	 */
	public void selectAndMakeMove(GameController gc) {
		int[] firstVacantSpace = gc.nextVacantSpaceOnBoard();
		int row = firstVacantSpace[0];
		int col = firstVacantSpace[1];
		
		int currentPlayerIndex = gc.getGame().getCurrentPlayerIndex();
		
		boolean placeFirstTileAttempt = false, drawAndPlaceTileAttempt = false, placeCastleAttempt = false;
		
		placeFirstTileAttempt = gc.placeFirstTile(currentPlayerIndex, row, col);
		/**
		 * arrange castle for the available move possible for current player
		 */
		Castle.CastleRank nextAvailableCastleRank = gc.nextAvailableCastleRank(currentPlayerIndex);
		/**
		 * if this condition is not null than place castle in current player index
		 */
		if(!placeFirstTileAttempt && nextAvailableCastleRank != null){
			placeCastleAttempt =  gc.placeCastle(currentPlayerIndex, nextAvailableCastleRank, row, col);
		}
		/**
		 * if this condition works than it will draw and place tile in game board
		 */
		if(!placeCastleAttempt && !placeFirstTileAttempt){
			drawAndPlaceTileAttempt =  gc.drawAndPlaceTile(row, col);
		}
		
		if(!drawAndPlaceTileAttempt){
			//pass
		}
		/**
		 * if this condition follows than there will be no further move to make and it will pass to the next player
		 */
		if(!placeFirstTileAttempt && !drawAndPlaceTileAttempt && !placeCastleAttempt){
			gc.getGame().gameActionLog += "Player " + currentPlayerIndex + " could not make any moves - passing on to the next player.";
		}

	}

}
