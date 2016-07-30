package com.github.hualuomoli.test;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import com.github.hualuomoli.base.config.BaseConfig;
import com.github.hualuomoli.demo.config.UserConfig;

@WebAppConfiguration
@ContextConfiguration(classes = { BaseConfig.class, UserConfig.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class AbstractContextServiceTest {

	@Autowired
	protected WebApplicationContext wac;

	@Before
	public void before() {
	}

}
