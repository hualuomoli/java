package com.github.hualuomoli.raml.join.adaptor.java;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.raml.model.ActionType;
import org.raml.model.MimeType;

import com.github.hualuomoli.raml.join.JoinParser.Adapter;
import com.github.hualuomoli.raml.join.adaptor.JavaActionAdaptor;
import com.github.hualuomoli.raml.util.JSONUtils;
import com.github.hualuomoli.raml.util.JSONUtils.JsonParam;
import com.github.hualuomoli.raml.util.RamlUtils;
import com.google.common.collect.Lists;

/**
 * POST JSON
 * @author hualuomoli
 *
 */
public class JavaPostJsonActionAdaptor extends JavaActionAdaptor {

	@Override
	public boolean support(Adapter adapter) {
		if (adapter.action.getType() != ActionType.POST) {
			return false;
		}
		Map<String, MimeType> body = adapter.action.getBody();
		if (body == null) {
			return false;
		}
		MimeType mimeType = body.get(MIME_TYPE_JSON);
		if (mimeType == null) {
			return false;
		}
		String schema = mimeType.getSchema();
		String example = mimeType.getExample();

		if (StringUtils.isBlank(schema) && StringUtils.isBlank(example)) {
			return false;
		}

		return true;
	}

	@Override
	protected List<String> getHeader(Adapter adapter) {
		List<String> datas = Lists.newArrayList();

		String entityName = Tool.getEntityName(adapter);
		String methodName = Tool.getMethodName(adapter);

		StringBuilder buffer = new StringBuilder();

		buffer.append("public");
		buffer.append(" ");
		buffer.append(Tool.hasResult(adapter) ? "String" : "void");
		buffer.append(" ");
		buffer.append(methodName);
		buffer.append("(");

		// 实体类
		buffer.append("@RequestBody ").append(entityName).append(" ").append(RamlUtils.unCap(entityName));

		// springmvv
		buffer.append(", ");
		buffer.append("HttpServletRequest request");
		buffer.append(", ");
		buffer.append("HttpServletResponse response");
		buffer.append(", ");
		buffer.append("Model model");

		buffer.append(")");
		buffer.append(" {");

		datas.add(buffer.toString());

		return datas;
	}

	@Override
	protected List<String> getEntityDefinition(Adapter adapter) {

		List<JsonParam> jsonParams;

		// FORM
		MimeType mimeType = adapter.action.getBody().get(MIME_TYPE_JSON);
		String schema = mimeType.getSchema();
		String example = mimeType.getExample();

		if (StringUtils.isNotBlank(schema)) {
			jsonParams = JSONUtils.parseSchema(schema);
		} else {
			jsonParams = JSONUtils.parseExample(example);
		}

		// URI
		jsonParams.addAll(Tool.getUriParams(adapter));

		String entityName = Tool.getEntityName(adapter);

		return Tool.getClassDefinition(jsonParams, entityName, 0, true);
	}

}
