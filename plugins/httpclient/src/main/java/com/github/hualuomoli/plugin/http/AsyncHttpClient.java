package com.github.hualuomoli.plugin.http;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * HttpClient
 * @author hualuomoli
 *
 */
public class AsyncHttpClient implements AsyncHttp {

	private static final Logger logger = LoggerFactory.getLogger(AsyncHttpClient.class);

	@Override
	public GetClient get(String url, Object... uriParameters) {
		return new HttpClientGetClient(Tool.getRealUrl(url, uriParameters));
	}

	@Override
	public PostClient post(String url, Object... uriParameters) {
		return new HttpClientPostClient(Tool.getRealUrl(url, uriParameters));
	}

	@Override
	public PayloadClient json(String url, Object... uriParameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DeleteClient delete(String url, Object... uriParameters) {
		return new HttpClientDeleteClient(Tool.getRealUrl(url, uriParameters));
	}

	@Override
	public FileUploadClient fileUpload(String url, Object... uriParameters) {
		return new HttpClientFileUploadClient(Tool.getRealUrl(url, uriParameters));
	}

	@SuppressWarnings("unchecked")
	public abstract static class HttpClientAdaptor<T extends Client> implements Client {

		private Callback callback;

		// 创建默认的httpClient实例.
		private CloseableHttpClient httpclient = HttpClients.createDefault();
		protected HttpRequestBase request;

		public HttpClientAdaptor() {
		}

		@Override
		public T addLinstener(Callback callback) {
			this.callback = callback;
			return (T) this;
		}

		@Override
		public T setHeader(String name, String value) {
			request.setHeader(name, value);
			return (T) this;
		}

		protected void preExecute() {

		}

		protected void showLog() {

		}

		@Override
		public void execute() {
			try {
				this.showLog();
				this.preExecute();
				HttpResponse response = httpclient.execute(request);

				// 获取响应内容
				String content = null;
				HttpEntity entity = response.getEntity();
				Header contentType = entity.getContentType(); // 响应内容类型
				if (contentType != null) {
					String[] value = contentType.getValue().split("charset=");
					if (value.length == 2) {
						content = StreamUtils.copyToString(entity.getContent(), Charset.forName(value[1]));
					} else {
						content = StreamUtils.copyToString(entity.getContent(), Charset.forName("utf-8"));
					}
				} else {
					content = StreamUtils.copyToString(entity.getContent(), Charset.forName("utf-8"));
				}

				int statusCode = response.getStatusLine().getStatusCode();

				callback.onMessage(new Res(statusCode, content, this));

			} catch (Exception e) {
				//
				if (logger.isErrorEnabled()) {
					logger.error("{}", e);
				}
				callback.onMessage(new Res(e));
			}

		}

	}

	// get
	public static class HttpClientGetClient extends HttpClientAdaptor<HttpClientGetClient> implements GetClient {

		private String url;
		protected HttpGet httpGet;

		private Map<String, Object> params = Maps.newHashMap();

		public HttpClientGetClient(String realUrl) {
			this.url = realUrl;
			request = httpGet = new HttpGet();
		}

		@Override
		public GetClient setParameter(String name, Object value) {
			params.put(name, value);
			return this;
		}

		@Override
		protected void showLog() {
			if (logger.isDebugEnabled()) {
				String strParameter = "";
				for (String name : params.keySet()) {
					strParameter += "&" + name + "=" + String.valueOf(params.get(name));
				}
				if (strParameter.length() > 0) {
					strParameter = strParameter.substring(1);
				}
				logger.debug("get请求");
				logger.debug("\turl={}", url);
				logger.debug("\tstrParameter={}", strParameter);
			}
		}

		@Override
		protected void preExecute() {
			try {
				String strParameter = "";
				for (String name : params.keySet()) {
					strParameter += "&" + name + "=" + this.encode(String.valueOf(params.get(name)));
				}
				if (strParameter.length() > 0) {
					strParameter = strParameter.substring(1);
				}
				if (url.indexOf("?") > 0) {
					url = url + "&" + strParameter;
				} else {
					url = url + "?" + strParameter;
				}
				request.setURI(new URI(url));
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}

		private String encode(String val) {
			try {
				return URLEncoder.encode(val, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				if (logger.isErrorEnabled()) {
					logger.error("{}", e);
				}
				return "";
			}
		}

	}

	// post
	public static class HttpClientPostClient extends HttpClientAdaptor<HttpClientPostClient> implements PostClient {

		private String url;
		private HttpPost httpPost;

		private Map<String, Object> params = Maps.newHashMap();

		public HttpClientPostClient(String realUrl) {
			url = realUrl;
			request = httpPost = new HttpPost(realUrl);
		}

		@Override
		public PostClient setParameter(String name, Object value) {
			params.put(name, value);
			return this;
		}

		@Override
		protected void showLog() {
			if (logger.isDebugEnabled()) {
				String strParameter = "";
				for (String name : params.keySet()) {
					strParameter += "&" + name + "=" + String.valueOf(params.get(name));
				}
				if (strParameter.length() > 0) {
					strParameter = strParameter.substring(1);
				}
				logger.debug("post请求");
				logger.debug("\turl={}", url);
				logger.debug("\tstrParameter={}", strParameter);
			}
		}

		@Override
		protected void preExecute() {
			// 创建参数队列
			List<NameValuePair> formparams = Lists.newArrayList();
			for (String name : params.keySet()) {
				formparams.add(new BasicNameValuePair(name, String.valueOf(params.get(name))));
			}
			try {
				UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
				httpPost.setEntity(uefEntity);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

	}

	// payload
	public static class HttpClientPayloadClient extends HttpClientAdaptor<HttpClientPayloadClient> implements PayloadClient {

		private String content;

		private String url;
		private HttpPost httpPost;

		public HttpClientPayloadClient(String realUrl) {
			url = realUrl;
			request = httpPost = new HttpPost(realUrl);
		}

		@Override
		public PayloadClient setContent(String content) {
			this.content = content;
			return this;
		}

		@Override
		protected void showLog() {
			if (logger.isDebugEnabled()) {
				logger.debug("payload请求");
				logger.debug("\turl={}", url);
				logger.debug("\tcontent={}", content);
			}
		}

		@Override
		protected void preExecute() {
			StringEntity se = new StringEntity(content, ContentType.APPLICATION_JSON);
			httpPost.setEntity(se);
		}

	}

	// delete
	public static class HttpClientDeleteClient extends HttpClientAdaptor<HttpClientPostClient> implements DeleteClient {

		private String url;
		protected HttpDelete httpDelete;

		public HttpClientDeleteClient(String realUrl) {
			url = realUrl;
			request = httpDelete = new HttpDelete(realUrl);
		}

		@Override
		protected void showLog() {
			if (logger.isDebugEnabled()) {
				logger.debug("delete请求");
				logger.debug("\turl={}", url);
			}
		}

	}

	// fileupload
	public static class HttpClientFileUploadClient extends HttpClientAdaptor<HttpClientFileUploadClient> implements FileUploadClient {

		private Map<String, Object> params = Maps.newHashMap();
		private Map<String, File> files = Maps.newHashMap();

		private String url;
		private HttpPost httpPost;

		public HttpClientFileUploadClient(String realUrl) {
			url = realUrl;
			request = httpPost = new HttpPost(realUrl);
		}

		@Override
		public FileUploadClient setParameter(String name, Object value) {
			params.put(name, value);
			return this;
		}

		@Override
		public FileUploadClient setUploadFile(String name, File file) {
			files.put(name, file);
			return this;
		}

		@Override
		protected void showLog() {
			if (logger.isDebugEnabled()) {
				String strParameter = "";
				for (String name : params.keySet()) {
					strParameter += "&" + name + "=" + String.valueOf(params.get(name));
				}
				if (strParameter.length() > 0) {
					strParameter = strParameter.substring(1);
				}
				logger.debug("fileupload请求");
				logger.debug("\turl={}", url);
				logger.debug("\tstrParameter={}", strParameter);
				// file
				for (String name : files.keySet()) {
					logger.debug("\t{}={}", name, files.get(name).getAbsolutePath());
				}
			}
		}

		@Override
		protected void preExecute() {
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			for (String name : files.keySet()) {
				builder.addPart(name, new FileBody(files.get(name)));
			}
			for (String name : params.keySet()) {
				builder.addPart(name, new StringBody(String.valueOf(params.get(name)), ContentType.TEXT_PLAIN));
			}
			HttpEntity reqEntity = builder.build();
			httpPost.setEntity(reqEntity);
		}

	}

}
