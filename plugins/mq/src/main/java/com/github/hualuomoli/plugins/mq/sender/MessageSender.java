package com.github.hualuomoli.plugins.mq.sender;

import java.io.Serializable;

import com.github.hualuomoli.plugins.mq.exception.MQException;

/**
 * 消息发送
 * @author hualuomoli
 *
 */
public interface MessageSender {

	/**
	 * 发送消息
	 * @param serializable
	 */
	void send(final Serializable serializable) throws MQException;

}
