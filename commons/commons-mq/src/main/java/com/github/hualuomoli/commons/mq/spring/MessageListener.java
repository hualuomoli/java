package com.github.hualuomoli.commons.mq.spring;

import javax.jms.ConnectionFactory;

import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

public abstract class MessageListener extends DefaultMessageListenerContainer implements javax.jms.MessageListener {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected PooledConnectionFactory consumerPooledConnectionFactory;

	public MessageListener() {
		super();
		setDestinationName(getDestinationName());
		setMessageListener(this);
		addConfig();
	}

	public void addConfig() {
	}

	public abstract String getDestinationName();

	public void setConsumerPooledConnectionFactory(PooledConnectionFactory consumerPooledConnectionFactory) {
		this.consumerPooledConnectionFactory = consumerPooledConnectionFactory;
	}

	@Override
	public ConnectionFactory getConnectionFactory() {
		return consumerPooledConnectionFactory;
	}

}
