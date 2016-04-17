package com.github.hualuomoli.web.login.web;

import javax.servlet.http.Cookie;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({
		// spring
		"classpath*:/spring/application-context-*.xml",
		// mvc
		"classpath*:/mvc/spring-mvc*.xml" })
public class LoginControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockHttpSession session;

	private MockMvc mockMvc;

	private static String sessionID;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	@Ignore
	public void login() throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/a/index");

		mockMvc.perform(builder)//
				.andDo(MockMvcResultHandlers.print())//
				.andDo(new ResultHandler() {
					@Override
					public void handle(MvcResult result) throws Exception {
						System.out.println(result);
						Cookie cookie = result.getResponse().getCookie("sessionId");
					}
				});
	}

	@Test
	// @Ignore
	public void testDoAsynchronousLogin() {
		try {
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/login/check");
			// add param
			builder.param("userName", "admin");
			builder.param("password", "admin");

			mockMvc.perform(builder)//
					// status
					// .andExpect(MockMvcResultMatchers.status().isOk())
					// header
					// .andExpect(MockMvcResultMatchers.header().string("Content-Type", "text/plain;charset=UTF-8")) //
					// .andExpect(MockMvcResultMatchers.cookie().exists("Content-Type")) // cookie
					// .andExpect(MockMvcResultMatchers.content().encoding("UTF-8")) // content
					.andDo(MockMvcResultHandlers.print()) //
					.andDo(new ResultHandler() {

						@Override
						public void handle(MvcResult result) throws Exception {
							System.out.println(result);
							Cookie cookie = result.getResponse().getCookie("sessionId");
							System.out.println(cookie.getName());
							System.out.println(cookie.getValue());
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
