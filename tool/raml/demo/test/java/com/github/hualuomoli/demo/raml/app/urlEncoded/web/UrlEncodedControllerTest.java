package com.github.hualuomoli.demo.raml.app.urlEncoded.web;

import org.junit.Test;

import com.github.hualuomoli.tool.raml.AbstractContextControllerTest;

public class UrlEncodedControllerTest extends AbstractContextControllerTest {

	@Override
	protected String getControllerRequestUrl() {
		return "/app/urlEncoded";
	}

	@Test
	public void testPostNoparam() throws Exception {
		mockMvc.perform(this.urlEncoded("/noparam"))
				// .andDo(this.print())//
				.andDo(this.showResult()) //
				.andExpect(this.statusOk())//
				.andExpect(this.resultJson())//
				.andExpect(this.resultSuccess())//
				.andReturn();
	}

	@Test
	public void testPostUriparamById() throws Exception {
		mockMvc.perform(this.urlEncoded("/uriparam/{id}", 1))
				// .andDo(this.print())//
				.andDo(this.showResult()) //
				.andExpect(this.statusOk())//
				.andExpect(this.resultJson())//
				.andExpect(this.resultSuccess())//
				.andReturn();
	}

	@Test
	public void testPostFormparam() throws Exception {
		mockMvc.perform(this.urlEncoded("/formparam")//
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
	public void testPostUriformparamByPageNumberPageSize() throws Exception {
		mockMvc.perform(this.urlEncoded("/uriformparam/{pageNumber}/{pageSize}", 1, 2)//
				.param("username", "hualuomoli")//
				.param("password", "123456"))
				// .andDo(this.print())//
				.andDo(this.showResult()) //
				.andExpect(this.statusOk())//
				.andExpect(this.resultJson())//
				.andExpect(this.resultSuccess())//
				.andReturn();
	}

}
