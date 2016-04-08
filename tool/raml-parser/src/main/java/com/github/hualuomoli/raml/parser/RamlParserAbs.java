package com.github.hualuomoli.raml.parser;

import java.io.File;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.raml.model.Raml;
import org.raml.model.Resource;
import org.raml.parser.visitor.RamlDocumentBuilder;

public abstract class RamlParserAbs implements RamlParser {

	public void parse(String ramlResourceLocation, String outputPath) throws Exception {
		this.parse(new RamlDocumentBuilder().build(ramlResourceLocation), outputPath);
	}

	public void parse(Raml raml, String outputPath) throws Exception {
		// set version
		raml.setVersion(raml.getVersion().replaceAll("[Vv]", ""));
		// delete output
		FileUtils.deleteDirectory(new File(outputPath));
		// copy template
		FileUtils.copyDirectory(new File(this.getCopyTemplateFolder()), new File(outputPath));
		// crate server
		Map<String, Resource> resources = raml.getResources();
		for (Resource resource : resources.values()) {
			this.parse(raml, resource, outputPath);
		}
		// config server
		this.config(raml, outputPath);
	}

	// config
	protected abstract void config(Raml raml, String outputPath);

	// template folder
	protected abstract String getCopyTemplateFolder();

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
