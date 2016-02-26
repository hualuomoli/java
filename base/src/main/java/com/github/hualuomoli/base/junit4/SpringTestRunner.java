package com.github.hualuomoli.base.junit4;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Spring测试工具
 * @author liubaoquan
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/application-context*.xml" })
public class SpringTestRunner {

	protected static final Logger logger = LoggerFactory.getLogger(SpringTestRunner.class);

}
