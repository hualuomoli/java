package com.github.hualuomoli.tool.creator.util;

import java.io.File;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.commons.template.TemplateUtils;
import com.github.hualuomoli.demo.base.entity.Demo;
import com.github.hualuomoli.tool.creator.dealer.TableDealer;
import com.github.hualuomoli.tool.creator.dealer.h2.H2TableDealer;
import com.github.hualuomoli.tool.creator.dealer.mysql.MySqlTableDealer;
import com.github.hualuomoli.tool.creator.entity.Mapper;
import com.google.common.collect.Sets;

public class MapperUtilsTest {

	private static final Logger logger = LoggerFactory.getLogger(MapperUtils.class);

	private static final String outputpath = "E:/output/creator";
	private static final Set<String> ignores = Sets.newHashSet("version", "pagination");
	private static final String projectPackageName = "com.github.hualuomoli";
	private static final TableDealer tableDealer = new MySqlTableDealer();

	String filepath;
	File output;

	@Test
	public void testGetMapper() throws Exception {

		String className = tableDealer.getClass().getSimpleName();
		String dbName = className.substring(0, className.length() - TableDealer.class.getSimpleName().length());

		Mapper mapper = MapperUtils.getMapper(Demo.class, ignores, projectPackageName);
		logger.debug("mapper {}", ToStringBuilder.reflectionToString(mapper));

		// xml
		filepath = new File(outputpath + "/src/main/resources/orm/mappers", mapper.getConfigFilePath()).getAbsolutePath();
		logger.debug("xml filepath {}", filepath);
		output = new File(filepath, mapper.getConfigFileName());
		TemplateUtils.processByResource("tpl/" + dbName, "xml.tpl", mapper, output);

		// mapper
		filepath = new File(outputpath + "/src/main/java", mapper.getFilepath()).getAbsolutePath();
		logger.debug("mapper filepath {}", filepath);
		output = new File(filepath, mapper.getFilename());
		TemplateUtils.processByResource("tpl", "mapper.tpl", mapper, output);

	}

}
