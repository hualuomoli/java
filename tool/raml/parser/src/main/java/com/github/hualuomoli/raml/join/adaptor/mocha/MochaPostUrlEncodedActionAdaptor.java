package com.github.hualuomoli.raml.join.adaptor.mocha;

import java.util.List;
import java.util.Map;

import org.raml.model.ActionType;
import org.raml.model.MimeType;
import org.raml.model.parameter.FormParameter;

import com.github.hualuomoli.raml.join.JoinParser.Adapter;
import com.github.hualuomoli.raml.join.adaptor.MochaActionAdaptor;
import com.github.hualuomoli.raml.util.RamlUtils;
import com.google.common.collect.Lists;

/**
 * POST URLEncoded
 * @author hualuomoli
 *
 */
public class MochaPostUrlEncodedActionAdaptor extends MochaActionAdaptor {

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
	public List<String> getContent(Adapter adapter) {

		List<String> datas = Lists.newArrayList();

		// request
		// .post('/api/uri')
		// .send('name=花落莫离')
		// .expect(200)
		// .end(done);

		datas.add("");
		datas.add("request");
		datas.add(RamlUtils.getIndentCharts(INDENT_CHAR, 1) + ".post('" + Tool.getRequestUri(adapter) + "')");
		// param
		Map<String, String> formParameter = Tool.getFormParams(adapter);

		for (String displayName : formParameter.keySet()) {
			datas.add(RamlUtils.getIndentCharts(INDENT_CHAR, 1) + ".send('" + displayName + "=" + formParameter.get(displayName) + "')");
		}
		datas.add(RamlUtils.getIndentCharts(INDENT_CHAR, 1) + ".expect(200)");

		datas.add(RamlUtils.getIndentCharts(INDENT_CHAR, 1) + ".end(done);");

		return datas;
	}
}
