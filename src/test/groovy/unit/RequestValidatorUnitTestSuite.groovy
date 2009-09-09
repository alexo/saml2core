import junit.framework.TestSuite
import groovy.util.GroovyTestSuite;

public class RequestValidatorUnitTestSuite extends TestSuite {

	private static final String TEST_ROOT = "src/test/groovy/unit/intient/saml2/validator/";
	
	public static TestSuite suite() throws Exception {
		TestSuite suite = new TestSuite()
        GroovyTestSuite gsuite = new GroovyTestSuite()

        suite.addTestSuite(gsuite.compile(TEST_ROOT + "bean/RequestRecipientTest.groovy"))
        suite.addTestSuite(gsuite.compile(TEST_ROOT + "bean/RequestValidityTest.groovy"))
        suite.addTestSuite(gsuite.compile(TEST_ROOT + "impl/RequestValidatorTest.groovy"))
        
        return suite
	}
	
}