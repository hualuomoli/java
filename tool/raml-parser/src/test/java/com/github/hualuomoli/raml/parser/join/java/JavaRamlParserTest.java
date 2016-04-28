package com.github.hualuomoli.raml.parser.join.java;

import org.junit.BeforeClass;
import org.junit.Test;

import com.github.hualuomoli.raml.parser.exception.ParseException;
import com.github.hualuomoli.raml.parser.join.java.JavaJoinRamlParser;

public class JavaRamlParserTest {

	private static JavaJoinRamlParser ramlParser;

	@BeforeClass
	public static void beforeClass() {
		ramlParser = new JavaJoinRamlParser() {
		};

		ramlParser.setOutputFilepath("E:/output/web");

	}

	@Test
	public void test() throws ParseException {
		// ramlParser.init(true);
		ramlParser.parse("raml/uri.raml");
		ramlParser.parse("raml/type.raml");
		ramlParser.parse("raml/response.raml");
		ramlParser.parse("raml/json.raml");
	}

}
