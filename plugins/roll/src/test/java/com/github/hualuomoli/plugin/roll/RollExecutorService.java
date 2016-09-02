package com.github.hualuomoli.plugin.roll;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.hualuomoli.plugin.roll.ByteRoll.ByteDealer;
import com.github.hualuomoli.plugin.roll.DefaultRollServiceTest.User;
import com.github.hualuomoli.plugin.roll.SerializableRoll.SerializableDealer;
import com.github.hualuomoli.plugin.roll.StringRoll.StringDealer;

@Service(value = "com.github.hualuomoli.plugin.roll.RollExecutorService")
public class RollExecutorService implements StringDealer, ByteDealer, SerializableDealer {

	private static final Logger logger = LoggerFactory.getLogger(RollExecutorService.class);

	@Override
	public boolean deal(String data) {
		logger.debug(">>>>>>>>>>>>>>> spring - string {}", data);
		return true;
	}

	@Override
	public boolean deal(byte[] data) {
		try {
			logger.debug(">>>>>>>>>>>>>>> spring - byte[] {}", new String(data, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
		}
		return true;
	}

	@Override
	public boolean deal(Serializable data) {
		User user = (User) data;
		logger.debug("{}-{}", user.getUsername(), user.getNickname());
		return true;
	}

}
