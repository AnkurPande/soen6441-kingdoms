package tester;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import model.GameInstance;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
		
		gi = new GameInstance();
		gc.setGame(gi);
		gc.saveGame(file1);
		
		gi = new GameInstance();
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
