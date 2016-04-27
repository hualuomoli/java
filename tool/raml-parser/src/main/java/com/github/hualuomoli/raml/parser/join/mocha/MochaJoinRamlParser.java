package com.github.hualuomoli.raml.parser.join.mocha;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.raml.model.Action;
import org.raml.model.ActionType;
import org.raml.model.Raml;
import org.raml.model.Resource;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.raml.parser.exception.ParseException;
import com.github.hualuomoli.raml.parser.join.JoinRamlParser;
import com.github.hualuomoli.raml.parser.join.java.JavaJoinRamlParser;
import com.github.hualuomoli.raml.parser.join.transfer.res.success.json.mocha.FileTransfer;
import com.github.hualuomoli.raml.parser.join.transfer.res.success.json.mocha.GetTransfer;
import com.github.hualuomoli.raml.parser.join.transfer.res.success.json.mocha.JsonTransfer;
import com.github.hualuomoli.raml.parser.join.transfer.res.success.json.mocha.RestfulTransfer;
import com.github.hualuomoli.raml.parser.join.transfer.res.success.json.mocha.UrlEncodedTransfer;
import com.github.hualuomoli.raml.parser.util.RamlUtils;
import com.google.common.collect.Lists;

/**
 * mocha测试解析
 * @author hualuomoli
 *
 */
public class MochaJoinRamlParser extends JoinRamlParser {

	public MochaJoinRamlParser() {
		super();
		transferList = Lists.newArrayList();
		transferList.add(new RestfulTransfer());
		transferList.add(new GetTransfer());
		transferList.add(new UrlEncodedTransfer());
		transferList.add(new JsonTransfer());
		transferList.add(new FileTransfer());
	}

	@Override
	protected void configProejct(Raml raml) {
		try {
			// use web project
			String path = JavaJoinRamlParser.class.getClassLoader().getResource("log4j.properties").getPath();
			// root project path
			String rootProjectFilepath = path.substring(0, path.indexOf("/tool/raml-parser/target"));
			// web project path
			String testProjectFilepath = new File(rootProjectFilepath, "test").getAbsolutePath();

			// filename folder
			String filename;

			// .gitignore
			filename = ".gitignore";
			FileUtils.copyFile(new File(testProjectFilepath, filename), new File(outputFilepath, filename));
			// .jshintrc
			filename = ".jshintrc";
			FileUtils.copyFile(new File(testProjectFilepath, filename), new File(outputFilepath, filename));
			// favicon.ico
			filename = "favicon.ico";
			FileUtils.copyFile(new File(testProjectFilepath, filename), new File(outputFilepath, filename));
			// package.json
			filename = "package.json";
			FileUtils.copyFile(new File(testProjectFilepath, filename), new File(outputFilepath, filename));

			// test/mocha.opts
			filename = "test/mocha.opts";
			FileUtils.copyFile(new File(testProjectFilepath, filename), new File(outputFilepath, filename));

			// create request.js
			StringBuilder buffer = new StringBuilder();
			// var request = require('supertest').agent('http://localhost:80/web');
			buffer.append("var request = require('supertest').agent");
			buffer.append("('");
			buffer.append(raml.getBaseUri());
			buffer.append("');").append(LINE);
			buffer.append("module.exports = request;");

			FileUtils.write(new File(outputFilepath, "test/request.js"), buffer.toString(), encoding);

		} catch (Exception e) {
		}
	}

	@Override
	public String getFileHeader(List<String> actionDatas, String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource)
			throws ParseException {
		StringBuilder buffer = new StringBuilder();

		buffer.append("var assert = require('assert');");
		buffer.append(LINE).append("var should = require('should');");
		buffer.append(LINE).append("var fs = require('fs');");
		buffer.append(LINE).append("var path = require('path');");
		buffer.append(LINE);
		buffer.append(LINE).append("var request = require('../request');");

		buffer.append(LINE);
		// describe('参数类型', function () {
		buffer.append(LINE).append("describe").append("('");
		buffer.append(resource.getDescription().replaceAll("\\n", "    "));
		buffer.append("', function() {");

		return buffer.toString();
	}

	@Override
	public String getFileFooter(List<String> actionDatas, String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource)
			throws ParseException {
		StringBuilder buffer = new StringBuilder();

		buffer.append(LINE);
		buffer.append(LINE).append("});").toString();

		return buffer.toString();
	}

	@Override
	public String getFilepath() throws ParseException {
		return "test";
	}

	@Override
	public String getFilename(String parentFullUri, Resource resource) throws ParseException {
		StringBuilder buffer = new StringBuilder();

		String uri = RamlUtils.trimPrefix(RamlUtils.trimUriParam(parentFullUri), uriPrefix);
		if (StringUtils.isEmpty(uri)) {
			throw new ParseException("please use valid raml file.");
		}
		buffer.append(uri.substring(1).replaceAll("/", "."));
		buffer.append(".spec.js");

		return "server/" + buffer.toString();
	}

	@Override
	public void configFile(Map<ActionType, Action> actions, Map<String, Resource> noChildResources, String parentFullUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException {
	}
}
