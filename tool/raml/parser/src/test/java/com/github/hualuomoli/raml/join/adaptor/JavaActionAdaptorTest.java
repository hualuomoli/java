package com.github.hualuomoli.raml.join.adaptor;

import java.nio.charset.Charset;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

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

		dealer = new JavaJoinFileDealer();

		parser = new JoinParser() {
		};
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
