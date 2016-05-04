package com.github.hualuomoli.raml.join.mocha;

import java.util.Set;

import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.raml.join.JoinParser;
import com.github.hualuomoli.raml.util.RamlUtils;

/**
 * Mocha拼接字符串解析器
 * @author hualuomoli
 *
 */
public class MochaJoinParser extends JoinParser {

	protected MochaConfig mochaConfig;

	@Override
	protected void setConfig(Config config) {
		super.setConfig(config);
		this.mochaConfig = (MochaConfig) config;
	}

	@Override
	public String getFileHeader(String fileUri, Set<UriParameter> fileUriParameters, String fileDescription) {
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
		buffer.append(fileDescription);
		buffer.append("', function() {");

		return buffer.toString();
	}

	@Override
	public String getFileFooter() {
		StringBuilder buffer = new StringBuilder();

		buffer.append(LINE);
		buffer.append(LINE).append("});").toString();

		return buffer.toString();
	}

	@Override
	public String getFilePath(String fileUri) {
		return "test";
	}

	@Override
	public String getFileName(String fileUri) {
		StringBuilder buffer = new StringBuilder();

		String uri = RamlUtils.trimPrefix(RamlUtils.trimUriParam(fileUri), mochaConfig.getIgnoreUriPrefix());
		buffer.append(uri.substring(1).replaceAll("/", "."));
		buffer.append(".spec.js");

		return "server/" + buffer.toString();
	}

	// mocha 配置
	public static class MochaConfig extends Config {
	}
}
