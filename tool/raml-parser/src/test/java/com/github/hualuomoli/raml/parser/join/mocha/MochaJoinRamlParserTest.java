package com.github.hualuomoli.raml.parser.join.mocha;

import org.junit.BeforeClass;
import org.junit.Test;

import com.github.hualuomoli.raml.parser.exception.ParseException;

public class MochaJoinRamlParserTest {

	private static MochaJoinRamlParser ramlParser;

	@BeforeClass
	public static void beforeClass() {
		ramlParser = new MochaJoinRamlParser() {
		};

		ramlParser.setOutputFilepath("E:/output/mocha");

	}

	@Test
	public void test() throws ParseException {
		// ramlParser.init(true);
		ramlParser.parse("raml/uri.raml");
		ramlParser.parse("raml/type.raml");
	}
}
