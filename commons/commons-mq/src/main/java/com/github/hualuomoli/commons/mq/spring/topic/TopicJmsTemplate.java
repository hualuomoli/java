package com.github.hualuomoli.commons.mq.spring.topic;

import javax.jms.Topic;

import com.github.hualuomoli.commons.mq.spring.JmsTemplate;

public abstract class TopicJmsTemplate extends JmsTemplate implements Topic {

	public TopicJmsTemplate() {
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
