package com.github.hualuomoli.commons.util;

import java.util.Date;

import org.junit.Test;

public class DateUtilsTest {

	@Test
	public void test() {
		System.out.println(DateUtils.sdfSlashYmd.format(new Date()));
		System.out.println(DateUtils.sdfSlashYmdHms.format(new Date()));
		System.out.println(DateUtils.sdfBarYmd.format(new Date()));
		System.out.println(DateUtils.sdfBarYmdHms.format(new Date()));
		System.out.println(DateUtils.sdfYmd.format(new Date()));
		System.out.println(DateUtils.sdfYmdHms.format(new Date()));
		System.out.println(DateUtils.sdfColonHms.format(new Date()));
		System.out.println(DateUtils.sdfHms.format(new Date()));
	}

}
