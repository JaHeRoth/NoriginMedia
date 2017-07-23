package rothschild.henning.jacob.noriginmedia.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import rothschild.henning.jacob.epg.EPG;
import rothschild.henning.jacob.epg.EPGClickListener;
import rothschild.henning.jacob.epg.EPGData;
import rothschild.henning.jacob.epg.domain.EPGChannel;
import rothschild.henning.jacob.epg.domain.EPGEvent;
import rothschild.henning.jacob.epg.misc.EPGDataImpl;
import rothschild.henning.jacob.epg.misc.MockDataService;
import rothschild.henning.jacob.noriginmedia.R;
import rothschild.henning.jacob.noriginmedia.SharedConstants;

public class MainActivity extends AppCompatActivity {
	
	// TODO: Update epg-view regularly, to represent that the time is constantly changing (right now the visual current-time-indicator is stuck until restart)
	
	private static final String EPG_FILE_LOCAL = "epg.txt";
	// 10.0.3.2 is localhost's IP address in Genymotion emulator (10.0.2.2 in Android emulator)
	private static final String EPG_FILE_REMOTE = "http://10.0.3.2:1337/epg"; // localhost
	private static final String EPG_BROADCAST_LOCAL = "EPG_BROADCAST_LOCAL";
	private static final String EPG_BROADCAST_REMOTE = "EPG_BROADCAST_REMOTE";
	private static final String TAG = MainActivity.class.getSimpleName() + ".";
	
	private EPG epg;
	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			handleReceivedBroadcast(intent);
		}
	};
	
	@Override
	protected void onDestroy() {
		if (epg != null) epg.clearEPGImageCache();
		LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
		super.onDestroy();
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
	    initEpg();
    }
	
	private void initEpg() {
		epg = (EPG) findViewById(R.id.epg);
		setEPGClickListener();
		fetchEPGData();
	}
	
	private void setEPGClickListener() {
		epg.setEPGClickListener(new EPGClickListener() {
			@Override
			public void onChannelClicked(int channelPosition, EPGChannel epgChannel) {
				Toast.makeText(MainActivity.this, epgChannel.getName() + " clicked", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onEventClicked(int channelPosition, int programPosition, EPGEvent epgEvent) {
				Toast.makeText(MainActivity.this, epgEvent.getTitle() + " clicked", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onResetButtonClicked() {
				epg.recalculateAndRedraw(true);
			}
		});
	}
	
	private void fetchEPGData() {
		fetchData(EPG_BROADCAST_LOCAL, LocationType.LOCAL, EPG_FILE_LOCAL);
		fetchData(EPG_BROADCAST_REMOTE, LocationType.REMOTE, EPG_FILE_REMOTE);
	}
	
	private void fetchData(String broadcastKey, LocationType locationType, String contentLocation) {
		LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(broadcastKey));
		new AsyncReader(getApplicationContext(), broadcastKey, locationType, contentLocation).execute();
	}
	
	private void handleReceivedBroadcast(Intent intent) {
		Log.d(TAG + "..ReceivedBroadcast", String.valueOf(intent.getIntExtra(SharedConstants.CODE_BUNDLE_KEY, SharedConstants.FAILED_CODE)));
		Log.d(TAG + "..ReceivedBroadcast", intent.getStringExtra(SharedConstants.READ_BUNDLE_KEY));
		// TODO: Check for identical, handle fail, write to storage, store in variable-cache
		setAndRedrawEPGData(new EPGDataCreator.fromJSONString(intent.getStringExtra(SharedConstants.READ_BUNDLE_KEY)));
	}
	
	private void setAndRedrawEPGData(EPGData data) {
		epg.setEPGData(data);
		epg.recalculateAndRedraw(false);
	}
}
