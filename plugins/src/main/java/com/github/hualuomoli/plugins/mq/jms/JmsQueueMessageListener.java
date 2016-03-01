package com.github.hualuomoli.plugins.mq.jms;

import javax.jms.MessageListener;

import org.springframework.jms.listener.DefaultMessageListenerContainer;

import com.github.hualuomoli.plugins.mq.QueueMessageListener;

public abstract class JmsQueueMessageListener extends DefaultMessageListenerContainer
		implements MessageListener, QueueMessageListener {

	// private ConnectionFactory connectionFactory;

	public JmsQueueMessageListener() {
		super();
		setDestinationName(getQueueName());
		setMessageListener(this);
	}

}
