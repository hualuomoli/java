package com.github.hualuomoli.commons.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * YAML工具
 * @author hualuomoli
 *
 */
public class YamlUtils {

	private static final Logger logger = LoggerFactory.getLogger(YamlUtils.class);

	private static final String split = "_y_a_m_l_"; // 数据分隔符
	// 已经加载过
	private static final Map<String, YamlUtils> loaded = Maps.newHashMap();

	// 数据map,如果数据已经获取过,存放到map中
	private Map<String, String> dataMap = Maps.newHashMap();
	// 加载的资源集合
	private List<Map<String, Object>> yamlList = Lists.newArrayList();

	@SuppressWarnings("unchecked")
	private YamlUtils(String... resourceLocations) {
		if (resourceLocations == null || resourceLocations.length == 0) {
			throw new RuntimeException();
		}

		InputStream is = null;
		for (String resourceLocation : resourceLocations) {
			if (logger.isDebugEnabled()) {
				logger.debug("load resource {}", resourceLocation);
			}
			try {
				is = ResourceUtils.load(resourceLocation).getInputStream();
				yamlList.add(new Yaml().loadAs(is, HashMap.class));
			} catch (Exception e) {
				// 资源未找到
				logger.warn("can not load resource {}", resourceLocation);
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
					}
				}
			}
		}

	}

	/**
	 * 获取实例
	 * @param files 加载的文件
	 * @return 实例
	 */
	public static YamlUtils getInstance(String... files) {
		if (files == null || files.length == 0) {
			throw new RuntimeException();
		}
		String load = StringUtils.join(files, ",");
		// 如果已经存在
		if (loaded.containsKey(load)) {
			logger.debug("files {} has loaded.", load);
			return loaded.get(load);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("load files {}", load);
		}
		return new YamlUtils(files);
	}

	/**
	* 获取数据
	* @param keys 键数组
	* @return 数据
	*/
	public String getValue(String... keys) {
		if (keys == null || keys.length == 0) {
			throw new RuntimeException();
		}
		// key使用特定字符串分割
		String union = StringUtils.join(keys, split);
		// 如果数据已经获取过,直接返回
		if (dataMap.containsKey(union)) {
			String obj = dataMap.get(union);
			if (logger.isDebugEnabled()) {
				logger.debug("find data from cache {} = {}", StringUtils.join(keys, "."), obj);
			}
			return obj;
		}

		// 获取配置的map
		String obj = this._getValue(keys);
		// 后面的会覆盖前面的
		if (obj != null) {
			// 获取到
			if (logger.isDebugEnabled()) {
				logger.debug("get data {} = {}", StringUtils.join(keys, "."), obj);
			}
			dataMap.put(union, obj);
			return obj;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("can not find key {}", StringUtils.join(keys, "."));
		}
		return null;

	}

	/**
	* 获取数据
	* @param keys 键数组
	* @return 数据
	*/
	@SuppressWarnings("unchecked")
	private String _getValue(String... keys) {
		for (int i = yamlList.size() - 1; i >= 0; i--) {
			Map<String, Object> map = yamlList.get(i);
			Map<String, Object> tempMap = map;
			// 循环遍历
			for (int j = 0; j < keys.length - 1; j++) {
				// 获取值
				tempMap = (Map<String, Object>) tempMap.get(keys[j]);
				// 如果只为空,退出当前查找
				if (tempMap == null) {
					break;
				}
			}
			// 如果没找到对应的数据,继续下次查找
			if (tempMap == null) {
				continue;
			}
			// 如果找到数据,返回
			if (tempMap.containsKey(keys[keys.length - 1])) {
				Object obj = tempMap.get(keys[keys.length - 1]);
				if (obj == null) {
					return null;
				}
				return String.valueOf(obj);
			}
		}
		// 默认没找到
		return null;
	}

	/**
	 * 获取配置
	 * @param key 键
	 * @return 值
	 */
	@SuppressWarnings("unchecked")
	public Config getConfig(String key) {
		if (StringUtils.isBlank(key)) {
			throw new RuntimeException("key must not be empty.");
		}
		List<Map<String, Object>> dataList = Lists.newArrayList();
		for (int i = yamlList.size() - 1; i >= 0; i--) {
			Map<String, Object> map = yamlList.get(i);
			if (map.containsKey(key)) {
				dataList.add((Map<String, Object>) map.get(key));
			}
		}
		return new Config(dataList);
	}

	// 配置
	public class Config {

		// 数据
		private List<Map<String, Object>> dataList;

		private Config(List<Map<String, Object>> dataList) {
			this.dataList = dataList;
		}

		/**
		 * 获取配置信息
		 * @param key 键
		 * @return 值
		 */
		@SuppressWarnings("unchecked")
		public Config getConfig(String key) {
			if (StringUtils.isBlank(key)) {
				throw new RuntimeException("key must not be empty.");
			}
			List<Map<String, Object>> list = Lists.newArrayList();
			for (Map<String, Object> dataMap : dataList) {
				if (dataMap.containsKey(key)) {
					list.add((Map<String, Object>) dataMap.get(key));
				}

			}
			return new Config(list);
		}

		/**
		 * 获取值
		 * @param key 键
		 * @return 值
		 */
		public String getValue(String key) {
			if (StringUtils.isBlank(key)) {
				throw new RuntimeException("key must not be empty.");
			}
			for (Map<String, Object> dataMap : dataList) {
				if (dataMap.containsKey(key)) {
					Object obj = dataMap.get(key);
					if (obj == null) {
						return null;
					}
					return String.valueOf(obj);
				}
			}
			return null;
		}

		/**
		 * 获取配置module
		 * @param key 键
		 * @return Map
		 */
		@SuppressWarnings("unchecked")
		public Map<String, Object> getObject(String key) {
			if (StringUtils.isBlank(key)) {
				throw new RuntimeException("key must not be empty.");
			}
			for (Map<String, Object> dataMap : dataList) {
				if (dataMap.containsKey(key)) {
					return (Map<String, Object>) dataMap.get(key);
				}
			}
			return Maps.newHashMap();
		}

		/**
		 * 获取配置module
		 * @param key 键
		 * @param cls module的类
		 * @return module
		 */
		public <T> T getObject(String key, Class<T> cls) {
			if (StringUtils.isBlank(key)) {
				throw new RuntimeException("key must not be empty.");
			}
			return this._parseMap2Object(this.getObject(key), cls);
		}

		/**
		 * 获取配置module集合
		 * @param key 键
		 * @return list
		 */
		@SuppressWarnings("unchecked")
		public List<Map<String, Object>> getList(String key) {
			if (StringUtils.isBlank(key)) {
				throw new RuntimeException("key must not be empty.");
			}
			for (Map<String, Object> dataMap : dataList) {
				if (dataMap.containsKey(key)) {
					return (List<Map<String, Object>>) dataMap.get(key);
				}
			}
			return Lists.newArrayList();
		}

		/**
		* 获取配置module
		* @param key 键
		* @param cls module的类
		* @return module
		*/
		public <T> List<T> getList(String key, Class<T> cls) {
			if (StringUtils.isBlank(key)) {
				throw new RuntimeException("key must not be empty.");
			}
			return this._parseMap2List(this.getList(key), cls);
		}

		/**
		 * 解析map为module
		 * @param tempMap map 
		 * @param cls module的类型
		 * @return module
		 */
		private <T> T _parseMap2Object(Map<String, Object> tempMap, Class<T> cls) {
			return JsonUtils.parseObject(JsonUtils.toJson(tempMap), cls);
		}

		/**
		* 解析map为module
		* @param tempMap map 
		* @param cls module的类型
		* @return module
		*/
		private <T> List<T> _parseMap2List(List<Map<String, Object>> tempList, Class<T> cls) {
			return JsonUtils.parseList(JsonUtils.toJson(tempList), cls);
		}

	}

}
