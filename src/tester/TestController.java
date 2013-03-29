package tester;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import model.Config;
import model.GameInstance;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import components.Castle.CastleRank;
import components.Tile;

import controller.GameController;

/**
 * Class to run tests on the classes inside the "controller" package
 * @author Team B
 *
 */
public class TestController {
	
	GameController gc;
	GameInstance gi ;
	
	@Before
	public void setUp(){
		gc = new GameController();
	}
	
	@After
	public void tearDown(){
		gc = null;
		gi = null;
	}

	@Test
	/**
	 * Tests the game loading from file functionality.
	 */
	public void testLoadGame() {
		
		String file1 = "test_file1.xml";
		String file2 = "test_file2.xml";
		String file3 = "test_file3.xml";
		
		gi = new GameInstance(new Config());
		gc.setGame(gi);
		gc.saveGame(file1);
		
		gi = new GameInstance(new Config());
		gc.setGame(gi);
		gc.saveGame(file2);
		
		gi = gc.loadGame(file1);
		gc.setGame(gi);
		gc.saveGame(file3);
		
		assertFalse(compareFileContents(file2,file3));
	}

	@Test
	/**
	 * Tests the game saving to file functionality.
	 */
	public void testSaveGame() {
		String file1 = "test_file1.xml";
		String file2 = "test_file2.xml";
		
		gc.saveGame(file1);
		gc.saveGame(file2);
		
		assertTrue(compareFileContents(file1, file2));
	}
	
	@Test
	/**
	 * Tests the draw and place tile functionality.
	 */
	public void testDrawAndPlaceTile(){
		assertTrue(gc.drawAndPlaceTile(0, 0));
		assertFalse(gc.drawAndPlaceTile(-8, 100));
	}
	
	@Test
	/**
	 * Tests the draw and place first tile functionality.
	 */
	public void testPlaceFirstTile(){
		assertTrue(gc.placeFirstTile(0, 1, 2));
		assertFalse(gc.placeFirstTile(-10, 55, 2));
	}
	
	@Test
	/**
	 * Tests the draw and place castle functionality.
	 */
	public void testPlaceCastle(){
		assertTrue(gc.placeCastle(0, CastleRank.ONE, 3, 2));
		assertFalse(gc.placeCastle(0, CastleRank.FOUR, 2, 20));
	}
	
	@Test
	/**
	 * Tests that there are validations on the test place items on game board.
	 */
	public void testPlaceItemOnGameBoard(){
		assertTrue(gc.isGameBoardPlaceValidAndVacant(0, 0));
		assertFalse(gc.isGameBoardPlaceValidAndVacant(110, -90));
	}
	
	@Test
	/**
	 * Tests that items can't be put on non vacant spaces of the game board.
	 */
	public void testItemPlacingOnNonEmptySpace(){
		gc.getGame().gameBoard[1][1] = new Tile();
		assertTrue(gc.isGameBoardPlaceValidAndVacant(0, 0));
		assertFalse(gc.isGameBoardPlaceValidAndVacant(1, 1));
	}
	
	@Test
	/**
	 * Tests placing the players first tile on board.
	 */
	public void testHasPlayerFirstTile(){
		gc.placeFirstTile(0, 4, 4);
		assertTrue(gc.hasPlayerFirstTile(1));
		assertFalse(gc.hasPlayerFirstTile(0));
	}
	
	@Test
	/**
	 * Tests the scoring functionalities from a pre-calculated XML file.
	 */
	public void testScoring(){
		gc.loadAndSetGame("junit_scoring_test.xml");
		gc.calculateScore();
		
		assertTrue(gc.getGame().players[0].getEpochScore(0) == 71);
		assertTrue(gc.getGame().players[0].getEpochScore(1) == 62);
		assertTrue(gc.getGame().players[0].getEpochScore(2) == 23);
		
		assertTrue(gc.getGame().players[1].getEpochScore(0) == 0);
		assertTrue(gc.getGame().players[1].getEpochScore(1) == 5);
		assertTrue(gc.getGame().players[1].getEpochScore(2) == 19);
		
		assertTrue(gc.getGame().players[2].getEpochScore(0) == 0);
		assertTrue(gc.getGame().players[2].getEpochScore(1) == 0);
		assertTrue(gc.getGame().players[2].getEpochScore(2) == 2);
		
		assertTrue(gc.getGame().players[3].getEpochScore(0) == 76);
		assertTrue(gc.getGame().players[3].getEpochScore(1) == 2);
		assertTrue(gc.getGame().players[3].getEpochScore(2) == -5);
		
	}
	
	/**
	 * Method to compare if the contents of two files are identical.
	 * 
	 * @param file1 The first file to compare.
	 * @param file2 The second file to compare.
	 * @return Returns true if the contents of the two files are identical - returns false otherwise.
	 */
	public boolean compareFileContents(String file1, String file2){
		
		BufferedReader brFile1 = null;
		BufferedReader brFile2 = null;
		
		try {
			brFile1 = new BufferedReader(new FileReader(file1));
			brFile2 = new BufferedReader(new FileReader(file2));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		while (true) {
		    String file1line = null ;
		    String file2line = null;
			try {
				file1line = brFile1.readLine();
				file2line = brFile2.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		    if (file1line == null || file2line == null)
		        break;
		    
		    if(!file1line.equalsIgnoreCase(file2line)){
		    	try {
					brFile1.close();
					brFile2.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	
		    	return false;
		    }   
		}
		
		try {
			brFile1.close();
			brFile2.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return true;
	}
}
