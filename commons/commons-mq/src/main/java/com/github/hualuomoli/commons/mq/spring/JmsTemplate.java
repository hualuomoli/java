package com.github.hualuomoli.commons.mq.spring;

import javax.jms.ConnectionFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class JmsTemplate extends org.springframework.jms.core.JmsTemplate {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected ConnectionFactory producerConnectionFactory;

	public JmsTemplate() {
		super();
		setDefaultDestinationName("JMS.TEMPLATE");
		addConfig();
	}

	public void addConfig() {
	}

	public abstract String getDestinationName();

	public void setProducerConnectionFactory(ConnectionFactory producerConnectionFactory) {
		this.producerConnectionFactory = producerConnectionFactory;
	}

	@Override
	public ConnectionFactory getConnectionFactory() {
		return producerConnectionFactory;
	}

}
