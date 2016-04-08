package com.github.hualuomoli.raml.parser.mocha;

import org.junit.Test;

public class MochaRamlParserTest {

	@Test
	public void test() throws Exception {
		new MochaRamlParser() {
		}.parse("raml/api.raml", "F:/output/node");
	}

}
