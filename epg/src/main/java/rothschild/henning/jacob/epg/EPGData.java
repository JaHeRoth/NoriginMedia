package rothschild.henning.jacob.epg;

import java.util.List;

import rothschild.henning.jacob.epg.domain.EPGChannel;
import rothschild.henning.jacob.epg.domain.EPGEvent;

/**
 * Interface to implement and pass to EPG containing data to be used.
 * Implementation can be a simple as simple as a Map/List or maybe an Adapter.
 * Created by Kristoffer on 15-05-23.
 */
public interface EPGData {

    EPGChannel getChannel(int position);

    List<EPGEvent> getEvents(int channelPosition);

    EPGEvent getEvent(int channelPosition, int programPosition);

    int getChannelCount();

    boolean hasData();
}
