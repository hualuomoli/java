package com.github.hualuomoli.demo.raml.app.delete.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.github.hualuomoli.mvc.test.AbstractContextControllerTest;

public class DeleteControllerTest extends AbstractContextControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private DeleteController deleteController;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(deleteController).build();
	}

	@Test
	public void testDeleteNoparam() throws Exception {
		this.mockMvc.perform(delete("/app/delete/noparam"))
				// .andDo(print())
				.andExpect(isStatusOk())//
				.andExpect(isJson())//
				.andExpect(isSuccess())//
				.andDo(printContent()) //
				.andReturn();
	}

	@Test
	public void testDeleteUriparamById() throws Exception {
		this.mockMvc.perform(delete("/app/delete/uriparam/{id}", "123456"))
				// .andDo(print())
				.andExpect(isStatusOk())//
				.andExpect(isJson())//
				.andExpect(isSuccess())//
				.andDo(printContent()) //
				.andReturn();
	}

}
