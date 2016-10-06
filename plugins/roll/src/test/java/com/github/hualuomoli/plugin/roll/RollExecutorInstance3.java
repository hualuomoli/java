package com.github.hualuomoli.plugin.roll;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.plugin.roll.SerializableRoll.SerializableDealer;

public class RollExecutorInstance3 implements SerializableDealer {

	private static final Logger logger = LoggerFactory.getLogger(RollExecutorInstance3.class);

	@Override
	public boolean deal(Serializable data) {
		Demo demo = (Demo) data;
		logger.debug("{}-{}", demo.getAge(), demo.getDate());
		return true;
	}

}
