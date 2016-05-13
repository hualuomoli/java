package com.github.hualuomoli.commons.socket;

import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.github.hualuomoli.commons.socket.dealer.SocketDealerAbstract;

public class SocketUtilsTest {

	@Test
	public void testOpen() {
		SocketDealerAbstract dealer = new SocketDealerAbstract() {

			@Override
			public Charset charset() {
				return com.github.hualuomoli.commons.constant.Charset.UTF8;
			}

			@Override
			public boolean login(String input) {
				boolean success = StringUtils.equals(input, "system:admin");
				if (success) {
					// do something
					System.out.println("login");
				}
				return success;
			}

			@Override
			public String execute(String input) {
				System.out.println(input);
				return "deal success";
			}

			@Override
			public void logout() {
				System.out.println("logout");
			}

		};

		SocketUtils.open(3000, dealer);

		// 测试
		try {
			Thread.sleep(10000 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
