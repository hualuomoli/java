package com.github.hualuomoli.plugin.mq.listener;

import java.io.Serializable;

import com.github.hualuomoli.plugin.mq.Message;

/**
 * 消息服务监听器
 * @author hualuomoli
 *
 */
public interface MessageListener extends Message {

	/**
	 * 接收到消息
	 * @param serializable 消息
	 * @return 处理完成返回true,否则返回false
	 */
	boolean onMessage(Serializable serializable);

}
