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
	
	// As this functions as a top-class of model, I feel it is ok to call static functions from here, instead of using dependency injection from our controller.
	
	// FIXME: Remote data is not stored (cached) locally
	// FIXME: Missing comparison functionality to assure remote content is new, before giving it to dumb UI
	
	// NOTE: For this example, filename will always be "epg"
	@Deprecated
	public static JSONObject local(Context context, String filename) throws JSONException, IOException {
		return fetch(ReaderCreator.localReader(context, filename));
	}
	
	// NOTE: For this example, urlString will always be "http://localhost:1337/epg"
	@Deprecated
	public static JSONObject remote(String urlString) throws JSONException, IOException {
		return fetch(ReaderCreator.remoteReader(urlString));
	}
	
	// NOTE: This method will grow once we implement the actual epg, so don't worry about it being too short. It'll grow because we'll have to convert from JSON to epg-format
	@Deprecated
	private static JSONObject fetch(BufferedReader reader) throws JSONException, IOException {
		return new JSONObject(StringFetcher.fromBufferedReader(reader));
	}
	
}
