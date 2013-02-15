package controller;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import model.GameInstance;

public class GameController {
	
	private GameInstance game;

	public GameInstance getGame() {
		return game;
	}

	public void setGame(GameInstance game) {
		this.game = game;
	}

	public GameController(GameInstance game){
		this.game = game;
	}
	
	public GameController(){
		this.game = new GameInstance();
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

			jaxbMarshaller.marshal(this.game, file);
			//jaxbMarshaller.marshal(this, System.out);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}