package com.github.hualuomoli.commons.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 项目工具类
 * @author hualuomoli
 *
 */
public class ProjectUtils {

	private static final Object OBJECT = new Object();
	private static String PROJECT_URL = null;
	private static String PROJECT_PATH = null;
	private static String PROJECT_STATIC_PATH = null;

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
		return PROJECT_URL;
	}

	// 获取项目路径
	public static String getProjectPath() {
		if (PROJECT_PATH == null) {
			synchronized (OBJECT) {
				if (PROJECT_PATH == null) {
					String path = ProjectUtils.class.getClassLoader().getResource(".").getPath().replaceAll("\\\\", "/");
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
		return PROJECT_PATH;
	}

	// 获取项目静态文件路径
	public static String getProjectStaticFilePath() {
		if (PROJECT_STATIC_PATH == null) {
			synchronized (OBJECT) {
				if (PROJECT_STATIC_PATH == null) {
					String path = ProjectUtils.class.getClassLoader().getResource(".").getPath().replaceAll("\\\\", "/");
					if (path.indexOf("/target/") >= 0) {
						// IDE
						PROJECT_STATIC_PATH = path.substring(0, path.lastIndexOf("/target/")) + "/src/main/webapp";
					} else if (path.indexOf("/WEB-INF/") >= 0) {
						// 部署
						PROJECT_STATIC_PATH = path.substring(0, path.lastIndexOf("/WEB-INF"));
					} else {
						throw new RuntimeException("can not support project type " + path);
					}
				}
			}
		}
		return PROJECT_STATIC_PATH;
	}

}
