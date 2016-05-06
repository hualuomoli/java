package com.github.hualuomoli.raml.join.adaptor.mocha;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.raml.model.ActionType;
import org.raml.model.MimeType;

import com.github.hualuomoli.raml.join.JoinParser.Adapter;
import com.github.hualuomoli.raml.join.adaptor.MochaActionAdaptor;
import com.github.hualuomoli.raml.util.RamlUtils;
import com.google.common.collect.Lists;

/**
 * POST JSON
 * @author hualuomoli
 *
 */
public class MochaPostJsonActionAdaptor extends MochaActionAdaptor {

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
	public List<String> getContent(Adapter adapter) {

		List<String> datas = Lists.newArrayList();

		// request
		// .post('/api/uri')
		// .send({
		// })
		// .expect(200)
		// .end(done);

		datas.add("");
		datas.add("request");
		datas.add(RamlUtils.getIndentCharts(INDENT_CHAR, 1) + ".post('" + Tool.getRequestUri(adapter) + "')");
		// param
		String example = adapter.formMimeType.getExample();
		List<String> array = RamlUtils.splitByLine(example);

		// start
		datas.add(RamlUtils.getIndentCharts(INDENT_CHAR, 1) + ".send(" + array.get(0));
		// datas
		for (int i = 1; i < array.size() - 1; i++) {
			datas.add(RamlUtils.getIndentCharts(INDENT_CHAR, 2) + array.get(i));
		}
		// end
		datas.add(RamlUtils.getIndentCharts(INDENT_CHAR, 1) + array.get(array.size() - 1) + ")");

		datas.add(RamlUtils.getIndentCharts(INDENT_CHAR, 1) + ".expect(200)");

		datas.add(RamlUtils.getIndentCharts(INDENT_CHAR, 1) + ".end(done);");

		return datas;
	}
}
