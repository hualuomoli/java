package com.github.hualuomoli.plugin.zxing;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.commons.util.CharsetUtils;
import com.github.hualuomoli.plugin.qrcode.QRCode;
import com.github.hualuomoli.plugin.qrcode.QRCodeAbstract;
import com.github.hualuomoli.plugin.qrcode.exception.QRCodeException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

/**
 * zxing 二维码
 * @author hualuomoli
 *
 */
public class ZxingQRCode extends QRCodeAbstract implements QRCode {

	private static final Logger logger = LoggerFactory.getLogger(ZxingQRCode.class);

	private String format = "jpg";
	private String charset = CharsetUtils.UTF8.name();

	public void setFormat(String format) {
		this.format = format;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	@Override
	public void write(OutputStream out, int width, int height, String content) throws QRCodeException {
		try {
			Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
			hints.put(EncodeHintType.CHARACTER_SET, charset);
			BitMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
			MatrixToImageWriter.writeToStream(matrix, format, out);

			if (logger.isDebugEnabled()) {
				logger.debug("success.");
			}

		} catch (Exception e) {
			throw new QRCodeException(e);
		}
	}

	@Override
	public String read(InputStream in) throws QRCodeException {
		try {
			BufferedImage image = ImageIO.read(in);
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			Binarizer binarizer = new HybridBinarizer(source);
			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
			Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
			hints.put(DecodeHintType.CHARACTER_SET, charset);
			Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码
			String content = result.getText();

			if (logger.isDebugEnabled()) {
				logger.debug("content {}", content);
			}

			return content;

		} catch (Exception e) {
			throw new QRCodeException(e);
		}
	}

}
