package com.github.hualuomoli.tool.raml;

import org.junit.Test;

public class JavaParserTest {

	@Test
	public void testExecute() {
		JavaParser parser = new JavaParser(true);
		parser.projectPackageName = "com.github.hualuomoli";
		parser.version = "1.0";
		parser.author = "hualuomoli";

		parser.execute(new String[] { //
				"raml/json.raml", //
				"raml/response.raml", //
				"raml/type.raml", //
				"raml/uri.raml", //
		});
	}

}
