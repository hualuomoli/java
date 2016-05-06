package com.github.hualuomoli.raml.join.adaptor;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.raml.model.ParamType;
import org.raml.model.parameter.AbstractParam;
import org.raml.model.parameter.FormParameter;
import org.raml.model.parameter.QueryParameter;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.raml.join.JoinParser.Adapter;
import com.github.hualuomoli.raml.util.RamlUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Mocha适配器
 * @author hualuomoli
 *
 */
public abstract class MochaActionAdaptor implements ActionAdaptor {

	public static final String INDENT_CHAR = " ";

	@Override
	public List<String> getDatas(Adapter adapter) {

		List<String> datas = Lists.newArrayList();

		List<String> note = this.getNote(adapter);
		List<String> header = this.getHeader(adapter);
		List<String> content = this.getContent(adapter);
		List<String> footer = this.getFooter(adapter);

		datas.addAll(note);
		datas.addAll(header);
		datas.addAll(content);
		datas.addAll(footer);

		return datas;
	}

	/**
	 * 获取方法注释
	 * @param adapter 适配者
	 * @return 方法注释
	 */
	public List<String> getNote(Adapter adapter) {
		List<String> datas = Lists.newArrayList();

		return datas;
	}

	/**
	 * 获取方法头部
	 * @param adapter 适配者
	 * @return 方法头部
	 */
	public List<String> getHeader(Adapter adapter) {
		List<String> datas = Lists.newArrayList();

		datas.add("");
		// it('没有参数', function (done) {
		datas.add("it('" + adapter.action.getResource().getDescription() + "', function (done) {");

		return datas;
	}

	/**
	 * 获取方法内容
	 * @param adapter 适配者
	 * @return 方法内容
	 */
	public List<String> getContent(Adapter adapter) {

		List<String> datas = Lists.newArrayList();

		// request
		// .post('/api/uri')
		// .set('content-type', 'application/json')
		// .send({
		// })
		// .expect(200)
		// .expect(function (res) {
		// res.body.code.should.equal('0');
		// })
		// .end(done);

		datas.add("");
		datas.add("request");
		datas.add(RamlUtils.getIndentCharts(INDENT_CHAR, 1) + "." + adapter.action.getType().toString().toLowerCase() + "('" + Tool.getRequestUri(adapter)
				+ "')");
		// param
		Map<String, String> queryParams;
		Map<String, String> formParams;

		switch (adapter.action.getType()) {
		case GET:
			queryParams = Tool.getQueryParams(adapter);
			for (String key : queryParams.keySet()) {
				datas.add(".query('" + key + " = ' +  encodeURIComponent('" + queryParams.get(key) + "'))");
			}
		case DELETE:
			queryParams = Tool.getQueryParams(adapter);
			formParams = Tool.getFormParams(adapter);

			if (queryParams.size() > 0) {
				for (String key : queryParams.keySet()) {
					datas.add(".query('" + key + "=' +  encodeURIComponent('" + queryParams.get(key) + "'))");
				}
			} else {
				for (String key : formParams.keySet()) {
					datas.add(".send('" + key + "=" + formParams.get(key) + "')");
				}
			}
			break;
		case POST:
		case PUT:
			if (adapter.formMimeType == null) {
				break;
			}
			if (StringUtils.equals(adapter.formMimeType.getType(), MIME_TYPE_URLENCODED)) {
				formParams = Tool.getFormParams(adapter);
				for (String key : formParams.keySet()) {
					datas.add(".send('" + key + "=" + formParams.get(key) + "')");
				}
			} else if (StringUtils.equals(adapter.formMimeType.getType(), MIME_TYPE_JSON)) {
				String example = adapter.formMimeType.getExample();
				datas.add(".send(" + example + ")");
			} else if (StringUtils.equals(adapter.formMimeType.getType(), MIME_TYPE_MULTIPART)) {
				formParams = Tool.getFormParams(adapter);
				for (String key : formParams.keySet()) {
					datas.add(".field('" + key + "', '" + formParams.get(key) + "')");
				}
				Map<String, String> fileParams = Tool.getFileParams(adapter);
				for (String key : fileParams.keySet()) {
					datas.add(".attach('" + key + "', path.join(__dirname, '" + formParams.get(key) + "'))");
				}
			}
			break;
		default:
			break;
		}

		datas.add(".expect(200)");

		datas.add(".end(done);");

		return datas;
	}

	/**
	 * 获取方法尾部
	 * @param adapter 适配者
	 * @return 方法尾部
	 */
	public List<String> getFooter(Adapter adapter) {
		List<String> datas = Lists.newArrayList();

		datas.add("});");

		return datas;
	}

	// 工具
	static class Tool {

		/**
		 * 获取请求的URI
		 * @param adapter 适配者
		 * @return 请求的URI
		 */
		public static String getRequestUri(Adapter adapter) {
			String uri = RamlUtils.getFullUri(adapter.action);
			Map<String, UriParameter> uriParameters = RamlUtils.getFullUriParameter(adapter.action);

			String[] searchList = new String[uriParameters.size()];
			String[] replacementList = new String[uriParameters.size()];

			int i = 0;
			for (String displayName : uriParameters.keySet()) {
				UriParameter uriParameter = uriParameters.get(displayName);
				searchList[i] = "{" + displayName + "}";
				replacementList[i] = uriParameter.getExample();
			}

			return StringUtils.replaceEach(uri, searchList, replacementList);
		}

		/**
		 * 获取请求参数
		 * @param adapter 适配这
		 * @return 请求参数
		 */
		public static Map<String, String> getQueryParams(Adapter adapter) {

			Map<String, String> params = Maps.newHashMap();

			// query
			Map<String, QueryParameter> queryParameters = adapter.action.getQueryParameters();
			if (queryParameters != null && queryParameters.size() > 0) {
				for (AbstractParam param : queryParameters.values()) {
					params.put(param.getDisplayName(), param.getExample());
				}
			}

			return params;

		}

		/**
		 * 获取请求参数
		 * @param adapter 适配这
		 * @return 请求参数
		 */
		public static Map<String, String> getFormParams(Adapter adapter) {

			Map<String, String> params = Maps.newHashMap();

			// form
			if (adapter.formMimeType != null) {
				Map<String, List<FormParameter>> formParameters = adapter.formMimeType.getFormParameters();
				for (String displayName : formParameters.keySet()) {
					List<FormParameter> list = formParameters.get(displayName);
					if (list != null && list.size() > 0) {
						FormParameter param = list.get(0);
						if (param.getType() != ParamType.FILE) {
							params.put(param.getDisplayName(), param.getExample());
						}
					}
				}
			}

			return params;

		}

		/**
		 * 获取请求参数
		 * @param adapter 适配这
		 * @return 请求参数
		 */
		public static Map<String, String> getFileParams(Adapter adapter) {

			Map<String, String> params = Maps.newHashMap();

			// form
			if (adapter.formMimeType != null) {
				Map<String, List<FormParameter>> formParameters = adapter.formMimeType.getFormParameters();
				for (String displayName : formParameters.keySet()) {
					List<FormParameter> list = formParameters.get(displayName);
					if (list != null && list.size() > 0) {
						FormParameter param = list.get(0);
						if (param.getType() == ParamType.FILE) {
							params.put(param.getDisplayName(), "../request");
						}
					}
				}
			}

			return params;

		}

		// end

	}

}
