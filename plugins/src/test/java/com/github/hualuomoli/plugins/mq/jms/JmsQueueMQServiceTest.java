package com.github.hualuomoli.plugins.mq.jms;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/application-context-activemq.xml" })
public class JmsQueueMQServiceTest {

	@Autowired
	private JmsQueueMessageService jmsQueueMessageService;

	@Test
	public void testString() {
		jmsQueueMessageService.sendMessage("activemq.queue", "activemq text message.");
	}

	@Test
	public void testSerializable() {
		Demo demo = new Demo("1", "root");
		List<Demo> child = Lists.newArrayList();
		Map<String, Demo> map = Maps.newHashMap();
		child.add(new Demo("11", "child1"));
		child.add(new Demo("12", "child2"));
		child.add(new Demo("13", "child3"));
		child.add(new Demo("14", "child4"));
		child.add(new Demo("15", "child5"));
		map.put("21", new Demo("21", "map1"));
		map.put("22", new Demo("22", "map2"));
		map.put("23", new Demo("23", "map3"));
		map.put("24", new Demo("24", "map4"));
		demo.setChild(child);
		demo.setMap(map);

		jmsQueueMessageService.sendMessage("activemq.queue", demo);
	}

	@Test
	public void testMap() {
		Map<String, Object> map = Maps.newHashMap();
		map.put("21", "map21");
		map.put("22", "map22");
		map.put("23", "map23");
		map.put("24", "map24");

		jmsQueueMessageService.sendMessage("activemq.queue", map);
	}

}
