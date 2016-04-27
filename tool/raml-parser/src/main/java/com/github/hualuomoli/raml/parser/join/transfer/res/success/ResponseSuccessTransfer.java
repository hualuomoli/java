package com.github.hualuomoli.raml.parser.join.transfer.res.success;

import org.apache.commons.lang3.StringUtils;
import org.raml.model.Action;
import org.raml.model.MimeType;

import com.github.hualuomoli.raml.parser.join.transfer.res.ResponseTransfer;

/**
 * 响应成功的转换器
 * @author hualuomoli
 *
 */
public abstract class ResponseSuccessTransfer implements ResponseTransfer {

	@Override
	public boolean support(Action action, MimeType requestMimeType, String status, MimeType responseMimeType) {
		if (StringUtils.isNotEmpty(status) && !StringUtils.equals(status, STATUS_SUCCESS)) {
			return false;
		}
		return this.support(action, requestMimeType, responseMimeType);
	}

	/**
	 * 是否支持
	 * @param action 事件类型
	 * @param requestMimeType 请求MimeType
	 * @param responseMimeType 响应MimeType
	 * @return 是否支持
	 */
	public abstract boolean support(Action action, MimeType requestMimeType, MimeType responseMimeType);

}
