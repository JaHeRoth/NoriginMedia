package rothschild.henning.jacob.noriginmedia;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import rothschild.henning.jacob.noriginmedia.model.Fetcher;

import static junit.framework.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedIntegrationTest {
    
    private final static String FILENAME = "epg.txt";
    private final static String URL_STRING = "http://localhost:1337/epg";
    
    // FIXME: DRY...
    
    /** Asserts that Fetcher.Remote(...) returns correct content from EPG */
    @Test
    public void fetchLocal() throws Exception {
        // Context of the app under test.
        Context context = InstrumentationRegistry.getTargetContext();
        // NOTE: .toString() must be called. It seems like otherwise == is called
        assertEquals(Fetcher.local(context, FILENAME).toString(), new JSONObject(localRead(FILENAME)).toString());
    }
    
    private String localRead(String filename) throws IOException {
        BufferedReader reader = filenameToBufferedReader(filename);
        String output = bufferedReaderToContentString(reader);
        reader.close();
        return output;
    }
    
    /** @return A BufferedReader for reading the content of the file 'filename' */
    private BufferedReader filenameToBufferedReader(String filename) throws IOException {
        return new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(filename)));
    }
    
    /** @return A long String made by all the contents of 'reader' */
    private String bufferedReaderToContentString(BufferedReader reader) throws IOException {
        return bufferedReaderToContentStringBuilder(reader).toString();
    }
    
    /** @return A StringBuilder made up of all the contents of 'reader' */
    private StringBuilder bufferedReaderToContentStringBuilder(BufferedReader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) stringBuilder.append(line);
        return stringBuilder;
    }
}
