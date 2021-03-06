package com.github.hualuomoli.plugin.mq.activemq.listener.pool;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.plugin.mq.activemq.entity.Order;
import com.github.hualuomoli.plugin.mq.listener.MessageListener;

public class OrderMessageListener1 implements MessageListener {

	private static final Logger logger = LoggerFactory.getLogger(OrderMessageListener1.class);

	@Override
	public String getDestinationName() {
		return "orderPool";
	}

	@Override
	public boolean onMessage(Serializable serializable) {
		Order order = (Order) serializable;
		logger.info("receive message {}", order.getId());
		// reload spring this class's hashCode no update
		logger.info("receive message {}", this.hashCode());
		return true;
	}

}
