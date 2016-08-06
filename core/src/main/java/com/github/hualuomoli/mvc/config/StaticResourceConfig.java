package com.github.hualuomoli.mvc.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.github.hualuomoli.commons.util.YamlUtils;

/**
 * 静态资源
 * @author hualuomoli
 *
 */
@Configuration
@EnableWebMvc
public class StaticResourceConfig extends WebMvcConfigurerAdapter {

	private static final List<Resource> list = YamlUtils.getInstance().getList("resources", Resource.class);

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		if (list == null || list.size() == 0) {
			return;
		}
		for (Resource resource : list) {
			registry.addResourceHandler(resource.url).addResourceLocations(resource.location);
		}
	}

	public static class Resource {
		private String url; // 资源访问路径
		private String location; // 文件路径

		public Resource() {
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public void setLocation(String location) {
			this.location = location;
		}

	}

}
