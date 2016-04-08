package com.github.hualuomoli.raml.parser.mocha;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.raml.model.Action;
import org.raml.model.Raml;
import org.raml.model.Resource;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.raml.parser.RamlParserAbs;

public class MochaRamlParser extends RamlParserAbs {

	public boolean agentExpress() {
		return true;
	}

	@Override
	public void config(Raml raml, String outputPath) {
	}

	@Override
	public String getCopyTemplateFolder() {
		return this.getResourceFilePath("tpl/mocha");
	}

	@Override
	public StringBuilder getHeader(Raml raml, Resource resource) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("\n").append("var assert = require('assert');");
		buffer.append("\n").append("var fs = require('fs');");
		buffer.append("\n").append("var path = require('path');");
		buffer.append("\n");
		buffer.append("\n").append("var logger = require('../logger/logger');");
		buffer.append("\n");
		if (this.agentExpress()) {
			buffer.append("\n").append("var request = require('supertest').agent(require('../app').listen());");
		} else {
			buffer.append("\n").append("var request = require('supertest')");
			buffer.append(".agent('").append(raml.getBaseUri()).append("');");
		}

		buffer.append("\n\n");
		buffer.append("describe('test " + resource.getRelativeUri() + "', function () {");

		return buffer;
	}

	@Override
	public StringBuilder getFooter() {
		return new StringBuilder("\n});");
	}

	@Override
	public StringBuilder getData(Action action, Resource resource, String outputPath) {

		StringBuilder buffer = new StringBuilder();

		// describe
		buffer.append("\n");
		buffer.append("  ");
		buffer.append("describe('test ");
		buffer.append(relativeUri + resource.getRelativeUri());
		buffer.append("', function () {");

		// it
		buffer.append("\n");
		buffer.append("    ");
		buffer.append("it('").append(action.getType().toString()).append("', function (done) {");

		buffer.append("\n");
		buffer.append("      ");
		buffer.append("request");
		// method
		buffer.append("\n");
		buffer.append("        ");
		buffer.append(".").append(this.getMethodName(action));
		buffer.append("('").append(this.getUrl(resource, action)).append("')");

		// 200
		buffer.append("\n");
		buffer.append("        ");
		buffer.append(".expect(200)");

		// show log
		buffer.append("\n");
		buffer.append("        ");
		buffer.append(".expect(function (res) {");

		buffer.append("\n");
		buffer.append("          ");
		buffer.append("logger.debug(").append(this.getMethodRes(action)).append(");");

		buffer.append("\n");
		buffer.append("        ");
		buffer.append("})");

		// done
		buffer.append("\n");
		buffer.append("        ");
		buffer.append(".end(done);");

		// -- it
		buffer.append("\n    });");

		// -- describe
		buffer.append("\n  });");

		return buffer;
	}

	private Object getMethodName(Action action) {
		switch (action.getType()) {
		case GET:
			return "get";
		case POST:
			return "post";
		case PUT:
			return "put";
		case DELETE:
			return "delete";
		default:
			break;
		}
		return "";
	}

	private Object getUrl(Resource resource, Action action) {
		String uri = relativeUri + resource.getRelativeUri();
		Map<String, UriParameter> uriParameters = resource.getUriParameters();
		for (UriParameter uriParameter : uriParameters.values()) {
			uri = StringUtils.replace(uri, "{" + uriParameter.getDisplayName() + "}", uriParameter.getExample());
		}
		return uri;
	}

	private Object getMethodRes(Action action) {
		return "res.body";
	}

	@Override
	public String getRelativeFilepath(Resource resource) {
		if (this.agentExpress()) {
			return "routes/" + this.getApiPackageName(resource.getRelativeUri()) + ".spec.js";
		}
		return this.getApiPackageName(resource.getRelativeUri()) + ".spec.js";
	}

}
