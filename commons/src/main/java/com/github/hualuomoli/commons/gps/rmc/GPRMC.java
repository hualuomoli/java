package com.github.hualuomoli.commons.gps.rmc;

import java.util.Date;

import com.github.hualuomoli.commons.gps.GPS;

/**
 * GPRMC
 * @author hualuomoli
 *
 */
public class GPRMC extends GPS {

	private String id; // 消息ID
	private Date time; // UTC时间(hhmmss.ss)
	private boolean state; // 状态 (A=数据有效,V=数据无效)
	private double latitude; // 纬度(ddmm.mmmm)
	private boolean latitudeNorth; // N/S指示(N=北,S=南)
	private double longitude; // 经度(dddmm.mmmm)
	private boolean longitudeWest; // E/W指示(W=西,E=东)
	private double speed; // 地面速度(Knot(节))
	private double direction; // 方位(度)
	private Date date; // 日期(ddmmyy)
	private String magnetic; // 磁变量

	public GPRMC() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public boolean isLatitudeNorth() {
		return latitudeNorth;
	}

	public void setLatitudeNorth(boolean latitudeNorth) {
		this.latitudeNorth = latitudeNorth;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public boolean isLongitudeWest() {
		return longitudeWest;
	}

	public void setLongitudeWest(boolean longitudeWest) {
		this.longitudeWest = longitudeWest;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getDirection() {
		return direction;
	}

	public void setDirection(double direction) {
		this.direction = direction;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMagnetic() {
		return magnetic;
	}

	public void setMagnetic(String magnetic) {
		this.magnetic = magnetic;
	}

}
