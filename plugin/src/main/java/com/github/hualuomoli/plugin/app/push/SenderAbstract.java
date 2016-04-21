package com.github.hualuomoli.plugin.app.push;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.plugin.app.exception.AppException;
import com.github.hualuomoli.plugin.app.push.entity.Message;
import com.github.hualuomoli.plugin.app.push.entity.Notification;
import com.github.hualuomoli.plugin.app.push.entity.Push;
import com.github.hualuomoli.plugin.app.push.entity.RichTxt;

/**
 * APP 消息推送
 * 
 * @author hualuomoli
 *
 */
public abstract class SenderAbstract implements Sender {

	private static final Logger logger = LoggerFactory.getLogger(Sender.class);

	@Override
	public void send(Push push) throws AppException {
		if (push instanceof Notification) {
			this.send((Notification) push);
		} else if (push instanceof Message) {
			this.send((Message) push);
		} else if (push instanceof RichTxt) {
			this.send((RichTxt) push);
		} else {
			logger.warn("can not support type {}", push.getClass().getName());
			throw new AppException("can not support type " + push.getClass().getName());
		}
	}

	/**
	 * 发送通知
	 * @param push 通知
	 * @throws AppException 发送异常
	 */
	public abstract void send(Notification push) throws AppException;

	/**
	 * 发送自定义消息
	 * @param push 自定义消息
	 * @throws AppException 发送异常
	 */
	public abstract void send(Message push) throws AppException;

	/**
	 * 发送富文本
	 * @param push 富文本
	 * @throws AppException 发送异常
	 */
	public abstract void send(RichTxt push) throws AppException;
}
