package com.github.hualuomoli.raml.parser.join.java.transfer;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.raml.model.Action;
import org.raml.model.MimeType;
import org.raml.model.Resource;
import org.raml.model.parameter.AbstractParam;
import org.raml.model.parameter.FormParameter;
import org.raml.model.parameter.QueryParameter;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.raml.parser.exception.ParseException;
import com.github.hualuomoli.raml.parser.join.Join;
import com.github.hualuomoli.raml.parser.join.transfer.Transfer;
import com.github.hualuomoli.raml.parser.util.RamlUtils;
import com.google.common.collect.Lists;

/**
 * JAVA字符串连接转换器
 * @author hualuomoli
 *
 */
public abstract class JavaTransfer implements Join, Transfer {

	/**
	* 获取事件数据
	* @param requestMimeType 请求MimeType
	* @param status 响应编码
	* @param responseMimeType 响应MimeType
	* @param action 事件
	* @param relativeUri 相对URI
	* @param parentFullUriParameters 父URI参数
	* @param resource 本资源
	* @return 事件数据
	*/
	public String getData(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException {
		StringBuilder buffer = new StringBuilder();

		buffer.append(LINE);
		// method header
		buffer.append(this.getMethodHeader(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUriParameters, resource));

		// method example
		buffer.append(this.getMethodExample(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUriParameters, resource));

		// method footer
		buffer.append(this.getMethodFooter());

		// entity
		buffer.append(this.getDataEntity(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUriParameters, resource));

		return buffer.toString();
	}

	/**
	 * 获取方法的头部
	 * @param requestMimeType 请求MimeType
	 * @param status 响应编码
	 * @param responseMimeType 响应MimeType
	 * @param action 事件
	 * @param relativeUri 相对URI
	 * @param parentFullUriParameters 父URI参数
	 * @param resource 本资源
	 * @return 方法头部
	 */
	public String getMethodHeader(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException {
		StringBuilder buffer = new StringBuilder();

		// 实体类
		String entityName = this.getEntityName(action, relativeUri);
		String entityUpperName = "";
		String entityLowerName = "";
		if (StringUtils.isNotEmpty(entityName)) {
			entityUpperName = entityName.substring(0, 1).toUpperCase() + entityName.substring(1);
			entityLowerName = entityName.substring(0, 1).toLowerCase() + entityName.substring(1);
		}

		/**
		 * 
		 * @param entity
		 * @param request
		 * @param response
		 * @param model
		 */
		buffer.append(LINE).append(TAB).append("/**");
		buffer.append(LINE).append(TAB).append(" * ").append(this.getClass().getSimpleName());
		buffer.append(LINE).append(TAB).append(" * ").append(StringUtils.isEmpty(action.getDescription()) ? "" : action.getDescription());
		// entity
		if (StringUtils.isNotEmpty(entityName)) {
			buffer.append(LINE).append(TAB).append(" * ").append("@param ").append(entityLowerName).append(" 实体类");
		}
		buffer.append(LINE).append(TAB).append(" * ").append("@param ").append("request ").append("HTPP请求");
		buffer.append(LINE).append(TAB).append(" * ").append("@param ").append("response ").append("HTTP响应");
		buffer.append(LINE).append(TAB).append(" * ").append("@param ").append("model ").append("Spring的Model");
		buffer.append(LINE).append(TAB).append(" */");

		// @RequestMapping(
		// value = "login",
		// method = { RequestMethod.POST },
		// consumes = { "application/x-www-form-urlencoded", "application/*" }
		// )
		buffer.append(LINE).append(TAB).append("@RequestMapping");
		buffer.append("(");

		buffer.append("value = ").append(QUOTES).append(relativeUri).append(QUOTES);

		buffer.append(", method = { ");
		buffer.append(this.getMethodTypeName(action, relativeUri));
		buffer.append(" }");

		// 有请求类型,设置类型
		if (requestMimeType != null) {
			buffer.append(", consumes = { ");

			buffer.append(QUOTES);
			buffer.append(requestMimeType.getType());
			buffer.append(QUOTES);

			buffer.append(" }");
		}

		buffer.append(")");

		// ResponseBody
		buffer.append(LINE).append(TAB).append("@ResponseBody");

		// public void login(Entity entity, HttpServletRequest request, HttpServletResponse response, Model model)
		buffer.append(LINE).append(TAB);
		buffer.append("public ");
		// 设置响应
		buffer.append(this.getResponseTypeName(responseMimeType));
		buffer.append(" ");
		buffer.append(this.getMethodName(action, relativeUri));
		buffer.append("(");

		// entity
		if (StringUtils.isNotEmpty(entityName)) {
			if (this.addEntityAnnonation(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUriParameters, resource)) {
				buffer.append("@RequestBody ");
			}
			buffer.append(entityUpperName);
			buffer.append(" ");
			buffer.append(entityLowerName);
			buffer.append(", ");
		}
		buffer.append("HttpServletRequest request, HttpServletResponse response, Model model");
		buffer.append(") {");

		return buffer.toString();
	}

	/**
	 * 获取方法的尾部
	 * @return 尾部
	 */
	public String getMethodFooter() {
		return new StringBuilder().append(LINE).append(TAB).append("}").toString();
	}

	/**
	 * 获取例子
	 * @param requestMimeType 请求MimeType
	 * @param status 响应编码
	 * @param responseMimeType 响应MimeType
	 * @param action 事件
	 * @param relativeUri 相对URI
	 * @param parentFullUriParameters 父URI参数
	 * @param resource 本资源
	 * @return 例子
	 */
	public String getMethodExample(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) {

		if (responseMimeType == null) {
			return StringUtils.EMPTY;
		}

		StringBuilder buffer = new StringBuilder();
		String examples = responseMimeType.getExample();

		buffer.append(LINE).append(LINE).append(TAB).append(TAB);
		buffer.append("String str = ").append(QUOTES).append(QUOTES).append(";");

		if (StringUtils.isNotEmpty(examples)) {

			List<String> array = RamlUtils.splitByLine(examples);
			for (String example : array) {
				buffer.append(LINE).append(TAB).append(TAB);
				buffer.append("str += ");
				buffer.append(QUOTES);
				buffer.append(RamlUtils.replaceQuotes(example));
				buffer.append(QUOTES);
				buffer.append(";");
			}
			buffer.append(LINE).append(TAB).append(TAB);
		}

		buffer.append("return str;");

		return buffer.toString();
	}

	/**
	 * 获取参数的类
	 * @param requestMimeType 请求MimeType
	 * @param status 响应编码
	 * @param responseMimeType 响应MimeType
	 * @param action 事件
	 * @param relativeUri 相对URI
	 * @param parentFullUriParameters 父URI参数
	 * @param resource 本资源
	 * @return 参数的类
	 */
	private String getDataEntity(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) {
		StringBuilder buffer = new StringBuilder();

		// 实体类名称
		String entityName = this.getEntityName(action, relativeUri);
		if (StringUtils.isEmpty(entityName)) {
			return StringUtils.EMPTY;
		}
		String entityUpperName = entityName.substring(0, 1).toUpperCase() + entityName.substring(1);

		buffer.append(LINE).append(LINE).append(TAB);
		buffer.append("public static class ");
		buffer.append(entityUpperName);
		buffer.append(" implements Serializable {");

		// serial
		// private static final long serialVersionUID = 4649474858114976268L;
		buffer.append(LINE).append(LINE).append(TAB).append(TAB);
		buffer.append("private static final long serialVersionUID = ");
		buffer.append(System.currentTimeMillis()).append("L;");

		buffer.append(LINE);

		// 参数
		// uri
		for (AbstractParam abstractParam : parentFullUriParameters.values()) {
			this.addMethodClassAttribute(buffer, abstractParam);
		}
		// query
		Map<String, QueryParameter> queryParameters = action.getQueryParameters();
		if (queryParameters != null) {
			for (AbstractParam abstractParam : queryParameters.values()) {
				this.addMethodClassAttribute(buffer, abstractParam);
			}
		}
		// form
		List<FormParameter> formParameterList = Lists.newArrayList();
		if (requestMimeType != null) {
			Map<String, List<FormParameter>> formParameters = requestMimeType.getFormParameters();
			if (formParameters != null) {
				Collection<List<FormParameter>> values = formParameters.values();
				for (List<FormParameter> c : values) {
					formParameterList.addAll(c);
				}
			}
		}
		for (AbstractParam abstractParam : formParameterList) {
			this.addMethodClassAttribute(buffer, abstractParam);
		}

		// getter settter
		for (AbstractParam abstractParam : parentFullUriParameters.values()) {
			this.addMethodClassGetterAndSetter(buffer, abstractParam);
		}
		if (queryParameters != null) {
			for (AbstractParam abstractParam : queryParameters.values()) {
				this.addMethodClassGetterAndSetter(buffer, abstractParam);
			}
		}
		for (AbstractParam abstractParam : formParameterList) {
			this.addMethodClassGetterAndSetter(buffer, abstractParam);
		}

		buffer.append(LINE).append(TAB);
		buffer.append("}");

		return buffer.toString();
	}

	/**
	 * 设置方法类的属性
	 * @param buffer buffer
	 * @param abstractParam 参数
	 */
	private void addMethodClassAttribute(StringBuilder buffer, AbstractParam abstractParam) {
		buffer.append(LINE).append(TAB).append(TAB);
		buffer.append("/** ").append(abstractParam.getDescription()).append("*/");
		buffer.append(LINE).append(TAB).append(TAB);
		buffer.append("private String ").append(abstractParam.getDisplayName()).append(";");
	}

	/**
	* 设置方法类的属性
	* @param buffer buffer
	* @param abstractParam 参数
	*/
	private void addMethodClassGetterAndSetter(StringBuilder buffer, AbstractParam abstractParam) {
		String displayName = abstractParam.getDisplayName();

		// getter
		buffer.append(LINE);
		buffer.append(LINE).append(TAB).append(TAB);
		buffer.append("public String get").append(displayName.substring(0, 1) + displayName.substring(1)).append("() {");
		buffer.append(LINE).append(TAB).append(TAB);
		buffer.append(TAB).append("return ").append(displayName).append(";");
		buffer.append(LINE).append(TAB).append(TAB);
		buffer.append("}");

		// getter
		buffer.append(LINE);
		buffer.append(LINE).append(TAB).append(TAB);
		buffer.append("public void set").append(displayName.substring(0, 1) + displayName.substring(1)).append("(String ").append(displayName).append(") {");
		buffer.append(LINE).append(TAB).append(TAB);
		buffer.append(TAB).append("this.").append(displayName).append(" = ").append(displayName).append(";");
		buffer.append(LINE).append(TAB).append(TAB);
		buffer.append("}");

	}

	/**
	 * 获取方法类型
	 * @param action 事件
	 * @param relativeUri URI
	 * @return 方法类型
	 */
	private String getMethodTypeName(Action action, String relativeUri) {
		// value = "login"
		StringBuilder buffer = new StringBuilder();

		switch (action.getType()) {
		case GET:
			buffer.append("RequestMethod.GET");
			break;
		case POST:
			buffer.append("RequestMethod.POST");
			break;
		case PUT:
			buffer.append("RequestMethod.PUT");
			break;
		case DELETE:
			buffer.append("RequestMethod.DELETE");
			break;
		default:
			break;
		}
		return buffer.toString();
	}

	/**
	 * 获取方法名, get add set del
	 * 如果想对URI不为空,增加URI
	 * @param action 事件
	 * @param relativeUri URI
	 * @return 方法名
	 */
	protected String getMethodName(Action action, String relativeUri) {
		// login
		StringBuilder buffer = new StringBuilder();

		switch (action.getType()) {
		case GET:
			buffer.append("get");
			break;
		case POST:
			buffer.append("add");
			break;
		case PUT:
			buffer.append("set");
			break;
		case DELETE:
			buffer.append("del");
			break;
		default:
			break;
		}

		// blank relative uri
		String uri = RamlUtils.trimUriParam(relativeUri);
		if (StringUtils.isNotBlank(uri)) {
			buffer.append(uri.substring(1, 2).toUpperCase());
			buffer.append(uri.substring(2));
		}
		return buffer.toString();
	}

	/**
	 * 获取实体类名
	 * @param action 事件
	 * @param relativeUri URI
	 * @return 实体类名
	 */
	public String getEntityName(Action action, String relativeUri) {

		String uri = RamlUtils.trimUriParam(relativeUri);
		// 返回默认
		if (StringUtils.isEmpty(uri)) {
			return DEFAULT_ENTITY_NAME;
		}
		StringBuilder buffer = new StringBuilder();
		String[] array = uri.split("[/]");
		buffer.append(array[0]);
		for (int i = 1; i < array.length; i++) {
			String s = array[i];
			buffer.append(s.substring(0, 1).toUpperCase());
			buffer.append(s.substring(1));
		}

		return buffer.toString();
	}

	/**
	 * 是否增加 @RequestBody注解
	 * @param requestMimeType 请求MimeType
	 * @param status 响应编码
	 * @param responseMimeType 响应MimeType
	 * @param action 事件
	 * @param relativeUri 相对URI
	 * @param parentFullUriParameters 父URI参数
	 * @param resource 本资源
	 * @return 是否增加 @RequestBody注解
	 */
	public boolean addEntityAnnonation(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) {
		return false;
	}

	/**
	 * 获取响应的类型 String void
	 * @param responseMimeType 响应MimeType
	 * @return 响应类型
	 */
	public abstract String getResponseTypeName(MimeType responseMimeType) throws ParseException;

}
