package com.github.hualuomoli.plugins.mq.activemq.listener.topic;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.plugins.mq.activemq.entity.User;
import com.github.hualuomoli.plugins.mq.listener.MessageListener;

public class UserMessageListener2 implements MessageListener {

	private static final Logger logger = LoggerFactory.getLogger(UserMessageListener2.class);

	@Override
	public String getDestinationName() {
		return "user";
	}

	@Override
	public void onMessage(Serializable serializable) {
		User user = (User) serializable;
		logger.info("receive message {}", user.getId());
		// reload spring this class's hashCode no update
		logger.info("receive message {}", this.hashCode());

	}

}
