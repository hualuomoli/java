package com.github.hualuomoli.commons.util;

import com.github.hualuomoli.commons.util.YamlConfig.Config;

/**
 * YAML工具
 * @author hualuomoli
 *
 */
public class YamlUtils {

	private static final String[] files = { "application.yaml", "global.yaml" };
	private static Config config = null;

	public static final Config getInstance() {
		if (config == null) {
			synchronized (files) {
				if (config == null) {
					config = YamlConfig.load(files);
				}
			}
		}
		return config;
	}

}
