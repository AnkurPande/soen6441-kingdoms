package controller;

import components.Castle;
import components.Tile.TileType;
/**
 * this class defines the playing strategy max of the game
 * @author TEAM B
 */
public class PlayingStrategyMax implements PlayingStrategy {
	
	@Override
	/**
	 * this class able to select and make move according to the player
	 * @param row - tile will place in first vacant space
	 * @param col - tile will place in first vacant space in col 
	 */
	public void selectAndMakeMove(GameController gc)  
	{
		int[] firstVacantSpace = gc.nextVacantSpaceOnBoard();
		int row = firstVacantSpace[0];
		int col = firstVacantSpace[1];
		//current player index shows place tile and castle attempt
		int currentPlayerIndex = gc.getGame().getCurrentPlayerIndex();
		
		boolean placeFirstTileAttempt = false, drawAndPlaceTileAttempt = false, placeCastleAttempt = false;
		
		boolean playerHasFirstTile = gc.getGame().players[currentPlayerIndex].hasFirstTile();
		/**
		 * place first tile to the current player 
		 */
		if(playerHasFirstTile){
			TileType playersFirstTileType = gc.getGame().players[currentPlayerIndex].playerTiles.firstElement().getType();
			/**
			 * if player first tiletype is resource than place first tile to the current player
			 */
			if(playersFirstTileType == TileType.RESOURCES){
				placeFirstTileAttempt = gc.placeFirstTile(currentPlayerIndex, row, col);
			}
		}
		/**
		 * if first attempt is unsucessful than draw the next tile to the current player index 
		 */
		if(!placeFirstTileAttempt){
			drawAndPlaceTileAttempt =  gc.drawAndPlaceTile(row, col);
		}
		
		Castle.CastleRank nextAvailableCastleRank = gc.nextAvailableCastleRank(currentPlayerIndex);//draw castle according to rank to current player
		if(!drawAndPlaceTileAttempt && !placeFirstTileAttempt && nextAvailableCastleRank != null){
			placeCastleAttempt =  gc.placeCastle(currentPlayerIndex, nextAvailableCastleRank, row, col);
		}
		
		/**
		 * place the first tile to current player index
		 */
		if(!placeCastleAttempt && !drawAndPlaceTileAttempt && !placeFirstTileAttempt && playerHasFirstTile){
			placeFirstTileAttempt = gc.placeFirstTile(currentPlayerIndex, row, col);
		}
		/**
		 * if these conditions are not possible than display "could not make any move"
		 */
		if(!placeFirstTileAttempt && !drawAndPlaceTileAttempt && !placeCastleAttempt){
			gc.getGame().gameActionLog += "Player " + currentPlayerIndex + " could not make any moves - passing on to the next player.";
		}
	}

}
