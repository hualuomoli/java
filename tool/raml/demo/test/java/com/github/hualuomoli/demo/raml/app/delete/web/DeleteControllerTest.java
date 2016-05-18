package com.github.hualuomoli.demo.raml.app.delete.web;

import org.junit.Test;

import com.github.hualuomoli.tool.raml.AbstractContextControllerTest;

public class DeleteControllerTest extends AbstractContextControllerTest {

	@Override
	protected String getControllerRequestUrl() {
		return "/app/delete";
	}

	@Test
	public void testDeleteNoparam() throws Exception {
		mockMvc.perform(this.delete("/noparam"))
				// .andDo(this.print())//
				.andDo(this.showResult()) //
				.andExpect(this.statusOk())//
				.andExpect(this.resultJson())//
				.andExpect(this.resultSuccess())//
				.andReturn();
	}

	@Test
	public void testDeleteUriparamById() throws Exception {
		mockMvc.perform(this.delete("/uriparam/{id}", "123456"))
				// .andDo(this.print())//
				.andDo(this.showResult()) //
				.andExpect(this.statusOk())//
				.andExpect(this.resultJson())//
				.andExpect(this.resultSuccess())//
				.andReturn();
	}

}
