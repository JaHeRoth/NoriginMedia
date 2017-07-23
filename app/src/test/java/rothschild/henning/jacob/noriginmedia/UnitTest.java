package rothschild.henning.jacob.noriginmedia;

import org.json.JSONObject;
import org.junit.Test;

import rothschild.henning.jacob.noriginmedia.model.BufferedReaderCreator;
import rothschild.henning.jacob.noriginmedia.model.JSONFetcher;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Our unit-tests
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTest {
	
	private TestResourceReader epgReader = new TestResourceReader(TestResourceReader.EPG_LOCAL);
	
	/** Asserts that JSONFetcher.fromBufferedReader(...) returns correct content */
	@Test
	public void jsonFetcher_something() throws Exception {
		assertNotEquals(JSONFetcher.fromBufferedReader(epgReader.filenameToBufferedReader()), null);
		assertNotEquals(JSONFetcher.fromBufferedReader(epgReader.filenameToBufferedReader()), "");
	}
	
	/** Asserts that JSONFetcher.fromBufferedReader(...) returns correct content */
	@Test
	public void jsonFetcher_correct() throws Exception {
		// NOTE: .toString() must be called. It seems like otherwise == is called
		JSONObject origin = JSONFetcher.fromBufferedReader(epgReader.filenameToBufferedReader());
		JSONObject comparison = epgReader.jsonRead();
		assertEquals("\n" + origin.toString() + "\n" + comparison.toString() + "\n", origin.toString(), comparison.toString());
	}
	
	/** Asserts that BufferedReaderCreator.remoteReader(...) returns a working BufferedReader.
	 * When server is off, this should fail. */
	@Test
	public void remoteReader() throws Exception {
		assertEquals(epgReader.bufferedReaderToContentString(BufferedReaderCreator.remoteReader(TestResourceReader.EPG_REMOTE)), epgReader.stringRead());
	}
	
}