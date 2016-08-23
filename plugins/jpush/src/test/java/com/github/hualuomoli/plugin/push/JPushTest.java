package com.github.hualuomoli.plugin.push;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;

import com.github.hualuomoli.commons.util.YamlUtils;
import com.google.common.collect.Lists;

import cn.jpush.api.JPushClient;

public class JPushTest {

	private List<App> apps = YamlUtils.getInstance().getList("apps", App.class, "jpush");
	private List<String> devices = YamlUtils.getInstance().getList("devices", String.class, "jpush");

	@Test
	public void test() {
		List<Jpush> list = Lists.newArrayList();

		for (App app : apps) {
			for (String device : devices) {
				Jpush jpush = null;

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
				jpush.setSupports(app.supports);
				jpush.setjPushClient(new JPushClient(app.masterSecret, app.masterSecret, app.maxRetryTimes));
				list.add(jpush);
			}
			// end device
		}

		// show client
		for (Jpush jpush : list) {
			System.out.println(ToStringBuilder.reflectionToString(jpush));
		}

	}

	// // 设备
	public static class Device {
		private String device;

		public String getDevice() {
			return device;
		}

		public void setDevice(String device) {
			this.device = device;
		}

	}

	// 应用
	public static class App {

		private String masterSecret;
		private String appKey;
		private Integer maxRetryTimes;
		private Set<String> supports;

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
