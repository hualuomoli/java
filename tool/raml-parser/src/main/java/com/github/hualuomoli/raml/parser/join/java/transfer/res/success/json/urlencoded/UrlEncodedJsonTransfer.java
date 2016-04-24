package com.github.hualuomoli.raml.parser.join.java.transfer.res.success.json.urlencoded;

import org.apache.commons.lang3.StringUtils;
import org.raml.model.Action;
import org.raml.model.ActionType;
import org.raml.model.MimeType;

import com.github.hualuomoli.raml.parser.join.java.transfer.res.success.json.ResponseSuccessJsonTransfer;

/**
 * 请求参数为URL Encoded
 * @author hualuomoli
 *
 */
public class UrlEncodedJsonTransfer extends ResponseSuccessJsonTransfer {

	@Override
	public boolean support(Action action, MimeType requestMimeType) {
		if (action.getType() != ActionType.POST) {
			return false;
		}
		if (requestMimeType != null && !StringUtils.equalsIgnoreCase(requestMimeType.getType(), MIME_TYPE_URLENCODED)) {
			return false;
		}

		return true;
	}

}
