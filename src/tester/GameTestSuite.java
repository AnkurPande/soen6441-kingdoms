package tester;

import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ TestController.class, TestModel.class})
/**
 * Test suite to run all tests at once.
 *
 */
public class GameTestSuite extends TestSuite{


}
