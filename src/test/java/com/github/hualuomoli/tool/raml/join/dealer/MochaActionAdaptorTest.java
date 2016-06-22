package com.github.hualuomoli.tool.raml.join.dealer;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.raml.model.Raml;

import com.github.hualuomoli.tool.raml.join.JoinFileDealer;
import com.github.hualuomoli.tool.raml.join.JoinParser;
import com.github.hualuomoli.tool.raml.join.adaptor.ActionAdaptor;
import com.github.hualuomoli.tool.raml.join.adaptor.MochaActionAdaptor;
import com.github.hualuomoli.tool.raml.join.adaptor.mocha.MochaDeleteActionAdaptor;
import com.github.hualuomoli.tool.raml.join.adaptor.mocha.MochaFileActionAdaptor;
import com.github.hualuomoli.tool.raml.join.adaptor.mocha.MochaGetActionAdaptor;
import com.github.hualuomoli.tool.raml.join.adaptor.mocha.MochaPostJsonActionAdaptor;
import com.github.hualuomoli.tool.raml.join.adaptor.mocha.MochaPostUrlEncodedActionAdaptor;
import com.github.hualuomoli.tool.raml.join.dealer.MochaJoinFileDealer;
import com.github.hualuomoli.tool.raml.join.dealer.MochaJoinFileDealer.MochaConfig;
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

		dealer = new MochaJoinFileDealer() {

			@Override
			public void configure(Raml[] ramls) {

				try {

					String path = this.getClass().getClassLoader().getResource(".").getPath();
					String testProjectFilepath = path.substring(0, path.indexOf("/target")) + "/src/test/resources/demo/mocha";

					// filename folder
					String filename;

					// .gitignore
					filename = ".gitignore";
					FileUtils.copyFile(new File(testProjectFilepath, filename), new File(mochaConfig.getOutputFilepath(), filename));
					// .jshintrc
					filename = ".jshintrc";
					FileUtils.copyFile(new File(testProjectFilepath, filename), new File(mochaConfig.getOutputFilepath(), filename));
					// favicon.ico
					filename = "favicon.ico";
					FileUtils.copyFile(new File(testProjectFilepath, filename), new File(mochaConfig.getOutputFilepath(), filename));
					// package.json
					filename = "package.json";
					FileUtils.copyFile(new File(testProjectFilepath, filename), new File(mochaConfig.getOutputFilepath(), filename));

					// test/mocha.opts
					filename = "test/mocha.opts";
					FileUtils.copyFile(new File(testProjectFilepath, filename), new File(mochaConfig.getOutputFilepath(), filename));

					// create request.js
					// var request = require('supertest').agent('http://localhost:80/web');
					List<String> datas = Lists.newArrayList();
					datas.add("var request = require('supertest').agent('" + ramls[0].getBaseUri() + "');");
					datas.add("module.exports = request;");

					FileUtils.writeLines(new File(mochaConfig.getOutputFilepath(), "test/request.js"), mochaConfig.getEncoding().name(), datas);

				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		};

		parser = new JoinParser();

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
