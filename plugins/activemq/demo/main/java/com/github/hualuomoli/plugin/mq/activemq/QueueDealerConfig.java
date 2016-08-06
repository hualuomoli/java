package com.github.hualuomoli.plugin.mq.activemq;

import java.io.Serializable;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
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
public class QueueDealerConfig {

	private static final Logger logger = LoggerFactory.getLogger(QueueDealerConfig.class);

	public ActiveMQConnectionFactory connectionFactory() {
		Conn conn = YamlUtils.getInstance().getObject("receiver", Conn.class);
		if (logger.isInfoEnabled()) {
			logger.info("userName {}", conn.getUserName());
			logger.info("password {}", conn.getPassword());
			logger.info("brokerURL {}", conn.getBrokerURL());
		}
		return new ActiveMQConnectionFactory(conn.getUserName(), conn.getPassword(), conn.getBrokerURL());
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

	@Bean
	public DefaultMessageListenerContainer listener1() {
		DefaultMessageListener listener = new DefaultMessageListener();
		listener.setDestination(this.queue());
		listener.setConnectionFactory(this.pooledConnectionFactory());
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
		listener.setDestination(this.queue());
		listener.setConnectionFactory(this.pooledConnectionFactory());
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
