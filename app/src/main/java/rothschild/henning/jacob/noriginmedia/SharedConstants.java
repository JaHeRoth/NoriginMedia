package rothschild.henning.jacob.noriginmedia;

/**
 * Created by Jacob H. Rothschild on 23.07.2017.
 */

public final class SharedConstants {
	public final static String CODE_BUNDLE_KEY = "result_code";
	public final static String READ_BUNDLE_KEY = "output_text";
	/** Reading succeeded */
	public final static int SUCCESS_CODE = 0;
	/** Remote reading returned nothing new */
	public final static int IDENTICAL_CODE = 1;
	/** Reading failed */
	public final static int FAILED_CODE = 2;
}
