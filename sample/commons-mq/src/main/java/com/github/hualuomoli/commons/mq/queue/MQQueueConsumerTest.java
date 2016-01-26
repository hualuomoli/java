package com.github.hualuomoli.commons.mq.queue;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MQQueueConsumerTest {

	private static final Logger logger = LoggerFactory.getLogger(MQQueueProducerTest.class);

	@Test
	public void testReceive() throws JMSException {
		new MQQueueConsumer() {

			@Override
			public void onMessage(Message message) {
				try {
					if (message == null)
						return;
					TextMessage m = (TextMessage) message;
					logger.debug("get content '{}' from active MQ", m.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}

			@Override
			public long getTimeout() {
				return 1000;
			}

		}.receive("queue");
	}

}
