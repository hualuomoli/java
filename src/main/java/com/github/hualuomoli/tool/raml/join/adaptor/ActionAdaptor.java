package com.github.hualuomoli.tool.raml.join.adaptor;

import java.util.List;

import com.github.hualuomoli.tool.raml.join.JoinParser.Adapter;

/**
 * Action适配器
 * @author hualuomoli
 *
 */
public interface ActionAdaptor {

	public static final String MIME_TYPE_URLENCODED = "application/x-www-form-urlencoded";
	public static final String MIME_TYPE_MULTIPART = "multipart/form-data";
	public static final String MIME_TYPE_JSON = "application/json";
	public static final String MIME_TYPE_XML = "application/xml";

	/**
	 * 是否支持
	 * @param adapter 适配者
	 * @return 是否支持
	 */
	boolean support(Adapter adapter);

	/**
	 * 获取数据
	 * @param adapter 适配者
	 * @return 数据
	 */
	List<String> getDatas(Adapter adapter);

}
