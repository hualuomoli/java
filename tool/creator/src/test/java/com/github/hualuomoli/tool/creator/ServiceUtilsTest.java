package com.github.hualuomoli.tool.creator;

import java.io.File;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.commons.template.TemplateUtils;
import com.github.hualuomoli.tool.creator.entity.Attribute;
import com.github.hualuomoli.tool.creator.entity.Service;

public class ServiceUtilsTest {

	private static final Logger logger = LoggerFactory.getLogger(ServiceUtils.class);

	@Test
	public void testGetService() throws Exception {
		Service service = ServiceUtils.getService(Attribute.class, null, "com.github.hualuomoli");
		logger.debug("service {}", ToStringBuilder.reflectionToString(service));

		String outputpath = "E:/github/hualuomoli/java/web";

		// service
		String filepath = new File(outputpath + "/src/main/java", service.getFilepath()).getAbsolutePath();
		logger.debug("serviceFilepath {}", filepath);
		File output = new File(filepath, service.getFilename());
		TemplateUtils.processByResource("tpl", "service.tpl", service, output);

		// service impl
		output = new File(filepath, service.getName() + "Impl.java");
		TemplateUtils.processByResource("tpl", "serviceImpl.tpl", service, output);

	}

}
