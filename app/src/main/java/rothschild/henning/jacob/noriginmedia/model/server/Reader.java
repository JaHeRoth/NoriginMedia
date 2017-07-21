package rothschild.henning.jacob.noriginmedia.model.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Jacob Rothschild on 08.10.2016.
 * Object used for fetching the html of a webpage.
 * Could have been made static, but Dependency Injection is preferred for cleaner project structure.
 */
class Reader {
	
	private static final String TAG = Reader.class.getSimpleName() + ".";

	/** @return The full HTML, in one, large String */
	String readHTML(String urlString) {
		try {
			return urlStringToHTML(urlString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/** @throws Exception All methods called by this method re-throw their exceptions upward. Thus, this method will throw a MalformedUTLException or IOException, if anything, anywhere goes wrong between url-string (input) and html-string (output) */
	private String urlStringToHTML(String urlString) throws Exception {
		return urlToHTML(stringToURL(urlString));
	}
	
	/** @return A URL-object created from 'urlString' */
	private URL stringToURL(String urlString) throws MalformedURLException {
		return new URL(urlString);
	}

	/** @return If possible, the whole HTML behind 'url', in one, large String */
	private String urlToHTML(URL url) throws IOException {
		BufferedReader reader = urlToBufferedReader(url);
		String output = bufferedReaderToHTML(reader);
		reader.close();
		return output;
	}
	
	/** @return A BufferedReader for fetching the HTML of 'url' */
	private BufferedReader urlToBufferedReader(URL url) throws IOException {
		// Settings "UTF-8" is necessary to read "æøå". This is NOT reproducible in normal java, but it is in GAE
		return new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
	}
	
	/** @return A long String made by all the contents of 'reader' */
	private String bufferedReaderToHTML(BufferedReader reader) throws IOException {
		return bufferedReaderToStringBuilder(reader).toString();
	}
	
	/** @return A StringBuilder made up of all the contents of 'reader' */
	private StringBuilder bufferedReaderToStringBuilder(BufferedReader reader) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) stringBuilder.append(line);
		return stringBuilder;
	}
}
