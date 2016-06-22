package com.github.hualuomoli.tool.raml;

import org.raml.model.Raml;
import org.raml.model.Resource;

import com.github.hualuomoli.tool.raml.Parser.Config;

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
	 * 配置
	 * @param ramls
	 */
	void configure(Raml[] ramls);

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
