package com.github.hualuomoli.plugin.http;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * HTTP异步调用
 * @author hualuomoli
 *
 */
public interface AsyncHttp {

	// 获取客户端
	GetClient get(String url, Object... uriParameters);

	PostClient post(String url, Object... uriParameters);

	DeleteClient delete(String url, Object... uriParameters);

	FileUploadClient fileUpload(String url, Object... uriParameters);

	// 客户端
	public static interface Client {

		// 添加头信息
		<T extends Client> T setHeader(String name, String value);

		// 添加监听器
		<T extends Client> T addLinstener(Callback callback);

		// 执行
		void execute();

	}

	// get
	public static interface GetClient extends Client {

		// 设置参数
		GetClient setParameter(String name, Object value);

	}

	// post
	public static interface PostClient extends Client {

		// 设置参数
		PostClient setParameter(String name, Object value);

		// 设置发送内容
		PostClient setContent(String content);

	}

	// delete
	public static interface DeleteClient extends Client {

	}

	// file upload
	public static interface FileUploadClient extends Client {

		// 设置参数
		FileUploadClient setParameter(String name, Object value);

		// 添加上传文件
		FileUploadClient setUploadFile(String name, File file);

	}

	// 回调
	public static interface Callback {

		// 处理
		void onMessage(Res res);

	}

	// 响应
	public static class Res {

		private Integer statusCode; // 响应状态码
		private String content; // 内容
		private Client client; // 处理者

		private JSONObject json;

		public Res(Integer statusCode, String content, Client client) {
			this.statusCode = statusCode;
			this.content = content;
			this.client = client;
			try {
				this.json = JSON.parseObject(content);
			} catch (Exception e) {
			}
		}

		// 是否响应成功
		public boolean isOk(String codeName, String codeSuccessValue) {
			if (statusCode != 200) {
				return false;
			}
			if (json == null) {
				return false;
			}
			if (!json.containsKey(codeName)) {
				return false;
			}

			return StringUtils.equals(String.valueOf(json.get(codeName)), codeSuccessValue);

		}

		public Integer getStatusCode() {
			return statusCode;
		}

		public String getContent() {
			return content;
		}

		public Client getClient() {
			return client;
		}

		public JSONObject getJson() {
			return json;
		}

		public <T> T getObject(String dataName, Class<T> cls) {
			if (json == null) {
				return null;
			}
			if (!json.containsKey(dataName)) {
				return null;
			}
			return JSON.parseObject(json.get(dataName).toString(), cls);
		}

		public <T> List<T> getList(String dataName, Class<T> cls) {
			if (json == null) {
				return null;
			}
			if (!json.containsKey(dataName)) {
				return null;
			}
			return JSON.parseArray(json.get(dataName).toString(), cls);
		}

		public <T> Page<T> getPage(String pageName, String pageNumberName, String pageSizeName, String totalName, String dataName, Class<T> cls) {
			if (json == null) {
				return null;
			}
			if (!json.containsKey(pageName)) {
				return null;
			}

			JSONObject pageObject = json.getJSONObject(pageName);
			if (!pageObject.containsKey(pageNumberName) || !pageObject.containsKey(pageSizeName) || !pageObject.containsKey(totalName)
					|| !pageObject.containsKey(dataName)) {
				return null;
			}

			final Integer pageNumber = pageObject.getInteger(pageNumberName);
			final Integer pageSize = pageObject.getInteger(pageSizeName);
			final Integer total = pageObject.getInteger(totalName);

			final List<T> dataList = JSON.parseArray(pageObject.get(dataName).toString(), cls);

			return new Page<T>() {

				@Override
				public Integer getPageNo() {
					return pageNumber;
				}

				@Override
				public Integer getPageSize() {
					return pageSize;
				}

				@Override
				public Integer getTotal() {
					return total;
				}

				@Override
				public List<T> getDataList() {
					return dataList;
				}

			};
		}

	}

	// 分页
	public static interface Page<T> {

		// 页码
		Integer getPageNo();

		// 每页数据
		Integer getPageSize();

		// 总量
		Integer getTotal();

		// 数据
		List<T> getDataList();

	}

	public static class Config {

		public String getCodeName() {
			return "code";
		}

		public String getSuccessCodeValue() {
			return "0";
		}

	}

	// 方法
	public static enum Method {
		GET(), //
		POST(), //
		DELETE(), //
		FILE_UPLOAD(), //
		;

	}

	// 响应数据类型
	public static enum ResponseType {

		OBJECT(), //
		LIST(), //
		PAGE(), //
		;
	}

}
