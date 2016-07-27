package com.github.hualuomoli.plugin.mq;

import java.io.Serializable;

/**
 * 消息队列发送消息
 * @author hualuomoli
 *
 */
public interface MessageSender {

	/**
	 * 消息队列是否可用
	 * @return 消息队列是否可用
	 */
	boolean success();

	/**
	 * 发送消息到消息队列
	 * @param destinationName 目标名称
	 * @param data 数据
	 * @return 是否发送成功
	 */
	void send(String destinationName, final Serializable data) throws MessageException;

}
