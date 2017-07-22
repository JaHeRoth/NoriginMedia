package rothschild.henning.jacob.noriginmedia.model;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Short helper-class to reduce coupling between Model and Controller.
 * Fetches data remotely (server) or locally (disk).
 * Created by Jacob H. Rothschild on 22.07.2017.
 */

public class Fetcher {
	
	// NOTE: For this example, filename will always be "epg"
	public static JSONObject local(Context context, String filename) throws JSONException, IOException {
		return fetch(BufferedReaderCreator.localReader(context, filename));
	}
	
	// NOTE: For this example, urlString will always be "http://localhost:1337/epg"
	public static JSONObject remote(String urlString) throws JSONException, IOException {
		return fetch(BufferedReaderCreator.remoteReader(urlString));
	}
	
	// NOTE: This method will grow once we implement the actual epg, so don't worry about it being too short. It'll grow because we'll have to convert from JSON to epg-format
	private static JSONObject fetch(BufferedReader reader) throws JSONException, IOException {
		return JSONFetcher.fromBufferedReader(reader);
	}
	
}
