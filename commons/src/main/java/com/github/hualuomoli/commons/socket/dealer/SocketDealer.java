package com.github.hualuomoli.commons.socket.dealer;

import java.nio.charset.Charset;

import com.github.hualuomoli.commons.socket.exception.SocketException;

/**
 * 处理人
 * @author hualuomoli
 *
 */
public interface SocketDealer {

	/**
	 * 心跳检测时长
	 * @return 心跳检测时长
	 */
	long heartSeconds();

	/**
	 * 超时时长
	 * @return 超时时长
	 */
	long timeout();

	/**
	 * 退出字符串
	 * @return 退出字符串
	 */
	String quit();

	/**
	 * 获取输入流编码
	 * @return 编码集
	 */
	Charset charset();

	/**
	 * 登录
	 * @param input 输入
	 * @return 是否登录成功
	 * @throws SocketException 异常
	 */
	boolean login(String input);

	/**
	 * 登录成功的消息
	 * @return 登录成功的消息
	 */
	String loginSuccessMesssage();

	/**
	 * 登录失败的消息
	 * @return 登录失败的消息
	 */
	String loginErrorMesssage();

	/**
	 * 执行
	 * @param input 输入
	 * @return 执行返回结果
	 * @throws SocketException 异常
	 */
	String execute(String input);

}
