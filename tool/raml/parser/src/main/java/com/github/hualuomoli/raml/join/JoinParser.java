package com.github.hualuomoli.raml.join;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.raml.model.Action;
import org.raml.model.ActionType;
import org.raml.model.MimeType;
import org.raml.model.Resource;
import org.raml.model.parameter.QueryParameter;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.raml.ParserAbstract;
import com.github.hualuomoli.raml.join.adaptor.util.AdaptorUtils.Translate;
import com.github.hualuomoli.raml.util.ParserUtils;
import com.github.hualuomoli.raml.util.ParserUtils.ResponseSM;
import com.google.common.collect.Lists;

/**
 * 连接字符串解析器
 * @author hualuomoli
 *
 */
public abstract class JoinParser extends ParserAbstract {

	public static final String LINE = "\n";

	protected JavaConfig javaConfig;
	private List<Adaptor> adaptors;

	@Override
	protected void setConfig(Config config) {
		super.setConfig(config);
		this.javaConfig = (JavaConfig) config;
	}

	public void setAdaptors(List<Adaptor> adaptors) {
		this.adaptors = adaptors;
	}

	@Override
	protected void create(Set<Action> actions, Set<Resource> leafResources, String fileUri, Set<UriParameter> fileUriParameters) {

		if (logger.isInfoEnabled()) {
			logger.info("create file, uri is {}", fileUri);
		}

		// 获取方法的数据集合
		List<String> actionDatas = Lists.newArrayList();

		// get action data
		actionDatas.addAll(this.getActionDatas(actions, StringUtils.EMPTY, new HashSet<UriParameter>(), fileUri, fileUriParameters));

		// no child resource
		for (Resource resource : leafResources) {

			// 方法的URI和方法的URI参数
			String methodUri = ParserUtils.getRelativeUri(resource);
			Set<UriParameter> methodUriParameters = ParserUtils.getUriParameters(resource);

			actionDatas.addAll(this.getActionDatas(ParserUtils.getActions(resource), methodUri, methodUriParameters, fileUri, fileUriParameters));
		}

		// 组装数据
		String fileData = this.assable(actionDatas, fileUri, fileUriParameters);
		// 写文件
		this.createFile(fileData, fileUri);
		// 配置文件
		this.configFile(fileUri);
	}

	/**
	 * 获取文件头
	 * @param fileUri 文件URI
	 * @param fileUriParameters 文件URI参数
	 * @return 文件头
	 */
	public abstract String getFileHeader(String fileUri, Set<UriParameter> fileUriParameters);

	/**
	 * 获取文件尾
	 * @return 文件尾
	 */
	public abstract String getFileFooter();

	/**
	 * 组装文件数据
	 * @param actionDatas Action的数据
	 * @param fileUri 文件URI
	 * @param fileUriParameters 文件URI参数
	 */
	private String assable(List<String> actionDatas, String fileUri, Set<UriParameter> fileUriParameters) {

		// 获取头部尾部
		String header = this.getFileHeader(fileUri, fileUriParameters);
		String footer = this.getFileFooter();

		// 组装
		StringBuilder buffer = new StringBuilder();

		// add header
		buffer.append(header);

		// add method
		for (int i = 0; i < actionDatas.size(); i++) {
			buffer.append(LINE).append(actionDatas.get(i));
		}

		// add footer
		buffer.append(LINE).append(footer);

		return buffer.toString();
	}

	/**
	 * 生成文件
	 * @param fileData 文件数据
	 * @param fileUri 文件URI
	 */
	private void createFile(String fileData, String fileUri) {
		String filepath = this.getFilePath(fileUri);
		String filename = this.getFileName(fileUri);

		// 生成文件
		this.createFile(filepath, filename, fileData);
	}

	/**
	 * 获取文件相对路径(相对输出目录)
	 * @param fileUri 文件URI
	 * @return 文件相对路径
	 */
	public abstract String getFilePath(String fileUri);

	/**
	 * 获取文件名
	 * @param fileUri 文件URI
	 * @return 文件名
	 */
	public abstract String getFileName(String fileUri);

	/**
	 * 配置文件
	 * @param fileUri 文件URI
	 */
	public void configFile(String fileUri) {
		// do config the file in your configuration
	}

	/**
	 * 获取Action集合的数据
	 * @param actions Action集合
	 * @param methodUri 方法的URI
	 * @param methodUriParameters 方法的URI参数
	 * @param fileUri 文件的URI
	 * @param fileUriParameters 文件的URI参数
	 * @return Action集合的数据
	 */
	private List<String> getActionDatas(Set<Action> actions, String methodUri, Set<UriParameter> methodUriParameters, String fileUri,
			Set<UriParameter> fileUriParameters) {
		List<String> actionDatas = Lists.newArrayList();
		// get action data
		for (Action action : actions) {
			List<String> actionData = this.getActionData(action, methodUri, methodUriParameters, fileUri, fileUriParameters);
			actionDatas.addAll(actionData);
		}
		return actionDatas;
	}

	/**
	 * 获取Action数据
	 * @param action Action
	 * @param methodUri 方法的URI
	 * @param methodUriParameters 方法的URI参数
	 * @param fileUri 文件的URI
	 * @param fileUriParameters 文件的URI参数
	 * @return Action数据
	 */
	public List<String> getActionData(Action action, String methodUri, Set<UriParameter> methodUriParameters, String fileUri,
			Set<UriParameter> fileUriParameters) {

		List<String> methodDatas = Lists.newArrayList();

		ActionType actionType = action.getType();
		Set<QueryParameter> queryParameters = ParserUtils.getQueryParameters(action);

		Set<MimeType> formMimeTypes = ParserUtils.getFormMimeTypes(action);
		Set<ResponseSM> responseSMs = ParserUtils.getResponseSM(action);

		// if empty add null
		if (formMimeTypes.size() == 0) {
			formMimeTypes.add(null);
		}
		if (responseSMs.size() == 0) {
			responseSMs.add(null);
		}

		for (MimeType formMimeType : formMimeTypes) {
			for (ResponseSM responseSM : responseSMs) {
				Adaptor adaptor = this.getAdaptor(actionType, queryParameters, formMimeType, responseSM);
				if (adaptor == null) {
					logger.warn("there is no adaptor for {} {}", formMimeType, responseSM);
				} else {
					Translate translate = new Translate(actionType, formMimeType, responseSM, action, methodUri, methodUriParameters, fileUri,
							fileUriParameters);
					String methodData = adaptor.getActionData(translate);
					methodDatas.add(methodData);
				}
			}
		}

		return methodDatas;
	}

	/**
	 * 获取适配器
	 * @param actionType Action 类型
	 * @param queryParameters 查询参数
	 * @param formMimeType 表单MimeType
	 * @param responseSM 响应状态和MimeType
	 * @return 适配器
	 */
	private Adaptor getAdaptor(ActionType actionType, Set<QueryParameter> queryParameters, MimeType formMimeType, ResponseSM responseSM) {
		for (Adaptor adaptor : adaptors) {
			if (adaptor.support(actionType, queryParameters, formMimeType, responseSM)) {
				return adaptor;
			}
		}
		return null;
	}

	// java 配置
	public static class JavaConfig extends Config {

		private String author; // 作者
		private double version; // 版本
		private String projectName; // 项目名称
		private String rootPackageName; // 根包名

		public JavaConfig() {
		}

		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public double getVersion() {
			return version;
		}

		public void setVersion(double version) {
			this.version = version;
		}

		public String getProjectName() {
			return projectName;
		}

		public void setProjectName(String projectName) {
			this.projectName = projectName;
		}

		public String getRootPackageName() {
			return rootPackageName;
		}

		public void setRootPackageName(String rootPackageName) {
			this.rootPackageName = rootPackageName;
		}

	}

}
