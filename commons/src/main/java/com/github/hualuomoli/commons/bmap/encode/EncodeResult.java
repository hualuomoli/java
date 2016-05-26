package com.github.hualuomoli.commons.bmap.encode;

/**
 * 地理编码返回
 * @author hualuomoli
 *
 */
public class EncodeResult {

	private int status;
	private Result result;

	public EncodeResult() {
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
		private int precise; // 位置的附加信息，是否精确查找。1为精确查找，即准确打点；0为不精确，即模糊打点。
		private int confidence; // 可信度，描述打点准确度
		private String level; // 地址类型

		public Result() {
		}

		public Location getLocation() {
			return location;
		}

		public void setLocation(Location location) {
			this.location = location;
		}

		public int getPrecise() {
			return precise;
		}

		public void setPrecise(int precise) {
			this.precise = precise;
		}

		public int getConfidence() {
			return confidence;
		}

		public void setConfidence(int confidence) {
			this.confidence = confidence;
		}

		public String getLevel() {
			return level;
		}

		public void setLevel(String level) {
			this.level = level;
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
