package com.github.hualuomoli.raml.parser.join.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.raml.model.Action;
import org.raml.model.MimeType;
import org.raml.model.ParamType;
import org.raml.model.Resource;
import org.raml.model.parameter.AbstractParam;
import org.raml.model.parameter.FormParameter;
import org.raml.model.parameter.UriParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.raml.parser.exception.ParseException;
import com.github.hualuomoli.raml.parser.join.Join;
import com.github.hualuomoli.raml.parser.util.RamlUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * 解析java工具
 * @author hualuomoli
 *
 */
public class JavaRamlUtils implements Join {

	private static final Logger logger = LoggerFactory.getLogger(JavaRamlUtils.class);

	/**
	 * 获取方法名称
	 * @param relativeUri 相对URI
	 * @param action action
	 * @return 方法的名称
	 */
	public static String getMethodName(String relativeUri, Action action) {

		StringBuilder buffer = new StringBuilder();

		// get post put delete
		buffer.append(action.getType().toString().toLowerCase());

		// list info
		String uri = RamlUtils.trimUriParam(relativeUri);
		if (StringUtils.isNotBlank(uri)) {
			buffer.append(uri.substring(1, 2).toUpperCase());
			buffer.append(uri.substring(2));
		}

		return buffer.toString();
	}

	// 方法的URI参数
	public static Set<MethodParam> getUriMethodParams(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException {
		Set<MethodParam> methodParams = Sets.newHashSet();

		// @param username 用户名
		if (parentFullUriParameters == null || parentFullUriParameters.size() == 0) {
			return methodParams;
		}

		for (UriParameter uriParameter : parentFullUriParameters.values()) {
			methodParams.add(new MethodParam(uriParameter));
		}

		return methodParams;
	}

	// 方法的File参数
	public static Set<MethodParam> getFileMethodParams(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException {
		Set<MethodParam> methodParams = Sets.newHashSet();
		// @RequestParam(value = "photo", required = true) File photo

		if (responseMimeType == null || responseMimeType.getFormParameters() == null || responseMimeType.getFormParameters().size() == 0) {
			return methodParams;
		}

		Map<String, List<FormParameter>> formParameters = requestMimeType.getFormParameters();
		for (String displayName : formParameters.keySet()) {
			List<FormParameter> list = formParameters.get(displayName);
			for (FormParameter formParameter : list) {
				if (formParameter.getType() == ParamType.FILE) {
					String name = formParameter.getDisplayName();
					String type = "MultipartFile";
					String anno = new StringBuilder() //
							.append("@RequestParam(value = ").append(QUOTES).append(name).append(QUOTES)//
							.append(", required = ").append(formParameter.isRequired() ? "true" : "false").append(")") //
							.toString();

					methodParams.add(new MethodParam(anno, type, name));
				}
			}
		}

		return methodParams;
	}

	// 方法的参数
	public static class MethodParam {

		public String anno; // 注解描述 @PathVariable(value= "usrname")
		public String type;
		public String name;

		public MethodParam() {
		}

		public MethodParam(String anno, String type, String name) {
			this.anno = anno;
			this.type = type;
			this.name = name;
		}

		public MethodParam(AbstractParam param) throws ParseException {
			String type = null;
			switch (param.getType()) {
			case STRING:
				type = "String";
				break;
			case BOOLEAN:
				type = "Boolean";
				break;
			case INTEGER:
				type = "Integer";
				break;
			case DATE:
				type = "Date";
				break;
			// case NUMBER:
			// case FILE:
			default:
				logger.warn("can not support type {}", param.getType());
			}
			this.type = type;
			this.name = param.getDisplayName();
			this.anno = new StringBuilder() //
					.append("@PathVariable(value=").append(QUOTES).append(this.name).append(QUOTES).append(")") //
					.toString();
		}

	}

	// URI参数
	public static Set<Note> getUriNotes(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource) {
		Set<Note> notes = Sets.newHashSet();
		// @param username 用户名
		if (parentFullUriParameters == null || parentFullUriParameters.size() == 0) {
			return notes;
		}

		for (UriParameter uriParameter : parentFullUriParameters.values()) {
			notes.add(new Note(uriParameter));
		}

		return notes;
	}

	// File参数
	public static Set<Note> getFileParameterNotes(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource) {
		Set<Note> notes = Sets.newHashSet();

		// @param photo 头像
		if (requestMimeType == null || requestMimeType.getFormParameters() == null || requestMimeType.getFormParameters().size() == 0) {
			return notes;
		}
		Map<String, List<FormParameter>> formParameters = requestMimeType.getFormParameters();
		for (String displayName : formParameters.keySet()) {
			List<FormParameter> list = formParameters.get(displayName);
			for (FormParameter formParameter : list) {
				if (formParameter.getType() == ParamType.FILE) {
					notes.add(new Note(formParameter));
				}
			}
		}

		return notes;
	}

	// 注释
	public static class Note {

		public String param;
		public String description;

		public Note() {
		}

		public Note(String param, String description) {
			this.param = param;
			this.description = description;
		}

		public Note(AbstractParam param) {
			this.param = param.getDisplayName();
			this.description = param.getDescription();
		}

	}

	// 获取方法的属性
	public static List<ClassAttribute> getClassAttributes(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource) {

		List<ClassAttribute> attributes = Lists.newArrayList();

		// URI
		if (parentFullUriParameters != null && parentFullUriParameters.size() > 0) {
			for (AbstractParam param : parentFullUriParameters.values()) {
				attributes.add(new ClassAttribute(param));
			}
		}

		// query
		if (action.getQueryParameters() != null && action.getQueryParameters().size() > 0) {
			for (AbstractParam param : action.getQueryParameters().values()) {
				attributes.add(new ClassAttribute(param));
			}
		}

		// form
		if (responseMimeType != null && responseMimeType.getFormParameters() != null && responseMimeType.getFormParameters().size() > 0) {
			Map<String, List<FormParameter>> formParameters = responseMimeType.getFormParameters();
			for (String displayName : formParameters.keySet()) {
				List<FormParameter> list = formParameters.get(displayName);
				for (AbstractParam param : list) {
					attributes.add(new ClassAttribute(param));
				}
			}
		}

		return attributes;

	}

	// 注释
	public static class ClassAttribute {

		public List<String> annos; // 注解描述 @Length(min = 1, max = 10, message="")
		public String type;
		public String name;
		public String desc;

		public ClassAttribute() {
		}

		public ClassAttribute(List<String> annos, String type, String name, String desc) {
			this.annos = annos;
			this.type = type;
			this.name = name;
			this.desc = desc;
		}

		public ClassAttribute(AbstractParam param) {
			String type = null;
			List<String> annos = Lists.newArrayList();
			// @NotNull(message = "")
			if (param.isRequired()) {
				annos.add(ValidHeader.getNotNull(param));
			}
			switch (param.getType()) {
			case STRING:
				type = "String";
				// @NotEmpty(message = "")
				if (param.isRequired()) {
					annos.add(ValidHeader.getNotEmpty(param));
				}
				// @Length(min = 1, max = 5, message = "用户名长度在1-5之间")
				if (param.getMinLength() != null || param.getMaxLength() != null) {
					annos.add(ValidHeader.getLength(param));
				}
				// @Pattern(regexp = "", message = "")
				if (StringUtils.isNotEmpty(param.getPattern())) {
					annos.add(ValidHeader.getPattern(param));
				}
				break;
			case BOOLEAN:
				type = "Boolean";
				break;
			case INTEGER:
				type = "Integer";
				// @Min(value = 1, message = "")
				// @Max(value = 10, message = "")
				if (param.getMinimum() != null) {
					annos.add(ValidHeader.getMin(param));
				}
				if (param.getMaximum() != null) {
					annos.add(ValidHeader.getMax(param));
				}
				break;
			case DATE:
				type = "Date";
				// @Pattern(regexp = "", message = "")
				if (StringUtils.isNotEmpty(param.getExample())) {
					annos.add(ValidHeader.getDateTimeFormat(param));
				}
				break;
			// case NUMBER:
			case FILE:
				type = "MultipartFile";
				break;
			default:
				logger.warn("can not support type {}", param.getType());
			}
			this.annos = annos;
			this.type = type;
			this.name = param.getDisplayName();
			this.desc = RamlUtils.dealDescription(param.getDescription());
		}

	}

	// 有效性header
	static class ValidHeader {

		// 必填选项
		// @NotNull(message = "")
		public static String getNotNull(AbstractParam param) {
			StringBuilder buffer = new StringBuilder();
			buffer.append("@NotNull");
			buffer.append("(");
			buffer.append("message = ");
			buffer.append(QUOTES);
			buffer.append(param.getDescription()).append("不能为空");
			buffer.append(QUOTES);
			buffer.append(")");
			return buffer.toString();
		}

		// 必填选项(字符串,集合,Map,数组)
		// @NotEmpty(message = "必填选项")
		public static String getNotEmpty(AbstractParam param) {
			StringBuilder buffer = new StringBuilder();
			buffer.append("@NotEmpty");
			buffer.append("(");
			buffer.append("message = ");
			buffer.append(QUOTES);
			buffer.append(param.getDescription()).append("不能为空");
			buffer.append(QUOTES);
			buffer.append(")");
			return buffer.toString();
		}

		// 长度限制
		// @Length(min = 1, max = 5, message = "用户名长度在1-5之间")
		public static String getLength(AbstractParam param) {
			StringBuilder buffer = new StringBuilder();
			if (param.getMaxLength() == null) {
				// 只设置了最小长度
				// @Length(min = 1, message = "数据长度不能小于1")
				buffer.append("@Length");
				buffer.append("(");
				// 最小值
				buffer.append("min = ").append(param.getMinLength()).append(", ");
				buffer.append("message = ");
				buffer.append(QUOTES);
				buffer.append(param.getDescription()).append("长度不能小于").append(param.getMinLength());
				buffer.append(QUOTES);
				buffer.append(")");
			} else if (param.getMinLength() == null) {
				// 只设置了最大长度
				// @Length(max = 5, message = "数据长度不能大于5")
				buffer.append("@Length");
				buffer.append("(");
				buffer.append("max = ").append(param.getMaxLength()).append(", ");
				buffer.append("message = ");
				buffer.append(QUOTES);
				buffer.append(param.getDescription()).append("长度不能大于").append(param.getMaxLength());
				buffer.append(QUOTES);
				buffer.append(")");
			} else {
				// 设置了最小长度和最大长度
				// @Length(min = 1, max = 5, message = "用户名长度在1-5之间")
				buffer.append("@Length");
				buffer.append("(");
				buffer.append("min = ").append(param.getMinLength()).append(", ");
				buffer.append("max = ").append(param.getMaxLength()).append(", ");
				buffer.append("message = ");
				buffer.append(QUOTES);
				buffer.append(param.getDescription());
				buffer.append("长度在").append(param.getMinLength()).append("-").append(param.getMaxLength()).append("之间");
				buffer.append(QUOTES);
				buffer.append(")");
			}
			return buffer.toString();
		}

		// 设置了最小值
		// @Min(value = 1, message = "")
		public static String getMin(AbstractParam param) {
			StringBuilder buffer = new StringBuilder();
			buffer.append("@Min");
			buffer.append("(");
			buffer.append("value = ").append(param.getMinimum().intValue()).append(", ");
			buffer.append("message = ");
			buffer.append(QUOTES);
			buffer.append(param.getDescription()).append("长度不能小于").append(param.getMinimum().intValue());
			buffer.append(QUOTES);
			buffer.append(")");
			return buffer.toString();
		}

		// 设置了最大值
		// @Max(value = 10, message = "")
		public static String getMax(AbstractParam param) {
			StringBuilder buffer = new StringBuilder();
			buffer.append("@Max");
			buffer.append("(");
			buffer.append("value = ").append(param.getMaximum().intValue()).append(", ");
			buffer.append("message = ");
			buffer.append(QUOTES);
			buffer.append(param.getDescription()).append("长度不能大于").append(param.getMaximum().intValue());
			buffer.append(QUOTES);
			buffer.append(")");
			return buffer.toString();
		}

		// 正则表达式
		// @Pattern(regexp = "", message = "")
		public static String getPattern(AbstractParam param) {
			StringBuilder buffer = new StringBuilder();

			String pattern = param.getPattern();
			String regexp = StringUtils.replace(pattern, "\\", "\\\\");

			// replace
			buffer.append("@Pattern");
			buffer.append("(");
			buffer.append("regexp = ");
			buffer.append(QUOTES);
			buffer.append(regexp);
			buffer.append(QUOTES);
			buffer.append(", ");
			buffer.append("message = ");
			buffer.append(QUOTES);
			buffer.append("请设置合法的").append(param.getDescription());
			buffer.append(QUOTES);
			buffer.append(")");
			return buffer.toString();
		}

		/**
		 * 日期注解
		 * @param buffer
		 * @param param
		 */
		public static String getDateTimeFormat(AbstractParam param) {
			StringBuilder buffer = new StringBuilder();

			String example = param.getExample();

			String pattern = DateFormat.getPattern(example);

			// replace
			buffer.append("@DateTimeFormat");
			buffer.append("(");
			buffer.append("pattern = ");
			buffer.append(QUOTES);
			buffer.append(pattern);
			buffer.append(QUOTES);
			// buffer.append(", ");
			// buffer.append("message = ");
			// buffer.append(QUOTES);
			// buffer.append("请设置合法的").append(param.getDescription());
			// buffer.append(QUOTES);
			buffer.append(")");
			return buffer.toString();
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

	// 解析json
	public static List<JSON> parseSchema(String schema) {
		JSONObject jsonObject = new JSONObject(schema);
		return parse(jsonObject.getJSONObject(Schema.PROPERTIES));
	}

	// 解析schema
	public static List<JSON> parse(JSONObject properties) {
		List<JSON> jsons = Lists.newArrayList();
		Set<String> keys = properties.keySet();

		for (String key : keys) {

			JSONObject jsonObject = properties.getJSONObject(key);
			String type = jsonObject.getString(Schema.TYPE);

			// public List<JSON> children; // 子属性
			// public int jsonType; // 默认简单
			//
			// public String name; // 名称
			// public String type; // 类型
			// public String desc; // 注释
			JSON json = new JSON();
			json.name = key;
			json.desc = jsonObject.has(Schema.DESCRIPTION) ? jsonObject.getString(Schema.DESCRIPTION) : "";

			if (StringUtils.equalsIgnoreCase(type, Schema.TYPE_OBJECT)) {
				List<JSON> children = parse(jsonObject.getJSONObject(Schema.PROPERTIES));
				json.jsonType = JSON.JSON_TYPE_MAP;
				json.children = children;
				json.type = json.name.substring(0, 1).toUpperCase() + json.name.substring(1);
			} else if (StringUtils.equalsIgnoreCase(type, Schema.TYPE_ARRAY)) {
				// TODO
				// remove end `s` or `List`
				List<JSON> children = parse(jsonObject.getJSONObject(Schema.ITEMS).getJSONObject(Schema.PROPERTIES));
				json.jsonType = JSON.JSON_TYPE_LIST;
				json.children = children;

				String temp = "";
				if (json.name.endsWith("s")) {
					temp = json.name.substring(0, json.name.length() - 1);
				} else if (json.name.endsWith("List") || json.name.endsWith("list")) {
					temp = json.name.substring(0, json.name.length() - 4);
				} else {
					temp = json.name;
				}
				json.type = temp.substring(0, 1).toUpperCase() + temp.substring(1);
			} else {
				json.jsonType = JSON.JSON_TYPE_SIMPLE;
				// 判断是否是日期
				/*
				 * String value = DateFormat.getPattern(jsonObject.getString(key)); if (StringUtils.isNotEmpty(value)) {
				 * json.type = "Date"; // @DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss") String anno = new
				 * StringBuilder()// .append("@DateTimeFormat(pattern = ")//
				 * .append(QUOTES).append(value).append(QUOTES)// .append(")") // .toString(); json.anno = anno; } else
				 */
				if (StringUtils.equalsIgnoreCase(type, Schema.TYPE_NUMBER)) {
					json.type = "Double";
				} else {
					json.type = type.substring(0, 1).toUpperCase() + type.substring(1);
				}

			}
			jsons.add(json);
		}
		return jsons;
	}

	public static class Schema {
		public static final String TYPE = "type";
		public static final String PROPERTIES = "properties";
		public static final String ITEMS = "items";
		public static final String DESCRIPTION = "description";

		public static final String TYPE_OBJECT = "object";
		public static final String TYPE_ARRAY = "array";

		public static final String TYPE_INTEGER = "integer";
		public static final String TYPE_NUMBER = "number";
		public static final String TYPE_BOOLEAN = "boolean";
		public static final String TYPE_DATE = "date";
	}

	public static class JSON {

		public static final int JSON_TYPE_SIMPLE = 1;
		public static final int JSON_TYPE_MAP = 2;
		public static final int JSON_TYPE_LIST = 3;

		public List<JSON> children; // 子属性
		public int jsonType; // 默认简单

		public String name; // 名称
		public String type; // 类型
		public String desc; // 注释
		public String anno; // 注解

		public JSON() {
		}

	}

}
