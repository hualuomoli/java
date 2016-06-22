package com.github.hualuomoli.tool;

import java.io.IOException;

import org.junit.Test;

public class PackageUtilsTest {

	@Test
	public void test() throws IOException {
		String projectPath = "E:/github/hualuomoli/java/tool/raml/parser";
		String output = "E:/output/java";
		String search = "com.github.hualuomoli";
		String replace = "cn.git.tester";
		PackageUtils.replace(projectPath, output, search, replace);
	}

}
