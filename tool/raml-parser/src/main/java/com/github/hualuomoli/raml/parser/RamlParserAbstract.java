package com.github.hualuomoli.raml.parser;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.raml.model.Action;
import org.raml.model.ActionType;
import org.raml.model.Raml;
import org.raml.model.Resource;
import org.raml.model.parameter.UriParameter;
import org.raml.parser.visitor.RamlDocumentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.raml.parser.exception.ParseException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 解析raml
 * @author hualuomoli
 *
 */
public abstract class RamlParserAbstract implements RamlParser {

	public static final Logger logger = LoggerFactory.getLogger(RamlParser.class);

	public static final String MIME_TYPE_URLENCODED = "application/x-www-form-urlencoded";
	public static final String MIME_TYPE_JSON = "application/json";
	// public static final String MIME_TYPE_XML = "application/xml";
	// public static final String MIME_TYPE_MULTIPART = "multipart/form-data";

	private String uriPrefix;
	private String outputFilepath;

	@Override
	public void parse(String ramlResourceLocation) throws ParseException {
		try {
			Raml raml = new RamlDocumentBuilder().build(ramlResourceLocation);
			this.parse(raml);
		} catch (Exception e) {
			throw new ParseException(e);
		}
	}

	@Override
	public void parse(Raml raml) throws ParseException {
		// 第一级设置相对目录,如 /raml/api/user 相对目录为user,/raml/api/order/product 相对目录为order/product
		Map<String, Resource> resources = raml.getResources();
		if (resources == null || resources.size() == 0) {
			throw new ParseException("please select valid raml file");
		}

		for (String rootRelativeUri : resources.keySet()) {
			Resource resource = resources.get(rootRelativeUri);
			if (logger.isInfoEnabled()) {
				logger.info("parsing {}", rootRelativeUri);
			}
			// create resource
			this.create(resource, resource.getRelativeUri(), resource.getUriParameters());
		}

	}

	/**
	 * 生成
	 * @param resource 资源
	 * @param parentRelativeUri 父uri
	 * @param parentUriParameters 父uri参数
	 */
	private void create(Resource resource, String parentRelativeUri, Map<String, UriParameter> parentUriParameters) throws ParseException {
		Map<String, Resource> resources = resource.getResources();
		Map<ActionType, Action> actions = resource.getActions();

		Map<String, Resource> noChildResources = Maps.newHashMap();
		Map<String, Resource> hasChildResources = Maps.newHashMap();

		// 分析包含子资源和不包含子资源的uri
		for (String relativeUri : resources.keySet()) {
			Resource r = resources.get(relativeUri);

			if (this.hasChild(r)) {
				hasChildResources.put(relativeUri, r);
			} else {
				noChildResources.put(relativeUri, r);
			}
		}

		// 处理没有子资源的resource
		// config
		this.config(actions, noChildResources, parentRelativeUri, parentUriParameters, resource);
		// 创建server或者client
		this.createFile(actions, noChildResources, parentRelativeUri, parentUriParameters, resource);

		// 处理有子资源的resource
		if (hasChildResources.size() == 0) {
			return;
		}

		// create
		for (String relativeUri : hasChildResources.keySet()) {

			Resource r = hasChildResources.get(relativeUri);

			// 新的parentRelativeUri,parentUriParameters
			String uri = this.getUri(r, parentRelativeUri);
			Map<String, UriParameter> params = this.getUriParameters(r, parentUriParameters);

			this.create(r, uri, params);
		}

	}

	/**
	 * 配置server或者client
	 * @param actions 功能
	 * @param noChildResources 没有子资源的resource资源
	 * @param parentRelativeUri 父相对uri
	 * @param parentUriParameters 父uri参数
	 * @param resource 本资源
	 */
	public abstract void config(Map<ActionType, Action> actions, Map<String, Resource> noChildResources, String parentRelativeUri,
			Map<String, UriParameter> parentUriParameters, Resource resource) throws ParseException;

	/**
	 * 创建server或者client
	 * @param actions 功能
	 * @param noChildResources 没有子资源的resource资源
	 * @param parentRelativeUri 父相对uri
	 * @param parentUriParameters 父uri参数
	 * @param resource 本资源
	 */
	public void createFile(Map<ActionType, Action> actions, Map<String, Resource> noChildResources, String parentRelativeUri,
			Map<String, UriParameter> parentUriParameters, Resource resource) throws ParseException {

		if (logger.isInfoEnabled()) {
			logger.info(parentRelativeUri);
		}

		List<String> actionDatas = Lists.newArrayList();

		// get action data
		for (ActionType actionType : actions.keySet()) {
			Action action = actions.get(actionType);
			String actionData = this.getData(action, "", parentUriParameters, resource);
			actionDatas.add(actionData);
		}

		// no child resource
		for (String relativeUri : noChildResources.keySet()) {
			Resource r = noChildResources.get(relativeUri);
			if (this.hasChild(r)) {
				throw new ParseException("please check parameter.");
			}
			// 新的parentRelativeUri,parentUriParameters
			String uri = r.getRelativeUri();
			Map<String, UriParameter> params = this.getUriParameters(r, parentUriParameters);

			// resource actions
			Map<ActionType, Action> as = r.getActions();
			for (ActionType actionType : as.keySet()) {
				Action action = as.get(actionType);
				String actionData = this.getData(action, uri, params, r);
				actionDatas.add(actionData);
			}
		}

		this.createFile(actionDatas, resource);

	}

	/**
	 * 创建server或者client
	 * @param actionDatas 事件数据集合
	 * @param resource 本资源
	 */
	public abstract void createFile(List<String> actionDatas, Resource resource);

	/**
	 * 获取事件数据
	 * @param action 事件
	 * @param relativeUri 相对URI
	 * @param uriParameters URI参数
	 * @param resource 本资源
	 * @return 事件数据
	 */
	public abstract String getData(Action action, String relativeUri, Map<String, UriParameter> uriParameters, Resource resource);

	/**
	 * 获取本资源URI参数
	 * @param resource 本资源
	 * @param parentUriParameters 父资源URI参数
	 * @return 本资源URI参数
	 */
	public Map<String, UriParameter> getUriParameters(Resource resource, Map<String, UriParameter> parentUriParameters) {
		Map<String, UriParameter> maps = Maps.newHashMap(resource.getUriParameters());
		if (parentUriParameters == null || parentUriParameters.size() == 0) {
			maps.putAll(parentUriParameters);
		}
		return maps;
	}

	/**
	 * 获取本资源URI
	 * @param resource 本资源
	 * @param parentRelativeUri 父URI
	 * @return 本资源URI
	 */
	public String getUri(Resource resource, String parentRelativeUri) {
		if (StringUtils.isEmpty(parentRelativeUri)) {
			return resource.getRelativeUri();
		}
		return parentRelativeUri + resource.getRelativeUri();
	}

	/**
	 * 是否有子资源 action resource
	 * @param resource 资源
	 * @return 如果含有action或者resource返回true,否则返回false
	 */
	public boolean hasChild(Resource resource) {
		Map<String, Resource> resources = resource.getResources();
		return resources != null && resources.size() > 0;
	}

	public String getUriPrefix() {
		return uriPrefix;
	}

	public void setUriPrefix(String uriPrefix) {
		this.uriPrefix = uriPrefix;
	}

	public String getOutputFilepath() {
		return outputFilepath;
	}

	public void setOutputFilepath(String outputFilepath) {
		this.outputFilepath = outputFilepath;
	}

}
