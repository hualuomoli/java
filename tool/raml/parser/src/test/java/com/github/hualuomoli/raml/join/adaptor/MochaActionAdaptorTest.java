package com.github.hualuomoli.raml.join.adaptor;

import java.nio.charset.Charset;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.hualuomoli.raml.join.JoinFileDealer;
import com.github.hualuomoli.raml.join.JoinParser;
import com.github.hualuomoli.raml.join.adaptor.mocha.MochaDeleteActionAdaptor;
import com.github.hualuomoli.raml.join.adaptor.mocha.MochaFileActionAdaptor;
import com.github.hualuomoli.raml.join.adaptor.mocha.MochaGetActionAdaptor;
import com.github.hualuomoli.raml.join.adaptor.mocha.MochaPostJsonActionAdaptor;
import com.github.hualuomoli.raml.join.adaptor.mocha.MochaPostUrlEncodedActionAdaptor;
import com.github.hualuomoli.raml.join.dealer.MochaJoinFileDealer;
import com.github.hualuomoli.raml.join.dealer.MochaJoinFileDealer.MochaConfig;
import com.google.common.collect.Lists;

public class MochaActionAdaptorTest {

	private JoinParser parser;
	private JoinFileDealer dealer;

	@Before
	public void before() {
		MochaActionAdaptor mochaGetActionAdaptor = new MochaGetActionAdaptor();
		MochaActionAdaptor mochaDeleteActionAdaptor = new MochaDeleteActionAdaptor();
		MochaActionAdaptor mochaPostUrlEncodedActionAdaptor = new MochaPostUrlEncodedActionAdaptor();
		MochaActionAdaptor mochaPostJsonActionAdaptor = new MochaPostJsonActionAdaptor();
		MochaActionAdaptor mochaFileActionAdaptor = new MochaFileActionAdaptor();

		dealer = new MochaJoinFileDealer();

		parser = new JoinParser() {
		};
		List<ActionAdaptor> adaptors = Lists.newArrayList(new ActionAdaptor[] { //
				//
				mochaGetActionAdaptor, //
				mochaDeleteActionAdaptor, //
				mochaPostUrlEncodedActionAdaptor, //
				mochaPostJsonActionAdaptor, //
				mochaFileActionAdaptor, //
		});
		parser.setAdaptors(adaptors);
		parser.setDealer(dealer);

	}

	@Test
	public void test() {
		MochaConfig config = new MochaConfig();
		config.setEncoding(Charset.forName("utf-8"));
		config.setOutputFilepath("E:/output/mocha");
		// config.setClear(true);
		//

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
