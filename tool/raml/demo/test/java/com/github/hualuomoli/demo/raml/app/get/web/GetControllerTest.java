package com.github.hualuomoli.demo.raml.app.get.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.github.hualuomoli.mvc.test.AbstractContextControllerTest;

public class GetControllerTest extends AbstractContextControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private GetController getController;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(getController).build();
	}

	@Test
	public void testGetNoparam() throws Exception {
		this.mockMvc.perform(get("/app/get/noparam")) //
				// .andDo(print())
				.andExpect(isStatusOk())//
				.andExpect(isJson())//
				.andExpect(isSuccess())//
				.andDo(printContent()) //
				.andReturn();
	}

	@Test
	public void testGetUriparamById() throws Exception {
		this.mockMvc.perform(get("/app/get/uriparam/{id}", "1")) //
				// .andDo(print())
				.andExpect(isStatusOk())//
				.andExpect(isJson())//
				.andExpect(isSuccess())//
				.andDo(printContent()) //
				.andReturn();
	}

	@Test
	public void testGetQueryparam() throws Exception {
		this.mockMvc
				.perform(get("/app/get/queryparam")//
						.characterEncoding("UTF-8")//
						.param("username", "hualuomoli")//
						.param("password", "123456"))
				// .andDo(print())
				.andExpect(isStatusOk())//
				.andExpect(isJson())//
				.andExpect(isSuccess())//
				.andDo(printContent()) //
				.andReturn();
	}

	@Test
	public void testGetUriqueryparamByPageNumberPageSize() throws Exception {
		this.mockMvc
				.perform(get("/app/get/uriqueryparam/{pageNumber}/{pageSize}", 1, 2)//
						.characterEncoding("UTF-8")//
						.param("startDate", "2015-06-07 12:24:30"))
				// .andDo(print())
				.andExpect(isStatusOk())//
				.andExpect(isJson())//
				.andExpect(isSuccess())//
				.andDo(printContent()) //
				.andReturn();
	}

}
