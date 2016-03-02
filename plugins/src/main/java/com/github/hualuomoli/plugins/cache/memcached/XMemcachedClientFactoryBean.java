package com.github.hualuomoli.plugins.cache.memcached;

import java.util.List;

import com.google.common.collect.Lists;

public class XMemcachedClientFactoryBean extends net.rubyeye.xmemcached.utils.XMemcachedClientFactoryBean {

	public void setWeightArray(String weights) {
		String[] array = weights.split(" ");
		List<Integer> list = Lists.newArrayList();
		for (String weight : array) {
			list.add(Integer.parseInt(weight));
		}
		super.setWeights(list);
	}

}
