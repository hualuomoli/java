package com.github.hualuomoli.activemq.listener.topic;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.mq.listener.MessageListener;
import com.github.hualuomoli.activemq.entity.User;

public class UserMessageListener1 implements MessageListener {

	private static final Logger logger = LoggerFactory.getLogger(UserMessageListener1.class);

	@Override
	public String getDestinationName() {
		return "user";
	}

	@Override
	public boolean onMessage(Serializable serializable) {
		User user = (User) serializable;
		logger.info("receive message {}", user.getId());
		// reload spring this class's hashCode no update
		logger.info("receive message {}", this.hashCode());
		return true;
	}

}
