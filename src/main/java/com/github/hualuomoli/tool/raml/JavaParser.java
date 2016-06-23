package com.github.hualuomoli.tool.raml;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.raml.model.Action;
import org.raml.model.ActionType;
import org.raml.model.MimeType;
import org.raml.model.ParamType;
import org.raml.model.Resource;
import org.raml.model.Response;
import org.raml.model.parameter.AbstractParam;
import org.raml.model.parameter.FormParameter;
import org.raml.model.parameter.QueryParameter;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.commons.util.ProjectUtils;
import com.github.hualuomoli.commons.util.SerializeUtils;
import com.github.hualuomoli.commons.util.TemplateUtils;
import com.github.hualuomoli.tool.raml.entity.RamlJsonParam;
import com.github.hualuomoli.tool.raml.entity.RamlMethod;
import com.github.hualuomoli.tool.raml.entity.RamlParam;
import com.github.hualuomoli.tool.raml.entity.RamlRequest;
import com.github.hualuomoli.tool.raml.entity.RamlResponse;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * java解析
 * @author hualuomoli
 *
 */
public class JavaParser extends AbstractParser {

	private String tplPath = "tpl/raml";
	private String outputPath = ProjectUtils.getLocation();
	private String main = "main";

	private String projectPackageName;
	private String author;
	private String version;

	private Resource resource; // 资源
	private String fullUri; // URI
	private String packageName; // 包名
	private String entityName; // 实体类名称
	private List<RamlMethod> ramlMethodList; // 方法

	public JavaParser() {
	}

	public JavaParser(Boolean test) {
		main = "test";
	}

	/**
	 * 创建文件
	 * 处理当前资源的Actions
	 * 处理当前资源下非子资源的资源 child.getResource == empty
	 * @param resource 资源
	 */
	protected void create(Resource resource) {
		this.resource = resource;

		fullUri = Tool.getResourceFullUri(resource);
		packageName = this._getPackageName();
		entityName = this._getEntityName();

		ramlMethodList = Lists.newArrayList();

		Map<ActionType, Action> actions = resource.getActions();
		for (Action action : actions.values()) {
			ramlMethodList.addAll(JavaTool.parse(action));
		}

		Set<Resource> resources = Tool.getLeafResources(resource);
		for (Resource r : resources) {
			ramlMethodList.addAll(JavaTool.parse(r));
		}
		//
		this._createController();
	}

	private void _createController() {
		String controllerPackageName = projectPackageName + "." + packageName + ".web";
		String controllerJavaName = entityName + "Controller";
		Map<String, Object> map = Maps.newHashMap();
		map.put("desc", resource.getDescription());
		map.put("author", author);
		map.put("version", version);
		map.put("date", new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").format(new Date()));

		map.put("packageName", controllerPackageName); // 包名
		map.put("javaName", controllerJavaName); // 类名
		map.put("uri", fullUri);

		// service
		map.put("servicePackageName", projectPackageName + "." + packageName + ".web");
		map.put("serviceJavaName", entityName + "Service");

		map.put("methods", ramlMethodList);

		// 创建目录
		File dir = new File(outputPath, "src/" + main + "/java/" + controllerPackageName.replaceAll("[.]", "/"));
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 输出
		TemplateUtils.processByResource(tplPath, "controller.tpl", map, new File(dir.getAbsolutePath(), controllerJavaName + ".java"));

	}

	// 包名
	private String _getPackageName() {
		if (StringUtils.isBlank(fullUri)) {
			return StringUtils.EMPTY;
		}
		return fullUri.substring(1).replaceAll("/", ".");
	}

	// 实体类名
	private String _getEntityName() {
		if (StringUtils.isBlank(fullUri)) {
			return StringUtils.EMPTY;
		}
		String[] array = fullUri.substring(1).split("[/]");
		// 最后一个
		return StringUtils.capitalize(array[array.length - 1]);
	}

	// 工具
	private static class JavaTool {

		// 解析
		static List<RamlMethod> parse(Resource resource) {
			List<RamlMethod> ramlMethodList = Lists.newArrayList();
			Map<ActionType, Action> actions = resource.getActions();
			for (Action action : actions.values()) {
				ramlMethodList.addAll(_parse(action, resource.getRelativeUri()));
			}
			return ramlMethodList;
		}

		// 解析
		static List<RamlMethod> parse(Action action) {
			return _parse(action, action.getResource().getRelativeUri());
		}

		// 解析
		private static List<RamlMethod> _parse(Action action, String uri) {
			if (!_valid(action)) {
				throw new RuntimeException("invalid " + action.getResource().getRelativeUri());
			}

			RamlMethod ramlMethod = new RamlMethod();
			ramlMethod.setResultType(_noResult(action) ? "void" : "String");
			ramlMethod.setMethodName(_getMethodName(uri, action.getType()));
			ramlMethod.setUriParams(_getUriParams(action));
			ramlMethod.setFileParams(_getFileParams(action));
			// 增加request请求,对于post请求可能会有多个
			List<RamlMethod> ramlMethodList = Req.addRequest(action, ramlMethod);
			// 增加response响应,响应可能有多个类型
			ramlMethodList = Res.addResponse(action, ramlMethodList);
			return ramlMethodList;
		}

		// 请求
		static class Req {

			// 请求
			static List<RamlMethod> addRequest(Action action, RamlMethod ramlMethod) {

				String methodName = _getMethodName(action.getResource().getRelativeUri(), action.getType());
				String className = StringUtils.capitalize(methodName) + "Entity";

				// method
				switch (action.getType()) {
				case GET:
					// 一个
					RamlRequest getRequest = new RamlRequest();
					getRequest.setClassName(className);
					getRequest.setParams(_getParamsForGetRequest(action));
					ramlMethod.setRequest(getRequest);
					return Lists.newArrayList(ramlMethod);
				case DELETE:
					// 一个
					RamlRequest deleteRequest = new RamlRequest();
					deleteRequest.setClassName(className);
					deleteRequest.setParams(_getParamsForDeleteRequest(action));
					ramlMethod.setRequest(deleteRequest);
					return Lists.newArrayList(ramlMethod);
				case POST:
				case PUT:
					// 可能有多个
					// 请求类型
					Map<String, MimeType> body = action.getBody();
					if (body == null || body.size() == 0) {
						// 没有参数
						RamlRequest postRequest = new RamlRequest();
						postRequest.setClassName(className);
						ramlMethod.setRequest(postRequest);
						return Lists.newArrayList(ramlMethod);
					}
					List<RamlMethod> ramlMethodList = Lists.newArrayList();
					for (String mimeType : body.keySet()) {
						if (StringUtils.equals(mimeType, MIME_TYPE_URLENCODED)) {
							RamlMethod urlencodedRamlMethod = SerializeUtils.clone(ramlMethod);
							RamlRequest request = new RamlRequest();
							request.setClassName(className);
							request.setParams(_getParamsForPostOrPutUrlEncodedRequest(action));
							urlencodedRamlMethod.setRequest(request);
							ramlMethodList.add(urlencodedRamlMethod);
						} else if (StringUtils.equals(mimeType, MIME_TYPE_MULTIPART)) {
							RamlMethod multipartRamlMethod = SerializeUtils.clone(ramlMethod);
							RamlRequest request = new RamlRequest();
							request.setClassName(className + "File");
							request.setParams(_getParamsForPostOrPutMultipartRequest(action));
							multipartRamlMethod.setRequest(request);
							ramlMethodList.add(multipartRamlMethod);
						} else if (StringUtils.equals(mimeType, MIME_TYPE_JSON)) {
							RamlMethod jsonRamlMethod = SerializeUtils.clone(ramlMethod);
							RamlRequest request = new RamlRequest();
							request.setClassName(className + "Json");
							request.setParams(_getParamsForPostOrPutJsonRequest(action));
							request.setJsonParams(_getJsonParamsForPostOrPutJsonRequest(action));
							jsonRamlMethod.setRequest(request);
							ramlMethodList.add(jsonRamlMethod);
						} else {
							throw new RuntimeException("can not supoort mimeType " + mimeType);
						}

					}
					return ramlMethodList;
				default:
					break;
				}
				throw new RuntimeException("can not suppot action " + action);
			}

			// GET请求的参数
			private static List<RamlParam> _getParamsForGetRequest(Action action) {
				List<RamlParam> ramlParms = Lists.newArrayList();

				// uri
				Map<String, UriParameter> uriParameters = action.getResource().getUriParameters();
				for (UriParameter uriParameter : uriParameters.values()) {
					ramlParms.add(_getParam(uriParameter));
				}
				// query
				Map<String, QueryParameter> queryParameters = action.getQueryParameters();
				for (QueryParameter queryParameter : queryParameters.values()) {
					ramlParms.add(_getParam(queryParameter));
				}

				return ramlParms;
			}

			// DELETE请求的参数
			private static List<RamlParam> _getParamsForDeleteRequest(Action action) {
				List<RamlParam> ramlParms = Lists.newArrayList();

				// uri
				Map<String, UriParameter> uriParameters = action.getResource().getUriParameters();
				for (UriParameter uriParameter : uriParameters.values()) {
					ramlParms.add(_getParam(uriParameter));
				}

				return ramlParms;
			}

			// POST/PUT - application/x-www-form-urlencoded 请求的参数
			private static List<RamlParam> _getParamsForPostOrPutUrlEncodedRequest(Action action) {
				List<RamlParam> ramlParms = Lists.newArrayList();

				// uri
				Map<String, UriParameter> uriParameters = action.getResource().getUriParameters();
				for (UriParameter uriParameter : uriParameters.values()) {
					ramlParms.add(_getParam(uriParameter));
				}

				Map<String, MimeType> body = action.getBody();
				if (body == null || body.size() == 0) {
					return ramlParms;
				}

				if (!body.containsKey(MIME_TYPE_URLENCODED)) {
					throw new RuntimeException("this action can not contains " + MIME_TYPE_URLENCODED);
				}

				Map<String, List<FormParameter>> formParameters = body.get(MIME_TYPE_URLENCODED).getFormParameters();
				if (formParameters == null || formParameters.size() == 0) {
					return ramlParms;
				}

				for (String displayName : formParameters.keySet()) {
					List<FormParameter> list = formParameters.get(displayName);
					if (list == null || list.size() != 1) {
						throw new RuntimeException("must set one param for " + displayName);
					}
					FormParameter formParameter = list.get(0);
					ramlParms.add(_getParam(formParameter));
				}

				return ramlParms;
			}

			// POST/PUT - multipart/form-data 请求的参数
			private static List<RamlParam> _getParamsForPostOrPutMultipartRequest(Action action) {
				List<RamlParam> ramlParms = Lists.newArrayList();

				// uri
				Map<String, UriParameter> uriParameters = action.getResource().getUriParameters();
				for (UriParameter uriParameter : uriParameters.values()) {
					ramlParms.add(_getParam(uriParameter));
				}

				Map<String, MimeType> body = action.getBody();
				if (body == null || body.size() == 0) {
					return ramlParms;
				}

				if (!body.containsKey(MIME_TYPE_MULTIPART)) {
					throw new RuntimeException("this action can not contains " + MIME_TYPE_MULTIPART);
				}

				Map<String, List<FormParameter>> formParameters = body.get(MIME_TYPE_MULTIPART).getFormParameters();
				if (formParameters == null || formParameters.size() == 0) {
					return ramlParms;
				}

				for (String displayName : formParameters.keySet()) {
					List<FormParameter> list = formParameters.get(displayName);
					if (list == null || list.size() != 1) {
						throw new RuntimeException("must set one param for " + displayName);
					}
					FormParameter formParameter = list.get(0);
					ramlParms.add(_getParam(formParameter));
				}

				return ramlParms;
			}

			// POST/PUT - application/json 请求的参数
			private static List<RamlParam> _getParamsForPostOrPutJsonRequest(Action action) {
				List<RamlParam> ramlParms = Lists.newArrayList();

				// uri
				Map<String, UriParameter> uriParameters = action.getResource().getUriParameters();
				for (UriParameter uriParameter : uriParameters.values()) {
					ramlParms.add(_getParam(uriParameter));
				}

				return ramlParms;
			}

			// POST/PUT - application/json 请求的参数
			private static List<RamlJsonParam> _getJsonParamsForPostOrPutJsonRequest(Action action) {
				List<RamlJsonParam> jsonParms = Lists.newArrayList();

				Map<String, MimeType> body = action.getBody();
				if (body == null || body.size() == 0) {
					return jsonParms;
				}

				if (!body.containsKey(MIME_TYPE_JSON)) {
					throw new RuntimeException("this action can not contains " + MIME_TYPE_JSON);
				}

				String schema = body.get(MIME_TYPE_JSON).getSchema();
				if (StringUtils.isBlank(schema)) {
					throw new RuntimeException("schema must not be empty.");
				}
				return Json.getParams(schema);
			}
		}

		// 响应
		static class Res {

			static List<RamlMethod> addResponse(Action action, List<RamlMethod> ramlMethodList) {
				List<RamlMethod> resultList = Lists.newArrayList();
				for (RamlMethod ramlMethod : ramlMethodList) {
					resultList.addAll(_addResponse(action, ramlMethod));
				}
				return resultList;
			}

			private static List<RamlMethod> _addResponse(Action action, RamlMethod ramlMethod) {
				List<RamlMethod> ramlMethodList = Lists.newArrayList();

				Map<String, Response> responses = action.getResponses();
				if (responses == null || responses.size() == 0) {
					return Lists.newArrayList(ramlMethod);
				}

				for (String status : responses.keySet()) {
					if (StringUtils.equals(status, STATUS_OK)) {
						ramlMethodList.addAll(OK.addResponse(action, ramlMethod));
					} else {
						throw new RuntimeException("can not support status " + status);
					}
				}

				return ramlMethodList;
			}

			// OK
			static class OK {

				private static List<RamlMethod> addResponse(Action action, RamlMethod ramlMethod) {

					Map<String, MimeType> body = action.getResponses().get(STATUS_OK).getBody();
					if (body == null || body.size() == 0) {
						return Lists.newArrayList(ramlMethod);
					}

					List<RamlMethod> resultList = Lists.newArrayList();
					for (String mimeType : body.keySet()) {
						if (StringUtils.equals(mimeType, MIME_TYPE_JSON)) {
							RamlMethod jsonRamlMethod = SerializeUtils.clone(ramlMethod);
							_addResponseForJson(action, jsonRamlMethod);
							resultList.add(jsonRamlMethod);
						}
					}
					return resultList;
				}

				// json
				private static void _addResponseForJson(Action action, RamlMethod ramlMethod) {

					String schema = action.getResponses().get(STATUS_OK).getBody().get(MIME_TYPE_JSON).getSchema();
					if (StringUtils.isBlank(schema)) {
						throw new RuntimeException("schema must not be empty.");
					}

					String methodName = _getMethodName(action.getResource().getRelativeUri(), action.getType());
					String className = StringUtils.capitalize(methodName) + "ResultEntity";

					// TODO
					List<RamlJsonParam> jsonParams = Json.getParams(schema);
					RamlResponse res = new RamlResponse();
					res.setClassName(className);
					res.setJsonParams(jsonParams);
					ramlMethod.setResponse(res);
				}

			}

		}

		// JSON
		static class Json {

			/** 
			* {
			*   "$schema": "http://json-schema.org/draft-04/schema#",
			*   "id": "http://jsonschema.net",
			*   "type": "object",
			*   "properties": {
			*     "code": {
			*       "id": "http://jsonschema.net/code",
			*       "type": "string",
			*       "description": "响应编码(0=成功)"
			*     },
			*     "msg": {
			*       "id": "http://jsonschema.net/msg",
			*       "type": "string",
			*       "description": "错误信息"
			*     },
			*     "page": {
			*       "id": "http://jsonschema.net/page",
			*       "type": "object",
			*       "description": "响应数据",
			*       "properties": {
			*         "total": {
			*           "id": "http://jsonschema.net/page/total",
			*           "type": "integer",
			*           "description": "总条数",
			*           "example": "100"
			*         },
			*         "pageSize": {
			*           "id": "http://jsonschema.net/page/pageSize",
			*           "type": "integer",
			*           "description": "每页条数",
			*           "example": "10"
			*         },
			*         "pageNumber": {
			*           "id": "http://jsonschema.net/page/pageNumber",
			*           "type": "integer",
			*           "description": "当前页数"
			*         },
			*         "photos": {
			*           "id": "http://jsonschema.net/page/photos",
			*           "type": "array",
			*           "description": "照片",
			*           "items": {
			*             "id": "http://jsonschema.net/page/photos/0",
			*             "type": "object",
			*             "properties": {
			*               "id": {
			*                 "id": "http://jsonschema.net/page/photos/0/id",
			*                 "type": "string",
			*                 "description": "照片唯一id"
			*               },
			*               "name": {
			*                 "id": "http://jsonschema.net/page/photos/0/name",
			*                 "type": "string",
			*                 "description": "照片名称"
			*               },
			*               "url": {
			*                 "id": "http://jsonschema.net/page/photos/0/url",
			*                 "type": "string",
			*                 "description": "照片url访问路径"
			*               },
			*               "suffix": {
			*                 "id": "http://jsonschema.net/page/photos/0/suffix",
			*                 "type": "string",
			*                 "description": "文件后辍名"
			*               },
			*               "shootTime": {
			*                 "id": "http://jsonschema.net/page/photos/0/shootTime",
			*                 "type": "string",
			*                 "description": "照片拍摄时间"
			*               },
			*               "fileType": {
			*                 "id": "http://jsonschema.net/page/photos/0/fileType",
			*                 "type": "integer",
			*                 "description": "资源类别(1=照片,2=视频)"
			*               },
			*               "location": {
			*                 "id": "http://jsonschema.net/page/photos/0/location",
			*                 "type": "string",
			*                 "description": "拍摄位置"
			*               }
			*             },
			*             "required": [
			*               "id",
			*               "name",
			*               "url",
			*               "suffix"
			*             ]
			*           }
			*         }
			*       },
			*       "required": [
			*         "total",
			*         "pageSize",
			*         "pageNumber"
			*       ]
			*     }
			*   },
			*   "required": [
			*     "code"
			*   ]
			* }
			*/
			// 参数
			static List<RamlJsonParam> getParams(String schema) {
				JSONObject jsonObject = new JSONObject(schema);
				return _getObjectParams(jsonObject);
			}

			// Object参数
			static List<RamlJsonParam> _getObjectParams(JSONObject jsonObject) {

				List<RamlJsonParam> ramlJsonParamList = Lists.newArrayList();

				JSONObject properties = jsonObject.getJSONObject(Schema.PROPERTIES);
				Set<String> requredSet = _getRequired(jsonObject);

				Set<String> keys = properties.keySet();
				for (String key : keys) {
					JSONObject keyObject = properties.getJSONObject(key);
					String type = keyObject.getString(Schema.TYPE);
					String description = keyObject.has(Schema.DESCRIPTION) ? keyObject.getString(Schema.DESCRIPTION) : "描述";
					switch (type) {
					case "array": // 数组
						RamlJsonParam arrayJsonParam = new RamlJsonParam();
						String name = key;
						if (key.endsWith("s") || key.endsWith("S")) {
							name = key.substring(0, key.length() - 1);
						} else if (StringUtils.equalsIgnoreCase(key, "list")) {
							logger.warn("please set property name. not use list");
							name = "list";
						} else if (key.endsWith("list") || key.endsWith("List")) {
							name = key.substring(0, key.length() - 4);
						}
						String arrayCalssName = StringUtils.capitalize(name);
						arrayJsonParam.setClassName(arrayCalssName);
						arrayJsonParam.setName(key);
						arrayJsonParam.setType("java.util.List<" + arrayCalssName + ">");
						arrayJsonParam.setComment(description);
						arrayJsonParam.setChildren(_getArrayParams(keyObject));
						ramlJsonParamList.add(arrayJsonParam);

						// annos
						if (requredSet.contains(key)) {
							// @NotEmpty(message = "必填选项")
						}
						break;
					case "object": // Object
						RamlJsonParam objectJsonParam = new RamlJsonParam();

						String objectCalssName = StringUtils.capitalize(key);
						objectJsonParam.setClassName(objectCalssName);
						objectJsonParam.setName(key);
						objectJsonParam.setType(objectCalssName);
						objectJsonParam.setComment(description);
						objectJsonParam.setChildren(_getObjectParams(keyObject));
						ramlJsonParamList.add(objectJsonParam);
						break;
					case "integer": // integer
						RamlJsonParam integerJsonParam = new RamlJsonParam();
						List<String> integerAnnos = Lists.newArrayList();
						// @Min(value = 1, message = "")
						if (keyObject.has(Schema.MINIMUM)) {
							long minimum = Long.parseLong(keyObject.getString(Schema.MINIMUM));
							integerAnnos.add("@Min(value = " + minimum + ", message = \"" + description + "不能小于" + minimum + "\")");
						}
						// @Max(value = 1, message = "")
						if (keyObject.has(Schema.MAXIMUM)) {
							long maximum = Long.parseLong(keyObject.getString(Schema.MAXIMUM));
							integerAnnos.add("@Max(value = " + maximum + ", message = \"" + description + "不能大于" + maximum + "\")");
						}
						integerJsonParam.setName(key);
						integerJsonParam.setType("Integer");
						integerJsonParam.setComment(description);
						integerJsonParam.setAnnos(integerAnnos);
						ramlJsonParamList.add(integerJsonParam);
						break;
					case "number": // double
						RamlJsonParam doubleJsonParam = new RamlJsonParam();
						List<String> doubleAnnos = Lists.newArrayList();
						// @Min(value = 1, message = "")
						if (keyObject.has(Schema.MINIMUM)) {
							double minimum = Double.parseDouble(keyObject.getString(Schema.MINIMUM));
							doubleAnnos.add("@Min(value = " + minimum + "D, message = \"" + description + "不能小于" + minimum + "\")");
						}
						// @Max(value = 1, message = "")
						if (keyObject.has(Schema.MAXIMUM)) {
							double maximum = Double.parseDouble(keyObject.getString(Schema.MAXIMUM));
							doubleAnnos.add("@Max(value = " + maximum + "D, message = \"" + description + "不能大于" + maximum + "\")");
						}
						doubleJsonParam.setName(key);
						doubleJsonParam.setType("Double");
						doubleJsonParam.setComment(description);
						doubleJsonParam.setAnnos(doubleAnnos);
						ramlJsonParamList.add(doubleJsonParam);
						break;
					case "string": // string
						RamlJsonParam stringJsonParam = new RamlJsonParam();
						List<String> stringAnnos = Lists.newArrayList();
						// @Length(min = 1, max = 5, message = "用户名长度在1-5之间")
						int minLength = 0;
						int maxLength = 0;
						if (keyObject.has(Schema.MIN_LENGTH)) {
							minLength = Integer.parseInt(keyObject.getString(Schema.MIN_LENGTH));
						}
						if (keyObject.has(Schema.MAX_LENGTH)) {
							maxLength = Integer.parseInt(keyObject.getString(Schema.MAX_LENGTH));
						}
						if (minLength > 0 && maxLength > 0) {
							stringAnnos.add("@Length(min = " + minLength + ", max = " + maxLength + ", message = \"" + description + "长度在" + minLength + " - " + maxLength + "之间\")");
						} else if (minLength > 0) {
							stringAnnos.add("@Length(min = " + minLength + ", message = \"" + description + "长度不能小于" + minLength + "\")");
						} else if (maxLength > 0) {
							stringAnnos.add("@Length(max = " + minLength + ", message = \"" + description + "长度不能大于" + maxLength + "\")");
						}
						// @Pattern(regexp = "", message = "")
						if (keyObject.has(Schema.PATTERN)) {
							String pattern = keyObject.getString(Schema.PATTERN);
							stringAnnos.add("@Pattern(regexp = \"" + pattern.replaceAll("\\\\", "\\\\\\\\") + "\", message = \"" + description + "格式不正确\")");
						}
						stringJsonParam.setName(key);
						stringJsonParam.setType("String");
						stringJsonParam.setComment(description);
						stringJsonParam.setAnnos(stringAnnos);
						ramlJsonParamList.add(stringJsonParam);
						break;
					case "date":
						RamlJsonParam dateJsonParam = new RamlJsonParam();
						List<String> dateAnnos = Lists.newArrayList();
						// @DateTimeFormat(pattern = "")
						String example = jsonObject.has(Schema.EXAMPLE) ? jsonObject.getString(Schema.EXAMPLE) : "";
						if (StringUtils.isNotBlank(example)) {
							String pattern;
							if (example.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
								pattern = "yyyy-MM-dd kk:mm:ss";
							} else if (example.matches("\\d{4}-\\d{2}-\\d{2}")) {
								pattern = "yyyy-MM-dd";
							} else if (example.matches("\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
								pattern = "yyyy/MM/dd kk:mm:ss";
							} else if (example.matches("\\d{4}/\\d{2}/\\d{2}")) {
								pattern = "yyyy/MM/dd";
							} else if (example.matches("\\d{14}")) {
								pattern = "yyyyMMddkkmmss";
							} else if (example.matches("\\d{8}")) {
								pattern = "yyyyMMdd";
							} else if (example.matches("\\d{2}:\\d{2}:\\d{2}")) {
								pattern = "kkmmss";
							} else {
								throw new RuntimeException("can not support patter " + example);
							}
							dateAnnos.add("@DateTimeFormat(pattern = \"" + pattern + "\")");
						}

						dateJsonParam.setName(key);
						dateJsonParam.setType("Date");
						dateJsonParam.setComment(description);
						dateJsonParam.setAnnos(dateAnnos);
						ramlJsonParamList.add(dateJsonParam);
						break;
					case "boolean":
						RamlJsonParam booleanJsonParam = new RamlJsonParam();
						List<String> booleanAnnos = Lists.newArrayList();

						booleanJsonParam.setName(key);
						booleanJsonParam.setType("Boolean");
						booleanJsonParam.setComment(description);
						booleanJsonParam.setAnnos(booleanAnnos);
						ramlJsonParamList.add(booleanJsonParam);
						break;
					default:
						throw new RuntimeException("can not support type " + type);
					}
					// end switch
				}
				return ramlJsonParamList;
			}

			// Array参数
			static List<RamlJsonParam> _getArrayParams(JSONObject jsonObject) {
				return _getObjectParams(jsonObject.getJSONObject(Schema.ITEMS));
			}

			// 必填
			static Set<String> _getRequired(JSONObject jsonObject) {
				Set<String> sets = Sets.newHashSet();
				if (!jsonObject.has(Schema.REQUIRED)) {
					return sets;
				}
				JSONArray required = jsonObject.getJSONArray(Schema.REQUIRED);
				for (Object object : required) {
					sets.add(object.toString());
				}
				return sets;
			}

			// schema
			static class Schema {

				public static final String TYPE = "type"; // 类型
				public static final String REQUIRED = "required"; // properties中参数是否必填
				public static final String PROPERTIES = "properties";
				public static final String ITEMS = "items";

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

		}

		// URI参数
		private static List<RamlParam> _getUriParams(Action action) {
			List<RamlParam> params = Lists.newArrayList();

			// uri
			Map<String, UriParameter> uriParameters = Tool.getResourceFullUriParameters(action.getResource());
			for (UriParameter uriParameter : uriParameters.values()) {
				RamlParam param = _getParam(uriParameter);
				// 该注解为路径注解,非属性注解
				List<String> annos = Lists.newArrayList();
				String anno = "@PathVariable(value = \"" + uriParameter.getDisplayName() + "\")";
				annos.add(anno);
				param.setAnnos(annos);

				params.add(param);
			}

			return params;
		}

		// File参数
		private static List<RamlParam> _getFileParams(Action action) {
			List<RamlParam> params = Lists.newArrayList();

			// file
			// body
			Map<String, MimeType> body = action.getBody();
			if (body != null && body.size() > 0) {
				if (body.size() > 1) {
					throw new RuntimeException("can not support " + body.size() + "form mimeType for " + action.getResource().getRelativeUri());
				}
				if (body.containsKey("multipart/form-data")) {
					Map<String, List<FormParameter>> formParameters = body.get("multipart/form-data").getFormParameters();
					for (String displayName : formParameters.keySet()) {
						List<FormParameter> list = formParameters.get(displayName);
						FormParameter formParameter = list.get(0);
						// 文件
						if (formParameter.getType() == ParamType.FILE) {
							RamlParam param = _getParam(formParameter);
							// 该注解为路径注解,非属性注解
							List<String> annos = Lists.newArrayList();
							String anno = "@RequestParam(value = \"" + formParameter.getDisplayName() + "\", required = " + (formParameter.isRequired() ? "true" : "false") + ")";
							annos.add(anno);
							param.setAnnos(annos);

							params.add(param);
						}
					}
				}
			}

			return params;
		}

		// 参数
		private static RamlParam _getParam(AbstractParam abstractParam) {
			RamlParam ramlParam = new RamlParam();
			ramlParam.setName(abstractParam.getDisplayName());
			// type
			switch (abstractParam.getType()) {
			case STRING:
				ramlParam.setType("String");
				break;
			case INTEGER:
				BigDecimal maxinum = abstractParam.getMaximum();
				if (maxinum != null && maxinum.doubleValue() > Integer.MAX_VALUE) {
					ramlParam.setType("Long");
				} else {
					ramlParam.setType("Integer");
				}
				break;
			case NUMBER:
				ramlParam.setType("Double");
				break;
			case BOOLEAN:
				ramlParam.setType("String");
				break;
			case DATE:
				ramlParam.setType("Date");
				break;
			case FILE:
				ramlParam.setType("MultipartFile");
				break;
			}
			ramlParam.setComment(abstractParam.getDescription());
			ramlParam.setAnnos(Valid.getValid(abstractParam));
			return ramlParam;
		}

		// 有效性注解
		static class Valid {

			/**
			 * 获取校验规则
			 * @param jsonParam 参数
			 * @return 校验规则
			 */
			static List<String> getValid(AbstractParam abstractParam) {
				List<String> valids = Lists.newArrayList();

				String notNull = _getNotNull(abstractParam);
				String notBlank = _getNotBlank(abstractParam);
				String length = _getLength(abstractParam);
				String min = _getMin(abstractParam);
				String max = _getMax(abstractParam);
				String pattern = _getPattern(abstractParam);
				String dateFormatPattern = _getDateFormatPattern(abstractParam);

				if (notNull != null) {
					valids.add(notNull);
				}
				if (notBlank != null) {
					valids.add(notBlank);
				}
				if (length != null) {
					valids.add(length);
				}
				if (min != null) {
					valids.add(min);
				}
				if (max != null) {
					valids.add(max);
				}
				if (pattern != null) {
					valids.add(pattern);
				}
				if (dateFormatPattern != null) {
					valids.add(dateFormatPattern);
				}

				return valids;
			}

			/**
			 * 是否必填
			 * @param jsonParam
			 * @return
			 */
			static boolean _isRequired(AbstractParam abstractParam) {
				return abstractParam.isRequired();
			}

			// 不能为空
			// @NotNull(message = "")
			static String _getNotNull(AbstractParam abstractParam) {
				if (!_isRequired(abstractParam)) {
					return null;
				}
				return "@NotNull(message = \"" + abstractParam.getDescription() + "必填\")";
			}

			// 不能为空(字符串)
			// @NotBlank(message = "")
			static String _getNotBlank(AbstractParam abstractParam) {
				if (abstractParam.getType() != ParamType.STRING) {
					return null;
				}
				return "@NotBlank(message = \"" + abstractParam.getDescription() + "不能为空\")";
			}

			// 长度限制
			// @Length(min = 1, max = 5, message = "用户名长度在1-5之间")
			static String _getLength(AbstractParam abstractParam) {
				if (abstractParam.getType() != ParamType.STRING) {
					return null;
				}
				Integer minLength = abstractParam.getMinLength();
				Integer maxLength = abstractParam.getMaxLength();

				if (minLength == null && maxLength == null) {
					return null;
				}

				int min = minLength == null ? 0 : minLength.intValue();
				int max = maxLength == null ? 0 : maxLength.intValue();

				if (min > 0 && max == 0) {
					// 只设置了最小长度
					// @Length(min = 1, message = "数据长度不能小于1")
					return "@Length(min = " + min + ", message = \"数据长度不能小于" + min + "\")";
				} else if (min == 0 && max > 0) {
					// 只设置了最大长度
					// @Length(max = 5, message = "数据长度不能大于5")
					return "@Length(max = " + max + ", message = \"数据长度不能大于" + max + "\")";
				} else if (min > 0 && max > 0) {
					// 设置了最小长度和最大长度
					// @Length(min = 1, max = 5, message = "用户名长度在1-5之间")
					return "@Length(min = " + min + ", max = " + max + ", message = \"用户名长度在" + min + "-" + max + "之间\")";
				}
				return null;
			}

			// 设置了最小值
			// @Min(value = 1, message = "")
			static String _getMin(AbstractParam abstractParam) {
				if (abstractParam.getType() != ParamType.INTEGER && abstractParam.getType() != ParamType.NUMBER) {
					return null;
				}
				// @Min(value = 1, message = "")
				BigDecimal minimum = abstractParam.getMinimum();
				if (minimum == null) {
					return null;
				}
				long min = minimum.longValue();
				return "@Min(value = " + min + "L, message = \"最小值" + min + "\")";
			}

			// 设置了最大值
			// @Max(value = 10, message = "")
			static String _getMax(AbstractParam abstractParam) {
				if (abstractParam.getType() != ParamType.INTEGER && abstractParam.getType() != ParamType.NUMBER) {
					return null;
				}
				// @Max(value = 10, message = "")
				BigDecimal maxinum = abstractParam.getMaximum();
				if (maxinum == null) {
					return null;
				}
				long max = maxinum.longValue();
				return "@Max(value = " + max + "L, message = \"最大值" + max + "\")";
			}

			// 正则表达式
			// @Pattern(regexp = "", message = "")
			static String _getPattern(AbstractParam abstractParam) {
				if (abstractParam.getType() != ParamType.STRING) {
					return null;
				}
				String pattern = abstractParam.getPattern();
				if (StringUtils.isBlank(pattern)) { // 不需要正则表达式验证
					return null;
				}
				String regexp = pattern.replaceAll("\\\\", "\\\\\\\\");
				return "@Pattern(regexp = \"" + regexp + "\", message = \"" + abstractParam.getDescription() + "格式不正确\")";
			}

			// 日期格式化
			// @DateTimeFormat(pattern = "")
			static String _getDateFormatPattern(AbstractParam abstractParam) {
				if (abstractParam.getType() != ParamType.DATE) {
					return null;
				}
				String example = abstractParam.getExample();
				if (StringUtils.isBlank(example)) {
					throw new RuntimeException("please set example first.");
				}
				String pattern;
				if (example.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
					pattern = "yyyy-MM-dd kk:mm:ss";
				} else if (example.matches("\\d{4}-\\d{2}-\\d{2}")) {
					pattern = "yyyy-MM-dd";
				} else if (example.matches("\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
					pattern = "yyyy/MM/dd kk:mm:ss";
				} else if (example.matches("\\d{4}/\\d{2}/\\d{2}")) {
					pattern = "yyyy/MM/dd";
				} else if (example.matches("\\d{14}")) {
					pattern = "yyyyMMddkkmmss";
				} else if (example.matches("\\d{8}")) {
					pattern = "yyyyMMdd";
				} else if (example.matches("\\d{2}:\\d{2}:\\d{2}")) {
					pattern = "kkmmss";
				} else {
					throw new RuntimeException("can not support patter " + example);
				}
				return "@DateTimeFormat(pattern = \"" + pattern + "\")";
			}

		}

		// end
		// 获取方法名
		private static String _getMethodName(String uri, ActionType actionType) {
			return actionType.toString().toLowerCase() + StringUtils.capitalize(_getEntityName(uri));
		}

		// end
		// 获取实体类名
		private static String _getEntityName(String uri) {
			String methodName = "";
			if (StringUtils.isBlank(uri)) {
				return methodName;
			}
			String[] array = uri.substring(1).split("[/]");
			boolean dynamic = false;
			for (String str : array) {
				if (str.startsWith("{") && str.endsWith("}")) {
					if (!dynamic) {
						dynamic = true;
						methodName += "By";
					}
					// 去掉前后的{}
					str = str.substring(1, str.length() - 1);
				}
				methodName += StringUtils.capitalize(str);
			}
			return methodName;
		}

		// 是否没有响应
		private static boolean _noResult(Action action) {
			Map<String, Response> responses = action.getResponses();
			if (responses == null || responses.size() == 0) {
				// 没有响应,如文件下载
				return true;
			}
			return false;
		}

		// 是否有效
		private static boolean _valid(Action action) {
			Map<String, Response> responses = action.getResponses();
			if (responses == null || responses.size() == 0) {
				// 没有响应,如文件下载
				return true;
			}
			for (String status : responses.keySet()) {
				if (!StringUtils.equals(status, "200")) {
					logger.error("can not support response status ", status);
					return false;
				}
				Response response = responses.get(status);
				Map<String, MimeType> body = response.getBody();
				if (body == null) {
					// 没有响应,如文件下载
					return true;
				}
				for (String mimeType : body.keySet()) {
					if (!StringUtils.equals(mimeType, MIME_TYPE_JSON)) {
						logger.error("can not support response body mimeType ", mimeType);
						return false;
					}
				}
			}
			return true;
		}

	}

	public void setProjectPackageName(String projectPackageName) {
		this.projectPackageName = projectPackageName;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
