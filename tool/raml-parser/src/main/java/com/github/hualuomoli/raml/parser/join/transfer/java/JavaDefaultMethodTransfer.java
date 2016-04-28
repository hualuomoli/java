package com.github.hualuomoli.raml.parser.join.transfer.java;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.raml.model.Action;
import org.raml.model.MimeType;
import org.raml.model.Resource;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.raml.parser.exception.ParseException;
import com.github.hualuomoli.raml.parser.join.util.JavaRamlUtils;
import com.github.hualuomoli.raml.parser.join.util.JavaRamlUtils.ClassAttribute;
import com.github.hualuomoli.raml.parser.join.util.JavaRamlUtils.JSON;
import com.github.hualuomoli.raml.parser.join.util.JavaRamlUtils.MethodParam;
import com.github.hualuomoli.raml.parser.join.util.JavaRamlUtils.Note;
import com.github.hualuomoli.raml.parser.util.RamlUtils;
import com.google.common.collect.Sets;

/**
 * JAVA默认类方法转换器(查询参数为实体类,返回结果为实体类)
 * @author hualuomoli
 *
 */
public abstract class JavaDefaultMethodTransfer extends JavaMethodTransfer {

	// 查询实体类
	@Override
	public Set<Note> getOtherNotes(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri, String parentFullUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) {

		Set<Note> notes = Sets.newHashSet();

		String queryEntityName = this.getQueryEntityName(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUri, parentFullUriParameters,
				resource);
		if (StringUtils.isEmpty(queryEntityName)) {
			return notes;
		}

		String lowerQueryEntityName = queryEntityName.substring(0, 1).toLowerCase() + queryEntityName.substring(1);

		notes.add(new Note(lowerQueryEntityName, "实体类"));

		return notes;
	}

	@Override
	public String getHeaderMethodResultTypeName(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException {
		String resultEntityName = this.getResultEntityName(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUri,
				parentFullUriParameters, resource);
		if (StringUtils.isEmpty(resultEntityName)) {
			return "void";
		}
		String upperResultEntityName = resultEntityName.substring(0, 1).toUpperCase() + resultEntityName.substring(1);
		return upperResultEntityName;
	}

	@Override
	public Set<MethodParam> getOtherParams(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource) {

		Set<MethodParam> params = Sets.newHashSet();

		String queryEntityName = this.getQueryEntityName(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUri, parentFullUriParameters,
				resource);
		if (StringUtils.isEmpty(queryEntityName)) {
			return params;
		}

		String upperQueryEntityName = queryEntityName.substring(0, 1).toUpperCase() + queryEntityName.substring(1);
		String lowerQueryEntityName = queryEntityName.substring(0, 1).toLowerCase() + queryEntityName.substring(1);

		params.add(new MethodParam("", upperQueryEntityName, lowerQueryEntityName));

		return params;
	}

	@Override
	public String getContent(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri, String parentFullUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException {

		StringBuilder buffer = new StringBuilder();

		String resultEntityName = this.getResultEntityName(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUri,
				parentFullUriParameters, resource);

		if (StringUtils.isEmpty(resultEntityName)) {
			return buffer.toString();
		}

		if (responseMimeType == null || StringUtils.isEmpty(responseMimeType.getExample())) {
			return buffer.toString();
		}

		// 首字母大写
		String upperResultEntityName = resultEntityName.substring(0, 1).toUpperCase() + resultEntityName.substring(1);

		String example = responseMimeType.getExample();
		String data = RamlUtils.dealExample(example);

		if (StringUtils.isEmpty(data)) {
			return buffer.toString();
		}

		buffer.append(LINE);

		// return JsonMapper.fromJsonString(data, Entity.clsss);
		buffer.append(LINE).append(TAB).append(TAB);
		buffer.append("return ");
		buffer.append("JsonMapper.fromJsonString(");
		buffer.append(QUOTES).append(data).append(QUOTES).append(", ");
		buffer.append(upperResultEntityName).append(".class);");

		return buffer.toString();
	}

	@Override
	public String getOthers(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri, String parentFullUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException {
		//
		StringBuilder buffer = new StringBuilder();

		String queryEntityDefinition = this.getQueryEntityDefinition(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUri,
				parentFullUriParameters, resource);
		String resultEntityDefinition = this.getResultEntityDefinition(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUri,
				parentFullUriParameters, resource);

		// query
		if (StringUtils.isNotEmpty(queryEntityDefinition)) {
			buffer.append(queryEntityDefinition);
		}

		// result
		if (StringUtils.isNotEmpty(resultEntityDefinition)) {
			// buffer.append(LINE);
			buffer.append(resultEntityDefinition);
		}

		return buffer.toString();
	}

	// 查询参数实体类定义
	public String getQueryEntityDefinition(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException {
		StringBuilder buffer = new StringBuilder();

		String queryEntityName = this.getQueryEntityName(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUri, parentFullUriParameters,
				resource);
		List<ClassAttribute> attributes = JavaRamlUtils.getClassAttributes(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUri,
				parentFullUriParameters, resource);

		if (StringUtils.isEmpty(queryEntityName)) {
			return buffer.toString();
		}

		String upperQueryEntityName = queryEntityName.substring(0, 1).toUpperCase() + queryEntityName.substring(1);

		buffer.append(LINE).append(LINE).append(TAB);
		buffer.append("public static ");
		buffer.append("class ").append(upperQueryEntityName).append(" implements EntityValidator {");

		buffer.append(LINE);
		// attribute
		for (ClassAttribute attribute : attributes) {
			// desc
			buffer.append(LINE).append(TAB).append(TAB);
			buffer.append("/** ").append(attribute.desc).append(" */");
			// annos
			List<String> annos = attribute.annos;
			for (String anno : annos) {
				buffer.append(LINE).append(TAB).append(TAB);
				buffer.append(anno);
			}
			// define
			buffer.append(LINE).append(TAB).append(TAB);
			buffer.append("private ").append(attribute.type).append(" ").append(attribute.name).append(";");

		}

		// method
		for (ClassAttribute attribute : attributes) {
			String upperName = attribute.name.substring(0, 1).toUpperCase() + attribute.name.substring(1);
			// getter
			buffer.append(LINE);
			buffer.append(LINE).append(TAB).append(TAB);
			buffer.append("public ").append(attribute.type).append(" get").append(upperName);
			buffer.append("() {");

			buffer.append(LINE).append(TAB).append(TAB);
			buffer.append(TAB).append("return ").append(attribute.name).append(";");

			buffer.append(LINE).append(TAB).append(TAB);
			buffer.append("}");
			// setter
			buffer.append(LINE);
			buffer.append(LINE).append(TAB).append(TAB);
			buffer.append("public void set").append(upperName);
			buffer.append("(");
			buffer.append(attribute.type).append(" ").append(attribute.name);
			buffer.append(") {");

			buffer.append(LINE).append(TAB).append(TAB);
			buffer.append(TAB).append("this.").append(attribute.name).append(" = ").append(attribute.name).append(";");

			buffer.append(LINE).append(TAB).append(TAB);
			buffer.append("}");
		}

		buffer.append(LINE).append(TAB);
		buffer.append("}");

		return buffer.toString();
	}

	// 返回结果实体类定义
	public String getResultEntityDefinition(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException {
		StringBuilder buffer = new StringBuilder();

		if (responseMimeType == null || !StringUtils.equals(responseMimeType.getType(), MIME_TYPE_JSON) || StringUtils.isEmpty(responseMimeType.getExample())) {
			return buffer.toString();
		}

		String resultEntityName = this.getResultEntityName(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUri,
				parentFullUriParameters, resource);
		if (StringUtils.isEmpty(resultEntityName)) {
			return buffer.toString();
		}

		// schema
		String schema = responseMimeType.getSchema();
		if (StringUtils.isEmpty(schema)) {
			throw new ParseException("please set json's schema.");
		}

		String upperResultEntityName = resultEntityName.substring(0, 1).toUpperCase() + resultEntityName.substring(1);

		// buffer.append(LINE);

		buffer.append(this.getResultClass(1, upperResultEntityName, JavaRamlUtils.parseSchema(schema)));

		return buffer.toString();
	}

	// 获取返回类的定义
	private String getResultClass(int level, String classNmae, List<JSON> jsons) {
		StringBuilder buffer = new StringBuilder();
		String tab = "";
		for (int i = 0; i < level; i++) {
			tab += TAB;
		}

		buffer.append(LINE).append(LINE).append(tab);
		buffer.append("public static ");
		buffer.append("class ").append(classNmae).append(" {");

		buffer.append(LINE);

		// attribute
		for (JSON json : jsons) {
			String array = json.jsonType == JSON.JSON_TYPE_LIST ? "[]" : "";
			if (StringUtils.isNotEmpty(json.desc)) {
				buffer.append(LINE).append(tab);
				buffer.append(TAB).append("/** ").append(json.desc).append(" */");
			}
			// if (StringUtils.isNotEmpty(json.anno)) {
			// buffer.append(LINE).append(tab);
			// buffer.append(TAB).append(json.anno);
			// }
			buffer.append(LINE).append(tab);
			buffer.append(TAB).append("private ").append(json.type).append(array).append(" ").append(json.name).append(";");
		}

		// method
		for (JSON json : jsons) {
			String upperName = json.name.substring(0, 1).toUpperCase() + json.name;
			String array = json.jsonType == JSON.JSON_TYPE_LIST ? "[]" : "";

			// getter
			buffer.append(LINE);
			buffer.append(LINE).append(tab);
			buffer.append(TAB).append("public ").append(json.type).append(array).append(" get").append(upperName).append("() {");

			buffer.append(LINE).append(tab);
			buffer.append(TAB).append(TAB).append("return ").append(json.name).append(";");

			buffer.append(LINE).append(tab);
			buffer.append(TAB).append("}");

			// setter
			buffer.append(LINE);
			buffer.append(LINE).append(tab);
			buffer.append(TAB).append("public void set").append(upperName).append("(").append(json.type).append(array).append(" ").append(json.name)
					.append(") {");

			buffer.append(LINE).append(tab);
			buffer.append(TAB).append(TAB).append("this.").append(json.name).append(" = ").append(json.name).append(";");

			buffer.append(LINE).append(tab);
			buffer.append(TAB).append("}");

		}

		// add inner class
		for (JSON json : jsons) {
			if (json.jsonType == JSON.JSON_TYPE_LIST || json.jsonType == JSON.JSON_TYPE_MAP) {
				buffer.append(this.getResultClass(level + 1, json.type, json.children));
			}
		}

		buffer.append(LINE).append(tab);
		buffer.append("}");

		return buffer.toString();
	}

	// 获取查询实体类名称
	protected String getQueryEntityName(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource) {
		return this.getEntityName(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUri, parentFullUriParameters, resource);
	}

	// 获取返回实体类名称
	protected String getResultEntityName(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource) {
		String entityName = this.getQueryEntityName(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUri, parentFullUriParameters,
				resource);
		if (StringUtils.isEmpty(entityName)) {
			entityName = this.getEntityName(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUri, parentFullUriParameters, resource);
		}
		String upperEntityName = entityName.substring(0, 1).toUpperCase() + entityName.substring(1);

		return "result" + upperEntityName;
	}

	// 获取实体类名称
	protected String getEntityName(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri, String parentFullUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) {
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

}
