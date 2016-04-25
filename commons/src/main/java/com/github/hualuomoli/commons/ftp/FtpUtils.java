package com.github.hualuomoli.commons.ftp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.commons.ftp.entity.Result;

/**
 * FTP工具
 * @author hualuomoli
 *
 */
public class FtpUtils {

	private static final Logger logger = LoggerFactory.getLogger(FtpUtils.class);

	public static final String DEFAULT_ENCODING = "UTF-8";
	public static final int DEFAULT_PORT = 21;

	public static final int MODE_TYPE_LOCAL_PASSIVE = 1; // 本地被动模式
	public static final int MODE_TYPE_LOCAL_ACTIVE = 2; // 本地主动模式
	public static final int MODE_TYPE_REMOTE_PASSIVE = 3; // 远程被动模式

	/**
	 * 连接,默认端口21,默认使用本地被动模式,UTF-8编码
	 * @param hostname 主机
	 * @return FTPClient
	 */
	public static FTPClient connect(String hostname) {
		return connect(hostname, DEFAULT_PORT);
	}

	/**
	 * 连接,默认使用本地被动模式,UTF-8编码
	 * @param hostname 主机
	 * @param port 端口
	 * @return FTPClient
	 */
	public static FTPClient connect(String hostname, int port) {
		return connect(hostname, port, MODE_TYPE_LOCAL_PASSIVE, DEFAULT_ENCODING);
	}

	/**
	 * 连接
	 * @param hostname 主机
	 * @param port 端口
	 * @param mode 传输模式 
	 * 		#MODE_TYPE_LOCAL_PASSIVE 
	 * 		#MODE_TYPE_LOCAL_ACTIVE 
	 * 		#MODE_TYPE_REMOTE_PASSIVE
	 * @param encoding 编码
	 * @return FTPClient
	 */
	public static FTPClient connect(String hostname, int port, int mode) {
		return connect(hostname, port, mode, DEFAULT_ENCODING);
	}

	/**
	 * 连接
	 * @param hostname 主机
	 * @param port 端口
	 * @param mode 传输模式
	 * 		#MODE_TYPE_LOCAL_PASSIVE 
	 * 		#MODE_TYPE_LOCAL_ACTIVE 
	 * 		#MODE_TYPE_REMOTE_PASSIVE
	 * @param encoding 编码
	 * @return FTPClient
	 */
	public static FTPClient connect(String hostname, int port, int mode, String encoding) {

		FTPClient ftpClient = null;

		try {
			ftpClient = new FTPClient();
			ftpClient.setControlEncoding(encoding);
			// 连接
			ftpClient.connect(hostname, port);

			// 设置文件传输类型为二进制
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			switch (mode) {
			case MODE_TYPE_LOCAL_PASSIVE:
				// 本地被动
				ftpClient.enterLocalPassiveMode();
				break;
			case MODE_TYPE_LOCAL_ACTIVE:
				// 本地主动
				ftpClient.enterLocalActiveMode();
				break;
			case MODE_TYPE_REMOTE_PASSIVE:
				// 远程被动
				ftpClient.enterRemotePassiveMode();
				break;
			default:
				throw new IOException("can not support mode type " + mode);
			}

			return ftpClient;
		} catch (Exception e) {
			if (ftpClient != null && ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return null;

	}

	/**
	 * 关闭连接
	 * @param ftpClient 连接
	 */
	public static void disconnect(FTPClient ftpClient) {
		if (ftpClient == null) {
			return;
		}
		if (!ftpClient.isConnected()) {
			return;
		}
		try {
			ftpClient.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 登陆
	 * @param ftpClient 连接
	 * @param username 用户名
	 * @param password 密码
	 * @return 登陆是否成功
	 */
	public static boolean login(FTPClient ftpClient, String username, String password) {
		try {
			// 登录
			ftpClient.login(username, password);

			return isLogin(ftpClient);

		} catch (Exception e) {
		}
		return false;

	}

	public static boolean isLogin(FTPClient ftpClient) {
		// 获取ftp登录应答代码
		int reply = ftpClient.getReplyCode();
		// 验证是否登陆成功
		return FTPReply.isPositiveCompletion(reply);
	}

	/**
	 * 登出
	 * @param ftpClient 连接
	 */
	public static void logout(FTPClient ftpClient) {
		if (ftpClient == null) {
			return;
		}
		if (isLogin(ftpClient)) {
			try {
				ftpClient.logout();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 下载文件
	 * @param ftpClient 连接
	 * @param remotePath 远程路径
	 * @param localPath 本地路径
	 * @param filename 文件名
	 */
	public static boolean downloadFile(FTPClient ftpClient, String remotePath, String localPath, String filename) {
		return downloadFile(ftpClient, remotePath, localPath, filename, DEFAULT_ENCODING);
	}

	/**
	 * 下载文件
	 * @param ftpClient 连接
	 * @param remotePath 远程路径
	 * @param localPath 本地路径
	 * @param filename 文件名
	 * @param encoding 编码
	 */
	public static boolean downloadFile(FTPClient ftpClient, String remotePath, String localPath, String filename, String encoding) {
		createFilepath(localPath);
		try {
			// 转移到FTP服务器目录至指定的目录下
			boolean ok = ftpClient.changeWorkingDirectory(new String(remotePath.getBytes(encoding), "iso-8859-1"));
			if (!ok) {
				logger.error("can not change working directory {}", remotePath);
				return false;
			}
			// 获取文件列表
			FTPFile[] fs = ftpClient.listFiles();
			for (FTPFile ff : fs) {
				if (ff.getName().equals(filename)) {
					if (ff.isFile()) {
						File localFile = new File(localPath, filename);
						OutputStream is = new FileOutputStream(localFile);
						ok = ftpClient.retrieveFile(ff.getName(), is);
						is.close();
						return ok;
					} else if (ff.isDirectory()) {
						logger.error("{} is folder.", remotePath + "/" + filename);
						return false;
					}
				}
			}
			logger.error("{} is not exists", remotePath + "/" + filename);
		} catch (Exception e) {
			logger.error("download file error. {}", e);
		}
		return false;

	}

	/**
	 * 下载目录
	 * @param ftpClient 连接
	 * @param remotePath 远程路径
	 * @param localPath 本地路径
	 */
	public static Result downloadFolder(FTPClient ftpClient, String remotePath, String localPath) {
		return downloadFolder(ftpClient, remotePath, localPath, DEFAULT_ENCODING);
	}

	/**
	 * 下载目录
	 * @param ftpClient 连接
	 * @param remotePath 远程路径
	 * @param localPath 本地路径
	 * @param encoding 文件名编码
	 */
	public static Result downloadFolder(FTPClient ftpClient, String remotePath, String localPath, String encoding) {
		createFilepath(localPath);
		Result result = new Result();
		int success = 0;
		int error = 0;

		try {

			// 转移到FTP服务器目录至指定的目录下
			boolean ok = ftpClient.changeWorkingDirectory(new String(remotePath.getBytes(encoding), "ISO-8859-1"));
			if (!ok) {
				logger.error("can not change working directory {}", remotePath);
				return result;
			}
			// 获取文件列表
			FTPFile[] fs = ftpClient.listFiles();
			for (FTPFile ff : fs) {
				String name = ff.getName();
				if (ff.isFile()) {
					ok = downloadFile(ftpClient, remotePath, localPath, name, encoding);
					if (ok) {
						success += 1;
					} else {
						error += 1;
					}
				} else if (ff.isDirectory()) {
					Result r = downloadFolder(ftpClient, remotePath + "/" + name, localPath + "/" + name, encoding);
					success += r.getSuccess();
					error += r.getError();
				}
			}
		} catch (Exception e) {
			logger.error("download folder error. {}", e);
		}
		result.setSuccess(success);
		result.setError(error);
		return result;

	}

	/**
	 * 上传文件
	 * @param ftpClient 连接
	 * @param remotePath 远程路径
	 * @param localPath 本地路径
	 * @param filename 文件名
	 * @return 是否上传成功
	 */
	public static boolean uploadFile(FTPClient ftpClient, String remotePath, String localPath, String filename) {
		return uploadFile(ftpClient, remotePath, localPath, filename, DEFAULT_ENCODING);
	}

	/**
	 * 上传文件
	 * @param ftpClient 连接
	 * @param remotePath 远程路径
	 * @param localPath 本地路径
	 * @param filename 文件名
	 * @param encoding 文件名编码
	 * @return 是否上传成功
	 */
	public static boolean uploadFile(FTPClient ftpClient, String remotePath, String localPath, String filename, String encoding) {
		try {
			// 转移到FTP服务器目录至指定的目录下
			boolean ok = ftpClient.changeWorkingDirectory(new String(remotePath.getBytes(encoding), "iso-8859-1"));
			if (!ok) {
				logger.error("can not change working directory {}", remotePath);
				return false;
			}

			File file = new File(localPath, filename);
			return ftpClient.storeFile(new String(filename.getBytes(encoding), "ISO-8859-1"), FileUtils.openInputStream(file));

		} catch (Exception e) {
			logger.error("upload file error. {}", e);
		}
		return false;
	}

	/**
	 * 上传文件
	 * @param ftpClient 连接
	 * @param remotePath 远程路径
	 * @param filename 文件名
	 * @param is 输入流
	 * @return 是否上传成功
	 */
	public static boolean uploadFile(FTPClient ftpClient, String remotePath, String filename, InputStream is) {
		return uploadFile(ftpClient, remotePath, filename, is, DEFAULT_ENCODING);
	}

	/**
	 * 上传文件
	 * @param ftpClient 连接
	 * @param remotePath 远程路径
	 * @param filename 文件名
	 * @param is 输入流
	 * @param encoding 文件名编码
	 * @return 是否上传成功
	 */
	public static boolean uploadFile(FTPClient ftpClient, String remotePath, String filename, InputStream is, String encoding) {
		try {
			// 转移到FTP服务器目录至指定的目录下
			boolean ok = ftpClient.changeWorkingDirectory(new String(remotePath.getBytes(encoding), "iso-8859-1"));
			if (!ok) {
				logger.error("can not change working directory {}", remotePath);
				return false;
			}

			return ftpClient.storeFile(new String(filename.getBytes(encoding), "ISO-8859-1"), is);
		} catch (Exception e) {
			logger.error("upload file error. {}", e);
		}
		return false;
	}

	/**
	 * 上传目录
	 * @param ftpClient 连接
	 * @param remotePath 远程路径
	 * @param localPath 本地路径
	 */
	public static Result uploadFolder(FTPClient ftpClient, String remotePath, String localPath) {
		return uploadFolder(ftpClient, remotePath, localPath, DEFAULT_ENCODING);
	}

	/**
	 * 上传目录
	 * @param ftpClient 连接
	 * @param remotePath 远程路径
	 * @param localPath 本地路径
	 * @param encoding 文件名编码
	 */
	public static Result uploadFolder(FTPClient ftpClient, String remotePath, String localPath, String encoding) {
		Result result = new Result();
		int success = 0;
		int error = 0;

		try {
			File dir = new File(localPath);
			if (!dir.exists()) {
				throw new IOException("please set valid localpath");
			}

			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isFile()) {
					boolean ok = uploadFile(ftpClient, remotePath, localPath, file.getName(), encoding);
					if (ok) {
						success += 1;
					} else {
						error += 1;
					}
				} else if (file.isDirectory()) {
					Result r = uploadFolder(ftpClient, remotePath + "/" + file.getName(), localPath + "/" + file.getName(), encoding);
					success += r.getSuccess();
					error += r.getError();
				}
			}
		} catch (Exception e) {
			logger.error("upload folder error. {}", e);
		}
		result.setSuccess(success);
		result.setError(error);
		return result;
	}

	/**
	 * 创建目录
	 * @param filepath 本地文件目录
	 */
	private static void createFilepath(String filepath) {
		File dir = new File(filepath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

}
