package com.github.hualuomoli.plugin.zxing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.github.hualuomoli.plugin.qrcode.QRCode;
import com.github.hualuomoli.plugin.qrcode.exception.QRCodeException;

public class ZxingQRCodeTest {

	private static QRCode rRCode;
	String content = "{\"uuid\":\"fbb37e0f8901409cb99454bc7273aada\",\"time\":\"2016-05-05 16:04:36\"}";

	@BeforeClass
	public static void beforeClass() {
		rRCode = new ZxingQRCode();
	}

	@Test
	public void testWriteFileStringIntIntString() throws QRCodeException {
		rRCode.write("E:/output/qrcode", "demo1.jpg", 200, 200, content);
		rRCode.write("E:/output/qrcode/demo2.jpg", 300, 300, content);
		rRCode.write(new File("E:/output/qrcode/demo3.gif"), 400, 400, content);
		rRCode.write(System.out, 400, 400, content);
	}

	@Test
	public void testReadFile() throws QRCodeException, FileNotFoundException {
		String data = null;
		data = rRCode.read("E:/output/qrcode", "demo1.jpg");
		System.out.println(data);
		data = rRCode.read("E:/output/qrcode/demo2.jpg");
		System.out.println(data);
		data = rRCode.read(new File("E:/output/qrcode/demo3.gif"));
		System.out.println(data);
		data = rRCode.read(new FileInputStream(new File("E:/output/qrcode/demo3.gif")));
		System.out.println(data);

	}

}
