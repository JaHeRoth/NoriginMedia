package rothschild.henning.jacob.noriginmedia.model.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Jacob Rothschild on 08.10.2016.
 * Object used for fetching content from a webpage or server, by taking a url-string, and returning a content-string.
 * Could have been made static, but Dependency Injection is preferred for cleaner project structure.
 */
public class Reader {
	
	private static final String TAG = Reader.class.getSimpleName() + ".";

	/** @return All the content behind 'urlString', in one, large String */
	public String fetchFromURLString(String urlString) {
		try {
			return urlStringToContentString(urlString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/** @throws Exception All methods called by this method re-throw their exceptions upward. Thus, this method will throw a MalformedUTLException or IOException, if anything, anywhere goes wrong between url-string (input) and content-string (output) */
	private String urlStringToContentString(String urlString) throws Exception {
		return urlToContentString(stringToURL(urlString));
	}
	
	/** @return A URL-object created from 'urlString' */
	private URL stringToURL(String urlString) throws MalformedURLException {
		return new URL(urlString);
	}

	/** @return If possible, all content behind 'url', in one, large String */
	private String urlToContentString(URL url) throws IOException {
		BufferedReader reader = urlToBufferedReader(url);
		String output = bufferedReaderToContentString(reader);
		reader.close();
		return output;
	}
	
	/** @return A BufferedReader for fetching the content of 'url' */
	private BufferedReader urlToBufferedReader(URL url) throws IOException {
		// Settings "UTF-8" is necessary to read "æøå". This is NOT reproducible in normal java, but it is in GAE
		return new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
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
