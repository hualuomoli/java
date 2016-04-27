package com.github.hualuomoli.raml.parser.join.transfer.res.success.json.java;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.raml.model.Action;
import org.raml.model.MimeType;
import org.raml.model.ParamType;
import org.raml.model.Resource;
import org.raml.model.parameter.AbstractParam;
import org.raml.model.parameter.FormParameter;
import org.raml.model.parameter.QueryParameter;
import org.raml.model.parameter.UriParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.raml.parser.exception.ParseException;
import com.github.hualuomoli.raml.parser.join.Join;
import com.github.hualuomoli.raml.parser.join.transfer.Transfer;
import com.github.hualuomoli.raml.parser.join.transfer.res.success.json.ResponseSuccessJsonTransfer;
import com.github.hualuomoli.raml.parser.util.RamlUtils;
import com.google.common.collect.Lists;

/**
 * JAVA字符串连接转换器,默认转换URLEncoded参数请求
 * @author hualuomoli
 *
 */
public abstract class JavaTransfer extends ResponseSuccessJsonTransfer implements Join, Transfer {

	public static final Logger logger = LoggerFactory.getLogger(JavaTransfer.class);

	public static final String RESPONSE_TYPE_VOID = "void";
	public static final String RESPONSE_TYPE_STRING = "String";

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

		// method notes
		buffer.append(this.getMethodNote(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUriParameters, resource));

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
	 * 获取方法的注释
	 * @param requestMimeType 请求MimeType
	 * @param status 响应编码
	 * @param responseMimeType 响应MimeType
	 * @param action 事件
	 * @param relativeUri 相对URI
	 * @param parentFullUriParameters 父URI参数
	 * @param resource 本资源
	 * @return 方法注释
	 */
	public String getMethodNote(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException {
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

		// URI参数
		this.addMethodUriParameterNote(buffer, requestMimeType, status, responseMimeType, action, relativeUri, parentFullUriParameters, resource);
		// 其他参数
		this.addMethodOtherParameterNote(buffer, requestMimeType, status, responseMimeType, action, relativeUri, parentFullUriParameters, resource);

		buffer.append(LINE).append(TAB).append(" * ").append("@param ").append("request ").append("HTPP请求");
		buffer.append(LINE).append(TAB).append(" * ").append("@param ").append("response ").append("HTTP响应");
		buffer.append(LINE).append(TAB).append(" * ").append("@param ").append("model ").append("Spring的Model");
		buffer.append(LINE).append(TAB).append(" */");

		return buffer.toString();
	}

	/**
	 * 设置方法的URI参数注释
	 * @param buffer buffer
	 * @param requestMimeType 请求MimeType
	 * @param status 响应编码
	 * @param responseMimeType 响应MimeType
	 * @param action 事件
	 * @param relativeUri 相对URI
	 * @param parentFullUriParameters 父URI参数
	 * @param resource 本资源
	 */
	public void addMethodUriParameterNote(StringBuilder buffer, MimeType requestMimeType, String status, MimeType responseMimeType, Action action,
			String relativeUri, Map<String, UriParameter> parentFullUriParameters, Resource resource) {
		// @param username 用户名
		if (parentFullUriParameters == null || parentFullUriParameters.size() == 0) {
			return;
		}

		for (UriParameter uriParameter : parentFullUriParameters.values()) {
			buffer.append(LINE).append(TAB).append(" * ").append("@param ");
			buffer.append(uriParameter.getDisplayName()).append(" ").append(uriParameter.getDescription());
		}

	}

	/**
	 * 设置方法的其他参数注释
	 * @param buffer buffer
	 * @param requestMimeType 请求MimeType
	 * @param status 响应编码
	 * @param responseMimeType 响应MimeType
	 * @param action 事件
	 * @param relativeUri 相对URI
	 * @param parentFullUriParameters 父URI参数
	 * @param resource 本资源
	 * @return 方法的其他参数
	 */
	public void addMethodOtherParameterNote(StringBuilder buffer, MimeType requestMimeType, String status, MimeType responseMimeType, Action action,
			String relativeUri, Map<String, UriParameter> parentFullUriParameters, Resource resource) {
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

		buffer.append(LINE).append(TAB).append("@RequestMapping");
		buffer.append("(");

		// value
		buffer.append("value = ").append(QUOTES).append(relativeUri).append(QUOTES);
		// method
		buffer.append(this.getMethodTypeHeader(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUriParameters, resource));
		// consumes
		buffer.append(this.getMethodConsumesHeader(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUriParameters, resource));
		// produces
		buffer.append(this.getMethodProducesHeader(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUriParameters, resource));

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

		// URI参数
		buffer.append(this.getMethodUriParameterHeader(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUriParameters, resource));
		// 其他参数
		buffer.append(this.getMethodOtherParameterHeader(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUriParameters, resource));

		buffer.append("HttpServletRequest request, HttpServletResponse response, Model model");
		buffer.append(") {");

		return buffer.toString();
	}

	/**
	 * 获取Spring MVC 的method
	 * @param requestMimeType 请求MimeType
	 * @param status 响应编码
	 * @param responseMimeType 响应MimeType
	 * @param action 事件
	 * @param relativeUri 相对URI
	 * @param parentFullUriParameters 父URI参数
	 * @param resource 本资源
	 * @return Spring MVC 的method
	 */
	public String getMethodTypeHeader(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) {
		// method = { RequestMethod.POST }
		StringBuilder buffer = new StringBuilder();

		buffer.append(", method = { ");
		buffer.append(this.getMethodTypeName(action, relativeUri));
		buffer.append(" }");

		return buffer.toString();
	}

	/**
	 * 获取Spring MVC 的consumes
	 * @param requestMimeType 请求MimeType
	 * @param status 响应编码
	 * @param responseMimeType 响应MimeType
	 * @param action 事件
	 * @param relativeUri 相对URI
	 * @param parentFullUriParameters 父URI参数
	 * @param resource 本资源
	 * @return Spring MVC 的produces
	 */
	public String getMethodConsumesHeader(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) {
		// consumes = { "application/x-www-form-urlencoded" }
		StringBuilder buffer = new StringBuilder();

		if (requestMimeType == null) {
			return buffer.toString();
		}

		buffer.append(", consumes = { ");

		buffer.append(QUOTES);
		buffer.append(requestMimeType.getType());
		buffer.append(QUOTES);

		buffer.append(" }");

		return buffer.toString();
	}

	/**
	* 获取Spring MVC 的produces
	* @param requestMimeType 请求MimeType
	* @param status 响应编码
	* @param responseMimeType 响应MimeType
	* @param action 事件
	* @param relativeUri 相对URI
	* @param parentFullUriParameters 父URI参数
	* @param resource 本资源
	* @return Spring MVC 的produces
	*/
	public String getMethodProducesHeader(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) {
		// produces = { "application/json" }

		StringBuilder buffer = new StringBuilder();

		if (responseMimeType == null) {
			return buffer.toString();
		}

		buffer.append(", produces = { ");

		buffer.append(QUOTES);
		buffer.append(responseMimeType.getType());
		buffer.append(QUOTES);

		buffer.append(" }");

		return buffer.toString();
	}

	/**
	 * 获取方法的URI参数声明
	 * @param requestMimeType 请求MimeType
	 * @param status 响应编码
	 * @param responseMimeType 响应MimeType
	 * @param action 事件
	 * @param relativeUri 相对URI
	 * @param parentFullUriParameters 父URI参数
	 * @param resource 本资源
	 * @return 方法的URI参数声明
	 */
	public String getMethodUriParameterHeader(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) {
		// @PathVariable(value = "username") String username
		StringBuilder buffer = new StringBuilder();

		if (parentFullUriParameters == null || parentFullUriParameters.size() == 0) {
			return buffer.toString();
		}

		for (UriParameter uriParameter : parentFullUriParameters.values()) {
			buffer.append("@PathVariable");

			buffer.append("(");
			buffer.append("value = ").append(QUOTES).append(uriParameter.getDisplayName()).append(QUOTES);
			buffer.append(")");

			buffer.append(" ");
			buffer.append(this.getParameterType(uriParameter));
			buffer.append(" ");
			buffer.append(uriParameter.getDisplayName());

			buffer.append(", ");
		}

		return buffer.toString();
	}

	/**
	 * 获取方法的其他参数声明
	 * @param requestMimeType 请求MimeType
	 * @param status 响应编码
	 * @param responseMimeType 响应MimeType
	 * @param action 事件
	 * @param relativeUri 相对URI
	 * @param parentFullUriParameters 父URI参数
	 * @param resource 本资源
	 * @param 方法的其他参数声明
	 */
	public String getMethodOtherParameterHeader(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) {
		// User user
		StringBuilder buffer = new StringBuilder();

		String entityName = this.getEntityName(action, relativeUri);
		if (StringUtils.isEmpty(entityName)) {
			return buffer.toString();
		}

		String entityUpperName = entityName.substring(0, 1).toUpperCase() + entityName.substring(1);
		String entityLowerName = entityName.substring(0, 1).toLowerCase() + entityName.substring(1);

		buffer.append(entityUpperName).append(" ").append(entityLowerName).append(", ");

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
				buffer.append("\\n");
				buffer.append(QUOTES);
				buffer.append(";");
			}
			buffer.append(LINE).append(TAB).append(TAB);
		}

		buffer.append("return str;");

		return buffer.toString();
	}

	/**
	 * 获取参数的类定义
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
		buffer.append(" implements EntityValidator, Serializable {");

		// serial
		// private static final long serialVersionUID = 4649474858114976268L;
		buffer.append(LINE).append(LINE).append(TAB).append(TAB);
		buffer.append("private static final long serialVersionUID = ");
		buffer.append(System.currentTimeMillis()).append("L;");

		//
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

		// 注释
		buffer.append(LINE).append(TAB).append(TAB);
		buffer.append("/** ").append(abstractParam.getDescription()).append("*/");

		// validator
		this.addMethodClassGetterValidator(buffer, abstractParam);

		// 属性
		buffer.append(LINE).append(TAB).append(TAB);
		buffer.append("private ");
		buffer.append(this.getParameterType(abstractParam));
		buffer.append(" ");
		buffer.append(abstractParam.getDisplayName());
		buffer.append(";");
	}

	/**
	* 设置方法类的属性
	* @param buffer buffer
	* @param abstractParam 参数
	*/
	private void addMethodClassGetterAndSetter(StringBuilder buffer, AbstractParam abstractParam) {
		String displayName = abstractParam.getDisplayName();

		buffer.append(LINE);

		// getter
		buffer.append(LINE).append(TAB).append(TAB);
		buffer.append("public ");
		buffer.append(this.getParameterType(abstractParam));
		buffer.append(" ");
		if (abstractParam.getType() == ParamType.BOOLEAN) {
			buffer.append("is");
		} else {
			buffer.append("get");
		}
		buffer.append(displayName.substring(0, 1) + displayName.substring(1)).append("() {");
		buffer.append(LINE).append(TAB).append(TAB);
		buffer.append(TAB).append("return ").append(displayName).append(";");
		buffer.append(LINE).append(TAB).append(TAB);
		buffer.append("}");

		// getter
		buffer.append(LINE);
		buffer.append(LINE).append(TAB).append(TAB);
		buffer.append("public void set").append(displayName.substring(0, 1) + displayName.substring(1));
		buffer.append("(");
		buffer.append(this.getParameterType(abstractParam));
		buffer.append(" ").append(displayName).append(") {");
		buffer.append(LINE).append(TAB).append(TAB);
		buffer.append(TAB).append("this.").append(displayName).append(" = ").append(displayName).append(";");
		buffer.append(LINE).append(TAB).append(TAB);
		buffer.append("}");

	}

	/**
	* 设置getter方法类的校验
	* @param buffer buffer
	* @param abstractParam 参数
	*/
	private void addMethodClassGetterValidator(StringBuilder buffer, AbstractParam abstractParam) {

		// @NotNull(message = "")
		if (abstractParam.isRequired()) {
			ValidHeader.addNotNull(buffer, abstractParam);
		}

		switch (abstractParam.getType()) {
		case STRING:
			// @NotEmpty(message = "")
			if (abstractParam.isRequired()) {
				ValidHeader.addNotEmpty(buffer, abstractParam);
			}
			// @Length(min = 1, max = 5, message = "用户名长度在1-5之间")
			if (abstractParam.getMinLength() != null || abstractParam.getMaxLength() != null) {
				ValidHeader.addLength(buffer, abstractParam);
			}
			// @Pattern(regexp = "", message = "")
			if (StringUtils.isNotEmpty(abstractParam.getPattern())) {
				ValidHeader.addPattern(buffer, abstractParam);
			}
			break;
		case BOOLEAN:
			break;
		case DATE:
			// @Pattern(regexp = "", message = "")
			if (StringUtils.isNotEmpty(abstractParam.getExample())) {
				ValidHeader.addDateTimeFormat(buffer, abstractParam);
			}
			break;
		case INTEGER:
			// @Min(value = 1, message = "")
			// @Max(value = 10, message = "")
			if (abstractParam.getMinimum() != null) {
				ValidHeader.addMin(buffer, abstractParam);
			}
			if (abstractParam.getMaximum() != null) {
				ValidHeader.addMax(buffer, abstractParam);
			}
			break;
		case FILE:
			break;
		case NUMBER:
			break;
		default:
			break;
		}

	}

	// 有效性header
	static class ValidHeader {

		// 必填选项
		// @NotNull(message = "")
		public static void addNotNull(StringBuilder buffer, AbstractParam abstractParam) {
			buffer.append(LINE).append(TAB).append(TAB);
			buffer.append("@NotNull");
			buffer.append("(");
			buffer.append("message = ");
			buffer.append(QUOTES);
			buffer.append(abstractParam.getDescription()).append("不能为空");
			buffer.append(QUOTES);
			buffer.append(")");
		}

		// 必填选项(字符串,集合,Map,数组)
		// @NotEmpty(message = "必填选项")
		public static void addNotEmpty(StringBuilder buffer, AbstractParam abstractParam) {
			buffer.append(LINE).append(TAB).append(TAB);
			buffer.append("@NotEmpty");
			buffer.append("(");
			buffer.append("message = ");
			buffer.append(QUOTES);
			buffer.append(abstractParam.getDescription()).append("不能为空");
			buffer.append(QUOTES);
			buffer.append(")");
		}

		// 长度限制
		// @Length(min = 1, max = 5, message = "用户名长度在1-5之间")
		public static void addLength(StringBuilder buffer, AbstractParam abstractParam) {
			buffer.append(LINE).append(TAB).append(TAB);
			if (abstractParam.getMaxLength() == null) {
				// 只设置了最小长度
				// @Length(min = 1, message = "数据长度不能小于1")
				buffer.append("@Length");
				buffer.append("(");
				// 最小值
				buffer.append("min = ").append(abstractParam.getMinLength()).append(", ");
				buffer.append("message = ");
				buffer.append(QUOTES);
				buffer.append(abstractParam.getDescription()).append("长度不能小于").append(abstractParam.getMinLength());
				buffer.append(QUOTES);
				buffer.append(")");
			} else if (abstractParam.getMinLength() == null) {
				// 只设置了最大长度
				// @Length(max = 5, message = "数据长度不能大于5")
				buffer.append("@Length");
				buffer.append("(");
				buffer.append("max = ").append(abstractParam.getMaxLength()).append(", ");
				buffer.append("message = ");
				buffer.append(QUOTES);
				buffer.append(abstractParam.getDescription()).append("长度不能大于").append(abstractParam.getMaxLength());
				buffer.append(QUOTES);
				buffer.append(")");
			} else {
				// 设置了最小长度和最大长度
				// @Length(min = 1, max = 5, message = "用户名长度在1-5之间")
				buffer.append("@Length");
				buffer.append("(");
				buffer.append("min = ").append(abstractParam.getMinLength()).append(", ");
				buffer.append("max = ").append(abstractParam.getMaxLength()).append(", ");
				buffer.append("message = ");
				buffer.append(QUOTES);
				buffer.append(abstractParam.getDescription());
				buffer.append("长度在").append(abstractParam.getMinLength()).append("-").append(abstractParam.getMaxLength()).append("之间");
				buffer.append(QUOTES);
				buffer.append(")");
			}
		}

		// 设置了最小值
		// @Min(value = 1, message = "")
		public static void addMin(StringBuilder buffer, AbstractParam abstractParam) {
			buffer.append(LINE).append(TAB).append(TAB);
			buffer.append("@Min");
			buffer.append("(");
			buffer.append("value = ").append(abstractParam.getMinimum().intValue()).append(", ");
			buffer.append("message = ");
			buffer.append(QUOTES);
			buffer.append(abstractParam.getDescription()).append("长度不能小于").append(abstractParam.getMinimum().intValue());
			buffer.append(QUOTES);
			buffer.append(")");
		}

		// 设置了最大值
		// @Max(value = 10, message = "")
		public static void addMax(StringBuilder buffer, AbstractParam abstractParam) {
			buffer.append(LINE).append(TAB).append(TAB);
			buffer.append("@Max");
			buffer.append("(");
			buffer.append("value = ").append(abstractParam.getMaximum().intValue()).append(", ");
			buffer.append("message = ");
			buffer.append(QUOTES);
			buffer.append(abstractParam.getDescription()).append("长度不能大于").append(abstractParam.getMaximum().intValue());
			buffer.append(QUOTES);
			buffer.append(")");
		}

		// 正则表达式
		// @Pattern(regexp = "", message = "")
		public static void addPattern(StringBuilder buffer, AbstractParam abstractParam) {
			String pattern = abstractParam.getPattern();
			String regexp = StringUtils.replace(pattern, "\\", "\\\\");

			// replace
			buffer.append(LINE).append(TAB).append(TAB);
			buffer.append("@Pattern");
			buffer.append("(");
			buffer.append("regexp = ");
			buffer.append(QUOTES);
			buffer.append(regexp);
			buffer.append(QUOTES);
			buffer.append(", ");
			buffer.append("message = ");
			buffer.append(QUOTES);
			buffer.append("请设置合法的").append(abstractParam.getDescription());
			buffer.append(QUOTES);
			buffer.append(")");
		}

		/**
		 * 日期注解
		 * @param buffer
		 * @param abstractParam
		 */
		public static void addDateTimeFormat(StringBuilder buffer, AbstractParam abstractParam) {
			String example = abstractParam.getExample();

			String pattern = DateFormat.getPattern(example);

			// replace
			buffer.append(LINE).append(TAB).append(TAB);
			buffer.append("@DateTimeFormat");
			buffer.append("(");
			buffer.append("pattern = ");
			buffer.append(QUOTES);
			buffer.append(pattern);
			buffer.append(QUOTES);
			// buffer.append(", ");
			// buffer.append("message = ");
			// buffer.append(QUOTES);
			// buffer.append("请设置合法的").append(abstractParam.getDescription());
			// buffer.append(QUOTES);
			buffer.append(")");
		}
	}

	// 日期转换
	static class DateFormat {

		private static List<Validator> validatorList = Lists.newArrayList();

		static {
			validatorList.add(new LineValidator());
		}

		public static String getPattern(String date) {
			for (int i = 0; i < validatorList.size(); i++) {
				Validator validator = validatorList.get(i);
				if (validator.valid(date)) {
					return validator.transfer(date);
				}
			}
			return null;
		}

		// 有效性
		interface Validator {

			boolean valid(String date);

			String transfer(String date);
		}

		static class LineValidator implements Validator {

			String regex = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$";
			String regex2 = "^\\d{4}-\\d{2}-\\d{2}$";

			@Override
			public boolean valid(String date) {
				return StringUtils.isNotEmpty(date) && (date.matches(regex) || date.matches(regex2));
			}

			@Override
			public String transfer(String date) {
				String temp = date.substring(8);
				if (StringUtils.isEmpty(temp)) {
					return "yyyy-MM-dd";
				}
				return "yyyy-MM-dd kk:mm:ss";
			}

		}

	}

	/**
	 * 获取参数类型
	 * @param abstractParam 参数
	 * @return 参数类型
	 */
	public String getParameterType(AbstractParam abstractParam) {
		switch (abstractParam.getType()) {
		case STRING:
			return "String";
		case INTEGER:
			return "Integer";
		case DATE:
			return "Date";
		case BOOLEAN:
			return "Boolean";
		case NUMBER:
			return "Double";
		case FILE:
			return "MultipartFile";
		default:
			break;
		}
		logger.warn("can not find param type" + abstractParam.getType());
		return "String";
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
	 * 获取实体类名,默认为URLEncoded方式
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
	 * 获取响应的类型 String void
	 * @param responseMimeType 响应MimeType
	 * @return 响应类型
	 */
	public String getResponseTypeName(MimeType responseMimeType) throws ParseException {
		if (responseMimeType == null) {
			return RESPONSE_TYPE_VOID;
		}
		if (StringUtils.equals(responseMimeType.getType(), MIME_TYPE_JSON)) {
			return RESPONSE_TYPE_STRING;
		}
		throw new ParseException("can not support response MimeType " + responseMimeType.getType());
	}

}
