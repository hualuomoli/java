package com.github.hualuomoli.plugin.mq;

import java.io.Serializable;

/**
 * 处理消息
 * @author hualuomoli
 *
 */
public interface MessageDealer {

	/**
	 * 接收消息
	 * @param data 数据
	 * @return 数据正常处理返回true,否则返回false
	 */
	boolean onMessage(Serializable data);

}
