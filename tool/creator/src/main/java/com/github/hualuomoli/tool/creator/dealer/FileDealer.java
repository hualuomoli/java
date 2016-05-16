package com.github.hualuomoli.tool.creator.dealer;

import com.github.hualuomoli.commons.util.ProjectUtils;
import com.github.hualuomoli.tool.creator.entity.CreatorTable;

/**
 * 文件处理器
 * @author hualuomoli
 *
 */
public interface FileDealer {

	void setConfig(Config config);

	// 执行
	void execute(Class<?> cls, CreatorTable creatorTable);

	// 配置
	public static class Config {

		public String output = ProjectUtils.getProjectPath();
		public String javaPath = "src/main/java/";
		public String resourcePath = "src/main/resources/";

		public String projectPackageName;

	}

}
