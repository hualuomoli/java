package com.github.hualuomoli.raml.parser.node;

import org.junit.Test;

public class NodeRamlParserTest {

	@Test
	public void test() throws Exception {
		new NodeRamlParser() {
			public String getCopyTemplateFolder() {
				return "F:/github/hualuomoli/front/server";
			};
		}.parse("raml/api.raml", "F:/output/node");
	}

}
