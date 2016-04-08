package com.github.hualuomoli.raml.parser.server.java;

import org.junit.Test;

public class JavaRamlParserTest {

	@Test
	public void testParseResourceStringString() throws Exception {
		new JavaRamlParser() {
			public String getPkg() {
				return "com.github.hualuomoli.raml";
			};

			public String getAuthor() {
				return "system";
			};

			protected String getCopyTemplateFolder() {
				return "F:/github/hualuomoli/java/web-all";
			};

			public String getProjectName() {
				return "web-raml";
			};
		}.parse("raml/api.raml", "F:/output");
	}

}
