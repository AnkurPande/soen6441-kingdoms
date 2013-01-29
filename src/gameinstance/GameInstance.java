package gameinstance;

import components.*;


public class GameInstance {
	
	private Player[] players;
	private EpochCounter currentEpoch;
	public GameComponents[][] gameComponentsOnBoard;
	
	
	
	public GameInstance(){
		for(int i=0;i<Config.NO_OF_PLAYERS;i++){
//			players[i] = new Player(Castle.Color.RED);
		}
		players = new Player[Config.NO_OF_PLAYERS];
		gameComponentsOnBoard = new GameComponents[5][6];
		
		players[0] = new Player(Castle.Color.RED, "Player 1");
		players[1] = new Player(Castle.Color.YELLOW, "Player 2");
		players[2] = new Player(Castle.Color.BLUE, "Player 3");
		players[3] = new Player(Castle.Color.GREEN, "Player 4");
		
		
		gameComponentsOnBoard[0][0] = players[0].rank1Castles[0];
		
		gameComponentsOnBoard[3][2] = players[2].rank2Castles[2];
	}
	
	class Player{
		
		private String name;
		
		private int score;
		private GameComponents[] playersGameComponents;
		
		private Castle[] rank1Castles;
		private Castle[] rank2Castles;
		private Castle[] rank3Castles;
		private Castle[] rank4Castles;
		
		private Castle.Color playerColor;
		
		public Player(Castle.Color color, String name){
			
			//Set the player color
			this.playerColor = color;
			
			this.name = name;
			
			rank1Castles = new Castle[Config.NO_OF_RANK1CASTLES_PER_PLAYER];
			rank2Castles = new Castle[Config.NO_OF_RANK2CASTLES_PER_PLAYER];
			rank3Castles = new Castle[Config.NO_OF_RANK3CASTLES_PER_PLAYER];
			rank4Castles = new Castle[Config.NO_OF_RANK4CASTLES_PER_PLAYER];
			
			//Create the Rank 1 castles
			for(int i=0;i<Config.NO_OF_RANK1CASTLES_PER_PLAYER;i++){
				rank1Castles[i] = new Castle(this.playerColor, Castle.Rank.ONE);
			}
			
			//Create the Rank 2 castles
			for(int i=0;i<Config.NO_OF_RANK2CASTLES_PER_PLAYER;i++){
				rank2Castles[i] = new Castle(this.playerColor, Castle.Rank.TWO);
			}
			
			//Create the Rank 3 castles
			for(int i=0;i<Config.NO_OF_RANK3CASTLES_PER_PLAYER;i++){
				rank3Castles[i] = new Castle(this.playerColor, Castle.Rank.THREE);
			}
			
			//Create the Rank 4 castles
			for(int i=0;i<Config.NO_OF_RANK4CASTLES_PER_PLAYER;i++){
				rank4Castles[i] = new Castle(this.playerColor, Castle.Rank.FOUR);
			}
			
		}
	}
	
	class Config{
		private static final int NO_OF_PLAYERS = 4;
		
		private static final int NO_OF_RANK1CASTLES_PER_PLAYER = 4;
		private static final int NO_OF_RANK2CASTLES_PER_PLAYER = 3;
		private static final int NO_OF_RANK3CASTLES_PER_PLAYER = 2;
		private static final int NO_OF_RANK4CASTLES_PER_PLAYER = 1;
		
	}

}
