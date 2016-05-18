package com.github.hualuomoli.demo.raml.app.json.web;

import java.util.Map;

import org.junit.Test;

import com.github.hualuomoli.commons.util.JsonUtils;
import com.github.hualuomoli.tool.raml.AbstractContextControllerTest;
import com.google.common.collect.Maps;

public class JsonControllerTest extends AbstractContextControllerTest {

	@Override
	protected String getControllerRequestUrl() {
		return "/app/json";
	}

	@Test
	public void testPostUserById() throws Exception {

		Map<String, Object> map = Maps.newHashMap();
		map.put("sex", "M");
		map.put("username", "hualuomoli");
		map.put("nickname", "花落莫离");
		map.put("age", 18);
		map.put("birthDay", "2016-09-15");
		// map.put("id", UUID.randomUUID().toString());

		String content = JsonUtils.toJson(map);

		mockMvc.perform(this.json("/user/{id}", 1)//
				.content(content))
				// .andDo(this.print())//
				.andDo(this.showResult()) //
				.andExpect(this.statusOk())//
				.andExpect(this.resultJson())//
				.andExpect(this.resultSuccess())//
				.andReturn();
	}

}
