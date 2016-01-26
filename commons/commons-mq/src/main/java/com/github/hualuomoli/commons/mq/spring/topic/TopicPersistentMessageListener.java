package com.github.hualuomoli.commons.mq.spring.topic;

public abstract class TopicPersistentMessageListener extends TopicMessageListener {

	@Override
	public void addConfig() {
		super.addConfig();
		// persistent
		setClientId(getClientID());
		setSubscriptionDurable(true);
	}

	public abstract String getClientID();

}
