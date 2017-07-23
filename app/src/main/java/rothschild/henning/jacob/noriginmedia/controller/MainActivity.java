package rothschild.henning.jacob.noriginmedia.controller;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import rothschild.henning.jacob.epg.EPG;
import rothschild.henning.jacob.epg.EPGClickListener;
import rothschild.henning.jacob.epg.EPGData;
import rothschild.henning.jacob.epg.domain.EPGChannel;
import rothschild.henning.jacob.epg.domain.EPGEvent;
import rothschild.henning.jacob.epg.misc.EPGDataImpl;
import rothschild.henning.jacob.epg.misc.MockDataService;
import rothschild.henning.jacob.noriginmedia.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
	    initEpg();
    }
	
	private EPG epg;
	
	private void initEpg() {
		epg = (EPG) findViewById(R.id.epg);
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
		
		// Do initial load of data.
		new AsyncLoadEPGData(epg).execute();
	}
	
	@Override
	protected void onDestroy() {
		if (epg != null) epg.clearEPGImageCache();
		super.onDestroy();
	}
	
	// FIXME: Only happens in onCreate. Should somehow update the view quite regularly, to represent that the time is constantly changing (right now current-time is stuck until restart)
	// FIXME: Pretty sure this is leaking both view and Activity. Wait until onPostExecute to get the view, and avoid the internal class extends AsyncTask pattern
	private static class AsyncLoadEPGData extends AsyncTask<Void, Void, EPGData> {
		
		EPG epg;
		
		public AsyncLoadEPGData(EPG epg) {
			this.epg = epg;
		}
		
		@Override
		protected EPGData doInBackground(Void... voids) {
			return new EPGDataImpl(MockDataService.getMockData());
		}
		
		@Override
		protected void onPostExecute(EPGData epgData) {
			epg.setEPGData(epgData);
			epg.recalculateAndRedraw(false);
		}
	}
}
