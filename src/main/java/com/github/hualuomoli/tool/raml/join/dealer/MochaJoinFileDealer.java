package com.github.hualuomoli.tool.raml.join.dealer;

import java.util.List;

import org.raml.model.Resource;

import com.github.hualuomoli.tool.raml.Parser.Config;
import com.github.hualuomoli.tool.raml.join.JoinFileDealer;
import com.github.hualuomoli.tool.raml.util.RamlUtils;
import com.google.common.collect.Lists;

/**
 * Mocha - Join文件处理者
 * @author hualuomoli
 *
 */
public abstract class MochaJoinFileDealer implements JoinFileDealer {

	protected MochaConfig mochaConfig;

	@Override
	public void setConfig(Config config) {
		mochaConfig = (MochaConfig) config;
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

	}

}
