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

@WebAppConfiguration
@ContextHierarchy({ //
		@ContextConfiguration(name = "parent", classes = BaseConfig.class), //
		@ContextConfiguration(name = "child", classes = MvcConfig.class) //
})
@RunWith(SpringJUnit4ClassRunner.class)
public class AbstractContextControllerTest {

	// http://www.csdn123.com/html/mycsdn20140110/a7/a75383fcc7d869a7627583ada5e76e46.html
	// perform：执行一个RequestBuilder请求，会自动执行SpringMVC的流程并映射到相应的控制器执行处理；
	// andDo：添加ResultHandler结果处理器，比如调试时打印结果到控制台；
	// andExpect：添加ResultMatcher验证规则，验证控制器执行完成后结果是否正确；
	// andReturn：最后返回相应的MvcResult；然后进行自定义验证/进行下一步的异步处理；

	@Autowired
	private WebApplicationContext wac;
	protected MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	// get请求
	public MockHttpServletRequestBuilder get(String urlTemplate, Object... urlVariables) {
		return MockMvcRequestBuilders.get(urlTemplate, urlVariables);
	}

	// delete
	public MockHttpServletRequestBuilder delete(String urlTemplate, Object... urlVariables) {
		return MockMvcRequestBuilders.delete(urlTemplate, urlVariables);
	}

	// post
	public MockHttpServletRequestBuilder post(String urlTemplate, Object... urlVariables) {
		return MockMvcRequestBuilders.post(urlTemplate, urlVariables);
	}

	// fileUpload
	public MockMultipartHttpServletRequestBuilder fileUpload(String urlTemplate, Object... urlVariables) {
		return MockMvcRequestBuilders.fileUpload(urlTemplate, urlVariables);
	}

	// 是否成功
	protected ResultMatcher isStatusOk() {
		return MockMvcResultMatchers.status().isOk();
	}

	// 是否是JSON
	protected ResultMatcher isJson() {
		return MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON);
	}

	// 是否业务执行成功
	@SuppressWarnings("unchecked")
	protected ResultMatcher isSuccess() {
		return new ResultMatcher() {
			@Override
			public void match(MvcResult result) throws Exception {
				String content = result.getResponse().getContentAsString();
				Map<String, Object> map = JsonUtils.parseObject(content, Map.class);
				Assert.assertEquals("success", "0", String.valueOf(map.get("code")));
			}
		};
	}

	// 打印响应信息
	protected ResultHandler print() {
		return MockMvcResultHandlers.print();
	}

	// 打印响应内容
	protected ResultHandler printContent() {
		return new ResultHandler() {

			@Override
			public void handle(MvcResult result) throws Exception {
				String content = result.getResponse().getContentAsString();
				System.out.println(content);
			}
		};
	}

}
