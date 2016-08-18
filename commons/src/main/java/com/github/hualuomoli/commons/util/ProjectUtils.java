package com.github.hualuomoli.commons.util;

import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 项目工具类
 * @author hualuomoli
 *
 */
public class ProjectUtils {

	private static final Logger logger = LoggerFactory.getLogger(ProjectUtils.class);

	private static final Object OBJECT = new Object();
	private static String PROJECT_URL = null;
	private static String PROJECT_PATH = null;

	// 获取项目URL
	public static final String getProjectUrl() {
		if (PROJECT_URL == null) {
			synchronized (OBJECT) {
				if (PROJECT_URL == null) {
					HttpServletRequest request = ServletUtils.getRequest();
					String schema = request.getScheme();
					String serverName = request.getServerName();
					int serverPort = request.getServerPort();
					String path = request.getContextPath();

					String basePath = schema + "://" + serverName + ":" + serverPort + path /* + "/" */;
					PROJECT_URL = basePath;
				}
			}
		}
		if (logger.isInfoEnabled()) {
			logger.info("project url = {}", PROJECT_URL);
		}
		return PROJECT_URL;
	}

	// 获取项目路径
	public static String getProjectPath() {
		if (PROJECT_PATH == null) {
			synchronized (OBJECT) {
				if (PROJECT_PATH == null) {
					URL url = ProjectUtils.class.getClassLoader().getResource("/");
					if (url == null) {
						if (logger.isDebugEnabled()) {
							logger.debug("get resource .");
						}
						url = ProjectUtils.class.getClassLoader().getResource(".");
					}
					String path = url.getPath().replaceAll("\\\\", "/");
					if (logger.isInfoEnabled()) {
						logger.info("path={}", path);
					}
					// IDE
					if (path.indexOf("/target/") >= 0) {
						PROJECT_PATH = path.substring(0, path.lastIndexOf("/target/"));
					} else if (path.indexOf("/WEB-INF/") >= 0) {
						// 部署
						PROJECT_PATH = path.substring(0, path.lastIndexOf("/WEB-INF"));
					} else {
						throw new RuntimeException("can not support project type " + path);
					}
				}
			}
		}
		if (logger.isInfoEnabled()) {
			logger.info("project path = {}", PROJECT_PATH);
		}
		return PROJECT_PATH;
	}

}
