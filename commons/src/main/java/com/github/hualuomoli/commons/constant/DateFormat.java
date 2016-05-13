package com.github.hualuomoli.commons.constant;

import java.text.SimpleDateFormat;

/**
 * 日期格式化
 * @author hualuomoli
 *
 */
public class DateFormat {

	public static final String PATTERN_SLASHYMD = "yyyy/MM/dd";
	public static final String PATTERN_SLASHYMD_HMS = "yyyy/MM/dd kk:mm:ss";
	public static final String PATTERN_BARYMD = "yyyy-MM-dd";
	public static final String PATTERN_BARYMD_HMS = "yyyy-MM-dd kk:mm:ss";
	public static final String PATTERN_YMD = "yyyyMMdd";
	public static final String PATTERN_YMD_HMS = "yyyyMMddHHmmss";

	public static final SimpleDateFormat SDF_SLASHYMD = new SimpleDateFormat(PATTERN_SLASHYMD);
	public static final SimpleDateFormat SDF_SLASHYMD_HMS = new SimpleDateFormat(PATTERN_SLASHYMD_HMS);
	public static final SimpleDateFormat SDF_BARYMD = new SimpleDateFormat(PATTERN_BARYMD);
	public static final SimpleDateFormat SDF_BARYMD_HMS = new SimpleDateFormat(PATTERN_BARYMD_HMS);
	public static final SimpleDateFormat SDF_YMD = new SimpleDateFormat(PATTERN_YMD);
	public static final SimpleDateFormat SDF_YMD_HMS = new SimpleDateFormat(PATTERN_YMD_HMS);

}
