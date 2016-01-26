package com.github.hualuomoli.commons.util;

import java.text.SimpleDateFormat;

/**
 * Date Util
 * @author hualuomoli
 *
 */
public class DateUtils {

	public static final SimpleDateFormat sdfSlashYmd = new SimpleDateFormat("yyyy/MM/dd");
	public static final SimpleDateFormat sdfSlashYmdHms = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
	public static final SimpleDateFormat sdfBarYmd = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat sdfBarYmdHms = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
	public static final SimpleDateFormat sdfYmd = new SimpleDateFormat("yyyyMMdd");
	public static final SimpleDateFormat sdfYmdHms = new SimpleDateFormat("yyyyMMddHHmmss");
	public static final SimpleDateFormat sdfColonHms = new SimpleDateFormat("kk:mm:ss");
	public static final SimpleDateFormat sdfHms = new SimpleDateFormat("kkmmss");

}
