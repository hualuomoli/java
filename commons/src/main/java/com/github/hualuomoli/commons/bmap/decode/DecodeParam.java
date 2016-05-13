package com.github.hualuomoli.commons.bmap.decode;

/**
 * 逆地理编码参数
 * @author hualuomoli
 *
 */
public class DecodeParam {

	private Coordtype coordtype = Coordtype.bd09ll; // 查询类型
	private String location; // 经纬度,使用逗号(,)分割
	private Pois pois = Pois.hide; // 是否查询周边

	public DecodeParam() {
	}

	public Coordtype getCoordtype() {
		return coordtype;
	}

	public void setCoordtype(Coordtype coordtype) {
		this.coordtype = coordtype;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Pois getPois() {
		return pois;
	}

	public void setPois(Pois pois) {
		this.pois = pois;
	}

	public static enum Coordtype {

		bd09ll("bd09ll"), // 百度经纬度坐标
		bd09mc("bd09mc"), // 百度米制坐标
		gcj02ll("gcj02ll"), // 国测局经纬度坐标
		wgs84ll("wgs84ll"); // GPS经纬度

		private String type;

		private Coordtype(String type) {
			this.type = type;
		}

		public String getType() {
			return type;
		}

	}

	// 是否显示指定位置周边的poi，0为不显示，1为显示。当值为1时，显示周边100米内的poi。
	public static enum Pois {
		show("1"), // 显示
		hide("0"); // 不显示

		private String value;

		private Pois(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

}
