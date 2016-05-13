package com.github.hualuomoli.commons;

import java.util.List;

import com.github.hualuomoli.commons.util.YamlUtils;
import com.google.common.collect.Lists;

/**
 * commons项目的配置
 * @author hualuomoli
 *
 */
public class CommonConfig {

	private static final YamlUtils utils = YamlUtils.getInstance("commons.yaml");

	public static String getValue(String... keys) {
		List<String> list = Lists.newArrayList(keys);
		list.add(0, "commons");
		return utils.getValue(list.toArray(new String[] {}));
	}

}
