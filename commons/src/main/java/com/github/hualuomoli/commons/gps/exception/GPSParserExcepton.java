package com.github.hualuomoli.commons.gps.exception;

/**
 * GPS解析异常
 * @author hualuomoli
 *
 */
public class GPSParserExcepton extends Exception {

	private static final long serialVersionUID = -4421639499776941519L;

	public static final GPSParserExcepton INVALID = new GPSParserExcepton("invalid data");

	public GPSParserExcepton(String message) {
		super(message);
	}

	public GPSParserExcepton(Throwable cause) {
		super(cause);
	}

}
