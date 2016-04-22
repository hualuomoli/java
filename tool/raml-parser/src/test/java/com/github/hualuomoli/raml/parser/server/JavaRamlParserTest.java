package com.github.hualuomoli.raml.parser.server;

import org.junit.BeforeClass;
import org.junit.Test;

import com.github.hualuomoli.raml.parser.RamlParser;
import com.github.hualuomoli.raml.parser.exception.ParseException;

public class JavaRamlParserTest {

	private static RamlParser ramlParser;

	@BeforeClass
	public static void beforeClass() {
		ramlParser = new JavaRamlParser() {
		};
	}

	@Test
	public void test() throws ParseException {
		ramlParser.parse("raml/api.raml");
	}

}
