package com.github.hualuomoli.raml.parser.server;

import java.io.File;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.raml.model.Action;
import org.raml.model.ActionType;
import org.raml.model.Raml;
import org.raml.model.Resource;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.raml.parser.RamlParserAbs;

/**
 * server reml parser
 * 
 * @author hualuomoli
 *
 */
public abstract class RamlParserServer extends RamlParserAbs {

	public static final String MIME_TYPE_JSON = "application/json";
	public static final String MIME_TYPE_XML = "application/xml";
	public static final String MIME_TYPE_URLENCODED = "application/x-www-form-urlencoded";
	public static final String MIME_TYPE_MULTIPART = "multipart/form-data";

	@Override
	public void parse(Raml raml, Resource resource, String outputPath) throws Exception {
		StringBuilder buffer = new StringBuilder();
		String relativeFilePath = this.getRelativeFilepath(resource);
		// get header
		buffer.append(this.getHeader(raml, resource));
		// get services
		buffer.append(this.getServices(resource, outputPath, false));
		// get footer
		buffer.append(this.getFooter());
		// flush
		FileUtils.writeStringToFile(new File(outputPath, relativeFilePath), buffer.toString());

	}

	public abstract Object getHeader(Raml raml, Resource resource);

	public abstract Object getFooter();

	public abstract String getRelativeFilepath(Resource resource);

	// get service
	public abstract StringBuilder getService(Action action, Resource resource, String outputPath);

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
			buffer.append(this.getService(action, resource, outputPath));
		}

		return buffer;
	}

	// get api package name
	public String getApiPackageName(String uri) {
		// remove /api
		// remove /{....}
		return uri.substring(this.getApiStart().length()).replaceAll("/\\{.*\\}", "").substring(1).replaceAll("/", ".");
	}

	// trime quotes(")
	public String trimQuotes(String description) {
		if (StringUtils.isBlank(description)) {
			return StringUtils.EMPTY;
		}
		return description.replaceAll("\"", "\\\\\"");
	}

}
