package gameinstance;

import components.*;
import components.Tile.TileType;


public class GameInstance {
	
	private Player[] players;
	private EpochCounter currentEpoch;
	public GameComponents[][] gameComponentsOnBoard;
	
	/**
	 * 
	 */
	public GameInstance(){
		
		//Initialize the player objects
		players = new Player[Config.NO_OF_PLAYERS];
		for(int i=0;i<players.length;i++){
			players[i] = new Player(PlayerColor.values()[i],"Player"+(i+1));
		}
		
		//Initialize the epoch counter
		currentEpoch = new EpochCounter();
		
		//Initialize the game board to hold the game components
		gameComponentsOnBoard = new GameComponents[Config.NO_OF_COLS][Config.NO_OF_ROWS];
		
		
		players[2].rank2Castles[2].setIconFileName("images/castle_green_rank1.jpg");
		players[3].rank2Castles[2].setIconFileName("images/castle_red_rank1.jpg");
		gameComponentsOnBoard[0][0] = players[0].rank1Castles[0];
		gameComponentsOnBoard[3][2] = players[2].rank2Castles[2];
		
		gameComponentsOnBoard[3][3] = new Tile(TileType.HAZARD); 
		gameComponentsOnBoard[3][4] = players[3].rank2Castles[2]; 
	}
	
	class Player{
		
		private String name;
		
		private int score;
		private GameComponents[] playersGameComponents;
		
		private Castle[] rank1Castles;
		private Castle[] rank2Castles;
		private Castle[] rank3Castles;
		private Castle[] rank4Castles;
		
		private PlayerColor playerColor;
		
		public Player(PlayerColor color, String name){
			
			//Set the player color & name
			this.playerColor = color;			
			this.setName(name);
			
			//Initialize the castles
			rank1Castles = new Castle[Config.NO_OF_RANK1CASTLES_PER_PLAYER];
			rank2Castles = new Castle[Config.NO_OF_RANK2CASTLES_PER_PLAYER];
			rank3Castles = new Castle[Config.NO_OF_RANK3CASTLES_PER_PLAYER];
			rank4Castles = new Castle[Config.NO_OF_RANK4CASTLES_PER_PLAYER];
			
			//Create the Rank 1 castles
			for(int i=0;i<Config.NO_OF_RANK1CASTLES_PER_PLAYER;i++){
				rank1Castles[i] = new Castle(this.playerColor, Castle.CastleRank.ONE);
			}
			
			//Create the Rank 2 castles
			for(int i=0;i<Config.NO_OF_RANK2CASTLES_PER_PLAYER;i++){
				rank2Castles[i] = new Castle(this.playerColor, Castle.CastleRank.TWO);
			}
			
			//Create the Rank 3 castles
			for(int i=0;i<Config.NO_OF_RANK3CASTLES_PER_PLAYER;i++){
				rank3Castles[i] = new Castle(this.playerColor, Castle.CastleRank.THREE);
			}
			
			//Create the Rank 4 castles
			for(int i=0;i<Config.NO_OF_RANK4CASTLES_PER_PLAYER;i++){
				rank4Castles[i] = new Castle(this.playerColor, Castle.CastleRank.FOUR);
			}
			
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
	
	public class Config{
		private static final int NO_OF_PLAYERS = 4;
		
		private static final int NO_OF_RANK1CASTLES_PER_PLAYER = 4;
		private static final int NO_OF_RANK2CASTLES_PER_PLAYER = 3;
		private static final int NO_OF_RANK3CASTLES_PER_PLAYER = 2;
		private static final int NO_OF_RANK4CASTLES_PER_PLAYER = 1;
		
		public static final int NO_OF_ROWS = 6;
		public static final int NO_OF_COLS = 5;
		
		public final static String BOARD_AREA_COLOR = "#000000";
		public final static String BOARD_AREA_FIELD_COLOR1 = "#E0E0E0";
		public final static String BOARD_AREA_FIELD_COLOR2 = "#CCCCFF";
		public final static String SCORING_AREA_COLOR = "#C0C0C0"; 
		public final static String PLAYER_INFO_AREA_COLOR = "#808080"; 
	}
	
	public enum PlayerColor{
		RED, YELLOW, BLUE, GREEN;
	}

}
