package com.github.hualuomoli.demo.raml.app.get.web;

import org.junit.Test;

import com.github.hualuomoli.tool.raml.AbstractContextControllerTest;

public class GetControllerTest extends AbstractContextControllerTest {

	@Override
	protected String getControllerRequestUrl() {
		return "/app/get";
	}

	@Test
	public void testGetNoparam() throws Exception {
		mockMvc.perform(get("/noparam")) //
				// .andDo(print())
				.andDo(printContent()) //
				.andExpect(isStatusOk())//
				.andExpect(isJson())//
				.andExpect(isSuccess())//
				.andReturn();
	}

	@Test
	public void testGetUriparamById() throws Exception {
		mockMvc.perform(get("/uriparam/{id}", "1")) //
				// .andDo(print())
				.andDo(printContent()) //
				.andExpect(isStatusOk())//
				.andExpect(isJson())//
				.andExpect(isSuccess())//
				.andReturn();
	}

	@Test
	public void testGetQueryparam() throws Exception {
		mockMvc.perform(get("/queryparam")//
				.characterEncoding("UTF-8")//
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
	public void testGetUriqueryparamByPageNumberPageSize() throws Exception {
		mockMvc.perform(get("/uriqueryparam/{pageNumber}/{pageSize}", 1, 2)//
				.characterEncoding("UTF-8")//
				.param("startDate", "2015-06-07 12:24:30"))
				// .andDo(print())
				.andDo(printContent()) //
				.andExpect(isStatusOk())//
				.andExpect(isJson())//
				.andExpect(isSuccess())//
				.andReturn();
	}

}
