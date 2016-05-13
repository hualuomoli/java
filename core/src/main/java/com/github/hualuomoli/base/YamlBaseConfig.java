package com.github.hualuomoli.base;

import java.util.List;

import com.github.hualuomoli.commons.util.YamlUtils;
import com.google.common.collect.Lists;

public class YamlBaseConfig {

	private static final YamlUtils utils = YamlUtils.getInstance("base.yaml");

	public static String getValue(String... keys) {
		List<String> list = Lists.newArrayList(keys);
		list.add(0, "base");
		return utils.getValue(list.toArray(new String[] {}));
	}

}
