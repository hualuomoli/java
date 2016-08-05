package com.github.hualuomoli.plugin.mq;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.github.hualuomoli.commons.util.JsonUtils;

/**
 * ActiveMQ发送消息
 * @author hualuomoli
 *
 */
public class ActiveMqMessageSender implements MessageSender {

	private static final Logger logger = LoggerFactory.getLogger(ActiveMqMessageSender.class);

	private JmsTemplate jmsTemplate;

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	@Override
	public boolean success() {
		return jmsTemplate != null;
	}

	@Override
	public void send(String destinationName, final Serializable data) throws MessageException {
		if (logger.isDebugEnabled()) {
			logger.debug("send data {}" + JsonUtils.getInstance().toJson(data));
		}

		if (!success()) {
			throw new MessageException("invalid message queue.");
		}

		try {
			jmsTemplate.send(destinationName, new MessageCreator() {

				@Override
				public Message createMessage(Session session) throws JMSException {
					return session.createObjectMessage(data);
				}
			});
		} catch (Exception e) {
			logger.warn("send message error {}", e);
			throw new MessageException(e);
		}

	}

}
