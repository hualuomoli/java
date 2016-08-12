package com.github.hualuomoli.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.commons.util.ProjectUtils;
import com.github.hualuomoli.commons.util.YamlUtils;

/**
 * 应用工具类
 * @author hualuomoli
 *
 */
public class ApplicationUtils {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationUtils.class);
	private static final Object OBJECT = new Object();

	private static String SERVER_URL = null;
	private static String UPLOAD_FILE_PATH = null;
	private static String UPLOAD_FILE_URL = null;

	private static final Application application = YamlUtils.getInstance().getObject("application", Application.class);

	/**
	 * 获取服务器地址
	 * @return 服务器地址
	 */
	public static String getServerUrl() {
		if (SERVER_URL == null) {
			synchronized (OBJECT) {
				if (SERVER_URL == null) {
					if (StringUtils.isNotBlank(application.serverUrl)) {
						SERVER_URL = application.serverUrl;
					} else {
						SERVER_URL = ProjectUtils.getProjectUrl();
					}
				}
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("server url = " + SERVER_URL);
		}
		return SERVER_URL;
	}

	/**
	 * 获取上传目录
	 * @return 上传目录
	 */
	public static String getUploadFilePath() {
		if (UPLOAD_FILE_PATH == null) {
			synchronized (OBJECT) {
				if (UPLOAD_FILE_PATH == null) {
					if (StringUtils.isNotBlank(application.uploadFilePath)) {
						UPLOAD_FILE_PATH = application.uploadFilePath;
					} else {
						UPLOAD_FILE_PATH = ProjectUtils.getProjectStaticFilePath() + "/files";
					}
				}
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("upload file path = " + UPLOAD_FILE_PATH);
		}
		return UPLOAD_FILE_PATH;
	}

	/**
	 * 获取上传目录
	 * @return 上传目录
	 */
	public static String getUploadFileUrl() {
		if (UPLOAD_FILE_URL == null) {
			synchronized (OBJECT) {
				if (UPLOAD_FILE_URL == null) {
					if (StringUtils.isNotBlank(application.uploadFileUrl)) {
						UPLOAD_FILE_URL = application.uploadFileUrl;
					} else {
						UPLOAD_FILE_URL = getServerUrl() + "/files";
					}
				}
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("upload file url = " + UPLOAD_FILE_URL);
		}
		return UPLOAD_FILE_URL;
	}

	public static class Application {
		private String serverUrl; // 服务访问URL
		private String uploadFilePath; // 文件保存目录
		private String uploadFileUrl; // 文件访问路径

		public String getServerUrl() {
			return serverUrl;
		}

		public void setServerUrl(String serverUrl) {
			this.serverUrl = serverUrl;
		}

		public String getUploadFilePath() {
			return uploadFilePath;
		}

		public void setUploadFilePath(String uploadFilePath) {
			this.uploadFilePath = uploadFilePath;
		}

		public String getUploadFileUrl() {
			return uploadFileUrl;
		}

		public void setUploadFileUrl(String uploadFileUrl) {
			this.uploadFileUrl = uploadFileUrl;
		}

	}

}
