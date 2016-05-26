package com.github.hualuomoli.commons.gps.rmc;

import java.text.SimpleDateFormat;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.commons.gps.GPSParser;
import com.github.hualuomoli.commons.gps.exception.GPSParserExcepton;

public class GPRMCParserTest {

	private static final Logger logger = LoggerFactory.getLogger(GPRMCParser.class);

	private GPSParser parser;
	private String location;

	@Before
	public void before() {
		parser = new GPRMCParser();
		location = "$GPRMC,061831.006,A,2236.9152,N,11403.2422,E,0.00,,130214,,,D*76";
	}

	@Test
	public void testParse() throws GPSParserExcepton {

		// valid
		logger.debug("support {}", parser.support(location));
		logger.debug("valid {}", parser.valid(location));
		GPRMC gps = parser.parse(location);
		logger.debug("gps {}", ToStringBuilder.reflectionToString(gps));
		logger.debug(new SimpleDateFormat("hhmmss.SSS").format(gps.getTime()));

		// invalid
		location = "$GPRMC,001716.067,V,0000.0000,N,00000.0000,E,,,280908,,,N*7D";
		logger.debug("support {}", parser.support(location));
		logger.debug("valid {}", parser.valid(location));

	}

}
