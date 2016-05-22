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

	private boolean close; // 是否关闭

	private Socket socket;
	private SocketDealer dealer;

	private InputStream in = null;
	private OutputStream out = null;

	public SocketServerThread(Socket socket, SocketDealer dealer) {
		this(socket, dealer, 10 * 60); // 默认十分钟
	}

	public SocketServerThread(Socket socket, SocketDealer dealer, long timeout) {
		this.socket = socket;
		this.dealer = dealer;
		close = false;

		try {
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

				// 验证用户信息
				if (!dealer.isLogin()) {
					String flush = dealer.login(read(in, dealer.getInputCharset()));
					write(out, flush, dealer.getOutputCharset());
				} else {
					String flush = dealer.execute(read(in, dealer.getInputCharset()));
					write(out, flush, dealer.getOutputCharset());
				}

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
				e.printStackTrace();
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
				try {
					Thread.sleep(dealer.getWaitSeconds());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// 验证客户端是否已经断开 SocketOptions.SO_OOBINLINE
				socket.sendUrgentData(9);
			} else {
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
