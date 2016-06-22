package com.github.hualuomoli.tool.raml.join.adaptor.java;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.raml.model.ActionType;
import org.raml.model.MimeType;
import org.raml.model.ParamType;
import org.raml.model.parameter.AbstractParam;
import org.raml.model.parameter.FormParameter;

import com.github.hualuomoli.tool.raml.join.JoinParser.Adapter;
import com.github.hualuomoli.tool.raml.join.adaptor.JavaActionAdaptor;
import com.github.hualuomoli.tool.raml.util.JSONUtils.JsonParam;
import com.github.hualuomoli.tool.raml.util.RamlUtils;
import com.google.common.collect.Lists;

/**
 * FILE
 * @author hualuomoli
 *
 */
public class JavaFileActionAdaptor extends JavaActionAdaptor {

	@Override
	public boolean support(Adapter adapter) {
		if (adapter.action.getType() != ActionType.POST) {
			return false;
		}
		if (!RamlUtils.isEmptyOr200JsonResponse(adapter)) {
			return false;
		}
		Map<String, MimeType> body = adapter.action.getBody();
		if (body == null) {
			return false;
		}
		MimeType mimeType = body.get(MIME_TYPE_MULTIPART);
		if (mimeType == null) {
			return false;
		}
		Map<String, List<FormParameter>> formParameters = mimeType.getFormParameters();
		if (formParameters == null || formParameters.size() == 0) {
			return false;
		}
		boolean hasFile = false;
		for (String displayName : formParameters.keySet()) {
			List<FormParameter> list = formParameters.get(displayName);
			if (list == null || list.size() == 0) {
				continue;
			}
			FormParameter formParameter = list.get(0);
			if (formParameter.getType() == ParamType.FILE) {
				hasFile = true;
				break;
			}
		}
		if (!hasFile) {
			return false;
		}

		return true;
	}

	@Override
	protected List<String> getNote(Adapter adapter) {
		List<String> datas = Lists.newArrayList();

		// 实体类的名称
		String entityName = Tool.getEntityName(adapter);

		// start
		datas.add("/**");

		// 实体类定义
		datas.add(" * @param " + RamlUtils.unCap(entityName) + " " + RamlUtils.dealDescription(adapter.action.getDescription()));

		// files
		Set<FormParameter> fileParams = Tool.getFileParameters(adapter.formMimeType);
		for (AbstractParam fileParam : fileParams) {
			datas.add(" * @param " + fileParam.getDisplayName() + " " + RamlUtils.dealDescription(fileParam.getDescription()));
		}

		// springmvc
		datas.add(" * @param request HttpServletRequest");
		datas.add(" * @param response HttpServletResponse");
		datas.add(" * @param model Model");

		// end
		datas.add(" */");

		return datas;
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
		buffer.append(entityName).append(" ").append(RamlUtils.unCap(entityName));
		// files
		Set<FormParameter> fileParams = Tool.getFileParameters(adapter.formMimeType);
		for (AbstractParam fileParam : fileParams) {
			buffer.append(", ");
			// @RequestParam(value = "", required = false)
			buffer.append("@RequestParam(value = \"").append(fileParam.getDisplayName()).append("\"");
			buffer.append(", required = ").append(fileParam.isRequired() ? "true" : "false");
			buffer.append(") ");

			// MultipartFile file
			buffer.append("MultipartFile").append(fileParam.isRepeat() ? "[]" : "").append(" ").append(fileParam.getDisplayName());
		}
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

		List<JsonParam> jsonParams = Lists.newArrayList();
		// URI
		jsonParams.addAll(Tool.getUriParams(adapter));
		// FORM
		jsonParams.addAll(Tool.getFormParams(adapter, MIME_TYPE_MULTIPART));

		String entityName = Tool.getEntityName(adapter);

		return Tool.getClassDefinition(jsonParams, entityName, 0, true);
	}

}
