import junit.framework.TestSuite
import groovy.util.GroovyTestSuite;

public class IdentifierIntegrationTestSuite extends TestSuite {

	private static final String TEST_ROOT = "src/test/groovy/integration/intient/saml2/identifiers/";
	
	public static TestSuite suite() throws Exception {
		TestSuite suite = new TestSuite()
        GroovyTestSuite gsuite = new GroovyTestSuite()

        suite.addTestSuite(gsuite.compile(TEST_ROOT + "IdentifierTest.groovy"))
        
        return suite
	}
	
}