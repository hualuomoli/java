package com.github.hualuomoli.tool.creator.dealer;

import java.util.List;

import org.junit.Test;

import com.github.hualuomoli.demo.entity.Demo;
import com.github.hualuomoli.tool.creator.dealer.mysql.MysqlCreatorDealer;
import com.google.common.collect.Lists;

public class CreatorDealerTest {

	private static final String projectPackageName = "com.github.hualuomoli.demo";
	private static final String outputPath = null; // null为当前项目

	@Test
	public void testExecute() {
		List<Class<?>> clses = Lists.newArrayList();
		clses.add(Demo.class);

		CreatorDealer dealer = new MysqlCreatorDealer();
		dealer.execute(clses, projectPackageName, outputPath);
	}

}
