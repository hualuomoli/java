package com.github.hualuomoli.raml.join;

import java.util.Set;

import org.raml.model.ActionType;
import org.raml.model.MimeType;
import org.raml.model.parameter.QueryParameter;

import com.github.hualuomoli.raml.join.adaptor.util.AdaptorUtils.Translate;
import com.github.hualuomoli.raml.util.ParserUtils.ResponseSM;

/**
 * 适配器
 * @author hualuomoli
 *
 */
public interface Adaptor {

	/**
	 * 是否支持
	 * @param actionType Action类型
	 * @param queryParameters 查询参数
	 * @param formMimeType 表单MimeType
	 * @param responseSM 响应状态和MimeType
	 * @return 是否支持
	 */
	boolean support(ActionType actionType, Set<QueryParameter> queryParameters, MimeType formMimeType, ResponseSM responseSM);

	/**
	 * 获取Action数据
	 * @param translate translate
	 * @return Action数据
	 */
	String getActionData(Translate translate);

}
