package com.github.hualuomoli.raml.parser.join.transfer.res.success.json.java;

import org.raml.model.Action;
import org.raml.model.MimeType;

import com.github.hualuomoli.raml.parser.join.transfer.util.TransferUtils;

/**
 * 请求参数为GET
 * @author hualuomoli
 *
 */
public class GetTransfer extends JavaTransfer {

	@Override
	public boolean support(Action action, MimeType requestMimeType) {
		return TransferUtils.isGet(action, requestMimeType);
	}

	// use get prefix
	@Override
	public String getEntityName(Action action, String relativeUri) {
		return "get" + super.getEntityName(action, relativeUri);
	}

}
