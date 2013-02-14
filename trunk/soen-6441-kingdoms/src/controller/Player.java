package controller;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import components.Castle;
import components.Castle.CastleRank;
import components.Coin;
import components.Tile;
import components.Coin.Material;
import controller.GameInstance.PlayerColor;


public class Player{
	
	private String name;
	
	private int score;
	
	@XmlElementWrapper(name="rank1Castles")
	@XmlElement(name="castle") 
	public Castle[] rank1Castles;
	
	@XmlElementWrapper(name="rank2Castles")
	@XmlElement(name="castle") 
	public Castle[] rank2Castles;
	
	@XmlElementWrapper(name="rank3Castles")
	@XmlElement(name="castle")
	public Castle[] rank3Castles;
	
	@XmlElementWrapper(name="rank4Castles")
	@XmlElement(name="castle")
	public Castle[] rank4Castles;
	
	@XmlElementWrapper(name="playerCoins")
	@XmlElement(name="coin")
	public Coin[] playerCoins;
	
	@XmlElementWrapper(name="playerTiles")
	@XmlElement(name="tile")
	protected Tile[] playerTiles;
	
	public PlayerColor playerColor;
	
	
	
	private Config gameConfig = new Config();
	
	public Player(){
		
	}
	
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
			rank1Castles[i] = new Castle(this.playerColor, CastleRank.ONE);
			rank1Castles[i].setIconFileName("images/castle_yellow_rank1.jpg");
		}
	
		//Create the Rank 2 castles
		for(int i=0;i<gameConfig.NO_OF_RANK2CASTLES_PER_PLAYER;i++){
			rank2Castles[i] = new Castle(this.playerColor, Castle.CastleRank.TWO);
			rank2Castles[i].setIconFileName("images/castle_green_rank1.jpg");
		}
		
		//Create the Rank 3 castles
		for(int i=0;i<gameConfig.NO_OF_RANK3CASTLES_PER_PLAYER;i++){
			rank3Castles[i] = new Castle(this.playerColor, Castle.CastleRank.THREE);
			rank3Castles[i].setIconFileName("images/castle_red_rank1.jpg");
		}
		
		//Create the Rank 4 castles
		for(int i=0;i<gameConfig.NO_OF_RANK4CASTLES_PER_PLAYER;i++){
			rank4Castles[i] = new Castle(this.playerColor, Castle.CastleRank.FOUR);
			rank4Castles[i].setIconFileName("images/castle_blue_rank1.jpg");
		}
		
	}
	
	private void initPlayerCoins(){
		playerCoins = new Coin[1000];
		playerCoins[0] = new Coin(Material.GOLD, 50);
	}
	
	private void initPlayerTiles(){
		playerTiles = new Tile[1000];
	}
	
	@XmlAttribute
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@XmlAttribute
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	//calculate total score as per game continues
	public int  total_score()
	{
		int totalrow=0;
		int totalcolumn=0;
		for(int row=0; row<6;row++ ){
			for(int column=0;column<5;column++){
				totalcolumn+=totalcolumn;
			}
			totalrow+= totalrow;
		}
		return totalrow+totalcolumn;
	}
}
