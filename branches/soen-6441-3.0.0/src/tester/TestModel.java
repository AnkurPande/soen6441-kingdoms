package tester;

import static org.junit.Assert.*;

import model.Config;
import model.GameInstance;
import model.GameInstance.PlayerColor;
import model.Player;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.GameController;

/**
 * Class to test the classes of the "model" package.
 * @author Team B
 *
 */
public class TestModel {

	GameInstance gi;
	Config conf;

	@Before
	public void setUp() throws Exception {
		gi = new GameInstance(new Config());
		conf = new Config();
	}

	@After
	public void tearDown() throws Exception {
		gi = null;
		conf = null;
	}

	@Test
	/**
	 * Tests the game config object when created with params.
	 */
	public void testConfigWithParam() {
		Config c1 = new Config(20);
		Config c2 = new Config(2);
		Config c3 = new Config(3);
		Config c4 = new Config(4);

		assertEquals(c1.NO_OF_PLAYERS,4);
		assertEquals(c2.NO_OF_PLAYERS,2);
		assertEquals(c3.NO_OF_PLAYERS,3);
		assertEquals(c4.NO_OF_PLAYERS,4);

		assertEquals(c1.NO_OF_RANK1CASTLES_PER_PLAYER,2);
		assertEquals(c2.NO_OF_RANK1CASTLES_PER_PLAYER,4);
		assertEquals(c3.NO_OF_RANK1CASTLES_PER_PLAYER,3);
		assertEquals(c4.NO_OF_RANK1CASTLES_PER_PLAYER,2);
	}

	@Test
	/**
	 * Tests the game config when created without params
	 */
	public void testConfigWithoutParam() {
		Config c = new Config();

		assertEquals(c.NO_OF_PLAYERS,4);
		assertEquals(c.NO_OF_RANK1CASTLES_PER_PLAYER,2);
	}

	@Test
	/**
	 * Tests the game instance object.
	 */
	public void testGameInstance(){
		GameInstance gi1 = new GameInstance(new Config());
		GameInstance gi2 = new GameInstance(new Config(2));
		GameInstance gi3 = new GameInstance(new Config(3));
		GameInstance gi4 = new GameInstance(new Config(4));

		assertEquals(gi1.players.length,4);
		assertEquals(gi2.players.length,2);
		assertEquals(gi3.players.length,3);
		assertEquals(gi4.players.length,4);

	}

	@Test
	/**
	 * Tests the current player index of the game instance.
	 */
	public void testGameInstanceCurrentPlayerIndex(){
		int currentPlayerIndex = gi.getCurrentPlayerIndex();
		assertTrue(currentPlayerIndex <= gi.players.length);
	}

	@Test
	/**
	 * Tests the epoch counter of the game instance.
	 */
	public void testGameInstanceEpochCounter(){

		int currentEpoch = gi.getCurrentEpoch().getEpochNo();
		assertTrue(currentEpoch <= 3 && currentEpoch >= 1);
	}

	@Test
	/**
	 * Tests the game board of the game instance.
	 */
	public void testGameInstanceGameBoard(){
		int rows = gi.gameBoard[0].length;
		int columns = gi.gameBoard.length;

		assertTrue(rows == conf.NO_OF_ROWS && columns == conf.NO_OF_COLS);
	}

	@Test
	/**
	 * Tests the castles of the player
	 */
	public void testPlayerCastles(){

		Player p = new Player(PlayerColor.GREEN, "Test", conf);

		assertEquals(p.rank1Castles.size(), conf.NO_OF_RANK1CASTLES_PER_PLAYER);
		assertEquals(p.rank2Castles.size(), conf.NO_OF_RANK2CASTLES_PER_PLAYER);
		assertEquals(p.rank3Castles.size(), conf.NO_OF_RANK3CASTLES_PER_PLAYER);
		assertEquals(p.rank4Castles.size(), conf.NO_OF_RANK4CASTLES_PER_PLAYER);

		assertEquals(p.playerTiles.size(), 0);
	}

	@Test
	/**
	 * Tests the coins of the player
	 */
	public void testPlayerCoins(){

		Player p = new Player(PlayerColor.GREEN, "Test", conf);

		assertEquals(p.playerCoins.size(), 0);

		Player pNew = gi.players[3];
		assertEquals(pNew.playerCoins.size(), 8);

		int coinValue = pNew.playerCoins.firstElement().getValue();
		assertEquals(coinValue, 50);
	}

	@Test
	/**
	 * Test getting the no of empty places on board.
	 */
	public void testGetEmptyPlacesOnBoard(){
		assertTrue(gi.getEmptyPlacesOnBoard() == 30);
	}

	@Test
	/**
	 * Tests getting a player index by color.
	 */
	public void testPlayerIndexByColor(){
		assertTrue(gi.players[0].getPlayerColor() == PlayerColor.RED);
		assertTrue(gi.players[1].getPlayerColor() == PlayerColor.YELLOW);
		assertTrue(gi.players[2].getPlayerColor() == PlayerColor.BLUE);
		assertTrue(gi.players[3].getPlayerColor() == PlayerColor.GREEN);
	}

	@Test
	/**
	 * 
	 */
	public void testIsGameEnded(){
		gi = new GameInstance(new Config(4));		
		assertFalse(gi.isGameEnded());
		
		GameController gc = new GameController(new Config(4));
		gc.setGame(gi);
		
		gc.playAllSixEpochs();
		assertTrue(gi.isGameEnded());
	}

}
