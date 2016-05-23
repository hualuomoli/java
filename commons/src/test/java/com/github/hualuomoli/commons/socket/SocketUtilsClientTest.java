package com.github.hualuomoli.commons.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.github.hualuomoli.commons.util.CharsetUtils;

public class SocketUtilsClientTest {

	@Test
	public void test() throws InterruptedException {
		Socket socket = null;
		System.out.println("ClientSocket Begin........");
		try {
			for (int i = 0; i < 5; i++) {
				socket = new Socket("localhost", 3000);
				new Thread(new ClientThread(socket, i + 1), "ClientThread " + i).start();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		Thread.sleep(1000 * 60 * 60);

	}

}

/**
 * 客户端线程
 * @author JCC
 *
 */
class ClientThread implements Runnable {
	Socket socket = null;
	int id = 0;

	public ClientThread(Socket socket, int id) {
		this.socket = socket;
		this.id = id;
	}

	@Override
	public void run() {
		OutputStream out = null;
		InputStream in = null;
		System.out.println("Begin to Chat to server...");
		try {
			out = socket.getOutputStream();
			in = socket.getInputStream();

			System.out.println("login");
			write(out, "admin:admin", CharsetUtils.GBK);
			System.out.println("get login data");
			String message = read(in, CharsetUtils.GBK);
			System.out.println(message);

			// 循环发送与服务端不停的交互数据
			while (true) {
				try {
					int seconds = RandomUtils.nextInt(3, 6);
					Thread.sleep(1000 * seconds);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				String data = "客户端" + id + "发送数据";
				int i = RandomUtils.nextInt(1, 100);
				if (i >= 90) {
					data = "quit";
					System.out.println(id + " quit");
				} else if (i > 80) {
					write(out, "heart", CharsetUtils.GBK);
					continue;
				}
				write(out, data, CharsetUtils.GBK);
				System.out.println("begin read message from server.");
				message = read(in, CharsetUtils.GBK);
				System.out.println(message);
			}

		} catch (IOException e) {
			System.out.println(id + "server timeout");
		} finally {
			try {
				in.close();
				out.close();
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
		int available = in.available();
		byte[] bytes = new byte[available];
		in.read(bytes);

		String input;

		if (inputCharset == null) {
			input = new String(bytes);
		} else {
			input = new String(bytes, inputCharset);
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
