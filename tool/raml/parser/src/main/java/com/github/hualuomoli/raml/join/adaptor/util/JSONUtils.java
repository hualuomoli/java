package com.github.hualuomoli.raml.join.adaptor.util;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.raml.model.ParamType;

import com.github.hualuomoli.raml.join.adaptor.entity.DataType;
import com.github.hualuomoli.raml.join.adaptor.entity.ParamData;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * JSON工具类
 * @author hualuomoli
 *
 */
public class JSONUtils {

	/**
	 * 解析Example
	 * @param example Example
	 * @return JSON集合
	 */
	public static List<ParamData> parseExample(String example) {
		/** {
		*   "code" : 0,
		*   "msg": "success",
		*   "data" : [
		* 	  {
		* 	    "name" : "Ipad 64G",
		* 	    "price" : 3200.15,
		* 	    "amount": 10,
		* 	    "def" : false,
		* 	    "time" : "2015-01-02 15:23:48"
		* 	  }
		*   ]
		* }
		*/
		JSONObject jsonObject = new JSONObject(example);
		JSONObject properties = getJSONObject(jsonObject, Schema.PROPERTIES);
		return parseExample(properties);
	}

	/**
	 * 解析Example
	 * @param jsonObject JSONObject
	 * @return JSON集合
	 */
	public static List<ParamData> parseExample(JSONObject jsonObject) {

		List<ParamData> paramDatas = Lists.newArrayList();

		Set<String> keys = jsonObject.keySet();
		for (String key : keys) {
			Object value = jsonObject.get(key);
			ParamData paramData = new ParamData();
			paramData.setDisplayName(key);

			if (value instanceof JSONObject) {
				// object
				List<ParamData> children = parseExample((JSONObject) value);

				// type children
				paramData.setDataType(DataType.OBJECT);
				paramData.setChildren(children);

			} else if (value instanceof JSONArray) {
				// array
				JSONArray array = (JSONArray) value;
				List<ParamData> children = parseExample(array.getJSONObject(0));

				// type children
				paramData.setDataType(DataType.ARRAY);
				paramData.setChildren(children);

			} else {
				// 简单属性
				paramData.setDataType(DataType.SIMPLE);
				paramData.setType(getParamType(value.getClass().getSimpleName()));
				paramData.setExample((String) value);
			}

			paramDatas.add(paramData);

		}

		return paramDatas;
	}

	/**
	 * 解析Schema
	 * @param schema Schema
	 * @return 参数集合
	 */
	public static List<ParamData> parseSchema(String schema) {
		/** {
		*   "$schema": "http://json-schema.org/draft-04/schema#",
		*   "id": "http://jsonschema.net",
		*   "type": "object",
		*   "properties": {
		* 	  "code": {
		* 	    "id": "http://jsonschema.net/code",
		* 	    "type": "integer"
		* 	  },
		* 	  "msg": {
		* 	    "id": "http://jsonschema.net/msg",
		* 	    "type": "string"
		* 	  },
		* 	  "data": {
		* 	    "id": "http://jsonschema.net/data",
		* 	    "type": "array",
		* 	    "items": {
		* 		  "id": "http://jsonschema.net/data/0",
		* 		  "type": "object",
		* 		  "properties": {
		* 		    "name": {
		* 			  "id": "http://jsonschema.net/data/0/name",
		* 			  "type": "string"
		* 		    },
		* 		    "price": {
		* 		  	  "id": "http://jsonschema.net/data/0/price",
		* 		  	  "type": "number"
		* 		    },
		* 		    "amount": {
		* 			  "id": "http://jsonschema.net/data/0/amount",
		* 			  "type": "integer"
		* 		    },
		* 		    "def": {
		* 			  "id": "http://jsonschema.net/data/0/def",
		* 			  "type": "boolean"
		* 		    },
		* 		    "time": {
		* 			  "id": "http://jsonschema.net/data/0/time",
		* 			  "type": "string"
		* 		    }
		* 		  }
		* 	    }
		* 	  }
		*   },
		*   "required": [
		* 	  "code",
		* 	  "msg",
		* 	  "data"
		*   ]
		* }
		*/
		JSONObject jsonObject = new JSONObject(schema);
		JSONObject properties = getJSONObject(jsonObject, Schema.PROPERTIES);
		Set<String> required = getRequired(jsonObject);

		return parseSchema(properties, required);
	}

	/**
	 * 解析Schema
	 * @param properties Schema的properties
	 * @param required properties中参数是否必填 
	 * @return 参数集合
	 */
	public static List<ParamData> parseSchema(JSONObject properties, Set<String> required) {
		List<ParamData> paramDatas = Lists.newArrayList();

		Set<String> keys = properties.keySet();

		for (String key : keys) {
			// data
			JSONObject data = getJSONObject(properties, key);
			String type = getString(data, Schema.TYPE);

			ParamData paramData = new ParamData();
			paramData.setDisplayName(key);
			paramData.setDescription(AdaptorUtils.trans2OneLine(getString(data, Schema.DESCRIPTION)));
			paramData.setRequired(required.contains(key));

			if (StringUtils.equalsIgnoreCase(type, "object")) {
				// object
				JSONObject newProperties = getJSONObject(data, Schema.PROPERTIES);
				Set<String> newRequired = getRequired(data);
				List<ParamData> children = parseSchema(newProperties, newRequired);

				// type children
				paramData.setDataType(DataType.OBJECT);
				paramData.setChildren(children);

			} else if (StringUtils.equalsIgnoreCase(type, "array")) {
				// array
				JSONObject items = getJSONObject(data, Schema.ITEMS);
				JSONObject newProperties = getJSONObject(items, Schema.PROPERTIES);
				Set<String> newRequired = getRequired(items);
				List<ParamData> children = parseSchema(newProperties, newRequired);

				// type children
				paramData.setDataType(DataType.ARRAY);
				paramData.setChildren(children);

			} else {
				paramData.setDataType(DataType.SIMPLE);
				paramData.setType(getParamType(getString(data, Schema.TYPE)));
				// TODO enumeration
				paramData.setDefaultValue(getString(data, Schema.DEFAULT));
				paramData.setExample(getString(data, Schema.EXAMPLE));
				// rule
				setRule(paramData, data);
			}
			paramDatas.add(paramData);
		}
		return paramDatas;
	}

	// 设置规则
	private static void setRule(ParamData paramData, JSONObject data) {
		paramData.setPattern(getString(data, "pattern"));
		paramData.setMinLength(getInteger(data, "minLength"));
		paramData.setMaxLength(getInteger(data, "maxLength"));
		paramData.setMinimum(getBigDecimal(data, "minimum"));
		paramData.setMaximum(getBigDecimal(data, "maximum"));
	}

	/**
	 * 获取参数类型
	 * @param type 参数类型名称
	 * @return 参数类型
	 */
	private static ParamType getParamType(String type) {
		// TODO
		// add other schema type transfer
		switch (type) {
		case "string":
			return ParamType.STRING;
		case "number":
			return ParamType.NUMBER;
		case "integer":
			return ParamType.INTEGER;
		case "date":
			return ParamType.DATE;
		case "boolean":
			return ParamType.BOOLEAN;
		case "file":
			return ParamType.FILE;
		}
		return ParamType.STRING;
	}

	/**
	 * JSONObject是否包含key
	 * @param json JSONObject
	 * @param key Key
	 * @return 是否包含
	 */
	public static boolean has(JSONObject json, String key) {
		if (json == null || StringUtils.isBlank(key)) {
			return false;
		}
		return json.has(key);
	}

	/**
	 * 获取字符串
	 * @param json JSONObject
	 * @param key Key
	 * @return 字符串
	 */
	public static String getString(JSONObject json, String key) {
		if (!has(json, key)) {
			return StringUtils.EMPTY;
		}
		Object value = json.get(key);
		if (value == null) {
			return StringUtils.EMPTY;
		}
		return (String) value;
	}

	/**
	 * 获取整数
	 * @param json
	 * @param key
	 * @return
	 */
	public static Integer getInteger(JSONObject json, String key) {
		String value = getString(json, key);
		if (StringUtils.isBlank(value)) {
			return null;
		}
		return Integer.parseInt(value);
	}

	/**
	 * 获取BigDecimal
	 * @param json 
	 * @param key
	 * @return
	 */
	public static BigDecimal getBigDecimal(JSONObject json, String key) {
		String value = getString(json, key);
		if (StringUtils.isBlank(value)) {
			return null;
		}
		return new BigDecimal(value);
	}

	/**
	 * 获取JSONObject
	 * @param json json数据
	 * @param key key
	 * @return key对应的json数据
	 */
	private static JSONObject getJSONObject(JSONObject json, String key) {
		if (!has(json, key)) {
			return new JSONObject();
		}
		return json.getJSONObject(key);
	}

	/**
	 * 获取必填集合
	 * @param json  json数据
	 * @return 必填集合
	 */
	private static Set<String> getRequired(JSONObject json) {
		Set<String> required = Sets.newHashSet();
		if (!has(json, Schema.REQUIRED)) {
			return required;
		}
		JSONArray array = json.getJSONArray(Schema.REQUIRED);
		for (Iterator<Object> it = array.iterator(); it.hasNext();) {
			String r = (String) it.next();
			required.add(r);
		}
		return required;
	}

	// schema
	private class Schema {
		public static final String DESCRIPTION = "description";
		public static final String TYPE = "type";
		public static final String PROPERTIES = "properties"; // type为object时,有该属性
		public static final String REQUIRED = "required"; // properties中参数是否必填
		public static final String ITEMS = "items"; // type为array时,有该属性

		public static final String DEFAULT = "default"; // 默认值
		public static final String EXAMPLE = "example"; // 例子

	}

}
