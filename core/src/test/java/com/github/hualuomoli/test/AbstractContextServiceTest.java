package com.github.hualuomoli.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import com.github.hualuomoli.base.config.BaseConfig;

@WebAppConfiguration
@ContextConfiguration(classes = { BaseConfig.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class AbstractContextServiceTest {

	@Autowired
	protected WebApplicationContext wac;

}
