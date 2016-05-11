package com.github.hualuomoli.tool.creator;

import java.io.File;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.commons.template.TemplateUtils;
import com.github.hualuomoli.tool.creator.entity.Attribute;
import com.github.hualuomoli.tool.creator.entity.Mapper;

public class MapperUtilsTest {

	private static final Logger logger = LoggerFactory.getLogger(MapperUtils.class);

	@Test
	public void testGetMapper() throws Exception {
		Mapper mapper = MapperUtils.getMapper(Attribute.class, null, "com.github.hualuomoli");
		logger.debug("mapper {}", ToStringBuilder.reflectionToString(mapper));

		String outputpath = "E:/github/hualuomoli/java/web";

		// config
		String configFilepath = new File(outputpath + "/src/main/resources/mappers", mapper.getConfigFilePath()).getAbsolutePath();
		logger.debug("filepath {}", configFilepath);
		File output = new File(configFilepath, mapper.getConfigFileName());
		TemplateUtils.processByResource("tpl", "config.tpl", mapper, output);

		// mapper
		String filepath = new File(outputpath + "/src/main/java", mapper.getFilepath()).getAbsolutePath();
		logger.debug("mapperFilepath {}", filepath);
		output = new File(filepath, mapper.getFilename());
		TemplateUtils.processByResource("tpl", "mapper.tpl", mapper, output);

	}

}
