package com.github.hualuomoli.commons.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.commons.socket.dealer.SocketDealer;
import com.github.hualuomoli.commons.socket.thread.SocketServerThread;
import com.google.common.collect.Maps;

/**
 * 线程工具类
 * @author hualuomoli
 *
 */
public class SocketUtils {

	private static final Logger logger = LoggerFactory.getLogger(SocketUtils.class);
	private static final Map<Integer, ServerSocket> serverSockets = Maps.newHashMap();

	/**
	 * 开启
	 * @param port 端口
	 * @param dealer 处理者
	 */
	public static void open(final int port, final SocketDealer dealer) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// 使用新线程开启,不然会阻塞
				doOpen(port, dealer);
			}
		}).start();
	}

	/**
	 * 开启
	 * @param port 端口
	 * @param dealer 处理者
	 */
	private static void doOpen(int port, SocketDealer dealer) {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			serverSockets.put(port, serverSocket);
			if (logger.isInfoEnabled()) {
				logger.info("create server {} success.", port);
			}

			// 使用循环方式一直等待客户端的连接
			while (true) {
				Socket accept = serverSocket.accept();
				if (logger.isInfoEnabled()) {
					logger.debug("accept client.{}", accept);
				}
				// 启动一个新的线程，接管与当前客户端的交互会话
				SocketServerThread portServer = new SocketServerThread(accept, dealer);
				new Thread(portServer).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (serverSocket != null) {
					serverSocket.close();
				}
				if (logger.isInfoEnabled()) {
					logger.info("close server {} ", port);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 开启
	 * @param port 端口
	 * @param dealer 处理者
	 */
	public static void close(int port) {
		if (!serverSockets.containsKey(port)) {
			return;
		}
		ServerSocket serverSocket = serverSockets.get(port);
		if (serverSocket == null || serverSocket.isClosed()) {
			return;
		}
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
