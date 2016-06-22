package com.github.hualuomoli.plugin.creator.dealer;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.github.hualuomoli.commons.util.ProjectUtils;

/**
 * 数据库列处理
 * @author hualuomoli
 *
 */
public abstract class AbstractCreatorDealer implements CreatorDealer {

	@Override
	public void execute(List<Class<?>> entityClses, String projectPackageName, String outputPath) {
		if (StringUtils.isBlank(outputPath)) {
			outputPath = ProjectUtils.getLocation();
		}
		if (entityClses == null || entityClses.size() == 0) {
			return;
		}
		// create file
		for (Class<?> entityCls : entityClses) {
			this.config(entityCls, projectPackageName, outputPath);
			this.createEntity();
			this.createXml();
			this.createDao();
			this.createService();
			this.createServiceImpl();
		}
		// create DB
		this.createDB();
	}

	/**
	 * 配置
	 * @param entityCls 实体类
	 * @param projectPackageName 项目目录 com.github.hualuomoli.demo
	 * @param outputPath 输出目录(maven项目目录)
	 */
	protected abstract void config(Class<?> entityCls, String projectPackageName, String outputPath);

	/**
	 * 配置Entity
	 */
	protected abstract void createEntity();

	/**
	 * 配置XML
	 */
	protected abstract void createXml();

	/**
	 * 配置DAO
	 */
	protected abstract void createDao();

	/**
	 * 配置Service接口
	 */
	protected abstract void createService();

	/**
	 * 配置Service实现
	 */
	protected abstract void createServiceImpl();

	/**
	 * 创建数据库
	 */
	protected abstract void createDB();

}
