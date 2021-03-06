package com.github.hualuomoli.raml.join.adaptor;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.raml.model.Raml;

import com.github.hualuomoli.raml.join.JoinFileDealer;
import com.github.hualuomoli.raml.join.JoinParser;
import com.github.hualuomoli.raml.join.adaptor.java.JavaDeleteActionAdaptor;
import com.github.hualuomoli.raml.join.adaptor.java.JavaFileActionAdaptor;
import com.github.hualuomoli.raml.join.adaptor.java.JavaGetActionAdaptor;
import com.github.hualuomoli.raml.join.adaptor.java.JavaPostJsonActionAdaptor;
import com.github.hualuomoli.raml.join.adaptor.java.JavaPostUrlEncodedActionAdaptor;
import com.github.hualuomoli.raml.join.dealer.JavaJoinFileDealer;
import com.github.hualuomoli.raml.join.dealer.JavaJoinFileDealer.JavaConfig;
import com.google.common.collect.Lists;

public class JavaActionAdaptorTest {

	private JoinParser parser;
	private JoinFileDealer dealer;

	@Before
	public void before() {

		JavaActionAdaptor javaGetActionAdaptor = new JavaGetActionAdaptor();
		JavaActionAdaptor javaDeleteActionAdaptor = new JavaDeleteActionAdaptor();
		JavaActionAdaptor javaPostUrlEncodedActionAdaptor = new JavaPostUrlEncodedActionAdaptor();
		JavaActionAdaptor javaPostJsonActionAdaptor = new JavaPostJsonActionAdaptor();
		JavaActionAdaptor javaFileActionAdaptor = new JavaFileActionAdaptor();

		dealer = new JavaJoinFileDealer() {
			@Override
			public void configure(Raml[] ramls) {

				try {

					String path = this.getClass().getClassLoader().getResource(".").getPath();
					String webProjectFilepath = path.substring(0, path.indexOf("/target")) + "/src/test/resources/demo/web";

					// filename folder
					String folder;
					String filename;

					// pom.xml
					filename = "pom.xml";
					String pomData = FileUtils.readFileToString(new File(webProjectFilepath, filename), "UTF-8");
					// update <artifactId>web</artifactId>
					pomData = StringUtils.replace(pomData, "<artifactId>web</artifactId>", "<artifactId>" + javaConfig.getProjectName() + "</artifactId>");
					// update <warName>web</warName>
					pomData = StringUtils.replace(pomData, "<warName>web</warName>", "<warName>" + javaConfig.getProjectName() + "</warName>");
					// flush
					FileUtils.write(new File(javaConfig.getOutputFilepath(), filename), pomData, "UTF-8");

					// .gitignore
					filename = ".gitignore";
					FileUtils.copyFile(new File(webProjectFilepath, filename), new File(javaConfig.getOutputFilepath(), filename));

					// src
					folder = "src";
					FileUtils.copyDirectory(new File(webProjectFilepath, folder), new File(javaConfig.getOutputFilepath(), folder));

				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		};

		parser = new JoinParser();

		List<ActionAdaptor> adaptors = Lists.newArrayList(new ActionAdaptor[] { //
				//
				javaGetActionAdaptor, //
				javaDeleteActionAdaptor, //
				javaPostUrlEncodedActionAdaptor, //
				javaPostJsonActionAdaptor, //
				javaFileActionAdaptor, //
		});
		parser.setAdaptors(adaptors);
		parser.setDealer(dealer);

	}

	@Test
	public void test() {
		JavaConfig config = new JavaConfig();
		config.setEncoding(Charset.forName("utf-8"));
		config.setOutputFilepath("E:/output/java");
		// config.setClear(true);
		//
		config.setAuthor("hualuomoli");
		config.setVersion(1.0);
		config.setProjectName("web-raml-demo");
		config.setRootPackageName("com.github.hualuomoli.demo");

		parser.init(config);
		parser.execute(new String[] {
				//
				"raml/uri.raml", //
				"raml/type.raml", //
				"raml/response.raml", //
				"raml/json.raml" //
		});

	}

}
