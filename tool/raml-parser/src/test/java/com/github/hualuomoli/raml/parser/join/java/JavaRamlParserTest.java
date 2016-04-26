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

		// ramlParser.setClearBeforeFlush(true);
		ramlParser.setOutputFilepath("E:/output");

	}

	@Test
	public void test() throws ParseException {
		ramlParser.parse("raml/api.raml");
	}

}
