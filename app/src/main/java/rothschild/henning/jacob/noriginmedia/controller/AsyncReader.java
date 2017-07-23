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
	private final boolean isLocal;
	private final String contentPath;
	
	AsyncReader(Context appContext, String broadcastName, boolean isLocal, String contentPath) {
		this.appContext = appContext;
		this.broadcastName = broadcastName;
		this.isLocal = isLocal;
		this.contentPath = contentPath;
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
	
	/** @return A BufferedReader based on the parameters 'isLocal' and 'contentPath' from the AsyncReader-constructor */
	private BufferedReader createBufferedReader() throws IOException {
		return isLocal ?
				ReaderCreator.localReader(appContext, contentPath) :
				ReaderCreator.remoteReader(contentPath);
	}
	
	@Override
	protected void onPostExecute(String epg) {
		Intent intent = new Intent(broadcastName);
		intent.putExtra(SharedConstants.CODE_BUNDLE_KEY, epg.isEmpty() ? SharedConstants.FAILED_CODE : SharedConstants.SUCCESS_CODE);
		intent.putExtra(SharedConstants.READ_BUNDLE_KEY, epg);
		LocalBroadcastManager.getInstance(appContext).sendBroadcast(intent);
	}
}