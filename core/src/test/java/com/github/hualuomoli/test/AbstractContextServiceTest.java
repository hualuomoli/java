package com.github.hualuomoli.test;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import com.github.hualuomoli.LoginUserServiceAdaptor;
import com.github.hualuomoli.base.config.BaseConfig;
import com.github.hualuomoli.commons.util.ServletUtils;

@WebAppConfiguration
@ContextConfiguration(classes = { BaseConfig.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class AbstractContextServiceTest {

	@Autowired
	protected WebApplicationContext wac;
	protected MockHttpServletRequest req;

	@Before
	public void before() {
		if (req == null) {
			synchronized (wac) {
				if (req == null) {
					req = (MockHttpServletRequest) ServletUtils.getRequest();
				}
			}
		}
		req.addHeader("token", LoginUserServiceAdaptor.NULL_TOKEN);
	}

}
