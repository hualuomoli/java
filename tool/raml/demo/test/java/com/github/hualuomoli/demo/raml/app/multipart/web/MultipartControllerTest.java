package com.github.hualuomoli.demo.raml.app.multipart.web;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

import com.github.hualuomoli.tool.raml.AbstractContextControllerTest;

public class MultipartControllerTest extends AbstractContextControllerTest {

	@Override
	protected String getControllerRequestUrl() {
		return "/app/multipart";
	}

	@Test
	public void testPostFileById() throws Exception {
		mockMvc.perform(fileUpload("/file/{id}", 1)//
				.file(new MockMultipartFile("photo", FileUtils.readFileToByteArray(new File("E:/pic.jpg"))))//
				.characterEncoding("UTF-8")//
				.param("username", "hualuomoli"))
				// .andDo(print())
				.andDo(printContent()) //
				.andExpect(isStatusOk())//
				.andExpect(isJson())//
				.andExpect(isSuccess())//
				.andReturn();
	}

}
