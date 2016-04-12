package com.github.hualuomoli.plugins.mq.listener;

import java.io.Serializable;

import com.github.hualuomoli.plugins.mq.activemq.entity.Order;

/**
 * 消息监听器
 * @author hualuomoli
 *
 */
public interface MessageListener {

	String getDestinationName();

	/**
	 * 接收到消息
	 * @param serializable 消息
	 */
	void onMessage(Serializable serializable);

}
