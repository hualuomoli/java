package com.github.hualuomoli.raml.join.adaptor;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.raml.model.MimeType;
import org.raml.model.ParamType;
import org.raml.model.parameter.AbstractParam;
import org.raml.model.parameter.FormParameter;
import org.raml.model.parameter.QueryParameter;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.raml.join.JoinParser.Adapter;
import com.github.hualuomoli.raml.util.JSONUtils;
import com.github.hualuomoli.raml.util.JSONUtils.JsonParam;
import com.github.hualuomoli.raml.util.RamlUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * JAVA适配器
 * @author hualuomoli
 *
 */
public abstract class JavaActionAdaptor implements ActionAdaptor {

	public static final String INDENT_CHAR = "\t";

	@Override
	public List<String> getDatas(Adapter adapter) {

		List<String> datas = Lists.newArrayList();

		List<String> note = this.getNote(adapter);
		List<String> annotation = this.getAnnotation(adapter);
		List<String> header = this.getHeader(adapter);
		List<String> content = this.getContent(adapter);
		List<String> footer = this.getFooter(adapter);
		List<String> entityDefinition = this.getEntityDefinition(adapter);
		List<String> resultEntityDefinition = this.getResultEntityDefinition(adapter);

		datas.addAll(note);
		datas.addAll(annotation);
		datas.addAll(header);
		datas.addAll(content);
		datas.addAll(footer);
		datas.addAll(entityDefinition);
		datas.addAll(resultEntityDefinition);

		return datas;
	}

	/**
	 * 获取方法注释
	 * @param adapter 适配者
	 * @return 方法注释
	 */
	public List<String> getNote(Adapter adapter) {
		List<String> datas = Lists.newArrayList();

		// 实体类的名称
		String entityName = Tool.getEntityName(adapter);

		// start
		datas.add("/**");

		// 实体类定义
		datas.add(" * @param " + RamlUtils.unCap(entityName) + " " + RamlUtils.dealDescription(adapter.action.getDescription()));

		// files
		Set<FormParameter> fileParams = Tool.getFileParameters(adapter.formMimeType);
		for (AbstractParam fileParam : fileParams) {
			datas.add(" * @param " + fileParam.getDisplayName() + " " + RamlUtils.dealDescription(fileParam.getDescription()));
		}

		// springmvc
		datas.add(" * @param request HttpServletRequest");
		datas.add(" * @param response HttpServletResponse");
		datas.add(" * @param model Model");

		// end
		datas.add(" */");

		return datas;
	}

	/**
	 * 获取方法注解
	 * @param adapter 适配者
	 * @return 方法注解
	 */
	public List<String> getAnnotation(Adapter adapter) {
		List<String> datas = Lists.newArrayList();

		// 请求注解
		String requestAnnonation = "";
		requestAnnonation += "@RequestMapping(" //
				// value
				+ "value = \"" + adapter.action.getResource().getRelativeUri() + "\""
				// split
				+ ", "
				// method
				+ "method = { RequestMethod." + adapter.action.getType().toString().toUpperCase() + " }";

		// consumes
		if (adapter.formMimeType != null) {
			// split
			requestAnnonation += ", "
					//
					+ "consumes = { \"" + adapter.formMimeType.getType() + "\" }";
		}
		// produces
		if (adapter.responseMimeType != null) {
			// split
			requestAnnonation += ", "
					//
					+ "produces = { \"" + adapter.responseMimeType.getType() + "\" }";
		}
		//
		requestAnnonation += ")";

		// 响应注解
		String responseAnnonation = "@ResponseBody";

		// 添加
		datas.add(requestAnnonation);
		datas.add(responseAnnonation);

		return datas;
	}

	/**
	 * 获取方法头部
	 * @param adapter 适配者
	 * @return 方法头部
	 */
	public List<String> getHeader(Adapter adapter) {
		List<String> datas = Lists.newArrayList();

		String entityName = Tool.getEntityName(adapter);
		String methodName = Tool.getMethodName(adapter);

		StringBuilder buffer = new StringBuilder();

		buffer.append("public");
		buffer.append(" ");
		buffer.append(Tool.hasResult(adapter) ? "String" : "void");
		buffer.append(" ");
		buffer.append(methodName);
		buffer.append("(");

		// 实体类
		buffer.append(entityName).append(" ").append(RamlUtils.unCap(entityName));
		// files
		Set<FormParameter> fileParams = Tool.getFileParameters(adapter.formMimeType);
		for (AbstractParam fileParam : fileParams) {
			buffer.append(", ");
			// @RequestParam(value = "", required = false)
			buffer.append("@RequestParam(value = \"").append(fileParam.getDisplayName()).append("\"");
			buffer.append(", required = ").append(fileParam.isRequired() ? "true" : "false");
			buffer.append(") ");

			// MultipartFile file
			buffer.append("MultipartFile").append(fileParam.isRepeat() ? "[]" : "").append(" ").append(fileParam.getDisplayName());
		}
		// springmvv
		buffer.append(", ");
		buffer.append("HttpServletRequest request");
		buffer.append(", ");
		buffer.append("HttpServletResponse response");
		buffer.append(", ");
		buffer.append("Model model");

		buffer.append(")");
		buffer.append(" {");

		datas.add(buffer.toString());

		return datas;
	}

	/**
	 * 获取方法内容
	 * @param adapter 适配者
	 * @return 方法内容
	 */
	public List<String> getContent(Adapter adapter) {

		List<String> datas = Lists.newArrayList();

		if (!Tool.hasResult(adapter)) {
			return datas;
		}

		String example = adapter.responseMimeType.getExample();
		if (StringUtils.isBlank(example)) {
			return datas;
		}

		datas.add("");

		// 定义变量
		datas.add("String datas = \"\";");

		// 添加数据
		List<String> array = RamlUtils.splitByLine(example);
		for (int i = 0; i < array.size(); i++) {
			datas.add("datas += \"" + RamlUtils.dealExample(array.get(i)) + "\";");
		}

		// 先转换为object,再转换为string
		// 转换成object
		String resultEntityName = Tool.getResultEntityName(adapter);
		if (JSONUtils.isListExample(example)) {
			datas.add("java.util.List<" + resultEntityName + "> data = JsonMapper.fromJsonString(datas,  JsonMapper.getJavaType(java.util.List.class,"
					+ resultEntityName + ".class));");
		} else {
			datas.add(resultEntityName + " data = JsonMapper.fromJsonString(datas,  " + resultEntityName + ".class);");
		}
		// 转换成string
		datas.add("String ret = JsonMapper.toJsonString(data);");

		// 增加logger
		datas.add("if (logger.isDebugEnabled()) {");
		datas.add(INDENT_CHAR + "logger.debug(\"ret {}\", ret);");
		datas.add("}");

		datas.add("return ret;");

		return datas;
	}

	/**
	 * 获取方法尾部
	 * @param adapter 适配者
	 * @return 方法尾部
	 */
	public List<String> getFooter(Adapter adapter) {
		List<String> datas = Lists.newArrayList();

		datas.add("}");

		return datas;
	}

	/**
	 * 获取方法实体类定义
	 * @param adapter 适配者
	 * @return 方法实体类定义
	 */
	public List<String> getEntityDefinition(Adapter adapter) {

		// 参数
		List<JsonParam> jsonParams = Lists.newArrayList();

		if (adapter.formMimeType != null && StringUtils.equals(adapter.formMimeType.getType(), MIME_TYPE_JSON)) {
			// json
			String schema = adapter.formMimeType.getSchema();
			String example = adapter.formMimeType.getExample();

			if (StringUtils.isNotBlank(schema)) {
				jsonParams = JSONUtils.parseSchema(schema);
			} else {
				jsonParams = JSONUtils.parseExample(example);
			}
		} else {
			Map<String, UriParameter> uriParameters = RamlUtils.getFullUriParameter(adapter.action);
			if (uriParameters != null && uriParameters.size() > 0) {
				for (AbstractParam param : uriParameters.values()) {
					JsonParam jsonParam = new JsonParam();
					jsonParam.setDataType(JsonParam.DATA_TYPE_SIMPLE);
					jsonParam.setParam(param);
					jsonParams.add(jsonParam);
				}
			}
			Map<String, QueryParameter> queryParameters = adapter.action.getQueryParameters();
			if (queryParameters != null && queryParameters.size() > 0) {
				for (AbstractParam param : queryParameters.values()) {
					JsonParam jsonParam = new JsonParam();
					jsonParam.setDataType(JsonParam.DATA_TYPE_SIMPLE);
					jsonParam.setParam(param);
					jsonParams.add(jsonParam);
				}
			}
			if (adapter.formMimeType != null) {
				Map<String, List<FormParameter>> formParameters = adapter.formMimeType.getFormParameters();
				for (String displayName : formParameters.keySet()) {
					List<FormParameter> list = formParameters.get(displayName);
					if (list != null && list.size() > 0) {
						AbstractParam param = list.get(0);
						JsonParam jsonParam = new JsonParam();
						jsonParam.setDataType(JsonParam.DATA_TYPE_SIMPLE);
						jsonParam.setParam(param);
						jsonParams.add(jsonParam);
					}
				}
			}
			// end
		}

		// parse
		return Tool.getClassDefinition(jsonParams, Tool.getEntityName(adapter), 0);

	}

	/**
	 * 获取方法返回实体类定义
	 * @param adapter 适配者
	 * @return 方法返回实体类定义
	 */
	public List<String> getResultEntityDefinition(Adapter adapter) {

		// 没有返回值
		if (!Tool.hasResult(adapter)) {
			return Lists.newArrayList();
		}

		// 参数
		List<JsonParam> jsonParams = Lists.newArrayList();

		if (adapter.responseMimeType != null && StringUtils.equals(adapter.responseMimeType.getType(), MIME_TYPE_JSON)) {
			// json
			String schema = adapter.responseMimeType.getSchema();
			String example = adapter.responseMimeType.getExample();

			if (StringUtils.isNotBlank(schema)) {
				jsonParams = JSONUtils.parseSchema(schema);
			} else {
				jsonParams = JSONUtils.parseExample(example);
			}
		} else {
			throw new RuntimeException("can not support response type");
		}

		// parse
		return Tool.getClassDefinition(jsonParams, Tool.getResultEntityName(adapter), 0);

	}

	// 工具
	static class Tool {

		/**
		 * 获取缩进字符串
		 * @param indentLevel 缩进级别
		 * @return 缩进字符串
		 */
		private static String getIndentCharts(int indentLevel) {
			String data = "";
			for (int i = 0; i < indentLevel; i++) {
				data += INDENT_CHAR;
			}
			return data;
		}

		/**
		 * 是否有返回值
		 * @param adapter 适配者
		 * @return 是否有返回值
		 */
		private static boolean hasResult(Adapter adapter) {
			return adapter.responseMimeType != null;
		}

		/**
		 * 获取文件类型的参数
		 * @param formMimeType 请求类型
		 * @return 文件类型的参数
		 */
		public static Set<FormParameter> getFileParameters(MimeType formMimeType) {
			Set<FormParameter> params = Sets.newHashSet();

			if (formMimeType == null || formMimeType.getFormParameters() == null || formMimeType.getFormParameters().size() == 0) {
				return params;
			}

			Map<String, List<FormParameter>> formParameters = formMimeType.getFormParameters();
			for (String displayName : formParameters.keySet()) {
				List<FormParameter> fs = formParameters.get(displayName);
				if (fs == null || fs.size() == 0) {
					continue;
				}
				FormParameter formParameter = fs.get(0);
				if (formParameter.getType() == ParamType.FILE) {
					params.add(formParameter);
				}
			}
			return params;
		}

		/**
		 * 获取方法名
		 * @param adapter 适配者
		 * @return 方法名
		 */
		public static String getMethodName(Adapter adapter) {

			// 方法的URI
			String methodUri = RamlUtils.getLastUri(adapter.action.getResource().getRelativeUri());

			// 请求类型
			String type = adapter.action.getType().toString().toLowerCase();

			return type + RamlUtils.cap(methodUri);
		}

		/**
		 * 获取请求的实体类名称
		 * @param adapter 适配者
		 * @return 实体类名称
		 */
		public static String getEntityName(Adapter adapter) {
			String type = adapter.action.getType().toString().toLowerCase();
			String methodUri = RamlUtils.getLastUri(adapter.action.getResource().getRelativeUri());
			return RamlUtils.cap(type) + RamlUtils.cap(methodUri) + "Entity";
		}

		/**
		 * 获取响应的实体类名称
		 * @param adapter 适配者
		 * @return 响应的实体类名称
		 */
		public static String getResultEntityName(Adapter adapter) {
			String type = adapter.action.getType().toString().toLowerCase();
			String methodUri = RamlUtils.getLastUri(adapter.action.getResource().getRelativeUri());
			return RamlUtils.cap(type) + RamlUtils.cap(methodUri) + "ResultEntity";
		}

		public static List<String> getClassDefinition(List<JsonParam> jsonParams, String className, int indentLevel) {

			List<String> datas = Lists.newArrayList();

			datas.add("");
			// header
			datas.add(getIndentCharts(indentLevel) + "public static class " + className + " {");

			// 解析
			datas.add("");

			// attribute
			for (int i = 0; i < jsonParams.size(); i++) {
				JsonParam jsonParam = jsonParams.get(i);
				if (StringUtils.equals(jsonParam.getDataType(), JSONUtils.JsonParam.DATA_TYPE_SIMPLE)) {

				}
				datas.add(getIndentCharts(indentLevel + 1) + "/** " + jsonParam.getParam().getDescription() + "*/");
				datas.add(getIndentCharts(indentLevel + 1) + "private " + getJavaTypeName(jsonParam)
						+ (StringUtils.equals(jsonParam.getDataType(), JsonParam.DATA_TYPE_ARRAY) ? "[]" : "") + " " + jsonParam.getParam().getDisplayName()
						+ ";");
			}

			// method
			for (int i = 0; i < jsonParams.size(); i++) {
				JsonParam jsonParam = jsonParams.get(i);

				// getter
				datas.add("");
				datas.add(getIndentCharts(indentLevel + 1) + "public " + getJavaTypeName(jsonParam) + getArrayStr(jsonParam) + " get"
						+ RamlUtils.cap(jsonParam.getParam().getDisplayName()) + "() {");
				datas.add(getIndentCharts(indentLevel + 2) + "return " + jsonParam.getParam().getDisplayName() + ";");
				datas.add(getIndentCharts(indentLevel + 1) + "}");

				// setter
				datas.add("");
				datas.add(getIndentCharts(indentLevel + 1) + "public void set" + RamlUtils.cap(jsonParam.getParam().getDisplayName()) + "("
						+ getJavaTypeName(jsonParam) + getArrayStr(jsonParam) + " " + jsonParam.getParam().getDisplayName() + ") {");
				datas.add(getIndentCharts(indentLevel + 2) + "this." + jsonParam.getParam().getDisplayName() + " = " + jsonParam.getParam().getDisplayName()
						+ ";");
				datas.add(getIndentCharts(indentLevel + 1) + "}");
			}

			// inner class
			for (int i = 0; i < jsonParams.size(); i++) {
				JsonParam jsonParam = jsonParams.get(i);
				if (StringUtils.equals(jsonParam.getDataType(), JsonParam.DATA_TYPE_ARRAY)
						|| StringUtils.equals(jsonParam.getDataType(), JsonParam.DATA_TYPE_OBJECT)) {
					datas.addAll(getClassDefinition(jsonParam.getChildren(), getJavaTypeName(jsonParam), indentLevel + 1));
				}
			}

			// footer
			datas.add(getIndentCharts(indentLevel) + "}");

			return datas;
		}

		/**
		 * 
		 * @param schemaType Schema 中的类型
		 * @return Java类型
		 */
		private static String getJavaTypeName(JsonParam jsonParam) {

			String javaType = null;
			String displayName = jsonParam.getParam().getDisplayName();

			switch (jsonParam.getDataType()) {
			case JsonParam.DATA_TYPE_OBJECT:
				// object
				javaType = RamlUtils.cap(displayName);
				break;
			case JsonParam.DATA_TYPE_ARRAY:
				// array
				if (displayName.endsWith("s")) {
					javaType = RamlUtils.cap(displayName.substring(0, displayName.length() - 1));
				} else if (StringUtils.equalsIgnoreCase(displayName, "list")) {
					javaType = "List";
				} else if (displayName.endsWith("List") || displayName.endsWith("list")) {
					javaType = RamlUtils.cap(displayName.substring(0, displayName.length() - 4));
				} else {
					javaType = RamlUtils.cap(displayName);
				}
				break;
			case JsonParam.DATA_TYPE_SIMPLE:
				// simple
				switch (jsonParam.getParam().getType()) {
				case STRING: // string
					javaType = String.class.getSimpleName();
					break;
				case INTEGER: // integer
					javaType = Integer.class.getSimpleName();
					break;
				case NUMBER: // number
					javaType = Double.class.getSimpleName();
					break;
				case DATE:
					javaType = Date.class.getSimpleName();
					break;
				case BOOLEAN: // boolean
					javaType = Boolean.class.getSimpleName();
					break;
				case FILE:
					javaType = "MultipartFile";
					break;
				}
			}
			return javaType;
		}

		/**
		 * 获取array的字符串[]
		 * @param jsonParam 参数
		 * @return 如果是array,返回[],否则返回空字符串
		 */
		private static String getArrayStr(JsonParam jsonParam) {
			return StringUtils.equals(jsonParam.getDataType(), JsonParam.DATA_TYPE_ARRAY) ? "[]" : "";
		}

	}

}
