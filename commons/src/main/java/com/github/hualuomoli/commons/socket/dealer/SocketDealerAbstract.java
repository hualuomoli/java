package com.github.hualuomoli.commons.socket.dealer;

/**
 * 处理人
 * @author hualuomoli
 *
 */
public abstract class SocketDealerAbstract implements SocketDealer {

	@Override
	public long waitSeconds() {
		return 100; // 100 ms
	}

	@Override
	public long timeout() {
		return 10 * 60 * 1000; // 10 m
	}

	@Override
	public String quit() {
		return "quit";
	}

	@Override
	public String heart() {
		return "heart";
	}

	@Override
	public String loginSuccessMesssage() {
		return "login success";
	}

	@Override
	public String loginErrorMesssage() {
		return "invalid username or password";
	}

}
