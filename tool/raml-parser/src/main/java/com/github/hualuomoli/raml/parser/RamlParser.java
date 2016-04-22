package com.github.hualuomoli.raml.parser;

import org.raml.model.Raml;

import com.github.hualuomoli.raml.parser.exception.ParseException;

/**
 * raml parser
 * 
 * @author hualuomoli
 *
 */
public interface RamlParser {

	// parse
	void parse(String ramlResourceLocation) throws ParseException;

	// parse
	void parse(Raml raml) throws ParseException;
}
