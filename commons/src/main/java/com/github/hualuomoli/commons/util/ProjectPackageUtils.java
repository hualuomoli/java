package com.github.hualuomoli.commons.util;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 项目替换
 * @author hualuomoli
 *
 */
public class ProjectPackageUtils {

	private static final Logger logger = LoggerFactory.getLogger(ProjectPackageUtils.class);

	/**
	 * 替换
	 * @param srcProject 原项目
	 * @param destProject 目标目录
	 * @param srcPackName 原包
	 * @param destPackName 目标包
	 */
	public static void replace(File srcProject, String destProject, String srcPackName, String destPackName) {

		File[] files = srcProject.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				// 文件
				try {
					String content = FileUtils.readFileToString(file);

					String[] searchContentList = new String[] { srcPackName };
					String[] replacementContentList = new String[] { destPackName };
					String replaceContent = StringUtils.replaceEach(content, searchContentList, replacementContentList);

					String[] searchPackageList = new String[] { srcPackName.replaceAll("[.]", "/") };
					String[] replacementPackageList = new String[] { destPackName.replaceAll("[.]", "/") };
					String replaceProject = StringUtils.replaceEach(destProject, searchPackageList, replacementPackageList);

					File dest = new File(replaceProject, file.getName());
					FileUtils.writeStringToFile(dest, replaceContent);
				} catch (Exception e) {
					if (logger.isErrorEnabled()) {
						logger.error("{}", e);
					}
					throw new RuntimeException(e);
				}
			} else if (file.isDirectory()) {
				replace(file, destProject + "/" + file.getName(), srcPackName, destPackName);
			} else {
				throw new RuntimeException();
			}
		}

	}

}
