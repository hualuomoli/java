package com.github.hualuomoli.plugin.ueditor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.lf5.util.StreamUtils;

public class Storage {

	// 保存
	public static void saveFile(InputStream inputStream, String realPath, String filename) throws IOException {
		OutputStream outputStream = new FileOutputStream(new File(realPath, filename));
		StreamUtils.copy(inputStream, outputStream);
		outputStream.flush();
		outputStream.close();
	}

	// 保存
	public static void saveFile(byte[] data, String realPath, String filename) throws IOException {
		FileUtils.writeByteArrayToFile(new File(realPath, filename), data);
	}

	// 保存
	public static void saveFile(String base64, String realPath, String filename) throws IOException {
		saveFile(decode(base64), realPath, filename);
	}

	// 解码
	public static byte[] decode(String base64) {
		return Base64.decodeBase64(base64);
	}

}
