package com.github.hualuomoli.commons.util;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class ReplacePackageTest {

	@Test
	public void testReplace() throws IOException {
		File dir = new File("E:/projects/git/my/web/plugin-mybatis/src/main/java");
		String src = "com.github.hualuomoli.core";
		String replacement = "com.github.hualuomoli";
		ReplacePackage.replace(dir, src, replacement);
	}

}
