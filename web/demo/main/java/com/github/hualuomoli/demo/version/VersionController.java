package com.github.hualuomoli.demo.version;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.hualuomoli.mvc.annotation.RequestVersion;

@RestController(value = "com.github.hualuomoli.demo.version.VersionController")
@RequestMapping(value = "/demo/version")
@RequestVersion
public class VersionController {

	private static final Logger logger = LoggerFactory.getLogger(VersionController.class);

	// default
	@RequestMapping(value = "", produces = { "text/plan" })
	public String init() {
		logger.debug("default version");
		return "0";
	}

	// version 1.0
	@RequestVersion(value = "v1.0.0")
	@RequestMapping(value = "", produces = { "text/plan" })
	public String v1() {
		logger.debug("version 1.0.0");
		return "1.0.0";
	}

	// version 3.2.1
	@RequestVersion(value = "v3.2.1")
	@RequestMapping(value = "", produces = { "text/plan" })
	public String v3_2_1() {
		logger.debug("version 3.2.1");
		return "3.2.1";
	}

}
