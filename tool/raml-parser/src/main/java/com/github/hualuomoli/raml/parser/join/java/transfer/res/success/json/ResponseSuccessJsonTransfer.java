package com.github.hualuomoli.raml.parser.join.java.transfer.res.success.json;

import org.apache.commons.lang3.StringUtils;
import org.raml.model.Action;
import org.raml.model.MimeType;

import com.github.hualuomoli.raml.parser.join.java.transfer.res.success.ResponseSuccessTransfer;

/**
 * 响应成功JSON的转换器
 * @author hualuomoli
 *
 */
public abstract class ResponseSuccessJsonTransfer extends ResponseSuccessTransfer {

	@Override
	public boolean support(Action action, MimeType requestMimeType, MimeType responseMimeType) {
		if (responseMimeType != null && !StringUtils.equals(responseMimeType.getType(), MIME_TYPE_JSON)) {
			return false;
		}
		return this.support(action, requestMimeType);
	}

	/**
	 * 是否支持
	 * @param action 事件类型
	 * @param requestMimeType 请求MimeType
	 * @param responseMimeType 响应MimeType
	 * @return 是否支持
	 */
	public abstract boolean support(Action action, MimeType requestMimeType);

}
