package com.github.hualuomoli.tool.creator;

import java.util.Set;

import com.github.hualuomoli.tool.creator.entity.Entity;
import com.github.hualuomoli.tool.creator.entity.Mapper;
import com.github.hualuomoli.tool.creator.entity.Service;

/**
 * Service工具
 * @author hualuomoli
 *
 */
public class ServiceUtils extends CreatorUtils {

	/**
	 * 根据实体类的class获取实体类service信息
	 * @param entityCls 实体类的class
	 * @param ignores 忽略的属性列
	 * @return 实体类service信息
	 */
	public static Service getService(Class<?> entityCls, Set<String> ignores, String skip) {

		if (skip == null) {
			skip = "";
		} else if (!skip.endsWith(".")) {
			skip = skip + ".";
		}

		Service service = new Service();

		// get mapper
		Mapper mapper = MapperUtils.getMapper(entityCls, ignores, skip);
		Entity entity = mapper.getEntity();
		//
		String name = entity.getSimpleName() + "Service";
		String packageName = getPackageName(entityCls) + ".service";
		String fullName = packageName + "." + name;
		String filename = name + ".java";
		String filepath = packageName.replaceAll("[.]", "/");

		// set
		service.setMapper(mapper);
		service.setEntity(entity);
		service.setName(name);
		service.setPackageName(packageName);
		service.setFullName(fullName);
		service.setFilename(filename);
		service.setFilepath(filepath);

		return service;
	}

}
