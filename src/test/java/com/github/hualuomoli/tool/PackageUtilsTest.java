package com.github.hualuomoli.tool;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.github.hualuomoli.constant.Charset;

public class PackageUtilsTest {

	private static void replace(String projectPath, String output, String search, String replace) throws IOException {
		File src = new File(projectPath);
		File[] files = src.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				replace(file, new File(output, file.getName()), search, replace);
			} else if (file.isDirectory()) {
				replace(file.getAbsolutePath(), output + "/" + file.getName(), search, replace);
			} else {
				throw new RuntimeException("can not support file " + file);
			}
		}
	}

	private static void replace(File src, File dest, String search, String replace) throws IOException {
		String content = FileUtils.readFileToString(src, Charset.UTF8);
		content = StringUtils.replace(content, search, replace);
		File dir = dest.getParentFile();
		if (!dir.exists()) {
			dir.mkdirs();
		}
		FileUtils.writeStringToFile(dest, content);
	}

	@Test
	public void test() throws IOException {
		String projectPath = "E:/github/hualuomoli/java-servlet3/src/main/java/com/github/hualuomoli/plugin/creator";
		String output = "E:/output/java/a";
		String search = "com.github.hualuomoli.plugin.creator";
		String replace = "com.github.hualuomoli.tool.creator";
		replace(projectPath, output, search, replace);
	}

}
