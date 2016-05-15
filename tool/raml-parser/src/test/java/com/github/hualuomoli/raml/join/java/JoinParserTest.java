package com.github.hualuomoli.raml.join.java;

import java.nio.charset.Charset;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.hualuomoli.raml.Parser.Config;
import com.github.hualuomoli.raml.join.Adaptor;
import com.github.hualuomoli.raml.join.JoinParser;
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
		parser = new JavaJoinParser();
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
		Config config = new Config();
		config.setEncoding(Charset.forName("utf-8"));
		config.setOutputFilepath("E:/output/java");
		// config.setClear(true);

		parser.init(config);
		parser.execute(new String[] { "raml/uri.raml" });

	}

}
