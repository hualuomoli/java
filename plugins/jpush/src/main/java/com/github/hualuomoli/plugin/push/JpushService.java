package com.github.hualuomoli.plugin.push;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.commons.util.YamlUtils;
import com.google.common.collect.Lists;

import cn.jpush.api.JPushClient;

public class JpushService implements Push {

	private static final Logger logger = LoggerFactory.getLogger(JpushService.class);
	private static List<Jpush> list = Lists.newArrayList();

	static {
		List<App> apps = YamlUtils.getInstance().getList("apps", App.class, "jpush");
		List<String> devices = YamlUtils.getInstance().getList("devices", String.class, "jpush");

		for (App app : apps) {
			for (String device : devices) {
				Jpush jpush = null;

				if (logger.isDebugEnabled()) {
					logger.debug("masterSecret={}, appKey={}, maxRetryTimes={}, device={}", //
							app.masterSecret, app.appKey, app.maxRetryTimes, device);
				}

				switch (device) {
				case Jpush.DEVICE_ANDROID:
					jpush = new AndroidJpush();
					break;
				case Jpush.DEVICE_IOS_DEV:
					jpush = new IOSDevJpush();
					break;
				case Jpush.DEVICE_IOS:
					jpush = new IOSJpush();
					break;
				default:
					throw new RuntimeException("can not support device type " + device);
				}
				//
				jpush.setAppName(app.name);
				jpush.setSupports(app.supports);
				jpush.setjPushClient(new JPushClient(app.masterSecret, app.appKey, app.maxRetryTimes));
				list.add(jpush);
			}
			// end device
		}
		// end
	}

	@Override
	public void pushAllNotification(String type, String alert, String title, Map<String, String> extras) {
		for (Jpush jpush : list) {
			jpush.pushAllNotification(type, alert, title, extras);
		}
	}

	// 应用
	public static class App {

		private String name;
		private String masterSecret;
		private String appKey;
		private Integer maxRetryTimes;
		private Set<String> supports;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getMasterSecret() {
			return masterSecret;
		}

		public void setMasterSecret(String masterSecret) {
			this.masterSecret = masterSecret;
		}

		public String getAppKey() {
			return appKey;
		}

		public void setAppKey(String appKey) {
			this.appKey = appKey;
		}

		public Integer getMaxRetryTimes() {
			return maxRetryTimes;
		}

		public void setMaxRetryTimes(Integer maxRetryTimes) {
			this.maxRetryTimes = maxRetryTimes;
		}

		public Set<String> getSupports() {
			return supports;
		}

		public void setSupports(Set<String> supports) {
			this.supports = supports;
		}

	}

}
