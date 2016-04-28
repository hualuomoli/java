package com.github.hualuomoli.raml.parser.join.transfer.mocha.rsj;

import org.apache.commons.lang3.StringUtils;
import org.raml.model.Action;
import org.raml.model.MimeType;

import com.github.hualuomoli.raml.parser.exception.ParseException;
import com.github.hualuomoli.raml.parser.join.transfer.mocha.MochaMethodTransfer;

/**
 * result success JSON
 * @author hualuomoli
 *
 */
public abstract class RSJMochaMethodTransfer extends MochaMethodTransfer {

	@Override
	public boolean support(Action action, MimeType requestMimeType, String status, MimeType responseMimeType) throws ParseException {
		if (StringUtils.isNotEmpty(status) && !StringUtils.equals(status, STATUS_SUCCESS)) {
			return false;
		}
		if (responseMimeType != null && !StringUtils.equals(responseMimeType.getType(), MIME_TYPE_JSON)) {
			return false;
		}
		return this.support(action, requestMimeType);
	}

	public abstract boolean support(Action action, MimeType requestMimeType) throws ParseException;

}
