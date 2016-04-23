package com.github.hualuomoli.raml.parser;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.io.FileUtils;
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
 * 解析RAML
 * @author hualuomoli
 *
 */
public abstract class RamlParserAbstract implements RamlParser {

	public static final Logger logger = LoggerFactory.getLogger(RamlParser.class);
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");

	private boolean clearBeforeFlush = false; // 强制清除输出目录
	private String outputFilepath; // 输出目录
	private String version = "1.0"; // 版本号

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
		// 清除输出目录
		this.clear();
		// this.createOutputFilepath();
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
	 * @param parentFullUri 父URI
	 * @param parentFullUriParameters 父URI参数
	 */
	private void create(Resource resource, String parentFullUri, Map<String, UriParameter> parentFullUriParameters) throws ParseException {
		Map<String, Resource> resources = resource.getResources();
		Map<ActionType, Action> actions = resource.getActions();

		Map<String, Resource> noChildResources = Maps.newHashMap();
		Map<String, Resource> hasChildResources = Maps.newHashMap();

		// 分析包含子资源和不包含子资源的uri
		for (String relativeUri : resources.keySet()) {
			Resource r = resources.get(relativeUri);
			if (this.hasChildResources(r)) {
				hasChildResources.put(relativeUri, r);
			} else {
				noChildResources.put(relativeUri, r);
			}
		}

		// 处理没有子资源的resource
		if (noChildResources.size() > 0 || (actions != null && actions.size() > 0)) {
			// 创建server或者client
			this.createFile(actions, noChildResources, parentFullUri, parentFullUriParameters, resource);
			// 配置server或者client
			this.configFile(actions, noChildResources, parentFullUri, parentFullUriParameters, resource);
		}

		// 处理有子资源的resource
		if (hasChildResources.size() == 0) {
			return;
		}

		// create
		for (String relativeUri : hasChildResources.keySet()) {

			Resource r = hasChildResources.get(relativeUri);

			// 新的parentRelativeUri,parentUriParameters
			String uri = this.getFullUri(r, parentFullUri);
			Map<String, UriParameter> params = this.getFullUriParameters(r, parentFullUriParameters);

			this.create(r, uri, params);
		}

	}

	/**
	 * 创建server或者client
	 * @param actions 功能
	 * @param noChildResources 没有子资源的resource资源
	 * @param parentFullUri 父URI
	 * @param parentFullUriParameters 父URI参数
	 * @param resource 本资源
	 */
	public abstract void createFile(Map<ActionType, Action> actions, Map<String, Resource> noChildResources, String parentFullUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException;

	/**
	 * 配置server或者client
	 * @param actions 功能
	 * @param noChildResources 没有子资源的resource资源
	 * @param parentFullUri 父URI
	 * @param parentFullUriParameters 父URI参数
	 * @param resource 本资源
	 */
	public abstract void configFile(Map<ActionType, Action> actions, Map<String, Resource> noChildResources, String parentFullUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException;

	/**
	 * 获取本资源URI参数
	 * @param resource 本资源
	 * @param parentFullUriParameters 父资源URI参数
	 * @return 本资源URI参数
	 */
	protected Map<String, UriParameter> getFullUriParameters(Resource resource, Map<String, UriParameter> parentFullUriParameters) {
		Map<String, UriParameter> maps = Maps.newHashMap(resource.getUriParameters());
		// 如果父资源不为空,添加
		if (parentFullUriParameters != null && parentFullUriParameters.size() > 0) {
			maps.putAll(parentFullUriParameters);
		}
		return maps;
	}

	/**
	 * 获取本资源URI
	 * @param resource 本资源
	 * @param parentFullUri 父URI
	 * @return 本资源URI
	 */
	protected String getFullUri(Resource resource, String parentFullUri) {
		if (StringUtils.isEmpty(parentFullUri)) {
			return resource.getRelativeUri();
		}
		return parentFullUri + resource.getRelativeUri();
	}

	/**
	 * 是否有子资源 resource
	 * @param resource 资源
	 * @return 如果含有resource返回true,否则返回false
	 */
	protected boolean hasChildResources(Resource resource) {
		Map<String, Resource> resources = resource.getResources();
		return resources != null && resources.size() > 0;
	}

	/**
	 * 清除输出目录
	 */
	private void clear() throws ParseException {
		if (!this.isClearBeforeFlush()) {
			return;
		}
		File dir = new File(this.getOutputFilepath());
		if (dir.exists()) {
			try {
				FileUtils.forceDelete(dir);
			} catch (IOException e) {
				throw new ParseException(e);
			}
		}
	}

	/**
	 * 按照换行分割
	 * @param data 数据
	 * @return 数据集合
	 */
	public static List<String> splitByLine(String data) {
		List<String> lines = Lists.newArrayList();

		if (StringUtils.isEmpty(data)) {
			return lines;
		}
		String[] array = data.split("\n");
		for (String line : array) {
			lines.add(line);
		}
		return lines;
	}

	/**
	 * 替换双引号 "data" --> \"data\"
	 * @param data 数据
	 * @return 替换后的数据
	 */
	public static String replaceQuotes(String data) {
		if (StringUtils.isEmpty(data)) {
			return StringUtils.EMPTY;
		}
		return data.replaceAll("\"", "\\\\\"");
	}

	public String getCurrentTime() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
		return sdf.format(new Date());
	}

	public boolean isClearBeforeFlush() {
		return clearBeforeFlush;
	}

	public void setClearBeforeFlush(boolean clearBeforeFlush) {
		this.clearBeforeFlush = clearBeforeFlush;
	}

	public String getOutputFilepath() {
		return outputFilepath;
	}

	public void setOutputFilepath(String outputFilepath) {
		this.outputFilepath = outputFilepath;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
