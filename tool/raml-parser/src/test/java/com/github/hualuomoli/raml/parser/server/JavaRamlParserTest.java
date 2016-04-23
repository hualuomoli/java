package com.github.hualuomoli.raml.parser.server;

import org.junit.BeforeClass;
import org.junit.Test;

import com.github.hualuomoli.raml.parser.exception.ParseException;
import com.github.hualuomoli.raml.parser.java.JavaRamlParser;

public class JavaRamlParserTest {

	private static JavaRamlParser ramlParser;

	@BeforeClass
	public static void beforeClass() {
		ramlParser = new JavaRamlParser() {
			@Override
			public String getUriPrefix() {
				return "/api";
			}

			@Override
			public String getOutputFilepath() {
				return "E:/output";
			}

			@Override
			public String getFilepath() {
				return "src/main/java";
			}

		};

	}

	@Test
	public void test() throws ParseException {
		ramlParser.parse("raml/api.raml");
	}

}
