package com.github.hualuomoli.commons.bmap.decode;

/**
 * 逆地理编码返回
 * @author hualuomoli
 *
 */
public class DecodeResult {

	private int status;
	private Result result;

	public DecodeResult() {
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public static class Result {
		private Location location;
		private String formatted_address; // 结构化地址信息
		private String business; // 所在商圈信息，如 "人民大学,中关村,苏州街"
		private AddressComponent addressComponent;
		private Poi[] pois; // 周边poi
		private String sematic_description; // 当前位置结合POI的语义化结果描述。

		public Result() {
		}

		public Location getLocation() {
			return location;
		}

		public void setLocation(Location location) {
			this.location = location;
		}

		public String getFormatted_address() {
			return formatted_address;
		}

		public void setFormatted_address(String formatted_address) {
			this.formatted_address = formatted_address;
		}

		public String getBusiness() {
			return business;
		}

		public void setBusiness(String business) {
			this.business = business;
		}

		public AddressComponent getAddressComponent() {
			return addressComponent;
		}

		public void setAddressComponent(AddressComponent addressComponent) {
			this.addressComponent = addressComponent;
		}

		public Poi[] getPois() {
			return pois;
		}

		public void setPois(Poi[] pois) {
			this.pois = pois;
		}

		public String getSematic_description() {
			return sematic_description;
		}

		public void setSematic_description(String sematic_description) {
			this.sematic_description = sematic_description;
		}

		/**
		 * 周边poi
		 * {
		 * "addr": "北京北京海淀海淀区中关村大街27号（地铁海淀黄庄站A1",
		 * "cp": "NavInfo",
		 * "direction": "内",
		 * "distance": "0",
		 * "name": "北京远景国际公寓(中关村店)",
		 * "poiType": "房地产",
		 * "point": {
		 *  "x": 116.32294589160056,
		 *  "y": 39.983610361549299
		 * },
		 * "tag": "房地产",
		 * "tel": "",
		 * "uid": "35a08504cb51b1138733049d",
		 * "zip": ""
		 * }
		 */
		public static class Poi {
			private String addr;// 地址信息
			private String cp;// 数据来源
			private String direction;// 和当前坐标点的方向
			private String distance;// 离坐标点距离
			private String name;// poi名称
			private String poiType;// poi类型，如’ 办公大厦,商务大厦’
			private Point point;// poi坐标{x,y}
			private String tel;// 电话
			private String uid;// poi唯一标识
			private String zip;// 邮编

			public Poi() {
			}

			public String getAddr() {
				return addr;
			}

			public void setAddr(String addr) {
				this.addr = addr;
			}

			public String getCp() {
				return cp;
			}

			public void setCp(String cp) {
				this.cp = cp;
			}

			public String getDirection() {
				return direction;
			}

			public void setDirection(String direction) {
				this.direction = direction;
			}

			public String getDistance() {
				return distance;
			}

			public void setDistance(String distance) {
				this.distance = distance;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getPoiType() {
				return poiType;
			}

			public void setPoiType(String poiType) {
				this.poiType = poiType;
			}

			public Point getPoint() {
				return point;
			}

			public void setPoint(Point point) {
				this.point = point;
			}

			public String getTel() {
				return tel;
			}

			public void setTel(String tel) {
				this.tel = tel;
			}

			public String getUid() {
				return uid;
			}

			public void setUid(String uid) {
				this.uid = uid;
			}

			public String getZip() {
				return zip;
			}

			public void setZip(String zip) {
				this.zip = zip;
			}

			public static class Point {
				private double x;
				private double y;

				public Point() {
				}

				public double getX() {
					return x;
				}

				public void setX(double x) {
					this.x = x;
				}

				public double getY() {
					return y;
				}

				public void setY(double y) {
					this.y = y;
				}
			}

		}

		/**
		 * {
		 *   "adcode": "110108",
		 *   "city": "北京市",
		 *   "country": "中国",
		 *   "direction": "附近",
		 *   "distance": "7",
		 *   "district": "海淀区",
		 *   "province": "北京市",
		 *   "street": "中关村大街",
		 *   "street_number": "27号1101-08室",
		 *   "country_code": 0
		 * }
		 *
		 */
		public static class AddressComponent {
			private String country;// 国家
			private String province;// 省名
			private String city;// 城市名
			private String district;// 区县名
			private String street;// 街道名
			private String street_number;// 街道门牌号
			private String adcode;// 行政区划代码
			private int country_code;// 国家代码
			private String direction;// 和当前坐标点的方向，当有门牌号的时候返回数据
			private String distance;// 和当前坐标点的距离，当有门牌号的时候返回数据

			public AddressComponent() {
			}

			public String getCountry() {
				return country;
			}

			public void setCountry(String country) {
				this.country = country;
			}

			public String getProvince() {
				return province;
			}

			public void setProvince(String province) {
				this.province = province;
			}

			public String getCity() {
				return city;
			}

			public void setCity(String city) {
				this.city = city;
			}

			public String getDistrict() {
				return district;
			}

			public void setDistrict(String district) {
				this.district = district;
			}

			public String getStreet() {
				return street;
			}

			public void setStreet(String street) {
				this.street = street;
			}

			public String getStreet_number() {
				return street_number;
			}

			public void setStreet_number(String street_number) {
				this.street_number = street_number;
			}

			public String getAdcode() {
				return adcode;
			}

			public void setAdcode(String adcode) {
				this.adcode = adcode;
			}

			public int getCountry_code() {
				return country_code;
			}

			public void setCountry_code(int country_code) {
				this.country_code = country_code;
			}

			public String getDirection() {
				return direction;
			}

			public void setDirection(String direction) {
				this.direction = direction;
			}

			public String getDistance() {
				return distance;
			}

			public void setDistance(String distance) {
				this.distance = distance;
			}

		}

		// 坐标位置
		public static class Location {
			private double lat; // 纬度坐标
			private double lng; // 经度坐标

			public Location() {
			}

			public double getLat() {
				return lat;
			}

			public void setLat(double lat) {
				this.lat = lat;
			}

			public double getLng() {
				return lng;
			}

			public void setLng(double lng) {
				this.lng = lng;
			}

		}
	}

}
