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
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.util.StreamUtils;

import com.github.hualuomoli.plugin.http.adaptor.AsyncHttpAdaptor;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * HttpClient
 * @author hualuomoli
 *
 */
public class AsyncHttpClient extends AsyncHttpAdaptor {

	@Override
	public GetClient get(String url, Object... uriParameters) {
		return new HttpClientGetClient(this.getRealUrl(url, uriParameters));
	}

	@Override
	public PostClient post(String url, Object... uriParameters) {
		return new HttpClientPostClient(this.getRealUrl(url, uriParameters));
	}

	@Override
	public DeleteClient delete(String url, Object... uriParameters) {
		return new HttpClientDeleteClient(this.getRealUrl(url, uriParameters));
	}

	@Override
	public FileUploadClient fileUpload(String url, Object... uriParameters) {
		return new HttpClientFileUploadClient(this.getRealUrl(url, uriParameters));
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

		@Override
		public void execute() {
			try {
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
			}

		}

	}

	public static class HttpClientGetClient extends HttpClientAdaptor<HttpClientGetClient> implements GetClient {

		private String url;
		private Map<String, Object> params = Maps.newHashMap();

		public HttpClientGetClient(String realUrl) {
			this.url = realUrl;
			request = new HttpGet();
		}

		@Override
		public GetClient setParameter(String name, Object value) {
			params.put(name, value);
			return this;
		}

		@Override
		protected void preExecute() {
			try {
				for (String name : params.keySet()) {
					if (url.lastIndexOf("?") != -1) {
						url += "&" + name + "=" + this.encode(params.get(name).toString());
					} else {
						url += "?" + name + "=" + this.encode(params.get(name).toString());
					}
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

	public static class HttpClientPostClient extends HttpClientAdaptor<HttpClientPostClient> implements PostClient {

		private Map<String, Object> params = Maps.newHashMap();
		private HttpPost httpPost;

		public HttpClientPostClient(String realUrl) {
			request = httpPost = new HttpPost(realUrl);
		}

		@Override
		public PostClient setParameter(String name, Object value) {
			params.put(name, value);
			return this;
		}

		@Override
		public PostClient setContent(String content) {
			// TODO Auto-generated method stub
			return this;
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

	public static class HttpClientDeleteClient extends HttpClientAdaptor<HttpClientPostClient> implements DeleteClient {

		public HttpClientDeleteClient(String realUrl) {
			request = new HttpDelete(realUrl);
		}

	}

	public static class HttpClientFileUploadClient extends HttpClientAdaptor<HttpClientFileUploadClient> implements FileUploadClient {

		private HttpPost httpPost;
		private Map<String, Object> params = Maps.newHashMap();
		private Map<String, File> files = Maps.newHashMap();

		public HttpClientFileUploadClient(String realUrl) {
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
