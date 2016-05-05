package com.github.hualuomoli.raml;

import org.raml.model.Resource;

import com.github.hualuomoli.raml.Parser.Config;

/**
 * 文件处理者
 * @author hualuomoli
 *
 */
public interface FileDealer {

	/**
	 * 设置配置信息
	 * @param config 配置信息
	 */
	void setConfig(Config config);

	/**
	 * 获取资源的相对文件路径
	 * @param resource 资源
	 * @return 资源的相对文件路径
	 */
	String getRelativeFilePath(Resource resource);

	/**
	 * 获取资源的文件名
	 * @param resource 资源
	 * @return 资源的文件名
	 */
	String getFileName(Resource resource);

}
