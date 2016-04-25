package com.github.hualuomoli.plugin.app.jpush.android;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.plugin.app.exception.AppException;
import com.github.hualuomoli.plugin.app.jpush.JPushSender;
import com.github.hualuomoli.plugin.app.push.entity.Notification;
import com.google.common.collect.Sets;

public class AndroidJPushSenderTest {

	private static final Logger logger = LoggerFactory.getLogger(AndroidJPushSender.class);

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
	public void testBuildNotification() throws AppException {
		// send all
		sender.send(push);

	}

	@Test
	public void testBuildNotificationTags() throws AppException {
		// send tags
		push.setTags(Sets.newHashSet("account", "game", "manager"));
		sender.send(push);
		logger.debug("send notification end.");
	}

	@Test
	public void testBuildNotificationAlias() throws AppException {
		// send tags
		push.setAliases(Sets.newHashSet("admin", "user"));
		sender.send(push);
		logger.debug("send notification with alias end.");
	}

}
