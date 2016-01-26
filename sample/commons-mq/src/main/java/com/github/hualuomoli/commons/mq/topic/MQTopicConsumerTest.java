package com.github.hualuomoli.commons.mq.topic;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MQTopicConsumerTest {

	private static final Logger logger = LoggerFactory.getLogger(MQTopicConsumerTest.class);

	@Test
	public void testReceive() throws JMSException {
		new MQTopicConsumer() {

			@Override
			public void onMessage(Message message) {
				try {
					TextMessage m = (TextMessage) message;
					logger.debug("get content '{}' from active MQ", m.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}.receive("topic");
	}

}
