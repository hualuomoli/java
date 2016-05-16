package com.github.hualuomoli.demo.raml.app.urlEncoded.web;

import org.junit.Test;
import org.springframework.http.MediaType;

import com.github.hualuomoli.tool.raml.AbstractContextControllerTest;

public class UrlEncodedControllerTest extends AbstractContextControllerTest {

	@Override
	protected String getControllerRequestUrl() {
		return "/app/urlEncoded";
	}

	@Test
	public void testPostNoparam() throws Exception {
		mockMvc.perform(post("/noparam"))
				// .andDo(print())
				.andDo(printContent()) //
				.andExpect(isStatusOk())//
				.andExpect(isJson())//
				.andExpect(isSuccess())//
				.andReturn();
	}

	@Test
	public void testPostUriparamById() throws Exception {
		mockMvc.perform(post("/uriparam/{id}", 1))
				// .andDo(print())
				.andDo(printContent()) //
				.andExpect(isStatusOk())//
				.andExpect(isJson())//
				.andExpect(isSuccess())//
				.andReturn();
	}

	@Test
	public void testPostFormparam() throws Exception {
		mockMvc.perform(post("/formparam")//
				.characterEncoding("UTF-8")//
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)//
				.param("username", "hualuomoli")//
				.param("password", "123456"))
				// .andDo(print())
				.andDo(printContent()) //
				.andExpect(isStatusOk())//
				.andExpect(isJson())//
				.andExpect(isSuccess())//
				.andReturn();
	}

	@Test
	public void testPostUriformparamByPageNumberPageSize() throws Exception {
		mockMvc.perform(post("/uriformparam/{pageNumber}/{pageSize}", 1, 2)//
				.characterEncoding("UTF-8")//
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)//
				.param("username", "hualuomoli")//
				.param("password", "123456"))
				// .andDo(print())
				.andDo(printContent()) //
				.andExpect(isStatusOk())//
				.andExpect(isJson())//
				.andExpect(isSuccess())//
				.andReturn();
	}

}
