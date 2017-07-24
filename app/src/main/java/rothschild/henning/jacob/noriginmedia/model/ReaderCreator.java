package rothschild.henning.jacob.noriginmedia.model;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Static functions for creating and returning BufferedReaders for local files and remote urls
 * Created by Jacob H. Rothschild on 22.07.2017.
 */

public class ReaderCreator {
	
	/** @return A BufferedReader for fetching the content of the url 'urlString' */
	public static BufferedReader remoteReader(String urlString) throws IOException {
		return inputStreamToBufferedReader(new URL(urlString).openStream());
	}
	
	public static BufferedReader localReader(Context context, String filename) throws IOException {
		return inputStreamToBufferedReader(context.openFileInput(filename));
	}
	
	/** @return A BufferedReader for reading the InputStream 'stream' */
	private static BufferedReader inputStreamToBufferedReader(InputStream stream) throws IOException {
		// Settings "UTF-8" is necessary to read "æøå". This is NOT reproducible in normal java, but it is in GAE
		return new BufferedReader(new InputStreamReader(stream, "UTF-8"));
	}
	
}
