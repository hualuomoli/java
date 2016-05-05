package com.github.hualuomoli.raml.join;

import java.util.List;

import org.raml.model.Resource;

import com.github.hualuomoli.raml.FileDealer;

/**
 * 
 * Join文件处理者
 * @author hualuomoli
 *
 */
public interface JoinFileDealer extends FileDealer {

	/**
	 * 获取文件头
	 * @param resource 资源
	 * @return 文件头
	 */
	List<String> getFileHeader(Resource resource);

	/**
	 * 获取文件尾
	 * @param resource 资源
	 * @return 文件尾
	 */
	List<String> getFileFooter(Resource resource);

}
