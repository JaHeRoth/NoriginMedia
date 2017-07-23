package rothschild.henning.jacob.noriginmedia.model;

import android.annotation.SuppressLint;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rothschild.henning.jacob.epg.EPGData;
import rothschild.henning.jacob.epg.domain.EPGChannel;
import rothschild.henning.jacob.epg.domain.EPGEvent;
import rothschild.henning.jacob.epg.misc.EPGDataImpl;

/**
 * Created by Jacob H. Rothschild on 23.07.2017.
 */

public class EPGDataCreator {
	
	// FIXME: Channel 5 is missing
	// FIXME: No EPGEvents are showing
	
	public static EPGData fromJSONString(String jsonString, SimpleDateFormat dateFormat) throws JSONException, ParseException {
		return new EPGDataImpl(jsonToEPGHashMap(new JSONObject(jsonString).getJSONArray("channels"), dateFormat));
	}
	
	private static LinkedHashMap<EPGChannel, List<EPGEvent>> jsonToEPGHashMap(JSONArray channels, SimpleDateFormat dateFormat) throws JSONException, ParseException {
		LinkedHashMap<EPGChannel, List<EPGEvent>> epgMap = new LinkedHashMap<>();
		for (int index = 0; index < channels.length(); index++) {
			JSONObject channel = channels.getJSONObject(index);
			epgMap.put(extractEPGChannel(channel), extractEPGEvents(channel, dateFormat));
		}
		Log.i("EPGDataCreator", "TimeAdjustment: " + hackyTimeRecenter());
		return epgMap;
	}
	
	private static EPGChannel extractEPGChannel(JSONObject channel) throws JSONException {
		return new EPGChannel(channel.getJSONObject("images").getString("logo"), channel.getString("title"), channel.getString("id"));
	}
	
	private static ArrayList<EPGEvent> extractEPGEvents(JSONObject channel, SimpleDateFormat dateFormat) throws JSONException, ParseException {
		JSONArray events = channel.getJSONArray("schedules");
		ArrayList<EPGEvent> epgEvents = new ArrayList<>();
		for (int index = 0; index < events.length(); index++) {
			JSONObject event = events.getJSONObject(index);
			epgEvents.add(new EPGEvent(stringDateToLongDate(event.getString("start"), dateFormat), stringDateToLongDate(event.getString("end"), dateFormat), event.getString("title")));
		}
		return epgEvents;
	}
	
	private static long stringDateToLongDate(String string, SimpleDateFormat dateFormat) throws ParseException {
		return dateFormat.parse(string).getTime() + hackyTimeRecenter();
	}
	
	/** WARNING: ONLY for demonstration purposes. Remove in any deployment version! <p>
	 * There is no reason in showing an empty screen with no content. Thus I decided to, for the sake of demonstration, always make the programs center around the current day. This would of course never be part of a deployment version, but is necessary for the purpose of demonstration, at least with the input-data I have been provided. */
	@SuppressLint("LongLogTag")
	private static long hackyTimeRecenter() {
		// 1489791600000L is 2017-03-18T00:00:00+01:00
		return TimeUnit.DAYS.toMillis(TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - 1489791600000L));
	}
	
}
