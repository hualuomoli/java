package com.github.hualuomoli.commons.mq.spring.queue;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// must config bean in spring configuration file
public class QueueMessageListenerTest extends QueueMessageListener {

	private static final Logger logger = LoggerFactory.getLogger(QueueMessageListenerTest.class);

	@Override
	public void onMessage(Message message) {
		try {
			TextMessage m = (TextMessage) message;
			logger.debug("get content '{}' from active MQ", m.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getQueueName() {
		return "spring.queue";
	}

}
