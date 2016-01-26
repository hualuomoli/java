package com.github.hualuomoli.commons.mq.spring.topic;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.MessageCreator;

public class TopicJmsTemplateTest {

	private static final Logger logger = LoggerFactory.getLogger(TopicJmsTemplateTest.class);

	private static ApplicationContext context;
	private static ConnectionFactory connectionFactory;

	@BeforeClass
	public static void beforeClass() {
		context = new ClassPathXmlApplicationContext(new String[] { "classpath*:spring/application-context-mq.xml" });
		connectionFactory = (ConnectionFactory) context.getBean("producerPooledConnectionFactory");
	}

	@Test
	public void test() {

		TopicJmsTemplate topicJmsTemplate = new TopicJmsTemplate() {

			@Override
			public String getTopicName() {
				return "spring.topic";
			}

			@Override
			public ConnectionFactory getConnectionFactory() {
				return connectionFactory;
			}
		};

		topicJmsTemplate.send(topicJmsTemplate.getTopicName(), new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage("this is a content from spring topic producer.");
			}
		});

		logger.debug("send text content to active MQ ok.");

		// wait consumer to consume
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
