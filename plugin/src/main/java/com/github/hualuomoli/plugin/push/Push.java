package com.github.hualuomoli.plugin.push;

import java.util.Map;

// 推送
public interface Push {

	/**
	 * 通知所有的手机
	 * @param type 数据的类型
	 * @param alert 提示信息
	 * @param title 标题
	 * @param extras 扩展信息
	 */
	void pushAllNotification(String type, String alert, String title, Map<String, String> extras);

}
