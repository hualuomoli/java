package com.github.hualuomoli.tool.creator.dealer;

import java.util.List;

import com.github.hualuomoli.commons.util.ProjectUtils;
import com.github.hualuomoli.tool.creator.entity.CreatorTable;

/**
 * DB处理器
 * @author hualuomoli
 *
 */
public interface DbDealer {

	void setDbConfig(DbConfig dbConfig);

	// 执行
	void execute(List<Class<?>> clsList, List<CreatorTable> tableList);

	// 配置
	public static class DbConfig {

		public String output = ProjectUtils.getProjectPath();
		public String dbPath = "db/";

	}

}
