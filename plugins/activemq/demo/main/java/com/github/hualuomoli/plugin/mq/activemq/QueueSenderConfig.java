package com.github.hualuomoli.plugin.mq.activemq;

import javax.jms.DeliveryMode;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import com.github.hualuomoli.plugin.mq.ActiveMqMessageSender;
import com.github.hualuomoli.plugin.mq.MessageSender;
import com.github.hualuomoli.plugin.mq.YamlActiveMQConfig;

@Configuration
public class QueueSenderConfig {

	private static final Logger logger = LoggerFactory.getLogger(QueueSenderConfig.class);

	public ActiveMQConnectionFactory connectionFactory() {
		String userName = YamlActiveMQConfig.getInstance().getValue("sender.username");
		String password = YamlActiveMQConfig.getInstance().getValue("sender.password");
		String brokerURL = YamlActiveMQConfig.getInstance().getValue("sender.brokerURL");
		if (logger.isInfoEnabled()) {
			logger.info("userName {}", userName);
			logger.info("password {}", password);
			logger.info("brokerURL {}", brokerURL);
		}
		return new ActiveMQConnectionFactory(userName, password, brokerURL);
	}

	public PooledConnectionFactory pooledConnectionFactory() {
		PooledConnectionFactory pool = new PooledConnectionFactory();
		pool.setConnectionFactory(this.connectionFactory());
		pool.setMaxConnections(10);
		return pool;
	}

	public ActiveMQQueue queue() {
		ActiveMQQueue queue = new ActiveMQQueue("queue");
		return queue;
	}

	public JmsTemplate jmsTemplate() {
		JmsTemplate jmsTemplate = new JmsTemplate();
		jmsTemplate.setConnectionFactory(this.pooledConnectionFactory());
		jmsTemplate.setDefaultDestination(this.queue());
		// deliveryMode, priority, timeToLive 的开关要生效，必须配置为true，默认false
		jmsTemplate.setExplicitQosEnabled(true);
		// 发送模式
		jmsTemplate.setDeliveryMode(DeliveryMode.PERSISTENT);
		return jmsTemplate;
	}

	@Bean
	public MessageSender messageSender() {

		ActiveMqMessageSender messageSender = new ActiveMqMessageSender();
		messageSender.setJmsTemplate(this.jmsTemplate());

		return messageSender;
	}

}
