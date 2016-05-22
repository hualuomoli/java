package com.github.hualuomoli.commons.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.commons.socket.dealer.SocketDealer;
import com.github.hualuomoli.commons.socket.thread.SocketServerThread;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * 线程工具类
 * @author hualuomoli
 *
 */
public class SocketUtils {

	private static final Logger logger = LoggerFactory.getLogger(SocketUtils.class);

	private static final Map<Integer, Set<SocketServerThread>> portServers = Maps.newHashMap();

	/**
	 * 开启
	 * @param port 端口
	 * @param dealer 处理者
	 */
	public static void open(int port, SocketDealer dealer) {
		ServerSocket serverSocket = null;
		Set<SocketServerThread> servers = Sets.newHashSet();
		try {
			serverSocket = new ServerSocket(port);
			logger.info("create server success.");
			portServers.put(port, servers);

			// 使用循环方式一直等待客户端的连接
			while (true) {
				Socket accept = serverSocket.accept();
				logger.debug("accept client.{}", accept);
				// 启动一个新的线程，接管与当前客户端的交互会话
				SocketServerThread portServer = new SocketServerThread(accept, dealer);
				servers.add(portServer);
				new Thread(portServer).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (serverSocket != null) {
					serverSocket.close();
				}
				logger.info("close socket");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
