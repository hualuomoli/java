package com.github.hualuomoli.commons.json;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class JsonMapperTest {

	private static final Logger logger = LoggerFactory.getLogger(JsonMapperTest.class);

	@Test
	public void testToJsonString() {
		Demo demo = new Demo(1234, "测试");
		String data = JsonMapper.toJsonString(demo);
		logger.debug("data:{}", data);
		List<Demo> list = Lists.newArrayList();
		list.add(new Demo(1234, "测试"));
		list.add(new Demo(5678, "哈哈"));
		list.add(new Demo(90, "test"));
		list.add(new Demo(null, null));
		list.add(new Demo(123456 + 789, null));
		list.add(new Demo(null, "ok"));
		String listData = JsonMapper.toJsonString(list);
		logger.debug("listData:{}", listData);

		Demo temp = new Demo();
		temp.setId(111);
		temp.setName("the name of demo");
		// string list
		List<String> stringList = Lists.newArrayList();
		stringList.add("1111");
		stringList.add("22222");
		stringList.add("呵呵");
		stringList.add("hahahahaha");
		temp.setList(stringList);
		// string map
		Map<String, String> stringMap = Maps.newHashMap();
		stringMap.put("ok", "oooooooooooooookkkkkkkkkkkkkkkk");
		stringMap.put("一边去", "咋说话呢");
		temp.setMap(stringMap);
		// demo list
		List<Demo> demoList = Lists.newArrayList();
		demoList.add(new Demo(1, "test1"));
		demoList.add(new Demo(2, "test2"));
		temp.setDemoList(demoList);
		// demo map
		Map<String, Demo> demoMap = Maps.newHashMap();
		demoMap.put("1", new Demo());
		demoMap.put("2", new Demo(3, "第三个了"));
		demoMap.put("3", new Demo(6, "the six"));
		temp.setDemoMap(demoMap);

		String tempData = JsonMapper.toJsonString(temp);
		logger.debug("tempData:{}", tempData);

	}

	@Test
	public void testFromJsonStringStringClassOfQ() {
		String data = "{\"id\":111,\"name\":\"the name of demo\",\"list\":[\"1111\",\"22222\",\"呵呵\",\"hahahahaha\"],\"map\":{\"一边去\":\"咋说话呢\",\"ok\":\"oooooooooooooookkkkkkkkkkkkkkkk\"},\"demoList\":[{\"id\":1,\"name\":\"test1\",\"list\":null,\"map\":null,\"demoList\":null,\"demoMap\":null},{\"id\":2,\"name\":\"test2\",\"list\":null,\"map\":null,\"demoList\":null,\"demoMap\":null}],\"demoMap\":{\"3\":{\"id\":6,\"name\":\"the six\",\"list\":null,\"map\":null,\"demoList\":null,\"demoMap\":null},\"2\":{\"id\":3,\"name\":\"第三个了\",\"list\":null,\"map\":null,\"demoList\":null,\"demoMap\":null},\"1\":{\"id\":null,\"name\":null,\"list\":null,\"map\":null,\"demoList\":null,\"demoMap\":null}}}";
		Demo demo = JsonMapper.fromJsonString(data, Demo.class);
		logger.debug(ToStringBuilder.reflectionToString(demo));
	}

	@Test
	public void testFromJsonStringStringJavaType() {
		// List<String>
		String stringList = "[\"1111\",\"22222\",\"呵呵\",\"hahahahaha\"]";
		List<String> list = JsonMapper.fromJsonString(stringList, JsonMapper.getJavaType(List.class, String.class));
		logger.debug("list:{}", list);

		// Map<String,String>
		String stringMap = "{\"一边去\":\"咋说话呢\",\"ok\":\"oooooooooooooookkkkkkkkkkkkkkkk\"}";
		Map<String, String> map = JsonMapper.fromJsonString(stringMap, JsonMapper.getJavaType(Map.class, String.class, String.class));
		logger.debug("map:{}", map);

		// List<Demo>
		String demoList = "[{\"id\":1,\"name\":\"test1\",\"list\":null,\"map\":null,\"demoList\":null,\"demoMap\":null},{\"id\":2,\"name\":\"test2\",\"list\":null,\"map\":null,\"demoList\":null,\"demoMap\":null}]";
		List<Demo> retList = JsonMapper.fromJsonString(demoList, JsonMapper.getJavaType(List.class, Demo.class));
		logger.debug("retList:{}", retList);

		// Map<String,Demo>
		String demoMap = "{\"3\":{\"id\":6,\"name\":\"the six\",\"list\":null,\"map\":null,\"demoList\":null,\"demoMap\":null},\"2\":{\"id\":3,\"name\":\"第三个了\",\"list\":null,\"map\":null,\"demoList\":null,\"demoMap\":null},\"1\":{\"id\":null,\"name\":null,\"list\":null,\"map\":null,\"demoList\":null,\"demoMap\":null}}";
		Map<String, Demo> retMap = JsonMapper.fromJsonString(demoMap, JsonMapper.getJavaType(Map.class, String.class, Demo.class));
		logger.debug("retMap:{}", retMap);
	}
}
