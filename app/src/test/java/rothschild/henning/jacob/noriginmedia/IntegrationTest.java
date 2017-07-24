package rothschild.henning.jacob.noriginmedia;

import org.junit.Test;

import rothschild.henning.jacob.noriginmedia.model.Fetcher;

import static junit.framework.Assert.assertEquals;

/**
 * Integration tests.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class IntegrationTest {
	
	private TestResourceReader testEPGReader = new TestResourceReader(TestResourceReader.EPG_LOCAL);
	
	/** Asserts that Fetcher.Remote(...) returns correct content from EPG */
	@Test
	public void fetchRemote() throws Exception {
		// NOTE: .toString() must be called. It seems like otherwise == is called
		assertEquals(testEPGReader.jsonRead().toString(), Fetcher.remote(TestResourceReader.EPG_REMOTE).toString());
	}
}