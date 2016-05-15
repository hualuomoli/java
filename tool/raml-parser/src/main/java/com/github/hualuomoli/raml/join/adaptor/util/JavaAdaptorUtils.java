package com.github.hualuomoli.raml.join.adaptor.util;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.raml.model.ActionType;
import org.raml.model.MimeType;
import org.raml.model.parameter.AbstractParam;
import org.raml.model.parameter.FormParameter;

import com.github.hualuomoli.raml.join.AdaptorAbstract.MethodAdaptor;
import com.github.hualuomoli.raml.join.adaptor.entity.DataType;
import com.github.hualuomoli.raml.join.adaptor.entity.ParamData;
import com.google.common.collect.Lists;

/**
 * 适配器工具类
 * @author hualuomoli
 *
 */
public class JavaAdaptorUtils extends AdaptorUtils implements MethodAdaptor {

	public static final String INDENT_CHAR = "\t";

	/**
	 * 获取请求的实体类名称
	 * @param actionType 请求类型
	 * @param methodUri 方法的URI
	 * @return
	 */
	public static String getEntityName(ActionType actionType, String methodUri) {
		String methodName = getMethodName(actionType, methodUri);
		return cap(methodName) + "Entity";
	}

	/**
	 * 获取响应的实体类名称
	 * @param actionType 请求类型
	 * @param methodUri 方法的URI
	 * @return
	 */
	public static String getResultEntityName(ActionType actionType, String methodUri, ResponseSM responseSM) {
		if (!hasResult(responseSM)) {
			return "void";
		}
		String methodName = getMethodName(actionType, methodUri);
		return cap(methodName) + "ResultEntity";
	}

	/**
	 * 是否有返回结果
	 * @param responseSM 响应
	 * @return 是否有返回结果
	 */
	public static boolean hasResult(ResponseSM responseSM) {
		return responseSM != null && responseSM.responseMimeType != null;
	}

	/**
	 * 获取方法的数据
	 * @param translate 转换实体类
	 * @return 方法的数据
	 */
	public String getMethodData(Translate translate) {
		return getMethodData(translate, new JavaDefaultTransfer());
	}

	/**
	 * 获取方法的数据
	 * @param translate 转换实体类
	 * @param transfer 转换器
	 * @return 方法的数据
	 */
	public String getMethodData(Translate translate, JavaTransfer transfer) {

		List<String> notes = transfer.getMethodNote(translate);
		List<String> annotations = transfer.getMethodAnnotation(translate);
		List<String> headers = transfer.getMethodHeader(translate);
		List<String> footers = transfer.getMethodFooter(translate);
		List<String> contents = transfer.getMethodContent(translate);
		List<List<String>> classDefinitionLists = transfer.getClassDefinition(translate);

		StringBuilder buffer = new StringBuilder();

		// add note
		append(buffer, notes, INDENT_CHAR, 1);

		// add annotation
		append(buffer, annotations, INDENT_CHAR, 1);

		// add header
		append(buffer, headers, INDENT_CHAR, 1);

		// add content
		append(buffer, contents, INDENT_CHAR, 2);

		// add footer
		append(buffer, footers, INDENT_CHAR, 1);

		// add class definition
		if (classDefinitionLists != null && classDefinitionLists.size() > 0) {
			for (List<String> classDefinitions : classDefinitionLists) {
				append(buffer, classDefinitions, INDENT_CHAR, 1);
			}
		}

		return buffer.toString();
	}

	// 转换器
	public static interface JavaTransfer extends Transfer {

		// 方法注解
		List<String> getMethodAnnotation(Translate translate);

		// 方法实体类定义
		List<List<String>> getClassDefinition(Translate translate);
	}

	// 默认转换器
	public static class JavaDefaultTransfer implements JavaTransfer {

		@Override
		public List<String> getMethodNote(Translate translate) {
			List<String> datas = Lists.newArrayList();

			String entityName = getEntityName(translate.actionType, translate.methodUri);

			// start
			datas.add("/**");

			switch (translate.actionType) {
			case GET:
			case DELETE:
				datas.add(" * @param " + unCap(entityName) + " " + getOneLineDescription(translate.action.getDescription()));
				break;
			case POST:
			case PUT:
				switch (translate.formMimeType.getType()) {
				case MIME_TYPE_URLENCODED:
					datas.add(" * @param " + unCap(entityName) + " " + getOneLineDescription(translate.action.getDescription()));
					break;
				case MIME_TYPE_JSON:
					datas.add(" * @param " + unCap(entityName) + " " + getOneLineDescription(translate.action.getDescription()));
					break;
				case MIME_TYPE_MULTIPART:
					// entity
					datas.add(" * @param " + unCap(entityName) + " " + getOneLineDescription(translate.action.getDescription()));
					// files
					Set<FormParameter> params = getFormFileParameters(translate.formMimeType);
					for (AbstractParam param : params) {
						datas.add(" * @param " + param.getDisplayName() + " " + getOneLineDescription(param.getDescription()));
					}
					break;
				}
				break;
			default:
				break;
			}

			datas.add(" * @param request HttpServletRequest");
			datas.add(" * @param response HttpServletResponse");
			datas.add(" * @param model Model");

			// end
			datas.add(" */");

			return datas;
		}

		@Override
		public List<String> getMethodHeader(Translate translate) {
			List<String> datas = Lists.newArrayList();

			String entityName = getEntityName(translate.actionType, translate.methodUri);
			String resultEntityName = getResultEntityName(translate.actionType, translate.methodUri, translate.responseSM);
			String methodName = getMethodName(translate.actionType, translate.methodUri);

			StringBuilder buffer = new StringBuilder();

			buffer.append("public");
			buffer.append(" ");
			buffer.append(resultEntityName);
			buffer.append(" ");
			buffer.append(methodName);
			buffer.append("(");

			switch (translate.actionType) {
			case GET:
			case DELETE:
				buffer.append(entityName).append(" ").append(unCap(entityName));
				break;
			case POST:
			case PUT:
				switch (translate.formMimeType.getType()) {
				case MIME_TYPE_URLENCODED:
					buffer.append(entityName).append(" ").append(unCap(entityName));
					break;
				case MIME_TYPE_JSON:
					buffer.append("@RequestBody ").append(entityName).append(" ").append(unCap(entityName));
					break;
				case MIME_TYPE_MULTIPART:
					// entity
					buffer.append(entityName).append(" ").append(unCap(entityName));
					// files
					Set<FormParameter> params = getFormFileParameters(translate.formMimeType);
					for (AbstractParam param : params) {
						buffer.append(", ");
						// @RequestParam(value = "", required = false)
						buffer.append("@RequestParam(value = \"").append(param.getDisplayName()).append("\"");
						buffer.append(", required = ").append(param.isRequired() ? "true" : "false");
						buffer.append(")");
					}
					break;
				}
				break;
			default:
				break;
			}

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

		@Override
		public List<String> getMethodFooter(Translate translate) {
			List<String> datas = Lists.newArrayList();

			datas.add("}");

			return datas;
		}

		@Override
		public List<String> getMethodContent(Translate translate) {
			List<String> datas = Lists.newArrayList();

			if (!hasResult(translate.responseSM)) {
				return datas;
			}

			String resultExample = getOneLineExample(translate.responseSM.responseMimeType.getExample());
			datas.add(resultExample);

			return datas;
		}

		@Override
		public List<String> getMethodAnnotation(Translate translate) {
			List<String> annotations = Lists.newArrayList();

			// 请求注解
			String requestAnnonation = "";
			requestAnnonation += "@RequestMapping(" //
					// value
					+ "value=\"" + translate.methodUri + "\""
					// split
					+ ", "
					// method
					+ "method = { RequestMethod." + translate.actionType.toString().toUpperCase() + " }";

			// consumes
			if (translate.formMimeType != null) {
				// split
				requestAnnonation += ", "
						//
						+ "consumes = { " + translate.formMimeType.getType() + " }";
			}
			// produces
			if (translate.responseSM != null && translate.responseSM.responseMimeType != null) {
				// split
				requestAnnonation += ", "
						//
						+ "produces = { " + translate.responseSM.responseMimeType.getType() + " }";
			}
			//
			requestAnnonation += ")";

			// 响应注解
			String responseAnnonation = "@ResponseBody";

			// 添加
			annotations.add(requestAnnonation);
			annotations.add(responseAnnonation);

			return annotations;
		}

		@Override
		public List<List<String>> getClassDefinition(Translate translate) {
			List<List<String>> classDefinitions = Lists.newArrayList();
			// query
			classDefinitions.add(getQueryClassDefinition(translate));
			// result
			classDefinitions.add(getResultClassDefinition(translate));
			return classDefinitions;
		}

		// 查询类定义
		private List<String> getQueryClassDefinition(Translate translate) {

			// 参数
			List<ParamData> paramDatas = Lists.newArrayList();
			String className = getEntityName(translate.actionType, translate.methodUri);

			if (translate.actionType == ActionType.GET || translate.actionType == ActionType.DELETE) {
				// get或者delete
				List<AbstractParam> params = Lists.newArrayList();
				params.addAll(translate.fileUriParameters);
				params.addAll(translate.methodUriParameters);
				params.addAll(getQueryParameters(translate.action));

				paramDatas = parseParams(params);
				return getQueryClassDefinition(paramDatas, className);

			} else if (translate.actionType == ActionType.POST || translate.actionType == ActionType.PUT) {
				// put或者post
				if (translate.formMimeType != null && StringUtils.equalsIgnoreCase(translate.formMimeType.getType(), MIME_TYPE_JSON)) {
					paramDatas = getJsonParamData(translate.formMimeType);
					return getJsonClassDefinition(paramDatas, className, INDENT_CHAR);
				}
			}

			return null;
		}

		// 返回类定义
		private List<String> getResultClassDefinition(Translate translate) {

			// 参数
			List<ParamData> paramDatas = Lists.newArrayList();
			String className = getEntityName(translate.actionType, translate.methodUri);

			if (translate.responseSM != null && translate.responseSM.responseMimeType != null) {
				// put或者post
				if (StringUtils.equalsIgnoreCase(translate.responseSM.responseMimeType.getType(), MIME_TYPE_JSON)) {
					paramDatas = getJsonParamData(translate.responseSM.responseMimeType);
					return getJsonClassDefinition(paramDatas, className, INDENT_CHAR);
				}
			}

			return null;
		}

		// 查询类定义
		private List<String> getQueryClassDefinition(List<ParamData> paramDatas, String className) {

			List<String> queryClassDefinition = Lists.newArrayList();

			// header
			queryClassDefinition.add("public static class " + className + " implements EntityValidator {");

			// 解析

			// attribute
			for (int i = 0; i < paramDatas.size(); i++) {
				ParamData paramData = paramDatas.get(i);
				queryClassDefinition.add(INDENT_CHAR + "/** " + paramData.getDescription() + "*/");
				// annotation
				List<String> annotations = getAnnotations(paramData);
				for (int j = 0; j < annotations.size(); j++) {
					queryClassDefinition.add(INDENT_CHAR + annotations.get(j));
				}
				queryClassDefinition.add(INDENT_CHAR + "private " + getJavaTypeName(paramData) + " " + paramData.getDisplayName() + ";");
			}

			// method
			for (int i = 0; i < paramDatas.size(); i++) {
				ParamData paramData = paramDatas.get(i);

				// getter
				queryClassDefinition.add(LINE);
				queryClassDefinition.add(INDENT_CHAR + "public " + getJavaTypeName(paramData) + " get" + cap(paramData.getDisplayName()) + "() {");
				queryClassDefinition.add(INDENT_CHAR + INDENT_CHAR + "return " + paramData.getDisplayName() + ";");
				queryClassDefinition.add(INDENT_CHAR + "}");

				// setter
				queryClassDefinition.add(LINE);
				queryClassDefinition.add(INDENT_CHAR + "public void set" + cap(paramData.getDisplayName()) + "(" + getJavaTypeName(paramData) + " "
						+ paramData.getDisplayName() + ") {");
				queryClassDefinition.add(INDENT_CHAR + INDENT_CHAR + "this." + paramData.getDisplayName() + " = " + paramData.getDisplayName() + ";");
				queryClassDefinition.add(INDENT_CHAR + "}");
			}

			// footer
			queryClassDefinition.add("}");

			return queryClassDefinition;

		}

		// 查询类定义
		private List<String> getJsonClassDefinition(List<ParamData> paramDatas, String className, String indentChars) {

			List<String> queryClassDefinition = Lists.newArrayList();

			// header
			queryClassDefinition.add("public static class " + className + " {");

			// 解析

			// attribute
			for (int i = 0; i < paramDatas.size(); i++) {
				ParamData paramData = paramDatas.get(i);
				queryClassDefinition.add(indentChars + "/** " + paramData.getDescription() + "*/");
				queryClassDefinition.add(indentChars + "private " + getJavaTypeName(paramData) + " " + paramData.getDisplayName() + ";");
			}

			// method
			for (int i = 0; i < paramDatas.size(); i++) {
				ParamData paramData = paramDatas.get(i);

				// getter
				queryClassDefinition.add(LINE);
				queryClassDefinition.add(indentChars + "public " + getJavaTypeName(paramData) + " get" + cap(paramData.getDisplayName()) + "() {");
				queryClassDefinition.add(indentChars + indentChars + "return " + paramData.getDisplayName() + ";");
				queryClassDefinition.add(indentChars + "}");

				// setter
				queryClassDefinition.add(LINE);
				queryClassDefinition.add(indentChars + "public void set" + cap(paramData.getDisplayName()) + "(" + getJavaTypeName(paramData) + " "
						+ paramData.getDisplayName() + ") {");
				queryClassDefinition.add(indentChars + indentChars + "this." + paramData.getDisplayName() + " = " + paramData.getDisplayName() + ";");
				queryClassDefinition.add(indentChars + "}");
			}

			// inner class
			for (int i = 0; i < paramDatas.size(); i++) {
				ParamData paramData = paramDatas.get(i);
				if (paramData.getDataType() == DataType.OBJECT || paramData.getDataType() == DataType.ARRAY) {
					queryClassDefinition.add(LINE);
					queryClassDefinition.addAll(getJsonClassDefinition(paramData.getChildren(), getJavaTypeName(paramData), indentChars + INDENT_CHAR));
					queryClassDefinition.add(LINE);
				}
			}

			// footer
			queryClassDefinition.add("}");

			return queryClassDefinition;

		}

		// 获取注解
		private static List<String> getAnnotations(ParamData paramData) {

			List<String> annotations = Lists.newArrayList();

			switch (paramData.getDataType()) {
			case OBJECT:
				// object
				if (paramData.isRequired()) {
					annotations.add(RuleAnnotation.getNotEmpty(paramData));
				}
				break;
			case ARRAY:
				// array
				if (paramData.isRequired()) {
					annotations.add(RuleAnnotation.getNotEmpty(paramData));
				}
				break;
			case SIMPLE:
				// simple
				switch (paramData.getType()) {
				case STRING: // string
					if (paramData.isRequired()) {
						annotations.add(RuleAnnotation.getNotNull(paramData));
					}
					if (paramData.getMinLength() > 0 || paramData.getMaxLength() > 0) {
						annotations.add(RuleAnnotation.getLength(paramData));
					}
					if (StringUtils.isNotEmpty(paramData.getPattern())) {
						annotations.add(RuleAnnotation.getPattern(paramData));
					}
					break;
				case INTEGER: // integer
					if (paramData.isRequired()) {
						annotations.add(RuleAnnotation.getNotNull(paramData));
					}
					if (paramData.getMinimum() != null) {
						annotations.add(RuleAnnotation.getMin(paramData));
					}
					if (paramData.getMaximum() != null) {
						annotations.add(RuleAnnotation.getMax(paramData));
					}
					break;
				case NUMBER: // number
					if (paramData.isRequired()) {
						annotations.add(RuleAnnotation.getNotNull(paramData));
					}
					break;
				case DATE:
					if (paramData.isRequired()) {
						annotations.add(RuleAnnotation.getNotNull(paramData));
					}
					annotations.add(RuleAnnotation.getDateTimeFormat(paramData));
					break;
				case BOOLEAN: // boolean
					if (paramData.isRequired()) {
						annotations.add(RuleAnnotation.getNotNull(paramData));
					}
					break;
				case FILE:
					if (paramData.isRequired()) {
						annotations.add(RuleAnnotation.getNotNull(paramData));
					}
					break;
				}
			}
			return annotations;
		}

		// 有效性header
		static class RuleAnnotation {

			// 必填选项(字符串,集合,Map,数组)
			// @NotEmpty(message = "必填选项")
			public static String getNotEmpty(ParamData paramData) {
				StringBuilder buffer = new StringBuilder();
				buffer.append("@NotEmpty");
				buffer.append("(");
				buffer.append("message = \"");
				buffer.append(paramData.getDescription()).append("不能为空");
				buffer.append("\")");
				return buffer.toString();
			}

			// 必填选项
			// @NotNull(message = "")
			public static String getNotNull(ParamData paramData) {
				StringBuilder buffer = new StringBuilder();
				buffer.append("@NotNull");
				buffer.append("(");
				buffer.append("message = \"");
				buffer.append(paramData.getDescription()).append("不能为空");
				buffer.append("\")");
				return buffer.toString();
			}

			// 长度限制
			// @Length(min = 1, max = 5, message = "用户名长度在1-5之间")
			public static String getLength(ParamData paramData) {
				StringBuilder buffer = new StringBuilder();
				if (paramData.getMaxLength() == null) {
					// 只设置了最小长度
					// @Length(min = 1, message = "数据长度不能小于1")
					buffer.append("@Length");
					buffer.append("(");
					// 最小值
					buffer.append("min = ").append(paramData.getMinLength()).append(", ");
					buffer.append("message = \"");
					buffer.append(paramData.getDescription()).append("长度不能小于").append(paramData.getMinLength());
					buffer.append("\")");
				} else if (paramData.getMinLength() == null) {
					// 只设置了最大长度
					// @Length(max = 5, message = "数据长度不能大于5")
					buffer.append("@Length");
					buffer.append("(");
					buffer.append("max = ").append(paramData.getMaxLength()).append(", ");
					buffer.append("message = \"");
					buffer.append(paramData.getDescription()).append("长度不能大于").append(paramData.getMaxLength());
					buffer.append("\")");
				} else {
					// 设置了最小长度和最大长度
					// @Length(min = 1, max = 5, message = "用户名长度在1-5之间")
					buffer.append("@Length");
					buffer.append("(");
					buffer.append("min = ").append(paramData.getMinLength()).append(", ");
					buffer.append("max = ").append(paramData.getMaxLength()).append(", ");
					buffer.append("message = \"");
					buffer.append(paramData.getDescription());
					buffer.append("长度在").append(paramData.getMinLength()).append("-").append(paramData.getMaxLength()).append("之间");
					buffer.append("\")");
				}
				return buffer.toString();
			}

			// 设置了最小值
			// @Min(value = 1, message = "")
			public static String getMin(ParamData paramData) {
				StringBuilder buffer = new StringBuilder();
				buffer.append("@Min");
				buffer.append("(");
				buffer.append("value = ").append(paramData.getMinimum().intValue()).append(", ");
				buffer.append("message = \"");
				buffer.append(paramData.getDescription()).append("长度不能小于").append(paramData.getMinimum().intValue());
				buffer.append("\")");
				return buffer.toString();
			}

			// 设置了最大值
			// @Max(value = 10, message = "")
			public static String getMax(ParamData paramData) {
				StringBuilder buffer = new StringBuilder();
				buffer.append("@Max");
				buffer.append("(");
				buffer.append("value = ").append(paramData.getMaximum().intValue()).append(", ");
				buffer.append("message = \"");
				buffer.append(paramData.getDescription()).append("长度不能大于").append(paramData.getMaximum().intValue());
				buffer.append("\")");
				return buffer.toString();
			}

			// 正则表达式
			// @Pattern(regexp = "", message = "")
			public static String getPattern(ParamData paramData) {
				StringBuilder buffer = new StringBuilder();

				String pattern = paramData.getPattern();
				String regexp = StringUtils.replace(pattern, "\\", "\\\\").replaceAll("\"", "\\\\\"");

				// replace
				buffer.append("@Pattern");
				buffer.append("(");
				buffer.append("regexp = \"");
				buffer.append(regexp);
				buffer.append("\", ");
				buffer.append("message = \"");
				buffer.append("请设置合法的").append(paramData.getDescription());
				buffer.append("\")");
				return buffer.toString();
			}

			// 日期
			// @DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")
			public static String getDateTimeFormat(ParamData paramData) {
				StringBuilder buffer = new StringBuilder();

				String pattern = DateFormat.getPattern(paramData.getExample());

				buffer.append("@DateTimeFormat");
				buffer.append("(");
				buffer.append("pattern = \"");
				buffer.append(pattern);
				buffer.append("\")");
				return buffer.toString();
			}

			// 日期格式化
			static class DateFormat {

				private static String regex1 = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
				private static String regex2 = "\\d{4}-\\d{2}-\\d{2}";
				private static String regex3 = "\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}";
				private static String regex4 = "\\d{4}/\\d{2}/\\d{2}";
				private static String regex5 = "\\d{14}";
				private static String regex6 = "\\d8}";

				private static String pattern1 = "yyyy-MM-dd kk:mm:ss";
				private static String pattern2 = "yyyy-MM-dd";
				private static String pattern3 = "yyyy/MM/dd kk:mm:ss";
				private static String pattern4 = "yyyy/MM/dd";
				private static String pattern5 = "yyyyMMddkkmmss";
				private static String pattern6 = "yyyyMMdd";

				private static String pattern = pattern1;

				static List<String> regexes = Lists.newArrayList(new String[] { //
						regex1, //
						regex2, //
						regex3, //
						regex4, //
						regex5, //
						regex6 //
				});
				static List<String> patterns = Lists.newArrayList(new String[] { //
						pattern1, //
						pattern2, //
						pattern3, //
						pattern4, //
						pattern5, //
						pattern6 //
				});

				public static String getPattern(String date) {
					if (StringUtils.isBlank(date)) {
						return pattern;
					}
					for (int i = 0; i < regexes.size(); i++) {
						String regex = regexes.get(i);
						if (date.matches(regex)) {
							return patterns.get(i);
						}
					}
					return pattern;
				}

			}
		}

		/**
		 * 
		 * @param schemaType Schema 中的类型
		 * @return Java类型
		 */
		private static String getJavaTypeName(ParamData paramData) {

			String javaType = null;
			String displayName = paramData.getDisplayName();

			switch (paramData.getDataType()) {
			case OBJECT:
				// object
				javaType = displayName.substring(0, 1).toUpperCase() + displayName.substring(1);
				break;
			case ARRAY:
				// array
				if (displayName.endsWith("s")) {
					javaType = displayName.substring(0, displayName.length() - 1);
				} else if (displayName.endsWith("List") || displayName.endsWith("list")) {
					javaType = displayName.substring(0, displayName.length() - 4);
				} else {
					javaType = displayName;
				}
				break;
			case SIMPLE:
				// simple
				switch (paramData.getType()) {
				case STRING: // string
					javaType = String.class.getName();
					break;
				case INTEGER: // integer
					javaType = Integer.class.getName();
					break;
				case NUMBER: // number
					javaType = Double.class.getName();
					break;
				case DATE:
					javaType = Date.class.getName();
					break;
				case BOOLEAN: // boolean
					javaType = Boolean.class.getName();
					break;
				case FILE:
					javaType = "MultipartFile";
					break;
				}
			}
			return javaType;
		}

		// get param data from schema or example
		private List<ParamData> getJsonParamData(MimeType mimeType) {
			String schema = mimeType.getSchema();
			String example = mimeType.getExample();

			List<ParamData> paramDatas;

			if (StringUtils.isNotBlank(schema)) {
				paramDatas = JSONUtils.parseSchema(schema);
			} else if (StringUtils.isNotBlank(example)) {
				paramDatas = JSONUtils.parseExample(example);
			} else {
				paramDatas = Lists.newArrayList();
			}
			return paramDatas;
		}

	}

}
