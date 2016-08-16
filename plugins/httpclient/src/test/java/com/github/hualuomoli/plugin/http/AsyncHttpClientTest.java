package com.github.hualuomoli.plugin.http;

import java.io.File;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.hualuomoli.plugin.http.AsyncHttp.Callback;
import com.github.hualuomoli.plugin.http.AsyncHttp.Client;
import com.github.hualuomoli.plugin.http.AsyncHttp.Res;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按字母排序
public class AsyncHttpClientTest {

	private static final Logger logger = LoggerFactory.getLogger(AsyncHttpClient.class);
	private static String token = "";

	private static String serverUrl = "http://192.168.1.161:8087";

	private void login(final Client c) {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(serverUrl + "/app/token") //
				.setParameter("bbCode", "10035726") //
				.setParameter("merId", "8balbum") //
				.setParameter("timestamp", "1467168174476") //
				.setParameter("type", "1") //
				.setParameter("signData", "1234") //
				.addLinstener(new Callback() {

					@Override
					public void onMessage(Res res) {
						if (res.isOk("code", "0000")) {
							if (logger.isDebugEnabled()) {
								logger.debug("login success");
							}
							token = res.getJson().getString("token");
							if (c != null) {
								if (logger.isDebugEnabled()) {
									logger.debug("reload client");
								}
								c.setHeader("token", token);
								c.execute();
							}
						} else {
							if (logger.isWarnEnabled()) {
								logger.warn("{}", res.getContent());
							}
						}
					}
				}) //
				.execute();
	}

	@Test
	public void test01Get() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(serverUrl + "/app/screen/photo") //
				.setHeader("token", "") //
				.addLinstener(new Callback() {

					@Override
					public void onMessage(Res res) {
						if (res.isOk("code", "0000")) {
							GetRes result = res.getObject("result", GetRes.class);
							logger.debug("{}", ToStringBuilder.reflectionToString(result));
						} else if (res.getStatusCode() == 401) {
							// login
							Client c = res.getClient();
							logger.debug("login ");
							login(c);
						} else {
							logger.debug("{}", res.getStatusCode());
						}
					}
				}).execute();
	}

	@Test
	public void test02Get() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(serverUrl + "/app/screen/photo") //
				.setHeader("token", token) //
				.addLinstener(new Callback() {

					@Override
					public void onMessage(Res res) {
						if (res.isOk("code", "0000")) {
							GetRes result = res.getObject("result", GetRes.class);
							logger.debug("{}", ToStringBuilder.reflectionToString(result));
						} else if (res.getStatusCode() == 401) {
							// login
							Client c = res.getClient();
							logger.debug("login 02 ");
							login(c);
						} else {
							logger.debug("{}", res.getStatusCode());
						}
					}
				}).execute();
	}

	public static class GetRes {

		private Date endDate;
		private Date startDate;
		private String[] photos;

		public GetRes() {
		}

		public Date getEndDate() {
			return endDate;
		}

		@JSONField(format = "yyyyMMddHHmmss")
		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}

		public Date getStartDate() {
			return startDate;
		}

		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}

		public String[] getPhotos() {
			return photos;
		}

		public void setPhotos(String[] photos) {
			this.photos = photos;
		}
	}

	@Test
	public void test03Post() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.post(serverUrl + "/app/album") //
				.setParameter("folderName", "家庭") //
				.setHeader("token", token) //
				.setHeader("Content-Type", "application/x-www-form-urlencoded") //
				.addLinstener(new Callback() {

					@Override
					public void onMessage(Res res) {
						if (res.isOk("code", "0000")) {
							logger.debug("{}", res.getContent());
						} else if (res.getStatusCode() == 401) {
							// login
							Client c = res.getClient();
							logger.debug("login post ");
							login(c);
						} else {
							logger.debug("{}", res.getStatusCode());
						}
					}
				}).execute();
	}

	@Test
	public void test04Delete() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.delete(serverUrl + "/app/photo/{id}", "196") //
				.setHeader("token", token) //
				.addLinstener(new Callback() {

					@Override
					public void onMessage(Res res) {
						if (res.isOk("code", "0000")) {
							logger.debug("{}", res.getContent());
						} else if (res.getStatusCode() == 401) {
							// login
							Client c = res.getClient();
							logger.debug("login delete ");
							login(c);
						} else {
							logger.debug(" status {}", res.getStatusCode());
							logger.debug(" content {}", res.getContent());
						}
					}
				}).execute();
	}

	@Test
	public void test04FileUpload() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.fileUpload(serverUrl + "/app/upload") //
				.setUploadFile("upload", new File("E:/pic.jpg")) //
				.setParameter("publisher", "小儿子") //
				.setParameter("fileType", "1") //
				.setParameter("deviceType", 1) //
				.setHeader("token", token) //
				.addLinstener(new Callback() {

					@Override
					public void onMessage(Res res) {
						if (res.isOk("code", "0000")) {
							logger.debug("{}", res.getContent());
						} else if (res.getStatusCode() == 401) {
							// login
							Client c = res.getClient();
							logger.debug("login delete ");
							login(c);
						} else {
							logger.debug(" status {}", res.getStatusCode());
							logger.debug(" content {}", res.getContent());
						}
					}
				}).execute();
	}

}
