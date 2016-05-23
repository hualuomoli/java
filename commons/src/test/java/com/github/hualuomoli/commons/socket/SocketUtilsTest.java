package com.github.hualuomoli.commons.socket;

import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.github.hualuomoli.commons.socket.dealer.SocketDealerAbstract;
import com.github.hualuomoli.commons.util.CharsetUtils;

public class SocketUtilsTest {

	@Test
	public void testOpen() {
		SocketDealerAbstract dealer = new SocketDealerAbstract() {

			@Override
			public long waitSeconds() {
				return (long) (1000 * 0.1);
			}

			@Override
			public long timeout() {
				return 1000 * 10;
			}

			@Override
			public Charset charset() {
				return CharsetUtils.GBK;
			}

			@Override
			public String loginSuccessMesssage() {
				return "login success";
			}

			@Override
			public String loginErrorMesssage() {
				return "登录失败";
			}

			@Override
			public boolean login(String input) {
				return StringUtils.equalsIgnoreCase(input, "admin:admin");
			}

			@Override
			public String execute(String input) {
				String outputData = "获取客户端数据: " + input;
				return outputData;
			}

		};

		SocketUtils.open(3000, dealer);

	}

}
