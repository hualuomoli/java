package com.github.hualuomoli.raml.join.adaptor;

import java.nio.charset.Charset;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.hualuomoli.raml.join.JoinFileDealer;
import com.github.hualuomoli.raml.join.JoinParser;
import com.github.hualuomoli.raml.join.JoinParser.Adapter;
import com.github.hualuomoli.raml.join.dealer.MochaJoinFileDealer;
import com.github.hualuomoli.raml.join.dealer.MochaJoinFileDealer.MochaConfig;
import com.google.common.collect.Lists;

public class MochaActionAdaptorTest {

	private JoinParser parser;
	private ActionAdaptor adaptor;
	private JoinFileDealer dealer;

	@Before
	public void before() {
		adaptor = new MochaActionAdaptor() {

			@Override
			public boolean support(Adapter adapter) {
				return true;
			}
		};

		dealer = new MochaJoinFileDealer();

		parser = new JoinParser() {
		};
		List<ActionAdaptor> adaptors = Lists.newArrayList(new ActionAdaptor[] { adaptor });
		parser.setAdaptors(adaptors);
		parser.setDealer(dealer);

	}

	@Test
	public void test() {
		MochaConfig config = new MochaConfig();
		config.setEncoding(Charset.forName("utf-8"));
		config.setOutputFilepath("E:/output/mocha");
		config.setClear(true);
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
