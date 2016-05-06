package com.github.hualuomoli.raml.join.adaptor.java;

import java.util.List;
import java.util.Map;

import org.raml.model.ActionType;
import org.raml.model.MimeType;
import org.raml.model.parameter.FormParameter;

import com.github.hualuomoli.raml.join.JoinParser.Adapter;
import com.github.hualuomoli.raml.join.adaptor.JavaActionAdaptor;
import com.github.hualuomoli.raml.util.JSONUtils.JsonParam;
import com.google.common.collect.Lists;

/**
 * POST URLEncoded
 * @author hualuomoli
 *
 */
public class JavaPostUrlEncodedActionAdaptor extends JavaActionAdaptor {

	@Override
	public boolean support(Adapter adapter) {
		if (adapter.action.getType() != ActionType.POST) {
			return false;
		}
		Map<String, MimeType> body = adapter.action.getBody();
		if (body == null) {
			return false;
		}
		MimeType mimeType = body.get(MIME_TYPE_URLENCODED);
		if (mimeType == null) {
			return false;
		}
		Map<String, List<FormParameter>> formParameters = mimeType.getFormParameters();
		if (formParameters == null || formParameters.size() == 0) {
			return false;
		}
		return true;
	}

	@Override
	protected List<String> getEntityDefinition(Adapter adapter) {

		List<JsonParam> jsonParams = Lists.newArrayList();
		// URI
		jsonParams.addAll(Tool.getUriParams(adapter));
		// FORM
		jsonParams.addAll(Tool.getFormParams(adapter, MIME_TYPE_URLENCODED));

		String entityName = Tool.getEntityName(adapter);

		return Tool.getClassDefinition(jsonParams, entityName, 0, true);
	}

}
