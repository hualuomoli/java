package com.github.hualuomoli.commons.mq.persistent;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MQPersistentTopicConsumerTest2 {

	private static final Logger logger = LoggerFactory.getLogger(MQPersistentTopicConsumerTest2.class);

	@Test
	public void testReceive() throws JMSException {
		new MQPersistentTopicConsumer() {

			@Override
			public String getClientID() {
				return "persistent2";
			}

			@Override
			public void onMessage(Message message) {
				try {
					TextMessage m = (TextMessage) message;
					logger.debug("get content '{}' from active MQ", m.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}.receive("persistent_topic");
	}

}
