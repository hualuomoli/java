package com.github.hualuomoli.demo.raml.app.origin.web;

import org.junit.Test;

import com.github.hualuomoli.tool.raml.AbstractContextControllerTest;

public class OriginControllerTest extends AbstractContextControllerTest {

	@Override
	protected String getControllerRequestUrl() {
		return "/app/origin";
	}

	@Test
	public void testGetList() throws Exception {
		mockMvc.perform(this.get("/list")) //
				// .andDo(this.print())//
				.andDo(this.showResult()) //
				.andExpect(this.statusOk())//
				.andExpect(this.resultJson())//
				.andReturn();
	}

	@Test
	public void testGetObject() throws Exception {
		mockMvc.perform(this.get("/object")) //
				// .andDo(this.print())//
				.andDo(this.showResult()) //
				.andExpect(this.statusOk())//
				.andExpect(this.resultJson())//
				.andReturn();
	}

}
