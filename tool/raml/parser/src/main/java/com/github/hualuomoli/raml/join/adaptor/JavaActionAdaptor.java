package com.github.hualuomoli.raml.join.adaptor;

import java.math.BigDecimal;
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
import org.springframework.web.multipart.MultipartFile;

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

		datas.add(""); // 换行
		datas.addAll(RamlUtils.getIndentDatas(note, INDENT_CHAR, 1));
		datas.addAll(RamlUtils.getIndentDatas(annotation, INDENT_CHAR, 1));
		datas.addAll(RamlUtils.getIndentDatas(header, INDENT_CHAR, 1));
		datas.addAll(RamlUtils.getIndentDatas(content, INDENT_CHAR, 2));
		datas.addAll(RamlUtils.getIndentDatas(footer, INDENT_CHAR, 1));
		datas.addAll(RamlUtils.getIndentDatas(entityDefinition, INDENT_CHAR, 1));
		datas.addAll(RamlUtils.getIndentDatas(resultEntityDefinition, INDENT_CHAR, 1));

		return datas;
	}

	/**
	 * 获取方法注释
	 * @param adapter 适配者
	 * @return 方法注释
	 */
	protected List<String> getNote(Adapter adapter) {
		List<String> datas = Lists.newArrayList();

		// 实体类的名称
		String entityName = Tool.getEntityName(adapter);

		// start
		datas.add("/**");

		// 实体类定义
		datas.add(" * @param " + RamlUtils.unCap(entityName) + " " + RamlUtils.dealDescription(adapter.action.getDescription()));

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
	protected List<String> getAnnotation(Adapter adapter) {
		List<String> datas = Lists.newArrayList();

		// 请求注解
		String relativeUri = "";
		if (adapter.child) {
			relativeUri = adapter.action.getResource().getRelativeUri();
		}
		String requestAnnonation = "";
		requestAnnonation += "@RequestMapping(" //
				// value
				+ "value = \"" + relativeUri + "\""
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
	protected List<String> getHeader(Adapter adapter) {
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
	protected List<String> getContent(Adapter adapter) {

		List<String> datas = Lists.newArrayList();

		// 增加logger
		String lowerEntityName = RamlUtils.unCap(Tool.getEntityName(adapter));

		datas.add("");
		datas.add("if (logger.isDebugEnabled()) {");
		datas.add(INDENT_CHAR + "logger.debug(\"" + lowerEntityName + " {}\", ToStringBuilder.reflectionToString(" + lowerEntityName + "));");
		datas.add("}");

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
			datas.add("java.util.List<" + resultEntityName + "> data = JsonMapper.fromJsonString(datas,  JsonMapper.getJavaType(java.util.List.class," + resultEntityName + ".class));");
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
	protected List<String> getFooter(Adapter adapter) {
		List<String> datas = Lists.newArrayList();

		datas.add("}");

		return datas;
	}

	/**
	 * 获取方法实体类定义
	 * @param adapter 适配者
	 * @return 方法实体类定义
	 */
	protected abstract List<String> getEntityDefinition(Adapter adapter);

	/**
	 * 获取方法返回实体类定义
	 * @param adapter 适配者
	 * @return 方法返回实体类定义
	 */
	protected List<String> getResultEntityDefinition(Adapter adapter) {

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
		return Tool.getClassDefinition(jsonParams, Tool.getResultEntityName(adapter), 0, false);

	}

	// 工具
	protected static class Tool {

		/**
		 * 是否有返回值
		 * @param adapter 适配者
		 * @return 是否有返回值
		 */
		public static boolean hasResult(Adapter adapter) {
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

		/**
		 * 获取URI的参数
		 * @param adapter 适配者
		 * @return URI的参数
		 */
		public static List<JsonParam> getUriParams(Adapter adapter) {
			List<JsonParam> jsonParams = Lists.newArrayList();
			// URI
			Map<String, UriParameter> uriParameters = RamlUtils.getFullUriParameter(adapter.action);

			if (uriParameters == null || uriParameters.size() == 0) {
				return jsonParams;
			}

			for (AbstractParam param : uriParameters.values()) {
				JsonParam jsonParam = new JsonParam();
				jsonParam.setDataType(JsonParam.DATA_TYPE_SIMPLE);
				jsonParam.setParam(param);
				jsonParams.add(jsonParam);
			}
			return jsonParams;
		}

		/**
		 * 获取Query的参数
		 * @param adapter 适配者
		 * @return Query的参数
		 */
		public static List<JsonParam> getQueryParams(Adapter adapter) {
			List<JsonParam> jsonParams = Lists.newArrayList();

			Map<String, QueryParameter> queryParameters = adapter.action.getQueryParameters();

			if (queryParameters == null || queryParameters.size() == 0) {
				return jsonParams;
			}

			for (AbstractParam param : queryParameters.values()) {
				JsonParam jsonParam = new JsonParam();
				jsonParam.setDataType(JsonParam.DATA_TYPE_SIMPLE);
				jsonParam.setParam(param);
				jsonParams.add(jsonParam);
			}
			return jsonParams;
		}

		/**
		 * 获取Form的参数
		 * @param adapter 适配者
		 * @param formMimeType 请求的协议类型
		 * @return Form的参数
		 */
		public static List<JsonParam> getFormParams(Adapter adapter, String formMimeType) {
			List<JsonParam> jsonParams = Lists.newArrayList();

			Map<String, MimeType> body = adapter.action.getBody();
			if (body == null) {
				return jsonParams;
			}
			MimeType mimeType = body.get(formMimeType);
			if (mimeType == null) {
				return jsonParams;
			}

			Map<String, List<FormParameter>> formParameters = mimeType.getFormParameters();
			for (String displayName : formParameters.keySet()) {
				List<FormParameter> list = formParameters.get(displayName);
				if (list == null || list.size() == 0) {
					continue;
				}
				FormParameter formParameter = list.get(0);
				JsonParam jsonParam = new JsonParam();
				jsonParam.setDataType(JsonParam.DATA_TYPE_SIMPLE);
				jsonParam.setParam(formParameter);
				jsonParams.add(jsonParam);
			}

			return jsonParams;
		}

		/**
		 * 获取类的定义
		 * @param jsonParams 例子参数
		 * @param className 类名
		 * @param indentLevel 缩进级别,默认为0
		 * @param request 是否为请求
		 * @return 类的定义
		 */
		public static List<String> getClassDefinition(List<JsonParam> jsonParams, String className, int indentLevel, boolean request) {

			List<String> datas = Lists.newArrayList();

			datas.add("");
			// header
			datas.add(RamlUtils.getIndentCharts(INDENT_CHAR, indentLevel) + "public static class " + className + (request ? " implements EntityValidator" : "") + " {");

			// 解析
			datas.add("");

			// attribute
			for (int i = 0; i < jsonParams.size(); i++) {
				JsonParam jsonParam = jsonParams.get(i);
				if (jsonParam.getDataType() == JSONUtils.JsonParam.DATA_TYPE_SIMPLE) {

				}
				datas.add(RamlUtils.getIndentCharts(INDENT_CHAR, indentLevel + 1) + "/** " + jsonParam.getParam().getDescription() + "*/");
				// 注解
				if (request) {
					List<String> valids = Tool.getValid(jsonParam);
					for (String valid : valids) {
						datas.add(RamlUtils.getIndentCharts(INDENT_CHAR, indentLevel + 1) + valid);
					}
				}
				datas.add(RamlUtils.getIndentCharts(INDENT_CHAR, indentLevel + 1) + "private " + getJavaTypeName(jsonParam) + (jsonParam.getDataType() == JsonParam.DATA_TYPE_ARRAY ? "[]" : "") + " " + jsonParam.getParam().getDisplayName()
						+ ";");
			}

			// method
			for (int i = 0; i < jsonParams.size(); i++) {
				JsonParam jsonParam = jsonParams.get(i);

				// getter
				datas.add("");
				datas.add(RamlUtils.getIndentCharts(INDENT_CHAR, indentLevel + 1) + "public " + getJavaTypeName(jsonParam) + getArrayStr(jsonParam) + " get" + RamlUtils.cap(jsonParam.getParam().getDisplayName()) + "() {");
				datas.add(RamlUtils.getIndentCharts(INDENT_CHAR, indentLevel + 2) + "return " + jsonParam.getParam().getDisplayName() + ";");
				datas.add(RamlUtils.getIndentCharts(INDENT_CHAR, indentLevel + 1) + "}");

				// setter
				datas.add("");
				datas.add(RamlUtils.getIndentCharts(INDENT_CHAR, indentLevel + 1) + "public void set" + RamlUtils.cap(jsonParam.getParam().getDisplayName()) + "(" + getJavaTypeName(jsonParam) + getArrayStr(jsonParam) + " "
						+ jsonParam.getParam().getDisplayName() + ") {");
				datas.add(RamlUtils.getIndentCharts(INDENT_CHAR, indentLevel + 2) + "this." + jsonParam.getParam().getDisplayName() + " = " + jsonParam.getParam().getDisplayName() + ";");
				datas.add(RamlUtils.getIndentCharts(INDENT_CHAR, indentLevel + 1) + "}");
			}

			// inner class
			for (int i = 0; i < jsonParams.size(); i++) {
				JsonParam jsonParam = jsonParams.get(i);
				if (jsonParam.getDataType() == JsonParam.DATA_TYPE_ARRAY || jsonParam.getDataType() == JsonParam.DATA_TYPE_OBJECT) {
					datas.addAll(getClassDefinition(jsonParam.getChildren(), getJavaTypeName(jsonParam), indentLevel + 1, request));
				}
			}

			// footer
			datas.add(RamlUtils.getIndentCharts(INDENT_CHAR, indentLevel) + "}");

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
				javaType = getSimpleJavaTypeName(jsonParam).getSimpleName();
				break;
			}
			return javaType;
		}

		/**
		 * 获取简单的java类型
		 * @param schemaType Schema 中的类型
		 * @return Java类型
		 */

		private static Class<?> getSimpleJavaTypeName(JsonParam jsonParam) {

			if (jsonParam.getDataType() != JsonParam.DATA_TYPE_SIMPLE) {
				return null;
			}

			// simple
			switch (jsonParam.getParam().getType()) {
			case STRING: // string
				return String.class;
			case INTEGER: // integer
				// max
				BigDecimal maxinum = jsonParam.getParam().getMaximum();
				if (maxinum != null && maxinum.longValue() > Integer.MAX_VALUE) {
					return Long.class;
				}
				return Integer.class;
			case NUMBER: // number
				return Double.class;
			case DATE:
				return Date.class;
			case BOOLEAN: // boolean
				return Boolean.class;
			case FILE:
				return MultipartFile.class;
			}
			return null;
		}

		/**
		 * 获取array的字符串[]
		 * @param jsonParam 参数
		 * @return 如果是array,返回[],否则返回空字符串
		 */
		private static String getArrayStr(JsonParam jsonParam) {
			return jsonParam.getDataType() == JsonParam.DATA_TYPE_ARRAY ? "[]" : "";
		}

		/**
		 * 获取校验规则
		 * @param jsonParam 参数
		 * @return 校验规则
		 */
		public static List<String> getValid(JsonParam jsonParam) {
			List<String> valids = Lists.newArrayList();

			String notNull = Valid.getNotNull(jsonParam);
			String notBlank = Valid.getNotBlank(jsonParam);
			String notEmpty = Valid.getNotEmpty(jsonParam);
			String length = Valid.getLength(jsonParam);
			String min = Valid.getMin(jsonParam);
			String max = Valid.getMax(jsonParam);
			String pattern = Valid.getPattern(jsonParam);

			if (notNull != null) {
				valids.add(notNull);
			}
			if (notBlank != null) {
				valids.add(notBlank);
			}
			if (notEmpty != null) {
				valids.add(notEmpty);
			}
			if (length != null) {
				valids.add(length);
			}
			if (min != null) {
				valids.add(min);
			}
			if (max != null) {
				valids.add(max);
			}
			if (pattern != null) {
				valids.add(pattern);
			}

			return valids;
		}

		// 有效性注解
		static class Valid {

			/**
			 * 是否必填
			 * @param jsonParam
			 * @return
			 */
			private static boolean isRequired(JsonParam jsonParam) {
				return jsonParam.getParam().isRequired();
			}

			/**
			 * 是否必填
			 * @param jsonParam
			 * @return
			 */
			private static boolean isDate(JsonParam jsonParam) {
				if (jsonParam.getDataType() == JsonParam.DATA_TYPE_SIMPLE && jsonParam.getParam().getType() == ParamType.STRING && StringUtils.isNotBlank(jsonParam.getParam().getPattern())) {
					String description = jsonParam.getParam().getDescription();
					if (StringUtils.contains(description, "时间") || StringUtils.contains(description, "日期")) {
						String pattern = jsonParam.getParam().getPattern();

						Set<String> dateFormats = Sets.newHashSet(new String[] { //
								"yyyy-MM-dd", //
								"yyyy-MM-dd hh:mm:ss", //
								"yyyy/MM/dd", //
								"yyyy/MM/dd hh:mm:ss", //
								"yyyyMMdd", //
								"yyyyMMddhhmmss", //
						});
						if (dateFormats.contains(pattern)) {
							return true;
						}
					}

				}
				return false;
			}

			// 不能为空
			// @NotNull(message = "")
			public static String getNotNull(JsonParam jsonParam) {
				if (!isRequired(jsonParam)) {
					return null;
				}
				return "@NotNull(message = \"" + jsonParam.getParam().getDescription() + "不能为空\")";
			}

			// 不能为空(字符串)
			// @NotBlank(message = "")
			public static String getNotBlank(JsonParam jsonParam) {
				if (isDate(jsonParam)) { // 日期字符串不处理
					return null;
				}
				if (jsonParam.getDataType() == JsonParam.DATA_TYPE_SIMPLE && jsonParam.getParam().getType() == ParamType.STRING) {
					return "@NotBlank(message = \"" + jsonParam.getParam().getDescription() + "不能为空\")";
				}
				return null;
			}

			// 必填选项(集合,Map)
			// @NotEmpty(message = "必填选项")
			public static String getNotEmpty(JsonParam jsonParam) {
				if (jsonParam.getDataType() == JsonParam.DATA_TYPE_ARRAY) {
					return "@NotEmpty(message = \"" + jsonParam.getParam().getDescription() + "必填选项\")";
				}
				return null;
			}

			// 长度限制
			// @Length(min = 1, max = 5, message = "用户名长度在1-5之间")
			public static String getLength(JsonParam jsonParam) {
				if (isDate(jsonParam)) {// 日期字符串不处理
					return null;
				}
				if (jsonParam.getDataType() == JsonParam.DATA_TYPE_SIMPLE && jsonParam.getParam().getType() == ParamType.STRING) {
					AbstractParam param = jsonParam.getParam();
					Integer minLength = param.getMinLength();
					Integer maxLength = param.getMaxLength();

					if (minLength == null && maxLength == null) {
						return null;
					}

					int min = minLength == null ? 0 : minLength.intValue();
					int max = maxLength == null ? 0 : maxLength.intValue();

					if (min > 0 && max == 0) {
						// 只设置了最小长度
						// @Length(min = 1, message = "数据长度不能小于1")
						return "@Length(min = " + min + ", message = \"数据长度不能小于" + min + "\")";
					} else if (min == 0 && max > 0) {
						// 只设置了最大长度
						// @Length(max = 5, message = "数据长度不能大于5")
						return "@Length(max = " + max + ", message = \"数据长度不能大于" + max + "\")";
					} else if (min > 0 && max > 0) {
						// 设置了最小长度和最大长度
						// @Length(min = 1, max = 5, message = "用户名长度在1-5之间")
						return "@Length(min = " + min + ", max = " + max + ", message = \"用户名长度在" + min + "-" + max + "之间\")";
					}
				}
				return null;
			}

			// 设置了最小值
			// @Min(value = 1, message = "")
			public static String getMin(JsonParam jsonParam) {
				if (jsonParam.getDataType() == JsonParam.DATA_TYPE_SIMPLE && (jsonParam.getParam().getType() == ParamType.NUMBER || jsonParam.getParam().getType() == ParamType.INTEGER)) {
					// @Min(value = 1, message = "")
					BigDecimal minimum = jsonParam.getParam().getMinimum();
					if (minimum == null) {
						return null;
					}
					Class<?> cls = getSimpleJavaTypeName(jsonParam);
					if (cls == Integer.class) {
						int min = minimum.intValue();
						return "@Min(value = " + min + ", message = \"最小值" + min + "\")";
					} else if (cls == Long.class) {
						long min = minimum.longValue();
						return "@Min(value = " + min + ", message = \"最小值" + min + "\")";
					} else if (cls == Double.class) {
						double min = minimum.doubleValue();
						return "@Min(value = " + min + ", message = \"最小值" + min + "\")";
					}

					return null;
				}
				return null;
			}

			// 设置了最大值
			// @Max(value = 10, message = "")
			public static String getMax(JsonParam jsonParam) {
				if (jsonParam.getDataType() == JsonParam.DATA_TYPE_SIMPLE && (jsonParam.getParam().getType() == ParamType.NUMBER || jsonParam.getParam().getType() == ParamType.INTEGER)) {
					// @Min(value = 1, message = "")
					BigDecimal maxinum = jsonParam.getParam().getMaximum();
					if (maxinum == null) {
						return null;
					}
					Class<?> cls = getSimpleJavaTypeName(jsonParam);
					if (cls == Integer.class) {
						int max = maxinum.intValue();
						return "@Max(value = " + max + ", message = \"最大值" + max + "\")";
					} else if (cls == Long.class) {
						long max = maxinum.longValue();
						return "@Max(value = " + max + ", message = \"最大值" + max + "\")";
					} else if (cls == Double.class) {
						double max = maxinum.doubleValue();
						return "@Max(value = " + max + ", message = \"最大值" + max + "\")";
					}

					return null;
				}
				return null;
			}

			// 正则表达式
			// @Pattern(regexp = "", message = "")
			public static String getPattern(JsonParam jsonParam) {

				if (jsonParam.getDataType() == JsonParam.DATA_TYPE_SIMPLE && jsonParam.getParam().getType() == ParamType.STRING) {
					String pattern = jsonParam.getParam().getPattern();
					if (StringUtils.isBlank(pattern)) {
						return null;
					}
					if (isDate(jsonParam)) {
						// @DateTimeFormat(pattern = "")
						return "@DateTimeFormat(pattern = \"" + pattern + "\")";
					}
					String regexp = pattern.replaceAll("\\\\", "\\\\\\\\");
					return "@Pattern(regexp = \"" + regexp + "\", message = \"" + jsonParam.getParam().getDescription() + "数据不合法\")";
				}
				return null;
			}

		}

		// end

	}

}
