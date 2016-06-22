package com.github.hualuomoli.tool.creator.util;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.demo.entity.Demo;
import com.github.hualuomoli.tool.creator._Attribute;

public class AttributeUtilsTest {

	private static final Logger logger = LoggerFactory.getLogger(AttributeUtils.class);

	private static final String projectPackageName = "com.github.hualuomoli";

	@Test
	public void testGetAttributes() {
		List<_Attribute> attributes = AttributeUtils.getAttributes(Demo.class, projectPackageName);
		for (_Attribute _Attribute : attributes) {
			logger.debug(ToStringBuilder.reflectionToString(_Attribute));
		}
	}

}
