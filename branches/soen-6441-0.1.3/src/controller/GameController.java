package controller;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import model.GameInstance;

/**
 * The GameController class is for containing the game play logic and performing actions on the game state (GameInstance).
 * This class also has methods to load and save game states - in xml format onto disc.
 * @author Team B
 */
public class GameController {
	
	private GameInstance game;
	
	/**
	 * Constructor with specifying a GameInstance. Creates a new GameController with a specified GameInstace.
	 * 
	 * @param game The GameInstance which will attached to the controller
	 */
	public GameController(GameInstance game){
		this.setGame(game);
	}
	
	/**
	 * Constructor without specifying a GameInstance. Will create a new GameInstance.
	 */
	public GameController(){
		this.game = new GameInstance();
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
	
	void assignTileToPlayer(GameInstance gi, int playerIndex, int tileIndex){
		int i = 0;
		for(i = 0; i < gi.players[playerIndex].playerTiles.length; i++)	{
			if(gi.players[playerIndex].playerTiles[i] == null){
				break;
			}
		}
		
		gi.players[playerIndex].playerTiles[i] = gi.tileBank[tileIndex];
		gi.tileBank[tileIndex] = null;
	}
	
	void assignTileToPlayer(int playerIndex, int tileIndex){
		assignTileToPlayer(this.game, playerIndex, tileIndex);
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
			//System.out.println(gi);

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

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(this.game, file);
			//jaxbMarshaller.marshal(this, System.out);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}