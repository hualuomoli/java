package com.github.hualuomoli.commons.mq.spring.topic;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

public class TopicMessageListenerTest extends TopicMessageListener {

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
	public String getTopicName() {
		return "spring.topic";
	}

}
