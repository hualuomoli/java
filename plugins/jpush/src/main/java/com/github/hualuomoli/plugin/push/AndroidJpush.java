package com.github.hualuomoli.plugin.push;

import java.util.Map;

import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

// 安卓
public class AndroidJpush extends Jpush {

	@Override
	public PushPayload buildAllNotification(String alert, String title, Map<String, String> extras) {
		return PushPayload.newBuilder() //
				.setPlatform(Platform.all())//
				.setAudience(Audience.all()) //
				.setNotification(Notification.android(alert, title, extras))//
				.build();
	}

}
