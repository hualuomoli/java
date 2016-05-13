package com.github.hualuomoli.demo.raml.app.json.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.github.hualuomoli.mvc.test.AbstractContextControllerTest;

public class JsonControllerTest extends AbstractContextControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private JsonController jsonController;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(jsonController).build();
	}

	@Test
	public void testPostUserById() throws Exception {
		this.mockMvc
				.perform(post("/app/json/user/{id}", 1)//
						.characterEncoding("UTF-8")//
						.contentType(MediaType.APPLICATION_JSON) //
						.param("sex", "M")//
						.param("username", "hualuomoli")//
						.param("nickname", "花落莫离")//
						.param("age", "20")//
						.param("birthDay", "2016-05-13"))
				// .andDo(print())
				.andExpect(isStatusOk())//
				.andExpect(isJson())//
				.andExpect(isSuccess())//
				.andDo(printContent()) //
				.andReturn();
	}

}
