package com.github.hualuomoli.raml.join.adaptor;

import java.util.Set;

import org.raml.model.ActionType;
import org.raml.model.MimeType;
import org.raml.model.parameter.QueryParameter;

import com.github.hualuomoli.raml.join.Adaptor;
import com.github.hualuomoli.raml.join.AdaptorAbstract;
import com.github.hualuomoli.raml.util.ParserUtils.ResponseSM;

/**
 * Restful 风格请求(get/delete 没有query和form参数)
 * @author hualuomoli
 *
 */
public class RestfulAdaptor extends AdaptorAbstract implements Adaptor {

	public RestfulAdaptor() {
		super();
	}

	public RestfulAdaptor(MethodAdaptor methodAdaptor) {
		super(methodAdaptor);
	}

	@Override
	public boolean support(ActionType actionType, Set<QueryParameter> queryParameters, MimeType formMimeType, ResponseSM responseSM) {

		// type
		if (actionType != ActionType.GET && actionType != ActionType.DELETE) {
			return false;
		}

		// 查询参数
		if (queryParameters != null && queryParameters.size() > 0) {
			return false;
		}

		// 表单参数
		if (formMimeType != null) {
			return false;
		}

		return true;
	}

}
