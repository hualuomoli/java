package com.github.hualuomoli.commons.mq.topic;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.springframework.jms.core.MessageCreator;

import com.github.hualuomoli.commons.mq.factory.MQConnectionFactory;

public abstract class MQTopicProducer implements MessageCreator {

	public void send(String topicName) throws JMSException {
		// connection
		Connection connection = MQConnectionFactory.getProducerConnection();
		// session
		Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
		// topic
		Destination destination = session.createTopic(topicName);
		// producer
		MessageProducer producer = session.createProducer(destination);
		Message message = this.createMessage(session);
		// send
		producer.send(message);
		// commit
		session.commit();
		// close
		session.close();
	}

}
