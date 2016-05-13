package com.github.hualuomoli.mvc.test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.web.context.WebApplicationContext;

import com.github.hualuomoli.base.config.BaseConfig;
import com.github.hualuomoli.commons.util.JsonUtils;
import com.github.hualuomoli.mvc.config.MvcConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { MvcConfig.class, BaseConfig.class })
public class AbstractContextControllerTest {

	// http://www.csdn123.com/html/mycsdn20140110/a7/a75383fcc7d869a7627583ada5e76e46.html
	// perform：执行一个RequestBuilder请求，会自动执行SpringMVC的流程并映射到相应的控制器执行处理；
	// andDo：添加ResultHandler结果处理器，比如调试时打印结果到控制台；
	// andExpect：添加ResultMatcher验证规则，验证控制器执行完成后结果是否正确；
	// andReturn：最后返回相应的MvcResult；然后进行自定义验证/进行下一步的异步处理；

	@Autowired
	protected WebApplicationContext wac;

	protected ResultMatcher isStatusOk() {
		return status().isOk();
	}

	protected ResultMatcher isJson() {
		return content().contentType(MediaType.APPLICATION_JSON);
	}

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
