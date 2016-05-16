package com.github.hualuomoli.demo.raml.app.json.web;

import java.util.Map;

import org.junit.Test;
import org.springframework.http.MediaType;

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

		mockMvc.perform(post("/user/{id}", 1)//
				.characterEncoding("UTF-8")//
				.contentType(MediaType.APPLICATION_JSON) //
				.content(content))
				// .andDo(print())
				.andDo(printContent()) //
				.andExpect(isStatusOk())//
				.andExpect(isJson())//
				.andExpect(isSuccess())//
				.andReturn();
	}

}
