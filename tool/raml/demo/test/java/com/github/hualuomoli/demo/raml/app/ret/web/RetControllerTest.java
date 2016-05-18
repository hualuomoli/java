
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
		mockMvc.perform(this.get("/nodata"))//
				// .andDo(this.print())//
				.andDo(this.showResult()) //
				.andExpect(this.statusOk())//
				.andExpect(this.resultJson())//
				.andExpect(this.resultSuccess())//
				.andReturn();
	}

	@Test
	public void testGetObjectdata() throws Exception {
		mockMvc.perform(this.get("/objectdata"))//
				// .andDo(this.print())//
				.andDo(this.showResult()) //
				.andExpect(this.statusOk())//
				.andExpect(this.resultJson())//
				.andExpect(this.resultSuccess())//
				.andReturn();
	}

	@Test
	public void testGetListdata() throws Exception {
		mockMvc.perform(this.get("/listdata"))//
				// .andDo(this.print())//
				.andDo(this.showResult()) //
				.andExpect(this.statusOk())//
				.andExpect(this.resultJson())//
				.andExpect(this.resultSuccess())//
				.andReturn();
	}

	@Test
	public void testGetPagedata() throws Exception {
		mockMvc.perform(this.get("/pagedata"))//
				// .andDo(this.print())//
				.andDo(this.showResult()) //
				.andExpect(this.statusOk())//
				.andExpect(this.resultJson())//
				.andExpect(this.resultSuccess())//
				.andReturn();
	}

}
