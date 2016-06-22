package com.github.hualuomoli.tool;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.github.hualuomoli.constant.Charset;

/**
 * 替换目录
 * @author hualuomoli
 *
 */
public class PackageUtils {

	// 替换项目的包名
	public static void replace(String projectPath, String output, String search, String replace) throws IOException {
		File src = new File(projectPath);
		File[] files = src.listFiles();
		// 替换输出目录
		output = StringUtils.replace(output, search.replaceAll("[.]", "/"), replace.replaceAll("[.]", "/"));
		for (File file : files) {
			if (file.isFile()) {
				_replace(file, new File(output, file.getName()), search, replace);
			} else if (file.isDirectory()) {
				replace(file.getAbsolutePath(), output + "/" + file.getName(), search, replace);
			} else {
				throw new RuntimeException("can not support file " + file);
			}
		}
	}

	// 替换文件中的内容
	private static void _replace(File src, File dest, String search, String replace) throws IOException {
		String content = FileUtils.readFileToString(src, Charset.UTF8);
		content = StringUtils.replace(content, search, replace);
		File dir = dest.getParentFile();
		if (!dir.exists()) {
			dir.mkdirs();
		}
		FileUtils.writeStringToFile(dest, content);
	}

}
