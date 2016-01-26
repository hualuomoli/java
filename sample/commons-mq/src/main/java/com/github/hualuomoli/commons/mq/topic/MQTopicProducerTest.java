package com.github.hualuomoli.commons.mq.topic;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MQTopicProducerTest {

	private static final Logger logger = LoggerFactory.getLogger(MQTopicProducerTest.class);

	@Test
	public void testSend() throws JMSException {
		new MQTopicProducer() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage("this is a content from topic producer.");
			}
		}.send("topic");
		logger.debug("send text content to active MQ ok.");
	}

}
