package com.github.hualuomoli.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.commons.util.ProjectUtils;
import com.github.hualuomoli.commons.util.YamlUtils;
import com.google.common.collect.Maps;

/**
 * 应用工具类
 * @author hualuomoli
 *
 */
public class ApplicationUtils {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationUtils.class);
	private static final Object OBJECT = new Object();
	public static final String FILES = "files";

	private static String SERVER_URL = null;

	private static final String serverUrl = YamlUtils.getInstance().getString("application", "serverUrl");
	private static final List<StaticFile> staticFileList = YamlUtils.getInstance().getList("staticFiles", StaticFile.class, "application");
	private static final Map<String, StaticFile> staticFileMap = Maps.newHashMap();

	/**
	 * 获取服务器地址
	 * @return 服务器地址
	 */
	public static String getServerUrl() {
		if (SERVER_URL == null) {
			synchronized (OBJECT) {
				if (SERVER_URL == null) {
					if (serverUrl != null) {
						SERVER_URL = serverUrl;
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

	// 获取静态资源
	public static StaticFile getStaticFile(String name) {
		StaticFile staticFile = staticFileMap.get(name);

		if (staticFile == null) {
			synchronized (OBJECT) {
				if (staticFile == null) {
					for (StaticFile sf : staticFileList) {
						if (StringUtils.equals(sf.name, name)) {
							StaticFile s = new StaticFile();
							// url
							if (!StringUtils.startsWith(sf.url, "http://")) {
								s.url = ProjectUtils.getProjectUrl() + sf.url;
							} else {
								s.url = sf.url;
							}
							// location
							if (sf.location.startsWith("file:")) {
								s.location = sf.location.substring("file:".length());
							} else if (sf.location.startsWith("project:")) {
								s.location = ProjectUtils.getProjectPath() + "/" + sf.location.substring("project:".length());
							} else {
								s.location = sf.location;
							}
							// name
							s.name = sf.name;

							staticFile = s;
							break;
						}
					}
					//
					if (staticFile != null) {
						staticFileMap.put(name, staticFile);
					}
				}
			}
		}

		return staticFile;
	}

	// 静态文件
	public static class StaticFile {

		private String name;
		private String url;
		private String location;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

	}

}
