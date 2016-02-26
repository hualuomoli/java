package com.github.hualuomoli.commons.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtils {

	private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	private static final String DEFAULT_CHARSET_NAME = "UTF-8";

	public static final String GET = "GET";
	public static final String POST = "POST";
	public static final String PUT = "PUT";
	public static final String DELETE = "DELETE";

	///////////
	// doGet //
	///////////
	public static String doGet(String urlStr) throws IOException {
		return doGet(urlStr, null);
	}

	public static String doGet(String urlStr, String queryString) throws IOException {
		HttpMethod method = null;
		try {
			HttpClient client = new HttpClient();
			method = new GetMethod(urlStr);
			if (StringUtils.isNotBlank(queryString)) {
				method.setQueryString(URIUtil.encodeQuery(queryString));
			}
			client.executeMethod(method);
			if (method.getStatusCode() == HttpStatus.SC_OK) {
				return method.getResponseBodyAsString();
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
		return null;
	}

	////////////
	// doPost //
	////////////
	public static String doPost(String urlStr) {
		return doPost(urlStr, null);
	}

	public static String doPost(String urlStr, Map<String, String> params) {
		HttpMethod method = null;

		try {
			HttpClient client = new HttpClient();
			method = new PostMethod(urlStr);

			// add parameter
			if (params != null) {
				HttpMethodParams p = new HttpMethodParams();
				for (Map.Entry<String, String> entry : params.entrySet()) {
					p.setParameter(entry.getKey(), entry.getValue());
				}
				method.setParams(p);
			}
			client.executeMethod(method);
			if (method.getStatusCode() == HttpStatus.SC_OK) {
				return method.getResponseBodyAsString();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
		return null;

	}

	///////////////
	// doPayload //
	///////////////
	public static String doPayload(String urlStr, String payload) throws IOException {
		return doPayload(urlStr, payload, POST);
	}

	public static String doPayload(String urlStr, String payload, String methodName) throws IOException {
		return doPayload(urlStr, payload, methodName, DEFAULT_CHARSET_NAME);
	}

	public static String doPayload(String urlStr, String payload, String methodName, String charsetName)
			throws IOException {
		if (StringUtils.isEmpty(payload)) {
			throw new IOException("please set payload data.");
		}
		// create URL
		URL url = new URL(urlStr);
		// get connection
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		// set in/out
		connection.setDoInput(true);
		connection.setDoOutput(true);
		// set method name
		connection.setRequestMethod(methodName);
		// set content type
		connection.setRequestProperty("Accept", "application/json");
		connection.setRequestProperty("Content-Type", "application/json; charset=" + charsetName);
		// write payload
		OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), charsetName);
		writer.write(payload);
		writer.close();
		// read data
		String line = getString(connection.getInputStream(), charsetName);
		connection.disconnect();
		// return data
		return line;
	}

	private static String getString(InputStream is, String charsetName) throws IOException {
		String line;
		StringBuilder buffer = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, charsetName));
		while ((line = br.readLine()) != null) {
			buffer.append(line);
		}
		br.close();
		return buffer.toString();
	}

}
