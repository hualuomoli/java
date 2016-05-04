package com.github.hualuomoli.tool.creator.dealer;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.commons.template.TemplateUtils;
import com.github.hualuomoli.demo.base.entity.Demo;
import com.github.hualuomoli.tool.creator.entity.Table;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class MysqlTableDealerTest {

	private static final Logger logger = LoggerFactory.getLogger(MySqlTableDealer.class);

	private static final String outputpath = "E:/output/creator";
	private static final Set<String> ignores = Sets.newHashSet("version", "pagination");
	private static final String projectPackageName = "com.github.hualuomoli";

	String filepath;
	File output;

	@Test
	public void testGetService() throws Exception {

		TableDealer tableDealer = new MySqlTableDealer();
		Table table = tableDealer.getTable(Demo.class, ignores, projectPackageName);
		logger.debug("table {}", ToStringBuilder.reflectionToString(table));

		List<Table> tableList = Lists.newArrayList();
		tableList.add(table);

		Map<String, Object> map = Maps.newHashMap();
		map.put("tableList", tableList);

		output = new File(outputpath, table.getName() + ".sql");
		TemplateUtils.processByResource("tpl", "mysql.tpl", map, output);

	}

}
