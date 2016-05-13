package com.github.hualuomoli.tool.raml;

import org.junit.BeforeClass;
import org.junit.Test;

import com.github.hualuomoli.commons.util.ProjectUtils;
import com.github.hualuomoli.mvc.rest.AppRestResponse;
import com.github.hualuomoli.tool.raml.JavaParser.Config;

public class JavaParserTest {

	private static Config config;

	@BeforeClass
	public static void beforeClass() {
		config = new JavaParser.Config();
		config.setProjectPackageName("com.github.hualuomoli.demo.raml");
		config.setAuthor("hualuomoli");
		config.setVersion("1.0");
		config.setCodeName("code");
		config.setMsgName("msg");
		config.setPageName("page");
		config.setPageTotalName("total");
		config.setPageNumberName("pageNumber");
		config.setPageSizeName("pageSize");

		config.setOutJavaPath(ProjectUtils.getProjectPath() + "/demo/main/java");
		config.setOutResourcePath(ProjectUtils.getProjectPath() + "/demo/main/resources");
		config.setRestResponse(AppRestResponse.class);
	}

	@Test
	public void test() {
		JavaParser parser = new JavaParser() {

			@Override
			public Config getConfig() {
				return config;
			}
		};

		parser.execute(new String[] {
				//
				"demo/raml/api.raml" //
		});
	}

}
