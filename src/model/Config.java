package model;

/**
 * This class contains all the configuration parameters for the game.
 * @author Team B
 */
public class Config{
		
	public final int NO_OF_PLAYERS;
	public final int NO_OF_RANK1CASTLES_PER_PLAYER;
	
	public final int NO_OF_RANK2CASTLES_PER_PLAYER = 3;
	public final int NO_OF_RANK3CASTLES_PER_PLAYER = 2;
	public final int NO_OF_RANK4CASTLES_PER_PLAYER = 1;
	
	public final int NO_OF_ROWS = 6;
	public final int NO_OF_COLS = 5;
	
	//----------------------------------------------------------------------------
	public final String BOARD_AREA_COLOR = "#000000";
	public final String BOARD_AREA_FIELD_COLOR1 = "#E0E0E0";
	public final String BOARD_AREA_FIELD_COLOR2 = "#CCCCFF";
	public final String SCORING_AREA_COLOR = "#C0C0C0"; 
	public final String PLAYER_INFO_AREA_COLOR = "#808080"; 
	public final String GAME_INFO_AREA_COLOR = "#808080"; 
	//----------------------------------------------------------------------------
	
	//----------------------------------------------------------------------------
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
	
	public final int TOTAL_NO_OF_COINS = NO_OF_COPPER_COINS_VAL1 
										+ NO_OF_COPPER_COINS_VAL5 
										+ NO_OF_SILVER_COINS 
										+ NO_OF_GOLD_COINS_VAL50 
										+ NO_OF_GOLD_COINS_VAL100;
	
	//----------------------------------------------------------------------------
	
	//----------------------------------------------------------------------------
	public final int NO_OF_RESOURCE_TILES = 12;
	public final int NO_OF_HAZARD_TILES = 6;
	public final int NO_OF_MOUNTAIN_TILES = 2;
	public final int NO_OF_DRAGON_TILES = 1;
	public final int NO_OF_GOLDMINE_TILES = 1;
	public final int NO_OF_WIZARD_TILES = 1;
	
	public final int TOTAL_NO_OF_TILES = NO_OF_RESOURCE_TILES 
										+ NO_OF_HAZARD_TILES
										+ NO_OF_MOUNTAIN_TILES 
										+ NO_OF_DRAGON_TILES
										+ NO_OF_GOLDMINE_TILES
										+ NO_OF_WIZARD_TILES;
	
	public final int NO_OF_TILES_PER_PLAYER = 1;
	public final int MAX_VALUE_OF_RESOURCE_TILES = 6;
	public final int MIN_VALUE_OF_HAZARD_TILES = -6;
	public final int VALUE_OF_MOUNTAIN_TILES = 0;
	public final int VALUE_OF_DRAGON_TILES = 0;
	public final int VALUE_OF_GOLDMINE_TILES = 0;
	public final int VALUE_OF_WIZARD_TILES = 0;
	//----------------------------------------------------------------------------
	
	public final int MAX_ITERATIONS_PER_EPOCH = 1000;
	//----------------------------------------------------------------------------
	
	public final int COIN_VALUE_TO_END_GAME = 500;
	
	public final int MAX_NO_OF_EPOCHS = 6;
	
	/**
	 * Constructor specifying the number of players. The no of players determines the no of rank 1 castles in the game.
	 * @param noOfPlayers The no of players in the game. 
	 */
	public Config(int noOfPlayers){
		
		if(!(noOfPlayers >= 2 && noOfPlayers <= 4)){
			System.out.println("Invalid no of players. Resetting to 4.");
			noOfPlayers = 4;
		}
		
		NO_OF_PLAYERS = noOfPlayers;
		NO_OF_RANK1CASTLES_PER_PLAYER =  calculateNoOfRank1Castles(NO_OF_PLAYERS);
	}
	
	/**
	 * Default constructor. No of players by default would be 4.
	 */
	public Config(){
		NO_OF_PLAYERS = 4;
		NO_OF_RANK1CASTLES_PER_PLAYER =  calculateNoOfRank1Castles(NO_OF_PLAYERS);
	}
	
	/**
	 * This method calculates the no of rank 1 castles based on the no of players.
	 * This calculation is done by the rules of of the game.
	 * 
	 * @param noOfPlayers The no of players in the game.
	 * @return Returns the no of rank 1 castles computed.
	 */
	private int calculateNoOfRank1Castles(int noOfPlayers){
		
		int noOfRank1Castles = 0;
		switch(noOfPlayers){
		case 2: noOfRank1Castles = 4;
				break;
		case 3: noOfRank1Castles = 3;
				break;
		case 4: noOfRank1Castles = 2;
				break;
		default:noOfRank1Castles = 2;
				break;
		}
		
		return noOfRank1Castles;
		
	}
}