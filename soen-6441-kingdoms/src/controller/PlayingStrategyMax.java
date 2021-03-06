package controller;

import components.Castle;
import components.Tile.TileType;

/**
 * The strategy makes the player choose the move that will maximize his/her scores at that turn.
 *  
 * @author Team B
 *
 */
public class PlayingStrategyMax implements PlayingStrategy {
	
	/**
	 * This method determines the move a player will make next.
	 */
	@Override
	public void selectAndMakeMove(GameController gc) {
		int[] firstVacantSpace = gc.nextVacantSpaceOnBoard();
		int row = firstVacantSpace[0];
		int col = firstVacantSpace[1];
		
		int currentPlayerIndex = gc.getGame().getCurrentPlayerIndex();
		
		boolean placeFirstTileAttempt = false, placeAndDrawTileAttempt = false, placeCastleAttempt = false;
		
		boolean playerHasFirstTile = gc.getGame().players[currentPlayerIndex].hasFirstTile();
		
		if(playerHasFirstTile){
			TileType playersFirstTileType = gc.getGame().players[currentPlayerIndex].playerTiles.firstElement().getType();
			
			if(playersFirstTileType == TileType.RESOURCES){
				placeFirstTileAttempt = gc.placeTileAndDraw(row, col);
			}
		}
		
		if(!placeFirstTileAttempt){
			placeAndDrawTileAttempt =  gc.placeTileAndDraw(row, col);
		}
		
		Castle.CastleRank nextAvailableCastleRank = gc.nextAvailableCastleRank(currentPlayerIndex);
		if(!placeAndDrawTileAttempt && !placeFirstTileAttempt && nextAvailableCastleRank != null){
			placeCastleAttempt =  gc.placeCastle(currentPlayerIndex, nextAvailableCastleRank, row, col);
		}
		
		
		if(!placeCastleAttempt && !placeAndDrawTileAttempt && !placeFirstTileAttempt && playerHasFirstTile){
			placeFirstTileAttempt = gc.placeFirstTile(currentPlayerIndex, row, col);
		}
		
		if(!placeFirstTileAttempt && !placeAndDrawTileAttempt && !placeCastleAttempt){
			gc.getGame().gameActionLog += "Player " + currentPlayerIndex + " could not make any moves - passing on to the next player.";
		}
	}

}
