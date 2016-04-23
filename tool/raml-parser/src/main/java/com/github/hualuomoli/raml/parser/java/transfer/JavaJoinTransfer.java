package com.github.hualuomoli.raml.parser.java.transfer;

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

import com.github.hualuomoli.raml.join.Join;
import com.github.hualuomoli.raml.parser.RamlParserAbstract;
import com.github.hualuomoli.raml.parser.transfer.Transfer;
import com.google.common.collect.Lists;

/**
 * JAVA字符串连接转换器
 * @author hualuomoli
 *
 */
public abstract class JavaJoinTransfer implements Join, Transfer {

	/**
	* 获取事件数据
	* @param queryMimeType 请求MimeType
	* @param status 响应编码
	* @param responseMimeType 响应MimeType
	* @param action 事件
	* @param relativeUri 相对URI
	* @param parentFullUriParameters 父URI参数
	* @param resource 本资源
	* @return 事件数据
	*/
	public String getData(MimeType queryMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) {
		StringBuilder buffer = new StringBuilder();

		buffer.append(LINE);
		// method header
		buffer.append(this.getMethodHeader(queryMimeType, status, responseMimeType, action, relativeUri));

		// method example
		buffer.append(this.getMethodExample(queryMimeType, status, responseMimeType, action, relativeUri));

		// method footer
		buffer.append(this.getMethodFooter());

		// entity
		buffer.append(this.getDataEntity(queryMimeType, status, responseMimeType, action, relativeUri, parentFullUriParameters, resource));

		return buffer.toString();
	}

	/**
	 * 获取实体类数据
	 * @param queryMimeType 请求MimeType
	 * @param status 响应编码
	 * @param responseMimeType 响应MimeType
	 * @param action 事件
	 * @param relativeUri 相对URI
	 * @param parentFullUriParameters 父URI参数
	 * @param resource 本资源
	 * @return 事件数据
	 */
	private String getDataEntity(MimeType queryMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) {
		StringBuilder buffer = new StringBuilder();

		buffer.append(LINE).append(LINE).append(TAB);
		buffer.append("public static class ");
		buffer.append(this.getEntityName(relativeUri, true));
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
		if (queryMimeType != null) {
			Map<String, List<FormParameter>> formParameters = queryMimeType.getFormParameters();
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
	 * 获取方法例子
	 * @param queryMimeType 请求MimeType
	 * @param status 响应编码
	 * @param responseMimeType 响应MimeType
	 * @param action 事件
	 * @param relativeUri URI
	 * @return 方法例子
	 */
	private String getMethodExample(MimeType queryMimeType, String status, MimeType responseMimeType, Action action, String relativeUri) {
		if (responseMimeType == null) {
			return StringUtils.EMPTY;
		}

		StringBuilder buffer = new StringBuilder();
		String examples = responseMimeType.getExample();

		buffer.append(LINE).append(LINE).append(TAB).append(TAB);
		buffer.append("String str = ").append(QUOTES).append(QUOTES).append(";");

		if (StringUtils.isNotEmpty(examples)) {

			List<String> array = RamlParserAbstract.splitByLine(examples);
			for (String example : array) {
				buffer.append(LINE).append(TAB).append(TAB);
				buffer.append("str += ");
				buffer.append(QUOTES);
				buffer.append(RamlParserAbstract.replaceQuotes(example));
				buffer.append(QUOTES);
				buffer.append(";");
			}
			buffer.append(LINE).append(TAB).append(TAB);
		}

		buffer.append("return str;");

		return buffer.toString();
	}

	/**
	 * 获取方法的头部
	 * @param queryMimeType 请求MimeType
	 * @param status 响应编码
	 * @param responseMimeType 响应MimeType
	 * @param action 事件
	 * @param relativeUri URI
	 * @return 方法头部
	 */
	private Object getMethodHeader(MimeType queryMimeType, String status, MimeType responseMimeType, Action action, String relativeUri) {
		StringBuilder buffer = new StringBuilder();
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
		buffer.append(LINE).append(TAB).append(" * ").append("@param ").append(this.getEntityName(relativeUri, false)).append(" 实体类");
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
		buffer.append(this.getMethodType(action, relativeUri));
		buffer.append(" }");

		buffer.append(", consumes = { ");

		List<String> supprotQueryMimeType = this.getSupportQueryMimeType();
		for (int i = 0; i < supprotQueryMimeType.size(); i++) {
			if (i >= 1) {
				buffer.append(", ");
			}
			buffer.append(QUOTES);
			buffer.append(supprotQueryMimeType.get(i));
			buffer.append(QUOTES);
		}
		buffer.append(" }");

		buffer.append(")");

		// ResponseBody
		buffer.append(LINE).append(TAB).append("@ResponseBody");

		// public void login(Entity entity, HttpServletRequest request, HttpServletResponse response, Model model)
		buffer.append(LINE).append(TAB);
		buffer.append("public");
		buffer.append(responseMimeType == null ? " void " : " String ");
		buffer.append(this.getMethodName(action, relativeUri));
		buffer.append("(");
		if (responseMimeType != null) {
			if (StringUtils.isNotEmpty(this.getEntityRequestBodyPrefix())) {
				buffer.append("@RequestBody ");
			}
			// 没有前缀
			buffer.append(this.getEntityName(relativeUri, true));
			buffer.append(" ");
			buffer.append(this.getEntityName(relativeUri, false));
		}
		buffer.append(", HttpServletRequest request, HttpServletResponse response, Model model");
		buffer.append(") {");

		return buffer.toString();
	}

	/**
	 * 获取方法的尾部
	 * @return 尾部
	 */
	private String getMethodFooter() {
		return new StringBuilder().append(LINE).append(TAB).append("}").toString();
	}

	/**
	 * 获取支持的请求MimeType
	 * @return 支持的请求MimeType
	 */
	protected abstract List<String> getSupportQueryMimeType();

	/**
	 * 获取Entity的前缀,如JSON需要增加Json前缀与URLEncoded区别
	 * @return Entity的前缀,null或空不提供
	 */
	protected abstract String getEntityRequestBodyPrefix();

	/**
	 * 获取方法类型
	 * @param action 事件
	 * @param relativeUri URI
	 * @return 方法类型
	 */
	private String getMethodType(Action action, String relativeUri) {
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
	 * 获取方法名
	 * @param action 事件
	 * @param relativeUri URI
	 * @return 方法名
	 */
	private String getMethodName(Action action, String relativeUri) {
		// value = "login"
		StringBuilder buffer = new StringBuilder();

		String entityName = this.getEntityName(relativeUri, true);

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
		return buffer.append(entityName).toString();
	}

	/**
	 * 获取Entity名称
	 * @param relativeUri URI
	 * @param upper 是否首字母大写
	 * @return Entity名称
	 */
	private String getEntityName(String relativeUri, boolean upper) {
		String temp = relativeUri.replaceAll("/\\{.*}", "");
		String name;
		if (StringUtils.isEmpty(temp)) {
			name = "entity";
		} else {
			name = temp.substring(1);
		}

		// 增加前缀
		name = name.substring(0, 1).toUpperCase() + name.substring(1);
		if (StringUtils.isNotEmpty(this.getEntityRequestBodyPrefix())) {
			name = this.getEntityRequestBodyPrefix() + name;
		}

		if (upper) {
			return name.substring(0, 1).toUpperCase() + name.substring(1);
		} else {
			return name.substring(0, 1).toLowerCase() + name.substring(1);
		}
	}

}
