package com.github.hualuomoli.demo.entity;

import java.io.File;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.commons.template.TemplateUtils;
import com.github.hualuomoli.tool.creator.ServiceUtils;
import com.github.hualuomoli.tool.creator.entity.Mapper;
import com.github.hualuomoli.tool.creator.entity.Service;
import com.google.common.collect.Sets;

public class CreatorDemoTest {

	private static final Logger logger = LoggerFactory.getLogger(CreatorDemo.class);

	@Test
	public void test() {
		Set<String> ignores = Sets.newHashSet(new String[] { "pagination" });
		Service service = ServiceUtils.getService(CreatorDemo.class, ignores, "com.github.hualuomoli");
		Mapper mapper = service.getMapper();

		String path = CreatorDemo.class.getClassLoader().getResource(".").getPath().replaceAll("\\\\", "/");
		String outputpath = path.substring(0, path.lastIndexOf("/target"));
		logger.debug("outputpath {}", outputpath);

		File relativeDir = null;
		File output = null;
		String filepath = null;
		String templateResourcePath = "tpl";

		// mapper
		relativeDir = new File(outputpath, "src/main/java");
		filepath = new File(relativeDir.getAbsolutePath(), mapper.getFilepath()).getAbsolutePath();
		output = new File(filepath, mapper.getFilename());
		TemplateUtils.processByResource(templateResourcePath, "mapper.tpl", mapper, output);

		// config
		relativeDir = new File(outputpath, "src/main/resources/mappers");
		filepath = new File(relativeDir.getAbsolutePath(), mapper.getConfigFilePath()).getAbsolutePath();
		output = new File(filepath, mapper.getConfigFileName());
		TemplateUtils.processByResource(templateResourcePath, "config.tpl", mapper, output);

		// service
		relativeDir = new File(outputpath, "src/main/java");
		filepath = new File(relativeDir.getAbsolutePath(), service.getFilepath()).getAbsolutePath();
		output = new File(filepath, service.getFilename());
		TemplateUtils.processByResource("tpl", "service.tpl", service, output);

		// service impl
		output = new File(filepath, service.getName() + "Impl.java");
		TemplateUtils.processByResource("tpl", "serviceImpl.tpl", service, output);

	}

}
