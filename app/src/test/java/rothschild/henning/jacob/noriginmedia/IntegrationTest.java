package rothschild.henning.jacob.noriginmedia;

import org.json.JSONObject;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import rothschild.henning.jacob.noriginmedia.model.Fetcher;

import static junit.framework.Assert.assertEquals;

/**
 * Integration tests.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class IntegrationTest {
	private final static String FILENAME = "epg.txt";
	private final static String URL_STRING = "http://localhost:1337/epg";
	
	/** Asserts that Fetcher.Remote(...) returns correct content from EPG */
	@Test
	public void fetchRemote() throws Exception {
		// NOTE: .toString() must be called. It seems like otherwise == is called
		assertEquals(Fetcher.remote(URL_STRING).toString(), new JSONObject(localRead(FILENAME)).toString());
	}
	
	private String localRead(String filename) throws IOException {
		BufferedReader reader = filenameToBufferedReader(filename);
		String output = bufferedReaderToContentString(reader);
		reader.close();
		return output;
	}
	
	/** @return A BufferedReader for reading the content of the file 'filename' */
	private BufferedReader filenameToBufferedReader(String filename) throws IOException {
		return new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(filename)));
	}
	
	/** @return A long String made by all the contents of 'reader' */
	private String bufferedReaderToContentString(BufferedReader reader) throws IOException {
		return bufferedReaderToContentStringBuilder(reader).toString();
	}
	
	/** @return A StringBuilder made up of all the contents of 'reader' */
	private StringBuilder bufferedReaderToContentStringBuilder(BufferedReader reader) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) stringBuilder.append(line);
		return stringBuilder;
	}
}