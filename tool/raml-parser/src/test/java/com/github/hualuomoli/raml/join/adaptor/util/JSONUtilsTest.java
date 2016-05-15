package com.github.hualuomoli.raml.join.adaptor.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.raml.join.adaptor.entity.ParamData;

public class JSONUtilsTest {

	public static final Logger logger = LoggerFactory.getLogger(JSONUtils.class);

	private static String filepath;

	@Before
	public void before() {
		String name = this.getClass().getName();
		String path = name.substring(0, name.lastIndexOf(".")).replaceAll("[.]", "/");
		filepath = JSONUtilsTest.class.getClassLoader().getResource(path).getPath();
		logger.debug("filepath {}", filepath);
	}

	@Test
	public void testParseExampleString() throws IOException {
		String example = FileUtils.readFileToString(new File(filepath, "example.json"));
		List<ParamData> jsons = JSONUtils.parseExample(example);
		for (ParamData json : jsons) {
			show(json, 0);
		}
	}

	@Test
	public void testParseSchemaString() throws IOException {
		String schema = FileUtils.readFileToString(new File(filepath, "schema.json"));
		List<ParamData> jsons = JSONUtils.parseSchema(schema);
		for (ParamData json : jsons) {
			show(json, 0);
		}
	}

	private void show(ParamData json, int level) {

		if (json.getChildren() == null) {
			String t = "";
			for (int i = 0; i < level; i++) {
				t += "  ";
			}
			logger.debug("{} {}", t, ToStringBuilder.reflectionToString(json));
		} else {
			for (ParamData j : json.getChildren()) {
				show(j, level + 1);
			}
		}
	}

}
