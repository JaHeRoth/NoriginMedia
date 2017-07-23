package rothschild.henning.jacob.noriginmedia;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import rothschild.henning.jacob.noriginmedia.model.Fetcher;

import static junit.framework.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedIntegrationTest {
    
    private TestResourceReader epgReader = new TestResourceReader(TestResourceReader.EPG_LOCAL);
    
    /** Asserts that Fetcher.Remote(...) returns correct content from EPG.
     * Until cache has been established, this should fail. */
    @Test
    public void fetchLocal() throws Exception {
        // Context of the app under test.
        Context context = InstrumentationRegistry.getTargetContext();
        // NOTE: .toString() must be called. It seems like '==' is called otherwise
        assertEquals(Fetcher.local(context, TestResourceReader.EPG_LOCAL).toString(), epgReader.jsonRead().toString());
    }
}
