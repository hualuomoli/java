package com.github.hualuomoli.commons.bmap;

import java.util.Map;

import com.github.hualuomoli.commons.bmap.decode.DecodeParam;
import com.github.hualuomoli.commons.bmap.decode.DecodeResult;
import com.github.hualuomoli.commons.bmap.encode.EncodeParam;
import com.github.hualuomoli.commons.bmap.encode.EncodeResult;
import com.github.hualuomoli.commons.util.HttpURLUtils;
import com.github.hualuomoli.commons.util.JsonUtils;
import com.github.hualuomoli.commons.util.YamlUtils;
import com.google.common.collect.Maps;

/**
 * 百度地图工具
 * @author admin
 *
 */
public class BMapUtils {

	private static final String URL = "http://api.map.baidu.com/geocoder/v2/";
	private static final String resultType = "json";
	private static final String security = YamlUtils.getInstance().getString("bmap", "security");

	/**
	 * 根据名称获取坐标
	 * @param param 参数
	 * @return 坐标
	 */
	public static EncodeResult encodeLocation(EncodeParam param) {
		Map<String, String> dataMap = Maps.newHashMap();
		dataMap.put("ak", security);
		dataMap.put("output", resultType);
		dataMap.put("address", param.getAddress());
		dataMap.put("city", param.getCity());

		String output = HttpURLUtils.get(URL, dataMap);
		return JsonUtils.parseObject(output, EncodeResult.class);
	}

	/**
	 * 根据坐标获取名称
	 * @param param 参数
	 * @return 名称
	 */
	public static DecodeResult decodeLocation(DecodeParam param) {
		Map<String, String> dataMap = Maps.newHashMap();
		dataMap.put("ak", security);
		dataMap.put("output", resultType);
		dataMap.put("location", param.getLocation());
		dataMap.put("pois", param.getPois().getValue());
		dataMap.put("coordtype", param.getCoordtype().getType());

		String output = HttpURLUtils.get(URL, dataMap);
		return JsonUtils.parseObject(output, DecodeResult.class);
	}

}
