package com.github.hualuomoli.mvc;

import com.github.hualuomoli.commons.YamlConfig;

public class YamlMvcConfig extends YamlConfig {

	private static YamlMvcConfig config = null;

	public static YamlMvcConfig getInstance() {
		if (config == null) {
			synchronized (OBJECT) {
				if (config == null) {
					config = new YamlMvcConfig();
				}
			}
		}
		return config;
	}

	@Override
	public String[] getFiles() {
		return new String[] { "mvc.yaml" };
	}

	@Override
	public String getPrefix() {
		return "mvc";
	}

}
