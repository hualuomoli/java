package com.github.hualuomoli.commons.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.commons.constant.Charset;

/**
 * HTTP工具
 * @author hualuomoli
 *
 */
public class HttpURLUtils {

	private static final Logger logger = LoggerFactory.getLogger(HttpURLUtils.class);

	// charset
	private static final String CHARSET_UTF8 = Charset.UTF8.name();

	// method
	public static final String METHOD_GET = "GET";
	public static final String METHOD_POST = "POST";

	// mime
	public static final String MIME_TEXT = "text/plain";
	public static final String MIME_JSON = "application/json";
	public static final String MIME_XML = "application/xml";
	public static final String MIME_URLENCODED = "application/x-www-form-urlencoded";

	// get
	public static String get(String urlStr) {
		return _get(urlStr, StringUtils.EMPTY);
	}

	// get
	public static String get(String urlStr, Map<String, String> dataMap) {
		String data = "";
		for (String key : dataMap.keySet()) {
			data += "&" + key + "=" + dataMap.get(key);
		}
		return _get(urlStr, data);
	}

	// get
	private static String _get(String urlStr, String data) {
		try {
			return invoke(METHOD_GET, urlStr, data, null);
		} catch (IOException e) {
			logger.error("invoke http get error.", e);
			return null;
		}
	}

	// urlEncoded
	public static String postUrlEncoded(String urlStr) {
		return _postUrlEncoded(urlStr, StringUtils.EMPTY);
	}

	// urlEncoded
	public static String postUrlEncoded(String urlStr, final Map<String, String> dataMap) {
		String data = "";
		for (String key : dataMap.keySet()) {
			data += "&" + key + "=" + dataMap.get(key);
		}
		return _postUrlEncoded(urlStr, data);
	}

	// urlEncoded
	private static String _postUrlEncoded(String urlStr, String data) {
		try {
			return invoke(METHOD_POST, urlStr, data, MIME_URLENCODED);
		} catch (IOException e) {
			logger.error("invoke http urlEncoded error.", e);
			return null;
		}
	}

	// json
	public static String postJSON(String urlStr) {
		return postJSON(urlStr, StringUtils.EMPTY);
	}

	// json
	public static String postJSON(String urlStr, String jsonData) {
		try {
			return invoke(METHOD_POST, urlStr, jsonData, MIME_JSON);
		} catch (IOException e) {
			logger.error("invoke http json error.", e);
			return null;
		}
	}

	// invoke
	public static String invoke(String methodName, String urlStr, String data, String acceptType) throws IOException {

		if (logger.isDebugEnabled()) {
			logger.debug("methodName {}", methodName);
			logger.debug("urlStr {}", urlStr);
			logger.debug("data {}", data);
		}
		if (StringUtils.isBlank(methodName) || StringUtils.isBlank(urlStr)) {
			throw new RuntimeException("please set method name and url.");
		}

		HttpURLConnection conn = null;
		OutputStream os = null;
		InputStream is = null;

		try {

			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();

			// method
			conn.setRequestMethod(methodName);

			conn.setDoInput(true); // input
			conn.setDoOutput(true); // output

			// header
			if (acceptType != null) {
				conn.setRequestProperty("Accept", acceptType);
				conn.setRequestProperty("Content-Type", acceptType + "; charset=" + CHARSET_UTF8);
			}

			// output data
			if (StringUtils.isNotBlank(data)) {
				os = conn.getOutputStream();
				os.write(data.getBytes());
			}

			// get response
			is = conn.getInputStream();
			String outStr = IOUtils.toString(is, CHARSET_UTF8);

			if (logger.isDebugEnabled()) {
				logger.debug("response {}", outStr);
			}
			return outStr;
		} catch (IOException e) {
			throw e;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
		}

	}

}
