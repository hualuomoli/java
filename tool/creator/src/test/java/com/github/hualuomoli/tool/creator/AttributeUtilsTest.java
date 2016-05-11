package com.github.hualuomoli.tool.creator;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.tool.creator.AttributeUtils;
import com.github.hualuomoli.tool.creator.entity.Attribute;

public class AttributeUtilsTest {

	private static final Logger logger = LoggerFactory.getLogger(AttributeUtils.class);

	@Test
	public void testGetAttributes() {
		List<Attribute> attributes = AttributeUtils.getAttributes(Attribute.class, null);
		for (Attribute attribute : attributes) {
			logger.debug("attribute {}", ToStringBuilder.reflectionToString(attribute));
		}
	}

}
