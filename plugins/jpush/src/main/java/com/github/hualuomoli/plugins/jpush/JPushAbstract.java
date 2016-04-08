package com.github.hualuomoli.plugins.jpush;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import com.github.hualuomoli.plugins.jpush.exception.JPushException;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.PushPayload;

public abstract class JPushAbstract implements JPush {

	private static final Object OBJECT = new Object();

	private static String masterSecret = null;
	private static String appKey = null;
	private static int maxRetryTimes = 0;

	private static Properties prop = null;

	public void pushNotification(String alert, String title, Map<String, String> extras) {
		try {
			getJpushClient().sendPush(this.builderNotification(alert, title, extras));
		} catch (Exception e) {
			throw new JPushException(e);
		}
	}

	public abstract PushPayload builderNotification(String alert, String title, Map<String, String> extras)
			throws JPushException;

	private static JPushClient getJpushClient() {
		return new JPushClient(getMasterSecret(), getAppKey(), getMaxRetryTimes());
	}

	public static String getMasterSecret() {
		if (masterSecret == null) {
			init();
		}
		return masterSecret;
	}

	public static String getAppKey() {
		if (appKey == null) {
			init();
		}
		return appKey;
	}

	public static int getMaxRetryTimes() {
		if (maxRetryTimes == 0) {
			init();
		}
		return maxRetryTimes;
	}

	private static void init() {
		if (prop == null) {
			synchronized (OBJECT) {
				if (prop == null) {
					prop = new Properties();
					try {
						prop.load(JPush.class.getClassLoader().getResourceAsStream("jpush.properties"));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		String env = prop.getProperty("jpush.env");
		masterSecret = prop.getProperty("jpush." + env + ".masterSecret");
		appKey = prop.getProperty("jpush." + env + ".appKey");
		maxRetryTimes = Integer.parseInt(prop.getProperty("jpush." + env + ".maxRetryTimes"));

		System.out.println("masterSecret = " + masterSecret);
		System.out.println("appKey = " + appKey);
		System.out.println("maxRetryTimes = " + maxRetryTimes);

	}

}
