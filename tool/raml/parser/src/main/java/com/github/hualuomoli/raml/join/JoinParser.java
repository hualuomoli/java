package com.github.hualuomoli.raml.join;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.raml.model.Action;
import org.raml.model.ActionType;
import org.raml.model.MimeType;
import org.raml.model.Resource;
import org.raml.model.Response;

import com.github.hualuomoli.raml.ParserAbstract;
import com.github.hualuomoli.raml.join.adaptor.ActionAdaptor;
import com.github.hualuomoli.raml.util.RamlUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 连接字符串解析器
 * @author hualuomoli
 *
 */
public abstract class JoinParser extends ParserAbstract {

	private JoinFileDealer dealer;
	private List<ActionAdaptor> adaptors;

	public void setDealer(JoinFileDealer dealer) {
		this.dealer = dealer;
	}

	public void setAdaptors(List<ActionAdaptor> adaptors) {
		this.adaptors = adaptors;
	}

	@Override
	protected void setConfig(Config config) {
		super.setConfig(config);
		dealer.setConfig(config);
	}

	@Override
	protected void create(Resource resource) {

		// 获取文件数据
		List<String> datas = this.getFileDatas(resource);
		// 写文件
		this.flush(resource, datas);
		// 配置文件
		this.configFile(resource);
	}

	/**
	 * 配置文件
	 * @param resource 资源
	 */
	protected void configFile(Resource resource) {
	}

	/**
	 * 输出文件
	 * @param resource 资源
	 * @param datas 数据
	 */
	private void flush(Resource resource, List<String> datas) {
		// 获取文件相对路径
		String filepath = dealer.getRelativeFilePath(resource);
		// 获取文件名
		String filename = dealer.getFileName(resource);
		// 写文件
		File dir = new File(config.getOutputFilepath(), filepath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dir.getAbsolutePath(), filename);
		try {
			FileUtils.writeLines(file, config.getEncoding().name(), datas);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取数据
	 * @param resource 资源
	 * @return 数据
	 */
	private List<String> getFileDatas(Resource resource) {

		// 获取文件头
		List<String> header = dealer.getFileHeader(resource);
		// 获取文件内容
		List<String> content = this.getContent(resource);
		// 获取文件尾
		List<String> footer = dealer.getFileFooter(resource);

		List<String> datas = Lists.newArrayList();
		datas.addAll(header);
		datas.addAll(content);
		datas.addAll(footer);

		return datas;

	}

	/**
	 * 获取内容
	 * @param resource 资源
	 * @return 内容
	 */
	private List<String> getContent(Resource resource) {

		List<String> content = Lists.newArrayList();

		// 本资源
		Map<ActionType, Action> actions = resource.getActions();
		for (Action action : actions.values()) {
			content.addAll(this.getData(action));
		}

		// 本资源下的非叶子资源
		Set<Resource> leafResources = Tool.getLeafResources(resource);
		for (Resource leafResource : leafResources) {
			content.addAll(this.getContent(leafResource));
		}

		return content;

	}

	/**
	 * 获取数据
	 * @param action 功能
	 * @return 数据
	 */
	private List<String> getData(Action action) {

		List<String> actionDatas = Lists.newArrayList();

		List<Adapter> adapters = ActionTool.getAdapters(action);
		for (Adapter adapter : adapters) {
			ActionAdaptor adaptor = ActionTool.getAdapter(adaptors, adapter);
			if (adaptor == null) {
				logger.error("can not find support adaptor for {} {}", adapter, RamlUtils.getFullUri(action.getResource()));
				continue;
			}
			List<String> datas = adaptor.getDatas(adapter);
			actionDatas.addAll(datas);
		}

		return actionDatas;
	}

	// 工具
	static class ActionTool {

		/**
		 * 获取适配者
		 * @param action 功能
		 * @return 适配者
		 */
		static List<Adapter> getAdapters(Action action) {
			List<Adapter> adapters = Lists.newArrayList();

			// 变量
			List<MimeType> formMimeTypes = Lists.newArrayList();
			Map<String, List<MimeType>> responseMap = Maps.newHashMap();

			// form
			Map<String, MimeType> formBody = action.getBody();
			if (formBody == null || formBody.size() == 0) {
				formMimeTypes.add(null); // size to 1
			} else {
				for (MimeType mimeType : formBody.values()) {
					formMimeTypes.add(mimeType);
				}
			}

			// response
			Map<String, Response> responses = action.getResponses();
			if (responses == null || responses.size() == 0) {
				List<MimeType> responseMimeTypes = Lists.newArrayList();
				responseMimeTypes.add(null);// size to 1
				responseMap.put("", responseMimeTypes); // size to 1
			} else {
				for (String code : responses.keySet()) {
					Response response = responses.get(code);
					List<MimeType> responseMimeTypes = Lists.newArrayList();

					if (response == null || response.getBody() == null) {
						responseMimeTypes.add(null); // size to 1
					} else {
						Map<String, MimeType> responseBody = response.getBody();
						for (MimeType mimeType : responseBody.values()) {
							responseMimeTypes.add(mimeType);
						}
					}
					responseMap.put(code, responseMimeTypes);
				}
			}

			for (MimeType formMimeType : formMimeTypes) {
				for (String code : responseMap.keySet()) {
					List<MimeType> responseMimeTypes = responseMap.get(code);
					for (MimeType responseMimeType : responseMimeTypes) {
						Adapter adapter = new Adapter();
						adapter.action = action;
						adapter.formMimeType = formMimeType;
						adapter.responseCode = code;
						adapter.responseMimeType = responseMimeType;
						adapters.add(adapter);
					}
				}
			}

			return adapters;
		}

		/**
		 * 获取适配者的适配器
		 * @param adaptors 适配器集合
		 * @param adapter 适配者
		 * @return 适配器
		 */
		static ActionAdaptor getAdapter(List<ActionAdaptor> adaptors, Adapter adapter) {
			for (int i = 0; i < adaptors.size(); i++) {
				ActionAdaptor adaptor = adaptors.get(i);
				if (adaptor.support(adapter)) {
					return adaptor;
				}
			}
			return null;
		}

	}

	// 适配者
	public static class Adapter {

		public Action action; // 功能
		public MimeType formMimeType; // form的类型
		public String responseCode; // 响应编码
		public MimeType responseMimeType; // 响应的类型

	}

}
