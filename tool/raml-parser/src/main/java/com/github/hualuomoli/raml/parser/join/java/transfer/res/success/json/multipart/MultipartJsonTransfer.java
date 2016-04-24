package com.github.hualuomoli.raml.parser.join.java.transfer.res.success.json.multipart;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.raml.model.Action;
import org.raml.model.ActionType;
import org.raml.model.MimeType;
import org.raml.model.Resource;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.raml.parser.join.java.transfer.res.success.json.ResponseSuccessJsonTransfer;

/**
 * multipart/form-data -> JSON
 * 请求参数为multipart/form-data
 * 响应类型为JSON
 * @author hualuomoli
 *
 */
public class MultipartJsonTransfer extends ResponseSuccessJsonTransfer {

	@Override
	public String getData(MimeType queryMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) {
		// TODO
		return "// 文件上传";
	}

	@Override
	public boolean support(Action action, MimeType queryMimeType) {
		if (action.getType() == null || action.getType() != ActionType.POST) {
			return false;
		}
		if (queryMimeType == null || !StringUtils.equals(queryMimeType.getType(), MIME_TYPE_MULTIPART)) {
			return false;
		}

		return true;
	}

}
