package com.github.hualuomoli.commons.socket.dealer;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 处理人
 * @author hualuomoli
 *
 */
public interface SocketDealer {

	/**
	 * 如果没有输入,等待时间,单位为毫秒
	 * @return 等待时间,单位为毫秒
	 */
	long getWaitSeconds();

	/**
	 * 是否已经登录
	 * @return 是否已经登录
	 * @throws IOException 异常
	 */
	boolean isLogin() throws IOException;

	/**
	 * 获取输入流编码
	 * @return 编码集
	 */
	Charset getInputCharset();

	/**
	 * 登录
	 * @param in 输入流
	 * @param out 输出流
	 * @return 登录返回结果
	 * @throws IOException 异常
	 */
	String login(String input) throws IOException;

	/**
	 * 获取输出流编码
	 * @return 编码集
	 */
	Charset getOutputCharset();

	/**
	 * 执行
	 * @param in 输入流
	 * @param out 输出流
	 * @throws IOException 异常
	 */
	String execute(String input) throws IOException;

}
