package com.github.hualuomoli.commons.prop;

import java.util.Properties;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesUtilsTest {

	private static final Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);

	@Test
	public void testLoad() {
		// load from this project
		Properties props = PropertiesUtils.load(new String[] { "log4j.properties" });
		logger.debug("log4j.rootLogger {}", props.get("log4j.rootLogger"));
	}

}
