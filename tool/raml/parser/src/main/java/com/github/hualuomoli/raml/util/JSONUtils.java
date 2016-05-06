package com.github.hualuomoli.raml.util;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.raml.model.ParamType;
import org.raml.model.parameter.AbstractParam;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * JSON工具类
 * @author hualuomoli
 *
 */
public class JSONUtils {

	/**
	 * example是否是list数据
	 * @param example example
	 * @return example是否是list数据
	 */
	public static boolean isListExample(String example) {
		// example
		/**
		 * [{
		 * 	"name" : "Ipad 64G",
		 * 	"price" : 3200.15,
		 * 	"amount": 10,
		 * 	"def" : false,
		 * 	"time" : "2015-01-02 15:23:48"
		 * },{
		 * 	"name" : "Ipad 64G",
		 * 	"price" : 3200.15,
		 * 	"amount": 10,
		 * 	"def" : false,
		 * 	"time" : "2015-01-02 15:23:48"
		 * }] 
		 */
		if (!(example.trim().startsWith("[") && example.trim().endsWith("]"))) {
			return false;
		}
		try {
			new JSONArray(example);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 解析Example
	 * @param example Example
	 * @return JSON集合
	 */
	public static List<JsonParam> parseExample(String example) {
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
		JSONObject jsonObject;
		if (isListExample(example)) {
			JSONArray jsonArray = new JSONArray(example);
			jsonObject = jsonArray.getJSONObject(0);
		} else {
			jsonObject = new JSONObject(example);
		}
		return parseExample(jsonObject);
	}

	/**
	 * 解析Example
	 * @param jsonObject JSONObject
	 * @return JSON集合
	 */
	public static List<JsonParam> parseExample(JSONObject jsonObject) {

		List<JsonParam> jsonParams = Lists.newArrayList();

		Set<String> keys = jsonObject.keySet();

		for (String key : keys) {
			Object value = jsonObject.get(key);

			JsonParam jsonParam = new JsonParam();

			if (value instanceof JSONObject) {
				// object
				jsonParam.setDataType(JsonParam.DATA_TYPE_OBJECT);

				// children
				List<JsonParam> children = parseExample((JSONObject) value);
				jsonParam.setChildren(children);
			} else if (value instanceof JSONArray) {
				// array
				jsonParam.setDataType(JsonParam.DATA_TYPE_ARRAY);

				// children
				JSONArray array = (JSONArray) value;
				List<JsonParam> children = parseExample(array.getJSONObject(0));
				jsonParam.setChildren(children);
			} else {
				// simple
				jsonParam.setDataType(JsonParam.DATA_TYPE_SIMPLE);

				// attribute
				AbstractParam param = new AbstractParam();
				param.setDisplayName(key);
				param.setType(Tool.getParamType(value.getClass().getSimpleName()));
				param.setExample((String) value);

				// set attribute
				jsonParam.setParam(param);
			}

			jsonParams.add(jsonParam);

		}

		return jsonParams;
	}

	/**
	 * Schema是否是list数据
	 * @param schema Schema
	 * @return Schema是否是list数据
	 */
	public static boolean isListSchema(String schema) {
		/** 
		 * {
		 *   "$schema": "http://json-schema.org/draft-04/schema#",
		 *   "type": "array",
		 *   "items": {
		 *     "type": "object",
		 *     "properties": {
		 *       "name": {
		 *         "type": "string"
		 *       },
		 *       "age": {
		 *         "type": "integer"
		 *       }
		 *     },
		 *     "required": [
		 *       "name",
		 *       "age"
		 *     ]
		 *   }
		 * }
		 */
		JSONObject jsonObject = new JSONObject(schema);
		return jsonObject.has(Schema.ITEMS);
	}

	/**
	 * 解析Schema
	 * @param schema Schema
	 * @return 参数集合
	 */
	public static List<JsonParam> parseSchema(String schema) {
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

		JSONObject properties;
		if (isListSchema(schema)) {
			JSONObject items = Tool.JSON.getJSONObject(jsonObject, Schema.ITEMS);
			properties = Tool.JSON.getJSONObject(items, Schema.PROPERTIES);
		} else {
			properties = Tool.JSON.getJSONObject(jsonObject, Schema.PROPERTIES);
		}
		Set<String> required = Tool.getRequired(jsonObject);

		return parseSchema(properties, required);
	}

	/**
	 * 解析Schema
	 * @param properties Schema的properties
	 * @param required properties中参数是否必填 
	 * @return 参数集合
	 */
	public static List<JsonParam> parseSchema(JSONObject properties, Set<String> required) {
		List<JsonParam> jsonParams = Lists.newArrayList();

		Set<String> keys = properties.keySet();

		for (String key : keys) {
			// data
			JSONObject data = Tool.JSON.getJSONObject(properties, key);
			String type = Tool.JSON.getString(data, Schema.TYPE);

			JsonParam jsonParam = new JsonParam();

			if (StringUtils.equalsIgnoreCase(type, Schema.TYPE_OBJECT)) {
				// object
				jsonParam.setDataType(JsonParam.DATA_TYPE_OBJECT);

				// children
				JSONObject newProperties = Tool.JSON.getJSONObject(data, Schema.PROPERTIES);
				Set<String> newRequired = Tool.getRequired(data);
				List<JsonParam> children = parseSchema(newProperties, newRequired);
				jsonParam.setChildren(children);

				// attribute
				AbstractParam param = new AbstractParam();
				param.setDisplayName(key);
				param.setDescription(RamlUtils.removeLine(Tool.JSON.getString(data, Schema.DESCRIPTION)));
				param.setRequired(required.contains(key));

				// set attribute
				jsonParam.setParam(param);

			} else if (StringUtils.equalsIgnoreCase(type, Schema.TYPE_ARRAY)) {
				// array
				jsonParam.setDataType(JsonParam.DATA_TYPE_ARRAY);

				// children
				JSONObject items = Tool.JSON.getJSONObject(data, Schema.ITEMS);
				JSONObject newProperties = Tool.JSON.getJSONObject(items, Schema.PROPERTIES);
				Set<String> newRequired = Tool.getRequired(items);
				List<JsonParam> children = parseSchema(newProperties, newRequired);
				jsonParam.setChildren(children);

				// attribute
				AbstractParam param = new AbstractParam();
				param.setDisplayName(key);
				param.setDescription(RamlUtils.removeLine(Tool.JSON.getString(data, Schema.DESCRIPTION)));
				param.setRequired(required.contains(key));

				// set attribute
				jsonParam.setParam(param);
			} else {
				// simple
				jsonParam.setDataType(JsonParam.DATA_TYPE_SIMPLE);

				// attribute
				AbstractParam param = new AbstractParam();
				param.setDisplayName(key);
				param.setDescription(RamlUtils.removeLine(Tool.JSON.getString(data, Schema.DESCRIPTION)));
				param.setRequired(required.contains(key));
				param.setType(Tool.getParamType(Tool.JSON.getString(data, Schema.TYPE)));
				// TODO enumeration
				param.setDefaultValue(Tool.JSON.getString(data, Schema.DEFAULT));
				param.setExample(Tool.JSON.getString(data, Schema.EXAMPLE));
				// rule
				Tool.setRule(param, data);

				// set attribute
				jsonParam.setParam(param);
			}
			jsonParams.add(jsonParam);
		}
		return jsonParams;
	}

	// 工具
	private static class Tool {

		/**
		 * 获取参数类型
		 * @param type 参数类型名称
		 * @return 参数类型
		 */
		static ParamType getParamType(String type) {
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
		 * 设置校验规则
		 * @param param 参数
		 * @param data json数据
		 */
		static void setRule(AbstractParam param, JSONObject data) {
			param.setPattern(JSON.getString(data, Schema.PATTERN));
			param.setMinLength(JSON.getInteger(data, Schema.MIN_LENGTH));
			param.setMaxLength(JSON.getInteger(data, Schema.MAX_LENGTH));
			param.setMinimum(JSON.getBigDecimal(data, Schema.MINIMUM));
			param.setMaximum(JSON.getBigDecimal(data, Schema.MAXIMUM));
		}

		/**
		 * 获取必填集合
		 * @param json  json数据
		 * @return 必填集合
		 */
		static Set<String> getRequired(JSONObject json) {
			Set<String> required = Sets.newHashSet();
			if (!JSON.has(json, Schema.REQUIRED)) {
				return required;
			}
			JSONArray array = JSON.getJSONArray(json, Schema.REQUIRED);
			for (Iterator<Object> it = array.iterator(); it.hasNext();) {
				String r = (String) it.next();
				required.add(r);
			}
			return required;
		}

		// json
		static class JSON {

			/**
			 * 是否包含key
			 * @param json JSONObject
			 * @param key Key
			 * @return 是否包含key
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
			 * 获取JSONObject
			 * @param json json数据
			 * @param key key
			 * @return key对应的json数据
			 */
			private static JSONArray getJSONArray(JSONObject json, String key) {
				if (!has(json, key)) {
					return new JSONArray();
				}
				return json.getJSONArray(key);
			}

		}

	}

	// schema
	private class Schema {

		public static final String TYPE_OBJECT = "object";
		public static final String TYPE_ARRAY = "array";

		public static final String TYPE = "type"; // 类型
		public static final String REQUIRED = "required"; // properties中参数是否必填
		public static final String PROPERTIES = "properties"; // type为object时,有该属性
		public static final String ITEMS = "items"; // type为array时,有该属性

		public static final String DESCRIPTION = "description";
		public static final String DEFAULT = "default"; // 默认值
		public static final String EXAMPLE = "example"; // 例子
		// 验证
		public static final String PATTERN = "pattern"; // 正则表达式
		public static final String MIN_LENGTH = "minLength"; // 最小长度
		public static final String MAX_LENGTH = "maxLength"; // 最大长度
		public static final String MINIMUM = "minimum"; // 最小值
		public static final String MAXIMUM = "maximum"; // 最大值

	}

	// json例子的参数
	public static class JsonParam {

		// 数据类型
		public static final int DATA_TYPE_OBJECT = 1;// 对象
		public static final int DATA_TYPE_ARRAY = 2;// 数组
		public static final int DATA_TYPE_SIMPLE = 3;// 简单类型

		private int dataType; // 数据类型
		private List<JsonParam> children;// 子数据
		// 当前数据属性
		private AbstractParam param;

		public JsonParam() {
		}

		public int getDataType() {
			return dataType;
		}

		public void setDataType(int dataType) {
			this.dataType = dataType;
		}

		public List<JsonParam> getChildren() {
			return children;
		}

		public void setChildren(List<JsonParam> children) {
			this.children = children;
		}

		public AbstractParam getParam() {
			return param;
		}

		public void setParam(AbstractParam param) {
			this.param = param;
		}

	}

}
