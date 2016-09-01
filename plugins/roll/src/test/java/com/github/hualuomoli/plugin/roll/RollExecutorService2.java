package com.github.hualuomoli.plugin.roll;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.hualuomoli.plugin.roll.DefaultRollServiceTest.User;
import com.github.hualuomoli.plugin.roll.SerializableRoll.SerializableDealer;

@Service(value = "com.github.hualuomoli.plugin.roll.RollExecutorService2")
public class RollExecutorService2 implements SerializableDealer {

	private static final Logger logger = LoggerFactory.getLogger(RollExecutorService2.class);

	@Override
	public boolean deal(Serializable data) {
		User user = (User) data;
		logger.debug("{}-{}", user.getUsername(), user.getNickname());
		return true;
	}

}
