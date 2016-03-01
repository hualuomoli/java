package com.github.hualuomoli.plugins.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息服务
 *
 */
public interface MessageService {

	public static final Logger logger = LoggerFactory.getLogger(MessageService.class);

	// void send(String name, Serializable serializable) throws MQException;

	// void addListener(MQListener mqListener);

}
