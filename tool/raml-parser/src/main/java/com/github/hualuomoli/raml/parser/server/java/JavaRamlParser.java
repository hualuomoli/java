package com.github.hualuomoli.raml.parser.server.java;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.raml.model.Action;
import org.raml.model.ActionType;
import org.raml.model.MimeType;
import org.raml.model.Raml;
import org.raml.model.Resource;
import org.raml.model.Response;
import org.raml.model.parameter.FormParameter;
import org.raml.model.parameter.QueryParameter;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.raml.parser.RamlParserAbs;
import com.google.common.collect.Lists;

public class JavaRamlParser extends RamlParserAbs {

	public static final String MIME_TYPE_JSON = "application/json";
	public static final String MIME_TYPE_XML = "application/xml";
	public static final String MIME_TYPE_URLENCODED = "application/x-www-form-urlencoded";
	public static final String MIME_TYPE_MULTIPART = "multipart/form-data";

	// package
	public String getPkg() {
		return "com.github.hualuomoli";
	}

	// author
	public String getAuthor() {
		return "hualuomoli";
	}

	@Override
	protected void config() {

	}

	@Override
	protected void parse(Raml raml, Resource resource, String outputPath) throws Exception {
		StringBuilder buffer = new StringBuilder();
		String packageName = getPkg() + ".api." + this.getApiPackageName(resource.getRelativeUri());
		String className = this.getApiClassName(resource.getRelativeUri()) + "Controller";
		// add header
		buffer.append("package ").append(packageName).append(";");
		buffer.append("\n");
		// add import
		buffer.append("import javax.servlet.http.HttpServletRequest;\n");
		buffer.append("import javax.servlet.http.HttpServletResponse;\n");
		buffer.append("\n");
		buffer.append("import org.springframework.stereotype.Controller;\n");
		buffer.append("import org.springframework.ui.Model;\n");
		buffer.append("import org.springframework.web.bind.annotation.RequestMapping;\n");
		buffer.append("import org.springframework.web.bind.annotation.RequestMethod;\n");
		buffer.append("import org.springframework.web.bind.annotation.ResponseBody;\n");

		// Description
		buffer.append("\n");
		buffer.append("/**");

		buffer.append("\n").append(" * ").append(resource.getDisplayName());
		buffer.append("\n").append(" * ").append("@Description ").append(resource.getDescription());
		buffer.append("\n").append(" * ").append("@Author ").append(this.getAuthor());
		buffer.append("\n").append(" * ").append("@Date ")
				.append(new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").format(new Date()));
		buffer.append("\n").append(" * ").append("@Version ").append(raml.getVersion());

		buffer.append("\n");
		buffer.append(" */");

		// @Controller(value = "com.github.hualuomoli.api.user.UserController")
		buffer.append("\n");
		buffer.append("@Controller(value = \"");
		buffer.append(packageName).append(".").append(className);
		buffer.append("\")");
		// @RequestMapping(value = "/api/user")
		buffer.append("\n");
		buffer.append("@RequestMapping(value= \"");
		buffer.append(resource.getRelativeUri());
		buffer.append("\")");

		buffer.append("\n");
		buffer.append("public class ");
		buffer.append(className);
		buffer.append(" { ");

		// add services
		buffer.append("\n");
		buffer.append(this.getServices(resource, outputPath, false));
		// add footer
		buffer.append("\n");
		buffer.append("\n");
		buffer.append("}");

		String filename = "src/main/java/" + packageName.replaceAll("[.]", "/") + "/" + className + ".java";
		File output = new File(outputPath, filename);

		FileUtils.writeStringToFile(output, buffer.toString());

	}

	// get services
	private StringBuilder getServices(Resource resource, String outputPath, boolean addParentRelativeUri) {
		StringBuilder buffer = new StringBuilder();

		// remove parent relative uri
		// common uri in controller annotation
		if (!addParentRelativeUri) {
			resource.setRelativeUri("");
		}

		// add child resource
		Map<String, Resource> resources = resource.getResources();
		for (Resource childResource : resources.values()) {
			// uri
			childResource.setRelativeUri(resource.getRelativeUri() + childResource.getRelativeUri());
			// uri parameter
			Map<String, UriParameter> uriParameters = childResource.getUriParameters();
			uriParameters.putAll(resource.getUriParameters());
			childResource.setUriParameters(uriParameters);
			buffer.append(this.getServices(childResource, outputPath, true));
		}

		// add action
		Map<ActionType, Action> actions = resource.getActions();
		for (Action action : actions.values()) {
			buffer.append(this.getActionService(action, resource, outputPath));
		}

		return buffer;
	}

	// get action service
	private StringBuilder getActionService(Action action, Resource resource, String outputPath) {
		StringBuilder buffer = new StringBuilder();
		Map<String, UriParameter> uriParameters = resource.getUriParameters();

		buffer.append("\n");
		// notes
		buffer.append("\n");
		buffer.append("  /**");
		// description
		if (StringUtils.isNotBlank(action.getDescription())) {
			String[] descriptions = action.getDescription().replaceAll("\\*", "").split("\\n");
			for (String description : descriptions) {
				buffer.append("\n");
				buffer.append("   * ");
				buffer.append(description);
			}
		}
		// uri description
		for (UriParameter uriParameter : uriParameters.values()) {
			buffer.append("\n");
			buffer.append("   * @param ");
			buffer.append(uriParameter.getDisplayName());
			buffer.append(" ");
			buffer.append(uriParameter.getDescription());
		}
		buffer.append("\n");
		buffer.append("   */");

		// @RequestMapping(value = "/info", method = RequestMethod.GET)
		buffer.append("\n");
		buffer.append("  ").append("@RequestMapping(");
		// value
		buffer.append("value= \"").append(resource.getRelativeUri()).append("\"");
		// method
		buffer.append(", method= RequestMethod.").append(action.getType());
		buffer.append(")");

		// public String doLogin(@PathVariable(value = "userId") String userId,
		// HttpServletRequest request, HttpServletResponse response,
		// Model model) {
		// method start
		buffer.append("\n");
		buffer.append("  ").append("public").append(" ").append("String").append(" ");
		buffer.append(this.getMethodName(resource.getRelativeUri(), action.getType()));
		buffer.append("(");
		// uri parameters

		for (UriParameter uriParameter : uriParameters.values()) {
			// @PathVariable(value = "userId") String userId,
			buffer.append("@PathVariable");
			buffer.append("(");
			buffer.append("value = \"").append(uriParameter.getDisplayName()).append("\"");
			buffer.append(")");
			buffer.append(" ").append("String").append(" ").append(uriParameter.getDisplayName());
			buffer.append(", ");
		}

		buffer.append("HttpServletRequest request, ");
		buffer.append("HttpServletResponse response, ");
		buffer.append("Model model");

		buffer.append(") {");

		// log Parameter
		// logger.debug("用户名 {}", username);
		// uri
		buffer.append("\n");
		buffer.append("    // uri parameter");
		for (UriParameter uriParameter : uriParameters.values()) {
			buffer.append("\n");
			buffer.append("    ").append("logger.debug(\"");
			buffer.append(uriParameter.getDescription());
			buffer.append("[");
			buffer.append(uriParameter.getDisplayName());
			buffer.append("]");
			buffer.append(" {}\", ");
			buffer.append(uriParameter.getDisplayName());
			buffer.append(");");
		}
		// query
		buffer.append("\n");
		buffer.append("    // query parameter");
		Map<String, QueryParameter> queryParameters = action.getQueryParameters();
		for (QueryParameter queryParameter : queryParameters.values()) {
			buffer.append("\n");
			buffer.append("    ").append("logger.debug(\"");
			buffer.append(queryParameter.getDescription());
			buffer.append("[");
			buffer.append(queryParameter.getDisplayName());
			buffer.append("]");
			buffer.append(" {}\", ");
			buffer.append("request.getParameter(\"");
			buffer.append(queryParameter.getDisplayName());
			buffer.append("\")");
			buffer.append(");");
		}

		// form
		buffer.append("\n");
		buffer.append("    // form parameter");
		Map<String, MimeType> body = action.getBody();
		if (body != null) {
			for (MimeType mimeType : body.values()) {
				// application/x-www-form-urlencoded
				if (StringUtils.equals(mimeType.getType(), MIME_TYPE_URLENCODED)) {
					Map<String, List<FormParameter>> formParameters = mimeType.getFormParameters();
					for (List<FormParameter> formParameterList : formParameters.values()) {
						for (FormParameter formParameter : formParameterList) {
							buffer.append("\n");
							buffer.append("    ").append("logger.debug(\"");
							buffer.append(formParameter.getDescription());
							buffer.append("[");
							buffer.append(formParameter.getDisplayName());
							buffer.append("]");
							buffer.append(" {}\", ");
							buffer.append("request.getParameter(\"");
							buffer.append(formParameter.getDisplayName());
							buffer.append("\")");
							buffer.append(");");
						}
					}
				} else if (StringUtils.equals(mimeType.getType(), MIME_TYPE_MULTIPART)) {
					// TODO
				} else if (StringUtils.equals(mimeType.getType(), MIME_TYPE_JSON)) {
					// Map<String, Object> map = request.getParameterMap();
					// for (String key : map.keySet()) {
					// }
					buffer.append("\n");
					buffer.append("      ").append("Map<String, Object> map = request.getParameterMap();");
					buffer.append("\n");
					buffer.append("      ").append("for (String key : map.keySet()) {");

					buffer.append("\n");
					buffer.append("        ").append("logger.debug(\"json data ");
					buffer.append(" {}\", key");
					buffer.append(");");

					buffer.append("\n");
					buffer.append("      ").append("}");

				} else if (StringUtils.equals(mimeType.getType(), MIME_TYPE_XML)) {
					// Map<String, Object> map = request.getParameterMap();
					// for (String key : map.keySet()) {
					// }
					buffer.append("\n");
					buffer.append("      ").append("Map<String, Object> map = request.getParameterMap();");
					buffer.append("\n");
					buffer.append("      ").append("for (String key : map.keySet()) {");

					buffer.append("\n");
					buffer.append("        ").append("logger.debug(\"xml data ");
					buffer.append(" {}\", key");
					buffer.append(");");

					buffer.append("\n");
					buffer.append("      ").append("}");

				}
			}
		}

		// responses
		// download not set response
		Map<String, Response> responses = action.getResponses();
		OK: for (Response response : responses.values()) {
			body = response.getBody();
			if (body != null) {
				for (MimeType mimeType : body.values()) {
					if (StringUtils.equals(mimeType.getType(), MIME_TYPE_JSON)) {
						String example = mimeType.getExample();
						String[] contents = example.split("\\n");
						buffer.append("\n\n");
						buffer.append("    ").append("StringBuilder buffer = new StringBuilder();");

						for (String content : contents) {
							buffer.append("\n");
							buffer.append("    ").append("buffer.append(\"");
							buffer.append(content.replaceAll("\"", "\\\\\""));
							buffer.append("\\n\");");

							// System.out.println(content);
						}
						buffer.append("\n");
						buffer.append("    ");
						buffer.append("return buffer.toString();");
						break OK;
					}
				}
			}
		}

		// method end
		buffer.append("\n");
		buffer.append("  ").append("}");

		return buffer;
	}

	// get api package name
	protected String getApiPackageName(String uri) {
		// remove /api
		// remove /{....}
		return uri.substring(this.getApiStart().length()).replaceAll("/\\{.*\\}", "").substring(1).replaceAll("/", ".");
	}

	// get api class name
	private String getApiClassName(String uri) {
		String[] array = this.getApiPackageName(uri).split("[.]");
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			buffer.append(array[i].substring(0, 1).toUpperCase()).append(array[i].substring(1));
		}
		return buffer.toString();
	}

	// get method name
	private String getMethodName(String relaticeUri, ActionType actionType) {
		ArrayList<String> list = Lists.newArrayList();
		String temp = relaticeUri.replaceAll("/\\{.*\\}", "");
		if (StringUtils.isNotBlank(temp)) {
			list = Lists.newArrayList(temp.substring(1).split("/"));
		}
		StringBuilder buffer = new StringBuilder();

		switch (actionType) {
		case GET: // use get it
			list.add(0, "get");
			break;
		case POST: // use add it
			list.add(0, "add");
			break;
		case DELETE: // use del it
			list.add(0, "del");
			break;
		case PUT:
			list.add(0, "set");
			break;
		default:
		}
		buffer.append(list.get(0));
		for (int i = 1; i < list.size(); i++) {
			buffer.append(list.get(i).substring(0, 1).toUpperCase()).append(list.get(i).substring(1));
		}
		return buffer.toString();
	}

}
