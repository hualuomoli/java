package com.github.hualuomoli.commons.gps.rmc;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;

import com.github.hualuomoli.commons.gps.GPSParser;
import com.github.hualuomoli.commons.gps.GPSParserAbstract;
import com.github.hualuomoli.commons.gps.exception.GPSParserExcepton;

/**
 * RMC：时间、日期、位置、速度
 * 数据描述:
 * 	名称			样例					单位				描述
 * 	消息ID		$GPRMC								RMC协议头
 * 	UTC时间		061831.000							hhmmss.ss
 * 	状态			A									A=数据有效,V=数据无效
 * 	纬度			2236.9152							ddmm.mmmm
 * 	N/S指示		N									N=北,S=南
 * 	经度			11403.2422							dddmm.mmmm
 * 	E/W指示		E									W=西,E=东
 * 	地面速度		0.00				Knot(海里每小时)			
 * 	方位								度
 * 	日期			130214								ddmmyy
 * 	磁变量							
 * 	校验和		*76				
 * 样例数据：$GPRMC,061831.000,A,2236.9152,N,11403.2422,E,0.00,,130214,,,D*76
 * @author hualuomoli
 *
 */
public class GPRMCParser extends GPSParserAbstract implements GPSParser {

	private static final SimpleDateFormat timeSDF = new SimpleDateFormat("hhmmss.SSS");
	private static final SimpleDateFormat dateSDF = new SimpleDateFormat("ddmmyy");

	@Override
	public boolean support(String location) {
		if (StringUtils.isBlank(location)) {
			return false;
		}
		return StringUtils.startsWith(location, "$GPRMC");
	}

	@Override
	public boolean valid(String location) {
		String[] array = location.split("[,]");
		if (array.length != 13) {
			return false;
		}
		if (!StringUtils.equals(array[2], "A")) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GPRMC parse(String location) throws GPSParserExcepton {

		// 设置数据
		setArray(location.split("[,]"));

		try {
			GPRMC gps = new GPRMC();

			gps.setId(next());
			gps.setTime(timeSDF.parse(next()));
			gps.setState(StringUtils.equals(next(), "A"));
			gps.setLatitude(Double.parseDouble(next()));
			gps.setLatitudeNorth(StringUtils.equals(next(), "N"));
			gps.setLongitude(Double.parseDouble(next()));
			gps.setLongitudeWest(StringUtils.equals(next(), "E"));
			gps.setSpeed(StringUtils.isBlank(next()) ? 0 : Double.parseDouble(current()));
			gps.setDirection(StringUtils.isBlank(next()) ? 0 : Double.parseDouble(current()));
			gps.setDate(dateSDF.parse(next()));
			gps.setMagnetic(next());

			return gps;
		} catch (ParseException e) {
			throw new GPSParserExcepton(e);
		}

	}

}
