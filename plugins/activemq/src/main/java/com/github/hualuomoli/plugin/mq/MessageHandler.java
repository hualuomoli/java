package com.github.hualuomoli.plugin.mq;

import javax.jms.Message;

/**
 * 消息处理器
 * @author hualuomoli
 *
 */
public interface MessageHandler {

	/**  
	 * Process the message, if process finish successfully, return true, otherwise false 
	 * return true if process successfully 
	 */
	boolean processMessage(Message message);

}
