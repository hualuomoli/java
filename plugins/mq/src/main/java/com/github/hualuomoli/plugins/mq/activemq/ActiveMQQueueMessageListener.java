package com.github.hualuomoli.plugins.mq.activemq;

import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.command.ActiveMQTextMessage;

import com.github.hualuomoli.plugins.mq.jms.JmsQueueMessageListener;

public abstract class ActiveMQQueueMessageListener extends JmsQueueMessageListener {

	@Override
	public void onMessage(Message message) {
		if (message == null) {
			return;
		}
		try {
			if (message instanceof ActiveMQTextMessage) {
				ActiveMQTextMessage m = (ActiveMQTextMessage) message;
				onMessage(m.getText());
				return;
			}
			if (message instanceof ActiveMQObjectMessage) {
				ActiveMQObjectMessage m = (ActiveMQObjectMessage) message;
				onMessage(m.getObject());
				return;
			}
			if (message instanceof ActiveMQMapMessage) {
				ActiveMQMapMessage m = (ActiveMQMapMessage) message;
				onMessage(m.getContentMap());
				return;
			}
			throw new JMSException("con't match class type " + message.getClass().getName());
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}

}
