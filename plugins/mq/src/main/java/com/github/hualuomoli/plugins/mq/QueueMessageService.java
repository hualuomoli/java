package com.github.hualuomoli.plugins.mq;

import java.io.Serializable;
import java.util.Map;

/**
 * 队列消息服务
 *
 */
public interface QueueMessageService extends MessageService {

	public static final String DEFAULT_DESTINATION_NAME = "MQ.DEFAULT.QUEUE";

	/**
	 * 发送消息
	 * 
	 * @param destinationName
	 *            消息的Key
	 * @param content
	 *            消息内容
	 * @throws MQException
	 *             发送消息失败
	 */
	void sendMessage(String destinationName, final String content) throws MQException;

	/**
	 * 发送消息
	 * 
	 * @param destinationName
	 *            消息的Key
	 * @param serializable
	 *            消息内容
	 * @throws MQException
	 *             发送消息失败
	 */
	void sendMessage(String destinationName, final Serializable serializable) throws MQException;

	/**
	 * 发送消息
	 * 
	 * @param destinationName
	 *            消息的Key
	 * @param map
	 *            消息内容
	 * @throws MQException
	 *             发送消息失败
	 */
	void sendMessage(String destinationName, final Map<String, Object> map) throws MQException;

}
