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
import com.github.hualuomoli.tool.raml.entity.RamlMethodMimeType;
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

	// 配置
	public String projectPackageName = "com.github.hualuomoli";
	public String author = "hualuomoli";
	public String version = "1.0";
	public static boolean autoRemoveResultWrap = true; // 自动去除返回的外层封装
	public static String pageSizeName = "pageSize";
	public static String pageNumberName = "pageNumber";
	public static String pageTotalName = "total";
	public static String restResponsePackageName = "com.github.hualuomoli.rest";
	public static String restResponseClassName = "AppRestResponse";

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

		// 当前资源下的请求
		Map<ActionType, Action> actions = resource.getActions();
		for (Action action : actions.values()) {
			JavaTool tool = new JavaTool(action, action.getResource().getRelativeUri());
			List<RamlMethod> list = tool.parse();
			ramlMethodList.addAll(list);
		}

		// 当前资源下没有子资源的请求
		Set<Resource> resources = Tool.getLeafResources(resource);
		for (Resource r : resources) {
			Map<ActionType, Action> as = r.getActions();
			for (Action a : as.values()) {
				JavaTool tool = new JavaTool(a, r.getRelativeUri());
				List<RamlMethod> list = tool.parse();
				ramlMethodList.addAll(list);
			}
		}

		// 生成
		this._create();
	}

	// 生成
	private void _create() {
		this._createController();
		if (autoRemoveResultWrap) {
			this._createService();
			this._createDao();
			this._createXml();
		}
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
		map.put("servicePackageName", projectPackageName + "." + packageName + ".service");
		map.put("serviceJavaName", entityName + "Service");

		// rest response
		map.put("restResponsePackageName", restResponsePackageName);
		map.put("restResponseClassName", restResponseClassName);

		// autoRemoveResultWrap
		map.put("autoRemoveResultWrap", autoRemoveResultWrap ? "Y" : "N");

		map.put("methods", ramlMethodList);

		// 创建目录
		File dir = new File(outputPath, "src/" + main + "/java/" + controllerPackageName.replaceAll("[.]", "/"));
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 输出
		TemplateUtils.processByResource(tplPath, "controller.tpl", map, new File(dir.getAbsolutePath(), controllerJavaName + ".java"));

	}

	private void _createService() {
		String servicePackageName = projectPackageName + "." + packageName + ".service";
		String serviceJavaName = entityName + "Service";
		Map<String, Object> map = Maps.newHashMap();
		map.put("desc", resource.getDescription());
		map.put("author", author);
		map.put("version", version);
		map.put("date", new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").format(new Date()));

		map.put("packageName", servicePackageName); // 包名
		map.put("javaName", serviceJavaName); // 类名

		// controller
		map.put("controllerPackageName", projectPackageName + "." + packageName + ".web");
		map.put("controllerJavaName", entityName + "Controller");

		// mapper
		map.put("mapperPackageName", projectPackageName + "." + packageName + ".mapper");
		map.put("mapperJavaName", entityName + "Mapper");

		map.put("methods", ramlMethodList);

		// 创建目录
		File dir = new File(outputPath, "src/" + main + "/java/" + servicePackageName.replaceAll("[.]", "/"));
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 输出
		TemplateUtils.processByResource(tplPath, "service.tpl", map, new File(dir.getAbsolutePath(), serviceJavaName + ".java"));

	}

	private void _createDao() {
		String mapperPackageName = projectPackageName + "." + packageName + ".mapper";
		String mapperJavaName = entityName + "Mapper";
		Map<String, Object> map = Maps.newHashMap();
		map.put("desc", resource.getDescription());
		map.put("author", author);
		map.put("version", version);
		map.put("date", new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").format(new Date()));

		map.put("packageName", mapperPackageName); // 包名
		map.put("javaName", mapperJavaName); // 类名

		// controller
		map.put("controllerPackageName", projectPackageName + "." + packageName + ".web");
		map.put("controllerJavaName", entityName + "Controller");

		map.put("methods", ramlMethodList);

		// 创建目录
		File dir = new File(outputPath, "src/" + main + "/java/" + mapperPackageName.replaceAll("[.]", "/"));
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 输出
		TemplateUtils.processByResource(tplPath, "dao.tpl", map, new File(dir.getAbsolutePath(), mapperJavaName + ".java"));

	}

	private void _createXml() {
		String mapperPackageName = projectPackageName + "." + packageName + ".mapper";
		String mapperJavaName = entityName + "Mapper";
		Map<String, Object> map = Maps.newHashMap();
		map.put("desc", resource.getDescription());
		map.put("author", author);
		map.put("version", version);
		map.put("date", new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").format(new Date()));

		map.put("packageName", mapperPackageName); // 包名
		map.put("javaName", mapperJavaName); // 类名

		// controller
		map.put("controllerPackageName", projectPackageName + "." + packageName + ".web");
		map.put("controllerJavaName", entityName + "Controller");

		map.put("methods", ramlMethodList);

		// 创建目录
		File dir = new File(outputPath, "src/" + main + "/resources/mappers/" + packageName.replaceAll("[.]", "/"));
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 输出
		TemplateUtils.processByResource(tplPath, "xml.tpl", map, new File(dir.getAbsolutePath(), mapperJavaName + ".xml"));

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
	public static class JavaTool {

		private Action action; // 事件
		private String relativeUri; // 相对URI(方法上的资源地址注解)
		private boolean hasResult = true; // 是否有返回值
		private String entityName; // 实体类名称
		private String methodName; // 请求方法名称
		private String upperMethodName; // 请求方法名称

		public JavaTool(Action action, String relativeUri) {
			this.action = action;
			this.relativeUri = relativeUri;
			_init();
		}

		// 初始化
		private void _init() {
			// 设置是否有返回值
			Map<String, Response> responses = action.getResponses();
			if (responses == null || responses.size() == 0) {
				// 没有响应
				hasResult = false;
			}
			// 设置实体类名称
			_setEntityName();
			// 设置方法名称
			methodName = action.getType().toString().toLowerCase() + entityName;
			upperMethodName = StringUtils.capitalize(methodName);
			// 设置URI
		}

		// 设置实体类名称
		private void _setEntityName() {
			entityName = "";
			if (StringUtils.isBlank(relativeUri)) {
				return;
			}
			String[] array = relativeUri.substring(1).split("[/]");
			boolean dynamic = false;
			for (String str : array) {
				if (str.startsWith("{") && str.endsWith("}")) {
					if (!dynamic) {
						dynamic = true;
						entityName += "By";
					}
					// 去掉前后的{}
					str = str.substring(1, str.length() - 1);
				}
				entityName += StringUtils.capitalize(str);
			}
		}

		// 解析
		public List<RamlMethod> parse() {

			RamlMethod ramlMethod = new RamlMethod();
			ramlMethod.setHasResult(hasResult ? "Y" : "N");
			ramlMethod.setMethodName(methodName);
			ramlMethod.setUriParams(this._getUriParams());
			ramlMethod.setFileParams(this._getFileParams());
			// mimeType
			RamlMethodMimeType methodMimeType = new RamlMethodMimeType();
			methodMimeType.setUri(relativeUri);
			methodMimeType.setMethod(action.getType().toString());
			ramlMethod.setMethodMimeType(methodMimeType);

			// 增加request请求,对于post请求可能会有多个
			List<RamlMethod> ramlMethodList = new Req().getParams(ramlMethod);
			// 增加response响应,响应可能有多个类型
			ramlMethodList = new Res().addResponse(ramlMethodList);
			return ramlMethodList;
		}

		// URI参数
		private List<RamlParam> _getUriParams() {
			List<RamlParam> params = Lists.newArrayList();

			Map<String, UriParameter> uriParameters = Tool.getResourceFullUriParameters(action.getResource());
			if (uriParameters == null || uriParameters.size() == 0) {
				return params;
			}

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
		private List<RamlParam> _getFileParams() {
			List<RamlParam> params = Lists.newArrayList();

			Map<String, MimeType> body = action.getBody();
			if (body == null || body.size() == 0) {
				return params;
			}

			if (!body.containsKey(MIME_TYPE_MULTIPART)) {
				return params;
			}

			Map<String, List<FormParameter>> formParameters = body.get(MIME_TYPE_MULTIPART).getFormParameters();
			for (String displayName : formParameters.keySet()) {
				List<FormParameter> list = formParameters.get(displayName);
				if (list == null || list.size() != 1) {
					throw new RuntimeException(displayName + "'s size must be 1.");
				}
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

			return params;
		}

		// 请求
		class Req {

			// 请求
			List<RamlMethod> getParams(RamlMethod ramlMethod) {

				// method
				switch (action.getType()) {
				case GET:
					// 一个
					RamlRequest getRequest = new RamlRequest();
					getRequest.setClassName(upperMethodName + "Entity");
					getRequest.setParams(this._getParamsForGetRequest());
					ramlMethod.setRequest(getRequest);
					return Lists.newArrayList(ramlMethod);
				case DELETE:
					// 一个
					RamlRequest deleteRequest = new RamlRequest();
					deleteRequest.setClassName(upperMethodName + "Entity");
					deleteRequest.setParams(this._getParamsForDeleteRequest());
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
						postRequest.setClassName(upperMethodName + "Entity");
						ramlMethod.setRequest(postRequest);
						return Lists.newArrayList(ramlMethod);
					}
					List<RamlMethod> ramlMethodList = Lists.newArrayList();
					for (String mimeType : body.keySet()) {
						if (StringUtils.equals(mimeType, MIME_TYPE_URLENCODED)) {
							RamlMethod urlencodedRamlMethod = SerializeUtils.clone(ramlMethod);
							RamlRequest request = new RamlRequest();
							request.setClassName(upperMethodName + "Entity");
							request.setParams(this._getParamsForPostOrPutUrlEncodedRequest());
							urlencodedRamlMethod.setRequest(request);
							// set consumes
							urlencodedRamlMethod.getMethodMimeType().setConsumes(MIME_TYPE_URLENCODED);
							ramlMethodList.add(urlencodedRamlMethod);
						} else if (StringUtils.equals(mimeType, MIME_TYPE_MULTIPART)) {
							RamlMethod multipartRamlMethod = SerializeUtils.clone(ramlMethod);
							RamlRequest request = new RamlRequest();
							request.setClassName(upperMethodName + "FileEntity");
							request.setParams(this._getParamsForPostOrPutMultipartRequest());
							multipartRamlMethod.setRequest(request);
							// set consumes
							multipartRamlMethod.getMethodMimeType().setConsumes(MIME_TYPE_MULTIPART);
							ramlMethodList.add(multipartRamlMethod);
						} else if (StringUtils.equals(mimeType, MIME_TYPE_JSON)) {
							RamlMethod jsonRamlMethod = SerializeUtils.clone(ramlMethod);
							RamlRequest request = new RamlRequest();
							request.setClassName(upperMethodName + "JsonEntity");
							request.setParams(this._getParamsForPostOrPutJsonRequest());
							request.setJsonParams(this._getJsonParamsForPostOrPutJsonRequest());
							jsonRamlMethod.setRequest(request);
							// set consumes
							jsonRamlMethod.getMethodMimeType().setConsumes(MIME_TYPE_JSON);
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
			private List<RamlParam> _getParamsForGetRequest() {
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
			private List<RamlParam> _getParamsForDeleteRequest() {
				List<RamlParam> ramlParms = Lists.newArrayList();

				// uri
				Map<String, UriParameter> uriParameters = action.getResource().getUriParameters();
				for (UriParameter uriParameter : uriParameters.values()) {
					ramlParms.add(_getParam(uriParameter));
				}

				return ramlParms;
			}

			// POST/PUT - application/x-www-form-urlencoded 请求的参数
			private List<RamlParam> _getParamsForPostOrPutUrlEncodedRequest() {
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
			private List<RamlParam> _getParamsForPostOrPutMultipartRequest() {
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
			private List<RamlParam> _getParamsForPostOrPutJsonRequest() {
				List<RamlParam> ramlParms = Lists.newArrayList();

				// uri
				Map<String, UriParameter> uriParameters = action.getResource().getUriParameters();
				for (UriParameter uriParameter : uriParameters.values()) {
					ramlParms.add(_getParam(uriParameter));
				}

				return ramlParms;
			}

			// POST/PUT - application/json 请求的参数
			private List<RamlJsonParam> _getJsonParamsForPostOrPutJsonRequest() {
				List<RamlJsonParam> jsonParms = Lists.newArrayList();

				Map<String, MimeType> body = action.getBody();
				if (body == null || body.size() == 0) {
					return jsonParms;
				}

				if (!body.containsKey(MIME_TYPE_JSON)) {
					throw new RuntimeException("this action can not contains " + MIME_TYPE_JSON);
				}

				String schema = body.get(MIME_TYPE_JSON).getSchema();
				String example = body.get(MIME_TYPE_JSON).getExample();
				if (StringUtils.isBlank(schema) || StringUtils.isBlank(example)) {
					throw new RuntimeException("schema and example must not be empty.");
				}
				Json json = new Json(schema, example);
				return json.getRequestParams();
			}
		}

		// 响应
		class Res {

			List<RamlMethod> addResponse(List<RamlMethod> ramlMethodList) {
				List<RamlMethod> resultList = Lists.newArrayList();
				for (RamlMethod ramlMethod : ramlMethodList) {
					resultList.addAll(this._addResponse(ramlMethod));
				}
				return resultList;
			}

			private List<RamlMethod> _addResponse(RamlMethod ramlMethod) {
				List<RamlMethod> ramlMethodList = Lists.newArrayList();

				Map<String, Response> responses = action.getResponses();
				if (responses == null || responses.size() == 0) {
					return Lists.newArrayList(ramlMethod);
				}

				for (String status : responses.keySet()) {
					if (StringUtils.equals(status, STATUS_OK)) {
						Response response = responses.get(status);
						ramlMethodList.addAll(new OK().addResponse(response, ramlMethod));
					} else {
						throw new RuntimeException("can not support status " + status);
					}
				}

				return ramlMethodList;
			}

			// OK
			class OK {

				private List<RamlMethod> addResponse(Response response, RamlMethod ramlMethod) {

					Map<String, MimeType> body = response.getBody();
					if (body == null || body.size() == 0) {
						return Lists.newArrayList(ramlMethod);
					}

					List<RamlMethod> resultList = Lists.newArrayList();
					for (String mimeType : body.keySet()) {
						if (StringUtils.equals(mimeType, MIME_TYPE_JSON)) {
							RamlMethod jsonRamlMethod = SerializeUtils.clone(ramlMethod);
							MimeType jsonMimeType = body.get(mimeType);
							_addResponseForJson(jsonMimeType, jsonRamlMethod);
							// set produces
							jsonRamlMethod.getMethodMimeType().setProduces(MIME_TYPE_JSON);
							resultList.add(jsonRamlMethod);
						} else {
							throw new RuntimeException("can not support response mimtType " + mimeType);
						}
					}
					return resultList;
				}

				// json
				private void _addResponseForJson(MimeType jsonMimeType, RamlMethod jsonRamlMethod) {

					String schema = jsonMimeType.getSchema();
					String example = jsonMimeType.getExample();
					if (StringUtils.isBlank(schema) || StringUtils.isBlank(example)) {
						throw new RuntimeException("schema must not be empty.");
					}

					String className = StringUtils.capitalize(methodName) + "ResultEntity";

					Json json = new Json(schema, example);

					List<RamlJsonParam> jsonParams = json.getResponseParams();
					RamlResponse res = new RamlResponse();
					res.setJson(json);
					res.setClassName(className);
					res.setJsonParams(jsonParams);
					jsonRamlMethod.setResponse(res);
				}

			}

		}

		// JSON
		public static class Json {

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

			private String schema;
			private String example;
			int type;
			String resultName;
			private String pageDataName;

			public int getType() {
				return type;
			}

			public void setType(int type) {
				this.type = type;
			}

			public String getResultName() {
				return resultName;
			}

			public void setResultName(String resultName) {
				this.resultName = resultName;
			}

			public static final int TYPE_NO_DATA = 2;
			public static final int TYPE_OBJECT = 3;
			public static final int TYPE_ARRAY = 4;
			public static final int TYPE_PAGE = 5;

			public Json(String schema, String example) {
				this.schema = schema;
				this.example = example;
			}

			// 请求参数
			List<RamlJsonParam> getRequestParams() {
				JSONObject jsonObject = new JSONObject(schema);
				return _getRequestObjectParams(jsonObject);
			}

			// 请求参数,添加验证注解
			List<RamlJsonParam> _getRequestObjectParams(JSONObject jsonObject) {

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
						JSONObject newJsonObject = keyObject.getJSONObject(Schema.ITEMS);
						arrayJsonParam.setChildren(_getRequestObjectParams(newJsonObject));
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
						objectJsonParam.setChildren(_getRequestObjectParams(keyObject));
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

			// 必填
			Set<String> _getRequired(JSONObject jsonObject) {
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

			// 响应内容
			List<RamlJsonParam> getResponseParams() {
				JSONObject jsonObject = new JSONObject(schema);

				if (autoRemoveResultWrap) {
					// 自动转换,去掉通用
					JSONObject obj = removeCommonField(jsonObject);
					if (obj == null) {
						return Lists.newArrayList();
					} else {
						return _getResponseObjectParams(obj);
					}
				} else {
					return _getResponseObjectParams(jsonObject);
				}

			}

			JSONObject removeCommonField(JSONObject jsonObject) {

				JSONObject properties = jsonObject.getJSONObject(Schema.PROPERTIES);
				Set<String> keys = properties.keySet();
				for (String key : keys) {
					JSONObject keyObject = properties.getJSONObject(key);
					switch (keyObject.getString(Schema.TYPE)) {
					case "object":
						JSONObject pageProperties = keyObject.getJSONObject(Schema.PROPERTIES);
						Set<String> pageKeys = pageProperties.keySet();
						Set<String> sets = Sets.newHashSet(pageTotalName, pageNumberName, pageSizeName);

						if (pageKeys.containsAll(sets)) {
							// page
							pageKeys.removeAll(sets);
							List<String> nameList = Lists.newArrayList(pageKeys);
							if (nameList.size() != 1) {
								throw new RuntimeException("we wan't page but find invalid schema " + schema);
							}
							String pageName = nameList.get(0);
							JSONObject pageDataObject = pageProperties.getJSONObject(pageName);
							if (!StringUtils.equals(pageDataObject.getString(Schema.TYPE), "array")) {
								throw new RuntimeException("we wan't array jsonObject,but find " + pageDataObject.getString(Schema.TYPE));
							}
							type = TYPE_PAGE;
							resultName = key;
							setPageDataName(pageName);
							return pageDataObject.getJSONObject(Schema.ITEMS);
						} else {
							type = TYPE_OBJECT;
							resultName = key;
							return keyObject;
						}
					case "array": // list data
						type = TYPE_ARRAY;
						resultName = key;
						return keyObject.getJSONObject(Schema.ITEMS);
					default:
						break;
					}
				}

				// return code,msg
				type = TYPE_NO_DATA;
				return null;
			}

			// 响应内容,添加数据转换
			List<RamlJsonParam> _getResponseObjectParams(JSONObject jsonObject) {

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
						JSONObject newJsonObject = keyObject.getJSONObject(Schema.ITEMS);
						arrayJsonParam.setChildren(_getRequestObjectParams(newJsonObject));
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
						objectJsonParam.setChildren(_getRequestObjectParams(keyObject));
						ramlJsonParamList.add(objectJsonParam);
						break;
					case "integer": // integer
						RamlJsonParam integerJsonParam = new RamlJsonParam();
						List<String> integerAnnos = Lists.newArrayList();
						integerJsonParam.setName(key);
						integerJsonParam.setType("Integer");
						integerJsonParam.setComment(description);
						integerJsonParam.setAnnos(integerAnnos);
						ramlJsonParamList.add(integerJsonParam);
						break;
					case "number": // double
						RamlJsonParam doubleJsonParam = new RamlJsonParam();
						List<String> doubleAnnos = Lists.newArrayList();
						doubleJsonParam.setName(key);
						doubleJsonParam.setType("Double");
						doubleJsonParam.setComment(description);
						doubleJsonParam.setAnnos(doubleAnnos);
						ramlJsonParamList.add(doubleJsonParam);
						break;
					case "string": // string
						RamlJsonParam stringJsonParam = new RamlJsonParam();
						List<String> stringAnnos = Lists.newArrayList();
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
							// TODO
							// 日期输出格式
							// String pattern;
							// if (example.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
							// pattern = "yyyy-MM-dd kk:mm:ss";
							// } else if (example.matches("\\d{4}-\\d{2}-\\d{2}")) {
							// pattern = "yyyy-MM-dd";
							// } else if (example.matches("\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
							// pattern = "yyyy/MM/dd kk:mm:ss";
							// } else if (example.matches("\\d{4}/\\d{2}/\\d{2}")) {
							// pattern = "yyyy/MM/dd";
							// } else if (example.matches("\\d{14}")) {
							// pattern = "yyyyMMddkkmmss";
							// } else if (example.matches("\\d{8}")) {
							// pattern = "yyyyMMdd";
							// } else if (example.matches("\\d{2}:\\d{2}:\\d{2}")) {
							// pattern = "kkmmss";
							// } else {
							// throw new RuntimeException("can not support patter " + example);
							// }
							// dateAnnos.add("@DateTimeFormat(pattern = \"" + pattern + "\")");
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

			public String getPageDataName() {
				return pageDataName;
			}

			public void setPageDataName(String pageDataName) {
				this.pageDataName = pageDataName;
			}

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

		// 参数
		private RamlParam _getParam(AbstractParam abstractParam) {
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
