package com.github.hualuomoli.commons.json;

import com.fasterxml.jackson.databind.JavaType;

/**
 * JSON转换器
 * @author liubaoquan
 *
 */
public class JsonMapper extends BaseJsonMapper {

	private static final long serialVersionUID = 2775545513213191891L;
	private static final JsonMapper mapper = new JsonMapper();

	private JsonMapper() {
		super();
		super.config();
	}

	public static JsonMapper getInstance() {
		return mapper;
	}

	/**
	 * 序列化Object
	 * @param object 需要序列化的Object
	 * @return 序列化后的字符串
	 */
	public static String toJsonString(Object object) {
		return getInstance().toJson(object);
	}

	/**
	 * 反序列化
	 * @param jsonString data
	 * @param clazz object的class
	 * @return object instance
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromJsonString(String jsonString, Class<?> clazz) {
		return (T) getInstance().fromJson(jsonString, clazz);
	}

	/**
	* 反序列化
	* @param jsonString data
	* @param javaType javaType类型数据 {@link BaseJsonMapper#getJavaType(Class, Class...) }
	* @return object instance
	*/
	@SuppressWarnings("unchecked")
	public static <T> T fromJsonString(String jsonString, JavaType javaType) {
		return (T) getInstance().fromJson(jsonString, javaType);
	}

	/**
	 * 创建集合的javaType
	 * ArrayList<MyBean>, 则调用constructCollectionType(ArrayList.class,MyBean.class)
	 * HashMap<String,MyBean>, 则调用(HashMap.class,String.class, MyBean.class)
	 * 
	 * @param collectionClass 集合的类
	 * @param elementClasses 属性的类
	 * @return javaType
	 */
	public static JavaType getJavaType(Class<?> collectionClass, Class<?>... elementClasses) {
		return getInstance().constructParametricType(collectionClass, elementClasses);
	}

}
