package com.github.hualuomoli.raml.parser.server.node;

import org.junit.Test;

public class NodeRamlParserTest {

	@Test
	public void test() throws Exception {
		new NodeRamlParser() {
		}.parse("raml/api.raml", "F:/output/node");
	}

}
