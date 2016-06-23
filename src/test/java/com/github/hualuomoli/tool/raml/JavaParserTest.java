package com.github.hualuomoli.tool.raml;

import org.junit.Test;

public class JavaParserTest {

	@Test
	public void testExecute() {
		JavaParser parser = new JavaParser(true);
		parser.setProjectPackageName("com.github.hualuomoli");
		parser.setVersion("1.0");
		parser.setAuthor("hualuomoli");
		parser.execute(new String[] { //
				"raml/json.raml", //
				"raml/response.raml", //
				"raml/type.raml", //
				"raml/uri.raml", //
		});
	}

}
