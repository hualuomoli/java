package com.github.hualuomoli.commons.socket.dealer;

import java.nio.charset.Charset;

/**
 * 处理人
 * @author hualuomoli
 *
 */
public abstract class SocketDealerAbstract implements SocketDealer {

	public static final int DEFAULT_WAIT_SECIBDS = 100; // 0.1s

	private int waitSeconds = DEFAULT_WAIT_SECIBDS;

	@Override
	public long getWaitSeconds() {
		return waitSeconds;
	}

	public void setWaitSeconds(int waitSeconds) {
		this.waitSeconds = waitSeconds;
	}

	private Charset inputCharset; // 输入编码
	private Charset outputCharset; // 输出编码

	public Charset getInputCharset() {
		return inputCharset;
	}

	public void setInputCharset(Charset inputCharset) {
		this.inputCharset = inputCharset;
	}

	public Charset getOutputCharset() {
		return outputCharset;
	}

	public void setOutputCharset(Charset outputCharset) {
		this.outputCharset = outputCharset;
	}

	public void setInputCharset(String inputCharset) {
		this.inputCharset = Charset.forName(inputCharset);
	}

	public void setOutputCharset(String outputCharset) {
		this.outputCharset = Charset.forName(outputCharset);
	}

}
