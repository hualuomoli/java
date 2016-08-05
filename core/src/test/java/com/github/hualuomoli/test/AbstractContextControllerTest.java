package com.github.hualuomoli.test;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.hualuomoli.base.config.BaseConfig;
import com.github.hualuomoli.commons.util.JsonUtils;
import com.github.hualuomoli.mvc.config.MvcConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({ //
		@ContextConfiguration(name = "parent", classes = BaseConfig.class), //
		@ContextConfiguration(name = "child", classes = MvcConfig.class) //
})
public class AbstractContextControllerTest {

	// http://www.csdn123.com/html/mycsdn20140110/a7/a75383fcc7d869a7627583ada5e76e46.html
	// perform：执行一个RequestBuilder请求，会自动执行SpringMVC的流程并映射到相应的控制器执行处理；
	// andDo：添加ResultHandler结果处理器，比如调试时打印结果到控制台；
	// andExpect：添加ResultMatcher验证规则，验证控制器执行完成后结果是否正确；
	// andReturn：最后返回相应的MvcResult；然后进行自定义验证/进行下一步的异步处理；

	private static final String characterEncoding = "UTF-8";

	@Autowired
	private WebApplicationContext wac;
	protected MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	// server访问URL
	protected String getServerUrl() {
		return "";
	}

	// controller访问URL
	protected String getControllerRequestUrl() {
		return "";
	}

	// get请求
	public final MockHttpServletRequestBuilder get(String relativeUrl, Object... urlParams) {
		return MockMvcRequestBuilders.get(this.getUrl(relativeUrl), urlParams)//
				.characterEncoding(characterEncoding);
	}

	// delete
	public final MockHttpServletRequestBuilder delete(String relativeUrl, Object... urlParams) {
		return MockMvcRequestBuilders.delete(this.getUrl(relativeUrl), urlParams);
	}

	// post
	public final MockHttpServletRequestBuilder post(String relativeUrl, Object... urlParams) {
		return MockMvcRequestBuilders.post(this.getUrl(relativeUrl), urlParams)//
				.characterEncoding(characterEncoding);
	}

	// urlEncoded
	public final MockHttpServletRequestBuilder urlEncoded(String relativeUrl, Object... urlParams) {
		return this.post(relativeUrl, urlParams) //
				.contentType(MediaType.APPLICATION_FORM_URLENCODED);
	}

	// json
	public final MockHttpServletRequestBuilder json(String relativeUrl, Object... urlParams) {
		return this.post(relativeUrl, urlParams) //
				.contentType(MediaType.APPLICATION_JSON);
	}

	// fileUpload
	public final MockMultipartHttpServletRequestBuilder fileUpload(String relativeUrl, Object... urlParams) {
		return MockMvcRequestBuilders.fileUpload(this.getUrl(relativeUrl), urlParams);
	}

	// 获取URL
	private final String getUrl(String relativeUrl) {
		return this.getServerUrl() + this.getControllerRequestUrl() + relativeUrl;
	}

	// 是否成功
	public final ResultMatcher statusOk() {
		return MockMvcResultMatchers.status().isOk();
	}

	// 是否是JSON
	public final ResultMatcher resultJson() {
		return MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON);
	}

	// 是否业务执行成功
	public final ResultMatcher resultSuccess() {
		return new ResultMatcher() {

			@SuppressWarnings("unchecked")
			@Override
			public void match(MvcResult result) throws Exception {
				String content = result.getResponse().getContentAsString();
				Map<String, Object> map = JsonUtils.getInstance().parseObject(content, Map.class);
				Assert.assertEquals("success", "0", String.valueOf(map.get("code")));
			}
		};
	}

	// 打印响应信息
	public final ResultHandler print() {
		return MockMvcResultHandlers.print();
	}

	// 打印响应内容
	public final ResultHandler showResult() {
		return new ResultHandler() {

			@Override
			public void handle(MvcResult result) throws Exception {
				byte[] bytes = result.getResponse().getContentAsByteArray();
				System.out.println(new String(bytes, characterEncoding));
			}
		};
	}

	public final ResultHandler checkContent(final String content) {
		return new ResultHandler() {

			@Override
			public void handle(MvcResult result) throws Exception {
				byte[] bytes = result.getResponse().getContentAsByteArray();
				String data = new String(bytes, characterEncoding);
				Assert.assertEquals("success", content, data);
			}
		};

	}

}
