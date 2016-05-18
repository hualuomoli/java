package com.github.hualuomoli.demo.raml.app.multipart.web;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.github.hualuomoli.tool.raml.AbstractContextControllerTest;

public class MultipartControllerTest extends AbstractContextControllerTest {

	@Override
	protected String getControllerRequestUrl() {
		return "/app/multipart";
	}

	@Test
	public void testPostFileById() throws Exception {
		mockMvc.perform(this.fileUpload("/file/{id}", 1)//
				.file(new MockMultipartFile("photo", "pic.jpg", MediaType.IMAGE_JPEG_VALUE, FileUtils.readFileToByteArray(new File("E:/pic.jpg"))))//
				.characterEncoding("UTF-8")//
				.param("username", "hualuomoli"))
				// .andDo(this.print())//
				.andDo(this.showResult()) //
				.andExpect(this.statusOk())//
				.andExpect(this.resultJson())//
				.andExpect(this.resultSuccess())//
				.andReturn();
	}

}
