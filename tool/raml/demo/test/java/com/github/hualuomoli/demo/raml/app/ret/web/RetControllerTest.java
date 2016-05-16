


package com.github.hualuomoli.demo.raml.app.ret.web;

import org.junit.Test;

import com.github.hualuomoli.tool.raml.AbstractContextControllerTest;

public class RetControllerTest extends AbstractContextControllerTest {

	@Override
	protected String getControllerRequestUrl() {
		return "/app/ret";
	}

	@Test
	public void testGetNodata() throws Exception {
		mockMvc.perform(get("/nodata")//
				.characterEncoding("UTF-8"))//
				// .andDo(print()) //
				.andDo(printContent())//
				.andExpect(isStatusOk()) //
				.andExpect(isJson())//
				.andExpect(isSuccess())//
				.andReturn();
	}

	@Test
	public void testGetObjectdata() throws Exception {
		mockMvc.perform(get("/objectdata")//
				.characterEncoding("UTF-8"))//
				// .andDo(print()) //
				.andDo(printContent())//
				.andExpect(isStatusOk()) //
				.andExpect(isJson())//
				.andExpect(isSuccess())//
				.andReturn();
	}

	@Test
	public void testGetListdata() throws Exception {
		mockMvc.perform(get("/listdata")//
				.characterEncoding("UTF-8"))//
				// .andDo(print()) //
				.andDo(printContent())//
				.andExpect(isStatusOk()) //
				.andExpect(isJson())//
				.andExpect(isSuccess())//
				.andReturn();
	}

	@Test
	public void testGetPagedata() throws Exception {
		mockMvc.perform(get("/pagedata")//
				.characterEncoding("UTF-8"))//
				// .andDo(print()) //
				.andDo(printContent())//
				.andExpect(isStatusOk()) //
				.andExpect(isJson())//
				.andExpect(isSuccess())//
				.andReturn();
	}

}
