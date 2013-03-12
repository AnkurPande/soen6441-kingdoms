package controller;

import java.io.File;
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

/**
 * The GameController class is for containing the game play logic and performing actions on the game state (GameInstance).
 * This class also has methods to load and save game states - in xml format from/to disc.
 * @author Team B
 */
public class GameController {
	
	private GameInstance game;
	private Config gameConfig;
	
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
		
		game.gameActionLog += "Player" + game.getCurrentPlayerIndex() + " requested to perform action 'draw and place tile' at row: " + rowOfGameBoard + ", col: " + colOfGameBoard + ".";
		
		if(!isGameBoardPlaceValidAndVacant(rowOfGameBoard, colOfGameBoard)){
			game.gameActionLog += "Action 'draw and place tile' failed due to specified location on game board isn't valid or isn't vacant.";
			return false;
		}
		
		Tile tileToPlace = null;
		try {
			tileToPlace = drawTile();
		} catch (Exception e) {
			game.gameActionLog += "Action 'draw and place tile' failed - tile bank has no more tiles.";
			return false;
		}
		
		placeComponentOnGameBoard(tileToPlace,rowOfGameBoard,colOfGameBoard);
		game.gameActionLog += "Player" + game.getCurrentPlayerIndex() + " request to perform action 'draw and place tile' succeeded.";
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
		
		game.gameActionLog += "Player" + game.getCurrentPlayerIndex() + " requested to perform action 'place first tile' at row: " + rowOfGameBoard + ", col: " + colOfGameBoard + ".";
		
		if(!isValidPlayerIndex(playerIndex)){
			game.gameActionLog += "Action 'place first tile' failed due to invalid player index.";
			return false;
		}
		
		Tile playersFirstTile = null;
		if(!hasPlayerFirstTile(playerIndex)){
			game.gameActionLog += "Action 'place first tile' failed as player's first tile is already placed.";			
			return false;
		}
		
		playersFirstTile = game.players[playerIndex].playerTiles.remove(0);
		placeComponentOnGameBoard(playersFirstTile,rowOfGameBoard,colOfGameBoard);
		game.gameActionLog += "Player" + game.getCurrentPlayerIndex() + " request to perform action 'place first tile' succeeded.";
		return true;
	}
	
	private boolean hasPlayerFirstTile(int playerIndex){
		if(!isValidPlayerIndex(playerIndex)){
			game.gameActionLog += "Action 'check if player has first tile' failed due to invalid player index.";
			return false;
		}
		
		if(game.players[playerIndex].playerTiles.size() > 0){
			return true;
		}
		
		return false;
	}
	
	public boolean placeCastle(int playerIndex, Castle.CastleRank rankOfCastle, int rowOfGameBoard, int colOfGameBoard){
		
		game.gameActionLog += "Player" + game.getCurrentPlayerIndex() + " requested to perform action 'place castle' at row: " + rowOfGameBoard + ", col: " + colOfGameBoard + ".";
		
		if(!isValidPlayerIndex(playerIndex)){
			game.gameActionLog += "Action 'place castle' failed due to invalid player.";
			return false;
		}
		
		if(!hasPlayerCastlesInHand(playerIndex, rankOfCastle)){
			game.gameActionLog += "Action 'place castle' failed due to invalid castle rank or player has no more of castles of specified rank.";
			return false;
		}
		
		if(!isGameBoardPlaceValidAndVacant(rowOfGameBoard, colOfGameBoard)){
			game.gameActionLog += "Action 'place castle' failed due to specified location on game board isn't valid or isn't vacant.";
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
		
		placeComponentOnGameBoard(castleToPlace,rowOfGameBoard,colOfGameBoard);
		game.gameActionLog += "Player" + game.getCurrentPlayerIndex() + " request to perform action 'place castle' succeeded.";
		
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
	
	private void placeComponentOnGameBoard(GameComponents gc, int row, int col){
		game.gameBoard[col][row] = gc;
		game.setEmptyPlacesOnBoard(game.getEmptyPlacesOnBoard()-1);
	}
	
	public void playOneEpoch(){

		int currentPlayerIndex = game.getCurrentPlayerIndex();
		int noOfPlayers = game.players.length;
		
		boolean loopCondition = true;
		int iteration = 0;
		
		int currentEpochNo = game.getCurrentEpoch().getCurrentEpochNo();
		
		game.gameActionLog += "Starting Epoch No:" + currentEpochNo + ".";
		
		while(loopCondition){
			PlayingStrategy strategy = getPlayerStrategy(currentPlayerIndex);
			strategy.selectAndMakeMove(this);
			
			currentPlayerIndex = (++currentPlayerIndex)%noOfPlayers;
			game.setCurrentPlayerIndex(currentPlayerIndex);
			
			game.gameActionLog += "Empty spaces on board:" + game.getEmptyPlacesOnBoard() + ".";
			iteration++;
			if(game.getEmptyPlacesOnBoard() <= 0 || iteration > game.getGameConfig().MAX_ITERATIONS_PER_EPOCH){
				loopCondition = false;
			}
		}
		
		game.getCurrentEpoch().setCurrentEpochNo(++currentEpochNo);
	}
		
	public void playEpoch(int epochNoToPlay){
		int currentEpochNo = game.getCurrentEpoch().getCurrentEpochNo();
		
		if(currentEpochNo == epochNoToPlay){
			playOneEpoch();
		}
		else
		{
			game.gameActionLog += "Can't start epoch no " + epochNoToPlay +" as games current epoch is not the same as requested epoch to play - current epoch no is:" + currentEpochNo + ".";
		}
	}
	
	public void playAllEpochs(){
		playEpoch(1);
		resetGameAtEpochEnd();
		playEpoch(2);
		resetGameAtEpochEnd();
		playEpoch(3);
		
	}
	
	public void resetGameAtEpochEnd() {
		resetCastles();
		resetTiles();
		game.initGameBoard();
	}	

	private void resetCastles() {
		
		for(int i = 0; i < game.gameBoard.length; i++){
			for(int j = 0; j < game.gameBoard[0].length; j++){
				if(game.gameBoard[i][j] instanceof Castle){
					
					Castle tempCastle = (Castle) game.gameBoard[i][j];
					if(tempCastle.getRank() == Castle.CastleRank.ONE){
						int index = game.getPlayerIndexByColor(tempCastle.getColor());
						game.players[index].rank1Castles.add(tempCastle);
					}
					
					game.gameBoard[i][j] = new Placeholder();
				}
			}
		}
	}
	
	private void resetTiles() {
		for(int i = 0; i < game.gameBoard.length; i++){
			for(int j = 0; j < game.gameBoard[0].length; j++){
				if(game.gameBoard[i][j] instanceof Tile){
					Tile tempTile = (Tile) game.gameBoard[i][j];
					game.tileBank.add(tempTile);
					
					game.gameBoard[i][j] = new Placeholder();
				}
			}
		}
		
		GameInstance.shuffleTileBank(game.tileBank);
		game.assignOneSetOfTilesToPlayers();
	}

	private PlayingStrategy getPlayerStrategy(int playerIndex){
		switch(playerIndex){
			case 0:	return new PlayingStrategyRandom();
			case 1: return new PlayingStrategyMin();
			case 2: return new PlayingStrategyMax();
			case 3: return new PlayingStrategyTryOneByOne();
			default: return null;
		}		
	}
	
	protected int[] nextVacantSpaceOnBoard(){
		int col = -1, row = -1;
		
		outerloop:
		for(int i = 0; i < game.gameBoard.length; i++){
			for(int j = 0; j < game.gameBoard[0].length; j++){
				if(game.gameBoard[i][j] instanceof Placeholder){
					col = i;
					row = j;
					break outerloop;
				}
			}
		}
		
		return new int[]{row,col};
	}
	
	protected Castle.CastleRank nextAvailableCastleRank(int playerIndex){
		if(!isValidPlayerIndex(playerIndex)){
			game.gameActionLog += "Action 'getNextAvailableCastleRank' failed due to invalid player.";
			return null;
		}
		
		if(game.players[playerIndex].rank1Castles.size() > 0){
			return Castle.CastleRank.ONE;
		}
		
		if(game.players[playerIndex].rank2Castles.size() > 0){
			return Castle.CastleRank.TWO;
		}
		
		if(game.players[playerIndex].rank3Castles.size() > 0){
			return Castle.CastleRank.THREE;
		}
		
		if(game.players[playerIndex].rank4Castles.size() > 0){
			return Castle.CastleRank.FOUR;
		}
		
		return null;
	}
}