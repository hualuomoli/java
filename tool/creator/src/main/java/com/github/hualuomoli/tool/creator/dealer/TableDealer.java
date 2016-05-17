package com.github.hualuomoli.tool.creator.dealer;

import java.util.List;
import java.util.Set;

import com.github.hualuomoli.tool.creator.entity.Column;
import com.github.hualuomoli.tool.creator.entity.Table;

/**
 * 数据库列处理
 * @author hualuomoli
 *
 */
public interface TableDealer {

	/**
	 * 获取数据库的表
	 * @param entityCls class
	 * @param ignores 忽略属性
	 * @param projectPackageName 项目包名,如com.github.hualuomoli
	 * @return 类的Column
	 */
	Table getTable(Class<?> entityCls, Set<String> ignores, String projectPackageName);

	/**
	 * 获取数据库的Column
	 * @param entityCls class
	 * @param ignores 忽略属性
	 * @param projectPackageName 项目包名,如com.github.hualuomoli
	 * @return 类的Column
	 */
	List<Column> getColumns(Class<?> entityCls, Set<String> ignores, String projectPackageName);

}
