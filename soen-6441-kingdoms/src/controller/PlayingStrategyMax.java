package controller;

import components.Castle;
import components.Tile.TileType;

public class PlayingStrategyMax implements PlayingStrategy {
	
	@Override
	public void selectAndMakeMove(GameController gc) {
		int[] firstVacantSpace = gc.nextVacantSpaceOnBoard();
		int row = firstVacantSpace[0];
		int col = firstVacantSpace[1];
		
		int currentPlayerIndex = gc.getGame().getCurrentPlayerIndex();
		
		boolean placeFirstTileAttempt = false, drawAndPlaceTileAttempt = false, placeCastleAttempt = false;
		
		boolean playerHasFirstTile = gc.getGame().players[currentPlayerIndex].hasFirstTile();
		
		if(playerHasFirstTile){
			TileType playersFirstTileType = gc.getGame().players[currentPlayerIndex].playerTiles.firstElement().getType();
			
			if(playersFirstTileType == TileType.RESOURCES){
				placeFirstTileAttempt = gc.placeFirstTile(currentPlayerIndex, row, col);
			}
		}
		
		if(!placeFirstTileAttempt){
			drawAndPlaceTileAttempt =  gc.drawAndPlaceTile(row, col);
		}
		
		Castle.CastleRank nextAvailableCastleRank = gc.nextAvailableCastleRank(currentPlayerIndex);
		if(!drawAndPlaceTileAttempt && !placeFirstTileAttempt && nextAvailableCastleRank != null){
			placeCastleAttempt =  gc.placeCastle(currentPlayerIndex, nextAvailableCastleRank, row, col);
		}
		
		
		if(!placeCastleAttempt && !drawAndPlaceTileAttempt && !placeFirstTileAttempt && playerHasFirstTile){
			placeFirstTileAttempt = gc.placeFirstTile(currentPlayerIndex, row, col);
		}
		
		if(!placeFirstTileAttempt && !drawAndPlaceTileAttempt && !placeCastleAttempt){
			gc.getGame().gameActionLog += "Player" + currentPlayerIndex + "could not make any moves. Passing on to the next player.";
		}
	}

}
