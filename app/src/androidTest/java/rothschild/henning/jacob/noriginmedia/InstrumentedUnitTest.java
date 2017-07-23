package rothschild.henning.jacob.noriginmedia;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import rothschild.henning.jacob.noriginmedia.misc.TestResourceReader;
import rothschild.henning.jacob.noriginmedia.model.BufferedReaderCreator;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedUnitTest {
    
    private TestResourceReader epgReader = new TestResourceReader(TestResourceReader.EPG_LOCAL);
    
    /** Asserts that BufferedReaderCreator.localReader(...) returns a working BufferedReader.
     * Until cache has been established, this should fail. */
    @Test
    public void localReader() throws Exception {
        // Context of the app under test.
        Context context = InstrumentationRegistry.getTargetContext();
        assertEquals(epgReader.bufferedReaderToContentString(BufferedReaderCreator.localReader(context, TestResourceReader.EPG_LOCAL)), epgReader.stringRead());
    }
}
