package com.github.hualuomoli.tool.raml;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.raml.model.Raml;
import org.raml.model.Resource;
import org.raml.model.parameter.UriParameter;
import org.raml.parser.visitor.RamlDocumentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * 抽象类,实现基本的操作
 * @author hualuomoli
 *
 */
public abstract class AbstractParser implements Parser {

	protected static final Logger logger = LoggerFactory.getLogger(Parser.class);

	@Override
	public void execute(String[] ramlResources) {
		if (ramlResources == null || ramlResources.length == 0) {
			return;
		}

		Raml[] ramls = new Raml[ramlResources.length];

		for (int i = 0; i < ramlResources.length; i++) {
			ramls[i] = new RamlDocumentBuilder().build(ramlResources[i]);
		}
		// 执行解析
		for (int i = 0; i < ramls.length; i++) {
			this.execute(ramls[i]);
		}
		// 配置
		this.configure(ramls);

	}

	// 配置RAML
	protected void configure(Raml[] ramls) {
	}

	/**
	 * 解析
	 * @param raml RAML
	 */
	private void execute(Raml raml) {
		// get raml's resource
		Map<String, Resource> resources = raml.getResources();
		if (resources == null || resources.size() == 0) {
			return;
		}
		for (Resource resource : resources.values()) {
			this.execute(resource);
		}
	}

	/**
	 * 解析资源
	 * @param resource 资源
	 */
	private void execute(Resource resource) {

		// 非叶子资源
		Set<Resource> notLeafResources = Tool.getNotLeafResources(resource);

		// 处理当前资源
		this.create(resource);

		// 处理有叶子资源的resource
		for (Resource notLeafResource : notLeafResources) {
			this.execute(notLeafResource);
		}

	}

	/**
	 * 创建文件
	 * 处理当前资源的Actions
	 * 处理当前资源下非子资源的资源 child.getResource == empty
	 * @param resource 资源
	 */
	protected abstract void create(Resource resource);

	/**
	 * 工具
	 * @author admin
	 *
	 */
	protected static class Tool {

		/**
		 * 获取资源下的叶子资源(该资源下没有resource)
		 * @param resource 资源 
		 * @return 叶子资源集合
		 */
		static Set<Resource> getLeafResources(Resource resource) {
			Set<Resource> resources = Sets.newHashSet();

			Map<String, Resource> resourceMap = resource.getResources();
			if (resourceMap == null || resourceMap.size() == 0) {
				return resources;
			}

			for (Resource r : resourceMap.values()) {
				if (_isLeaf(r)) {
					resources.add(r);
				}
			}

			return resources;

		}

		/**
		* 获取资源下的叶子资源(该资源下没有resource)
		* @param resource 资源 
		* @return 叶子资源集合
		*/
		static Set<Resource> getNotLeafResources(Resource resource) {
			Set<Resource> resources = Sets.newHashSet();

			Map<String, Resource> resourceMap = resource.getResources();
			if (resourceMap == null || resourceMap.size() == 0) {
				return resources;
			}

			for (Resource r : resourceMap.values()) {
				if (!_isLeaf(r)) {
					resources.add(r);
				}
			}

			return resources;

		}

		/**
		 * 资源是否是叶子资源(该资源下没有resource)
		 * @param resource 资源 
		 * @return 是否是叶子资源
		 */

		private static boolean _isLeaf(Resource resource) {
			Map<String, Resource> rs = resource.getResources();
			return rs == null || rs.size() == 0;
		}

		/**
		 * 获取资源全路径
		 * @param resource 资源
		 * @return 资源全路径
		 */
		static String getResourceFullUri(Resource resource) {
			if (resource == null) {
				return StringUtils.EMPTY;
			}
			return getResourceFullUri(resource.getParentResource()) + resource.getRelativeUri();
		}

		/**
		 * 获取资源参数
		 * @param resource  资源
		 * @return 资源参数
		 */
		static Map<String, UriParameter> getResourceFullUriParameters(Resource resource) {
			Map<String, UriParameter> map = Maps.newHashMap();
			if (resource == null) {
				return map;
			}
			map.putAll(getResourceFullUriParameters(resource.getParentResource()));
			map.putAll(resource.getUriParameters());
			return map;
		}

	}

}
