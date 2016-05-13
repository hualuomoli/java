package com.github.hualuomoli.commons.util;

import java.io.IOException;
import java.net.SocketException;
import java.util.UUID;

import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.commons.util.FtpUtils.Result;

public class FtpUtilsTest {

	private static final Logger logger = LoggerFactory.getLogger(FtpUtils.class);

	@Test
	public void testDownload() throws SocketException, IOException {
		FTPClient ftpClient = null;

		ftpClient = FtpUtils.connect("192.168.1.182", 21, FtpUtils.MODE_TYPE_LOCAL_ACTIVE);
		// ftpClient = FtpUtils.connect("192.168.1.182", 21, FtpUtils.MODE_TYPE_REMOTE_PASSIVE);

		boolean success = FtpUtils.login(ftpClient, "8bmall", "wz12345679");
		logger.debug("login {}", success);
		if (!success) {
			logger.debug("can not login.");
			return;
		}
		String localPath = "E:/temp/download/8bmail";
		String uploadPath = "E:/github/demo";
		String remotePath = "/8bmall";
		String filename = "20160415";
		String folername = "views";
		String noAuthor = "/demo";
		Result result = null;
		// downloadFile
		// 1 下载的是文件
		success = FtpUtils.downloadFile(ftpClient, remotePath, localPath, filename);
		logger.debug("download file {}", success);
		// 2 下载的是目录
		success = FtpUtils.downloadFile(ftpClient, remotePath, localPath, folername);
		logger.debug("download folder {}", success);
		// 3 文件不存在
		success = FtpUtils.downloadFile(ftpClient, remotePath, localPath, this.random(remotePath));
		logger.debug("download not exists file {}", success);
		// 4 目录不存在
		success = FtpUtils.downloadFile(ftpClient, this.random(remotePath), localPath, folername);
		logger.debug("download not exists folder {}", success);
		// 5 权限不足
		success = FtpUtils.downloadFile(ftpClient, noAuthor, localPath, filename);
		logger.debug("download no author folder {}", success);

		// downloadFolder
		// 1 下载的是目录
		result = FtpUtils.downloadFolder(ftpClient, remotePath, localPath);
		logger.debug("download folder. success {}, error {}", result.getSuccess(), result.getError());
		// 2 下载的是文件
		result = FtpUtils.downloadFolder(ftpClient, remotePath + "/" + filename, localPath);
		logger.debug("download file. success {}, error {}", result.getSuccess(), result.getError());
		// 3 目录不存在
		result = FtpUtils.downloadFolder(ftpClient, this.random(remotePath), localPath);
		logger.debug("download not exists folder. success {}, error {}", result.getSuccess(), result.getError());
		// 5 权限不足
		result = FtpUtils.downloadFolder(ftpClient, noAuthor, localPath);
		logger.debug("download no author folder. success {}, error {}", result.getSuccess(), result.getError());

		// uploadFile
		success = FtpUtils.uploadFile(ftpClient, remotePath, uploadPath, "readme.md");
		logger.debug("upload file {}", success);
		// logger.debug("update {}", success);

		FtpUtils.logout(ftpClient);
		FtpUtils.disconnect(ftpClient);
	}

	private String random(String path) {
		return path + "/" + UUID.randomUUID().toString().replaceAll("[-]", "");
	}

}
