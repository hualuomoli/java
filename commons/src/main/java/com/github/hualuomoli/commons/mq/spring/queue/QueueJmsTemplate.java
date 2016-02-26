package com.github.hualuomoli.commons.mq.spring.queue;

import javax.jms.Queue;

import com.github.hualuomoli.commons.mq.spring.JmsTemplate;

public abstract class QueueJmsTemplate extends JmsTemplate implements Queue {

	public QueueJmsTemplate() {
		super();
	}

	@Override
	public String getDestinationName() {
		return getQueueName();
	}

	@Override
	public abstract String getQueueName();

}
