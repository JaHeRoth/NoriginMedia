package rothschild.henning.jacob.noriginmedia.model.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * Created by Jacob Rothschild on 08.10.2016.
 * Object used for fetching the html of a webpage.
 * Could have been made static, but Dependency Injection is preferred for cleaner project structure.
 */
class Reader {

	private static final Logger log = Logger.getLogger(Reader.class.getName());

	/**
	 * @return The full HTML, in one long String
	 */
	String readHTML(String urlString) {
		return arrayListToString(urlStringToHTML(urlString));
	}

	/**
	 * @return The result of calling '.toString()' on each elements within 'strings' and adding the results together
	 */
	private <T> String arrayListToString(ArrayList<T> list) {
		StringBuilder stringBuilder = new StringBuilder();
		for (T line : list) stringBuilder.append(line);
		return stringBuilder.toString();
	}

	private ArrayList<String> urlStringToHTML(String urlString) {
		try {
			return unsafe_urlStringToHTML(urlString);
		} catch (Exception e) {
			log.severe("ERROR encountered in 'urlStringToHTML()', or more specifically in 'unsafe_urlStringToHTML()'; urlString = " + urlString);
			log.severe(Arrays.toString(e.getStackTrace()));
		}
		return null;
	}

	private ArrayList<String> unsafe_urlStringToHTML(String urlString) throws Exception {
		return extractHTMLFromURL(stringToURL(urlString));
	}

	/**
	 * @return If possible an ArrayList<String> of all lines of the html behind 'url'
	 */
	private ArrayList<String> extractHTMLFromURL(URL url) throws IOException {
		// Settings "UTF-8" is necessary to read "æøå". This is NOT reproducible in normal java, but it is in GAE
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
			ArrayList<String> output = bufferedReaderToArrayList(reader);
			reader.close();
			return output;
		} catch (IOException e) {
			e.printStackTrace();
			log.info(String.format(new Locale("no"), "Reader.extractHTMLFromURL() threw exception: url.toString() = %s, url.getFile() = %s, url.getHost() = %s, url.getPath() = %s",
					url.toString(), url.getFile(), url.getHost(), url.getPath()));
			throw e;
		}
	}
	
	/**
	 * @return An ArrayList<String> made by all the contents of 'reader'
	 */
	private ArrayList<String> bufferedReaderToArrayList(BufferedReader reader) throws IOException {
		ArrayList<String> output = new ArrayList<>();
		String line;
		while ((line = reader.readLine()) != null) output.add(line);
		return output;
	}

	/**
	 * @return A URL-object created from 'urlString'
	 */
	private URL stringToURL(String urlString) throws MalformedURLException {
		return new URL(urlString);
	}
}
