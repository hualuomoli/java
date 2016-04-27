package com.github.hualuomoli.raml.parser.join.transfer.res.success.json.java;

import org.raml.model.Action;
import org.raml.model.MimeType;

import com.github.hualuomoli.raml.parser.join.transfer.util.TransferUtils;

/**
 * 请求参数为Restful
 * @author hualuomoli
 *
 */
public class RestfulTransfer extends JavaTransfer {

	@Override
	public boolean support(Action action, MimeType requestMimeType) {
		return TransferUtils.isRestful(action, requestMimeType);
	}

	// 不实用entity
	@Override
	public String getEntityName(Action action, String relativeUri) {
		return null;
	}

}
