package com.github.hualuomoli.plugins.jpush.android;

import java.util.Map;

import com.github.hualuomoli.plugins.jpush.JPush;
import com.github.hualuomoli.plugins.jpush.JPushAbstract;
import com.github.hualuomoli.plugins.jpush.exception.JPushException;

import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

public class JPushAndroid extends JPushAbstract {

	private static JPush jpush;

	public static JPush getInstance() {
		if (jpush == null) {
			jpush = new JPushAndroid();
		}
		return jpush;
	}

	@Override
	public PushPayload builderNotification(String alert, String title, Map<String, String> extras) throws JPushException {
		return PushPayload.newBuilder() //
				.setPlatform(Platform.all())//
				.setAudience(Audience.all()) //
				.setNotification(Notification.android(alert, title, extras))//
				.build();
	}

}
