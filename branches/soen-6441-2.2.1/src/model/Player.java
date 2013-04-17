package model;

import java.util.Vector;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import model.GameInstance.PlayerColor;

import components.Castle;
import components.Castle.CastleRank;
import components.Coin;
import components.Tile;
import controller.PlayingStrategy;

/**
 * This class creates the player object.
 * The parameters of a player such as color, name, etc. is managed by this class.
 * @author Team B
 */
@XmlRootElement
public class Player{

	private String name;

	@XmlTransient
	private PlayingStrategy strategy;

	@XmlElementWrapper(name="rank1Castles")
	@XmlElement(name="castle") 
	public Vector<Castle> rank1Castles;

	@XmlElementWrapper(name="rank2Castles")
	@XmlElement(name="castle") 
	public Vector<Castle> rank2Castles;

	@XmlElementWrapper(name="rank3Castles")
	@XmlElement(name="castle")
	public Vector<Castle> rank3Castles;

	@XmlElementWrapper(name="rank4Castles")
	@XmlElement(name="castle")
	public Vector<Castle> rank4Castles;

	@XmlElementWrapper(name="playerCoins")
	@XmlElement(name="coin")
	public Vector<Coin> playerCoins;

	@XmlElementWrapper(name="playerTiles")
	@XmlElement(name="tile")
	public Vector<Tile> playerTiles;

	private PlayerColor playerColor;

	private Config gameConfig;
	
	@XmlElementWrapper(name="playerScoreByEpoch")
	@XmlElement(name="epochScore")
	private int[] epochScore = new int[6];


	/**
	 * Default constructor.
	 */
	public Player(){

	}

	/**
	 * Creates a player with a specified color, name and configuration.
	 * 
	 * @param color The color of the player - is linked with the color of the players castles.
	 * @param name The name of the player.
	 * @param config The configuration object to use for this player. This is coming from the GameInstance that is loading this player.
	 */
	public Player(PlayerColor color, String name, Config config){

		//Set the player color & name
		this.setPlayerColor(color);			
		this.setName(name);

		//Set the configuration object of the current player.
		this.gameConfig = config;

		//Initialize the castle objects owned by the players
		initCastles();

		//Initialize the coins for the player - at start of game each player is given a certain no of coins
		initPlayerCoins();

		//Initialize the tiles owned by the player
		initPlayerTiles();

	}

	/**
	 * Initialize the castle objects of this player.
	 */
	private void initCastles(){
		//TODO refactor

		//Initialize the castles
		rank1Castles = new Vector<Castle>();
		rank2Castles = new Vector<Castle>();
		rank3Castles = new Vector<Castle>();
		rank4Castles = new Vector<Castle>();

		//Create the Rank 1 castles
		for(int i=0;i<gameConfig.NO_OF_RANK1CASTLES_PER_PLAYER;i++){
			Castle temp = new Castle(this.playerColor, CastleRank.ONE);
			String iconFile = "images/castle_" + this.playerColor + "_rank1.png";
			temp.setIconFileName(iconFile);
			rank1Castles.add(temp);
		}

		//Create the Rank 2 castles
		for(int i=0;i<gameConfig.NO_OF_RANK2CASTLES_PER_PLAYER;i++){
			Castle temp = new Castle(this.playerColor, CastleRank.TWO);
			String iconFile = "images/castle_" + this.playerColor + "_rank2.png";
			temp.setIconFileName(iconFile);
			rank2Castles.add(temp);
		}

		//Create the Rank 3 castles
		for(int i=0;i<gameConfig.NO_OF_RANK3CASTLES_PER_PLAYER;i++){
			Castle temp = new Castle(this.playerColor, CastleRank.THREE);
			String iconFile = "images/castle_" + this.playerColor + "_rank3.png";
			temp.setIconFileName(iconFile);
			rank3Castles.add(temp);
		}

		//Create the Rank 4 castles
		for(int i=0;i<gameConfig.NO_OF_RANK4CASTLES_PER_PLAYER;i++){
			Castle temp = new Castle(this.playerColor, CastleRank.FOUR);
			String iconFile = "images/castle_" + this.playerColor + "_rank4.png";
			temp.setIconFileName(iconFile);
			rank4Castles.add(temp);
		}

	}

	/**
	 * Initialize the coin object for this player.
	 */
	private void initPlayerCoins(){
		playerCoins = new Vector<Coin>();
	}

	private void initPlayerTiles(){
		playerTiles = new Vector<Tile>();
	}

	@XmlAttribute
	/**
	 * Gets the name of a player.
	 * @return The name of the player is returned.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of a player.
	 * @param name The name to assign to the player
	 */
	private void setName(String name) {
		this.name = name;
	}

	@XmlAttribute
	/**
	 * Gets the player color.
	 * 
	 * @return The color of the player.
	 */
	public PlayerColor getPlayerColor() {
		return playerColor;
	}

	/**
	 * Set the player color
	 * 
	 * @param playerColor The color to set the player.
	 */
	private void setPlayerColor(PlayerColor playerColor) {
		this.playerColor = playerColor;
	}

	/**
	 * Checks if a player has the first tile in hand (not already placed in board)
	 * 
	 * @return Returns true if the player has tiles in hand - returns false otherwise.
	 */
	public boolean hasFirstTile(){
		if(playerTiles.size() > 0){
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * Getter for the strategy of the player.
	 * @return Returns the players strategy.
	 */
	@XmlTransient
	public PlayingStrategy getStrategy() {
		return strategy;
	}

	/**
	 * Setter for the strategy of the player.
	 * @param newStrategy The new strategy to set the player to.
	 */
	public void setStrategy(PlayingStrategy newStrategy) {
		this.strategy = newStrategy;
	}

	/**
	 * Gets a textual description for the player i.e. castles/tiles in hand, score. 
	 * 
	 * @return Textual description of the players current status is returned.
	 */
	public String getStatusDescription(){
		String description = "";

		description += this.getName() + " -> ";
		description += "Rank 1 castles :" + this.rank1Castles.size() + ",";
		description += "Rank 2 castles :" + this.rank2Castles.size() + ",";
		description += "Rank 3 castles :" + this.rank3Castles.size() + ",";
		description += "Rank 4 castles :" + this.rank4Castles.size() + "|";

		description += "Tile(s) :" + this.playerTiles.size() + "|";
		
		description += getScoreDescription();

		return description;
	}
	
	/**
	 * Gets a textual description of the players score.
	 * 
	 * @return Textual description of the players score.
	 */
	public String getScoreDescription(){
		String description = "";
		
		description += "Epoch 1 score :" + this.getEpochScore(0) + "|";
		description += "Epoch 2 score :" + this.getEpochScore(1) + "|";
		description += "Epoch 3 score :" + this.getEpochScore(2) + "|";
		description += "Epoch 4 score :" + this.getEpochScore(3) + "|";
		description += "Epoch 5 score :" + this.getEpochScore(4) + "|";
		description += "Epoch 6 score :" + this.getEpochScore(5) + "|";
		description += "Total score :" + this.getScoreAllEpochs() + "|";
		description += "Coin value :" + this.evaluateCoinValue() + "|";

		return description;
	}

	/**
	 * Evaluates and returns the players current score based on the coins the player has.
	 * 
	 * @return The score of the player.
	 */
	public int evaluateCoinValue(){

		int score = 0;
		java.util.Iterator<Coin> itr = this.playerCoins.iterator();

		while(itr.hasNext()){
			Coin c = itr.next();
			score += c.getValue();
		}

		return score;
	}
	
	/**
	 * Gets the player score for the specified epoch.
	 * 
	 * @return The player score for the specified epoch.
	 */
	public int getEpochScore(int epochNo) {
		if(epochNo >= 0 && epochNo < epochScore.length){
			return epochScore[epochNo];
		}
		
		return 0;
	}
	
	/**
	 * Sets the player score for the specified epoch no to the specified score.
	 * 
	 * @param epochNo The epoch no whose score is to be set.
	 * @param score The score to set to.
	 */
	public void setEpochScore(int epochNo, int score) {
		if(epochNo >= 0 && epochNo < epochScore.length){
			epochScore[epochNo] = score;
		}
	}
	
	/**
	 * Calculates the player score for all epochs.
	 * @return The players score for all epochs.
	 */
	public int getScoreAllEpochs(){
		int sum = 0;
		
		for(int i = 0 ; i < this.epochScore.length ; i++){
			sum += this.epochScore[i];
		}
		
		return sum;
	}

}
