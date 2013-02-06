package gameinstance;

import java.util.Random;

import components.*;
import components.Coin.Material;
import components.Tile.TileType;


public class GameInstance {
	
	private Player[] players;
	private int currentPlayerIndex;
	
	private EpochCounter currentEpoch;
	public GameComponents[][] gameBoard;
	
	private Tile[] tileBank;
	private Coin[] coinBank;
	
	/**
	 * 
	 */
	public GameInstance(){
		
		//Initialize the epoch counter
		currentEpoch = new EpochCounter();
		
		initTileBank();
		shuffleTiles(tileBank);
		
		initCoinBank();
		
		initPlayers();
		
		initGameBoard();
		
		
		players[2].rank2Castles[2].setIconFileName("images/castle_green_rank1.jpg");
		players[3].rank2Castles[2].setIconFileName("images/castle_red_rank1.jpg");
		gameBoard[0][0] = players[0].rank1Castles[0];
		gameBoard[3][2] = players[2].rank2Castles[2];
		
		gameBoard[3][3] = new Tile(TileType.HAZARD); 
		gameBoard[3][4] = players[3].rank2Castles[2]; 
		
		
	}
	
	private void initPlayers(){
		//Initialize the player objects
		players = new Player[Config.NO_OF_PLAYERS];
		for(int i=0;i<players.length;i++){
			players[i] = new Player(PlayerColor.values()[i],"Player"+(i+1));
		}
			
	}
	
	private void initTileBank(){
		
		tileBank = new Tile[countNoOfTiles()];
		
		//TODO refactor
		int j = -1;
		for(int i = 0; i < Config.NO_OF_RESOURCE_TILES; i++){
			tileBank[++j] = new Tile(TileType.RESOURCES);
		}
		
		for(int i = 0; i < Config.NO_OF_HAZARD_TILES; i++){
			tileBank[++j] = new Tile(TileType.HAZARD);
		}
		
		for(int i = 0; i < Config.NO_OF_MOUNTAIN_TILES; i++){
			tileBank[++j] = new Tile(TileType.MOUNTAIN);
		}
		
		for(int i = 0; i < Config.NO_OF_DRAGON_TILES; i++){
			tileBank[++j] = new Tile(TileType.DRAGON);
		}
		
		for(int i = 0; i < Config.NO_OF_GOLDMINE_TILES; i++){
			tileBank[++j] = new Tile(TileType.GOLDMINE);
		}
	}
	
	private void initCoinBank(){
		
		coinBank = new Coin[countNoOfCoins()];
		
		//TODO refactor
		int j = -1;
		for(int i = 0; i < Config.NO_OF_COPPER_COINS_VAL1; i++){
			coinBank[++j] = new Coin(Material.COPPER, Config.COPPER_COINS_VAL1_VAL);
		}
		
		for(int i = 0; i < Config.NO_OF_COPPER_COINS_VAL5; i++){
			coinBank[++j] = new Coin(Material.COPPER, Config.COPPER_COINS_VAL5_VAL);
		}
		
		for(int i = 0; i < Config.NO_OF_SILVER_COINS; i++){
			coinBank[++j] = new Coin(Material.SILVER, Config.SILVER_COINS_VAL);
		}
		
		for(int i = 0; i < Config.NO_OF_GOLD_COINS_VAL50; i++){
			coinBank[++j] = new Coin(Material.GOLD, Config.GOLD_COINS_VAL50_VAL);
		}
		
		for(int i = 0; i < Config.NO_OF_GOLD_COINS_VAL100; i++){
			coinBank[++j] = new Coin(Material.GOLD, Config.GOLD_COINS_VAL100_VAL);
		}
	}
	
	private int countNoOfTiles(){
		
		return 	Config.NO_OF_RESOURCE_TILES 
					+ Config.NO_OF_HAZARD_TILES
					+ Config.NO_OF_MOUNTAIN_TILES 
					+ Config.NO_OF_DRAGON_TILES
					+ Config.NO_OF_GOLDMINE_TILES
					+ Config.NO_OF_WIZARD_TILES;
	}
	
	private int countNoOfCoins(){
		return Config.NO_OF_COPPER_COINS_VAL1 
				+ Config.NO_OF_COPPER_COINS_VAL5 
				+ Config.NO_OF_SILVER_COINS 
				+ Config.NO_OF_GOLD_COINS_VAL50 
				+ Config.NO_OF_GOLD_COINS_VAL100;
	}
	
	private void initGameBoard(){
		//Initialize the game board to hold the game components
		gameBoard = new GameComponents[Config.NO_OF_COLS][Config.NO_OF_ROWS];
	}
	
	public static void shuffleTiles(Tile[] tiles) {
		int n = tiles.length;
		Random random = new Random();
		random.nextInt();
		for (int i = 0; i < n; i++) {
			int change = i + random.nextInt(n - i);
			swap(tiles, i, change);
		}
	}

	private static void swap(Tile[] a, int i, int change) {
		Tile helper = a[i];
		a[i] = a[change];
		a[change] = helper;
	}

	
	class Player{
		
		private String name;
		
		private int score;
		private GameComponents[] playersGameComponents;
		
		private Castle[] rank1Castles;
		private Castle[] rank2Castles;
		private Castle[] rank3Castles;
		private Castle[] rank4Castles;
		
		private Coin[] playerCoins;
		private Tile[] playerTiles;
		
		private PlayerColor playerColor;
		
		public Player(PlayerColor color, String name){
			
			//Set the player color & name
			this.playerColor = color;			
			this.setName(name);
			
			this.score = 0;
		
			//Initialize the castle objects for the player
			initCastles();
			
			initCoins();
			
			
		}
		
		private void initCastles(){
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
		
		private void initCoins(){
			playerCoins = new Coin[countNoOfCoins()];
			playerCoins[0] = new Coin(Material.GOLD, 50);
		}
		
		
		
		private int evaluateCoins(){
			int total = 0;
			for(int i = 0; i < playerCoins.length; i++){
				if(playerCoins[i] != null){
					total += playerCoins[i].getValue();
				}
			}
			
			return total;
		}
		
		private void initTiles(){
			playerTiles = new Tile[countNoOfTiles()];
			//TODO 
			//assign a tile to the player
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
		
		public final static int NO_OF_COPPER_COINS_VAL1 = 19;
		public final static int COPPER_COINS_VAL1_VAL = 1;
		
		public final static int NO_OF_COPPER_COINS_VAL5 = 12;
		public final static int COPPER_COINS_VAL5_VAL = 5;
		
		public final static int NO_OF_SILVER_COINS = 20;
		public final static int SILVER_COINS_VAL = 10;
		
		public final static int NO_OF_GOLD_COINS_VAL50 = 8;
		public final static int GOLD_COINS_VAL50_VAL = 50;
		
		public final static int NO_OF_GOLD_COINS_VAL100 = 4;
		public final static int GOLD_COINS_VAL100_VAL = 4;
		
		public final static int NO_OF_RESOURCE_TILES = 12;
		public final static int NO_OF_HAZARD_TILES = 6;
		public final static int NO_OF_MOUNTAIN_TILES = 2;
		public final static int NO_OF_DRAGON_TILES = 1;
		public final static int NO_OF_GOLDMINE_TILES = 1;
		public final static int NO_OF_WIZARD_TILES = 1;
	}
	
	public enum PlayerColor{
		RED, YELLOW, BLUE, GREEN;
	}

}
