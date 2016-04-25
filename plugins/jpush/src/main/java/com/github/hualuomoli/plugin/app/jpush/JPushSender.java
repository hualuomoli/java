package com.github.hualuomoli.plugin.app.jpush;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.plugin.app.exception.AppException;
import com.github.hualuomoli.plugin.app.push.SenderAbstract;
import com.github.hualuomoli.plugin.app.push.entity.Message;
import com.github.hualuomoli.plugin.app.push.entity.Notification;
import com.github.hualuomoli.plugin.app.push.entity.RichTxt;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.PushPayload;

/**
 * jpush发送
 * @author hualuomoli
 *
 */
public abstract class JPushSender extends SenderAbstract {

	protected static final Logger logger = LoggerFactory.getLogger(JPushSender.class);

	private static final Object OBJECT = new Object();

	private static String masterSecret = null;
	private static String appKey = null;
	private static int maxRetryTimes = 0;

	private static Properties prop = null;

	@Override
	public void send(Notification push) throws AppException {
		this.send(this.build(push));
	}

	@Override
	public void send(Message push) throws AppException {
		this.send(this.build(push));
	}

	@Override
	public void send(RichTxt push) throws AppException {
		this.send(this.build(push));
	}

	private void send(List<PushPayload> pushPayloads) throws AppException {
		try {
			for (PushPayload pushPayload : pushPayloads) {
				getJpushClient().sendPush(pushPayload);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("push size {} ok.", pushPayloads.size());
			}
		} catch (Exception e) {
			throw new AppException(e);
		}
	}

	/**
	 * 创建发送payload
	 * @param push 发送消息
	 * @return payload
	 * @throws AppException 异常
	 */
	public abstract List<PushPayload> build(Notification push) throws AppException;

	/**
	 * 创建发送payload
	 * @param push 发送消息
	 * @return payload
	 * @throws AppException 异常
	 */
	public abstract List<PushPayload> build(Message push) throws AppException;

	/**
	 * 创建发送payload
	 * @param push 发送消息
	 * @return payload
	 * @throws AppException 异常
	 */
	public abstract List<PushPayload> build(RichTxt push) throws AppException;

	/**
	 * 获取client
	 * @return client
	 */
	public static JPushClient getJpushClient() {
		return new JPushClient(getMasterSecret(), getAppKey(), getMaxRetryTimes());
	}

	private static String getMasterSecret() {
		if (masterSecret == null) {
			init();
		}
		return masterSecret;
	}

	private static String getAppKey() {
		if (appKey == null) {
			init();
		}
		return appKey;
	}

	private static int getMaxRetryTimes() {
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
						prop.load(JPushSender.class.getClassLoader().getResourceAsStream("jpush.properties"));
					} catch (IOException e) {
						e.printStackTrace();
					}

					String env = prop.getProperty("jpush.env");
					masterSecret = prop.getProperty("jpush." + env + ".masterSecret");
					appKey = prop.getProperty("jpush." + env + ".appKey");
					maxRetryTimes = Integer.parseInt(prop.getProperty("jpush." + env + ".maxRetryTimes"));

					if (logger.isInfoEnabled()) {
						logger.info("env = {}", env);
						logger.info("masterSecret = {}", masterSecret);
						logger.info("appKey = {}", appKey);
						logger.info("maxRetryTimes = {}", maxRetryTimes);
					}

				}
			}
		}

	}

}
