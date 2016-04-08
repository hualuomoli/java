package com.github.hualuomoli.raml.parser;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
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

		// config server
		this.config(raml, outputPath);

		// crate server
		Map<String, Resource> resources = raml.getResources();
		for (Resource resource : resources.values()) {
			this.parse(raml, resource, outputPath);
		}

	}

	// config
	public abstract void config(Raml raml, String outputPath);

	// template folder
	public abstract String getCopyTemplateFolder();

	/**
	 * parse
	 * 
	 * @param raml
	 * 
	 * @param resource
	 *            raml
	 * @param outputPath
	 *            output filepath
	 */
	public abstract void parse(Raml raml, Resource resource, String outputPath) throws Exception;

	// get raml API common start
	public String getApiStart() {
		return "/api";
	}

	// get resource file absolute path
	public String getResourceFilePath(String filename) {
		return RamlParser.class.getClassLoader().getResource(filename).getPath();
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
