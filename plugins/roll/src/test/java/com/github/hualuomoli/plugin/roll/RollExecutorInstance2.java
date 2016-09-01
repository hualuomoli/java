package com.github.hualuomoli.plugin.roll;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.plugin.roll.DefaultRollServiceTest.User;
import com.github.hualuomoli.plugin.roll.SerializableRoll.SerializableDealer;

public class RollExecutorInstance2 implements SerializableDealer {

	private static final Logger logger = LoggerFactory.getLogger(RollExecutorInstance2.class);

	@Override
	public boolean deal(Serializable data) {
		User user = (User) data;
		logger.debug("{}-{}", user.getUsername(), user.getNickname());
		return true;
	}

}
