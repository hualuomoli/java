package com.github.hualuomoli.commons.bmap.transfer;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 经纬度转换
 * @author hualuomoli
 *
 */
public class TransUtils {

	private static final int DEFAULT_LATITUDE_SCALE = 9; // 纬度默认保留9位
	private static final int DEFAULT_LONGITUDE_SCALE = 12; // 经度默认保留12位

	/**
	 * 解析纬度,默认 #DEFAULT_LATITUDE_SCALE
	 * @param degree 度
	 * @return 维度
	 */
	public static String parseLatitude(String degree) {
		return parseLatitude(degree, DEFAULT_LATITUDE_SCALE);
	}

	/**
	 * 解析纬度
	 * @param degree 度
	 * @param scale 保留小数位
	 * @return 维度
	 */
	public static String parseLatitude(String degree, int scale) {
		double latitude = 0;
		if (degree.matches("\\d{2}°\\d{2}'\\d{2}\\.\\d{1}")) {
			// 22°37'31.0
			latitude += Integer.parseInt(degree.substring(0, 2)); // 度
			latitude += Double.parseDouble(degree.substring(3, 5)) / 60;// 分
			latitude += Double.parseDouble(degree.substring(6)) / 3600; //
		} else if (degree.matches("\\d{4}\\.\\d{4}")) {
			// 2236.9152
			// 度 分
			latitude += Integer.parseInt(degree.substring(0, 2)); // 度
			latitude += Double.parseDouble(degree.substring(2)) / 60;// 分
		}
		return precision(latitude, scale);
	}

	/**
	 * 解析经度,默认 #DEFAULT_LONGITUDE_SCALE
	 * @param degree 度
	 * @return 经度
	 */
	public static String parseLongitude(String degree) {
		return parseLongitude(degree, DEFAULT_LONGITUDE_SCALE);
	}

	/**
	 * 解析经度
	 * @param degree 度
	 * @param scale 保留小数位
	 * @return 经度
	 */
	public static String parseLongitude(String degree, int scale) {

		double longitude = 0;
		if (degree.matches("\\d{3}°\\d{2}'\\d{2}\\.\\d{1}")) {
			// 114°03'24.0
			longitude += Integer.parseInt(degree.substring(0, 3)); // 度
			longitude += Double.parseDouble(degree.substring(4, 6)) / 60;// 分
			longitude += Double.parseDouble(degree.substring(7)) / 3600; //
		} else if (degree.matches("\\d{5}\\.\\d{4}")) {
			// 11403.2422
			// 度 分
			longitude += Integer.parseInt(degree.substring(0, 3)); // 度
			longitude += Double.parseDouble(degree.substring(3)) / 60;// 分
		}
		return precision(longitude, scale);

	}

	/**
	 * 保留精度,默认四舍五入
	 * @param latitude
	 * @param scale 精度
	 * @return 维度
	 */
	private static String precision(double data, int scale) {
		return precision(data, scale, RoundingMode.HALF_UP);
	}

	/**
	 * 保留精度
	 * @param latitude
	 * @param scale 精度
	 * @roundingMode 模式
	 * @return 维度
	 */
	private static String precision(double data, int scale, RoundingMode roundingMode) {
		return new BigDecimal(data).setScale(scale, roundingMode).toString();
	}

}
