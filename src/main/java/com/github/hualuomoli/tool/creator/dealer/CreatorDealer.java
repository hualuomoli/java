package com.github.hualuomoli.tool.creator.dealer;

import java.util.List;

public interface CreatorDealer {

	/**
	 * 执行
	 * @param entityClses 实体类
	 * @param ignoreAttributes 忽略属性
	 * @param projectPackageName 项目目录 com.github.hualuomoli.demo
	 * @param outputPath 输出目录(maven项目目录)
	 */
	void execute(List<Class<?>> entityClses, String projectPackageName, String outputPath);

}
