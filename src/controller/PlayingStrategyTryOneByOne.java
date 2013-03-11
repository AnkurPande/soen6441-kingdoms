package controller;

import components.Castle;

public class PlayingStrategyTryOneByOne implements PlayingStrategy {
	
	@Override
	public void selectAndMakeMove(GameController gc) {
		int[] firstVacantSpace = gc.nextVacantSpaceOnBoard();
		int row = firstVacantSpace[0];
		int col = firstVacantSpace[1];
		
		int currentPlayerIndex = gc.getGame().getCurrentPlayerIndex();
		
		boolean placeFirstTileAttempt = false, drawAndPlaceTileAttempt = false, placeCastleAttempt = false;
		
		placeFirstTileAttempt = gc.placeFirstTile(currentPlayerIndex, row, col);
		
		Castle.CastleRank nextAvailableCastleRank = gc.nextAvailableCastleRank(currentPlayerIndex);
		if(!placeFirstTileAttempt && nextAvailableCastleRank != null){
			placeCastleAttempt =  gc.placeCastle(currentPlayerIndex, nextAvailableCastleRank, row, col);
		}
		
		if(!placeCastleAttempt && !placeFirstTileAttempt){
			drawAndPlaceTileAttempt =  gc.drawAndPlaceTile(row, col);
		}
		
		if(!drawAndPlaceTileAttempt){
			//pass
		}
		
		if(!placeFirstTileAttempt && !drawAndPlaceTileAttempt && !placeCastleAttempt){
			gc.getGame().gameActionLog += "Player" + currentPlayerIndex + " could not make any moves. Passing on to the next player.";
		}

	}

}
