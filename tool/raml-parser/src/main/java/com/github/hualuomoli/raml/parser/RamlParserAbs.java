package com.github.hualuomoli.raml.parser;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.raml.model.Action;
import org.raml.model.ActionType;
import org.raml.model.Raml;
import org.raml.model.Resource;
import org.raml.model.parameter.UriParameter;
import org.raml.parser.visitor.RamlDocumentBuilder;

public abstract class RamlParserAbs implements RamlParser {

	public static final String MIME_TYPE_JSON = "application/json";
	public static final String MIME_TYPE_XML = "application/xml";
	public static final String MIME_TYPE_URLENCODED = "application/x-www-form-urlencoded";
	public static final String MIME_TYPE_MULTIPART = "multipart/form-data";

	protected String relativeUri = "";

	public void parse(String ramlResourceLocation, String outputPath) throws Exception {
		this.parse(new RamlDocumentBuilder().build(ramlResourceLocation), outputPath);
	}

	public void parse(Raml raml, String outputPath) throws Exception {
		// set version
		raml.setVersion(raml.getVersion().replaceAll("[Vv]", ""));
		// delete output
		if (this.deleteBeforeCreate()) {
			FileUtils.deleteDirectory(new File(outputPath));
		}
		// copy template
		FileUtils.copyDirectory(new File(this.getCopyTemplateFolder()), new File(outputPath));

		// config server
		this.config(raml, outputPath);

		// crate server
		Map<String, Resource> resources = raml.getResources();
		for (Resource resource : resources.values()) {
			this.parse(raml, resource, outputPath);
		}

	}

	public boolean deleteBeforeCreate() {
		return false;
	}

	// config
	public abstract void config(Raml raml, String outputPath);

	// template folder
	public abstract String getCopyTemplateFolder();

	public void parse(Raml raml, Resource resource, String outputPath) throws Exception {
		StringBuilder buffer = new StringBuilder();
		String relativeFilePath = this.getRelativeFilepath(resource);
		// get header
		buffer.append(this.getHeader(raml, resource));
		// get services
		buffer.append(this.getDatas(resource, outputPath, false));
		// get footer
		buffer.append(this.getFooter());
		// flush
		FileUtils.writeStringToFile(new File(outputPath, relativeFilePath), buffer.toString());
	}

	// get resource header
	public abstract StringBuilder getHeader(Raml raml, Resource resource);

	// get resource footer
	public abstract StringBuilder getFooter();

	// get resource datas
	public StringBuilder getDatas(Resource resource, String outputPath, boolean addParentRelativeUri) {
		StringBuilder buffer = new StringBuilder();

		// remove parent relative uri
		// common uri in controller annotation
		if (!addParentRelativeUri) {
			relativeUri = resource.getRelativeUri();
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
			buffer.append(this.getDatas(childResource, outputPath, true));
		}

		// add action
		Map<ActionType, Action> actions = resource.getActions();
		for (Action action : actions.values()) {
			buffer.append(this.getData(action, resource, outputPath));
		}

		return buffer;
	}

	// get Data
	public abstract StringBuilder getData(Action action, Resource resource, String outputPath);

	// get flush relative file path
	public abstract String getRelativeFilepath(Resource resource);

	// get raml API common start
	public String getApiStart() {
		return "/api";
	}

	// get resource file absolute path
	public String getResourceFilePath(String filename) {
		return RamlParser.class.getClassLoader().getResource(filename).getPath();
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

	// replace content
	public void replaceContent(String outputPath, String filename, List<String> searchList,
			List<String> replacementList) {
		if (searchList == null || replacementList == null || searchList.size() != replacementList.size()) {
			return;
		}
		try {
			File file = new File(outputPath, filename);
			String content = FileUtils.readFileToString(file, "UTF-8");
			content = StringUtils.replaceEach(content, searchList.toArray(new String[] {}),
					replacementList.toArray(new String[] {}));
			FileUtils.writeStringToFile(file, content, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
