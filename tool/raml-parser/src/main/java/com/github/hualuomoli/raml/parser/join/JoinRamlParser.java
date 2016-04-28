package com.github.hualuomoli.raml.parser.join;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.raml.model.Action;
import org.raml.model.ActionType;
import org.raml.model.MimeType;
import org.raml.model.Resource;
import org.raml.model.Response;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.raml.parser.RamlParserAbstract;
import com.github.hualuomoli.raml.parser.exception.ParseException;
import com.github.hualuomoli.raml.parser.join.transfer.MethodTransfer;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * 连接字符串解析器
 * @author hualuomoli
 *
 */
public abstract class JoinRamlParser extends RamlParserAbstract implements Join {

	protected String encoding = "UTF-8"; // 输出文件编码
	protected String uriPrefix; // URI的前缀,如 /api/user 希望目录不包含api
	protected String author = "hualuomoli"; // 作者,用于注释
	protected List<MethodTransfer> transferList; // 转换器

	public JoinRamlParser() {
	}

	public JoinRamlParser(List<MethodTransfer> transferList) {
		this.transferList = transferList;
	}

	@Override
	public void createFile(Map<ActionType, Action> actions, Map<String, Resource> noChildResources, String parentFullUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException {
		if (logger.isInfoEnabled()) {
			logger.info(parentFullUri);
		}

		List<String> actionDatas = Lists.newArrayList();

		// get action data
		for (ActionType actionType : actions.keySet()) {
			Action action = actions.get(actionType);
			String actionData = this.getData(action, "", parentFullUri, parentFullUriParameters, resource);
			actionDatas.add(actionData);
		}

		// no child resource
		for (String relativeUri : noChildResources.keySet()) {
			Resource r = noChildResources.get(relativeUri);
			if (this.hasChildResources(r)) {
				throw new ParseException("please check parameter.");
			}
			// 新的parentRelativeUri,parentUriParameters
			String uri = r.getRelativeUri();
			Map<String, UriParameter> params = this.getFullUriParameters(r, parentFullUriParameters);

			// resource actions
			Map<ActionType, Action> as = r.getActions();
			for (ActionType actionType : as.keySet()) {
				Action action = as.get(actionType);
				String actionData = this.getData(action, uri, parentFullUri, params, r);
				actionDatas.add(actionData);
			}
		}

		this.createFile(actionDatas, parentFullUri, parentFullUriParameters, resource);
	}

	/**
	 * 创建server或者client
	 * @param actionDatas 事件数据集合
	 * @param parentFullUri 父URI
	 * @param parentFullUriParameters 父URI参数
	 * @param resource 本资源
	 */
	private void createFile(List<String> actionDatas, String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource)
			throws ParseException {
		StringBuilder buffer = new StringBuilder();

		String serverHeader = this.getFileHeader(actionDatas, parentFullUri, parentFullUriParameters, resource);
		String serverFooter = this.getFileFooter(actionDatas, parentFullUri, parentFullUriParameters, resource);

		// header
		buffer.append(serverHeader);

		// datas
		for (String actionData : actionDatas) {
			buffer.append(actionData);
		}

		// footer
		buffer.append(serverFooter);

		this.flushFileData(buffer.toString(), parentFullUri, resource);
	}

	/**
	 * 获取文件头部
	 * @param actionDatas 事件数据(方法)
	 * @param parentFullUri 父URI
	 * @param parentFullUriParameters 父URI参数
	 * @param resource 本资源
	 * @return 文件头部
	 */
	public abstract String getFileHeader(List<String> actionDatas, String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource)
			throws ParseException;

	/**
	 * 获取文件尾部
	 * @param actionDatas 事件数据(方法)
	 * @param parentFullUri 父URI
	 * @param parentFullUriParameters 父URI参数
	 * @param resource 本资源
	 * @return 文件尾部
	 */
	public abstract String getFileFooter(List<String> actionDatas, String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource)
			throws ParseException;

	/**
	 * 输出文件数据
	 * @param data 数据
	 * @param parentFullUri 父URI
	 * @param resource 本资源
	 */
	private void flushFileData(String data, String parentFullUri, Resource resource) throws ParseException {
		// 文件路径
		String filepath = this.getFilepath();
		// 文件名
		String filename = this.getFilename(parentFullUri, resource);
		// 生成路径
		File dir = new File(outputFilepath, filepath);
		if (!dir.exists()) {
			if (!dir.mkdirs()) {
				throw new ParseException("can not create folder " + dir.getAbsolutePath());
			}
		}
		// 生成文件
		File file = new File(dir.getAbsolutePath(), filename);
		// 写数据
		try {
			FileUtils.write(file, data, Charset.forName(encoding));
		} catch (IOException e) {
			throw new ParseException(e);
		}
	}

	/**
	 * 获取文件路径
	 * @return 文件路径
	 */
	public abstract String getFilepath() throws ParseException;

	/**
	 * 获取文件名
	 * @param parentFullUri 父URI
	 * @param resource 本资源
	 * @return 文件名
	 */
	public abstract String getFilename(String parentFullUri, Resource resource) throws ParseException;

	/**
	 * 获取事件数据
	 * @param action 事件
	 * @param relativeUri 相对URI
	 * @param parentFullUri 
	 * @param parentFullUriParameters 父URI参数
	 * @param resource 本资源
	 * @return 事件数据
	 */
	public String getData(Action action, String relativeUri, String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource)
			throws ParseException {
		List<String> datas = Lists.newArrayList();

		// 请求MimeType
		Set<MimeType> queryMimeTypes = Sets.newHashSet();
		// 响应
		List<R> rs = Lists.newArrayList();

		// transferList
		Map<String, MimeType> body = action.getBody();
		Map<String, Response> responses = action.getResponses();

		// set query MimeType
		if (body != null) {
			queryMimeTypes.addAll(body.values());
		}

		// set response MimeType
		if (responses != null) {
			Set<String> statuses = responses.keySet();
			for (String status : statuses) { // status
				// get status response MimeType
				Response r = responses.get(status);
				Map<String, MimeType> b = r.getBody();
				if (b != null) {
					Collection<MimeType> vs = b.values();
					for (MimeType mimeType : vs) {
						R temp = new R();
						temp.status = status;
						temp.mimeType = mimeType;
						rs.add(temp);
					}
				}
			}
		}

		boolean ok; // 是否找到transfer

		// 没有请求,也没有响应
		ok = false;
		if (queryMimeTypes.size() == 0 && rs.size() == 0) {
			for (int i = 0; i < transferList.size(); i++) {
				MethodTransfer transfer = transferList.get(i);
				if (transfer.support(action, null, null, null)) {
					if (logger.isDebugEnabled()) {
						logger.debug("transfer {}", transfer);
					}
					String data = transfer.getData(null, null, null, action, relativeUri, parentFullUri, parentFullUriParameters, resource);
					datas.add(data);
					ok = true;
					break;
				}
			}
			if (!ok) {
				logger.warn("00 there is no tranfer support {} {} {}", parentFullUri, relativeUri, action.getType());
			}
		}

		// 有请求,没有响应
		ok = false;
		if (queryMimeTypes.size() > 0 && rs.size() == 0) {
			for (MimeType queryMimeType : queryMimeTypes) {
				for (int i = 0; i < transferList.size(); i++) {
					MethodTransfer transfer = transferList.get(i);
					if (transfer.support(action, queryMimeType, null, null)) {
						if (logger.isDebugEnabled()) {
							logger.debug("transfer {}", transfer);
						}
						String data = transfer.getData(queryMimeType, null, null, action, relativeUri, parentFullUri, parentFullUriParameters, resource);
						datas.add(data);
						ok = true;
						break;
					}
				}
			}
			if (!ok) {
				logger.warn("n0 there is no tranfer support {} {} {}", parentFullUri, relativeUri, action.getType());
			}
		}
		// 没有请求,有响应
		ok = false;
		if (queryMimeTypes.size() == 0 && rs.size() > 0) {
			for (R r : rs) {
				for (int i = 0; i < transferList.size(); i++) {
					MethodTransfer transfer = transferList.get(i);
					if (transfer.support(action, null, r.status, r.mimeType)) {
						if (logger.isDebugEnabled()) {
							logger.debug("transfer {}", transfer);
						}
						String data = transfer.getData(null, r.status, r.mimeType, action, relativeUri, parentFullUri, parentFullUriParameters, resource);
						datas.add(data);
						ok = true;
						break;
					}
				}
				if (!ok) {
					logger.warn("0m there is no tranfer support {} {} {}", parentFullUri, relativeUri, action.getType());
				}
			}
		}

		// 有请求,有响应
		ok = false;
		if (queryMimeTypes.size() > 0 && rs.size() > 0) {
			for (MimeType queryMimeType : queryMimeTypes) {
				for (R r : rs) {
					for (int i = 0; i < transferList.size(); i++) {
						MethodTransfer transfer = transferList.get(i);
						if (transfer.support(action, queryMimeType, r.status, r.mimeType)) {
							if (logger.isDebugEnabled()) {
								logger.debug("transfer {}", transfer);
							}
							String data = transfer.getData(queryMimeType, r.status, r.mimeType, action, relativeUri, parentFullUri, parentFullUriParameters,
									resource);
							datas.add(data);
							ok = true;
							break;
						}
					}
					if (!ok) {
						logger.warn("mn there is no tranfer support {} {} {}", parentFullUri, relativeUri, action.getType());
					}
				}
			}
		}

		// parse datas to string
		StringBuilder buffer = new StringBuilder();
		for (String data : datas) {
			buffer.append(data);
		}

		return buffer.toString();
	}

	// 响应
	class R {
		public String status;
		public MimeType mimeType;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public void setUriPrefix(String uriPrefix) {
		this.uriPrefix = uriPrefix;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setTransferList(List<MethodTransfer> transferList) {
		this.transferList = transferList;
	}

}
