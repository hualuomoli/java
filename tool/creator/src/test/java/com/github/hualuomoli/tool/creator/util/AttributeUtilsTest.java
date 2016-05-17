package com.github.hualuomoli.tool.creator.util;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.demo.base.entity.Demo;
import com.github.hualuomoli.tool.creator.entity.Attribute;
import com.google.common.collect.Sets;

public class AttributeUtilsTest {

	private static final Logger logger = LoggerFactory.getLogger(AttributeUtils.class);
	private static final Set<String> ignores = Sets.newHashSet("version", "pagination");
	private static final String projectPackageName = "com.github.hualuomoli";

	@Test
	public void testGetAttributes() {
		List<Attribute> attributes = AttributeUtils.getAttributes(Demo.class, ignores, projectPackageName);
		for (Attribute attribute : attributes) {
			logger.debug("attribute {}", ToStringBuilder.reflectionToString(attribute));
		}
	}

}
