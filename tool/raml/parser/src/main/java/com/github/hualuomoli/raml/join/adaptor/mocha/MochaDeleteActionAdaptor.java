package com.github.hualuomoli.raml.join.adaptor.mocha;

import java.util.List;
import java.util.Map;

import org.raml.model.ActionType;

import com.github.hualuomoli.raml.join.JoinParser.Adapter;
import com.github.hualuomoli.raml.join.adaptor.MochaActionAdaptor;
import com.github.hualuomoli.raml.util.RamlUtils;
import com.google.common.collect.Lists;

/**
 * DELETE
 * @author hualuomoli
 *
 */
public class MochaDeleteActionAdaptor extends MochaActionAdaptor {

	@Override
	public boolean support(Adapter adapter) {
		return adapter.action.getType() == ActionType.DELETE;
	}

	@Override
	public List<String> getContent(Adapter adapter) {

		List<String> datas = Lists.newArrayList();

		// request
		// .delete('/api/uri')
		// .send('name=花落莫离')
		// .expect(200)
		// .end(done);

		datas.add("");
		datas.add("request");
		datas.add(RamlUtils.getIndentCharts(INDENT_CHAR, 1) + ".delete('" + Tool.getRequestUri(adapter) + "')");
		// param
		Map<String, String> queryParameter = Tool.getQueryParams(adapter);
		for (String displayName : queryParameter.keySet()) {
			datas.add(RamlUtils.getIndentCharts(INDENT_CHAR, 1) + ".send('" + displayName + "=" + queryParameter.get(displayName) + "')");
		}
		Map<String, String> formParameter = Tool.getFormParams(adapter);
		for (String displayName : formParameter.keySet()) {
			datas.add(RamlUtils.getIndentCharts(INDENT_CHAR, 1) + ".send('" + displayName + "=" + formParameter.get(displayName) + "')");
		}

		datas.add(RamlUtils.getIndentCharts(INDENT_CHAR, 1) + ".expect(200)");

		datas.add(RamlUtils.getIndentCharts(INDENT_CHAR, 1) + ".end(done);");

		return datas;
	}
}
