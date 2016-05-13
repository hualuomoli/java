package com.github.hualuomoli.tool.raml;

/**
 * 解析器
 * @author hualuomoli
 *
 */
public interface Parser {

	public static final String STATUS_OK = "200";

	public static final String MIME_TYPE_URLENCODED = "application/x-www-form-urlencoded"; // 表单提交
	public static final String MIME_TYPE_MULTIPART = "multipart/form-data"; // 文件上传
	public static final String MIME_TYPE_JSON = "application/json"; // payload提交或者返回

	/**
	 * 执行解析
	 * @param ramlResources Raml 文件的资源路径,文件必须放在资源目录下
	 */
	void execute(String[] ramlResources);

}
