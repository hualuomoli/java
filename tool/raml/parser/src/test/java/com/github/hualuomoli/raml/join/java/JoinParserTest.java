package com.github.hualuomoli.raml.join.java;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.raml.model.Raml;

import com.github.hualuomoli.raml.join.Adaptor;
import com.github.hualuomoli.raml.join.JoinParser;
import com.github.hualuomoli.raml.join.JoinParser.JavaConfig;
import com.github.hualuomoli.raml.join.adaptor.FileAdaptor;
import com.github.hualuomoli.raml.join.adaptor.GetAdaptor;
import com.github.hualuomoli.raml.join.adaptor.JSONAdaptor;
import com.github.hualuomoli.raml.join.adaptor.RestfulAdaptor;
import com.github.hualuomoli.raml.join.adaptor.UriEncodedAdaptor;
import com.github.hualuomoli.raml.join.adaptor.util.JavaAdaptorUtils;
import com.google.common.collect.Lists;

public class JoinParserTest {

	private JoinParser parser;

	@Before
	public void before() {
		parser = new JavaJoinParser() {
			@Override
			protected void configure(Raml[] ramls) {
				// TODO
				String path = JoinParser.class.getClassLoader().getResource(".").getPath();
				String rootProjectPath = path.substring(0, path.indexOf("/tool/raml/parser/target"));
				logger.debug(rootProjectPath);
				config.getOutputFilepath();
				// web
				try {
					// src
					FileUtils.copyDirectory(new File(rootProjectPath, "web/src"), new File(config.getOutputFilepath(), "src"));
					// pom
					String pomContent = FileUtils.readFileToString(new File(rootProjectPath, "web/pom.xml"));
					String data = pomContent.replaceAll("web", javaConfig.getProjectName());
					FileUtils.write(new File(config.getOutputFilepath(), "pom.xml"), data, Charset.forName("UTF-8"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		JavaAdaptorUtils methodAdaptor = new JavaAdaptorUtils();

		List<Adaptor> adaptors = Lists.newArrayList(new Adaptor[] { //
				new GetAdaptor(methodAdaptor), // get
				new RestfulAdaptor(methodAdaptor), // restfule
				new UriEncodedAdaptor(methodAdaptor), // uri encoded
				new JSONAdaptor(methodAdaptor), // json
				new FileAdaptor(methodAdaptor) // file
		});
		parser.setAdaptors(adaptors);
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
		config.setRootPackageName("com.github.hualuomoli");

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
