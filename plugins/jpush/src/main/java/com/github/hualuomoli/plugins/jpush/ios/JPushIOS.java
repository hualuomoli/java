package com.github.hualuomoli.plugins.jpush.ios;

import java.util.Map;

import com.github.hualuomoli.plugins.jpush.JPushAbstract;
import com.github.hualuomoli.plugins.jpush.exception.JPushException;

import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

public class JPushIOS extends JPushAbstract {

	@Override
	public PushPayload builderNotification(String alert, String title, Map<String, String> extras)
			throws JPushException {
		return PushPayload.newBuilder() //
				.setPlatform(Platform.all())//
				.setAudience(Audience.all()) //
				.setNotification(Notification.ios(alert, extras))//
				.build();
	}

}
