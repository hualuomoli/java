package com.github.hualuomoli;

import com.github.hualuomoli.commons.YamlConfig;

public class YamlWebConfig extends YamlConfig {

	private static YamlWebConfig config = null;

	public static YamlWebConfig getInstance() {
		if (config == null) {
			synchronized (OBJECT) {
				if (config == null) {
					config = new YamlWebConfig();
				}
			}
		}
		return config;
	}

	@Override
	public String[] getFiles() {
		return new String[] { "web.yaml" };
	}

	@Override
	public String getPrefix() {
		return "web";
	}

}
