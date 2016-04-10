package com.github.hualuomoli.plugins.mq.activemq;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ActiveMQQueueMessageListenerTest extends ActiveMQQueueMessageListener {

	@Override
	public String getQueueName() {
		return "activemq.queue";
	}

	@Override
	public void onMessage(String content) {
		System.out.println("text message '" + content + "'");
	}

	@Override
	public void onMessage(Serializable serializable) {
		System.out.println("serializable message '" + ToStringBuilder.reflectionToString(serializable) + "'");
	}

	@Override
	public void onMessage(Map<String, Object> map) {
		System.out.println("map message '" + map + "'");
	}

}
