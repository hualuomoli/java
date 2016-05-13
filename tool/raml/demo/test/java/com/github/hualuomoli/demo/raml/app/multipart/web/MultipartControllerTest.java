package com.github.hualuomoli.demo.raml.app.multipart.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.github.hualuomoli.mvc.test.AbstractContextControllerTest;

public class MultipartControllerTest extends AbstractContextControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private MultipartController multipartController;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(multipartController).build();
	}

	@Test
	public void testPostFileById() throws Exception {
		this.mockMvc
				.perform(fileUpload("/app/multipart/file/{id}", 1)//
						.file(new MockMultipartFile("photo", FileUtils.readFileToByteArray(new File("E:/pic.jpg"))))//
						.characterEncoding("UTF-8")//
						// .contentType(MediaType.MULTIPART_FORM_DATA) //
						.param("username", "hualuomoli"))
				// .andDo(print())
				.andExpect(isStatusOk())//
				.andExpect(isJson())//
				.andExpect(isSuccess())//
				.andDo(printContent()) //
				.andReturn();
	}

}
