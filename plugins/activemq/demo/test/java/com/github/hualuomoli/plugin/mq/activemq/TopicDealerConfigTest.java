package com.github.hualuomoli.plugin.mq.activemq;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import com.github.hualuomoli.plugin.mq.BaseConfig;
import com.github.hualuomoli.plugin.mq.MessageSender;

@WebAppConfiguration
@ContextConfiguration(classes = { BaseConfig.class, TopicDealerConfig.class, TopicSenderConfig.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class TopicDealerConfigTest {

	@Autowired
	protected WebApplicationContext wac;
	private MessageSender messageSender;

	@Before
	public void beforeClass() {
		messageSender = wac.getBean(MessageSender.class);
	}

	@Test
	public void test() throws InterruptedException {
		if (messageSender.success()) {
			int count = (int) (Math.random() * 5) + 10;
			for (int i = 0; i < count; i++) {
				messageSender.send("topic", "this is topic message from server.这是一段来自server的内容 " + i);
			}
			Thread.sleep(10 * 1000);
		}
	}

}
