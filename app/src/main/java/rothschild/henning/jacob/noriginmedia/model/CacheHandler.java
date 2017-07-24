package rothschild.henning.jacob.noriginmedia.model;

import java.util.HashMap;

/**
 * Created by Jacob H. Rothschild on 24.07.2017.
 */

public class CacheHandler {
	
	private HashMap<String, Object> cache = new HashMap<>();
	
	/**
	 * Puts key-value pair in cache, if it is NOT identical to something stored there already
	 * @return Whether this key-value pair is NOT stored in cache
	 */
	public boolean ifNewWillCacheAndTell(String key, Object value) {
		boolean isNew = isNew(key, value);
		if (isNew) putInCache(key, value);
		return isNew;
	}
	
	/** @return Whether value is NOT stored in our cache (and thus tells us something new) */
	public boolean isNew(String key, Object value) {
		Object cached = cache.get(key);
		return cached == null || !value.equals(cached);
	}
	
	/** Puts key-value pair in cache */
	public void putInCache(String key, Object value) {
		cache.put(key, value);
	}
	
}
