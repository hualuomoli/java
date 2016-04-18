package com.github.hualuomoli.jpush.android;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.github.hualuomoli.app.exception.AppException;
import com.github.hualuomoli.app.push.entity.Notification;
import com.github.hualuomoli.jpush.JPushSender;
import com.google.common.collect.Sets;

public class AndroidJPushSenderTest {

	private static JPushSender sender;
	private Notification push;

	@BeforeClass
	public static void beforeClass() {
		sender = AndroidJPushSender.getInstance();
	}

	@Before
	public void before() {
		push = new Notification();
		push.setAlert("提示信息");
		push.setTitle("标题");
		Map<String, String> extras = new HashMap<String, String>();
		extras.put("id", "1234");
		push.setExtras(extras);
	}

	@Test
	@Ignore
	public void testBuildNotification() throws AppException {
		// send all
		sender.send(push);

	}

	@Test
	@Ignore
	public void testBuildNotificationTags() throws AppException {
		// send tags
		push.setTags(Sets.newHashSet("account", "game", "manager"));
		sender.send(push);

	}

	@Test
	@Ignore
	public void testBuildNotificationAlias() throws AppException {
		// send tags
		push.setAliases(Sets.newHashSet("admin", "user"));
		sender.send(push);
	}

}
