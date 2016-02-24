package com.github.hualuomoli.commons.raml.parser;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.raml.model.Action;
import org.raml.model.MimeType;
import org.raml.model.Raml;
import org.raml.model.Resource;
import org.raml.model.Response;
import org.raml.parser.visitor.RamlDocumentBuilder;

import com.github.hualuomoli.commons.raml.entity.Api;
import com.github.hualuomoli.commons.raml.entity.ApiAction;
import com.github.hualuomoli.commons.raml.entity.ApiActionType;
import com.github.hualuomoli.commons.raml.entity.ApiResource;
import com.github.hualuomoli.commons.raml.util.TemplateUtils;
import com.google.common.collect.Lists;

/**
 * RAML解析器
 * 
 * @author baoquan
 *
 */
public final class RamlParser {

	public static List<Resource> parser(String resourceFileName) {
		return parser(build(resourceFileName));
	}

	public static List<Resource> parser(Raml raml) {
		return parser(raml.getResources());
	}

	public static List<Resource> parser(Map<String, Resource> resources) {
		List<Resource> ret = Lists.newArrayList();
		for (Resource resource : resources.values()) {
			ret.addAll(parser(resource));
		}
		return ret;
	}

	public static List<Resource> parser(Resource resource) {
		List<Resource> ret = Lists.newArrayList();
		Map<String, Resource> resources = resource.getResources();
		if (resources == null || resources.size() == 0) {
			ret.add(resource);
		} else {
			ret.addAll(parser(resources));
		}
		return ret;
	}

	public static Raml build(String resourceFileName) {
		return new RamlDocumentBuilder().build(resourceFileName);
	}

	public static Raml build(InputStream content, String resourceLocation) {
		return new RamlDocumentBuilder().build(content, resourceLocation);
	}

	public static void create(String resourceFileName, String packageName, String moduleName, String outPath) {

		String className = "";
		String modulePath = "";
		if (moduleName.indexOf(".") >= 0) {
			String[] array = moduleName.split("[.]");
			for (String key : array) {
				if (className.length() == 0) {
					className = key.substring(0, 1).toUpperCase() + key.substring(1);
					modulePath = key;
				} else {
					className = className + key.substring(0, 1).toUpperCase() + key.substring(1);
					modulePath = modulePath + "/" + key;
				}
			}
		} else {
			className = moduleName.substring(0, 1).toUpperCase() + moduleName.substring(1);
			modulePath = moduleName;
		}

		try {
			String tplPath = RamlParser.class.getClassLoader().getResource("tpls/api").getPath();
			String data = TemplateUtils.getTemplateData(tplPath, "controller.tpl",
					parser2Api(resourceFileName, packageName, moduleName, className, modulePath));
			outPath = outPath.replaceAll("\\\\", "/");
			if (!outPath.endsWith("/")) {
				outPath = outPath + "/";
			}
			String[] array = packageName.split("[.]");
			for (String key : array) {
				outPath = outPath + key + "/";
			}
			outPath = outPath + "api/";
			array = moduleName.split("[.]");
			for (String key : array) {
				outPath = outPath + key + "/";
			}
			outPath = outPath + "web/";

			FileUtils.writeStringToFile(new File(outPath, className + "Controller.java"), data, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Api parser2Api(String resourceFileName, String packageName, String moduleName, String className,
			String modulePath) {

		List<Resource> resources = RamlParser.parser(resourceFileName);

		Api api = new Api();
		api.setPackageName(packageName);
		api.setModuleName(moduleName);
		api.setClassName(className);
		api.setModulePath(modulePath);
		List<ApiResource> resourceList = Lists.newArrayList();
		api.setResourceList(resourceList);

		for (Resource resource : resources) {

			String uri = resource.getUri();
			uri = uri.substring(uri.indexOf("/" + modulePath) + ("/" + modulePath).length());

			String[] array = uri.split("/");
			String methodUriPath = "";
			String methodName = "";
			for (String key : array) {
				if (!key.startsWith("{")) {
					if (methodUriPath.length() == 0) {
						methodUriPath = key;
						methodName = key;
					} else {
						methodUriPath = methodUriPath + "/" + key;
						methodName = methodName + key.substring(0, 1).toUpperCase() + key.substring(1);
					}
				}
			}

			ApiResource apiResource = new ApiResource();

			apiResource.setUri(uri);
			apiResource.setMethodUriPath(methodUriPath);
			apiResource.setMethodName(methodName);

			List<ApiAction> actionList = Lists.newArrayList();
			Collection<Action> actions = resource.getActions().values();
			for (Action action : actions) {
				ApiAction apiAction = new ApiAction();
				switch (action.getType()) {
				case GET:
					apiAction.setType(ApiActionType.GET);
				case POST:
					apiAction.setType(ApiActionType.POST);
					break;
				case DELETE:
					apiAction.setType(ApiActionType.DELETE);
					break;
				case PUT:
					apiAction.setType(ApiActionType.PUT);
					break;
				default:
					break;
				}

				// 只获取第一个响应
				String example = "";
				try {
					List<Response> responses = Lists.newArrayList(action.getResponses().values());
					ArrayList<MimeType> mimeTypes = Lists.newArrayList(responses.get(0).getBody().values());
					example = mimeTypes.get(0).getExample();
					// Pattern p = Pattern.compile("\\s*|\t|\r|\n");
					// Matcher m = p.matcher(example);
					// example = m.replaceAll("");
					example = trimQuotes(trimBlank(example));
				} catch (Exception e) {
				}
				apiAction.setExample(example);

				actionList.add(apiAction);

			}

			apiResource.setActionList(actionList);

			resourceList.add(apiResource);

		}

		return api;

	}

	public static String trimBlank(String str) {
		if (StringUtils.isEmpty(str)) {
			return StringUtils.EMPTY;
		}
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(str);
		return m.replaceAll("");
	}

	public static String trimQuotes(String str) {
		if (StringUtils.isEmpty(str)) {
			return StringUtils.EMPTY;
		}
		return str.replaceAll("\"", "\\\\\"");

	}

}
