package com.github.hualuomoli.plugins.jpush;

import java.util.Map;

/**
 * jpush
 * 
 * @author hualuomoli
 *
 */
public interface JPush {

	/** push notification */
	void pushNotification(String alert, String title, Map<String, String> extras);

}
