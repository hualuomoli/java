package com.github.hualuomoli.extend.notice;

/**
 * 通知器
 * @author hulauomoli
 *
 */
public interface Notifer<T extends Noticer> {

	// 是否支持
	boolean support(Class<?> cls);

	// 通知
	void notice(T noticer);

}
