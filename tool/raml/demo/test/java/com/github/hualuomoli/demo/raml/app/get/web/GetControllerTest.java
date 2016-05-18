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
		mockMvc.perform(this.get("/noparam")) //
				// .andDo(this.print())//
				.andDo(this.showResult()) //
				.andExpect(this.statusOk())//
				.andExpect(this.resultJson())//
				.andExpect(this.resultSuccess())//
				.andReturn();
	}

	@Test
	public void testGetUriparamById() throws Exception {
		mockMvc.perform(this.get("/uriparam/{id}", "1")) //
				// .andDo(this.print())//
				.andDo(this.showResult()) //
				.andExpect(this.statusOk())//
				.andExpect(this.resultJson())//
				.andExpect(this.resultSuccess())//
				.andReturn();
	}

	@Test
	public void testGetQueryparam() throws Exception {
		mockMvc.perform(this.get("/queryparam")//
				.param("username", "hualuomoli")//
				.param("password", "123456"))
				// .andDo(this.print())//
				.andDo(this.showResult()) //
				.andExpect(this.statusOk())//
				.andExpect(this.resultJson())//
				.andExpect(this.resultSuccess())//
				.andReturn();
	}

	@Test
	public void testGetUriqueryparamByPageNumberPageSize() throws Exception {
		mockMvc.perform(this.get("/uriqueryparam/{pageNumber}/{pageSize}", 1, 2)//
				.param("startDate", "2015-06-07 12:24:30"))
				// .andDo(this.print())//
				.andDo(this.showResult()) //
				.andExpect(this.statusOk())//
				.andExpect(this.resultJson())//
				.andExpect(this.resultSuccess())//
				.andReturn();
	}

}
