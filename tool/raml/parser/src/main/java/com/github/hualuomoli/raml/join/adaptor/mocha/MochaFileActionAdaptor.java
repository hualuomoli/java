package com.github.hualuomoli.raml.join.adaptor.mocha;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.raml.model.ActionType;
import org.raml.model.MimeType;
import org.raml.model.ParamType;
import org.raml.model.parameter.FormParameter;

import com.github.hualuomoli.raml.join.JoinParser.Adapter;
import com.github.hualuomoli.raml.join.adaptor.MochaActionAdaptor;
import com.github.hualuomoli.raml.util.RamlUtils;
import com.google.common.collect.Lists;

/**
 * FILE
 * @author hualuomoli
 *
 */
public class MochaFileActionAdaptor extends MochaActionAdaptor {

	@Override
	public boolean support(Adapter adapter) {
		if (adapter.action.getType() != ActionType.POST) {
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
	public List<String> getContent(Adapter adapter) {

		List<String> datas = Lists.newArrayList();

		// request
		// .post('/api/uri')
		// .field('username', 'admin')
		// .attach('photo', path.join(__dirname, '../../favicon.ico'))
		// .expect(200)
		// .end(done);

		datas.add("");
		datas.add("request");
		datas.add(RamlUtils.getIndentCharts(INDENT_CHAR, 1) + ".post('" + Tool.getRequestUri(adapter) + "')");
		// param
		Map<String, String> formParameter = Tool.getFormParams(adapter);
		for (String displayName : formParameter.keySet()) {
			datas.add(RamlUtils.getIndentCharts(INDENT_CHAR, 1) + ".field('" + displayName + "', '" + formParameter.get(displayName) + "')");
		}
		// file
		Set<String> files = Tool.getFileParams(adapter);
		for (String displayName : files) {
			datas.add(RamlUtils.getIndentCharts(INDENT_CHAR, 1) + ".attach('" + displayName + "', path.join(__dirname, '../../favicon.ico'))");
		}

		datas.add(RamlUtils.getIndentCharts(INDENT_CHAR, 1) + ".expect(200)");

		datas.add(RamlUtils.getIndentCharts(INDENT_CHAR, 1) + ".end(done);");

		return datas;
	}
}
