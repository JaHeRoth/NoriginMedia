package rothschild.henning.jacob.noriginmedia;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import rothschild.henning.jacob.noriginmedia.model.ReaderCreator;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedUnitTest {
    
    private TestResourceReader testEPGReader = new TestResourceReader(TestResourceReader.EPG_LOCAL);
    
    /** Asserts that ReaderCreator.localReader(...) returns a working BufferedReader.
     * Until cache has been established, this should fail. */
    @Test
    public void localReader() throws Exception {
        // Context of the app under test.
        Context context = InstrumentationRegistry.getTargetContext();
        assertEquals(testEPGReader.stringRead(), testEPGReader.bufferedReaderToContentString(ReaderCreator.localReader(context, TestResourceReader.EPG_LOCAL)));
    }
}
