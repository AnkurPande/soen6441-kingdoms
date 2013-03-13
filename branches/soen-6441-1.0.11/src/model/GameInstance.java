package model;

import java.awt.List;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import components.*;
import components.Coin.Material;
import components.Tile.TileType;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
/**
 * This class represents the instance or model of a game.
 * The status of a game, it's components and players, etc. is stored in objects of this class.
 * @author Team B
 */
public class GameInstance {
	
	private Config gameConfig;
	public String gameActionLog = "";
	
	@XmlElementWrapper(name="players")
	@XmlElement(name="player")
	public Player[] players;
	
	private int currentPlayerIndex;	
	private EpochCounter currentEpoch;
	
	@XmlElementWrapper(name="gameBoard")
	@XmlElement(name="placeOnBoard", nillable=true,defaultValue="")
	public GameComponents[][] gameBoard;
	private int emptyPlacesOnBoard;
	
	@XmlElementWrapper(name="tilebank")
	@XmlElement(name="tile")
	public Vector<Tile> tileBank;
	
	@XmlElementWrapper(name="coinBank")
	@XmlElement(name="coin")
	protected Vector<Coin> coinBank;
	
	private ArrayList<PropertyChangeListener> listeners = new ArrayList<PropertyChangeListener>();

	
	/**
	 * Default constructor. Creates a blank by default.
	 */
	public GameInstance(){
	
	}
	
	/**
	 * Constructor with specifying a Config. This will create a new game as per the configuration of the specified Config object.
	 * 
	 * @param config The configuration to use to create the new game.
	 */
	public GameInstance(Config config){
		this.gameConfig = config;
		createNewGame();		
	}
	
	/**
	 * Method to create a new game. This method initializes all the necessary game items and components. 
	 */
	private void createNewGame(){
		//Initialize the epoch counter
		setCurrentEpoch(new EpochCounter());
		
		//Initialize and shuffle the tiles
		initTileBank();
		shuffleTileBank(tileBank);
		
		//Initialize the coin bank (holds the coins owned by the game - not individual players)
		initCoinBank();
		
		//Initialize the players (player objects)
		initPlayers();
		
		//Randomly select a current player
		this.currentPlayerIndex = new Random().nextInt(gameConfig.NO_OF_PLAYERS);
		
		//Give 50 Gold to each player.
		giveFirstSetOfCoinsToPlayers();
		
		//Give each player a random first tile
		assignOneSetOfTilesToPlayers();
		
		//Initialize the game board
		initGameBoard();
	}
	
	/**
	 * Initialize the player objects of the game.
	 */
	private void initPlayers(){
		//Initialize the player objects
		players = new Player[gameConfig.NO_OF_PLAYERS];
		for(int i=0;i<players.length;i++){
			players[i] = new Player(PlayerColor.values()[i],"Player"+(i+1), this.gameConfig);
		}
			
	}
	
	/**
	 * Initialize the tile bank of the game.
	 */
	private void initTileBank(){
		
		tileBank  = new Vector<Tile>();
		
		//TODO refactor
		for(int i = 0; i < gameConfig.NO_OF_RESOURCE_TILES; i++){
			int tileValue = (i%gameConfig.MAX_VALUE_OF_RESOURCE_TILES) + 1;	//Modulus is used to ensure the value of tile is something between min value (1) and max value (6) for resource tiles
			tileBank.add(new Tile(TileType.RESOURCES, tileValue));
		}
		
		for(int i = 0; i < gameConfig.NO_OF_HAZARD_TILES; i++){
			int tileValue = (-(i%(-gameConfig.MIN_VALUE_OF_HAZARD_TILES))) - 1; //Modulus is used to ensure the value of tile is something between min value(-6) and max value (-1) for this hazard tiles
			tileBank.add(new Tile(TileType.HAZARD, tileValue));
		}
		
		for(int i = 0; i < gameConfig.NO_OF_MOUNTAIN_TILES; i++){
			tileBank.add(new Tile(TileType.MOUNTAIN, gameConfig.VALUE_OF_MOUNTAIN_TILES));
		}
		
		for(int i = 0; i < gameConfig.NO_OF_DRAGON_TILES; i++){
			tileBank.add( new Tile(TileType.DRAGON, gameConfig.VALUE_OF_DRAGON_TILES));
		}
		
		for(int i = 0; i < gameConfig.NO_OF_GOLDMINE_TILES; i++){
			tileBank.add(new Tile(TileType.GOLDMINE, gameConfig.VALUE_OF_GOLDMINE_TILES));
		}
		
		for(int i = 0; i < gameConfig.NO_OF_WIZARD_TILES; i++){
			tileBank.add(new Tile(TileType.WIZARD, gameConfig.VALUE_OF_WIZARD_TILES));
		}
	}
	
	/**
	 * Initialize the coin bank of the game.
	 */
	private void initCoinBank(){
		
		coinBank = new Vector<Coin>();
		
		//TODO refactor
		for(int i = 0; i < gameConfig.NO_OF_COPPER_COINS_VAL1; i++){
			coinBank.add(new Coin(Material.COPPER, gameConfig.COPPER_COINS_VAL1_VAL));
		}
		
		for(int i = 0; i < gameConfig.NO_OF_COPPER_COINS_VAL5; i++){
			coinBank.add(new Coin(Material.COPPER, gameConfig.COPPER_COINS_VAL5_VAL));
		}
		
		for(int i = 0; i < gameConfig.NO_OF_SILVER_COINS; i++){
			coinBank.add( new Coin(Material.SILVER, gameConfig.SILVER_COINS_VAL));
		}
		
		for(int i = 0; i < gameConfig.NO_OF_GOLD_COINS_VAL50; i++){
			coinBank.add( new Coin(Material.GOLD, gameConfig.GOLD_COINS_VAL50_VAL));
		}
		
		for(int i = 0; i < gameConfig.NO_OF_GOLD_COINS_VAL100; i++){
			coinBank.add(new Coin(Material.GOLD, gameConfig.GOLD_COINS_VAL100_VAL));
		}
	}
	
	/**
	 * Method to initialize the the game board.
	 */
	public void initGameBoard(){
		//Initialize the game board to hold the game components
		gameBoard = new GameComponents[gameConfig.NO_OF_COLS][gameConfig.NO_OF_ROWS];
		
		for(int i = 0; i < gameBoard.length; i++){
			for(int j = 0; j < gameBoard[0].length; j++){
				gameBoard[i][j] = new Placeholder();
				emptyPlacesOnBoard++;
			}
		}
	}
	
	/**
	 * Method to shuffle the tiles bank.
	 * 
	 * @param tiles An array of tiles (the tile bank).
	 */
	public static void shuffleTileBank(Vector<Tile> tiles) {
		Collections.shuffle(tiles);
	}

	/**
	 * Method to assign a new random tile to a player. 
	 * This is done on the beginning of a new game.
	 */
	public void assignOneSetOfTilesToPlayers(){
		for(int i = 0; i < players.length; i++){
			Tile temp = tileBank.remove(0);
			players[i].playerTiles.add(temp);
		}
	}
	
	/**
	 * Method to give a set of coins to players at the beginning of the game. As of build 1 - initially every player
	 * is given a gold coin of 50 points.
	 */
	private void giveFirstSetOfCoinsToPlayers(){
		for(int i = 0; i < players.length; i++){
			Coin tempGoldCoin = null;
			for(int j = 0; j < coinBank.size(); j++){
				tempGoldCoin = coinBank.elementAt(j);
				if( (tempGoldCoin.getMaterial() == Coin.Material.GOLD) && (tempGoldCoin.getValue() == 50) ){
					tempGoldCoin = coinBank.remove(j);
					break;
				}
			}
			players[i].playerCoins.add(tempGoldCoin);
		}
	}

	@XmlElement
	/**
	 * Gets the current epoch of the game.
	 * 
	 * @return Returns the current epoch of the game.
	 */
	public EpochCounter getCurrentEpoch() {
		return this.currentEpoch;
	}

	/**
	 * Sets the current epoch of the game.
	 * 
	 * @param newEpoch The new epoch to set the game to.
	 */
	public void setCurrentEpoch(EpochCounter newEpoch) {
		this.currentEpoch = newEpoch;
	}

	@XmlAttribute
	/**
	 * Gets the current players index - i.e. whose turn it is.
	 * 
	 * @return The index of the current player - the player whose turn it is now.
	 */
	public int getCurrentPlayerIndex() {
		return this.currentPlayerIndex;
	}
	
	/**
	 * Sets the current player index to the one defined in the parameter.
	 *  
	 * @param newPlayerIndex The new player index - change turn to this player.
	 */
	public void setCurrentPlayerIndex(int newPlayerIndex) {
		notifyListeners(this, "currentPlayerIndex", this.currentPlayerIndex, newPlayerIndex);
		this.currentPlayerIndex = newPlayerIndex;
	}
	
	/**
	 * Getter method for the GameConfig object (which contains the game configuration data)
	 * @return The game's game configuration object is returned.
	 */
	public Config getGameConfig() {
		return gameConfig;
	}
	
	/**
	 * Setter method for the GameConfig object of the game.
	 * @param gameConfig The new GameConfig object that is to be set to the current game.
	 */
	public void setGameConfig(Config gameConfig) {
		this.gameConfig = gameConfig;
	}
	
	public int getEmptyPlacesOnBoard() {
		return emptyPlacesOnBoard;
	}

	public void setEmptyPlacesOnBoard(int emptyPlacesOnBoard) {
		this.emptyPlacesOnBoard = emptyPlacesOnBoard;
	}
	
	public int getPlayerIndexByColor(PlayerColor color){
		for(int i = 0; i < players.length ; i++){
			if(players[i].getPlayerColor() == color){
				return i;
			}
		}
		return -1;
	}
	
	private void notifyListeners(Object object, String property, int oldPlayerIndex, int newPlayerIndex) {
		for (PropertyChangeListener prop : listeners) {
			prop.propertyChange(new PropertyChangeEvent(this, property, oldPlayerIndex, newPlayerIndex));
		}
	}

	public void addChangeListener(PropertyChangeListener newListener) {
		listeners.add(newListener);
	}
	
	/**
	 * An enum to restrict the player colors.
	 *
	 */
	public enum PlayerColor{
		RED, YELLOW, BLUE, GREEN;
	}

}
