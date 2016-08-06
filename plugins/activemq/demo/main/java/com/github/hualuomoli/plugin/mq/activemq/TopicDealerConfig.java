package com.github.hualuomoli.plugin.mq.activemq;

import java.io.Serializable;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import com.github.hualuomoli.commons.util.YamlUtils;
import com.github.hualuomoli.plugin.mq.DefaultMessageListener;
import com.github.hualuomoli.plugin.mq.MessageDealer;

/**
 * 使用Annotation自动注册Bean
 * @author hualuomoli
 *
 */
@Configuration
public class TopicDealerConfig {

	private static final Logger logger = LoggerFactory.getLogger(TopicDealerConfig.class);

	public ActiveMQConnectionFactory connectionFactoryUser1() {
		Conn conn = YamlUtils.getInstance().getObject("sender", Conn.class);
		if (logger.isInfoEnabled()) {
			logger.info("userName {}", conn.getUserName());
			logger.info("password {}", conn.getPassword());
			logger.info("brokerURL {}", conn.getBrokerURL());
		}
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(conn.getUserName(), conn.getPassword(), conn.getBrokerURL());
		connectionFactory.setClientID("user1");

		return connectionFactory;
	}

	public ActiveMQConnectionFactory connectionFactoryUser2() {
		Conn conn = YamlUtils.getInstance().getObject("sender", Conn.class);
		if (logger.isInfoEnabled()) {
			logger.info("userName {}", conn.getUserName());
			logger.info("password {}", conn.getPassword());
			logger.info("brokerURL {}", conn.getBrokerURL());
		}
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(conn.getUserName(), conn.getPassword(), conn.getBrokerURL());
		connectionFactory.setClientID("user2");

		return connectionFactory;
	}

	public PooledConnectionFactory pooledConnectionFactoryUser1() {
		PooledConnectionFactory pool = new PooledConnectionFactory();
		pool.setConnectionFactory(this.connectionFactoryUser1());
		pool.setMaxConnections(10);
		return pool;
	}

	public PooledConnectionFactory pooledConnectionFactoryUser2() {
		PooledConnectionFactory pool = new PooledConnectionFactory();
		pool.setConnectionFactory(this.connectionFactoryUser2());
		pool.setMaxConnections(10);
		return pool;
	}

	public ActiveMQTopic topic() {
		ActiveMQTopic topic = new ActiveMQTopic("topic");
		return topic;
	}

	@Bean
	public DefaultMessageListenerContainer listener1() {
		DefaultMessageListener listener = new DefaultMessageListener();
		listener.setDestination(this.topic());
		listener.setConnectionFactory(this.pooledConnectionFactoryUser1());
		listener.setPubSubDomain(true); // 广播
		listener.setDealer(new MessageDealer() {

			@Override
			public boolean onMessage(Serializable data) {
				if (Math.random() * 2 < 1) {
					logger.info("1>>>> ok.......... " + data);
					return true;
				} else {
					logger.warn("1>>>> back " + data);
					return false;
				}
			}
		});

		return listener;
	}

	@Bean
	public DefaultMessageListenerContainer listener2() {
		DefaultMessageListener listener = new DefaultMessageListener();
		listener.setDestination(this.topic());
		listener.setConnectionFactory(this.pooledConnectionFactoryUser2());
		listener.setPubSubDomain(true); // 广播
		listener.setDealer(new MessageDealer() {

			@Override
			public boolean onMessage(Serializable data) {
				if (Math.random() * 2 < 1) {
					logger.info("2>>>> ok.......... " + data);
					return true;
				} else {
					logger.warn("2>>>> back " + data);
					return false;
				}
			}
		});

		return listener;
	}
}
