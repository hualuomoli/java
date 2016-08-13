package com.github.hualuomoli.mvc.config;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.github.hualuomoli.commons.util.ProjectUtils;
import com.github.hualuomoli.commons.util.YamlUtils;

/**
 * 静态资源
 * @author hualuomoli
 *
 */
@Configuration
@EnableWebMvc
public class StaticResourceConfig extends WebMvcConfigurerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(StaticResourceConfig.class);

	private static final List<Resource> list = YamlUtils.getInstance().getList("resources", Resource.class);

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		if (list == null || list.size() == 0) {
			return;
		}
		for (Resource resource : list) {

			if (logger.isDebugEnabled()) {
				logger.debug("config static resource {} {} --> {}", resource.type, resource.url, resource.location);
			}

			String location = resource.location;

			switch (resource.type) {
			case Resource.FILE:
				try {
					location = new File(location).getCanonicalPath();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				location = "file:" + location + File.separator;
				break;
			case Resource.PROJECT:
				try {
					location = new File(ProjectUtils.getProjectPath(), location).getCanonicalPath();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				location = "file:" + location + File.separator;
				break;
			case Resource.CLASSPATH:
				location = "classpath:" + location;
				break;
			default:
				// webapps
			}

			if (logger.isInfoEnabled()) {
				logger.info("set static resource {} --> {}", resource.url, location);
			}
			registry.addResourceHandler(resource.url).addResourceLocations(location);
		}
	}

	public static class Resource {

		private static final String CLASSPATH = "classpath";
		private static final String FILE = "file";
		private static final String PROJECT = "project";

		private String url; // 资源访问路径
		private String location; // 文件路径
		private String type = ""; // 类型

		public Resource() {
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public void setType(String type) {
			this.type = type;
		}

	}

}
