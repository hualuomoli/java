package com.github.hualuomoli.tool.raml.join.adaptor;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.raml.model.ParamType;
import org.raml.model.parameter.AbstractParam;
import org.raml.model.parameter.FormParameter;
import org.raml.model.parameter.QueryParameter;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.tool.raml.join.JoinParser.Adapter;
import com.github.hualuomoli.tool.raml.util.RamlUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * Mocha适配器
 * @author hualuomoli
 *
 */
public abstract class MochaActionAdaptor implements ActionAdaptor {

	public static final String INDENT_CHAR = "  ";

	@Override
	public List<String> getDatas(Adapter adapter) {

		List<String> datas = Lists.newArrayList();

		List<String> note = this.getNote(adapter);
		List<String> header = this.getHeader(adapter);
		List<String> content = this.getContent(adapter);
		List<String> footer = this.getFooter(adapter);

		datas.addAll(RamlUtils.getIndentDatas(note, INDENT_CHAR, 1));
		datas.addAll(RamlUtils.getIndentDatas(header, INDENT_CHAR, 1));
		datas.addAll(RamlUtils.getIndentDatas(content, INDENT_CHAR, 2));
		datas.addAll(RamlUtils.getIndentDatas(footer, INDENT_CHAR, 1));

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
		String descripton = adapter.action.getDescription();
		if (StringUtils.isBlank(descripton)) {
			descripton = adapter.action.getResource().getDescription();
		}
		// it('没有参数', function (done) {
		datas.add("it('" + descripton + "', function (done) {");

		return datas;
	}

	/**
	 * 获取方法内容
	 * @param adapter 适配者
	 * @return 方法内容
	 */
	public abstract List<String> getContent(Adapter adapter);

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
	protected static class Tool {

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
				i++;
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
		public static Set<String> getFileParams(Adapter adapter) {

			Set<String> files = Sets.newHashSet();

			// form
			if (adapter.formMimeType != null) {
				Map<String, List<FormParameter>> formParameters = adapter.formMimeType.getFormParameters();
				for (String displayName : formParameters.keySet()) {
					List<FormParameter> list = formParameters.get(displayName);
					if (list != null && list.size() > 0) {
						FormParameter param = list.get(0);
						if (param.getType() == ParamType.FILE) {
							files.add(param.getDisplayName());
						}
					}
				}
			}

			return files;

		}

		// end

	}

}
