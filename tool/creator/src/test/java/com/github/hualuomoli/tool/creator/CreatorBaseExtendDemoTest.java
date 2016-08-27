package com.github.hualuomoli.tool.creator;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import com.github.hualuomoli.commons.util.ProjectUtils;
import com.github.hualuomoli.extend.entity.Product;
import com.github.hualuomoli.tool.creator.dealer.DbDealer.DbConfig;
import com.github.hualuomoli.tool.creator.dealer.FileDealer.Config;
import com.github.hualuomoli.tool.creator.dealer.mysql.MysqlDbDealer;
import com.github.hualuomoli.tool.creator.dealer.mysql.MysqlFileDealer;
import com.github.hualuomoli.tool.creator.util.CreatorUtils;
import com.google.common.collect.Lists;

/**
 * 生成base的文件
 * @author hualuomoli
 *
 */
public class CreatorBaseExtendDemoTest {

	private Creator creator;

	@Before
	public void before() {
		String output = ProjectUtils.getProjectPath();

		// to core
		output = StringUtils.replace(output, "/tool/creator", "/core-extend");

		CreatorAdaptor creatorAdaptor = new CreatorAdaptor();
		Config creatorConfig = new Config();
		creatorConfig.output = output;
		creatorConfig.javaPath = "demo/main/java/";
		creatorConfig.resourcePath = "demo/main/resources/";
		creatorConfig.projectPackageName = "com.github.hualuomoli.extend";
		MysqlFileDealer fileDealer = new MysqlFileDealer();
		fileDealer.setConfig(creatorConfig);

		MysqlDbDealer dbDealer = new MysqlDbDealer();
		DbConfig dbConfig = new DbConfig();
		dbConfig.dbPath = "db/";
		dbConfig.dbName = "database-demo.sql";
		dbConfig.output = output;
		dbDealer.setDbConfig(dbConfig);

		creatorAdaptor.setFileDealer(fileDealer);
		creatorAdaptor.setDbDealer(dbDealer);
		creator = creatorAdaptor;

		CreatorUtils.setPrefix("test");

	}

	@Test
	public void testExecute() {
		List<Class<?>> clsList = Lists.newArrayList();

		// product
		clsList.add(Product.class);

		System.out.println(clsList.size());

		// create
		creator.execute(clsList.toArray(new Class<?>[] {}));
	}

}
