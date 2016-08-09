package com.github.hualuomoli.tool.creator;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.hualuomoli.commons.util.ProjectUtils;
import com.github.hualuomoli.system.entity.Menu;
import com.github.hualuomoli.system.entity.Role;
import com.github.hualuomoli.system.entity.RoleMenu;
import com.github.hualuomoli.system.entity.User;
import com.github.hualuomoli.system.entity.UserRole;
import com.github.hualuomoli.tool.creator.dealer.DbDealer.DbConfig;
import com.github.hualuomoli.tool.creator.dealer.FileDealer.Config;
import com.github.hualuomoli.tool.creator.dealer.mysql.MysqlDbDealer;
import com.github.hualuomoli.tool.creator.dealer.mysql.MysqlFileDealer;
import com.github.hualuomoli.tool.creator.util.CreatorUtils;
import com.google.common.collect.Lists;

public class CreatorTest {

	private Creator creator;

	@Before
	public void before() {
		String output = ProjectUtils.getProjectPath();

		CreatorAdaptor creatorAdaptor = new CreatorAdaptor();
		Config creatorConfig = new Config();
		creatorConfig.output = output;
		creatorConfig.javaPath = "src/main/java/";
		creatorConfig.resourcePath = "src/main/resources/";
		creatorConfig.projectPackageName = "com.github.hualuomoli.system";
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

		CreatorUtils.setPrefix("sys");

	}

	@Test
	public void testExecute() {
		List<Class<?>> clsList = Lists.newArrayList();

		// menu
		clsList.add(Menu.class);
		// role
		clsList.add(Role.class);
		// // user
		// clsList.add(User.class);
		// user-role
		clsList.add(UserRole.class);
		// role-menu
		clsList.add(RoleMenu.class);

		System.out.println(clsList.size());

		// create
		creator.execute(clsList.toArray(new Class<?>[] {}));

	}

}
