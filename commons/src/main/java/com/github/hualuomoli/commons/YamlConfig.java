package com.github.hualuomoli.commons;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.github.hualuomoli.commons.util.YamlUtils;
import com.github.hualuomoli.commons.util.YamlUtils.Config;
import com.google.common.collect.Lists;

public abstract class YamlConfig {

	protected static final Object OBJECT = new Object();
	private static final String GLOBAL_YAML = "global.yaml";

	private YamlUtils yaml;

	protected YamlConfig() {
		List<String> files = Lists.newArrayList(this.getFiles());
		files.add(GLOBAL_YAML);
		yaml = YamlUtils.getInstance(files.toArray(new String[] {}));
	}

	// yaml配置文件
	protected abstract String[] getFiles();

	// 前缀
	protected abstract String getPrefix();

	// 获取值
	public String getValue(String[] keys) {
		List<String> list = Lists.newArrayList(keys);
		list.add(0, this.getPrefix());
		return yaml.getValue(list.toArray(new String[] {}));
	}

	// 获取值
	public String getValue(String key) {
		return this.getValue(key, ".");
	}

	// 获取值
	public String getValue(String key, String seperator) {
		if (StringUtils.isBlank(key) || StringUtils.isBlank(seperator)) {
			return null;
		}
		return this.getValue(StringUtils.split(key, seperator));
	}

	// 获取配置
	public Config getConfig(String[] keys) {
		Config config = yaml.getConfig(this.getPrefix());
		if (config == null) {
			return null;
		}
		for (String key : keys) {
			config = config.getConfig(key);
			if (config == null) {
				return null;
			}
		}
		return config;
	}

	// 获取配置
	public Config getConfig(String key) {
		return this.getConfig(key, ".");
	}

	// 获取配置
	public Config getConfig(String key, String seperator) {
		if (StringUtils.isBlank(key) || StringUtils.isBlank(seperator)) {
			return null;
		}
		return this.getConfig(StringUtils.split(key, seperator));
	}

}
