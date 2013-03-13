package controller;

import components.Castle;
import components.Tile.TileType;
/**
 * this class defines the playing strategy min condition which implements playing strategy class
 * @author TEAM B
 *
 */
public class PlayingStrategyMin implements PlayingStrategy {
	
	@Override
	/**
	 * set the select and move in game controller in game board
	 * @param gc is the object of game controller 
	 * 
	 */
	public void selectAndMakeMove(GameController gc) {
		int[] firstVacantSpace = gc.nextVacantSpaceOnBoard();
		int row = firstVacantSpace[0];
		int col = firstVacantSpace[1];
		/**
		 * here we will get our current player index
		 * @param draw and place (tile & castle) attempt for current player
		 */
		int currentPlayerIndex = gc.getGame().getCurrentPlayerIndex();
		
		boolean placeFirstTileAttempt = false, drawAndPlaceTileAttempt = false, placeCastleAttempt = false;
		
		boolean playerHasFirstTile = gc.getGame().players[currentPlayerIndex].hasFirstTile();
		/**
		 * here we will get the tile type of the current player
		 */
		if(playerHasFirstTile){
			TileType playersFirstTileType = gc.getGame().players[currentPlayerIndex].playerTiles.firstElement().getType();
			
			if(playersFirstTileType == TileType.HAZARD){
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
		/**
		 * if only one function is working  
		 */
		
		if(!placeCastleAttempt && !drawAndPlaceTileAttempt && !placeFirstTileAttempt && playerHasFirstTile){
			placeFirstTileAttempt = gc.placeFirstTile(currentPlayerIndex, row, col);
		}
		/**
		 * if all three conditions are not working than there will be no possible move
		 */
		if(!placeFirstTileAttempt && !drawAndPlaceTileAttempt && !placeCastleAttempt){
			gc.getGame().gameActionLog += "Player " + currentPlayerIndex + " could not make any moves - passing on to the next player.";
		}
	}

}
