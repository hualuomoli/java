package com.github.hualuomoli.activemq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

public class ActiveMqMessageListener extends DefaultMessageListenerContainer implements MessageListener {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public ActiveMqMessageListener() {
		super();
		setMessageListener(this);
	}

	private com.github.hualuomoli.mq.listener.MessageListener listener;

	public void setListener(com.github.hualuomoli.mq.listener.MessageListener listener) {
		this.listener = listener;
		setDestinationName(listener.getDestinationName());
	}

	@Override
	public void onMessage(Message message) {
		if (message instanceof ActiveMQObjectMessage) {
			ActiveMQObjectMessage m = (ActiveMQObjectMessage) message;
			try {
				if (logger.isDebugEnabled()) {
					logger.debug("receive message ok.");
					logger.debug("receive data {} ", m.getObject());
				}
				listener.onMessage(m.getObject());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		} else {
			// message.
			logger.error("con't constant message type {}", message.getClass().getName());
		}
	}

}
