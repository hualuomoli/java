package com.github.hualuomoli.plugin.roll;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import org.junit.AfterClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.hualuomoli.plugin.roll.service.DefaultRollService;
import com.github.hualuomoli.test.AbstractContextServiceTest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按字母排序
public class DefaultRollServiceTest extends AbstractContextServiceTest {

	@Autowired
	private DefaultRollService defaultRollService;

	@Test
	public void test01String() {
		for (int i = 0; i < 10; i++) {
			defaultRollService.push("1234", RollExecutorService.class, "1s,2s,3s,5s");
			defaultRollService.push("5678", RollExecutorInstance.class, "1s,2s,3s,5s");
		}
	}

	@Test
	public void test02ByteArray() throws UnsupportedEncodingException {
		for (int i = 0; i < 10; i++) {
			defaultRollService.push("测试1245haha家里的附件是".getBytes("UTF-8"), RollExecutorService.class, "1s,2s,3s,5s");
			defaultRollService.push("加拉斯加福禄寿werojdflk收到了附近洒到了".getBytes("UTF-8"), RollExecutorInstance.class, "1s,2s,3s,5s");
		}
	}

	@Test
	public void test03Serializable() {
		for (int i = 0; i < 10; i++) {
			defaultRollService.push(new User("hualuomoli", "花落莫离"), RollExecutorService2.class, "1s,2s,3s,5s");
			defaultRollService.push(new User("manager", "管理员"), RollExecutorInstance2.class, "1s,2s,3s,5s");
		}
	}

	@Test
	public void test04Serializable() {
		Calendar calendar = Calendar.getInstance();
		for (int i = 0; i < 10; i++) {
			calendar.add(Calendar.SECOND, 20);
			defaultRollService.push(new Demo(20, calendar.getTime()), RollExecutorService3.class, "1s,2s,3s,5s");
			calendar.add(Calendar.SECOND, 50);
			defaultRollService.push(new Demo(50, calendar.getTime()), RollExecutorInstance3.class, "1s,2s,3s,5s");
		}
	}

	@SuppressWarnings("serial")
	public static class User implements Serializable {

		private String username;
		private String nickname;

		public User(String username, String nickname) {
			this.username = username;
			this.nickname = nickname;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

	}

	@AfterClass
	public static void afterClass() throws InterruptedException {
		Thread.sleep(1000 * 10);
	}

}
