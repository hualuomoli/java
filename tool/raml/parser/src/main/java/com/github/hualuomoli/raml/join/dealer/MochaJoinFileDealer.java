package com.github.hualuomoli.raml.join.dealer;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.raml.model.Raml;
import org.raml.model.Resource;

import com.github.hualuomoli.raml.Parser.Config;
import com.github.hualuomoli.raml.join.JoinFileDealer;
import com.github.hualuomoli.raml.util.RamlUtils;
import com.google.common.collect.Lists;

/**
 * Mocha - Join文件处理者
 * @author hualuomoli
 *
 */
public class MochaJoinFileDealer implements JoinFileDealer {

	private MochaConfig mochaConfig;

	@Override
	public void setConfig(Config config) {
		mochaConfig = (MochaConfig) config;
	}

	@Override
	public void configure(Raml[] ramls) {
		try {
			String testProjectFilepath = mochaConfig.getTestProjectPath();

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
		}
	}

	@Override
	public String getRelativeFilePath(Resource resource) {
		return "test";
	}

	@Override
	public String getFileName(Resource resource) {
		StringBuilder buffer = new StringBuilder();

		String uri = RamlUtils.removePrefix(RamlUtils.getFullUri(resource), mochaConfig.getIgnoreUriPrefix());
		buffer.append(uri.substring(1).replaceAll("/", "."));
		buffer.append(".spec.js");

		return "server/" + buffer.toString();
	}

	@Override
	public List<String> getFileHeader(Resource resource) {

		List<String> datas = Lists.newArrayList();

		datas.add("var assert = require('assert');");
		datas.add("var should = require('should');");
		datas.add("var fs = require('fs');");
		datas.add("var path = require('path');");
		datas.add("");
		datas.add("var request = require('../request');");

		datas.add("");
		// describe('参数类型', function () {
		datas.add("describe('" + RamlUtils.dealDescription(resource.getDescription()) + "', function () {");

		return datas;
	}

	@Override
	public List<String> getFileFooter(Resource resource) {
		List<String> datas = Lists.newArrayList();

		datas.add("");
		datas.add("});");

		return datas;
	}

	// config
	public static class MochaConfig extends Config {

		private String testProjectPath;

		public String getTestProjectPath() {
			return testProjectPath;
		}

		public void setTestProjectPath(String testProjectPath) {
			this.testProjectPath = testProjectPath;
		}

	}

}
