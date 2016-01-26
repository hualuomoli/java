package com.github.hualuomoli.commons.mq.queue;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MQQueueProducerTest {

	private static final Logger logger = LoggerFactory.getLogger(MQQueueProducerTest.class);

	@Test
	public void testSend() throws JMSException {
		new MQQueueProducer() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage("this is a content from queue producer.");
			}
		}.send("queue");
		logger.debug("send text content to active MQ ok.");
	}

}
