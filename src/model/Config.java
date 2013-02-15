package model;

public class Config{
		
	public final int NO_OF_PLAYERS;
	public final int NO_OF_RANK1CASTLES_PER_PLAYER;
	
	public final int NO_OF_RANK2CASTLES_PER_PLAYER = 3;
	public final int NO_OF_RANK3CASTLES_PER_PLAYER = 2;
	public final int NO_OF_RANK4CASTLES_PER_PLAYER = 1;
	
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
	
	public Config(int no_of_players){
		if(!(no_of_players >= 2 && no_of_players <= 4)){
			System.out.println("Invalid no of players. Resetting to 4.");
			no_of_players = 4;
		}
		
		int no_of_rank1castles = 0;
		switch(no_of_players){
			case 2: no_of_rank1castles = 4;
					break;
			case 3: no_of_rank1castles = 3;
					break;
			case 4: no_of_rank1castles = 2;
					break;
			default:no_of_rank1castles = 2;
					break;
		}
		
		NO_OF_PLAYERS = no_of_players;
		NO_OF_RANK1CASTLES_PER_PLAYER = no_of_rank1castles;
	}
	
	public Config(){
		NO_OF_PLAYERS = 4;
		NO_OF_RANK1CASTLES_PER_PLAYER = 2;
	}
}