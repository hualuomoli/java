package com.github.hualuomoli.tool.creator;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.tool.creator.entity.Attribute;
import com.github.hualuomoli.tool.creator.entity.Entity;

public class EntityUtilsTest {

	private static final Logger logger = LoggerFactory.getLogger(EntityUtils.class);

	@Test
	public void testGetEntity() {
		Entity entity = EntityUtils.getEntity(Attribute.class, null);
		logger.debug("entity {}", ToStringBuilder.reflectionToString(entity));

	}

}
