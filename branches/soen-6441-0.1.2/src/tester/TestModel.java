package tester;

import static org.junit.Assert.*;

import model.Config;
import model.GameInstance;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestModel {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConfigInt() {
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
	public void testConfig() {
		Config c = new Config();
		
		assertEquals(c.NO_OF_PLAYERS,4);
		assertEquals(c.NO_OF_RANK1CASTLES_PER_PLAYER,2);
	}
	
	@Test
	public void testGameInstance(){
		GameInstance gi = new GameInstance();
		
		assertEquals(gi.players.length,4);
	}

}
