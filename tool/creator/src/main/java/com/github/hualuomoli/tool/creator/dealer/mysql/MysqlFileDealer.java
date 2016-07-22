package com.github.hualuomoli.tool.creator.dealer.mysql;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import com.github.hualuomoli.base.annotation.entity.EntityUnique;
import com.github.hualuomoli.commons.util.TemplateUtils;
import com.github.hualuomoli.tool.creator.dealer.FileDealer;
import com.github.hualuomoli.tool.creator.entity.CreatorColumn;
import com.github.hualuomoli.tool.creator.entity.CreatorTable;
import com.google.common.collect.Maps;

public class MysqlFileDealer implements FileDealer {

	private static final String tplPath = "tpl/creator/mysql";

	private Config config;

	private Class<?> entityCls;
	private String dbName;

	public void setConfig(Config config) {
		this.config = config;
	}

	@Override
	public void execute(Class<?> cls, CreatorTable creatorTable) {

		this.entityCls = cls;

		Map<String, Object> map = Maps.newHashMap();
		map.put("packageName", this.config.projectPackageName); // 包名
		map.put("javaName", entityCls.getSimpleName()); // 类名
		map.put("entityPackageName", entityCls.getPackage().getName());

		map.put("dbName", dbName);
		map.put("table", creatorTable);
		map.put("unique", this.getUnique(creatorTable));

		this.createEntity(map);
		this.createMapper(map);
		this.createXml(map);
		this.createService(map);
		this.createServiceImpl(map);

	}

	private CreatorColumn getUnique(CreatorTable creatorTable) {

		List<CreatorColumn> columns = creatorTable.getColumns();
		for (CreatorColumn creatorColumn : columns) {
			// 查找具有该注解的属性
			Field field = creatorColumn.getField();
			EntityUnique entityUnique = field.getAnnotation(EntityUnique.class);
			if (entityUnique != null) {
				// 找到
				return creatorColumn;
			}
		}

		return null;

	}

	private void createEntity(Map<String, Object> map) {

		// 创建目录
		File dir = new File(this.config.output, this.config.javaPath + this.config.projectPackageName.replaceAll("[.]", "/") + "/base/entity");
		if (!dir.exists()) {
			dir.mkdirs();
		}

		// 输出
		TemplateUtils.processByResource(tplPath, "entity.tpl", map, new File(dir.getAbsolutePath(), "Base" + entityCls.getSimpleName() + ".java"));
	}

	private void createMapper(Map<String, Object> map) {

		// 创建目录
		File dir = new File(this.config.output, this.config.javaPath + this.config.projectPackageName.replaceAll("[.]", "/") + "/base/mapper");
		if (!dir.exists()) {
			dir.mkdirs();
		}

		// 输出
		TemplateUtils.processByResource(tplPath, "mapper.tpl", map, new File(dir.getAbsolutePath(), "Base" + entityCls.getSimpleName() + "Mapper.java"));
	}

	private void createXml(Map<String, Object> map) {

		// 创建目录
		File dir = new File(this.config.output, this.config.resourcePath + "mappers/" + this.config.projectPackageName.replaceAll("[.]", "/") + "/base/mapper");
		if (!dir.exists()) {
			dir.mkdirs();
		}

		// 输出
		TemplateUtils.processByResource(tplPath, "xml.tpl", map, new File(dir.getAbsolutePath(), "Base" + entityCls.getSimpleName() + "Mapper.xml"));
	}

	private void createService(Map<String, Object> map) {

		// 创建目录
		File dir = new File(this.config.output, this.config.javaPath + this.config.projectPackageName.replaceAll("[.]", "/") + "/base/service");
		if (!dir.exists()) {
			dir.mkdirs();
		}

		// 输出
		TemplateUtils.processByResource(tplPath, "service.tpl", map, new File(dir.getAbsolutePath(), "Base" + entityCls.getSimpleName() + "Service.java"));
	}

	private void createServiceImpl(Map<String, Object> map) {

		// 创建目录
		File dir = new File(this.config.output, this.config.javaPath + this.config.projectPackageName.replaceAll("[.]", "/") + "/base/service/impl");
		if (!dir.exists()) {
			dir.mkdirs();
		}

		// 输出
		TemplateUtils.processByResource(tplPath, "serviceImpl.tpl", map,
				new File(dir.getAbsolutePath(), "Base" + entityCls.getSimpleName() + "ServiceImpl.java"));
	}

}
