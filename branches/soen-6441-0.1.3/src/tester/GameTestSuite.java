package tester;

import static org.junit.Assert.*;
import junit.framework.TestSuite;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ TestController.class})
/**
 * Test suite to run all tests at once.
 *
 */
public class GameTestSuite extends TestSuite{

	@Test
	public void testAll() {
		fail("Not yet implemented");
	}

}
