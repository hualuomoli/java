package com.github.hualuomoli.tool.raml;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
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
import org.raml.model.Raml;
import org.raml.model.Resource;
import org.raml.model.Response;
import org.raml.model.parameter.AbstractParam;
import org.raml.model.parameter.FormParameter;
import org.raml.model.parameter.QueryParameter;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.commons.util.SerializeUtils;
import com.github.hualuomoli.commons.util.TemplateUtils;
import com.github.hualuomoli.tool.raml.JavaParser.Entity.Column;
import com.github.hualuomoli.tool.raml.JavaParser.Entity.Dependent;
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
public abstract class JavaParser extends AbstractParser {

	private static final String TEMPLATE_PATH = "tpl/raml";

	private Resource resource; // 资源
	private String fullUri; // URI
	private String packageName; // 包名
	private String entityName; // 实体类名称
	private String filePackageName; // 文件的包名
	private List<RamlMethod> ramlMethodList; // 方法
	private static Map<String, Entity> entityMap = Maps.newHashMap();

	// 配置信息
	public abstract Config getConfig();

	public JavaParser() {
	}

	@Override
	protected void configure(Raml[] ramls) {
		final StringComparator comparator = new StringComparator();
		for (String entityName : entityMap.keySet()) {
			Entity entity = entityMap.get(entityName);
			// sort column and dependent
			Collections.sort(entity.columnList, new Comparator<Column>() {

				@Override
				public int compare(Column o1, Column o2) {
					return comparator.compare(o1.getName(), o2.getName());
				}
			});
			Collections.sort(entity.dependentList, new Comparator<Dependent>() {

				@Override
				public int compare(Dependent o1, Dependent o2) {
					return comparator.compare(o1.getName(), o2.getName());
				}
			});
			this._createEntity(entity);
		}

	}

	class StringComparator implements Comparator<String> {

		@Override
		public int compare(String o1, String o2) {
			int length = o1.length() > o2.length() ? o2.length() : o1.length();
			for (int i = 0; i < length; i++) {
				int s = o1.charAt(i) - o2.charAt(i);
				if (s == 0) {
					continue;
				}
				return s;
			}
			return o1.length() - o2.length();
		}

	}

	// 创建entity
	private void _createEntity(Entity entity) {

		String entityPackageName = this.getConfig().projectPackageName + ".base.entity";
		String entityJavaName = entity.getName();
		Map<String, Object> map = Maps.newHashMap();
		map.put("desc", resource.getDescription());
		map.put("author", this.getConfig().author);
		map.put("version", this.getConfig().version);
		map.put("date", new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").format(new Date()));

		map.put("packageName", entityPackageName); // 包名
		map.put("javaName", entityJavaName); // 类名

		map.put("entity", entity);

		// 创建目录
		File dir = new File(this.getConfig().outJavaPath, entityPackageName.replaceAll("[.]", "/"));
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 输出
		TemplateUtils.processByResource(TEMPLATE_PATH, "entity.tpl", map, new File(dir.getAbsolutePath(), entityJavaName + ".java"));

	}

	/**
	 * 创建文件
	 * 处理当前资源的Actions
	 * 处理当前资源下非子资源的资源 child.getResource == empty
	 * @param resource 资源
	 */
	protected void create(Resource resource) {
		this.resource = resource;

		if (StringUtils.isBlank(resource.getDescription())) {
			throw new RuntimeException("please set description for " + Tool.getResourceFullUri(resource));
		}

		fullUri = Tool.getResourceFullUri(resource);
		packageName = this._getPackageName();
		entityName = this._getEntityName();

		ramlMethodList = Lists.newArrayList();

		// 当前资源下的请求
		Map<ActionType, Action> actions = resource.getActions();
		for (Action action : actions.values()) {
			JavaTool tool = new JavaTool(this, action, action.getResource().getRelativeUri());
			List<RamlMethod> list = tool.parse();
			ramlMethodList.addAll(list);
		}

		// 当前资源下没有子资源的请求
		Set<Resource> resources = Tool.getLeafResources(resource);
		for (Resource r : resources) {
			Map<ActionType, Action> as = r.getActions();
			for (Action a : as.values()) {
				JavaTool tool = new JavaTool(this, a, r.getRelativeUri());
				List<RamlMethod> list = tool.parse();
				ramlMethodList.addAll(list);
			}
		}

		// 生成
		this._create();
	}

	// 生成
	private void _create() {

		if (ramlMethodList == null || ramlMethodList.size() == 0) {
			return;
		}

		filePackageName = this.getConfig().projectPackageName + "." + packageName;

		Map<String, Object> map = Maps.newHashMap();
		// 操作信息
		map.put("desc", resource.getDescription());
		map.put("author", this.getConfig().author);
		map.put("version", this.getConfig().version);
		map.put("date", new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").format(new Date()));

		// REST风格的响应
		map.put("restResponsePackageName", this.getConfig().restResponse.getPackage().getName());
		map.put("restResponseClassName", this.getConfig().restResponse.getSimpleName());

		// 参数
		map.put("methods", ramlMethodList);
		map.put("packageName", filePackageName); // 包名
		map.put("uri", fullUri);
		map.put("controllerJavaName", entityName + "Controller"); // 类名
		map.put("serviceJavaName", entityName + "Service");
		map.put("mapperJavaName", entityName + "Mapper");

		this._createReq();
		this._createRes();
		this._createController(map);
		this._createService(map);
		this._createMapper(map);
		this._createXml(map);
	}

	private void _createReq() {

		// 创建目录
		File dir = new File(this.getConfig().outJavaPath, filePackageName.replaceAll("[.]", "/") + "/entity");
		if (!dir.exists()) {
			dir.mkdirs();
		}

		// 输出
		for (RamlMethod ramlMethod : ramlMethodList) {
			RamlRequest req = ramlMethod.getRequest();
			Map<String, Object> map = Maps.newHashMap();
			map.put("packageName", filePackageName);
			map.put("className", req.getClassName());
			map.put("params", req.getParams());
			map.put("jsonParams", req.getJsonParams());
			TemplateUtils.processByResource(TEMPLATE_PATH, "req.tpl", map, new File(dir.getAbsolutePath(), req.getClassName() + ".java"));
		}

	}

	private void _createRes() {

		// 创建目录
		File dir = new File(this.getConfig().outJavaPath, filePackageName.replaceAll("[.]", "/") + "/entity");
		if (!dir.exists()) {
			dir.mkdirs();
		}

		// 输出
		for (RamlMethod ramlMethod : ramlMethodList) {
			RamlResponse res = ramlMethod.getResponse();
			Map<String, Object> map = Maps.newHashMap();
			map.put("packageName", filePackageName);
			map.put("className", res.getClassName());
			map.put("jsonParams", res.getJsonParams());
			TemplateUtils.processByResource(TEMPLATE_PATH, "res.tpl", map, new File(dir.getAbsolutePath(), res.getClassName() + ".java"));

			for (RamlJsonParam ramlJsonParam : res.getJsonParams()) {
				if (ramlJsonParam.getChildren() != null && ramlJsonParam.getChildren().size() > 0) {
					this._createResChild(dir, ramlJsonParam);
				}
			}

		}

	}

	private void _createResChild(File dir, RamlJsonParam ramlJsonParam) {

		// 输出
		Map<String, Object> map = Maps.newHashMap();
		map.put("packageName", filePackageName);
		map.put("className", ramlJsonParam.getClassName());
		map.put("jsonParams", ramlJsonParam.getChildren());
		TemplateUtils.processByResource(TEMPLATE_PATH, "res.tpl", map, new File(dir.getAbsolutePath(), ramlJsonParam.getClassName() + ".java"));

		for (RamlJsonParam child : ramlJsonParam.getChildren()) {
			if (child.getChildren() != null && child.getChildren().size() > 0) {
				this._createResChild(dir, child);
			}
		}

	}

	private void _createController(Map<String, Object> map) {

		// 创建目录
		File dir = new File(this.getConfig().outJavaPath, filePackageName.replaceAll("[.]", "/") + "/web");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 输出
		TemplateUtils.processByResource(TEMPLATE_PATH, "controller.tpl", map, new File(dir.getAbsolutePath(), entityName + "Controller.java"));

	}

	private void _createService(Map<String, Object> map) {
		// 创建目录
		File dir = new File(this.getConfig().outJavaPath, filePackageName.replaceAll("[.]", "/") + "/service");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 输出
		TemplateUtils.processByResource(TEMPLATE_PATH, "service.tpl", map, new File(dir.getAbsolutePath(), entityName + "Service.java"));
	}

	private void _createMapper(Map<String, Object> map) {

		// 创建目录
		File dir = new File(this.getConfig().outJavaPath, filePackageName.replaceAll("[.]", "/") + "/mapper");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 输出
		TemplateUtils.processByResource(TEMPLATE_PATH, "mapper.tpl", map, new File(dir.getAbsolutePath(), entityName + "Mapper.java"));

	}

	private void _createXml(Map<String, Object> map) {

		// 创建目录
		File dir = new File(this.getConfig().outResourcePath, "/mappers/" + packageName.replaceAll("[.]", "/"));
		if (!dir.exists()) {
			dir.mkdirs();
		}

		// 输出
		TemplateUtils.processByResource(TEMPLATE_PATH, "xml.tpl", map, new File(dir.getAbsolutePath(), entityName + "Mapper.xml"));

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

	// 参数
	private static RamlParam _getParam(AbstractParam abstractParam) {
		RamlParam ramlParam = new RamlParam();
		ramlParam.setName(abstractParam.getDisplayName());

		String def = abstractParam.getDefaultValue();

		// type
		switch (abstractParam.getType()) {
		case STRING:
			ramlParam.setType("String");
			if (def != null) {
				ramlParam.setDef("\"" + def + "\"");
			}
			break;
		case INTEGER:
			BigDecimal maxinum = abstractParam.getMaximum();
			if (maxinum != null && maxinum.doubleValue() > Integer.MAX_VALUE) {
				ramlParam.setType("Long");
			} else {
				ramlParam.setType("Integer");
			}
			if (def != null) {
				ramlParam.setDef(def);
			}
			break;
		case NUMBER:
			ramlParam.setType("Double");
			if (def != null) {
				ramlParam.setDef(def + "D");
			}
			break;
		case BOOLEAN:
			throw new RuntimeException("please use string replace boolean.");
		case DATE:
			ramlParam.setType("Date");
			if (def != null) {
				ramlParam.setDef("DateUtils.parse(\"" + def + "\")");
			}
			break;
		case FILE:
			ramlParam.setType("MultipartFile");
			break;
		}
		ramlParam.setComment(StringUtils.isBlank(abstractParam.getDescription()) ? "注释" : abstractParam.getDescription());
		ramlParam.setAnnos(new Valid(abstractParam).getValid());
		return ramlParam;
	}

	// 移除array的后缀,如s, es, list
	private static String _removeArraySuffix(String key) {
		if (StringUtils.endsWithIgnoreCase(key, "s")) {
			return key.substring(0, key.length() - 1);
		} else if (StringUtils.endsWithIgnoreCase(key, "es")) {
			return key.substring(0, key.length() - 2);
		} else if (StringUtils.equalsIgnoreCase(key, "list")) {
			logger.warn("please set property name. not use list");
			return "list";
		} else if (StringUtils.endsWithIgnoreCase(key, "list")) {
			return key.substring(0, key.length() - 4);
		}
		return key;
	}

	// 工具
	public static class JavaTool {

		private JavaParser javaParser;

		private Action action; // 事件
		private String relativeUri; // 相对URI(方法上的资源地址注解)
		private String entityName; // 实体类名称
		private String methodName; // 请求方法名称

		public JavaTool(JavaParser javaParser, Action action, String relativeUri) {
			this.javaParser = javaParser;
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
				throw new RuntimeException("no response found.");
			}
			// 设置实体类名称
			_setEntityName();
			// 设置方法名称
			methodName = action.getType().toString().toLowerCase() + entityName;
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
			ramlMethod.setMethodName(methodName);
			ramlMethod.setUriParams(this._getUriParams());
			ramlMethod.setFileParams(this._getFileParams());
			// mimeType
			RamlMethodMimeType methodMimeType = new RamlMethodMimeType();
			methodMimeType.setUri(relativeUri);
			methodMimeType.setMethod(action.getType().toString());
			ramlMethod.setMethodMimeType(methodMimeType);

			// 增加request请求,对于post请求可能会有多个
			List<RamlMethod> ramlMethodList = new Req(this).getParams(ramlMethod);
			// 增加response响应,响应可能有多个类型
			ramlMethodList = new Res(this).addResponse(ramlMethodList);
			// 设置entity
			new ResEntity(ramlMethodList).add2Entity();

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
					String anno = "@RequestParam(value = \"" + formParameter.getDisplayName() + "\", required = "
							+ (formParameter.isRequired() ? "true" : "false") + ")";
					annos.add(anno);
					param.setAnnos(annos);

					params.add(param);
				}
			}

			return params;
		}

	}

	// 请求
	static class Req {

		@SuppressWarnings("unused")
		private JavaTool javaTool;
		private Action action;
		private String methodName;

		public Req(JavaTool javaTool) {
			this.javaTool = javaTool;
			this.action = javaTool.action;
			this.methodName = javaTool.methodName;

			if (logger.isDebugEnabled()) {
				logger.debug("resource {}", Tool.getResourceFullUri(action.getResource()));
				logger.debug("method {}", action.getType().toString().toLowerCase());
			}

		}

		// 请求
		List<RamlMethod> getParams(RamlMethod ramlMethod) {

			// method
			switch (action.getType()) {
			case GET:
				// 一个
				RamlRequest getRequest = new RamlRequest();
				getRequest.setClassName(StringUtils.capitalize(methodName) + "Entity");
				getRequest.setParams(this._getParamsForGetRequest());
				ramlMethod.setRequest(getRequest);
				return Lists.newArrayList(ramlMethod);
			case DELETE:
				// 一个
				RamlRequest deleteRequest = new RamlRequest();
				deleteRequest.setClassName(StringUtils.capitalize(methodName) + "Entity");
				deleteRequest.setParams(this._getParamsForDeleteRequest());
				ramlMethod.setRequest(deleteRequest);
				return Lists.newArrayList(ramlMethod);
			case PUT:
			case POST:
				// 可能有多个
				// 请求类型
				Map<String, MimeType> body = action.getBody();
				if (body == null || body.size() == 0) {
					// 没有参数
					RamlRequest postRequest = new RamlRequest();
					postRequest.setClassName(StringUtils.capitalize(methodName) + "Entity");
					postRequest.setParams(this._getUriParams());
					ramlMethod.setRequest(postRequest);
					return Lists.newArrayList(ramlMethod);
				}
				List<RamlMethod> ramlMethodList = Lists.newArrayList();
				for (String mimeType : body.keySet()) {
					if (StringUtils.equals(mimeType, MIME_TYPE_URLENCODED)) {
						RamlMethod urlencodedRamlMethod = SerializeUtils.clone(ramlMethod);
						RamlRequest request = new RamlRequest();
						request.setClassName(StringUtils.capitalize(methodName) + "UrlEncodedEntity");
						request.setParams(this._getParamsForPostOrPutUrlEncodedRequest());
						urlencodedRamlMethod.setRequest(request);
						// set consumes
						urlencodedRamlMethod.getMethodMimeType().setConsumes(MIME_TYPE_URLENCODED);
						ramlMethodList.add(urlencodedRamlMethod);
					} else if (StringUtils.equals(mimeType, MIME_TYPE_MULTIPART)) {
						RamlMethod multipartRamlMethod = SerializeUtils.clone(ramlMethod);
						RamlRequest request = new RamlRequest();
						request.setClassName(StringUtils.capitalize(methodName) + "FileEntity");
						request.setParams(this._getParamsForPostOrPutMultipartRequest());
						multipartRamlMethod.setRequest(request);
						// set consumes
						multipartRamlMethod.getMethodMimeType().setConsumes(MIME_TYPE_MULTIPART);
						ramlMethodList.add(multipartRamlMethod);
					} else if (StringUtils.equals(mimeType, MIME_TYPE_JSON)) {
						RamlMethod jsonRamlMethod = SerializeUtils.clone(ramlMethod);
						RamlRequest request = new RamlRequest();
						request.setClassName(StringUtils.capitalize(methodName) + "JsonEntity");
						request.setAnno("@RequestBody");
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

		// DELETE请求的参数
		private List<RamlParam> _getUriParams() {
			List<RamlParam> ramlParms = Lists.newArrayList();

			// uri
			Map<String, UriParameter> uriParameters = Tool.getResourceFullUriParameters(action.getResource());
			for (UriParameter uriParameter : uriParameters.values()) {
				RamlParam ramlParam = _getParam(uriParameter);
				// 去除验证
				ramlParam.getAnnos().clear();
				ramlParms.add(ramlParam);
			}

			return ramlParms;
		}

		// GET请求的参数
		private List<RamlParam> _getParamsForGetRequest() {
			List<RamlParam> ramlParms = Lists.newArrayList();

			// uri
			ramlParms.addAll(this._getUriParams());

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
			ramlParms.addAll(this._getUriParams());

			return ramlParms;
		}

		// POST/PUT - application/x-www-form-urlencoded 请求的参数
		private List<RamlParam> _getParamsForPostOrPutUrlEncodedRequest() {
			List<RamlParam> ramlParms = Lists.newArrayList();

			// uri
			ramlParms.addAll(this._getUriParams());

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
			ramlParms.addAll(this._getUriParams());

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
			ramlParms.addAll(this._getUriParams());

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

			ReqJson reqJson = new ReqJson(schema, example);
			return reqJson.getParams();
		}
	}

	// 响应
	static class Res {

		private JavaTool javaTool;
		private Action action;

		public Res(JavaTool javaTool) {
			this.javaTool = javaTool;
			this.action = this.javaTool.action;
		}

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
				throw new RuntimeException("must has response.");
			}

			for (String status : responses.keySet()) {
				switch (status) {
				case STATUS_OK:
					// 200 响应
					Response response = responses.get(status);
					ramlMethodList.addAll(new ResStatusOK(this).addResponse(response, ramlMethod));
					break;
				default:
					throw new RuntimeException("can not support status " + status);
				}
			}

			return ramlMethodList;
		}

		// OK
		static class ResStatusOK {

			private Res res;
			private String methodName;

			public ResStatusOK(Res res) {
				this.res = res;
				this.methodName = this.res.javaTool.methodName;
			}

			private List<RamlMethod> addResponse(Response response, RamlMethod ramlMethod) {

				Map<String, MimeType> body = response.getBody();
				if (body == null || body.size() == 0) {
					return Lists.newArrayList(ramlMethod);
				}

				List<RamlMethod> resultList = Lists.newArrayList();
				for (String mimeType : body.keySet()) {
					switch (mimeType) {
					case MIME_TYPE_JSON:
						// 返回数据为json
						RamlMethod jsonRamlMethod = SerializeUtils.clone(ramlMethod);
						MimeType jsonMimeType = body.get(MIME_TYPE_JSON);
						_addJsonResponse(jsonMimeType, jsonRamlMethod);
						// set produces
						jsonRamlMethod.getMethodMimeType().setProduces(MIME_TYPE_JSON);
						resultList.add(jsonRamlMethod);
						break;
					default:
						throw new RuntimeException("can not support response mimtType " + mimeType);
					}
				}
				return resultList;
			}

			// json
			private void _addJsonResponse(MimeType jsonMimeType, RamlMethod jsonRamlMethod) {

				String schema = jsonMimeType.getSchema();
				String example = jsonMimeType.getExample();
				if (StringUtils.isBlank(schema) || StringUtils.isBlank(example)) {
					throw new RuntimeException("schema must not be empty.");
				}

				String className = StringUtils.capitalize(methodName) + "Result" + "JsonEntity";
				ResJson resJson = new ResJson(res.javaTool, schema, example, className);
				//
				List<RamlJsonParam> jsonParams = resJson.getParams();

				RamlResponse res = new RamlResponse();
				res.setResJson(resJson);
				res.setClassName(resJson.getClassName());
				res.setJsonParams(jsonParams);
				jsonRamlMethod.setResponse(res);
			}

		}

	}

	// 有效性注解
	static class Valid {

		private AbstractParam abstractParam;
		private String description;
		private String displayName;
		private String message;

		public Valid(AbstractParam abstractParam) {
			this.abstractParam = abstractParam;
			this.description = this.abstractParam.getDescription();
			this.displayName = this.abstractParam.getDisplayName();
			if (StringUtils.isBlank(this.description)) {
				throw new RuntimeException("please set descripion for " + this.displayName);
			}
			this.message = this.description + " - " + this.displayName;
		}

		/**
		 * 获取校验规则
		 * @param jsonParam 参数
		 * @return 校验规则
		 */
		List<String> getValid() {
			List<String> valids = Lists.newArrayList();

			String notNull = _getNotNull();
			String notBlank = _getNotBlank();
			String length = _getLength();
			String min = _getMin();
			String max = _getMax();
			String pattern = _getStringPattern();
			String dateFormatPattern = _getDateFormatPattern();

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
		boolean _isRequired() {
			return abstractParam.isRequired();
		}

		// 不能为空
		// @NotNull(message = "")
		String _getNotNull() {
			if (!_isRequired()) {
				return null;
			}
			return "@NotNull(message = \"" + message + " 必填\")";
		}

		// 不能为空(字符串)
		// @NotBlank(message = "")
		String _getNotBlank() {
			if (abstractParam.getType() != ParamType.STRING) {
				return null;
			}
			return "@NotBlank(message = \"" + message + "不能为空\")";
		}

		// 长度限制
		// @Length(min = 1, max = 5, message = "用户名长度在1-5之间")
		String _getLength() {
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
				return "@Length(min = " + min + ", message = \"" + message + "长度不能小于" + min + "\")";
			} else if (min == 0 && max > 0) {
				// 只设置了最大长度
				// @Length(max = 5, message = "数据长度不能大于5")
				return "@Length(max = " + max + ", message = \"" + message + "长度不能大于" + max + "\")";
			} else if (min > 0 && max > 0) {
				// 设置了最小长度和最大长度
				// @Length(min = 1, max = 5, message = "用户名长度在1-5之间")
				return "@Length(min = " + min + ", max = " + max + ", message = \"" + message + "长度在" + min + "-" + max + "之间\")";
			}
			return null;
		}

		// 设置了最小值
		// @Min(value = 1, message = "")
		String _getMin() {
			if (abstractParam.getType() != ParamType.INTEGER && abstractParam.getType() != ParamType.NUMBER) {
				return null;
			}
			// @Min(value = 1, message = "")
			BigDecimal minimum = abstractParam.getMinimum();
			if (minimum == null) {
				return null;
			}
			long min = minimum.longValue();
			return "@Min(value = " + min + "L, message = \"" + message + "最小值" + min + "\")";
		}

		// 设置了最大值
		// @Max(value = 10, message = "")
		String _getMax() {
			if (abstractParam.getType() != ParamType.INTEGER && abstractParam.getType() != ParamType.NUMBER) {
				return null;
			}
			// @Max(value = 10, message = "")
			BigDecimal maxinum = abstractParam.getMaximum();
			if (maxinum == null) {
				return null;
			}
			long max = maxinum.longValue();
			return "@Max(value = " + max + "L, message = \"" + message + "最大值" + max + "\")";
		}

		// 正则表达式
		// @Pattern(regexp = "", message = "")
		String _getStringPattern() {
			if (abstractParam.getType() != ParamType.STRING) {
				return null;
			}
			String pattern = abstractParam.getPattern();
			if (StringUtils.isBlank(pattern)) { // 不需要正则表达式验证
				return null;
			}
			String regexp = pattern.replaceAll("\\\\", "\\\\\\\\");
			return "@Pattern(regexp = \"" + regexp + "\", message = \"" + message + "格式不正确,如:" + abstractParam.getExample() + "\")";
		}

		// 日期格式化
		// @DateTimeFormat(pattern = "")
		String _getDateFormatPattern() {
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

	// JSON
	public static class ReqJson {

		private String schema;
		@SuppressWarnings("unused")
		private String example;

		public ReqJson(String schema, String example) {
			this.schema = schema;
			this.example = example;
			if (StringUtils.isBlank(schema) || StringUtils.isBlank(example)) {
				throw new RuntimeException("please set example and schema.");
			}
		}

		// 请求参数
		List<RamlJsonParam> getParams() {
			JSONObject jsonObject = new JSONObject(schema);
			return _getParams(jsonObject);
		}

		// 请求参数,添加验证注解
		List<RamlJsonParam> _getParams(JSONObject jsonObject) {

			List<RamlJsonParam> ramlJsonParamList = Lists.newArrayList();

			JSONObject properties = jsonObject.getJSONObject(Schema.PROPERTIES);
			Set<String> requredSet = _getRequired(jsonObject);

			Set<String> keys = properties.keySet();
			for (String key : keys) {
				JSONObject keyObject = properties.getJSONObject(key);
				String type = keyObject.getString(Schema.TYPE);
				String description = "";
				String example = "";
				String message = "";

				if (!StringUtils.equalsIgnoreCase(type, "object") && !StringUtils.equalsIgnoreCase(type, "array")) {
					if (!keyObject.has(Schema.DESCRIPTION) || !keyObject.has(Schema.EXAMPLE)) {
						logger.warn("invalid key {}", key);
						throw new RuntimeException("please set description and example.");
					}
					description = keyObject.getString(Schema.DESCRIPTION);
					example = String.valueOf(keyObject.get(Schema.EXAMPLE));
					message = description + " - " + key; // 提示信息
				}

				switch (type) {
				case "array": // 数组
					RamlJsonParam arrayJsonParam = new RamlJsonParam();
					String name = _removeArraySuffix(key); // 移除后缀

					String arrayCalssName = StringUtils.capitalize(name);
					arrayJsonParam.setClassName(arrayCalssName);
					arrayJsonParam.setName(key);
					arrayJsonParam.setType("java.util.List<" + arrayCalssName + ">");
					arrayJsonParam.setComment(description);
					JSONObject newJsonObject = keyObject.getJSONObject(Schema.ITEMS);
					arrayJsonParam.setChildren(_getParams(newJsonObject));
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
					objectJsonParam.setChildren(_getParams(keyObject));
					ramlJsonParamList.add(objectJsonParam);
					break;
				case "integer": // integer
					RamlJsonParam integerJsonParam = new RamlJsonParam();
					List<String> integerAnnos = Lists.newArrayList();
					// @Min(value = 1, message = "")
					if (keyObject.has(Schema.MINIMUM)) {
						long minimum = Long.parseLong(keyObject.getString(Schema.MINIMUM));
						integerAnnos.add("@Min(value = " + minimum + ", message = \"" + message + "不能小于" + minimum + "\")");
					}
					// @Max(value = 1, message = "")
					if (keyObject.has(Schema.MAXIMUM)) {
						long maximum = Long.parseLong(keyObject.getString(Schema.MAXIMUM));
						integerAnnos.add("@Max(value = " + maximum + ", message = \"" + message + "不能大于" + maximum + "\")");
					}
					// 默认值
					if (keyObject.has(Schema.DEFAULT)) {
						String def = String.valueOf(keyObject.get(Schema.DEFAULT));
						integerJsonParam.setDef(def);
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
						doubleAnnos.add("@Min(value = " + minimum + "D, message = \"" + message + "不能小于" + minimum + "\")");
					}
					// @Max(value = 1, message = "")
					if (keyObject.has(Schema.MAXIMUM)) {
						double maximum = Double.parseDouble(keyObject.getString(Schema.MAXIMUM));
						doubleAnnos.add("@Max(value = " + maximum + "D, message = \"" + message + "不能大于" + maximum + "\")");
					}
					// 默认值
					if (keyObject.has(Schema.DEFAULT)) {
						String def = String.valueOf(keyObject.get(Schema.DEFAULT));
						doubleJsonParam.setDef(def + "D");
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
						stringAnnos.add("@Length(min = " + minLength + ", max = " + maxLength + ", message = \"" + message + "长度在" + minLength + " - "
								+ maxLength + "之间\")");
					} else if (minLength > 0) {
						stringAnnos.add("@Length(min = " + minLength + ", message = \"" + message + "长度不能小于" + minLength + "\")");
					} else if (maxLength > 0) {
						stringAnnos.add("@Length(max = " + minLength + ", message = \"" + message + "长度不能大于" + maxLength + "\")");
					}
					// @Pattern(regexp = "", message = "")
					if (keyObject.has(Schema.PATTERN)) {
						String pattern = keyObject.getString(Schema.PATTERN);
						stringAnnos.add(
								"@Pattern(regexp = \"" + pattern.replaceAll("\\\\", "\\\\\\\\") + "\", message = \"" + message + "格式不正确,如:" + example + "\")");
					}
					// 默认值
					if (keyObject.has(Schema.DEFAULT)) {
						String def = String.valueOf(keyObject.get(Schema.DEFAULT));
						stringJsonParam.setDef("\"" + def + "\"");
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
					// 默认值
					if (keyObject.has(Schema.DEFAULT)) {
						String def = String.valueOf(keyObject.get(Schema.DEFAULT));
						dateJsonParam.setDef("DateUtils.parse(\"" + def + "\")");
					}
					dateJsonParam.setName(key);
					dateJsonParam.setType("Date");
					dateJsonParam.setComment(description);
					dateJsonParam.setAnnos(dateAnnos);
					ramlJsonParamList.add(dateJsonParam);
					break;
				case "boolean":
					// RamlJsonParam booleanJsonParam = new RamlJsonParam();
					// List<String> booleanAnnos = Lists.newArrayList();
					//
					// booleanJsonParam.setName(key);
					// booleanJsonParam.setType("Boolean");
					// booleanJsonParam.setComment(description);
					// booleanJsonParam.setAnnos(booleanAnnos);
					// ramlJsonParamList.add(booleanJsonParam);
					// break;
					throw new RuntimeException("please use string replace boolean");
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

	}

	// JSON
	public static class ResJson {

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

		private JavaTool javaTool;
		private String schema;
		private String example;
		private Integer type; // json的类型
		private String resultName; // 返回的类名
		private String exampleData; // 例子
		private String className; // 类名
		private String prefixResponseClassName;// 响应类的前部

		public static final int TYPE_NO_DATA = 2;
		public static final int TYPE_OBJECT = 3;
		public static final int TYPE_ARRAY = 4;
		public static final int TYPE_PAGE = 5;

		public Integer getType() {
			return type;
		}

		public String getExampleData() {
			return exampleData;
		}

		public String getResultName() {
			return resultName;
		}

		public String getClassName() {
			return className;
		}

		public ResJson(JavaTool javaTool, String schema, String example, String prefixResponseClassName) {
			this.javaTool = javaTool;
			this.schema = schema;
			this.example = example;
			this.prefixResponseClassName = prefixResponseClassName;
			this.className = this.prefixResponseClassName;
			if (StringUtils.isBlank(this.schema) || StringUtils.isBlank(this.example)) {
				throw new RuntimeException("please set example and schema.");
			}
		}

		// 响应内容
		List<RamlJsonParam> getParams() {
			JSONObject jsonObject = new JSONObject(schema);

			Set<String> keys = jsonObject.getJSONObject(Schema.PROPERTIES).keySet();

			// 判断类型
			// 移除code和msg
			keys.remove(javaTool.javaParser.getConfig().codeName);
			keys.remove(javaTool.javaParser.getConfig().msgName);
			if (keys.size() == 0) {
				// 只有code和msg
				this.type = TYPE_NO_DATA; // 设置类型
				return Lists.newArrayList();
			}

			// 移除后只剩余一个属性
			if (keys.size() != 1) {
				throw new RuntimeException("can not support json " + example);
			}

			List<String> tempList = Lists.newArrayList(keys);
			resultName = tempList.get(0); // 返回的类名

			JSONObject obj = null;
			Object exampleData = null;

			// 判断是否是page
			if (keys.contains(javaTool.javaParser.getConfig().pageName)) {
				this.type = TYPE_PAGE; // 设置类型
				// page
				JSONObject pageJsonObject = jsonObject.getJSONObject(Schema.PROPERTIES).getJSONObject(resultName);
				Set<String> pageKeys = pageJsonObject.getJSONObject(Schema.PROPERTIES).keySet();

				// 移除page相关信息
				pageKeys.remove(javaTool.javaParser.getConfig().pageTotalName);
				pageKeys.remove(javaTool.javaParser.getConfig().pageNumberName);
				pageKeys.remove(javaTool.javaParser.getConfig().pageSizeName);

				// 移除后只剩余一个属性
				if (pageKeys.size() != 1) {
					throw new RuntimeException("can not support json " + example);
				}
				tempList = Lists.newArrayList(pageKeys);
				resultName = tempList.get(0); // 返回的类名
				obj = pageJsonObject.getJSONObject(Schema.PROPERTIES).getJSONObject(resultName).getJSONObject(Schema.ITEMS);
				// set example data
				exampleData = new JSONObject(example).getJSONObject(javaTool.javaParser.getConfig().pageName).getJSONArray(resultName);
				className = _removeArraySuffix(resultName); // 移除后缀
			} else {
				// 判断是Object还是Array
				JSONObject tempJsonObject = jsonObject.getJSONObject(Schema.PROPERTIES).getJSONObject(resultName);
				String type = tempJsonObject.getString(Schema.TYPE);
				switch (type) {
				case "object":
					this.type = TYPE_OBJECT; // 设置类型
					obj = tempJsonObject;
					// set example data
					exampleData = new JSONObject(example).getJSONObject(resultName);
					className = resultName;
					break;
				case "array":
					this.type = TYPE_ARRAY; // 设置类型
					obj = tempJsonObject.getJSONObject(Schema.ITEMS);
					// set example data
					exampleData = new JSONObject(example).getJSONArray(resultName);
					className = _removeArraySuffix(resultName); // 移除后缀
					break;
				default:
					throw new RuntimeException("can not support json " + example);
				}
			}

			this.exampleData = exampleData.toString().replaceAll("\\\"", "\\\\\"");
			// deal
			className = prefixResponseClassName + StringUtils.capitalize(className);
			return this._getParams(obj);

		}

		// 响应内容,添加数据转换
		List<RamlJsonParam> _getParams(JSONObject jsonObject) {

			List<RamlJsonParam> ramlJsonParamList = Lists.newArrayList();

			JSONObject properties = jsonObject.getJSONObject(Schema.PROPERTIES);
			// Set<String> requredSet = _getRequired(jsonObject);

			Set<String> keys = properties.keySet();
			for (String key : keys) {
				JSONObject keyObject = properties.getJSONObject(key);
				String type = keyObject.getString(Schema.TYPE);

				String description = "";
				String example = "";

				if (!StringUtils.equalsIgnoreCase(type, "object") && !StringUtils.equalsIgnoreCase(type, "array")) {
					if (!keyObject.has(Schema.DESCRIPTION) || !keyObject.has(Schema.EXAMPLE)) {
						logger.warn("invalid key {}", key);
						throw new RuntimeException("please set description and example.");
					}
					description = keyObject.getString(Schema.DESCRIPTION);
					example = String.valueOf(keyObject.get(Schema.EXAMPLE));
				}

				switch (type) {
				case "array": // 数组
					RamlJsonParam arrayJsonParam = new RamlJsonParam();
					String name = _removeArraySuffix(key); // 移除后缀

					String arrayCalssName = prefixResponseClassName + StringUtils.capitalize(name);
					arrayJsonParam.setClassName(arrayCalssName);
					arrayJsonParam.setName(key);
					arrayJsonParam.setType("java.util.List<" + arrayCalssName + ">");
					// arrayJsonParam.setComment(description);
					JSONObject newJsonObject = keyObject.getJSONObject(Schema.ITEMS);
					arrayJsonParam.setChildren(_getParams(newJsonObject));
					ramlJsonParamList.add(arrayJsonParam);

					break;
				case "object": // Object
					RamlJsonParam objectJsonParam = new RamlJsonParam();

					String objectCalssName = prefixResponseClassName + StringUtils.capitalize(key);
					objectJsonParam.setClassName(objectCalssName);
					objectJsonParam.setName(key);
					objectJsonParam.setType(objectCalssName);
					// objectJsonParam.setComment(description);
					objectJsonParam.setChildren(_getParams(keyObject));
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

					dateJsonParam.setName(key);
					dateJsonParam.setType("Date");
					dateJsonParam.setComment(description);
					dateJsonParam.setAnnos(dateAnnos);
					ramlJsonParamList.add(dateJsonParam);
					break;
				case "boolean":
					// RamlJsonParam booleanJsonParam = new RamlJsonParam();
					// List<String> booleanAnnos = Lists.newArrayList();
					// booleanJsonParam.setName(key);
					// booleanJsonParam.setType("Boolean");
					// booleanJsonParam.setComment(description);
					// booleanJsonParam.setAnnos(booleanAnnos);
					// ramlJsonParamList.add(booleanJsonParam);
					// break;
					throw new RuntimeException("please use string replace boolean");
				default:
					throw new RuntimeException("can not support type " + type);
				}
				// end switch
			}
			return ramlJsonParamList;
		}

		// // 必填
		// Set<String> _getRequired(JSONObject jsonObject) {
		// Set<String> sets = Sets.newHashSet();
		// if (!jsonObject.has(Schema.REQUIRED)) {
		// return sets;
		// }
		// JSONArray required = jsonObject.getJSONArray(Schema.REQUIRED);
		// for (Object object : required) {
		// sets.add(object.toString());
		// }
		// return sets;
		// }

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

	public static class ResEntity {

		private List<RamlMethod> ramlMethodList;

		public ResEntity(List<RamlMethod> ramlMethodList) {
			this.ramlMethodList = ramlMethodList;
		}

		// 设置实体类
		void add2Entity() {
			for (RamlMethod ramlMethod : ramlMethodList) {
				RamlResponse res = ramlMethod.getResponse();
				if (res == null) {
					continue;
				}
				ResJson resJson = res.getResJson();
				List<RamlJsonParam> jsonParms = res.getJsonParams();
				String prefixResponseClassName = resJson.prefixResponseClassName;
				String entityName = resJson.className.substring(prefixResponseClassName.length());

				this._add(jsonParms, entityName, prefixResponseClassName);

			}
		}

		private void _add(List<RamlJsonParam> jsonParms, String entityName, String prefixResponseClassName) {
			if (StringUtils.isBlank(entityName)) {
				return;
			}
			Entity entity = entityMap.get(entityName);
			if (entity == null) {
				entity = new Entity();
				entity.name = entityName;
				entityMap.put(entityName, entity);
			}
			for (RamlJsonParam ramlJsonParam : jsonParms) {
				if (ramlJsonParam.getChildren() == null || ramlJsonParam.getChildren().size() == 0) {
					// column
					Entity.Column column = new Entity.Column();
					column.name = ramlJsonParam.getName();
					column.type = ramlJsonParam.getType();
					column.comment = ramlJsonParam.getComment();
					if (!entity.columnList.contains(column)) {
						entity.columnList.add(column);
					}
				} else {
					// dependent
					entityName = ramlJsonParam.getClassName().substring(prefixResponseClassName.length());

					Entity.Dependent dependent = new Entity.Dependent();
					dependent.type = entityName;
					dependent.name = ramlJsonParam.getName();
					dependent.comment = ramlJsonParam.getComment();
					if (ramlJsonParam.getClassName().startsWith("java.util.List<")) {
						// list
						dependent.relation = Entity.Dependent.RELATION_ARRAY;
					} else {
						// object
						dependent.relation = Entity.Dependent.RELATION_OBJECT;
					}
					if (!entity.dependentList.contains(dependent)) {
						entity.dependentList.add(dependent);
					}
					this._add(ramlJsonParam.getChildren(), entityName, prefixResponseClassName);
				}
			}
		}

	}

	public static class Entity {

		private String name; // 名称
		private List<Column> columnList = Lists.newArrayList();
		private List<Dependent> dependentList = Lists.newArrayList();

		public Entity() {
		}

		public String getName() {
			return name;
		}

		public List<Column> getColumnList() {
			return columnList;
		}

		public List<Dependent> getDependentList() {
			return dependentList;
		}

		// 列
		public static class Column {
			private String name;
			private String type;
			private String comment;

			public Column() {
			}

			public String getName() {
				return name;
			}

			public String getType() {
				return type;
			}

			public String getComment() {
				return comment;
			}

			@Override
			public boolean equals(Object obj) {
				if (obj == null) {
					return false;
				}
				if (obj.getClass() != this.getClass()) {
					return false;
				}
				Column column = (Column) obj;
				return StringUtils.equals(column.getName(), this.getName());
			}

		}

		public static class Dependent {
			private String name;
			private String type;
			private String comment;
			private int relation;

			public static final int RELATION_OBJECT = 1;
			public static final int RELATION_ARRAY = 2;

			public Dependent() {
			}

			public String getName() {
				return name;
			}

			public String getType() {
				return type;
			}

			public String getComment() {
				return comment;
			}

			public int getRelation() {
				return relation;
			}

			@Override
			public boolean equals(Object obj) {
				if (obj == null) {
					return false;
				}
				if (obj.getClass() != this.getClass()) {
					return false;
				}
				Dependent dependent = (Dependent) obj;
				return StringUtils.equals(dependent.getName(), this.getName());
			}

		}

	}

	public static class Config {
		private String projectPackageName; // 项目包名
		private String author; // 作者
		private String version; // 版本

		private String codeName; // 返回编码的名称
		private String msgName; // 返回错误信息的名称
		private String pageName; // 返回分页信息的名称
		private String pageTotalName; // 返回分页总数的名称
		private String pageNumberName; // 返回当前页码的名称
		private String pageSizeName; // 返回每页数据个数的名称

		private String outJavaPath; // 输出java路径
		private String outResourcePath; // 输出资源路径

		private Class<?> restResponse; // rest风格的响应类

		public void setProjectPackageName(String projectPackageName) {
			this.projectPackageName = projectPackageName;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public void setCodeName(String codeName) {
			this.codeName = codeName;
		}

		public void setMsgName(String msgName) {
			this.msgName = msgName;
		}

		public void setPageName(String pageName) {
			this.pageName = pageName;
		}

		public void setPageTotalName(String pageTotalName) {
			this.pageTotalName = pageTotalName;
		}

		public void setPageNumberName(String pageNumberName) {
			this.pageNumberName = pageNumberName;
		}

		public void setPageSizeName(String pageSizeName) {
			this.pageSizeName = pageSizeName;
		}

		public void setOutJavaPath(String outJavaPath) {
			this.outJavaPath = outJavaPath;
		}

		public void setOutResourcePath(String outResourcePath) {
			this.outResourcePath = outResourcePath;
		}

		public void setRestResponse(Class<?> restResponse) {
			this.restResponse = restResponse;
		}

	}

}
