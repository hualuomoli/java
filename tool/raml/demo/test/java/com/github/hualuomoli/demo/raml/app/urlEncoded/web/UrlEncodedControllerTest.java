package com.github.hualuomoli.demo.raml.app.urlEncoded.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.github.hualuomoli.tool.raml.AbstractContextControllerTest;

public class UrlEncodedControllerTest extends AbstractContextControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private UrlEncodedController urlEncodedController;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(urlEncodedController).build();
	}

	@Test
	public void testPostNoparam() throws Exception {
		this.mockMvc.perform(post("/app/urlEncoded/noparam"))
				// .andDo(print())
				.andExpect(isStatusOk())//
				.andExpect(isJson())//
				.andExpect(isSuccess())//
				.andDo(printContent()) //
				.andReturn();
	}

	@Test
	public void testPostUriparamById() throws Exception {
		this.mockMvc.perform(post("/app/urlEncoded/uriparam/{id}", 1))
				// .andDo(print())
				.andExpect(isStatusOk())//
				.andExpect(isJson())//
				.andExpect(isSuccess())//
				.andDo(printContent()) //
				.andReturn();
	}

	@Test
	public void testPostFormparam() throws Exception {
		this.mockMvc
				.perform(post("/app/urlEncoded/formparam")//
						.characterEncoding("UTF-8")//
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)//
						.param("username", "hualuomoli")//
						.param("password", "123456"))
				// .andDo(print())
				.andExpect(isStatusOk())//
				.andExpect(isJson())//
				.andExpect(isSuccess())//
				.andDo(printContent()) //
				.andReturn();
	}

	@Test
	public void testPostUriformparamByPageNumberPageSize() throws Exception {
		this.mockMvc
				.perform(post("/app/urlEncoded/uriformparam/{pageNumber}/{pageSize}", 1, 2)//
						.characterEncoding("UTF-8")//
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)//
						.param("username", "hualuomoli")//
						.param("password", "123456"))
				// .andDo(print())
				.andExpect(isStatusOk())//
				.andExpect(isJson())//
				.andExpect(isSuccess())//
				.andDo(printContent()) //
				.andReturn();
	}

}
