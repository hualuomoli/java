package com.github.hualuomoli.config;

import java.util.List;

import com.github.hualuomoli.commons.util.YamlUtils;
import com.google.common.collect.Lists;

public class WebConfig {

	private static final YamlUtils utils = YamlUtils.getInstance("web.yaml");

	public static String getValue(String... keys) {
		List<String> list = Lists.newArrayList(keys);
		list.add(0, "web");
		return utils.getValue(list.toArray(new String[] {}));
	}

}
