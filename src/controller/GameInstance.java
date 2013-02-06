package controller;

import java.util.Random;

import components.*;
import components.Coin.Material;
import components.Tile.TileType;


public class GameInstance {
	
	GameController controller;
	public Config gameConfig;
	
	protected Player[] players;
	private int currentPlayerIndex;
	
	private EpochCounter currentEpoch;
	public GameComponents[][] gameBoard;
	
	protected Tile[] tileBank;
	protected Coin[] coinBank;
	
	/**
	 * 
	 */
	public GameInstance(){
		
		controller = new GameController(this);
		
		//Create a configuration object for the game
		this.gameConfig = new Config();
		
		//Initialize the epoch counter
		setCurrentEpoch(new EpochCounter());
		
		//Initialize and shuffle the tiles
		initTileBank();
		shuffleTiles(tileBank);
		
		//Initialize the coin bank (holds the coins owned by the game - not individual players)
		initCoinBank();
		
		//Initialize the players (player objects)
		initPlayers();
		//Randomly select a current player
		this.currentPlayerIndex = new Random().nextInt(gameConfig.NO_OF_PLAYERS);
		//Give each player a random first tile
		assignFirstSetOfTilesToPlayers();
		
		//Initialize the game board
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
		players = new Player[gameConfig.NO_OF_PLAYERS];
		for(int i=0;i<players.length;i++){
			players[i] = new Player(PlayerColor.values()[i],"Player"+(i+1));
		}
			
	}
	
	private void initTileBank(){
		
		tileBank = new Tile[countNoOfTiles()];
		
		//TODO refactor
		int j = -1;
		for(int i = 0; i < gameConfig.NO_OF_RESOURCE_TILES; i++){
			tileBank[++j] = new Tile(TileType.RESOURCES);
		}
		
		for(int i = 0; i < gameConfig.NO_OF_HAZARD_TILES; i++){
			tileBank[++j] = new Tile(TileType.HAZARD);
		}
		
		for(int i = 0; i < gameConfig.NO_OF_MOUNTAIN_TILES; i++){
			tileBank[++j] = new Tile(TileType.MOUNTAIN);
		}
		
		for(int i = 0; i < gameConfig.NO_OF_DRAGON_TILES; i++){
			tileBank[++j] = new Tile(TileType.DRAGON);
		}
		
		for(int i = 0; i < gameConfig.NO_OF_GOLDMINE_TILES; i++){
			tileBank[++j] = new Tile(TileType.GOLDMINE);
		}
		
		for(int i = 0; i < gameConfig.NO_OF_WIZARD_TILES; i++){
			tileBank[++j] = new Tile(TileType.WIZARD);
		}
	}
	
	private void initCoinBank(){
		
		coinBank = new Coin[countNoOfCoins()];
		
		//TODO refactor
		int j = -1;
		for(int i = 0; i < gameConfig.NO_OF_COPPER_COINS_VAL1; i++){
			coinBank[++j] = new Coin(Material.COPPER, gameConfig.COPPER_COINS_VAL1_VAL);
		}
		
		for(int i = 0; i < gameConfig.NO_OF_COPPER_COINS_VAL5; i++){
			coinBank[++j] = new Coin(Material.COPPER, gameConfig.COPPER_COINS_VAL5_VAL);
		}
		
		for(int i = 0; i < gameConfig.NO_OF_SILVER_COINS; i++){
			coinBank[++j] = new Coin(Material.SILVER, gameConfig.SILVER_COINS_VAL);
		}
		
		for(int i = 0; i < gameConfig.NO_OF_GOLD_COINS_VAL50; i++){
			coinBank[++j] = new Coin(Material.GOLD, gameConfig.GOLD_COINS_VAL50_VAL);
		}
		
		for(int i = 0; i < gameConfig.NO_OF_GOLD_COINS_VAL100; i++){
			coinBank[++j] = new Coin(Material.GOLD, gameConfig.GOLD_COINS_VAL100_VAL);
		}
	}
	
	private int countNoOfTiles(){
		
		return 	gameConfig.NO_OF_RESOURCE_TILES 
					+ gameConfig.NO_OF_HAZARD_TILES
					+ gameConfig.NO_OF_MOUNTAIN_TILES 
					+ gameConfig.NO_OF_DRAGON_TILES
					+ gameConfig.NO_OF_GOLDMINE_TILES
					+ gameConfig.NO_OF_WIZARD_TILES;
	}
	
	private int countNoOfCoins(){
		return gameConfig.NO_OF_COPPER_COINS_VAL1 
				+ gameConfig.NO_OF_COPPER_COINS_VAL5 
				+ gameConfig.NO_OF_SILVER_COINS 
				+ gameConfig.NO_OF_GOLD_COINS_VAL50 
				+ gameConfig.NO_OF_GOLD_COINS_VAL100;
	}
	
	private void initGameBoard(){
		//Initialize the game board to hold the game components
		gameBoard = new GameComponents[gameConfig.NO_OF_COLS][gameConfig.NO_OF_ROWS];
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
	
	private void assignFirstSetOfTilesToPlayers(){
		for(int i = 0; i < gameConfig.NO_OF_PLAYERS; i++){
			controller.assignTileToPlayer(i, i);
		}
	}

	
	public EpochCounter getCurrentEpoch() {
		return currentEpoch;
	}

	public void setCurrentEpoch(EpochCounter currentEpoch) {
		this.currentEpoch = currentEpoch;
	}


	public int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}

	public void setCurrentPlayerIndex(int currentPlayerIndex) {
		this.currentPlayerIndex = currentPlayerIndex;
	}


	class Player{
		
		private String name;
		
		private int score;
		private Castle[] rank1Castles;
		private Castle[] rank2Castles;
		private Castle[] rank3Castles;
		private Castle[] rank4Castles;
		
		private Coin[] playerCoins;
		protected Tile[] playerTiles;
		
		private PlayerColor playerColor;
		
		public Player(PlayerColor color, String name){
			
			//Set the player color & name
			this.playerColor = color;			
			this.setName(name);
			
			//Initialize score to zero - at start each players has zero score
			this.setScore(0);
		
			//Initialize the castle objects owned by the players
			initCastles();
			
			//Initialize the coins for the player - at start of game each player is given a certain no of coins
			initPlayerCoins();
			
			//Initialize the tiles owned by the player
			initPlayerTiles();

		}
		
		private void initCastles(){
			//Initialize the castles
			rank1Castles = new Castle[gameConfig.NO_OF_RANK1CASTLES_PER_PLAYER];
			rank2Castles = new Castle[gameConfig.NO_OF_RANK2CASTLES_PER_PLAYER];
			rank3Castles = new Castle[gameConfig.NO_OF_RANK3CASTLES_PER_PLAYER];
			rank4Castles = new Castle[gameConfig.NO_OF_RANK4CASTLES_PER_PLAYER];
			
			//Create the Rank 1 castles
			for(int i=0;i<gameConfig.NO_OF_RANK1CASTLES_PER_PLAYER;i++){
				rank1Castles[i] = new Castle(this.playerColor, Castle.CastleRank.ONE);
			}
			
			//Create the Rank 2 castles
			for(int i=0;i<gameConfig.NO_OF_RANK2CASTLES_PER_PLAYER;i++){
				rank2Castles[i] = new Castle(this.playerColor, Castle.CastleRank.TWO);
			}
			
			//Create the Rank 3 castles
			for(int i=0;i<gameConfig.NO_OF_RANK3CASTLES_PER_PLAYER;i++){
				rank3Castles[i] = new Castle(this.playerColor, Castle.CastleRank.THREE);
			}
			
			//Create the Rank 4 castles
			for(int i=0;i<gameConfig.NO_OF_RANK4CASTLES_PER_PLAYER;i++){
				rank4Castles[i] = new Castle(this.playerColor, Castle.CastleRank.FOUR);
			}
			
		}
		
		private void initPlayerCoins(){
			playerCoins = new Coin[countNoOfCoins()];
			playerCoins[0] = new Coin(Material.GOLD, 50);
		}
		
		private void initPlayerTiles(){
			playerTiles = new Tile[countNoOfTiles()];
		}
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getScore() {
			return score;
		}

		public void setScore(int score) {
			this.score = score;
		}
	}
	
	public class Config{
		private final int NO_OF_PLAYERS = 4;
		
		private final int NO_OF_RANK1CASTLES_PER_PLAYER = 4;
		private final int NO_OF_RANK2CASTLES_PER_PLAYER = 3;
		private final int NO_OF_RANK3CASTLES_PER_PLAYER = 2;
		private final int NO_OF_RANK4CASTLES_PER_PLAYER = 1;
		
		public final int NO_OF_ROWS = 6;
		public final int NO_OF_COLS = 5;
		
		public final String BOARD_AREA_COLOR = "#000000";
		public final String BOARD_AREA_FIELD_COLOR1 = "#E0E0E0";
		public final String BOARD_AREA_FIELD_COLOR2 = "#CCCCFF";
		public final String SCORING_AREA_COLOR = "#C0C0C0"; 
		public final String PLAYER_INFO_AREA_COLOR = "#808080"; 
		
		public final int NO_OF_COPPER_COINS_VAL1 = 19;
		public final int COPPER_COINS_VAL1_VAL = 1;
		
		public final int NO_OF_COPPER_COINS_VAL5 = 12;
		public final int COPPER_COINS_VAL5_VAL = 5;
		
		public final int NO_OF_SILVER_COINS = 20;
		public final int SILVER_COINS_VAL = 10;
		
		public final int NO_OF_GOLD_COINS_VAL50 = 8;
		public final int GOLD_COINS_VAL50_VAL = 50;
		
		public final int NO_OF_GOLD_COINS_VAL100 = 4;
		public final int GOLD_COINS_VAL100_VAL = 4;
		
		public final int NO_OF_RESOURCE_TILES = 12;
		public final int NO_OF_HAZARD_TILES = 6;
		public final int NO_OF_MOUNTAIN_TILES = 2;
		public final int NO_OF_DRAGON_TILES = 1;
		public final int NO_OF_GOLDMINE_TILES = 1;
		public final int NO_OF_WIZARD_TILES = 1;
	}
	
	public enum PlayerColor{
		RED, YELLOW, BLUE, GREEN;
	}

}
