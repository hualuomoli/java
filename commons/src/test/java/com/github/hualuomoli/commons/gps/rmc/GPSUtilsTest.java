package com.github.hualuomoli.commons.gps.rmc;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.commons.gps.GPS;
import com.github.hualuomoli.commons.gps.GPSParser;
import com.github.hualuomoli.commons.gps.GPSUtils;
import com.github.hualuomoli.commons.gps.exception.GPSParserExcepton;
import com.google.common.collect.Lists;

public class GPSUtilsTest {

	private static final Logger logger = LoggerFactory.getLogger(GPSUtils.class);

	private String location;

	@Before
	public void before() {
		location = "$GPRMC,061831.006,A,2236.9152,N,11403.2422,E,0.00,,130214,,,D*76";
	}

	@Test
	public void testParseString() throws GPSParserExcepton {
		GPS gps = GPSUtils.parse(location);
		logger.debug("gps class {}", gps.getClass().getName());
		logger.debug("gps {}", ToStringBuilder.reflectionToString(gps));
	}

	@Test
	public void testParseStringListOfGPSParser() throws GPSParserExcepton {
		List<GPSParser> parserList = Lists.newArrayList();
		parserList.add(new GPRMCParser());

		GPS gps = GPSUtils.parse(location, parserList);
		logger.debug("gps class {}", gps.getClass().getName());
		logger.debug("gps {}", ToStringBuilder.reflectionToString(gps));
	}

	@Test(expected = GPSParserExcepton.class)
	public void testInvalid() throws GPSParserExcepton {
		location = "$GPRMC,001716.067,V,0000.0000,N,00000.0000,E,,,280908,,,N*7D";
		GPS gps = GPSUtils.parse(location);
		logger.debug("gps class {}", gps.getClass().getName());
		logger.debug("gps {}", ToStringBuilder.reflectionToString(gps));
	}

	@Test
	public void testNoSupported() throws GPSParserExcepton {
		location = "$GPGGA,061831.000,2236.9152,N,11403.2422,E,2,07,1.1,144.0,M,-2.2,M,4.8,0000*60";
		GPS gps = GPSUtils.parse(location);
		logger.debug("no support parser {}", gps == null);
	}

}
