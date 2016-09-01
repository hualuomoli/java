package com.github.hualuomoli.plugin.roll;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.plugin.roll.ByteRoll.ByteDealer;
import com.github.hualuomoli.plugin.roll.StringRoll.StringDealer;

public class RollExecutorInstance implements StringDealer, ByteDealer {

	private static final Logger logger = LoggerFactory.getLogger(RollExecutorInstance.class);

	@Override
	public boolean deal(String data) {
		logger.debug(">>>>>>>>>>>>>>> intance - string {}", data);
		return true;
	}

	@Override
	public boolean deal(byte[] data) {
		try {
			logger.debug(">>>>>>>>>>>>>>> intance - byte[] {}", new String(data, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
		}
		return true;
	}

}
