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
		mockMvc.perform(delete("/noparam"))
				// .andDo(print())
				.andDo(printContent()) //
				.andExpect(isStatusOk())//
				.andExpect(isJson())//
				.andExpect(isSuccess())//
				.andReturn();
	}

	@Test
	public void testDeleteUriparamById() throws Exception {
		mockMvc.perform(delete("/uriparam/{id}", "123456"))
				// .andDo(print())
				.andDo(printContent()) //
				.andExpect(isStatusOk())//
				.andExpect(isJson())//
				.andExpect(isSuccess())//
				.andReturn();
	}

}
