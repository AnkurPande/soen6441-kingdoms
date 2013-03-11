package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import components.Castle;
import components.GameComponents;
import components.Placeholder;
import components.Tile;

import model.Config;
import model.GameInstance;
import model.GameInstance.PlayerColor;
import model.Player;

/**
 * The GameController class is for containing the game play logic and performing actions on the game state (GameInstance).
 * This class also has methods to load and save game states - in xml format from/to disc.
 * @author Team B
 */
public class GameController {
	
	private GameInstance game;
	private Config gameConfig;
	private LinkedList list = new LinkedList();
	private LinkedList sublist1 = new LinkedList();
	private LinkedList sublist2 = new LinkedList();
	private List<GameComponents> listcol = new ArrayList<GameComponents>();

	
	
	/**
	 * Constructor with specifying a GameInstance. Creates a new GameController with a specified GameInstace.
	 * 
	 * @param game The GameInstance which will attached to the controller
	 */
	public GameController(GameInstance game){
		this.setGame(game);
	}
	
	/**
	 * Constructor with specifying a configuration. A new game instance will be created with this configuration.
	 * @param gameConfig The game configuration to use to create the GameInstance.
	 */
	public GameController(Config gameConfig){
		this.gameConfig = gameConfig;
		this.setGame(new GameInstance(this.gameConfig));
	}
	
	/**
	 * Constructor without specifying a GameInstance. Will create a new GameInstance.
	 */
	public GameController(){
		this.gameConfig = new Config();
		this.setGame(new GameInstance(this.gameConfig));
	}
	
	/**
	 * Gets the current GameInstance of the controller.
	 * @return The current game instance of the controller.
	 */
	public GameInstance getGame() {
		return game;
	}
	
	/**
	 * Sets the GameInstance specified to the controller.
	 * 
	 * @param game The GameInstance that is to be set to the controller.
	 */
	public void setGame(GameInstance game) {
		this.game = game;
	}
		
	/**
	 * Wrapper method to load a game state (GameInstance) from disc. This method validates the parameters and calls the method loadGameState for the actual load.
	 * 
	 * @param fileName The file name of the GameInstance saved.
	 * @return The loaded game state converted into a GameInstance object.
	 */
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
	
	public void loadAndSetGame(String fileName){
		this.game = loadGame(fileName);
	}

	/**
	 * Method to load a game state (GameInstance) from disc. Load a valid XML file from disc and converts to a GameInstance object.
	 *  
	 * @param file The file name of the XML file where the GameInstance is saved.
	 * @return The loaded XML file converted to a GameInstance object.
	 */
	private GameInstance loadGameState(File file){
		
		GameInstance gi = null;
		
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(GameInstance.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			gi = (GameInstance) jaxbUnmarshaller.unmarshal(file);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		return gi;
	}
	
	/**
	 * Wrapper method to save a game state (GameInstance) to file in XML format. This method validates the parameter and calls the method "saveGameState" to do the actual save. 
	 * @param fileName The file name where the state of the game is to be saved to.
	 */
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
	
	/**
	 * Method to save a game state (GameInstance) to file in XML format.
	 * 
	 * @param file The name of the file to save the game state as.
	 */
	private void saveGameState(File file){
		
		try {

			JAXBContext jaxbContext = JAXBContext.newInstance(GameInstance.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(this.game, file);
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	public boolean drawAndPlaceTile(int rowOfGameBoard, int colOfGameBoard){
		
		if(!isGameBoardPlaceValidAndVacant(rowOfGameBoard, colOfGameBoard)){
			game.gameActionLog += "Action Draw and Place Tile failed due to specified location on game board isn't valid or isn't vacant.";
			return false;
		}
		
		Tile tileToPlace = null;
		try {
			tileToPlace = drawTile();
		} catch (Exception e) {
			game.gameActionLog += "Action Draw and Place Tile failed - tile bank has no more tiles.";
			return false;
		}
		
		game.gameBoard[colOfGameBoard][rowOfGameBoard] = tileToPlace;
		return true;
	}
	
	private Tile drawTile() throws Exception{
		if(game.tileBank.size() > 0 ){
			return game.tileBank.remove(0);
		}
		else{
			throw new Exception("Tile Bank empty!");
		}
	}
	
	public boolean placeFirstTile(int playerIndex, int rowOfGameBoard, int colOfGameBoard ){
		if(!isValidPlayerIndex(playerIndex)){
			game.gameActionLog += "Action Place First Tile failed due to invalid player index.";
			return false;
		}
		
		Tile playersFirstTile = null;
		if(!hasPlayerFirstTile(playerIndex)){
			game.gameActionLog += "Action Place First Tile failed as player's first tile is already placed.";			
			return false;
		}
		
		playersFirstTile = game.players[playerIndex].playerTiles.remove(0);
		game.gameBoard[colOfGameBoard][rowOfGameBoard] = playersFirstTile;
		return true;
	}
	
	private boolean hasPlayerFirstTile(int playerIndex){
		if(!isValidPlayerIndex(playerIndex)){
			game.gameActionLog += "Action Has Player First Tile failed due to invalid player index.";
			return false;
		}
		
		if(game.players[playerIndex].playerTiles.size() > 0){
			return true;
		}
		
		return false;
	}
	
	public boolean placeCastle(int playerIndex, Castle.CastleRank rankOfCastle, int rowOfGameBoard, int colOfGameBoard){
		if(!isValidPlayerIndex(playerIndex)){
			game.gameActionLog += "Action Place Castle failed due to invalid player.";
			return false;
		}
		
		if(!hasPlayerCastlesInHand(playerIndex, rankOfCastle)){
			game.gameActionLog += "Action Place Castle failed due to invalid castle rank or player has no more of castles of specified rank.";
			return false;
		}
		
		if(!isGameBoardPlaceValidAndVacant(rowOfGameBoard, colOfGameBoard)){
			game.gameActionLog += "Action Place Castle failed due to specified location on game board isn't valid or isn't vacant.";
			return false;
		}
		
		Castle castleToPlace = null;
		if( rankOfCastle == Castle.CastleRank.ONE ){
			castleToPlace = game.players[playerIndex].rank1Castles.remove(0);
		}
		else if( rankOfCastle == Castle.CastleRank.TWO ){
			castleToPlace = game.players[playerIndex].rank2Castles.remove(0);
		}
		else if( rankOfCastle == Castle.CastleRank.THREE ){
			castleToPlace = game.players[playerIndex].rank3Castles.remove(0);
		}
		else if( rankOfCastle == Castle.CastleRank.FOUR ){
			castleToPlace = game.players[playerIndex].rank4Castles.remove(0);
		}
		
		game.gameBoard[colOfGameBoard][rowOfGameBoard] = castleToPlace;
		
		return true;
	}
	
	private boolean isValidPlayerIndex(int playerIndex){
		if(playerIndex < 0 || playerIndex > game.players.length){
			return false;
		}
		
		return true;
	}
	
	private boolean hasPlayerCastlesInHand(int playerIndex, Castle.CastleRank rankOfCastle){
		if( (rankOfCastle == Castle.CastleRank.ONE) && (game.players[playerIndex].rank1Castles.size() > 0) ){
			return true;
		}
		else if( (rankOfCastle == Castle.CastleRank.TWO) && (game.players[playerIndex].rank2Castles.size() > 0)){
			return true;
		}
		else if( (rankOfCastle == Castle.CastleRank.THREE) && (game.players[playerIndex].rank3Castles.size() > 0)){
			return true;
		}
		else if( (rankOfCastle == Castle.CastleRank.FOUR) && (game.players[playerIndex].rank4Castles.size() > 0)){
			return true;
		}
		else{
			return false;
		}
	}
	
	private boolean isGameBoardPlaceValidAndVacant(int row, int col){
		if(col < 0 || col >= game.gameBoard.length){
			return false;
		}
		
		if(row < 0 || row >= game.gameBoard[0].length){
			return false;
		}
		
		if( !(game.gameBoard[col][row] instanceof Placeholder)){
			return false;
		}
		
		return true;
	}
	
	//
	
	
	//
	public void createList(){
		
		int index =0 ;	
		
		for(int i = 0; i<=game.gameBoard[0].length; i++){
			
				if(game.gameBoard[0][i] instanceof Tile){
					Tile tile = (Tile) game.gameBoard[0][i];
						if (tile.getType() == Tile.TileType.MOUNTAIN){
						index = i; 
						}
						break;
				}
		}		
				
		if(!(index == 0)){
			for(int j=0; j<index; j++){
				sublist1.add(list.remove(j)); 
			}
			for(int j=index; j<=game.gameBoard[0].length; j++ ){
				sublist2.add(list.remove(j+1));
			}
		}
		else {
			for(int j = 0; j<=game.gameBoard[0].length; j++){
			list.add(j, game.gameBoard[0][j] );
			}
		}	
		
	}		
			
		
	
	public int calculateScore(LinkedList list){
		
		int RedPlayerCastleValue = 0;
		int YellowPlayerCastleValue = 0;
		int BluePlayerCastleValue = 0;
		int GreenPlayerCastleValue = 0;
		int score = 0;
		int rankupdate =0;
		for(int i = 0; i<=list.size();i++){
						
			if(list.get(i) instanceof Castle){
				Castle castle = (Castle)list.get(i);
				if(i>0){
				if(list.get(i+1) instanceof Tile || list.get(i-1) instanceof Tile){
					Tile tilenext = (Tile)list.get(i+1);
					Tile tilepre = (Tile)list.get(i-1);
				   if(tilenext.getType()== Tile.TileType.WIZARD){
					  	   
				  	if(castle.getColor() == GameInstance.PlayerColor.RED){
					Castle.CastleRank rank = castle.getRank();
					RedPlayerCastleValue = RedPlayerCastleValue + getRankOfCastle(rank);
        
				}
				else if(castle.getColor() == GameInstance.PlayerColor.YELLOW){
					Castle.CastleRank rank = castle.getRank();
					YellowPlayerCastleValue = YellowPlayerCastleValue + getRankOfCastle(rank);
				}
				else if(castle.getColor() == GameInstance.PlayerColor.BLUE){
					Castle.CastleRank rank = castle.getRank();
					BluePlayerCastleValue = BluePlayerCastleValue + getRankOfCastle(rank);
				}
				else if(castle.getColor() == GameInstance.PlayerColor.GREEN){
					Castle.CastleRank rank = castle.getRank();
					YellowPlayerCastleValue = YellowPlayerCastleValue + getRankOfCastle(rank);
				}
				   }
				}
			}
			
		}
		}
		return score;
	}
	
	private int getRankOfCastle(Castle.CastleRank r){
		Castle.CastleRank rank = r;
		int rankValue = 0;
		switch(rank){
		case ONE: rankValue = 1;
			break;
		case TWO: rankValue = 2;
			break;
		case THREE: rankValue = 3;
			break;
		case FOUR: rankValue = 4;
			break;
		}
		
		return rankValue;	
	}
	
	public int calculateResources(LinkedList list){
		int resourceTotal = 0;
		for(int i=0; i<=list.size(); i++)
		{
			if(list.get(i) == Tile.TileType.DRAGON)
			{
				break;
			}
			else if(list.get(i) == Tile.TileType.RESOURCES)
			{
				Tile tile = (Tile) list.get(i);
				resourceTotal = resourceTotal + tile.getValue();
			}	
			
		}
		return resourceTotal;
	}
	
	public int calculateHazards(LinkedList list){
		int hazardTotal = 0;
		for(int i=0; i<=list.size(); i++)
		{
			 if(list.get(i) == Tile.TileType.HAZARD)
			 {
				Tile tile = (Tile) list.get(i);
				hazardTotal = hazardTotal + tile.getValue();
			 }	
			
		}
		return hazardTotal;
	}
	
	public int calculateBaseValue(int resourceTotal, int hazardTotal, LinkedList list){
		int resource = resourceTotal;
		int hazard = hazardTotal;
		int baseValue = 0;
		for(int i=0; i<= list.size(); i++){
			if(list.get(i) == Tile.TileType.GOLDMINE)
			{
				resource = 2*resource;
				hazard = 2*hazard;
				baseValue = resource + hazard;
				break;
			}
		}
		return baseValue;
	}
}