package com.github.hualuomoli.raml.parser.join.transfer.java;

import java.util.Map;
import java.util.Set;

import org.raml.model.Action;
import org.raml.model.MimeType;
import org.raml.model.Resource;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.raml.parser.exception.ParseException;
import com.github.hualuomoli.raml.parser.join.transfer.MethodTransferAbstract;
import com.github.hualuomoli.raml.parser.join.util.JavaRamlUtils;
import com.github.hualuomoli.raml.parser.join.util.JavaRamlUtils.MethodParam;
import com.github.hualuomoli.raml.parser.join.util.JavaRamlUtils.Note;

/**
 * JAVA方法转换器
 * @author hualuomoli
 *
 */
public abstract class JavaMethodTransfer extends MethodTransferAbstract {

	@Override
	public String getNote(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri, String parentFullUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException {

		// URI参数
		Set<Note> uriNotes = JavaRamlUtils.getUriNotes(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUri, parentFullUriParameters,
				resource);
		Set<Note> fileNotes = JavaRamlUtils.getFileParameterNotes(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUri,
				parentFullUriParameters, resource);
		// 其他参数
		Set<Note> otherNotes = this.getOtherNotes(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUri, parentFullUriParameters,
				resource);

		StringBuilder buffer = new StringBuilder();

		buffer.append(LINE).append(TAB).append("/**");
		buffer.append(LINE).append(TAB).append(" * ").append(this.getClass().getSimpleName());
		buffer.append(LINE).append(TAB).append(" * ").append(action.getDescription());
		// URI
		for (Note note : uriNotes) {
			buffer.append(LINE).append(TAB).append(" * @param ").append(note.param).append(" ").append(note.description);
		}
		// File
		for (Note note : fileNotes) {
			buffer.append(LINE).append(TAB).append(" * @param ").append(note.param).append(" ").append(note.description);
		}
		// other
		for (Note note : otherNotes) {
			buffer.append(LINE).append(TAB).append(" * @param ").append(note.param).append(" ").append(note.description);
		}
		buffer.append(LINE).append(TAB).append(" * ").append("@param ").append("request ").append("HTPP请求");
		buffer.append(LINE).append(TAB).append(" * ").append("@param ").append("response ").append("HTTP响应");
		buffer.append(LINE).append(TAB).append(" * ").append("@param ").append("model ").append("Spring的Model");
		buffer.append(LINE).append(TAB).append(" */");

		return buffer.toString();
	}

	// 其他参数
	public abstract Set<Note> getOtherNotes(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException;

	@Override
	public String getHeader(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri, String parentFullUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException {
		StringBuilder buffer = new StringBuilder();

		// 注解
		buffer.append(
				this.getHeaderAnnoation(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUri, parentFullUriParameters, resource));
		// 方法的头部
		buffer.append(this.getHeaderMethod(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUri, parentFullUriParameters, resource));

		return buffer.toString();
	}

	// header 注解
	// 请求注解 @RequestMapping
	// 返回注解 @ResponseBody
	private String getHeaderAnnoation(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException {
		StringBuilder buffer = new StringBuilder();

		// @RequestMapping
		buffer.append(LINE).append(TAB).append("@RequestMapping");
		buffer.append("(");

		// value = "/info"
		buffer.append("value = ").append(QUOTES).append(relativeUri).append(QUOTES);
		// method = { RequestMethod.POST }
		buffer.append(", method= { RequestMethod.").append(action.getType().toString()).append(" }");
		// consumes = { "application/x-www-form-urlencoded" }
		if (requestMimeType != null) {
			buffer.append(", consumes = { ").append(QUOTES).append(requestMimeType.getType()).append(QUOTES).append(" }");
		}
		// produces = { "application/json" }
		if (responseMimeType != null) {
			buffer.append(", produces = { ").append(QUOTES).append(responseMimeType.getType()).append(QUOTES).append(" }");
		}

		buffer.append(")");

		// @ResponseBody
		buffer.append(LINE).append(TAB).append("@ResponseBody");

		return buffer.toString();
	}

	// 方法的头部
	private String getHeaderMethod(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri, String parentFullUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException {

		Set<MethodParam> uriParams = JavaRamlUtils.getUriMethodParams(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUri,
				parentFullUriParameters, resource);
		Set<MethodParam> fileParams = JavaRamlUtils.getFileMethodParams(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUri,
				parentFullUriParameters, resource);
		Set<MethodParam> otherParams = this.getOtherParams(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUri,
				parentFullUriParameters, resource);

		StringBuilder buffer = new StringBuilder();

		buffer.append(LINE).append(TAB).append("public ");
		// 返回类型名称
		buffer.append(this.getHeaderMethodResultTypeName(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUri, parentFullUriParameters,
				resource));
		buffer.append(" ");
		// 方法名
		buffer.append(
				this.getHeaderMethodName(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUri, parentFullUriParameters, resource));

		buffer.append("(");

		// @PathVariable(value = "username") String username
		// URI
		for (MethodParam methodParam : uriParams) {
			buffer.append(methodParam.anno).append(" ").append(methodParam.type).append(" ").append(methodParam.name).append(", ");
		}
		// File
		for (MethodParam methodParam : fileParams) {
			buffer.append(methodParam.anno).append(" ").append(methodParam.type).append(" ").append(methodParam.name).append(", ");
		}
		// other
		for (MethodParam methodParam : otherParams) {
			buffer.append(methodParam.anno).append(" ").append(methodParam.type).append(" ").append(methodParam.name).append(", ");
		}

		buffer.append("HttpServletRequest request, HttpServletResponse response, Model model) {");

		return buffer.toString();
	}

	// 方法的返回类型
	public abstract String getHeaderMethodResultTypeName(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException;

	// 方法的名称
	protected String getHeaderMethodName(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException {
		return JavaRamlUtils.getMethodName(relativeUri, action);
	}

	// 其他参数
	public abstract Set<MethodParam> getOtherParams(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException;

	@Override
	public String getFooter(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri, String parentFullUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException {
		return new StringBuilder().append(LINE).append(TAB).append("}").toString();
	}

}
