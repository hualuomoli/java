package com.github.hualuomoli.tool.raml.join.adaptor.mocha;

import java.util.List;
import java.util.Map;

import org.raml.model.ActionType;

import com.github.hualuomoli.tool.raml.join.JoinParser.Adapter;
import com.github.hualuomoli.tool.raml.join.adaptor.MochaActionAdaptor;
import com.github.hualuomoli.tool.raml.util.RamlUtils;
import com.google.common.collect.Lists;

/**
 * GET
 * @author hualuomoli
 *
 */
public class MochaGetActionAdaptor extends MochaActionAdaptor {

	@Override
	public boolean support(Adapter adapter) {
		if (adapter.action.getType() != ActionType.GET) {
			return false;
		}
		if (!RamlUtils.is200JsonResponse(adapter)) {
			return false;
		}
		return true;
	}

	@Override
	public List<String> getContent(Adapter adapter) {

		List<String> datas = Lists.newArrayList();

		// request
		// .get('/api/uri')
		// .query('name=' + encodeURIComponent('花落莫离'))
		// .expect(200)
		// .end(done);

		datas.add("");
		datas.add("request");
		datas.add(RamlUtils.getIndentCharts(INDENT_CHAR, 1) + ".get('" + Tool.getRequestUri(adapter) + "')");
		// param
		Map<String, String> queryParams = Tool.getQueryParams(adapter);

		for (String displayName : queryParams.keySet()) {
			datas.add(
					RamlUtils.getIndentCharts(INDENT_CHAR, 1) + ".query('" + displayName + "=' + encodeURIComponent('" + queryParams.get(displayName) + "'))");
		}
		datas.add(RamlUtils.getIndentCharts(INDENT_CHAR, 1) + ".expect(200)");

		datas.add(RamlUtils.getIndentCharts(INDENT_CHAR, 1) + ".end(done);");

		return datas;
	}
}
