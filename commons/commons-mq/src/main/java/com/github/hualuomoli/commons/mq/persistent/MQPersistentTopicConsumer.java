package com.github.hualuomoli.commons.mq.persistent;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.Topic;

import com.github.hualuomoli.commons.mq.factory.MQConnectionFactory;

public abstract class MQPersistentTopicConsumer {

	public void receive(String topicName) throws JMSException {
		Connection connection = MQConnectionFactory.getPersistentConsumerConnection(getClientID());
		final Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic(topicName);
		// consumer
		MessageConsumer consumer = session.createDurableSubscriber(topic, getClientID());
		// receive
		consumer.setMessageListener(new MessageListener() {
			public void onMessage(Message message) {
				MQPersistentTopicConsumer.this.onMessage(message);
				try {
					session.commit();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		try {
			Thread.sleep(1000 * 3600 * 24 * 365);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// close
			session.close();
		}
	}

	public abstract String getClientID();

	public abstract void onMessage(Message message);

}
