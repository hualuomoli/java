package com.github.hualuomoli.raml.join.adaptor;

import java.util.Set;

import org.raml.model.ActionType;
import org.raml.model.MimeType;
import org.raml.model.ParamType;
import org.raml.model.parameter.FormParameter;
import org.raml.model.parameter.QueryParameter;

import com.github.hualuomoli.raml.join.Adaptor;
import com.github.hualuomoli.raml.join.AdaptorAbstract;
import com.github.hualuomoli.raml.join.adaptor.util.AdaptorUtils;
import com.github.hualuomoli.raml.util.ParserUtils;
import com.github.hualuomoli.raml.util.ParserUtils.ResponseSM;

/**
 * MultiPart请求
 * @author hualuomoli
 *
 */
public class FileAdaptor extends AdaptorAbstract implements Adaptor {

	public FileAdaptor() {
		super();
	}

	public FileAdaptor(MethodAdaptor methodAdaptor) {
		super(methodAdaptor);
	}

	@Override
	public boolean support(ActionType actionType, Set<QueryParameter> queryParameters, MimeType formMimeType, ResponseSM responseSM) {

		// type
		if (actionType != ActionType.POST) {
			return false;
		}

		// 查询参数
		if (queryParameters != null && queryParameters.size() > 0) {
			return false;
		}

		// 表单参数
		if (formMimeType == null) {
			return false;
		}

		// 表单没有参数
		Set<FormParameter> formParameters = ParserUtils.getFormParameters(formMimeType);
		if (formParameters != null && formParameters.size() > 0) {
			return false;
		}

		// 表单没有file的参数
		boolean has = false;
		for (FormParameter formParameter : formParameters) {
			if (formParameter.getType() == ParamType.FILE) {
				has = true;
				break;
			}
		}
		if (!has) {
			return false;
		}

		// 响应
		if (responseSM == null) {
			return false;
		}

		// 协议
		if (!AdaptorUtils.isMultiPart(formMimeType)) {
			return false;
		}

		return true;
	}

}