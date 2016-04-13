package com.github.hualuomoli.plugins.mq.listener;

import java.io.Serializable;

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
