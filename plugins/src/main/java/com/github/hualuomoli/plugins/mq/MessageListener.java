package com.github.hualuomoli.plugins.mq;

import java.io.Serializable;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface MessageListener {

	public static final Logger logger = LoggerFactory.getLogger(MessageListener.class);

	void onMessage(String content);

	void onMessage(Serializable serializable);

	void onMessage(Map<String, Object> map);

}
