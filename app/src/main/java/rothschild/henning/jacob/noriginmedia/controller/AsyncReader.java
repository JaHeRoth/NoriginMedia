package rothschild.henning.jacob.noriginmedia.controller;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;

import java.io.BufferedReader;
import java.io.IOException;

import rothschild.henning.jacob.noriginmedia.SharedConstants;
import rothschild.henning.jacob.noriginmedia.model.ReaderCreator;
import rothschild.henning.jacob.noriginmedia.model.StringFetcher;

/**
 * Asynchronously reads the content of either a file (local) or page (remote)
 * Created by Jacob H. Rothschild on 23.07.2017.
 */

class AsyncReader extends AsyncTask<Void, Void, String> {
	
	private final Context appContext;
	private final String broadcastName;
	private final LocationType locationType;
	private final String contentLocation;
	private final String cacheKey;
	private final String destination;
	
	AsyncReader(Context appContext, String broadcastName, LocationType locationType, String contentLocation, String cacheKey, String destination) {
		this.appContext = appContext;
		this.broadcastName = broadcastName;
		this.locationType = locationType;
		this.contentLocation = contentLocation;
		this.cacheKey = cacheKey;
		this.destination = destination;
	}
	
	@Override
	protected String doInBackground(Void... voids) {
		// FIXME: Shouldn't be using the general 'catch (Exception e)'. 'nulls' and other exception-sources should be spotted before becoming exceptions at all
		try {
			// Closest I could get to dependency injection while running everything asynchronously
			return StringFetcher.fromBufferedReader(createBufferedReader());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/** @return A BufferedReader, based on the parameters 'locationType' and 'contentLocation' received in the AsyncReader-constructor */
	private BufferedReader createBufferedReader() throws IOException {
		return locationType == LocationType.LOCAL ?
				ReaderCreator.localReader(appContext, contentLocation) :
				ReaderCreator.remoteReader(contentLocation);
	}
	
	@Override
	protected void onPostExecute(String epg) {
		Intent intent = new Intent(broadcastName);
		intent.putExtra(SharedConstants.CODE_BUNDLE_KEY, epg.isEmpty() ? SharedConstants.FAILED_CODE : SharedConstants.SUCCESS_CODE);
		intent.putExtra(SharedConstants.READ_BUNDLE_KEY, epg);
		intent.putExtra(SharedConstants.CACHE_BUNDLE_KEY, cacheKey);
		intent.putExtra(SharedConstants.DESTINATION_BUNDLE_KEY, destination);
		LocalBroadcastManager.getInstance(appContext).sendBroadcast(intent);
	}
}