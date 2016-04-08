package com.github.hualuomoli.raml.parser;

import java.util.Map;

import org.raml.model.Raml;
import org.raml.model.Resource;
import org.raml.parser.visitor.RamlDocumentBuilder;

public abstract class RamlParserAbs implements RamlParser {

	public void parse(String ramlResourceLocation, String outputPath) throws Exception {
		this.parse(new RamlDocumentBuilder().build(ramlResourceLocation), outputPath);
	}

	public void parse(Raml raml, String outputPath) throws Exception {
		Map<String, Resource> resources = raml.getResources();
		for (Resource resource : resources.values()) {
			this.parse(raml, resource, outputPath);
		}
		this.config();
	}

	// config
	protected abstract void config();

	/**
	 * parse
	 * 
	 * @param verion
	 * 
	 * @param resource
	 *            raml
	 * @param outputPath
	 *            output filepath
	 */
	protected abstract void parse(Raml verion, Resource resource, String outputPath) throws Exception;

	// get raml API common start
	protected String getApiStart() {
		return "/api";
	}

	// get resource file absolute path
	public static String getResourceFilePath(String filename) {
		return RamlParser.class.getClassLoader().getResource(filename).getPath();
	}

}
