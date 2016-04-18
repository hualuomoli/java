package com.github.hualuomoli.app.push;

import com.github.hualuomoli.app.exception.AppException;
import com.github.hualuomoli.app.push.entity.Push;

/**
 * APP 消息推送
 * 
 * @author hualuomoli
 *
 */
public interface Sender {

	/**
	 * 发送通知
	 * @param push 通知
	 * @throws AppException 发送异常
	 */
	void send(Push push) throws AppException;

}
