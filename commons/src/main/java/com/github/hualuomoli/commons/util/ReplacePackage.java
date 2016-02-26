package com.github.hualuomoli.commons.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.google.common.collect.Lists;

/**
 * 替换文件夹中的包名
 * @author hualuomoli
 *
 */
public class ReplacePackage {

	public static void main(String[] args) throws IOException {

		// String dir = "C:/Users/Dean/Desktop/package";
		// String src = "org.springframework";
		// String dest = "com.github.hualuomoli";

		String dir = args[0];
		String src = args[1];
		String dest = args[2];

		src = src.replaceAll("[.]", "\\.");
		replace(new File(dir), src, dest);
	}

	public static void replace(File dir, String src, String replacement) throws IOException {
		String regex = src.replaceAll("[.]", "\\.");
		List<File> files = listFiles(dir);
		for (File file : files) {
			String content = FileUtils.readFileToString(file);
			// file.delete();
			content = content.replaceAll(regex, replacement);
			FileUtils.writeStringToFile(file, content);
		}
	}

	// 查询文件夹下的所有文件
	public static List<File> listFiles(File dir) {
		List<File> list = Lists.newArrayList();
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				list.addAll(listFiles(file));
			} else {
				list.add(file);
			}
		}
		return list;
	}

}
