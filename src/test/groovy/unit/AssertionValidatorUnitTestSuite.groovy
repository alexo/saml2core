import junit.framework.TestSuite
import groovy.util.GroovyTestSuite;

public class AssertionValidatorUnitTestSuite extends TestSuite {

	private static final String TEST_ROOT = "src/test/groovy/unit/intient/saml2/validator/";
	
	public static TestSuite suite() throws Exception {
		TestSuite suite = new TestSuite()
        GroovyTestSuite gsuite = new GroovyTestSuite()

        suite.addTestSuite(gsuite.compile(TEST_ROOT + "bean/AssertionRecipientTest.groovy"))
        suite.addTestSuite(gsuite.compile(TEST_ROOT + "bean/AssertionValidityTest.groovy"))
        suite.addTestSuite(gsuite.compile(TEST_ROOT + "impl/AssertionValidatorTest.groovy"))
        
        return suite
	}
	
}