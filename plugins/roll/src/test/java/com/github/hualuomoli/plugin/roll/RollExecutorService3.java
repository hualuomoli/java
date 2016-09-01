package com.github.hualuomoli.plugin.roll;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.hualuomoli.plugin.roll.SerializableRoll.SerializableDealer;

@Service(value = "com.github.hualuomoli.plugin.roll.RollExecutorService3")
public class RollExecutorService3 implements SerializableDealer {

	private static final Logger logger = LoggerFactory.getLogger(RollExecutorService3.class);

	@Override
	public boolean deal(Serializable data) {
		Demo demo = (Demo) data;
		logger.debug("{}-{}", demo.getAge(), demo.getDate());
		return true;
	}

}
