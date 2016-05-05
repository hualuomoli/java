package com.github.hualuomoli.plugin.qrcode;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import com.github.hualuomoli.plugin.qrcode.exception.QRCodeException;

/**
 * 二维码
 * @author hualuomoli
 *
 */
public interface QRCode {

	/**
	 * 生成二维码
	 * @param filename 输出文件名
	 * @param width 宽度
	 * @param height 高度
	 * @param content 内容
	 */
	void write(String filename, int width, int height, String content) throws QRCodeException;

	/**
	 * 生成二维码
	 * @param filepath 输出文件路径
	 * @param filename 输出文件名称
	 * @param width 宽度
	 * @param height 高度
	 * @param content 内容
	 */
	void write(String filepath, String filename, int width, int height, String content) throws QRCodeException;

	/**
	 * 生成二维码
	 * @param file 输出文件
	 * @param width 宽度
	 * @param height 高度
	 * @param content 内容
	 */
	void write(File file, int width, int height, String content) throws QRCodeException;

	/**
	 * 生成二维码
	 * @param out 输出文件流
	 * @param width 宽度
	 * @param height 高度
	 * @param content 内容
	 */
	void write(OutputStream out, int width, int height, String content) throws QRCodeException;

	/**
	 * 读取二维码内容
	 * @param filename 文件名
	 * @return 二维码内容
	 */
	String read(String filename) throws QRCodeException;

	/**
	 * 读取二维码内容
	 * @param filepath 文件路径
	 * @param filename 文件名称
	 * @return 二维码内容
	 */
	String read(String filepath, String filename) throws QRCodeException;

	/**
	* 读取二维码内容
	* @param file 二维码文件
	* @return 二维码内容
	*/
	String read(File file) throws QRCodeException;

	/**
	* 读取二维码内容
	* @param in 输入流
	* @return 二维码内容
	*/
	String read(InputStream in) throws QRCodeException;

}
