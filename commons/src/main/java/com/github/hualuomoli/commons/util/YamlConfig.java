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
public class YamlConfig {

	private static final Logger logger = LoggerFactory.getLogger(YamlConfig.class);

	private static final Map<String, Config> map = Maps.newHashMap();
	private static final Map<String, Data> dataMap = Maps.newHashMap();

	/**
	 * 加载资源文件
	 * @param resourceLocations 资源文件
	 * @return 配置
	 */
	public static final Config load(String... resourceLocations) {
		if (resourceLocations == null || resourceLocations.length == 0) {
			throw new RuntimeException();
		}
		String key = StringUtils.join(resourceLocations, ",");
		Config config = null;

		if (map.containsKey(key)) {
			config = map.get(key);
		} else {

			List<Data> datas = Lists.newArrayList();
			for (String resourceLocation : resourceLocations) {
				// check resource location
				Data data = null;
				if (dataMap.containsKey(resourceLocation)) {
					data = dataMap.get(resourceLocation);
				} else {
					if (logger.isDebugEnabled()) {
						logger.debug("config resource {}", resourceLocation);
					}
					data = new Data(resourceLocation);
					dataMap.put(resourceLocation, data);
				}
				datas.add(data);
			}
			config = new Config(datas);

			// set config
			map.put(key, config);
		}
		return config;
	}

	// 配置
	public static class Config {

		private List<Data> datas;

		public Config(List<Data> datas) {
			this.datas = datas;
		}

		/**
		 * 获取 String，键使用点(.)分割
		 * @param key 键
		 * @return String
		 */
		public String getStringBySeparator(String key) {
			return this.getStringBySeparator(key, ".");
		}

		/**
		 * 获取 String
		 * @param key 键
		 * @param separator 分隔符
		 * @return String
		 */
		public String getStringBySeparator(String key, String separator) {
			String str = null;
			for (int i = datas.size() - 1; i >= 0; i--) {
				str = datas.get(i).getStringBySeparator(key, separator);
				if (str != null) {
					return str;
				}
			}
			return null;
		}

		/**
		 * 获取 String
		 * @param name 名称
		 * @param cls 类型
		 * @param keys 前缀键值
		 * @return Object
		 */
		public String getString(String... keys) {
			String str = null;
			for (int i = datas.size() - 1; i >= 0; i--) {
				str = datas.get(i).getString(keys);
				if (str != null) {
					return str;
				}
			}
			return null;
		}

		/**
		 * 获取Object
		 * @param name 名称
		 * @param cls 类型
		 * @param keys 前缀键值
		 * @return Object
		 */
		public <T> T getObject(String name, Class<T> cls, String... keys) {
			T t = null;
			for (int i = datas.size() - 1; i >= 0; i--) {
				t = datas.get(i).getObject(name, cls, keys);
				if (t != null) {
					return t;
				}
			}
			return null;
		}

		/**
		 * 获取 List
		 * @param name 名称
		 * @param cls 类型
		 * @param keys 前缀键值
		 * @return List
		 */
		public <T> List<T> getList(String name, Class<T> cls, String... keys) {
			List<T> list = null;
			for (int i = datas.size() - 1; i >= 0; i--) {
				list = datas.get(i).getList(name, cls, keys);
				if (list != null) {
					return list;
				}
			}
			return null;
		}

	}

	// 数据
	private static class Data {

		private Map<String, Object> map;

		@SuppressWarnings("unchecked")
		public Data(String resourceLocation) {
			InputStream is = null;
			try {
				is = ResourceUtils.load(resourceLocation).getInputStream();
				map = new Yaml().loadAs(is, HashMap.class);
			} catch (IOException e) {
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (Exception e) {
						if (logger.isErrorEnabled()) {
							logger.error("{}", e);
						}
					}
				}
			}
		}

		/**
		 * 获取 String
		 * @param key 键
		 * @param separator 分隔符
		 * @return String
		 */
		public String getStringBySeparator(String key, String separator) {
			return this.getString(StringUtils.split(key, separator));
		}

		/**
		 * 获取 String
		 * @param name 名称
		 * @param cls 类型
		 * @param keys 前缀键值
		 * @return String
		 */
		@SuppressWarnings("unchecked")
		public String getString(String... keys) {

			if (keys == null || keys.length == 0) {
				return null;
			}

			Map<String, Object> tempMap = map;

			for (int i = 0; i < keys.length - 1; i++) {
				if (tempMap == null) {
					return null;
				}
				tempMap = (Map<String, Object>) tempMap.get(keys[i]);
			}

			// 没有数据
			if (tempMap == null) {
				return null;
			}
			// 获取name对应的数据
			Object obj = tempMap.get(keys[keys.length - 1]);
			return obj == null ? null : String.valueOf(obj);
		}

		/**
		 * 获取Object
		 * @param name 名称
		 * @param cls 类型
		 * @param keys 前缀键值
		 * @return Object
		 */
		@SuppressWarnings("unchecked")
		public <T> T getObject(String name, Class<T> cls, String... keys) {
			Map<String, Object> tempMap = this.get(keys);
			// 没有数据
			if (tempMap == null) {
				return null;
			}
			// 获取name对应的数据
			tempMap = (Map<String, Object>) tempMap.get(name);
			if (tempMap == null) {
				return null;
			}
			// 获取数据
			return JsonUtils.parseObject(JsonUtils.toJson(tempMap), cls);
		}

		/**
		 * 获取 List
		 * @param name 名称
		 * @param cls 类型
		 * @param keys 前缀键值
		 * @return List
		 */
		public <T> List<T> getList(String name, Class<T> cls, String... keys) {
			Map<String, Object> tempMap = this.get(keys);
			// 没有数据
			if (tempMap == null) {
				return null;
			}
			// 获取name对应的数据
			List<?> list = (List<?>) tempMap.get(name);
			if (list == null) {
				return null;
			}
			// 获取数据
			return JsonUtils.parseList(JsonUtils.toJson(list), cls);
		}

		/**
		 * 获取keys对应的数据
		 * @param keys 键
		 * @return 值
		 */
		@SuppressWarnings("unchecked")
		private Map<String, Object> get(String... keys) {
			Map<String, Object> tempMap = map;
			for (String key : keys) {
				if (tempMap == null) {
					return null;
				}
				tempMap = (Map<String, Object>) tempMap.get(key);
			}
			return tempMap;
		}
	}

}
