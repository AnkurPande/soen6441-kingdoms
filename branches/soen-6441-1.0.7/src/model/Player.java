package model;

import java.util.Vector;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import model.GameInstance.PlayerColor;

import components.Castle;
import components.Castle.CastleRank;
import components.Coin;
import components.Tile;
import components.Coin.Material;

/**
 * This class creates the player object.
 * The parameters of a player such as color, name, etc. is managed by this class.
 * @author Team B
 */
public class Player{
	
	private String name;
	
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
	
}
