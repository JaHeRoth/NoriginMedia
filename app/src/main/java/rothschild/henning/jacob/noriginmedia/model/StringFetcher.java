package rothschild.henning.jacob.noriginmedia.model;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by Jacob H. Rothschild on 22.07.2017.
 */

public class StringFetcher {
	
	/** @return All the content from 'reader', in one, large String */
	public static String fromBufferedReader(BufferedReader reader) throws JSONException, IOException {
		return bufferedReaderToContentString(reader);
	}
	
	/** @return All the content from 'reader', in one, large String */
	private static String bufferedReaderToContentString(BufferedReader reader) throws IOException {
		StringBuilder builder = bufferedReaderToContentStringBuilder(reader);
		reader.close();
		return builder.toString();
	}
	
	/** @return A StringBuilder made up of all the contents of 'reader' */
	private static StringBuilder bufferedReaderToContentStringBuilder(BufferedReader reader) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) stringBuilder.append(line);
		return stringBuilder;
	}
	
}
