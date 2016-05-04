package com.github.hualuomoli.commons.prop;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 *  Properties文件载入工具类
 * @author hualuomoli
 *
 */
public class PropertiesUtils {

	private static Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);
	private static ResourceLoader resourceLoader = new DefaultResourceLoader();

	/**
	 * 载入多个文件, 文件路径使用Spring Resource格式.
	 */
	public static Properties load(String... resourcesPaths) {
		Properties props = new Properties();

		for (String location : resourcesPaths) {

			if (logger.isDebugEnabled()) {
				logger.debug("Loading properties file from:" + location);
			}

			InputStream is = null;
			try {
				Resource resource = resourceLoader.getResource(location);
				is = resource.getInputStream();
				props.load(is);
			} catch (IOException ex) {
				logger.error("Could not load properties from path:" + location + ", " + ex.getMessage());
			} finally {
				IOUtils.closeQuietly(is);
			}
		}
		return props;
	}

}
