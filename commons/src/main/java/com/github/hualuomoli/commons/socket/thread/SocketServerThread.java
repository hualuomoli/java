package com.github.hualuomoli.commons.socket.thread;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.commons.socket.dealer.SocketDealer;

/**
 * socket线程
 * @author hualuomoli
 *
 */
public class SocketServerThread implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(SocketServerThread.class);

	private Socket socket;
	private SocketDealer dealer;

	private boolean login; // 是否登录
	private long timer; // 计时器

	private InputStream in = null;
	private OutputStream out = null;

	public SocketServerThread(Socket socket, SocketDealer dealer) {
		this.socket = socket;
		this.dealer = dealer;

		login = false;
		timer = 0;

		try {
			// 输入输出流
			in = socket.getInputStream();
			out = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {

			// 使用循环的方式，不停的与客户端交互会话
			while (true) {

				// 读取数据
				String input = this.read(in, dealer.charset());
				// 返回数据
				String flush;

				if (StringUtils.equals(input, dealer.quit())) {
					// 是否退出
					if (logger.isInfoEnabled()) {
						logger.info("close socket.");
					}
					// 登出
					dealer.logout();
					break;
				} else if (StringUtils.equals(input, dealer.heart())) {
					// 是否心跳检测
					if (logger.isInfoEnabled()) {
						logger.info("heart checker.");
					}
					continue;
				} else if (!login) {
					// 验证用户信息
					boolean success = dealer.login(input);
					if (success) {
						login = true;
						flush = dealer.loginSuccessMesssage();
					} else {
						flush = dealer.loginErrorMesssage();
					}
				} else {
					// 数据处理
					flush = dealer.execute(input);
				}
				// 输出
				write(out, flush, dealer.charset());
			}
		} catch (Exception e) {
			if (logger.isWarnEnabled()) {
				logger.warn("{}", e);
			}
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				// e.printStackTrace();
			}
		}
	}

	/**
	 * 读取数据
	 * @param in 输入流
	 * @param inputCharset 输入数据编码
	 * @return 输入数据
	 * @throws IOException 读取数据错误
	 */
	private String read(InputStream in, Charset inputCharset) throws IOException {
		int available;
		while (true) {
			available = in.available();

			if (available == 0) {
				// 没有输入
				// 休眠等待
				try {
					Thread.sleep(dealer.waitSeconds());
					timer += dealer.waitSeconds();
				} catch (InterruptedException e) {
				}
				// 判断是否超时
				if (timer >= dealer.timeout()) {
					if (logger.isInfoEnabled()) {
						logger.info("timeout.");
					}
					return dealer.quit();
				}

				// 客户端断开链接
				// try {
				// socket.sendUrgentData(0xFF);
				// } catch (Exception e) {
				// e.printStackTrace();
				// close = true;
				// if (logger.isInfoEnabled()) {
				// logger.info("client close.");
				// }
				// return quit;
				// }
			} else {
				// 有输入
				timer = 0;
				break;
			}
		}
		byte[] bytes = new byte[available];
		in.read(bytes);

		String input;

		if (inputCharset == null) {
			input = new String(bytes);
		} else {
			input = new String(bytes, inputCharset);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("read data: {}", input);
		}
		return input;
	}

	/**
	 * 写数据
	 * @param out 输出流
	 * @param flush 输出数据
	 * @param outputCharset 输出数据编码
	 * @throws IOException 异常
	 */
	private void write(OutputStream out, String flush, Charset outputCharset) throws IOException {
		if (StringUtils.isBlank(flush)) {
			return;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("flush data: {}", flush);
		}
		byte[] bytes;
		if (outputCharset == null) {
			bytes = flush.getBytes();
		} else {
			bytes = flush.getBytes(outputCharset);
		}
		out.write(bytes);
		out.flush();
	}

}
