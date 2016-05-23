package com.github.hualuomoli.commons.socket.dealer;

import java.nio.charset.Charset;

import com.github.hualuomoli.commons.util.CharsetUtils;

/**
 * 处理人
 * @author hualuomoli
 *
 */
public abstract class SocketDealerAbstract implements SocketDealer {

	@Override
	public long heartSeconds() {
		return 0;
	}

	@Override
	public long timeout() {
		return 0;
	}

	@Override
	public String quit() {
		return null;
	}

	@Override
	public String loginSuccessMesssage() {
		return null;
	}

	@Override
	public String loginErrorMesssage() {
		return null;
	}

	@Override
	public Charset charset() {
		return CharsetUtils.UTF8;
	}

}
