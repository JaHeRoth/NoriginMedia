package rothschild.henning.jacob.noriginmedia;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A class used for reading files from test/resources, when testing. NOT used by any parts of the actual program (only by the tests).
 * This file should NOT be in the main code folder, but I found no other way to make it available for both test-types (test and androidTest)
 * Created by Jacob H. Rothschild on 23.07.2017.
 */

public class TestResourceReader {
	
	public final static String EPG_LOCAL = "epg.txt";
	public final static String EPG_REMOTE = "http://localhost:1337/epg";
	
	private String filename;
	
	TestResourceReader(String filename) {
		this.filename = filename;
	}
	
	/** @return All the content from the constructor-time initialized 'filename', in one, large JSONObject */
	public JSONObject jsonRead() throws JSONException, IOException {
		return new JSONObject(stringRead());
	}
	
	/** @return All the content from the constructor-time initialized 'filename', in one, large String */
	public String stringRead() throws IOException {
		BufferedReader reader = filenameToBufferedReader();
		String output = bufferedReaderToContentString(reader);
		reader.close();
		return output;
	}
	
	/** @return A BufferedReader for reading the content of the file 'filename' */
	public BufferedReader filenameToBufferedReader() throws IOException {
		return new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(filename)));
	}
	
	/** @return A long String made by all the contents of 'reader' */
	public String bufferedReaderToContentString(BufferedReader reader) throws IOException {
		return bufferedReaderToContentStringBuilder(reader).toString();
	}
	
	/** @return A StringBuilder made up of all the contents of 'reader' */
	public StringBuilder bufferedReaderToContentStringBuilder(BufferedReader reader) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) stringBuilder.append(line);
		return stringBuilder;
	}
	
}
