package com.github.hualuomoli.plugins.mq.activemq;

import java.util.UUID;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.hualuomoli.plugins.mq.activemq.entity.Order;
import com.github.hualuomoli.plugins.mq.activemq.entity.User;
import com.github.hualuomoli.plugins.mq.exception.MQException;

public class ActiveMqMessageSenderTest {

	private static ConnectionFactory connectionFactory;

	@BeforeClass
	public static void beforeClass() {
		connectionFactory = new ActiveMQConnectionFactory();
	}

	// queue
	@Test
	public void testQueueSend() throws MQException {
		ActiveMqMessageSender sender = new ActiveMqMessageSender();
		sender.setDestinationName("order"); // 订单
		sender.setConnectionFactory(connectionFactory);
		sender.setPubSubDomain(false);
		sender.send(new Order("1234", "20160412094203657", "测试订单"));
	}

	// topic
	@Test
	public void testTopicSend() throws MQException {
		ActiveMqMessageSender sender = new ActiveMqMessageSender();
		sender.setDestinationName("user"); // 用户
		sender.setConnectionFactory(connectionFactory);
		sender.setPubSubDomain(true);
		sender.send(new User("1", "admin", "admin123", "花落莫离"));
	}

	// some queue message's receiver
	@SuppressWarnings("resource")
	@Test
	public void testQueueReceive() throws MQException, InterruptedException {
		// message listener must manage by spring
		// so we load it
		new ClassPathXmlApplicationContext(new String[] { "classpath:spring/application-context-activemq-listener-queue.xml" });

		int length = 200;
		int reload = 50;
		int reInit = 100;

		// send queue message
		for (int i = 1; i < length; i++) {
			ActiveMqMessageSender sender = new ActiveMqMessageSender();
			sender.setDestinationName("order"); // 订单
			sender.setConnectionFactory(connectionFactory);
			sender.setPubSubDomain(false);
			sender.send(new Order(i + "", "20160412094203657", "测试订单"));

			if (i == reload) {
				// reload spring
				// add third listener
				new ClassPathXmlApplicationContext(new String[] { "classpath:spring/application-context-activemq-listener-queue.xml",
						"classpath:spring/application-context-activemq-listener-queue-add.xml" });
			}
			if (i == reInit) {
				// reInit spring
				// remove third listener
				// but con't remove
				new ClassPathXmlApplicationContext(new String[] { "classpath:spring/application-context-activemq-listener-queue.xml" });
			}
			Thread.sleep(200);
		}

		// wait consume
		Thread.sleep(200);
	}

	// some queue message's receiver
	@SuppressWarnings("resource")
	@Test
	public void testTopicReceive() throws MQException, InterruptedException {
		// message listener must manage by spring
		// so we load it
		new ClassPathXmlApplicationContext(new String[] { "classpath:spring/application-context-activemq-listener-topic.xml" });

		int length = 200;
		int reload = 50;
		int reInit = 100;

		// send queue message
		for (int i = 1; i < length; i++) {
			ActiveMqMessageSender sender = new ActiveMqMessageSender();
			sender.setDestinationName("user"); // 用户
			sender.setConnectionFactory(connectionFactory);
			sender.setPubSubDomain(true);
			sender.send(new User(i + "", "admin", "admin123", "花落莫离"));

			if (i == reload) {
				// reload spring
				// add third listener
				new ClassPathXmlApplicationContext(new String[] { "classpath:spring/application-context-activemq-listener-topic.xml",
						"classpath:spring/application-context-activemq-listener-topic-add.xml" });
			}
			if (i == reInit) {
				// reInit spring
				// remove third listener
				// but con't remove
				new ClassPathXmlApplicationContext(new String[] { "classpath:spring/application-context-activemq-listener-topic.xml" });
			}
			Thread.sleep(200);
		}

		// wait consume
		Thread.sleep(200);
	}

	// queue security
	@SuppressWarnings("resource")
	@Test
	public void testSendUseActiveMqSecurity() throws MQException {
		// load listener
		new ClassPathXmlApplicationContext(new String[] { "classpath:spring/application-context-activemq-listener-security.xml" });

		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "failover:(tcp://localhost:61616)");
		ActiveMqMessageSender sender = new ActiveMqMessageSender();
		sender.setDestinationName("orderSecurity"); // 订单
		sender.setConnectionFactory(connectionFactory);
		sender.setPubSubDomain(false);
		sender.send(new Order("1", "20160412094203657", "测试订单"));
	}

	// topic
	@SuppressWarnings("resource")
	@Test
	public void testSendUseConnectPool() throws MQException, InterruptedException {
		// load listener
		new ClassPathXmlApplicationContext(new String[] { "classpath:spring/application-context-activemq-listener-pool.xml" });

		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("admin", "admin", "failover:(tcp://localhost:61616)");
		PooledConnectionFactory pool = new PooledConnectionFactory(activeMQConnectionFactory);
		pool.setMaxConnections(20);

		// send queue message
		for (int i = 1; i < 100; i++) {
			ActiveMqMessageSender sender = new ActiveMqMessageSender();
			sender.setDestinationName("orderPool"); // 用户
			sender.setConnectionFactory(pool);
			sender.setPubSubDomain(false);
			sender.send(new Order(i + "", "20160412094203657", "测试订单"));

			Thread.sleep(20);
		}

		Thread.sleep(1000);
	}

	// persistent
	@SuppressWarnings("resource")
	@Test
	public void testPersistentSend() throws MQException, InterruptedException {
		// 默认持久化到硬盘
		// <persistenceAdapter>
		// <kahaDB directory="${activemq.data}/kahadb"/>
		// </persistenceAdapter>
		String id = UUID.randomUUID().toString().replaceAll("-", "");

		ActiveMqMessageSender sender = new ActiveMqMessageSender();
		sender.setDestinationName("orderPersistent"); // 订单
		sender.setConnectionFactory(connectionFactory);
		sender.setPubSubDomain(false);
		sender.send(new Order(id, "20160412094203657", "测试订单"));

		System.out.println(id);
		// 关闭服务
		// 启动服务
		Thread.sleep(20 * 1000); // 休眠3分钟用于关闭和启动

		// load listener
		new ClassPathXmlApplicationContext(new String[] { "classpath:spring/application-context-activemq-listener-persistent.xml" });

		Thread.sleep(1000);

	}

	// queue security
	@SuppressWarnings("resource")
	@Test
	public void testSendColony() throws MQException, InterruptedException {
		// load listener
		new ClassPathXmlApplicationContext(new String[] { "classpath:spring/application-context-activemq-listener-colony.xml" });

		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("failover:(tcp://localhost:61616,tcp://localhost:61617)");
		PooledConnectionFactory pool = new PooledConnectionFactory(activeMQConnectionFactory);
		pool.setMaxConnections(20);

		for (int i = 0; i < 100; i++) {
			ActiveMqMessageSender sender = new ActiveMqMessageSender();
			sender.setDestinationName("orderColony"); // 订单
			sender.setConnectionFactory(pool);
			sender.setPubSubDomain(false);
			sender.send(new Order(i + "", "20160412094203657", "测试订单"));
		
			Thread.sleep(200);
		}

		Thread.sleep(10000);
	}

}
