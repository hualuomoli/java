package com.github.hualuomoli.raml.join.adaptor;

import java.util.Set;

import org.raml.model.ActionType;
import org.raml.model.MimeType;
import org.raml.model.parameter.QueryParameter;

import com.github.hualuomoli.raml.join.Adaptor;
import com.github.hualuomoli.raml.join.AdaptorAbstract;
import com.github.hualuomoli.raml.util.ParserUtils.ResponseSM;

/**
 * get请求
 * @author hualuomoli
 *
 */
public class GetAdaptor extends AdaptorAbstract implements Adaptor {

	public GetAdaptor() {
		super();
	}

	public GetAdaptor(MethodAdaptor methodAdaptor) {
		super(methodAdaptor);
	}

	@Override
	public boolean support(ActionType actionType, Set<QueryParameter> queryParameters, MimeType formMimeType, ResponseSM responseSM) {

		// type
		if (actionType != ActionType.GET) {
			return false;
		}

		// 表单参数
		if (formMimeType != null) {
			return false;
		}

		// 响应
		if (responseSM == null) {
			return false;
		}

		return true;
	}

}
