package com.github.hualuomoli.plugin.app.push.entity;

import java.util.Map;

/**
 * 通知
 * @author admin
 *
 */
public class Notification extends Push {

	private static final long serialVersionUID = -7471583621415290086L;

	private String alert; // 弹出信息
	private String title; // 提示信息
	private Map<String, String> extras; // 扩展信息

	public Notification() {
	}

	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Map<String, String> getExtras() {
		return extras;
	}

	public void setExtras(Map<String, String> extras) {
		this.extras = extras;
	}

}
