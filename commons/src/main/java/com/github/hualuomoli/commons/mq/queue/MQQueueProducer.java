package com.github.hualuomoli.commons.mq.queue;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.springframework.jms.core.MessageCreator;

import com.github.hualuomoli.commons.mq.factory.MQConnectionFactory;

public abstract class MQQueueProducer implements MessageCreator {

	public void send(String queueName) throws JMSException {
		// get connect
		Connection connection = MQConnectionFactory.getProducerConnection();
		// get session
		Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
		// get queue
		Destination destination = session.createQueue(queueName);
		MessageProducer producer = session.createProducer(destination);
		// send
		Message message = this.createMessage(session);
		producer.send(message);
		// commit
		session.commit();
		// close
		session.close();
	}

}
