package com.github.hualuomoli.commons.mq.factory;

import java.io.IOException;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnectionFactory;

public class MQConnectionFactory {

	private static final String fileName = "prop/mq.properties";
	private static final Properties prop = new Properties();
	private static String environment = null;

	private static final Object PRODUCER = new Object();
	private static final Object CONSUMER = new Object();
	private static Connection producerConnection = null;
	private static Connection consumerConnection = null;

	static {
		try {
			prop.load(MQConnectionFactory.class.getClassLoader().getResourceAsStream(fileName));
			environment = prop.getProperty("project.environment");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public static Connection getProducerConnection() throws JMSException {

		if (producerConnection != null) {
			return producerConnection;
		}

		synchronized (PRODUCER) {
			if (producerConnection != null) {
				return producerConnection;
			}
			String user = prop.getProperty(environment + ".mq.producer.username");
			String password = prop.getProperty(environment + ".mq.producer.password");
			String url = prop.getProperty(environment + ".mq.producer.url");
			// get connection factory
			ConnectionFactory contectionFactory = new ActiveMQConnectionFactory(user, password, url);
			// get connect
			Connection connection = contectionFactory.createConnection();
			// start
			connection.start();
			return connection;
		}
	}

	public static Connection getConsumerConnection() throws JMSException {

		if (consumerConnection != null) {
			return consumerConnection;
		}

		synchronized (CONSUMER) {
			if (consumerConnection != null) {
				return consumerConnection;
			}
			String user = prop.getProperty(environment + ".mq.consumer.username");
			String password = prop.getProperty(environment + ".mq.consumer.password");
			String url = prop.getProperty(environment + ".mq.consumer.url");
			// get connection factory
			ConnectionFactory contectionFactory = new ActiveMQConnectionFactory(user, password, url);
			// get connect
			Connection connection = contectionFactory.createConnection();
			// start
			connection.start();
			return connection;
		}
	}

	public static Connection getPersistentConsumerConnection(String clientID) throws JMSException {

		if (consumerConnection != null) {
			return consumerConnection;
		}

		synchronized (CONSUMER) {
			if (consumerConnection != null) {
				return consumerConnection;
			}
			String user = prop.getProperty(environment + ".mq.consumer.username");
			String password = prop.getProperty(environment + ".mq.consumer.password");
			String url = prop.getProperty(environment + ".mq.consumer.url");
			// get connection factory
			ActiveMQConnectionFactory contectionFactory = new ActiveMQConnectionFactory(user, password, url);
			contectionFactory.setUseAsyncSend(true);
			// get connect
			Connection connection = contectionFactory.createConnection();
			connection.setClientID(clientID);
			// start
			connection.start();
			return connection;
		}
	}
}
