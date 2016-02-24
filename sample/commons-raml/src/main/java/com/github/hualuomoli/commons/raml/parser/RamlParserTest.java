package com.github.hualuomoli.commons.raml.parser;

import org.junit.Test;

public class RamlParserTest {

	@Test
	public void testCreate() {
		String outPath = "E:/github/hualuomoli/java/sample/modules-base/src/main/java/";
		String packageName = "com.github.hualuomoli";

		RamlParser.create("raml/account/account.raml", packageName, "account", outPath);
		RamlParser.create("raml/custome/address/custome-address.raml", packageName, "custome.address", outPath);
		RamlParser.create("raml/custome/agency/custome-agency.raml", packageName, "custome.agency", outPath);
	}

}
