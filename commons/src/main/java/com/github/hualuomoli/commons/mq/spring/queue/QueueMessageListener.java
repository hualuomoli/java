package com.github.hualuomoli.commons.mq.spring.queue;

import javax.jms.Queue;

import com.github.hualuomoli.commons.mq.spring.MessageListener;

public abstract class QueueMessageListener extends MessageListener implements Queue {

	public QueueMessageListener() {
		super();
	}

	@Override
	public String getDestinationName() {
		return getQueueName();
	}

	@Override
	public abstract String getQueueName();

}
