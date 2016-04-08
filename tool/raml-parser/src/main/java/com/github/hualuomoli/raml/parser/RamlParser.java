package com.github.hualuomoli.raml.parser;

import org.raml.model.Raml;

/**
 * raml parser
 * 
 * @author hualuomoli
 *
 */
public interface RamlParser {

	// parse
	void parse(String ramlResourceLocation, String outputPath) throws Exception;

	// parse
	void parse(Raml raml, String outputPath) throws Exception;
}
