package com.github.hualuomoli.base;

import com.github.hualuomoli.commons.YamlConfig;

public class YamlBaseConfig extends YamlConfig {

	private static YamlBaseConfig config = null;

	public static YamlBaseConfig getInstance() {
		if (config == null) {
			synchronized (OBJECT) {
				if (config == null) {
					config = new YamlBaseConfig();
				}
			}
		}
		return config;
	}

	@Override
	public String[] getFiles() {
		return new String[] { "base.yaml" };
	}

	@Override
	public String getPrefix() {
		return "base";
	}

}
