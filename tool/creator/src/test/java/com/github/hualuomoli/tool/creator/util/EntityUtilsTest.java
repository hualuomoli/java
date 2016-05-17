package com.github.hualuomoli.tool.creator.util;

import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.demo.base.entity.Demo;
import com.github.hualuomoli.tool.creator.entity.Entity;
import com.google.common.collect.Sets;

public class EntityUtilsTest {

	private static final Logger logger = LoggerFactory.getLogger(EntityUtils.class);
	private static final Set<String> ignores = Sets.newHashSet("version", "pagination");
	private static final String projectPackageName = "com.github.hualuomoli";

	@Test
	public void testGetEntity() {
		Entity entity = EntityUtils.getEntity(Demo.class, ignores, projectPackageName);
		logger.debug("entity {}", ToStringBuilder.reflectionToString(entity));

	}

}
