package com.github.hualuomoli.tool.creator.dealer.mysql;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.github.hualuomoli.base.annotation.entity.EntityUnique;
import com.github.hualuomoli.commons.util.TemplateUtils;
import com.github.hualuomoli.tool.creator.dealer.FileDealer;
import com.github.hualuomoli.tool.creator.entity.CreatorColumn;
import com.github.hualuomoli.tool.creator.entity.CreatorTable;
import com.google.common.collect.Lists;
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
		map.put("uniques", this.getUnique(cls, creatorTable));

		this.createEntity(map);
		this.createMapper(map);
		this.createXml(map);
		this.createService(map);
		this.createServiceImpl(map);

	}

	// 唯一键
	private List<CreatorColumn> getUnique(Class<?> cls, CreatorTable creatorTable) {

		List<CreatorColumn> unionUniqueList = Lists.newArrayList();

		EntityUnique entityUnique = cls.getAnnotation(EntityUnique.class);
		if (entityUnique == null) {
			return unionUniqueList;
		}
		boolean union = entityUnique.union();
		String[] columnNames = entityUnique.columnNmaes();
		if (columnNames == null || columnNames.length == 0) {
			return unionUniqueList;
		}

		if (union) {
			// 联合唯一
			List<CreatorColumn> columns = creatorTable.getColumns();
			for (String columnName : columnNames) {
				boolean success = false;
				for (CreatorColumn creatorColumn : columns) {
					String name = creatorColumn.getField().getName();
					if (StringUtils.equals(name, columnName)) {
						unionUniqueList.add(creatorColumn);
						success = true;
						break;
					}
				}
				if (!success) {
					throw new RuntimeException("can not find field " + columnName);
				}
			}
		} else {
			// 唯一
			if (columnNames.length != 1) {
				throw new RuntimeException("唯一键只能有一个,如果是关联唯一,请设置 union = true");
			}
			String columnName = columnNames[0];
			List<CreatorColumn> columns = creatorTable.getColumns();
			for (CreatorColumn creatorColumn : columns) {
				String name = creatorColumn.getField().getName();
				if (StringUtils.equals(name, columnName)) {
					unionUniqueList.add(creatorColumn);
					break;
				}
			}
		}

		return unionUniqueList;

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
