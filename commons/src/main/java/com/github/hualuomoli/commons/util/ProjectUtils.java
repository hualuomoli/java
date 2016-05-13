package com.github.hualuomoli.commons.util;

/**
 * 项目工具类
 * @author hualuomoli
 *
 */
public class ProjectUtils {

	// 获取项目路径
	public static String getProjectPath() {
		String path = ProjectUtils.class.getClassLoader().getResource(".").getPath().replaceAll("\\\\", "/");
		// IDE
		if (path.indexOf("/target/") >= 0) {
			return path.substring(0, path.lastIndexOf("/target/"));
		}
		// 部署
		if (path.indexOf("/WEB-INF/") >= 0) {
			return path.substring(0, path.lastIndexOf("/WEB-INF"));
		}
		throw new RuntimeException("can not support project type " + path);
	}

	// 获取项目静态文件路径
	public static String getProjectStaticFilePath() {
		String path = ProjectUtils.class.getClassLoader().getResource(".").getPath().replaceAll("\\\\", "/");
		// IDE
		if (path.indexOf("/target/") >= 0) {
			return path.substring(0, path.lastIndexOf("/target/")) + "/src/main/webapp";
		}
		// 部署
		if (path.indexOf("/WEB-INF/") >= 0) {
			return path.substring(0, path.lastIndexOf("/WEB-INF"));
		}
		throw new RuntimeException("can not support project type " + path);
	}

}
