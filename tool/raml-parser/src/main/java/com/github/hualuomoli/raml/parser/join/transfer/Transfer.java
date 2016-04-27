package com.github.hualuomoli.raml.parser.join.transfer;

import java.util.Map;

import org.raml.model.Action;
import org.raml.model.MimeType;
import org.raml.model.Resource;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.raml.parser.exception.ParseException;

/**
 * 转换器
 * @author hualuomoli
 *
 */
public interface Transfer {

	public static final String STATUS_SUCCESS = "200"; // 成功响应编码

	public static final String DEFAULT_ENTITY_NAME = "entity"; // 默认的实体类名称

	public static final String MIME_TYPE_URLENCODED = "application/x-www-form-urlencoded";
	public static final String MIME_TYPE_MULTIPART = "multipart/form-data";
	public static final String MIME_TYPE_JSON = "application/json";
	public static final String MIME_TYPE_XML = "application/xml";
	public static final String MIME_TYPE_TEXT = "text/plain";

	public static final String MIME_TYPE_ALL = "*/*";

	/**
	 * 是否支持
	 * @param action 事件类型
	 * @param requestMimeType 请求MimeType
	 * @param status 响应编码
	 * @param responseMimeType 响应MimeType
	 * @return 是否支持
	 */
	boolean support(Action action, MimeType requestMimeType, String status, MimeType responseMimeType) throws ParseException;

	/**
	 * 获取数据
	 * @param requestMimeType 请求MimeType
	 * @param status 响应编码
	 * @param responseMimeType 响应MimeType
	 * @param action 事件
	 * @param relativeUri 相对URI
	 * @param parentFullUri 父URI
	 * @param parentFullUriParameters 父URI参数
	 * @param resource 本资源
	 * @return 事件数据
	 */
	String getData(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri, String parentFullUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException;

}
