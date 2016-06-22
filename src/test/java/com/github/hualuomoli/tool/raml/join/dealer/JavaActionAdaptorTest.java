package com.github.hualuomoli.tool.raml.join.dealer;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.raml.model.Raml;
import org.raml.model.Resource;

import com.github.hualuomoli.commons.util.ProjectUtils;
import com.github.hualuomoli.tool.raml.join.JoinFileDealer;
import com.github.hualuomoli.tool.raml.join.JoinParser;
import com.github.hualuomoli.tool.raml.join.adaptor.ActionAdaptor;
import com.github.hualuomoli.tool.raml.join.adaptor.JavaActionAdaptor;
import com.github.hualuomoli.tool.raml.join.adaptor.java.JavaDeleteActionAdaptor;
import com.github.hualuomoli.tool.raml.join.adaptor.java.JavaFileActionAdaptor;
import com.github.hualuomoli.tool.raml.join.adaptor.java.JavaGetActionAdaptor;
import com.github.hualuomoli.tool.raml.join.adaptor.java.JavaPostJsonActionAdaptor;
import com.github.hualuomoli.tool.raml.join.adaptor.java.JavaPostUrlEncodedActionAdaptor;
import com.github.hualuomoli.tool.raml.join.dealer.JavaJoinFileDealer.JavaConfig;
import com.github.hualuomoli.tool.raml.join.dealer.JavaJoinFileDealer.Tool;
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

			// 测试时,将该参数设置为test目录
			// TODO
			@Override
			public String getRelativeFilePath(Resource resource) {
				return "src/test/java/" + Tool.getPackageName(resource, javaConfig).replaceAll("[.]", "/");
			}

			@Override
			public void configure(Raml[] ramls) {

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
		// 如果不设置,生产的文件在当前项目下
		// config.setOutputFilepath("");
		// config.setClear(true);
		//
		config.setAuthor("hualuomoli");
		config.setVersion(1.0);
		config.setRootPackageName("com.github.hualuomoli.app");

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
