package com.github.hualuomoli.raml.parser.join.transfer.res.success.json.java;

import java.util.List;
import java.util.Map;

import org.raml.model.Action;
import org.raml.model.MimeType;
import org.raml.model.ParamType;
import org.raml.model.Resource;
import org.raml.model.parameter.FormParameter;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.raml.parser.exception.ParseException;
import com.github.hualuomoli.raml.parser.join.transfer.util.TransferUtils;

/**
 * 请求参数为multipart/form-data
 * @author hualuomoli
 *
 */
public class FileTransfer extends JavaTransfer {

	@Override
	public boolean support(Action action, MimeType requestMimeType) throws ParseException {
		return TransferUtils.isFile(action, requestMimeType);
	}

	// 其他参数
	@Override
	public void addMethodOtherParameterNote(StringBuilder buffer, MimeType requestMimeType, String status, MimeType responseMimeType, Action action,
			String relativeUri, Map<String, UriParameter> parentFullUriParameters, Resource resource) {

		// add super
		super.addMethodOtherParameterNote(buffer, requestMimeType, status, responseMimeType, action, relativeUri, parentFullUriParameters, resource);

		// @param photo 头像
		Map<String, List<FormParameter>> formParameters = requestMimeType.getFormParameters();

		for (String displayName : formParameters.keySet()) {
			List<FormParameter> values = formParameters.get(displayName);
			for (FormParameter formParameter : values) {
				if (formParameter.getType() == ParamType.FILE) {
					buffer.append(LINE).append(TAB).append(" * ").append("@param ");
					buffer.append(formParameter.getDisplayName()).append(" ").append(formParameter.getDescription());
					buffer.append(", ");
				}
			}
		}
	}

	@Override
	public String getMethodOtherParameterHeader(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) {
		StringBuilder buffer = new StringBuilder();

		// add super
		buffer.append(super.getMethodOtherParameterHeader(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUriParameters, resource));

		// @RequestParam(value = "photo") File photo
		Map<String, List<FormParameter>> formParameters = requestMimeType.getFormParameters();

		for (String displayName : formParameters.keySet()) {
			List<FormParameter> values = formParameters.get(displayName);
			for (FormParameter formParameter : values) {
				if (formParameter.getType() == ParamType.FILE) {
					buffer.append("@RequestParam");
					buffer.append("(");
					buffer.append("value = ").append(QUOTES).append(displayName).append(QUOTES);
					buffer.append(", required = ").append(formParameter.isRequired() ? "true" : "false");
					buffer.append(")");

					buffer.append(" MultipartFile ").append(displayName);

					buffer.append(", ");
				}
			}
		}

		return buffer.toString();
	}

	@Override
	public String getEntityName(Action action, String relativeUri) {
		return "file" + super.getEntityName(action, relativeUri);
	}

}
