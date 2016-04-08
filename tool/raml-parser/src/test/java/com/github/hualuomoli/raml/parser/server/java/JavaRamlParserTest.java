package com.github.hualuomoli.raml.parser.server.java;

import org.junit.Test;

public class JavaRamlParserTest {

	@Test
	public void testParseResourceStringString() throws Exception {
		new JavaRamlParser() {
			public String getPkg() {
				return "org.raml.hualuomoli.parser";
			};

			public String getAuthor() {
				return "system";
			};

			public String getCopyTemplateFolder() {
				return "F:/github/hualuomoli/java/web-all";
			};

			public String getProjectName() {
				return "web-raml";
			};
		}.parse("raml/api.raml", "F:/output/java");
	}

}
