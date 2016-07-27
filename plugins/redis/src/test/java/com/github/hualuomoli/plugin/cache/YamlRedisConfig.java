package com.github.hualuomoli.plugin.cache;

import com.github.hualuomoli.commons.YamlConfig;

public class YamlRedisConfig extends YamlConfig {

	private static YamlRedisConfig config = null;

	public static YamlRedisConfig getInstance() {
		if (config == null) {
			synchronized (OBJECT) {
				if (config == null) {
					config = new YamlRedisConfig();
				}
			}
		}
		return config;
	}

	@Override
	public String[] getFiles() {
		return new String[] { "redis.yaml" };
	}

	@Override
	public String getPrefix() {
		return "redis";
	}

}
