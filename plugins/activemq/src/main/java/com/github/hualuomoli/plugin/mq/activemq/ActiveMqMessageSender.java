package com.github.hualuomoli.plugin.mq.activemq;

import java.io.Serializable;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.github.hualuomoli.plugin.mq.exception.MessageException;
import com.github.hualuomoli.plugin.mq.sender.MessageSender;

/**
 * Active MQ 消息发送
 * @author hualuomoli
 *
 */
public class ActiveMqMessageSender implements MessageSender {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private String destinationName;
	private ConnectionFactory connectionFactory;
	private boolean pubSubDomain;

	private JmsTemplate jmsTemplate;

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public void setPubSubDomain(boolean pubSubDomain) {
		this.pubSubDomain = pubSubDomain;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	private JmsTemplate getJmsTemplate() {
		if (jmsTemplate == null) {
			jmsTemplate = new JmsTemplate(connectionFactory);
			jmsTemplate.setPubSubDomain(pubSubDomain);

		}
		return jmsTemplate;
	}

	@Override
	public String getDestinationName() {
		return destinationName;
	}

	@Override
	public void send(final Serializable serializable) throws MessageException {
		try {
			this.getJmsTemplate().send(this.getDestinationName(), new MessageCreator() {

				@Override
				public Message createMessage(Session session) throws JMSException {
					return session.createObjectMessage(serializable);
				}
			});
			if (logger.isDebugEnabled()) {
				logger.debug("send message ok.");
				logger.debug("send data {} ", serializable);
			}
		} catch (Exception e) {
			logger.warn("send message error {}", e);
			throw new MessageException(e);
		}
	}

}
