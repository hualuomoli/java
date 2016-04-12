package com.github.hualuomoli.plugins.mq.activemq.listener.queue;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.plugins.mq.activemq.entity.Order;
import com.github.hualuomoli.plugins.mq.listener.MessageListener;

public class OrderMessageListener2 implements MessageListener {

	private static final Logger logger = LoggerFactory.getLogger(OrderMessageListener2.class);

	@Override
	public void onMessage(Serializable serializable) {
		Order order = (Order) serializable;
		logger.info("receive message {}", order.getId());
		// reload spring this class's hashCode no update
		logger.info("receive message {}", this.hashCode());
	}

	@Override
	public String getDestinationName() {
		return "order";
	}

}
