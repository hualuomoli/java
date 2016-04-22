package com.github.hualuomoli.raml.parser.server;

import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.raml.model.Action;
import org.raml.model.ActionType;
import org.raml.model.MimeType;
import org.raml.model.Resource;
import org.raml.model.Response;
import org.raml.model.parameter.AbstractParam;
import org.raml.model.parameter.FormParameter;
import org.raml.model.parameter.QueryParameter;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.raml.parser.exception.ParseException;
import com.google.common.collect.Maps;

/**
 * java解析
 * @author hualuomoli
 *
 */
public class JavaRamlParser extends ServerRamlParser {

	private static final String LINE = "\n";
	private static final String TAB = "\t";
	private static final String QUOTES = "\"";

	@Override
	public void config(Map<ActionType, Action> actions, Map<String, Resource> noChildResources, String parentRelativeUri,
			Map<String, UriParameter> parentUriParameters, Resource resource) throws ParseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void createFile(List<String> actionDatas, Resource resource) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getData(Action action, String relativeUri, Map<String, UriParameter> uriParameters, Resource resource) {
		// 只处理 #MIME_TYPE_URLENCODED 类型的请求

		StringBuilder buffer = new StringBuilder();
		Map<String, MimeType> body = action.getBody();

		// description

		// RequestMapping
		this.addRequestMapping(buffer, relativeUri, body);
		// ResponseBody
		buffer.append(LINE).append(TAB).append("@ResponseBody");
		// method
		this.addMethodHead(buffer, relativeUri, uriParameters, action);
		// 输出example
		if (this.isResponseData(action)) {
			this.addResponseExample(buffer, action);
		}
		// method end
		buffer.append(LINE).append(TAB).append("}");

		// add entity
		this.addRequestEntity(buffer, relativeUri, uriParameters, action);

		if (logger.isDebugEnabled()) {
			logger.debug("\n" + buffer.toString());
		}

		return buffer.toString();
	}

	/**
	 * 添加请求的Entity
	 * @param buffer buffer
	 * @param uriParameters URI参数
	 * @param action 事件
	 */
	private void addRequestEntity(StringBuilder buffer, String relativeUri, Map<String, UriParameter> uriParameters, Action action) {
		String ClassName = this.getMethodName(relativeUri);
		buffer.append(LINE);
		buffer.append(TAB).append("public static class ").append(StringUtils.isEmpty(ClassName) ? "Entity" : ClassName).append(" {").append(LINE);

		Map<String, QueryParameter> queryParameters = action.getQueryParameters();
		Map<String, List<FormParameter>> formParameters = null;
		Map<String, MimeType> body = action.getBody();
		if (body != null) {
			MimeType mimeType = body.get(MIME_TYPE_URLENCODED);
			if (mimeType != null) {
				formParameters = mimeType.getFormParameters();
			}
		}
		// uri
		if (uriParameters != null && uriParameters.size() > 0) {
			for (String fieldName : uriParameters.keySet()) {
				this.addRequestParam(buffer, uriParameters.get(fieldName));
			}
		}
		// query
		if (queryParameters != null && queryParameters.size() > 0) {
			for (String fieldName : queryParameters.keySet()) {
				this.addRequestParam(buffer, queryParameters.get(fieldName));
			}
		}
		// form
		if (formParameters != null && formParameters.size() > 0) {
			for (String fieldName : formParameters.keySet()) {
				this.addRequestParam(buffer, formParameters.get(fieldName).get(0));
			}
		}

		// getter setter
		if (uriParameters != null && uriParameters.size() > 0) {
			for (String fieldName : uriParameters.keySet()) {
				this.addRequestParamGetterAndSetter(buffer, uriParameters.get(fieldName));
			}
		}
		if (queryParameters != null && queryParameters.size() > 0) {
			for (String fieldName : queryParameters.keySet()) {
				this.addRequestParamGetterAndSetter(buffer, queryParameters.get(fieldName));
			}
		}
		if (formParameters != null && formParameters.size() > 0) {
			for (String fieldName : formParameters.keySet()) {
				this.addRequestParamGetterAndSetter(buffer, formParameters.get(fieldName).get(0));
			}
		}

		buffer.append(LINE).append(TAB).append("}");
	}

	/**
	 * 设置请求的参数
	 * @param buffer buffer
	 * @param param 参数
	 */
	private void addRequestParam(StringBuilder buffer, AbstractParam param) {
		buffer.append(LINE);
		buffer.append(TAB).append(TAB).append("private String ").append(param.getDisplayName()).append(";");
		buffer.append(" /** ").append(param.getDescription()).append(" */");
	}

	/**
	 * 设置请求的参数
	 * @param buffer buffer
	 * @param param 参数
	 */
	private void addRequestParamGetterAndSetter(StringBuilder buffer, AbstractParam param) {
		// getter
		buffer.append(LINE);
		buffer.append(TAB).append(TAB).append("public String ").append("get");
		buffer.append(param.getDisplayName().substring(0, 1).toUpperCase() + param.getDisplayName().substring(1));
		buffer.append("(){");
		buffer.append(LINE).append(TAB).append(TAB).append(TAB).append("return ").append(param.getDisplayName()).append(";");
		buffer.append(LINE).append(TAB).append(TAB).append("}");

		// setter
		buffer.append(LINE);
		buffer.append(TAB).append(TAB).append("public void ").append("set");
		buffer.append(param.getDisplayName().substring(0, 1).toUpperCase() + param.getDisplayName().substring(1));
		buffer.append("(String ").append(param.getDisplayName());
		buffer.append("){");
		buffer.append(LINE).append(TAB).append(TAB).append(TAB).append("this.").append(param.getDisplayName()).append(" = ");
		buffer.append(param.getDisplayName()).append(";");
		buffer.append(LINE).append(TAB).append(TAB).append("}");

	}

	/**
	 * 添加方法头部
	 * @param buffer buffer
	 * @param relativeUri 相对URI
	 * @param uriParameters URI参数
	 * @param action 事件
	 */
	private void addMethodHead(StringBuilder buffer, String relativeUri, Map<String, UriParameter> uriParameters, Action action) {
		// public void login(Entity entity, HttpServletRequest request, HttpServletResponse response, Model model) {
		buffer.append(LINE);
		buffer.append(TAB).append("public ").append(this.isResponseData(action) ? "String " : "Void ");
		String methodName = this.getMethodName(relativeUri);

		switch (action.getType()) {
		case GET:
			buffer.append("get").append(methodName);
			break;
		case POST:
			buffer.append("add").append(methodName);
			break;
		case PUT:
			buffer.append("set").append(methodName);
			break;
		case DELETE:
			buffer.append("remove").append(methodName);
			break;
		default:
			throw new RuntimeException("not exists's exception");
		}
		buffer.append("(");
		if (StringUtils.isEmpty(methodName)) {
			buffer.append("Entity entity");
		} else {
			buffer.append(methodName).append(" ").append(methodName.substring(0, 1).toLowerCase() + methodName.substring(1));
		}
		buffer.append(", HttpServletRequest request, HttpServletResponse response, Model model) {");
	}

	/**
	 * 获取方法的名称,去除所有的URI参数
	 * @param relativeUri URI
	 * @return 方法的名称
	 */
	private String getMethodName(String relativeUri) {

		String uri;
		if (StringUtils.isEmpty(relativeUri)) {
			uri = "";
		} else {
			String temp = relativeUri.replaceAll("/\\{.*}", "");
			if (StringUtils.isEmpty(temp)) {
				return "";
			}
			String[] array = temp.substring(1).split("/");
			StringBuilder b = new StringBuilder();
			for (int i = 0; i < array.length; i++) {
				String s = array[i];
				b.append(s.substring(0, 1).toUpperCase() + s.substring(1));
			}
			uri = b.toString();
		}

		return uri;

	}

	/**
	 * 添加请求路径 
	 * @param buffer buffer
	 * @param relativeUri 相对URI
	 * @param body body
	 */
	private void addRequestMapping(StringBuilder buffer, String relativeUri, Map<String, MimeType> body) {
		// @RequestMapping(value = "login", consumes = { "application/x-www-form-urlencoded", "application/*" })
		buffer.append(LINE);
		buffer.append(TAB).append("@RequestMapping");
		buffer.append("(");
		// value
		buffer.append("value").append(" = ").append(QUOTES).append(relativeUri).append(QUOTES);
		// consumes

		buffer.append(", ");
		buffer.append("consumes").append(" = ").append("{ ");
		if (body != null && body.containsKey(MIME_TYPE_URLENCODED)) {
			buffer.append(QUOTES).append(MIME_TYPE_URLENCODED).append(QUOTES);
			buffer.append(", ");
		}
		// add default
		buffer.append(QUOTES).append("*/*").append(QUOTES);
		buffer.append(" }");
		buffer.append(")");
	}

	/**
	 * 是否输出数据
	 * @param action action
	 * @return 是否输出数据
	 */
	private boolean isResponseData(Action action) {
		Map<String, Response> responses = action.getResponses();
		if (responses == null || responses.size() == 0) {
			return false;
		}
		// 只处理200
		Response response = responses.get("200");
		if (response == null) {
			return false;
		}
		Map<String, MimeType> body = response.getBody();
		if (body == null || body.size() == 0) {
			return false;
		}
		return body.containsKey(MIME_TYPE_JSON);
	}

	/**
	 * 获取响应example
	 * @param action action
	 * @return 响应example
	 */
	private void addResponseExample(StringBuilder buffer, Action action) {
		String example = action.getResponses().get("200").getBody().get(MIME_TYPE_JSON).getExample();
		if (StringUtils.isEmpty(example)) {
			return;
		}
		buffer.append(LINE);
		buffer.append(TAB).append(TAB).append("String str = \"\";");
		String[] array = example.split("\n");
		for (String data : array) {
			buffer.append(LINE).append(TAB).append(TAB).append("str += \"");
			buffer.append(data.replaceAll("\"", "\\\\\""));
			buffer.append("\";");
		}
		buffer.append(LINE).append(TAB).append(TAB).append("return str;");
	}

}
