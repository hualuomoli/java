package com.github.hualuomoli.commons.util;

import java.io.File;

import org.junit.Test;

public class ProjectPackageUtilsTest {

	@Test
	public void testReplace() {
		File srcProject = new File(ProjectUtils.getProjectPath());
		String destProject = "E:/test";
		String srcPackName = "com.github.hualuomoli";
		String destPackName = "cn.hualuomoli";

		ProjectPackageUtils.replace(srcProject, destProject, srcPackName, destPackName);
	}

	@Test
	public void testReplace1() {
		File srcProject = new File(ProjectUtils.getProjectPath(), "../system");
		String destProject = ProjectUtils.getProjectPath() + "/../core-extend";
		String srcPackName = "com.github.hualuomoli.system";
		String destPackName = "com.github.hualuomoli.extend";

		ProjectPackageUtils.replace(srcProject, destProject, srcPackName, destPackName);
	}

}
