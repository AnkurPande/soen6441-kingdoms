package controller;

import java.io.File;
import java.util.Random;

import components.*;
import components.Coin.Material;
import components.Tile.TileType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GameInstance {
	
	GameController controller;
	public Config gameConfig;
	
	@XmlElementWrapper(name="players")
	@XmlElement(name="player")
	protected Player[] players;
	
	private int currentPlayerIndex;	
	private EpochCounter currentEpoch;
	
	@XmlElementWrapper(name="gameBoard")
	@XmlElement(name="placeOnBoard", nillable=true,defaultValue="")
	public GameComponents[][] gameBoard;
	
	@XmlElementWrapper(name="tilebank")
	@XmlElement(name="tile")
	protected Tile[] tileBank;
	
	@XmlElementWrapper(name="coinBank")
	@XmlElement(name="coin")
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
		
		for(int i = 0; i < gameBoard.length; i++){
			for(int j = 0; j < gameBoard[0].length; j++){
				gameBoard[i][j] = new Placeholder();
			}
		}
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

	@XmlElement
	public EpochCounter getCurrentEpoch() {
		return this.currentEpoch;
	}

	public void setCurrentEpoch(EpochCounter newEpoch) {
		this.currentEpoch = newEpoch;
	}

	@XmlAttribute
	public int getCurrentPlayerIndex() {
		return this.currentPlayerIndex;
	}

	public void setCurrentPlayerIndex(int newPlayerIndex) {
		this.currentPlayerIndex = newPlayerIndex;
	}
	
	public GameInstance loadGame(String fileName){
		
		File file;
		if(fileName == null || fileName == ""){
			file = new File("default_game_save.xml");
		}
		else{
			file = new File(fileName);
		}
		
		return loadGameState(file);
		
	}

	
	public GameInstance loadGameState(File file){
		GameInstance gi = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(GameInstance.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			gi = (GameInstance) jaxbUnmarshaller.unmarshal(file);
			//System.out.println(gi);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		return gi;
	}
	
	public void saveGame(String fileName){
		
		File file;
		if(fileName == null || fileName == ""){
			file = new File("default_game_save.xml");
		}
		else{
			file = new File(fileName);
		}
		
		saveGameState(file);
		
	}
	
	private void saveGameState(File file){
		try {

			JAXBContext jaxbContext = JAXBContext.newInstance(GameInstance.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(this, file);
			//jaxbMarshaller.marshal(this, System.out);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	public enum PlayerColor{
		RED, YELLOW, BLUE, GREEN;
	}

}
