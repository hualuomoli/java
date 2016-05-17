package com.github.hualuomoli.commons;

/**
 * commons项目的配置
 * @author hualuomoli
 *
 */
public class YamlCommonConfig extends YamlConfig {

	private static YamlCommonConfig config = null;

	public static YamlCommonConfig getInstance() {
		if (config == null) {
			synchronized (OBJECT) {
				if (config == null) {
					config = new YamlCommonConfig();
				}
			}
		}
		return config;
	}

	@Override
	public String[] getFiles() {
		return new String[] { "commons.yaml" };
	}

	@Override
	public String getPrefix() {
		return "commons";
	}

}
