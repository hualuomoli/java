package com.github.hualuomoli.demo.raml.app.header.web;

import org.junit.Test;

import com.github.hualuomoli.tool.raml.AbstractContextControllerTest;

public class HeaderControllerTest extends AbstractContextControllerTest {

	@Override
	protected String getControllerRequestUrl() {
		return "/app/header";
	}

	@Test
	public void testGetGet() throws Exception {
		mockMvc.perform(this.get("/get")) //
				// .andDo(this.print())//
				.andDo(this.showResult()) //
				.andExpect(this.statusOk())//
				.andExpect(this.resultJson())//
				.andExpect(this.resultSuccess())//
				.andReturn();
	}

}
