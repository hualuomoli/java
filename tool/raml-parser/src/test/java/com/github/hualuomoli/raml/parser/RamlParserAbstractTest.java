package com.github.hualuomoli.raml.parser;

import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.raml.model.Action;
import org.raml.model.ActionType;
import org.raml.model.Resource;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.raml.parser.exception.ParseException;

public class RamlParserAbstractTest {

	private static RamlParser ramlParser;

	@BeforeClass
	public static void beforeClass() {
		ramlParser = new RamlParserAbstract() {

			@Override
			public String getOutputFilepath() {
				return "E:/output/raml";
			}

			@Override
			public void createFile(Map<ActionType, Action> actions, Map<String, Resource> noChildResources, String parentFullUri,
					Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException {
				// TODO Auto-generated method stub

			}

			@Override
			public void configFile(Map<ActionType, Action> actions, Map<String, Resource> noChildResources, String parentFullUri,
					Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException {
				// TODO Auto-generated method stub

			}

		};
	}

	@Test
	public void testParseStringString() throws ParseException {
		ramlParser.parse("raml/api.raml");
	}

}
