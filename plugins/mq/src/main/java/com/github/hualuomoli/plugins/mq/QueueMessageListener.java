package com.github.hualuomoli.plugins.mq;

import javax.jms.Queue;

public interface QueueMessageListener extends MessageListener, Queue {

	String getQueueName();

}
