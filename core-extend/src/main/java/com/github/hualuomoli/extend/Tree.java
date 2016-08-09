package com.github.hualuomoli.extend;

import com.github.hualuomoli.extend.notice.Noticer;

public interface Tree extends Noticer {

	public static final String ROOT_CODE = "0";

	// 获取ID
	String getId();

	void setId(String id);

	// 数据编码
	String getDataCode();

	void setDataCode(String dataCode);

	// 数据名称
	String getDataName();

	void setDataName(String dataName);

	// 数据排序
	Integer getDataSort();

	void setDataSort(Integer dataSort);

	// 数据父编码
	String getParentCode();

	void setParentCode(String parentCode);

	// 全名称
	String getFullName();

	void setFullName(String fullName);

	// 排序字段名称
	String getDataSortName();

	// 名称分割符
	String getNameSeparator();

}
