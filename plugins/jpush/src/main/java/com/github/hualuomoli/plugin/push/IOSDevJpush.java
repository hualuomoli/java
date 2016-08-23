package com.github.hualuomoli.plugin.push;

import java.util.Map;

import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

// ios 开发环境
public class IOSDevJpush extends Jpush {

	@Override
	public PushPayload buildAllNotification(String alert, String title, Map<String, String> extras) {
		return PushPayload.newBuilder() //
				.setPlatform(Platform.all())//
				.setAudience(Audience.all()) //
				.setNotification(Notification.ios(alert, extras))//
				.build();
	}

}
