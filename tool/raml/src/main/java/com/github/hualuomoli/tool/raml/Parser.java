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

	public static final String HEADER_TOKEN = "token"; // 用户令牌,使用该header表示该请求需要用户登录
	public static final String HEADER_ROLE = "role"; // 用户角色,限制功能的使用人
	public static final String HEADER_PERMISSION = "permission"; // 用户权限,限制功能的使用人

	/**
	 * 执行解析
	 * @param ramlResources Raml 文件的资源路径,文件必须放在资源目录下
	 */
	void execute(String[] ramlResources);

}
