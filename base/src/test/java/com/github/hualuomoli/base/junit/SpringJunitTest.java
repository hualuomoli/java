package com.github.hualuomoli.base.junit;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { //
		"classpath:spring/application-context-jdbc.xml", //
		"classpath:spring/application-context-jdbc-init.xml", //
		"classpath:spring/application-context-core.xml", //
		"classpath:spring/application-context-orm.xml" //
})
public class SpringJunitTest {

}
