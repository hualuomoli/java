package com.github.hualuomoli.commons.ftp.entity;

import java.io.Serializable;

/**
 * 上传下载(目录)结果
 * @author hualuomoli
 *
 */
public class Result implements Serializable {

	private static final long serialVersionUID = -6399759364837426878L;

	private int success;
	private int error;

	public Result() {
	}

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

}
