package rothschild.henning.jacob.noriginmedia;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import rothschild.henning.jacob.noriginmedia.model.BufferedReaderCreator;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedUnitTest {
    
    private final static String FILENAME = "epg.txt";
    private final static String URL_STRING = "http://localhost:1337/epg";
    
    /** Asserts that BufferedReaderCreator.localReader(...) returns a working BufferedReader.
     * Until cache has been established, this should fail. */
    @Test
    public void localReader() throws Exception {
        // Context of the app under test.
        Context context = InstrumentationRegistry.getTargetContext();
        assertEquals(bufferedReaderToContentString(BufferedReaderCreator.localReader(context, FILENAME)), localRead(FILENAME));
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
