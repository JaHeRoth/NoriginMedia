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
import rothschild.henning.jacob.noriginmedia.SharedConstants;
import rothschild.henning.jacob.noriginmedia.R;

public class MainActivity extends AppCompatActivity {
	
	// TODO: Update epg-view regularly, to represent that the time is constantly changing (right now the visual current-time-indicator is stuck until restart)
	
	private static final String EPG_FILE_LOCAL = "epg.txt";
	private static final String EPG_FILE_REMOTE = "http://localhost:1337/epg";
	private static final String EPG_BROADCAST_LOCAL = "EPG_BROADCAST_LOCAL";
	private static final String EPG_BROADCAST_REMOTE = "EPG_BROADCAST_REMOTE";
	private static final String TAG = MainActivity.class.getSimpleName() + ".";
	
	private EPG epg;
	private BroadcastReceiver localReceiver;
	private BroadcastReceiver remoteReceiver;
	
	@Override
	protected void onDestroy() {
		if (epg != null) epg.clearEPGImageCache();
		LocalBroadcastManager.getInstance(this).unregisterReceiver(localReceiver);
		LocalBroadcastManager.getInstance(this).unregisterReceiver(remoteReceiver);
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
		registerLocalBroadcastReceiverForEpgLocal();
		registerLocalBroadcastReceiverForEpgRemote();
		new AsyncReader(getApplicationContext(), EPG_BROADCAST_LOCAL, true, EPG_FILE_LOCAL).execute();
		new AsyncReader(getApplicationContext(), EPG_BROADCAST_REMOTE, false, EPG_FILE_REMOTE).execute();
	}
	
	private void registerLocalBroadcastReceiverForEpgLocal() {
		localReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				Log.d(TAG + "..EpgLocal", intent.getStringExtra(SharedConstants.EPG_BUNDLE_NAME));
				// TODO: Use the received string, after having model convert it
				setEPGData(new EPGDataImpl(MockDataService.getMockData()));
			}
		};
		LocalBroadcastManager.getInstance(this).registerReceiver(localReceiver, new IntentFilter(EPG_BROADCAST_LOCAL));
	}
	
	private void registerLocalBroadcastReceiverForEpgRemote() {
		remoteReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				Log.d(TAG + "..EpgRemote", intent.getStringExtra(SharedConstants.EPG_BUNDLE_NAME));
				// TODO: Use the received string, after having model convert it
				setEPGData(new EPGDataImpl(MockDataService.getMockData()));
			}
		};
		LocalBroadcastManager.getInstance(this).registerReceiver(remoteReceiver, new IntentFilter(EPG_BROADCAST_REMOTE));
	}
	
	/** Calls 'epg.setEPGData()' and redraws its view */
	private void setEPGData(EPGData data) {
		epg.setEPGData(data);
		epg.recalculateAndRedraw(false);
	}
}
