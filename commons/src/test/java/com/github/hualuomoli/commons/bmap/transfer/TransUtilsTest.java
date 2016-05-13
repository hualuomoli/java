package com.github.hualuomoli.commons.bmap.transfer;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransUtilsTest {

	private static final Logger logger = LoggerFactory.getLogger(TransUtils.class);

	@Test
	public void testParseLatitudeString() {
		logger.debug("latitude {}", TransUtils.parseLatitude("22°37'31.0"));
		logger.debug("latitude {}", TransUtils.parseLatitude("2236.9152"));
	}

	@Test
	public void testParseLatitudeStringInt() {
		logger.debug("latitude {}", TransUtils.parseLatitude("22°37'31.0", 6));
		logger.debug("latitude {}", TransUtils.parseLatitude("2236.9152", 6));
	}

	@Test
	public void testParseLongitudeString() {
		logger.debug("longitude {}", TransUtils.parseLongitude("114°03'24.0"));
		logger.debug("longitude {}", TransUtils.parseLongitude("11403.2422"));
	}

	@Test
	public void testParseLongitudeStringInt() {
		logger.debug("longitude {}", TransUtils.parseLongitude("114°03'24.0", 4));
		logger.debug("longitude {}", TransUtils.parseLongitude("11403.2422", 4));
	}

}
