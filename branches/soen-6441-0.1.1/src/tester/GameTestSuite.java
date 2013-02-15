package tester;

import static org.junit.Assert.*;
import junit.framework.TestSuite;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ TestController.class, TestComponents.class })
public class GameTestSuite extends TestSuite{

	@Test
	public void testAll() {
		fail("Not yet implemented");
	}

}
