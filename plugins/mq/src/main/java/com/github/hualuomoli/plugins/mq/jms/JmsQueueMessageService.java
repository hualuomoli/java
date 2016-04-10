package com.github.hualuomoli.plugins.mq.jms;

import java.io.Serializable;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.github.hualuomoli.plugins.mq.MQException;
import com.github.hualuomoli.plugins.mq.QueueMessageService;

/**
 * ActiveMQ队列服务
 *
 */
public class JmsQueueMessageService extends JmsTemplate implements QueueMessageService {

	// private ConnectionFactory connectionFactory;

	public JmsQueueMessageService() {
		super();
		setDefaultDestinationName(DEFAULT_DESTINATION_NAME);
	}

	@Override
	public void sendMessage(String destinationName, final String content) throws MQException {
		try {
			this.send(destinationName, new MessageCreator() {

				@Override
				public Message createMessage(Session session) throws JMSException {
					return session.createTextMessage(content);
				}
			});
		} catch (Exception e) {
			throw new MQException(e);
		}

	}

	@Override
	public void sendMessage(String destinationName, final Serializable serializable) throws MQException {
		try {
			this.send(destinationName, new MessageCreator() {

				@Override
				public Message createMessage(Session session) throws JMSException {
					return session.createObjectMessage(serializable);
				}
			});
		} catch (Exception e) {
			throw new MQException(e);
		}

	}

	@Override
	public void sendMessage(String destinationName, final Map<String, Object> map) throws MQException {
		try {
			this.send(destinationName, new MessageCreator() {

				@Override
				public Message createMessage(Session session) throws JMSException {
					MapMessage message = session.createMapMessage();
					if (map != null && map.size() > 0) {
						for (String key : map.keySet()) {
							message.setObject(key, map.get(key));
						}
					}
					return message;
				}
			});
		} catch (Exception e) {
			throw new MQException(e);
		}
	}

}
