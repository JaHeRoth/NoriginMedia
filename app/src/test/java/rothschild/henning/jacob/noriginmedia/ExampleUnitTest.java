package rothschild.henning.jacob.noriginmedia;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
	private final String urlString = "http://localhost:1337/epg";
	
	/** Asserts that Reader.java doesn't crash */
	@Test
    public void readerClassRuns() throws Exception {
        new Reader().fetchFromURLString(urlString);
    }
	
	/** Asserts that no exceptions are thrown and caught inside Reader.java */
	@Test
	public void readerClassReadsEffortlessly() throws Exception {
		assertNotEquals(new Reader().fetchFromURLString(urlString), null);
	}
	
	/** Asserts that Reader.java manages to read something (returns more than an empty string)  */
	@Test
	public void readerClassReadsSomething() throws Exception {
		assertNotEquals(new Reader().fetchFromURLString(urlString), "");
	}
	
	/** Asserts that Reader.java returns correct content from EPG */
	@Test
	public void readerClassReadsEPGCorrectly() throws Exception {
		assertEquals(new Reader().fetchFromURLString(urlString), localRead("epg.txt"));
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