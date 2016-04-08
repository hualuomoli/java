package com.github.hualuomoli.raml.parser.node;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.raml.model.Action;
import org.raml.model.MimeType;
import org.raml.model.Raml;
import org.raml.model.Resource;
import org.raml.model.Response;
import org.raml.model.parameter.FormParameter;
import org.raml.model.parameter.QueryParameter;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.raml.parser.RamlParserAbs;
import com.google.common.collect.Lists;

public class NodeRamlParser extends RamlParserAbs {

	@Override
	public StringBuilder getHeader(Raml raml, Resource resource) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("\n").append("var express = require('express');");
		buffer.append("\n").append("var util = require('util');");
		buffer.append("\n").append("var fs = require('fs');");
		buffer.append("\n").append("var path = require('path');");
		buffer.append("\n");
		buffer.append("\n").append("var logger = require('../logger/logger');");
		buffer.append("\n");
		buffer.append("\n").append("var router = express.Router();");
		buffer.append("\n");

		return buffer;
	}

	@Override
	public StringBuilder getFooter() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("\n\n").append("module.exports = router;");
		return buffer;
	}

	@Override
	public String getRelativeFilepath(Resource resource) {
		return "routes/" + this.getApiPackageName(resource.getRelativeUri()) + ".js";
	}

	@Override
	public void config(Raml raml, String outputPath) {
		Map<String, Resource> resources = raml.getResources();
		StringBuilder buffer = new StringBuilder();
		buffer.append("\n  app.use('/demo', require('./demo'));");
		for (Resource resource : resources.values()) {
			String relativeUri = resource.getRelativeUri();
			// app.use('/demo', demo);
			buffer.append("\n");
			buffer.append("  ");
			buffer.append("app.use('");
			buffer.append(relativeUri.replaceAll("[}]", "").replaceAll("[{]", ":"));
			buffer.append("', ");

			buffer.append("require('./");
			buffer.append(this.getApiPackageName(resource.getRelativeUri()));
			buffer.append("')");

			buffer.append(");");
		}

		this.replaceContent(outputPath, "routes/index.js", Lists.newArrayList("app.use('/demo', require('./demo'));"),
				Lists.newArrayList(buffer.toString()));

	}

	@Override
	public StringBuilder getData(Action action, Resource resource, String outputPath) {
		// TODO Auto-generated method stub
		StringBuilder buffer = new StringBuilder();

		// router.get('/res/txt', function (req, res) {
		buffer.append("\n");
		buffer.append("router.").append(this.getMethodName(action));
		buffer.append("('").append(this.getRouteName(resource.getRelativeUri())).append("', ");
		buffer.append("function (req, res) {");

		// parameters
		// logger.log("id " + req.params.id);
		// uri
		Map<String, UriParameter> uriParameters = resource.getUriParameters();
		for (UriParameter uriParameter : uriParameters.values()) {
			buffer.append("\n");
			buffer.append("  ");
			buffer.append("logger.log(\"");
			buffer.append(this.trimQuotes(uriParameter.getDescription()));
			buffer.append("[");
			buffer.append(uriParameter.getDisplayName());
			buffer.append("] \" + ");
			buffer.append("req.params.");
			buffer.append(uriParameter.getDisplayName());
			buffer.append(");");
		}

		// query
		Map<String, QueryParameter> queryParameters = action.getQueryParameters();
		for (QueryParameter queryParameter : queryParameters.values()) {
			buffer.append("\n");
			buffer.append("  ");
			buffer.append("logger.log(\"");
			buffer.append(this.trimQuotes(queryParameter.getDescription()));
			buffer.append("[");
			buffer.append(queryParameter.getDisplayName());
			buffer.append("] \" + ");
			buffer.append("req.query.");
			buffer.append(queryParameter.getDisplayName());
			buffer.append(");");
		}

		// form
		Map<String, MimeType> body = action.getBody();
		if (body != null) {
			for (MimeType mimeType : body.values()) {
				// application/x-www-form-urlencoded
				if (StringUtils.equals(mimeType.getType(), MIME_TYPE_URLENCODED)) {
					Map<String, List<FormParameter>> formParameters = mimeType.getFormParameters();
					for (List<FormParameter> formParameterList : formParameters.values()) {
						for (FormParameter formParameter : formParameterList) {
							buffer.append("\n");
							buffer.append("  ");
							buffer.append("logger.log(\"");
							buffer.append(this.trimQuotes(formParameter.getDescription()));
							buffer.append("[");
							buffer.append(formParameter.getDisplayName());
							buffer.append("] \" + ");
							buffer.append("req.body.");
							buffer.append(formParameter.getDisplayName());
							buffer.append(");");
						}
					}
				} else if (StringUtils.equals(mimeType.getType(), MIME_TYPE_MULTIPART)) {
					// TODO
				} else if (StringUtils.equals(mimeType.getType(), MIME_TYPE_JSON)) {
					buffer.append("\n");
					buffer.append("  ");
					buffer.append("logger.log(");
					buffer.append("req.body");
					buffer.append(");");

				} else if (StringUtils.equals(mimeType.getType(), MIME_TYPE_XML)) {
					buffer.append("\n");
					buffer.append("  ");
					buffer.append("logger.log(");
					buffer.append("req.body");
					buffer.append(");");

				}
			}
		}

		Map<String, Response> responses = action.getResponses();
		OK: for (Response response : responses.values()) {
			body = response.getBody();
			if (body != null) {
				for (MimeType mimeType : body.values()) {
					if (StringUtils.equals(mimeType.getType(), MIME_TYPE_JSON)) {
						String example = mimeType.getExample();
						String[] contents = example.split("\\n");
						buffer.append("\n\n");
						buffer.append("    ").append("var str = '';");

						for (String content : contents) {
							buffer.append("\n");
							buffer.append("    ").append("str += \"");
							buffer.append(this.trimQuotes(content));
							buffer.append("\\n\";");

							// System.out.println(content);
						}
						buffer.append("\n");
						buffer.append("    ");
						buffer.append("res.send(str);");
						break OK;
					}
				}
			}
		}

		// });
		buffer.append("\n");
		buffer.append("});");

		return buffer;
	}

	private String getRouteName(String relativeUri) {
		if (StringUtils.isBlank(relativeUri)) {
			return "/";
		}
		// /{id} -> /:id
		return relativeUri.replaceAll("[}]", "").replaceAll("[{]", ":");
	}

	private String getMethodName(Action action) {
		switch (action.getType()) {
		case GET:
			return "get";
		case POST:
			return "post";
		case DELETE:
			return "delete";
		case PUT:
			return "put";
		default:
			break;
		}
		return "";
	}

	@Override
	public String getCopyTemplateFolder() {
		return this.getResourceFilePath("tpl/node");
	}

}
