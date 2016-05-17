package com.github.hualuomoli.tool.creator.util;

import java.util.Set;

import com.github.hualuomoli.tool.creator.entity.Entity;
import com.github.hualuomoli.tool.creator.entity.Mapper;

/**
 * Mapper工具
 * @author hualuomoli
 *
 */
public class MapperUtils extends CreatorUtils {

	/**
	 * 根据实体类的class获取实体类mapper信息
	 * @param entityCls 实体类的class
	 * @param ignores 忽略的属性列
	 * @param projectPackageName 项目包名,如com.github.hualuomoli
	 * @return 实体类mapper信息
	 */
	public static Mapper getMapper(Class<?> entityCls, Set<String> ignores, String projectPackageName) {

		Mapper mapper = new Mapper();

		// get entity
		Entity entity = EntityUtils.getEntity(entityCls, ignores, projectPackageName);

		//
		String name = entity.getSimpleName() + "Mapper";
		String packageName = getPackageName(entityCls) + ".mapper";
		String fullName = packageName + "." + name;
		String filename = name + ".java";
		String filepath = packageName.replaceAll("[.]", "/");
		String configFileName = name + ".xml";
		String configFilePath = getRelativePackageName(entityCls, projectPackageName).replaceAll("[.]", "/");

		// set
		mapper.setEntity(entity);
		mapper.setName(name);
		mapper.setPackageName(packageName);
		mapper.setFullName(fullName);
		mapper.setFilename(filename);
		mapper.setFilepath(filepath);
		mapper.setConfigFileName(configFileName);
		mapper.setConfigFilePath(configFilePath);

		return mapper;
	}

}
