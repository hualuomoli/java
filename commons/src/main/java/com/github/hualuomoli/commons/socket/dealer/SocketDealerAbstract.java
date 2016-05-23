package com.github.hualuomoli.commons.socket.dealer;

import java.nio.charset.Charset;

/**
 * 处理人
 * @author hualuomoli
 *
 */
public abstract class SocketDealerAbstract implements SocketDealer {

	private long waitSeconds;
	private long timeout;
	private Charset charset;
	private String quit;
	private String heart;
	private String loginSuccessMesssage;
	private String loginErrorMesssage;

	@Override
	public long waitSeconds() {
		return waitSeconds;
	}

	@Override
	public long timeout() {
		return timeout;
	}

	@Override
	public Charset charset() {
		return charset;
	}

	@Override
	public String quit() {
		return quit;
	}

	@Override
	public String heart() {
		return heart;
	}

	@Override
	public String loginSuccessMesssage() {
		return loginSuccessMesssage;
	}

	@Override
	public String loginErrorMesssage() {
		return loginErrorMesssage;
	}

	public void setWaitSeconds(long waitSeconds) {
		this.waitSeconds = waitSeconds;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	public void setCharsetName(String charsetName) {
		this.charset = Charset.forName(charsetName);
	}

	public void setQuit(String quit) {
		this.quit = quit;
	}

	public void setHeart(String heart) {
		this.heart = heart;
	}

	public void setLoginSuccessMesssage(String loginSuccessMesssage) {
		this.loginSuccessMesssage = loginSuccessMesssage;
	}

	public void setLoginErrorMesssage(String loginErrorMesssage) {
		this.loginErrorMesssage = loginErrorMesssage;
	}

}
