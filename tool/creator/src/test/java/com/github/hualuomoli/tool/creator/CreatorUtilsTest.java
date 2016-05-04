package com.github.hualuomoli.tool.creator;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.commons.template.TemplateUtils;
import com.github.hualuomoli.demo.base.entity.Demo;
import com.github.hualuomoli.tool.creator.dealer.TableDealer;
import com.github.hualuomoli.tool.creator.dealer.mysql.MySqlTableDealer;
import com.github.hualuomoli.tool.creator.entity.Mapper;
import com.github.hualuomoli.tool.creator.entity.Service;
import com.github.hualuomoli.tool.creator.entity.Table;
import com.github.hualuomoli.tool.creator.util.ServiceUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class CreatorUtilsTest {

	private static final Logger logger = LoggerFactory.getLogger(CreatorUtilsTest.class);

	private static final String outputpath = "E:/github/hualuomoli/java/base";
	// private static final String outputpath = "E:/output/creator";
	private static final Set<String> ignores = Sets.newHashSet(/*"version",*/ "pagination");
	private static final String projectPackageName = "com.github.hualuomoli";

	String filepath;
	File output;

	@Test
	public void test() {

		Service service = ServiceUtils.getService(Demo.class, ignores, projectPackageName);
		Mapper mapper = service.getMapper();

		// xml
		filepath = new File(outputpath + "/src/main/resources/orm/mappers", mapper.getConfigFilePath()).getAbsolutePath();
		logger.debug("xml filepath {}", filepath);
		output = new File(filepath, mapper.getConfigFileName());
		TemplateUtils.processByResource("tpl", "xml.tpl", mapper, output);

		// mapper
		filepath = new File(outputpath + "/src/main/java", mapper.getFilepath()).getAbsolutePath();
		logger.debug("mapper filepath {}", filepath);
		output = new File(filepath, mapper.getFilename());
		TemplateUtils.processByResource("tpl", "mapper.tpl", mapper, output);

		// service
		filepath = new File(outputpath + "/src/main/java", service.getFilepath()).getAbsolutePath();
		logger.debug("service filepath {}", filepath);
		output = new File(filepath, service.getFilename());
		TemplateUtils.processByResource("tpl", "service.tpl", service, output);

		// service impl
		output = new File(filepath + "/orm", service.getName() + "Impl.java");
		TemplateUtils.processByResource("tpl", "serviceImpl.tpl", service, output);

		// database
		TableDealer tableDealer = new MySqlTableDealer();
		Table table = tableDealer.getTable(Demo.class, ignores, projectPackageName);

		List<Table> tableList = Lists.newArrayList();
		tableList.add(table);

		Map<String, Object> map = Maps.newHashMap();
		map.put("tableList", tableList);

		filepath = new File(outputpath, "/src/test/resources/orm/database").getAbsolutePath();
		logger.debug("database filepath {}", filepath);
		output = new File(filepath, "init.sql");
		TemplateUtils.processByResource("tpl/databases", "mysql.tpl", map, output);

	}

}
