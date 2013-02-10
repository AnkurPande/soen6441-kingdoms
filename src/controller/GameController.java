package controller;

public class GameController {
	
	GameInstance game;
	int finalscore;
	GameInstance ginew;//for score
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
		ginew = gi;
	}
	
	void assignTileToPlayer(int playerIndex, int tileIndex){
		assignTileToPlayer(this.game, playerIndex, tileIndex);
	}
	// total score of each player(row+)
	int totalScore(int index){
		//for(int index=0;index<ginew.players.length;index++){
			finalscore = ginew.players[index].total_score();
			//}
		return finalscore;
	}
}
