package com.github.hualuomoli.commons;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.util.StreamUtils;

/**
 * 资源
 * @author hualuomoli
 *
 */
public final class ResourceUtils {

	private static final ResourceLoader resourceLoader = new DefaultResourceLoader();
	private static final ResourcePatternResolver resourceResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);

	/**
	 * 获取加载器
	 * @return 加载器
	 */
	public static final ResourceLoader getResourceLoader() {
		return resourceLoader;
	}

	/**
	 * 加载资源
	 * @param resourceLocation 资源路径,如 classpath:prop/jdbc.properties
	 * @return 资源
	 */
	public static final Resource load(String resourceLocation) {
		return resourceLoader.getResource(resourceLocation);
	}

	/**
	 * 加载通配符资源
	 * @param resourceLocationPattern 通配符资源路径,如 classpath:prop/*.properties
	 * @return 资源
	 */
	public static final Resource[] loadPattern(String resourceLocationPattern) throws IOException {
		return resourceResolver.getResources(resourceLocationPattern);
	}

	/**
	 * 获取资源内容
	 * @param resourceLocation 资源路径
	 * @param charset 资源编码集
	 * @return 资源内容,如果有错误返回null
	 */
	public static final String getResourceContent(String resourceLocation, Charset charset) {
		InputStream is = null;
		try {
			is = load(resourceLocation).getInputStream();
			return StreamUtils.copyToString(is, charset);
		} catch (IOException e) {
			return null;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
	}

}
