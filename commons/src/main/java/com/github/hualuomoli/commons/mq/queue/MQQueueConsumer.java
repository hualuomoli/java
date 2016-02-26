package com.github.hualuomoli.commons.mq.queue;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import com.github.hualuomoli.commons.mq.factory.MQConnectionFactory;

public abstract class MQQueueConsumer {

	public void receive(String queueName) throws JMSException {
		Connection connection = MQConnectionFactory.getConsumerConnection();
		final Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue(queueName);
		// consumer
		MessageConsumer consumer = session.createConsumer(destination);
		// receive
		this.onMessage(consumer.receive(this.getTimeout()));
		// commit
		session.commit();
		// close
		session.close();
	}

	public abstract long getTimeout();

	public abstract void onMessage(Message message);

}
