package com.github.hualuomoli.mvc;

import java.util.List;

import com.github.hualuomoli.commons.util.YamlUtils;
import com.google.common.collect.Lists;

public class YamlMvcConfig {

	private static final YamlUtils utils = YamlUtils.getInstance("mvc.yaml");

	public static String getValue(String... keys) {
		List<String> list = Lists.newArrayList(keys);
		list.add(0, "mvc");
		return utils.getValue(list.toArray(new String[] {}));
	}

}
