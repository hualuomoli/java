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

	private static final long DEFAULT_HEART_SECONDS = 100; // 默认100毫秒
	private static final long DEFAULT_TIME_OUT = 1000 * 60 * 10; // 默认十分钟
	private static final String DEFAULT_QUIT = "quit";
	private static final String DEFAULT_LOGIN_SUCCESS_MESSAGE = "登录成功";
	private static final String DEFAULT_LOGIN_ERROR_MESSAGE = "用户名或密码错误";

	private boolean close; // 是否关闭
	private long heartSeconds; // 心跳检测时长(单位毫秒)
	private long timeout; // 超时时长(单位毫秒)
	private boolean login; // 是否登录
	private long timer; // 计时器
	private String quit;// 退出字符串
	private String loginSuccessMessage; // 登录成功的消息
	private String loginErrorMessage; // 登录失败的消息

	private Socket socket;
	private SocketDealer dealer;

	private InputStream in = null;
	private OutputStream out = null;

	public SocketServerThread(Socket socket, SocketDealer dealer) {
		this.socket = socket;
		this.dealer = dealer;
		close = false;

		try {
			// 心跳,默认100毫秒
			long heartSeconds = dealer.heartSeconds();
			this.heartSeconds = heartSeconds == 0 ? DEFAULT_HEART_SECONDS : heartSeconds;
			// 超时,默认30分钟
			long timeout = dealer.timeout();
			this.timeout = timeout == 0 ? DEFAULT_TIME_OUT : timeout;
			// 退出,默认 quit
			String quit = dealer.quit();
			this.quit = quit == null ? DEFAULT_QUIT : quit;
			// 消息
			String loginSuccessMessage = dealer.loginSuccessMesssage();
			this.loginSuccessMessage = loginSuccessMessage == null ? DEFAULT_LOGIN_SUCCESS_MESSAGE : loginSuccessMessage;
			String loginErrorMessage = dealer.loginErrorMesssage();
			this.loginErrorMessage = loginErrorMessage == null ? DEFAULT_LOGIN_ERROR_MESSAGE : loginErrorMessage;
			// 计时器
			this.timer = 0;
			// 输入输出流
			in = socket.getInputStream();
			out = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setClose(boolean close) {
		this.close = close;
	}

	@Override
	public void run() {
		try {

			// 使用循环的方式，不停的与客户端交互会话
			while (!close) {

				// 读取数据
				String input = this.read(in, dealer.charset());
				// 返回数据
				String flush;

				// 是否退出
				if (StringUtils.equals(input, quit)) {
					if (logger.isInfoEnabled()) {
						logger.info("close socket.");
					}
					break;
				}
				// 验证用户信息
				if (!login) {
					boolean success = dealer.login(input);
					if (success) {
						login = true;
						flush = loginSuccessMessage;
					} else {
						flush = loginErrorMessage;
					}
				} else {
					flush = dealer.execute(input);
				}
				// 输出
				write(out, flush, dealer.charset());
			}
		} catch (Exception e) {
			e.printStackTrace();
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
					Thread.sleep(heartSeconds);
					timer += heartSeconds;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// 判断是否超时
				if (timer >= timeout) {
					close = true;
					if (logger.isInfoEnabled()) {
						logger.info("timeout.");
					}
					return quit;
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
