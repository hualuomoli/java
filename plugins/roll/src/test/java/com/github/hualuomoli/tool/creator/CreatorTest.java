package com.github.hualuomoli.tool.creator;

import org.junit.Before;
import org.junit.Test;

import com.github.hualuomoli.commons.util.ProjectUtils;
import com.github.hualuomoli.plugin.roll.entity.PollData;
import com.github.hualuomoli.plugin.roll.entity.PollErrorHis;
import com.github.hualuomoli.plugin.roll.entity.PollFrequency;
import com.github.hualuomoli.plugin.roll.entity.PollSuccessHis;
import com.github.hualuomoli.tool.creator.dealer.DbDealer.DbConfig;
import com.github.hualuomoli.tool.creator.dealer.FileDealer.Config;
import com.github.hualuomoli.tool.creator.dealer.mysql.MysqlDbDealer;
import com.github.hualuomoli.tool.creator.dealer.mysql.MysqlFileDealer;
import com.github.hualuomoli.tool.creator.util.CreatorUtils;

public class CreatorTest {

	private Creator creator;

	@Before
	public void before() {
		String output = ProjectUtils.getProjectPath();

		// output = "E:/github/hualuomoli/java-servlet3-multi/core";

		CreatorAdaptor creatorAdaptor = new CreatorAdaptor();
		Config creatorConfig = new Config();
		creatorConfig.output = output;
		creatorConfig.javaPath = "src/main/java/";
		creatorConfig.resourcePath = "src/main/resources/";
		creatorConfig.projectPackageName = "com.github.hualuomoli.plugin.roll";
		MysqlFileDealer fileDealer = new MysqlFileDealer();
		fileDealer.setConfig(creatorConfig);

		MysqlDbDealer dbDealer = new MysqlDbDealer();
		DbConfig dbConfig = new DbConfig();
		dbConfig.dbPath = "db/";
		dbConfig.output = output;
		dbDealer.setDbConfig(dbConfig);

		creatorAdaptor.setFileDealer(fileDealer);
		creatorAdaptor.setDbDealer(dbDealer);
		creator = creatorAdaptor;

		CreatorUtils.setPrefix("pr");

	}

	@Test
	public void testExecute() {
		creator.execute(PollData.class, PollFrequency.class, PollSuccessHis.class, PollErrorHis.class);
	}

}
