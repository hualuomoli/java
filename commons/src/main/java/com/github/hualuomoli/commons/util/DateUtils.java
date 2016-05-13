package com.github.hualuomoli.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {

	private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

	public static final String PATTERN_SLASHYMD = "yyyy/MM/dd";
	public static final String PATTERN_SLASHYMD_HMS = "yyyy/MM/dd kk:mm:ss";
	public static final String PATTERN_BARYMD = "yyyy-MM-dd";
	public static final String PATTERN_BARYMD_HMS = "yyyy-MM-dd kk:mm:ss";
	public static final String PATTERN_YMD = "yyyyMMdd";
	public static final String PATTERN_YMD_HMS = "yyyyMMddHHmmss";
	public static final String PATTERN_HMS = "HH:mm:ss";

	public static final SimpleDateFormat SDF_SLASHYMD = new SimpleDateFormat(PATTERN_SLASHYMD);
	public static final SimpleDateFormat SDF_SLASHYMD_HMS = new SimpleDateFormat(PATTERN_SLASHYMD_HMS);
	public static final SimpleDateFormat SDF_BARYMD = new SimpleDateFormat(PATTERN_BARYMD);
	public static final SimpleDateFormat SDF_BARYMD_HMS = new SimpleDateFormat(PATTERN_BARYMD_HMS);
	public static final SimpleDateFormat SDF_YMD = new SimpleDateFormat(PATTERN_YMD);
	public static final SimpleDateFormat SDF_YMD_HMS = new SimpleDateFormat(PATTERN_YMD_HMS);
	public static final SimpleDateFormat SDF_HMS = new SimpleDateFormat(PATTERN_HMS);

	private static final SimpleDateFormat _getSDF(String source) {
		if (source.matches("\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
			return SDF_SLASHYMD_HMS;
		} else if (source.matches("\\d{4}/\\d{2}/\\d{2}")) {
			return SDF_SLASHYMD;
		} else if (source.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
			return SDF_BARYMD_HMS;
		} else if (source.matches("\\d{4}-\\d{2}-\\d{2}")) {
			return SDF_BARYMD;
		} else if (source.matches("\\d{14}")) {
			return SDF_YMD_HMS;
		} else if (source.matches("\\d{8}")) {
			return SDF_YMD;
		} else if (source.matches("\\d{2}:\\d{2}:\\d{2}")) {
			return SDF_HMS;
		} else {
			throw new RuntimeException("can not support pattern " + source);
		}
	}

	/**
	 * 解析日期
	 * @param source 日期字符串
	 * @return 日期
	 */
	public static final Date parse(String source) {
		try {
			return _getSDF(source).parse(source);
		} catch (ParseException e) {
			logger.warn("can not parse source " + source);
			return null;
		}
	}

}
