package com.github.hualuomoli.tool.raml;

import org.junit.BeforeClass;
import org.junit.Test;

import com.github.hualuomoli.commons.util.ProjectUtils;
import com.github.hualuomoli.extend.rest.AppRestResponse;
import com.github.hualuomoli.tool.raml.JavaParser.Config;

public class JavaParserOriginTest {

	private static Config config;

	@BeforeClass
	public static void beforeClass() {
		config = new JavaParser.Config();
		config.setProjectPackageName("com.github.hualuomoli.demo.raml");
		config.setResponseTrim(false);
		// config.setForece(true);

		config.setAuthor("hualuomoli");
		config.setVersion("1.0");
		config.setDate("2016-05-19 10:25:26");
		// config.setDate(new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").format(new Date()));
		config.setCodeName("code");
		config.setMsgName("msg");
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
				"demo/raml/origin.raml" //
		});
	}

}
