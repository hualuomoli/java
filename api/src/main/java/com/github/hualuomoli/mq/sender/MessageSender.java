package com.github.hualuomoli.mq.sender;

import java.io.Serializable;

import com.github.hualuomoli.mq.Message;
import com.github.hualuomoli.mq.exception.MessageException;

/**
 * 消息发送
 * @author hualuomoli
 *
 */
public interface MessageSender extends Message {

	/**
	 * 发送消息
	 * @param serializable
	 */
	void send(final Serializable serializable) throws MessageException;

}
