package rothschild.henning.jacob.noriginmedia.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by Jacob H. Rothschild on 24.07.2017.
 */

public class StorageWriter {
	
	public static void writeIfFilenameNotNull(Context appContext, @Nullable String filename, String content) throws IOException {
		if (filename != null) write(appContext, filename, content);
	}
	
	public static void write(Context appContext, @NonNull String filename, String content) throws IOException {
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(appContext.openFileOutput(filename, Context.MODE_PRIVATE));
		outputStreamWriter.write(content);
		outputStreamWriter.close();
	}
	
}
