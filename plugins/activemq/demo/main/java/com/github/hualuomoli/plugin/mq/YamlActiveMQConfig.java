package com.github.hualuomoli.plugin.mq;

import com.github.hualuomoli.commons.YamlConfig;

public class YamlActiveMQConfig extends YamlConfig {

	private static YamlActiveMQConfig config = null;

	public static YamlActiveMQConfig getInstance() {
		if (config == null) {
			synchronized (OBJECT) {
				if (config == null) {
					config = new YamlActiveMQConfig();
				}
			}
		}
		return config;
	}

	@Override
	public String[] getFiles() {
		return new String[] { "activemq.yaml" };
	}

	@Override
	public String getPrefix() {
		return "activemq";
	}

}
