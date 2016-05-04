package com.github.hualuomoli.raml;

import java.io.File;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.raml.model.Action;
import org.raml.model.Raml;
import org.raml.model.Resource;
import org.raml.model.parameter.UriParameter;
import org.raml.parser.visitor.RamlDocumentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.raml.exception.ParserException;
import com.github.hualuomoli.raml.util.ParserUtils;
import com.google.common.collect.Sets;

/**
 * 抽象类,实现基本的操作
 * @author hualuomoli
 *
 */
public abstract class ParserAbstract implements Parser {

	public static final Logger logger = LoggerFactory.getLogger(Parser.class);

	protected Config config;

	@Override
	public void init(Config config) {

		// set config
		setConfig(config);

		// valid
		this.valid();
		// clear output folder
		this.clearFolder();
		// init output folder
		this.initFolder();

	}

	// 设置配置文件
	protected void setConfig(Config config) {
		this.config = config;
	}

	/** 配置参数是否有效 */
	private boolean valid() {
		String outputFilepath = config.getOutputFilepath();
		if (StringUtils.isEmpty(outputFilepath)) {
			return false;
		}

		return true;
	}

	// clear output folder
	private void initFolder() {
		String outputFilepath = config.getOutputFilepath();
		File dir = new File(outputFilepath);
		if (!dir.exists()) {
			boolean success = dir.mkdirs();
			if (!success) {
				throw new ParserException("can not create folder " + dir.getAbsolutePath());
			}
		}
	}

	// clear output folder
	private void clearFolder() {
		if (!config.isClear()) {
			return;
		}
		String outputFilepath = config.getOutputFilepath();
		File dir = new File(outputFilepath);
		if (dir.exists()) {
			try {
				FileUtils.forceDelete(dir);
			} catch (Exception e) {
				throw new ParserException(e);
			}

		}
	}

	@Override
	public void execute(String[] ramlResources) {
		if (ramlResources == null || ramlResources.length == 0) {
			return;
		}

		Raml[] ramls = new Raml[ramlResources.length];

		for (int i = 0; i < ramlResources.length; i++) {
			ramls[i] = new RamlDocumentBuilder().build(ramlResources[i]);
		}
		// 执行解析
		this.execute(ramls);
		// 配置
		this.configure(ramls);

	}

	/**
	 * 解析
	 * @param raml RAML
	 */
	private void execute(Raml[] ramls) {
		for (int i = 0; i < ramls.length; i++) {
			this.execute(ramls[i]);
		}
	}

	// 配置
	protected void configure(Raml[] ramls) {
	}

	/**
	 * 解析
	 * @param raml RAML
	 */
	private void execute(Raml raml) {
		// get raml's resource
		Map<String, Resource> resources = raml.getResources();
		if (resources == null || resources.size() == 0) {
			return;
		}

		// deal root resource
		for (String rootRelativeUri : resources.keySet()) {
			Resource rootResource = resources.get(rootRelativeUri);
			if (logger.isInfoEnabled()) {
				logger.info("parsing {}", rootRelativeUri);
			}
			// execute
			this.execute(rootResource);

		}

	}

	/**
	 * 解析跟资源
	 * @param resource 跟资源
	 */
	private void execute(Resource rootResource) {
		if (ParserUtils.isLeaf(rootResource)) {
			return;
		}
		this.execute(rootResource, ParserUtils.getRelativeUri(rootResource), ParserUtils.getUriParameters(rootResource), rootResource.getDisplayName());
	}

	/**
	 * 解析非叶子节点的资源
	 * @param notLeafResource 非叶子节点的资源
	 * @param parentFullRelativeUri 父资源的全路径 
	 * @param parentFullUriParameters 父资源的全路径参数
	 * @param parentFullDescripiton 父资源的描述(级别之间使用 - 分割)
	 */
	private void execute(Resource notLeafResource, String parentFullRelativeUri, Set<UriParameter> parentFullUriParameters, String parentFullDescripiton) {

		Set<Action> actions = ParserUtils.getActions(notLeafResource);
		// 叶子资源
		Set<Resource> leafResources = ParserUtils.getLeafResources(notLeafResource);
		// 非叶子资源
		Set<Resource> notLeafResources = ParserUtils.getNotLeafResources(notLeafResource);

		//
		// String fullRelativeUri = this.getNewFullRelativeUri(parentFullRelativeUri, notLeafResource);
		// Set<UriParameter> fullUriParameters = this.getNewFullUriParameters(parentFullUriParameters, notLeafResource);

		// 处理没有子资源的resource
		if (leafResources.size() > 0 || (actions != null && actions.size() > 0)) {
			// 创建server或者client
			this.create(actions, leafResources, parentFullRelativeUri, parentFullUriParameters, parentFullDescripiton);
		}

		// create
		for (Resource r : notLeafResources) {
			String newFullRelativeUri = this.getNewFullRelativeUri(parentFullRelativeUri, r);
			Set<UriParameter> newFullUriParameters = this.getNewFullUriParameters(parentFullUriParameters, r);
			String newParentFullDescripiton = parentFullDescripiton + "" + r.getDisplayName();
			this.execute(r, newFullRelativeUri, newFullUriParameters, newParentFullDescripiton);
		}

	}

	/**
	 * 获取新的URI全路径
	 * @param parentFullRelativeUri 父URI全路经
	 * @param resource 新资源
	 * @return 新的URI全路径
	 */
	protected String getNewFullRelativeUri(String parentFullRelativeUri, Resource resource) {
		return parentFullRelativeUri + ParserUtils.getRelativeUri(resource);
	}

	/**
	 * 获取新的URI全路径参数
	 * @param parentFullRelativeUri 父URI全路经参数
	 * @param resource 新资源
	 * @return 新的URI全路径参数
	 */
	protected Set<UriParameter> getNewFullUriParameters(Set<UriParameter> parentFullUriParameters, Resource resource) {
		Set<UriParameter> newFullUriParameters = Sets.newHashSet();
		newFullUriParameters.addAll(parentFullUriParameters);
		newFullUriParameters.addAll(ParserUtils.getUriParameters(resource));
		return newFullUriParameters;
	}

	/**
	 * 创建文件
	 * @param actions Action集合
	 * @param leafResources 叶子节点资源
	 * @param fileUri 文件的URI
	 * @param fileUriParameters 文件的URI参数
	 * @param fileDescription 文件的描述
	 */
	protected abstract void create(Set<Action> actions, Set<Resource> leafResources, String fileUri, Set<UriParameter> fileUriParameters,
			String fileDescription);

	/**
	 * 生成文件
	 * @param filepath 文件路径
	 * @param filename 文件名
	 * @param data 数据
	 */
	public void createFile(String filepath, String filename, String data) {
		File dir = new File(config.getOutputFilepath(), filepath);
		if (!dir.exists()) {
			boolean success = dir.mkdirs();
			if (!success) {
				throw new ParserException("can not create folder " + dir.getAbsolutePath());
			}
		}

		File file = new File(dir.getAbsolutePath(), filename);
		try {
			FileUtils.write(file, data, config.getEncoding());
		} catch (Exception e) {
			throw new ParserException(e);
		}
	}

}
