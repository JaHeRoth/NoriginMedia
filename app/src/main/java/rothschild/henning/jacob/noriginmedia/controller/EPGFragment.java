package rothschild.henning.jacob.noriginmedia.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;

import rothschild.henning.jacob.epg.EPG;
import rothschild.henning.jacob.epg.EPGClickListener;
import rothschild.henning.jacob.epg.EPGData;
import rothschild.henning.jacob.epg.domain.EPGChannel;
import rothschild.henning.jacob.epg.domain.EPGEvent;
import rothschild.henning.jacob.noriginmedia.R;
import rothschild.henning.jacob.noriginmedia.SharedConstants;
import rothschild.henning.jacob.noriginmedia.model.CacheHandler;
import rothschild.henning.jacob.noriginmedia.model.EPGDataCreator;
import rothschild.henning.jacob.noriginmedia.model.StorageWriter;

public class EPGFragment extends Fragment {
	
	// TODO: Update epg-view regularly, to represent that the time is constantly changing (right now the visual current-time-indicator is stuck until restart)
	
	private static final String TAG = EPGFragment.class.getSimpleName();
	private static final String EPG_KEY = "epg";
	private static final String EPG_FILE_LOCAL = "epg.txt";
	// 10.0.3.2 is localhost's IP address in Genymotion emulator (10.0.2.2 in Android emulator)
	private static final String EPG_FILE_REMOTE = "http://10.0.3.2:1337/epg"; // localhost
	private static final String EPG_BROADCAST_LOCAL = "EPG_BROADCAST_LOCAL";
	private static final String EPG_BROADCAST_REMOTE = "EPG_BROADCAST_REMOTE";
	private final static String EPG_INPUT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX";
	
	private CacheHandler cache = new CacheHandler();
	private EPG epg;
	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			handleReceivedBroadcast(intent);
		}
	};
	
	@Override
	public void onDestroyView() {
		if (epg != null) epg.clearEPGImageCache();
		LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
		super.onDestroyView();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_epg, container, false);
		initEpg((EPG) rootView.findViewById(R.id.epg));
		return rootView;
	}
	
	private void initEpg(EPG epg) {
		this.epg = epg;
		setEPGClickListener();
		fetchEPGData();
	}
	
	private void setEPGClickListener() {
		epg.setEPGClickListener(new EPGClickListener() {
			@Override
			public void onChannelClicked(int channelPosition, EPGChannel epgChannel) {
				Toast.makeText(getActivity(), epgChannel.getName() + " clicked", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onEventClicked(int channelPosition, int programPosition, EPGEvent epgEvent) {
				Toast.makeText(getActivity(), epgEvent.getTitle() + " clicked", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onResetButtonClicked() {
				epg.recalculateAndRedraw(true);
			}
		});
	}
	
	private void fetchEPGData() {
		fetchData(EPG_BROADCAST_LOCAL, LocationType.LOCAL, EPG_FILE_LOCAL, EPG_KEY, null);
		fetchData(EPG_BROADCAST_REMOTE, LocationType.REMOTE, EPG_FILE_REMOTE, EPG_KEY, EPG_FILE_LOCAL);
	}
	
	private void fetchData(String broadcastKey, LocationType locationType, String contentLocation, String cacheKey, String destination) {
		LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver, new IntentFilter(broadcastKey));
		new AsyncReader(getActivity().getApplicationContext(), broadcastKey, locationType, contentLocation, cacheKey, destination).execute();
	}
	
	private void handleReceivedBroadcast(Intent intent) {
		try {
			int resultCode = intent.getIntExtra(SharedConstants.CODE_BUNDLE_KEY, SharedConstants.FAILED_CODE);
			String cacheKey = intent.getStringExtra(SharedConstants.CACHE_BUNDLE_KEY);
			String read = intent.getStringExtra(SharedConstants.READ_BUNDLE_KEY);
			String destinationFilename = intent.getStringExtra(SharedConstants.DESTINATION_BUNDLE_KEY);
			handleReceivedBroadcastValues(resultCode, cacheKey, read, destinationFilename);
		} catch (Exception e) {
			e.printStackTrace();
			// I'm not a big fan of these general exception catchers...
		}
	}
	
	private void handleReceivedBroadcastValues(int resultCode, String cacheKey, String read, String destinationFilename) throws Exception {
		if (resultCode == SharedConstants.SUCCESS_CODE && cache.ifNewWillCacheAndTell(cacheKey, read)) {
			StorageWriter.writeIfFilenameNotNull(getActivity().getApplicationContext(), destinationFilename, read);
			setAndRedrawEPGData(EPGDataCreator.fromJSONString(read, new SimpleDateFormat(EPG_INPUT_DATE_FORMAT, Locale.UK)));
		}
	}
	
	private void setAndRedrawEPGData(EPGData data) {
		epg.setEPGData(data);
		epg.recalculateAndRedraw(false);
	}
}
