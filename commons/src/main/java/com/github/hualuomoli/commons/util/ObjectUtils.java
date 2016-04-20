package com.github.hualuomoli.commons.util;

import java.lang.reflect.Method;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;

/**
 * Object工具
 * @author hualuomoli
 *
 */
public class ObjectUtils {

	private static final Logger logger = LoggerFactory.getLogger(ObjectUtils.class);

	/**
	 * 复制
	 * @param dest 目标
	 * @param src 来源
	 */
	public static void copy(Object dest, Object src) {
		copy(dest, src, false);
	}

	/**
	 * 复制
	 * @param dest 目标
	 * @param src 来源
	 * @param strict 严格复制(src与dest类型相同)
	 */
	public static void copy(Object dest, Object src, boolean strict) {

		if (dest == null || src == null) {
			return;
		}

		if (strict && !StringUtils.equals(dest.getClass().getName(), src.getClass().getName())) {
			return;
		}

		Class<?> cls = dest.getClass();
		// get method
		Set<Method> getMethods = getMethods(cls, new Interceptor() {

			@Override
			public boolean group(Method method) {
				if (!method.getName().startsWith("get")) {
					return false;
				}
				if (method.getParameterTypes().length > 0) {
					return false;
				}
				return true;
			}

		});

		for (Method getMethod : getMethods) {
			Method setMethod = obtainSetter(getMethod, cls);
			if (setMethod != null) {
				try {
					setMethod.invoke(dest, getMethod.invoke(src));
				} catch (Exception e) {
					logger.warn("copy error {} ", e);
				}
			}
		}

	}

	// 获取class的方法
	public static Set<Method> getMethods(Class<?> cls, Interceptor interceptor) {

		Set<Method> sets = Sets.newHashSet();

		if (cls == null || interceptor == null) {
			return sets;
		}

		Method[] methods = cls.getMethods();
		for (Method method : methods) {
			if (interceptor.group(method)) {
				sets.add(method);
			}
		}

		return sets;
	}

	/**
	 * 含有getter
	 * @param setterMethod set 方法
	 * @param cls class
	 * @return 如果有返回,否则返回null
	 */
	public static Method obtainGetter(Method setterMethod, Class<?> cls) {
		if (setterMethod == null || cls == null) {
			return null;
		}
		String name = setterMethod.getName();
		if (!name.startsWith("set")) {
			return null;
		}
		String getName = "get" + name.substring(3);
		Class<?>[] setParameterTypes = setterMethod.getParameterTypes();
		if (setParameterTypes == null || setParameterTypes.length != 1) {
			return null;
		}

		try {
			Method getterMethod = cls.getMethod(getName);
			Class<?> returnType = getterMethod.getReturnType();
			if (!StringUtils.equals(returnType.getName(), setParameterTypes[0].getName())) {
				return null;
			}
			return getterMethod;
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 含有setter
	 * @param getterMethod get 方法
	 * @param cls class 
	 * @return 如果有返回,否则返回null
	 */
	public static Method obtainSetter(Method getterMethod, Class<?> cls) {
		if (getterMethod == null || cls == null) {
			return null;
		}
		String name = getterMethod.getName();
		if (!name.startsWith("get")) {
			return null;
		}
		String setName = "set" + name.substring(3);
		Class<?> type = getterMethod.getReturnType();

		try {
			return cls.getMethod(setName, type);
		} catch (Exception e) {
		}
		return null;
	}

	// 拦截器
	public interface Interceptor {

		/**
		 * 过滤方法
		 * @param method 方法
		 * @return 方法是否有效
		 */
		boolean group(Method method);

	}

}
