package com.github.hualuomoli.plugin.push;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.PushPayload;

public abstract class Jpush implements Push {

	// 设备
	public static final String DEVICE_ANDROID = "android";
	public static final String DEVICE_IOS_DEV = "iosdev";
	public static final String DEVICE_IOS = "ios";

	protected static final Logger logger = LoggerFactory.getLogger(Jpush.class);

	protected JPushClient jPushClient;
	private Set<String> supports;

	public void setjPushClient(JPushClient jPushClient) {
		this.jPushClient = jPushClient;
	}

	public void setSupports(Set<String> supports) {
		this.supports = supports;
	}

	/**
	 * 是否支持
	 * @param type 数据的类型
	 * @return 是否支持
	 */
	public boolean support(String type) {
		return supports.contains(type);
	}

	@Override
	public void pushAllNotification(String type, String alert, String title, Map<String, String> extras) {
		if (!this.support(type)) {
			return;
		}
		this.doPush(this.buildAllNotification(alert, title, extras));
	}

	// 创建通知所有手机的payload
	public abstract PushPayload buildAllNotification(String alert, String title, Map<String, String> extras);

	// 推送
	private void doPush(PushPayload pushPayload) {
		try {
			this.jPushClient.sendPush(pushPayload);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("{}", e);
			}
		}
	}

}
