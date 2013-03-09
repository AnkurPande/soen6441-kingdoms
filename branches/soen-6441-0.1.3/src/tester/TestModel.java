package tester;

import static org.junit.Assert.*;

import model.Config;
import model.GameInstance;
import model.GameInstance.PlayerColor;
import model.Player;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
	public void testConfigWithoutParam() {
		Config c = new Config();
		
		assertEquals(c.NO_OF_PLAYERS,4);
		assertEquals(c.NO_OF_RANK1CASTLES_PER_PLAYER,2);
	}
	
	@Test
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
	public void testGameInstanceCurrentPlayerIndex(){
		int currentPlayerIndex = gi.getCurrentPlayerIndex();
		assertTrue(currentPlayerIndex <= gi.players.length);
	}
	
	@Test
	public void testGameInstanceEpochCounter(){
		
		int currentEpoch = gi.getCurrentEpoch().getCurrentEpochNo();
		assertTrue(currentEpoch <= 3 && currentEpoch >= 1);
	}
	
	@Test
	public void testGameInstanceGameBoard(){
		int rows = gi.gameBoard[0].length;
		int columns = gi.gameBoard.length;
		
		assertTrue(rows == conf.NO_OF_ROWS && columns == conf.NO_OF_COLS);
	}
		
	@Test
	public void testPlayerCastles(){
		
		Player p = new Player(PlayerColor.GREEN, "Test", conf);
		
		assertEquals(p.rank1Castles.length, conf.NO_OF_RANK1CASTLES_PER_PLAYER);
		assertEquals(p.rank2Castles.length, conf.NO_OF_RANK2CASTLES_PER_PLAYER);
		assertEquals(p.rank3Castles.length, conf.NO_OF_RANK3CASTLES_PER_PLAYER);
		assertEquals(p.rank4Castles.length, conf.NO_OF_RANK4CASTLES_PER_PLAYER);
		
		assertEquals(p.playerTiles.size(), 0);
	}
	
	@Test
	public void testPlayerCoins(){
		
		Player p = new Player(PlayerColor.GREEN, "Test", conf);
		
		assertEquals(p.playerCoins.size(), 0);
		
		Player pNew = gi.players[3];
		assertEquals(pNew.playerCoins.size(), 1);
		
		int coinValue = pNew.playerCoins.firstElement().getValue();
		assertEquals(coinValue, 50);
	}

}