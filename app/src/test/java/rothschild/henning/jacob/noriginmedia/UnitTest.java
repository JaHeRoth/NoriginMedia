package rothschild.henning.jacob.noriginmedia;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Locale;

import rothschild.henning.jacob.noriginmedia.model.ReaderCreator;
import rothschild.henning.jacob.noriginmedia.model.StringFetcher;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Our unit-tests
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTest {
	
	private TestResourceReader testEPGReader = new TestResourceReader(TestResourceReader.EPG_LOCAL);
	
	/** Asserts that StringFetcher.fromBufferedReader(...) returns correct content */
	@Test
	public void stringFetcher_something() throws Exception {
		assertNotEquals(null, StringFetcher.fromBufferedReader(testEPGReader.filenameToBufferedReader()));
		assertNotEquals("", StringFetcher.fromBufferedReader(testEPGReader.filenameToBufferedReader()));
	}
	
	/** Asserts that StringFetcher.fromBufferedReader(...) returns correct content */
	@Test
	public void stringFetcher_correct() throws Exception {
		// NOTE: .toString() must be called. It seems like otherwise == is called
		String origin = StringFetcher.fromBufferedReader(testEPGReader.filenameToBufferedReader());
		String comparison = testEPGReader.stringRead();
		assertEquals("\n" + origin + "\n" + comparison + "\n", comparison, origin);
	}
	
	/** Asserts that ReaderCreator.remoteReader(...) returns a working BufferedReader.
	 * When server is off, this should fail. */
	@Test
	public void remoteReaderCreator() throws Exception {
		assertEquals(testEPGReader.stringRead(), testEPGReader.bufferedReaderToContentString(ReaderCreator.remoteReader(TestResourceReader.EPG_REMOTE)));
	}
	
	/** Asserts that dates are understood correctly */
	@Test
	public void dateFormatter() throws Exception {
		assertEquals(1489796400000L, new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", new Locale("no")).parse("2017-03-18T01:20:00+01:00").getTime());
	}
	
}