package com.github.hualuomoli.commons.mq.spring.topic;

import javax.jms.Topic;

import com.github.hualuomoli.commons.mq.spring.MessageListener;

public abstract class TopicMessageListener extends MessageListener implements Topic {

	public TopicMessageListener() {
		super();
	}

	@Override
	public void addConfig() {
		setPubSubDomain(true);
	}

	@Override
	public String getDestinationName() {
		return getTopicName();
	}

	@Override
	public abstract String getTopicName();

}
