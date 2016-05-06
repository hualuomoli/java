package com.github.hualuomoli.raml.join.adaptor.java;

import java.util.List;

import org.raml.model.ActionType;

import com.github.hualuomoli.raml.join.JoinParser.Adapter;
import com.github.hualuomoli.raml.join.adaptor.JavaActionAdaptor;
import com.github.hualuomoli.raml.util.JSONUtils.JsonParam;
import com.google.common.collect.Lists;

/**
 * DELETE
 * @author hualuomoli
 *
 */
public class JavaDeleteActionAdaptor extends JavaActionAdaptor {

	@Override
	public boolean support(Adapter adapter) {
		return adapter.action.getType() == ActionType.DELETE;
	}

	@Override
	protected List<String> getEntityDefinition(Adapter adapter) {

		List<JsonParam> jsonParams = Lists.newArrayList();
		// URI
		jsonParams.addAll(Tool.getUriParams(adapter));
		// QUERY
		jsonParams.addAll(Tool.getQueryParams(adapter));
		// FORM
		jsonParams.addAll(Tool.getFormParams(adapter, MIME_TYPE_URLENCODED));

		String entityName = Tool.getEntityName(adapter);

		return Tool.getClassDefinition(jsonParams, entityName, 0, true);
	}

}
