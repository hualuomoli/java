package com.github.hualuomoli.raml.parser.join.transfer.res.success.json.java;

import org.raml.model.Action;
import org.raml.model.MimeType;

import com.github.hualuomoli.raml.parser.join.transfer.util.TransferUtils;

/**
 * 请求参数为URL Encoded
 * @author hualuomoli
 *
 */
public class UrlEncodedTransfer extends JavaTransfer {

	@Override
	public boolean support(Action action, MimeType requestMimeType) {
		return TransferUtils.isUrlEncoded(action, requestMimeType);
	}

}
