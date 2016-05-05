package com.github.hualuomoli.plugin.qrcode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.commons.lang3.StringUtils;

import com.github.hualuomoli.plugin.qrcode.exception.QRCodeException;

/**
 * 实现基本的操作
 * @author hualuomoli
 *
 */
public abstract class QRCodeAbstract implements QRCode {

	@Override
	public void write(String filename, int width, int height, String content) throws QRCodeException {
		if (StringUtils.isBlank(filename)) {
			throw new QRCodeException("please set filename.");
		}
		this.write(new File(filename), width, height, content);
	}

	@Override
	public void write(String filepath, String filename, int width, int height, String content) throws QRCodeException {
		if (StringUtils.isBlank(filepath) || StringUtils.isBlank(filename)) {
			throw new QRCodeException("please set filepath and filename.");
		}
		this.write(new File(filepath, filename), width, height, content);
	}

	@Override
	public void write(File file, int width, int height, String content) throws QRCodeException {
		try {
			if (file.exists()) {
				file.delete();
			} else {
				this.createPath(file);
			}
			this.write(new FileOutputStream(file), width, height, content);
		} catch (Exception e) {
			throw new QRCodeException(e);
		}
	}

	// create file's path
	protected void createPath(File file) {
		File dir = file.getParentFile();
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	@Override
	public String read(String filename) throws QRCodeException {
		if (StringUtils.isBlank(filename)) {
			throw new QRCodeException("please set filename.");
		}
		return this.read(new File(filename));
	}

	@Override
	public String read(String filepath, String filename) throws QRCodeException {
		if (StringUtils.isBlank(filepath) || StringUtils.isBlank(filename)) {
			throw new QRCodeException("please set filepath and filename.");
		}
		return this.read(new File(filepath, filename));
	}

	@Override
	public String read(File file) throws QRCodeException {
		if (!file.exists()) {
			throw new QRCodeException("please use valid file");
		}
		try {
			return this.read(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			throw new QRCodeException(e);
		}
	}

}
