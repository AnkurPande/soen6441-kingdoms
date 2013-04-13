package controller;

import java.io.File;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import components.Castle;
import components.EpochCounter;
import components.GameComponents;
import components.Placeholder;
import components.Tile;
import components.Tile.TileType;

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
	
	/**
	 * This method draws a tile from the tile bank and places it on the specified row and coloumn of the game board.
	 * 
	 * @param rowOfGameBoard The row index of the game board to place the tile.
	 * @param colOfGameBoard The column index of the game board to place the tile.
	 * @return Returns true if draw and place was successful - otherwise returns false.
	 */
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
	
	/**
	 * This method draws a tile from the top of the tile bank and returns that tile.
	 * This takes care of reducing the no of tiles in the tile bank.
	 * 
	 * @return The tile that has been drawn from the top of the games tile bank.
	 * @throws Exception
	 */
	private Tile drawTile() throws Exception{
		if(game.tileBank.size() > 0 ){
			return game.tileBank.remove(0);
		}
		else{
			throw new Exception("Tile Bank empty!");
		}
	}
	
	/**
	 * This method takes the player's tile (the first tile given to the player at the beginning of the game) and places it on the game board.
	 * 
	 * @param playerIndex The index of the player whose tile is to be taken.
	 * @param rowOfGameBoard The row where the tile is to be placed.
	 * @param colOfGameBoard The column where the tile is to be placed.
	 * @return Returns true if placing the first tile was successful - otherwise returns false.
	 */
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
	
	/**
	 * This method checks if a particular player has the first tile in hand - or is it already placed on the game board.
	 * 
	 * @param playerIndex The index of the player whose tile is to be checked.
	 * @return Returns true if the player has the first tile in hand - otherwise returns false.
	 */
	public boolean hasPlayerFirstTile(int playerIndex){
		if(!isValidPlayerIndex(playerIndex)){
			game.gameActionLog += "Action 'check if player has first tile' failed due to invalid player index.";
			return false;
		}
		
		if(game.players[playerIndex].playerTiles.size() > 0){
			return true;
		}
		
		return false;
	}
	
	/**
	 * This method places a castle on the game board at the specified location.
	 * 
	 * @param playerIndex The player whose castle is to be placed.
	 * @param rankOfCastle The rank of the castle that is to be placed.
	 * @param rowOfGameBoard The row index of the game board where the castle is to be placed. 
	 * @param colOfGameBoard The column index of the game where the castle is to be placed.
	 * @return Returns true if placing of the castle was successful - otherwise returns false.
	 */
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
	
	/**
	 * Checks if the specified player index is valid for this game.
	 * @param playerIndex The player index number that is to be validated.
	 * @return Returns true if the player index is a valid player index for this game - otherwise returns false.
	 */
	private boolean isValidPlayerIndex(int playerIndex){
		if(playerIndex < 0 || playerIndex > game.players.length){
			return false;
		}
		
		return true;
	}
	
	/**
	 * Checks if a player has a particular rank of castles in hand (not on the board).
	 * @param playerIndex The player whose castle stock is to be checked.
	 * @param rankOfCastle The rank of the castle that is to be checked.
	 * @return Returns true if the player specified has the castles of specified rank in hand - otherwise returns false.
	 */
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
	
	/**
	 * Checks if a the specified place on the game board is vacant (a game component such as castle/tile to be placed at that location).
	 * @param rowIndex The row index of the game board to check.
	 * @param columnIndex The column index of the game board to check.
	 * @return Returns true if the specified location of the game board is vacant - otherwise returns false.
	 */
	public boolean isGameBoardPlaceValidAndVacant(int rowIndex, int columnIndex){
		if(columnIndex < 0 || columnIndex >= game.gameBoard.length){
			return false;
		}
		
		if(rowIndex < 0 || rowIndex >= game.gameBoard[0].length){
			return false;
		}
		
		if( !(game.gameBoard[columnIndex][rowIndex] instanceof Placeholder)){
			return false;
		}
		
		return true;
	}
	
	/**
	 * Places any game component (tiles/castles, etc.) on the game board.
	 * The objective of having this method is to abstract placing of any game component on the game board.
	 * Note that this is private method and should not be accessed by outside classes.
	 * This is for use of other methods within this class.
	 * @param gc The game component to place.
	 * @param row The row index where the game component is to be placed.
	 * @param col The col index where the game component is to be placed.
	 */
	private void placeComponentOnGameBoard(GameComponents gc, int row, int col){
		
		if(isGameBoardPlaceValidAndVacant(row, col)){
			game.gameBoard[col][row] = gc;
			game.setEmptyPlacesOnBoard(game.getEmptyPlacesOnBoard()-1);
		}
	}
	
	/**
	 * Plays one epoch (executes all the player turns until the game board is used up).
	 */
	private void playOneEpoch(){

		int currentPlayerIndex = game.getCurrentPlayerIndex();
		int noOfPlayers = game.players.length;
		
		boolean loopCondition = true;
		int iteration = 0;
		
		int currentEpochNo = game.getCurrentEpoch().getEpochNo();
		
		game.gameActionLog += "Starting Epoch No:" + currentEpochNo + ".";
		
		//setPlayerStrategies();
		setPlayerStrategiesByUserInput();
		
		while(loopCondition){
			game.players[currentPlayerIndex].getStrategy().selectAndMakeMove(this);
			
			currentPlayerIndex = (++currentPlayerIndex)%noOfPlayers;
			game.setCurrentPlayerIndex(currentPlayerIndex);
			
			game.gameActionLog += "Empty spaces on board:" + game.getEmptyPlacesOnBoard() + ".";
			iteration++;
			if(game.getEmptyPlacesOnBoard() <= 0 || iteration > game.getGameConfig().MAX_ITERATIONS_PER_EPOCH){
				loopCondition = false;
			}
		}
	}
	
	/**
	 * Plays the epoch specified.
	 * @param epochNoToPlay The epoch no to play.
	 */
	public void playEpoch(int epochNoToPlay){
		int currentEpochNo = game.getCurrentEpoch().getEpochNo();
		
		if(currentEpochNo == epochNoToPlay){
			playOneEpoch();
		}
		else
		{
			game.gameActionLog += "Can't start epoch no " + epochNoToPlay +" as games current epoch is not the same as requested epoch to play - current epoch no is:" + currentEpochNo + ".";
		}
	}
	
	/**
	 * Plays all epochs sequentially and ends the game.
	 */
	public void playAllEpochs(){
		playEpoch(1);
		calculateScore();
		resetGameAtEpochEnd();
		
		playEpoch(2);
		calculateScore();
		resetGameAtEpochEnd();
		
		playEpoch(3);
		calculateScore();
		resetGameAtEpochEnd();
		
		game.setGameEnded(true);
		
	}
	
	/**
	 * Resets the game state to the epoch beginning.
	 */
	public void resetGameAtEpochEnd() {
		displayScores();
		setHighestScorerOfEpochToCurrentPlayer();
		incrementEpoch();
		resetCastles();
		resetTiles();
		game.initGameBoard();
	}
	
	/**
	 * Sets the highest scorer of the current epoch to the next player.
	 */
	private void setHighestScorerOfEpochToCurrentPlayer() {
		int higestScore = 0, highestScorerIndex = -1;
		for(int i = 0 ; i < game.players.length ; i++){
			int currentEpochScore = game.players[i].getEpochScore(game.getCurrentEpoch().getEpochNo() - 1);
			if( currentEpochScore > higestScore){
				higestScore = currentEpochScore;
				highestScorerIndex = i;
			}
		}
		
		if(highestScorerIndex > -1){
			game.setCurrentPlayerIndex(highestScorerIndex);
		}
		
		System.out.println("Highest scorer index :" + highestScorerIndex);
	}

	/**
     * Displays the scores of the players after epoch end.
     */
    private void displayScores() {
    	System.out.println("-----------------------------------");
    	System.out.println("Displaying Scores");
    	System.out.println("-----------------------------------");
    	
		for(int i = 0; i < game.players.length ; i++){
			System.out.println(game.players[i].getScoreDescription());
		}
	}
	
	/**
	 * Increments the current epoch to the next one.
	 */
	private void incrementEpoch() {
		int currentEpochNo = game.getCurrentEpoch().getEpochNo();
		int nextEpochNo = currentEpochNo + 1;
		if(nextEpochNo <= 3){
			game.setCurrentEpoch(new EpochCounter(nextEpochNo));		
		}
		else if(nextEpochNo == 4){
			game.setCurrentEpoch(new EpochCounter(nextEpochNo-1));
		}
	}
	
	/**
	 * Reset the castles on the game board.
	 * Returns the rank 1 castles to the player - returns other castles to the game box.
	 */
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
	
	/**
	 * Reset the tiles on the game board.
	 */
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
	
	/**
	 * Gets the player strategy for the player specified.
	 * 
	 * @param playerIndex The player index whose strategy is to be fetched.
	 * @return Returns the playing strategy of the player index specified.
	 */
	private PlayingStrategy getPlayerStrategy(int playerIndex){
		
		switch(playerIndex){
			case 0:	return new PlayingStrategyRandom();
			case 1: return new PlayingStrategyMin();
			case 2: return new PlayingStrategyMax();
			case 3: return new PlayingStrategyTryOneByOne();
			default:return null;
		}		
	}
	
	
	private PlayingStrategy getPlayerStrategyByUserInput(int strategyIndex){

		switch(strategyIndex){
			case 0:	return new PlayingStrategyRandom();
			case 1: return new PlayingStrategyMin();
			case 2: return new PlayingStrategyMax();
			case 3: return new PlayingStrategyTryOneByOne();
			case 4: return new PlayingStrategyHuman();
			default:return null;
		}		
	}
	
	/**
	 * Sets the playing strategies for all the players.
	 */
	private void setPlayerStrategies(){
		for(int i =  0; i <game.players.length ; i++){
			game.players[i].setStrategy(getPlayerStrategy(i));
		}
	}
	
	private void setPlayerStrategiesByUserInput(){
		for(int i =  0; i <game.players.length ; i++){
			System.out.println("------------------------------------------------------------------------------------");
			System.out.println("Please select strategy for player index: " + i);
			System.out.println("------------------------------------------------------------------------------------");
			int selectedStrategyIndex = getUserInputStrategy();
			game.players[i].setStrategy(getPlayerStrategyByUserInput(selectedStrategyIndex));
		}
	}
	
	private int getUserInputStrategy(){
		int maxStrategyIndex = 4;
		int input;
						
	    Scanner sc = new Scanner(System.in);
	    
		do {
			System.out.println("------------------------------------------------------------------------------------");
			System.out.println("Strategy 0 : Random. Strategy 1 : Min. Strategy 2 : Max. Strategy 3 : OneByOne. Strategy 4 : Human.");
			System.out.println("------------------------------------------------------------------------------------");
			System.out.print("Please enter the strategy index number (whole no between 0 and " + maxStrategyIndex + ") :");
			while (!sc.hasNextInt()) {
				System.out.println("Invalid input! Please try again:");
				sc.next();
				System.out.print("Please enter the strategy index number (whole no between 0 and " + maxStrategyIndex + ") :");
			}
			
			input = sc.nextInt();
			
			if(input < 0 || input > maxStrategyIndex){
				System.out.println("Input must be within the specified range! Please try again:");
			}
			
		} while (input < 0 || input > maxStrategyIndex);
		
		return input;
	}
	
	
	public int getUserInputNoOfPlayers(){
		int maxNoOfPlayers = 4;
		int input;
						
	    Scanner sc = new Scanner(System.in);
	    
		do {
			System.out.print("Please enter the no of players (whole no between 2 and " + maxNoOfPlayers + ") :");
			while (!sc.hasNextInt()) {
				System.out.println("Invalid input! Please try again:");
				sc.next();
				System.out.print("Please enter the no of players (whole no between 2 and " + maxNoOfPlayers + ") :");
			}
			
			input = sc.nextInt();
			
			if(input < 0 || input > maxNoOfPlayers){
				System.out.println("Input must be within the specified range! Please try again:");
			}
			
		} while (input < 2 || input > maxNoOfPlayers);
		
		return input;
	}
	
	/**
	 * Gets the next vacant space on board.
	 * @return Returns the row and column index of the next vacant space on the game board as an int array. The first item of this int array is the row index of the vacant space, the second item is the column index. 
	 */
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
	
	/**
	 * Returns the rank of next available castles that the player has in hand starting from rank one castles.
	 * 
	 * @param playerIndex The index of the player whose castle stock is to be looked at.
	 * @return Returns the rank of the next available castle stock of the player.
	 */
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
	
	/**
	 * Calculates the score.
	 */
	public void calculateScore(){
		int noOfCols = game.gameBoard.length;
		int noOfRows = game.gameBoard[0].length;
		
		int[][] colScores1 = new int[noOfRows][1];
		int[][] colScores2 = new int[noOfRows][1];
		
		int[][] rowScores1 = new int[noOfCols][1];
		int[][] rowScores2 = new int[noOfCols][1];
		
		boolean divideRow = false;
		boolean divideCol = false;
		
		boolean[] rowHasGoldMineTile = new boolean[noOfCols];
		boolean[] colHasGoldMineTile = new boolean[noOfRows];
		boolean[] rowHasDragonTile = new boolean[noOfCols];
		boolean[] colHasDragonTile = new boolean[noOfRows];
		
		
		//Check rows and columns for the existence of Gold Mine and Dragon tiles.
		//-------------------------------------------------------------------------------------
		for(int i = 0 ; i < noOfCols; i++){
			for(int j = 0 ; j < noOfRows; j++){
				if(game.gameBoard[i][j] instanceof components.Tile){

					Tile tempTile = (Tile)game.gameBoard[i][j];

					if(tempTile.getType() == TileType.GOLDMINE){
						rowHasGoldMineTile[i] = true;
						colHasGoldMineTile[j] = true;
					}
					
					if(tempTile.getType() == TileType.DRAGON){
						rowHasDragonTile[i] = true;
						colHasDragonTile[j] = true;
					}
				}
			}
		}
		//-------------------------------------------------------------------------------------
		
		//Calculate row wise score of the board
		//-------------------------------------------------------------------------------------
		for(int i = 0 ; i < noOfCols; i++){
			for(int j = 0 ; j < noOfRows; j++){
				if(game.gameBoard[i][j] instanceof components.Tile){

					Tile tempTile = (Tile)game.gameBoard[i][j];
					int multiplyFactor = 1;

					if(tempTile.getType() == TileType.MOUNTAIN){
						divideRow = true;
					}
					
					if(rowHasGoldMineTile[i]){
						multiplyFactor = 2;
					}
					
					if(rowHasDragonTile[i] && tempTile.getType() == TileType.RESOURCES){
						multiplyFactor = 0;
					}

					if(divideRow){
						rowScores2[i][0] += tempTile.getValue()*multiplyFactor;
					}else{
						rowScores1[i][0] += tempTile.getValue()*multiplyFactor;
					}
				}
			}

			divideRow = false;
		}
		//-------------------------------------------------------------------------------------
		
		//Calculate column wise score of the board
		//-------------------------------------------------------------------------------------
		for(int i = 0 ; i < noOfRows; i++){
			for(int j = 0 ; j < noOfCols ; j++){
				divideCol = updateScoreColumnWise(i, j, colScores1, colScores2, divideCol, colHasGoldMineTile[i], colHasDragonTile[i]);
			}
			
			divideCol = false;
		}
		//-------------------------------------------------------------------------------------
		
		int[] playerScoreByRow = new int[game.players.length];
		int[] playerScoreByColumn = new int[game.players.length];
		int[] playerScoreTotal = new int[game.players.length];
		
		//Calculate player scores by row
		//-------------------------------------------------------------------------------------
		for(int i = 0 ; i < noOfCols; i++){
			for(int j = 0 ; j < noOfRows; j++){
				if(game.gameBoard[i][j] instanceof components.Castle){
					Castle tempCastle = (Castle)game.gameBoard[i][j];
					
					int rankIncreaseBy = 0;
					if(isLocationAdjacentToWizardTile(j,i)){
						rankIncreaseBy = 1;
					}
					
					for(int k = 0 ; k < game.players.length ; k++){
						if(game.players[k].getPlayerColor() == tempCastle.getColor()){
							playerScoreByRow[k] += (tempCastle.getRankValue() + rankIncreaseBy)* (rowScores1[i][0] + rowScores2[i][0]);
						}
					}
				}
			}
		}
		//-------------------------------------------------------------------------------------
		
		//Calculate player scores by column
		//-------------------------------------------------------------------------------------
		for(int i = 0 ; i < noOfRows; i++){
			for(int j = 0 ; j < noOfCols ; j++){
				if(game.gameBoard[j][i] instanceof components.Castle){
					Castle tempCastle = (Castle)game.gameBoard[j][i];
					

					int rankIncreaseBy = 0;
					if(isLocationAdjacentToWizardTile(i,j)){
						rankIncreaseBy = 1;
					}
					
					for(int k = 0 ; k < game.players.length ; k++){
						if(game.players[k].getPlayerColor() == tempCastle.getColor()){
							playerScoreByColumn[k] +=  (tempCastle.getRankValue() + rankIncreaseBy) * (colScores1[i][0] + colScores2[i][0]);
						}
					}
				}
			}
		}
		//-------------------------------------------------------------------------------------
		
		//Add the player scores by row and column to get the total score. Then sets the players coin value to the score.
		//-------------------------------------------------------------------------------------
		for(int i = 0; i < playerScoreTotal.length ; i++){
			playerScoreTotal[i] = playerScoreByRow[i] + playerScoreByColumn[i];
			int playersCurrentScore = game.players[i].evaluateCoinValue();
			game.players[i].setEpochScore(game.getCurrentEpoch().getEpochNo() - 1, playerScoreTotal[i]);
			game.players[i].playerCoins.firstElement().setValue(playerScoreTotal[i] + playersCurrentScore);
		}
		//-------------------------------------------------------------------------------------
			
//		System.out.println(rowScores1);
//		System.out.println(rowScores2);
//		System.out.println(colScores1);
//		System.out.println(colScores2);
//		System.out.println(rowHasGoldMineTile);
//		System.out.println(colHasGoldMineTile);
//		System.out.println(playerScoreByRow);
//		System.out.println(playerScoreByColumn);
	}
	
	/**
	 * This method updates the column wise score arrays based on the type of tile in the specified location. 
	 * 
	 * @param rowIndex The row index of the location to check.
	 * @param colIndex The column index of the location to check.
	 * @param colScores1 The 1st array to store the column wise scores (for tiles above any mountain tiles).
	 * @param colScores2 The 2nd array to store the column wise scores (for tiles below the mountain tiles).
	 * @param divideCol Determines whether to divide the columns due to the existence of a mountain tile in the column. 
	 * @param goldMineImpact Determines whether to include the GoldMine impact (multiply all resource and hazard tile values by 2).
	 * @param dragonTileImpact Determines whether to include the Dragon tile impact (nullify all the resource tiles values).
	 * @return Returns whether to keep the column divided due to an existence of a mountain tile in the column.
	 */
	private boolean updateScoreColumnWise(int rowIndex, int colIndex, int[][] colScores1, int[][] colScores2, boolean divideCol, boolean goldMineImpact, boolean dragonTileImpact){
		
		if(game.gameBoard[colIndex][rowIndex] instanceof components.Tile){
			Tile tempTile = (Tile)game.gameBoard[colIndex][rowIndex];
			int multiplyFactor = 1;
			
			if(tempTile.getType() == TileType.MOUNTAIN){
				divideCol = true;
			}
			
			if(goldMineImpact){
				multiplyFactor = 2;
			}
			
			if(dragonTileImpact && tempTile.getType() == TileType.RESOURCES){
				multiplyFactor = 0;
			}
			
			if(divideCol){
				colScores2[rowIndex][0] += tempTile.getValue()*multiplyFactor;
			}
			else{
				colScores1[rowIndex][0] += tempTile.getValue()*multiplyFactor;
			}
		}
		
		return divideCol;
	}
	
	/**
	 * Method to check if a location on the Game Board is orthogonally adjacent to a wizard tile.
	 * 
	 * @param rowIndex The row index of the location on the Game Board to check.
	 * @param columnIndex The column index of the location on the Game Board to check.
	 * @return Returns true if the specified location has an orthogonally adjacent wizard tile - returns false otherwise. If the specified location is invalid also returns false.
	 */
	private boolean isLocationAdjacentToWizardTile(int rowIndex, int columnIndex){
		
		boolean wizardTileAbove = hasLocationWizardTile(rowIndex, columnIndex - 1);
		boolean wizardTileBelow = hasLocationWizardTile(rowIndex, columnIndex + 1);
		boolean wizardTileOnLeft = hasLocationWizardTile(rowIndex - 1, columnIndex);
		boolean wizardTileOnRight = hasLocationWizardTile(rowIndex + 1, columnIndex);
		
		return (wizardTileAbove || wizardTileBelow || wizardTileOnLeft || wizardTileOnRight);
	}
	
	/**
	 * Method to check if a particular location has a Wizard tile.
	 * 
	 * @param rowIndex The row index of the location on the Game Board to check.
	 * @param columnIndex The column index of the location on the Game Board to check.
	 * @return Returns true if the specified location has a wizard tile - returns false otherwise. If the specified location is invalid also returns false.
	 */
	private boolean hasLocationWizardTile(int rowIndex, int columnIndex){
		
		boolean result = false;
		
		if(rowIndex < 0 || rowIndex >= game.gameBoard[0].length){
			return false;
		}
		
		if(columnIndex < 0 || columnIndex >= game.gameBoard.length){
			return false;
		}
		
		if(game.gameBoard[columnIndex][rowIndex] instanceof components.Tile){
			Tile temp = (Tile)game.gameBoard[columnIndex][rowIndex];
			
			if(temp.getType() == TileType.WIZARD){
				result = true;
			}
		}
		
		return result;
	}
}
