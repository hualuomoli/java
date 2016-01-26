package com.github.hualuomoli.commons.mq.persistent;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MQPersistentTopicProducerTest {

	private static final Logger logger = LoggerFactory.getLogger(MQPersistentTopicProducerTest.class);

	@Test
	public void testSend() throws JMSException {
		new MQPersistentTopicProducer() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage("this is a content from peristent producer.");
			}
		}.send("persistent_topic");
		logger.debug("send text content to active MQ ok.");
	}

}
