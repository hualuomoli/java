package com.github.hualuomoli.raml.parser;

import java.util.List;
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
			public String getUriPrefix() {
				return "/raml/api";
			}

			@Override
			public String getOutputFilepath() {
				return "E:/output/raml";
			}

			@Override
			public void config(Map<ActionType, Action> actions, Map<String, Resource> noChildResources, String parentRelativeUri,
					Map<String, UriParameter> parentUriParameters, Resource resource) throws ParseException {
			}

			@Override
			public void createFile(List<String> actionDatas, Resource resource) {
			}

			@Override
			public String getData(Action action, String relativeUri, Map<String, UriParameter> uriParameters, Resource resource) {
				logger.debug("\t{}", relativeUri);
				return null;
			}

		};
	}

	@Test
	public void testParseStringString() throws ParseException {
		ramlParser.parse("raml/api.raml");
	}

}
