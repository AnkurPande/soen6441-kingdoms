package controller;

public class GameController {
	
	GameInstance game;
	
	public GameController(GameInstance game){
		this.game = game;
	}
	
	void assignTileToPlayer(GameInstance gi, int playerIndex, int tileIndex){
		int i = 0;
		for(i = 0; i < gi.players[playerIndex].playerTiles.length; i++)	{
			if(gi.players[playerIndex].playerTiles[i] == null){
				break;
			}
		}
		gi.players[playerIndex].playerTiles[i] = gi.tileBank[tileIndex];
		gi.tileBank[tileIndex] = null;
	}
	
	void assignTileToPlayer(int playerIndex, int tileIndex){
		assignTileToPlayer(this.game, playerIndex, tileIndex);
	}
}
