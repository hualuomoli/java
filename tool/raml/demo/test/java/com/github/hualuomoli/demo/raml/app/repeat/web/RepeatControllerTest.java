package com.github.hualuomoli.demo.raml.app.repeat.web;

import static org.junit.Assert.fail;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.github.hualuomoli.tool.raml.AbstractContextControllerTest;

public class RepeatControllerTest extends AbstractContextControllerTest {

	@Override
	protected String getControllerRequestUrl() {
		return "/app/repeat";
	}

	@Test
	public void testPostUrlencoded() throws Exception {
		mockMvc.perform(this.urlEncoded("/urlencoded")//
				.param("age", "10")//
				.param("age", "11") //
				.param("salary", "100")//
				.param("salary", "200")//
				.param("salary", "100"))//
				// .andDo(this.print())//
				.andDo(this.showResult()) //
				.andExpect(this.statusOk())//
				.andExpect(this.resultJson())//
				.andExpect(this.resultSuccess())//
				.andReturn();
	}

	@Test
	public void testPostFile() throws Exception {
		mockMvc.perform(this.fileUpload("/file")//
				.file(new MockMultipartFile("photo", "pic.jpg", MediaType.IMAGE_JPEG_VALUE, FileUtils.readFileToByteArray(new File("E:/pic.jpg"))))//
				.file(new MockMultipartFile("photo", "jdbc.properties", MediaType.TEXT_PLAIN_VALUE,
						FileUtils.readFileToByteArray(new File("E:/jdbc.properties"))))//
				.param("username", "hualuomoli"))//
				// .andDo(this.print())//
				.andDo(this.showResult()) //
				.andExpect(this.statusOk())//
				.andExpect(this.resultJson())//
				.andExpect(this.resultSuccess())//
				.andReturn();
	}

}
