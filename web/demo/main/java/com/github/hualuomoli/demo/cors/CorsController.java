package com.github.hualuomoli.demo.cors;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.hualuomoli.mvc.rest.AppRestResponse;
import com.github.hualuomoli.mvc.validator.EntityValidator;

@RestController(value = "com.github.hualuomoli.demo.cors.CorsController")
@RequestMapping(value = "/demo/cors")
public class CorsController {

	@RequestMapping(value = "", method = RequestMethod.GET, produces = { "application/json" })
	public String get(Get get) {
		return AppRestResponse.getNoData();
	}

	public static class Get implements EntityValidator {

		private String username;
		private String nickname;

		public Get() {
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

	}

	@RequestMapping(value = "", method = RequestMethod.POST, consumes = { "application/x-www-form-urlencoded" }, produces = { "application/json" })
	public String postUrlEncoded(PostUrlEncoded postUrlEncoded) {
		return AppRestResponse.getNoData();
	}

	public static class PostUrlEncoded implements EntityValidator {

		private String username;
		private String nickname;

		public PostUrlEncoded() {
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

	}

	@RequestMapping(value = "", method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json" })
	public String postJson(@RequestBody PostJson postJson) {
		return AppRestResponse.getNoData();
	}

	public static class PostJson implements EntityValidator {

		private String username;
		private String nickname;

		private List<Address> addresses;
		private User info;

		public PostJson() {
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

		public List<Address> getAddresses() {
			return addresses;
		}

		public void setAddresses(List<Address> addresses) {
			this.addresses = addresses;
		}

		public User getInfo() {
			return info;
		}

		public void setInfo(User info) {
			this.info = info;
		}

		public static class Address {
			private String province;
			private String city;
			private String county;
			private String name;

			public Address() {
			}

			public String getProvince() {
				return province;
			}

			public void setProvince(String province) {
				this.province = province;
			}

			public String getCity() {
				return city;
			}

			public void setCity(String city) {
				this.city = city;
			}

			public String getCounty() {
				return county;
			}

			public void setCounty(String county) {
				this.county = county;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}
		}

		public static class User {
			private String username;
			private String nickname;
			private Integer age;
			private String sex;
			private Double wigth;
			private Double height;

			public User() {
			}

			public String getUsername() {
				return username;
			}

			public void setUsername(String username) {
				this.username = username;
			}

			public String getNickname() {
				return nickname;
			}

			public void setNickname(String nickname) {
				this.nickname = nickname;
			}

			public Integer getAge() {
				return age;
			}

			public void setAge(Integer age) {
				this.age = age;
			}

			public String getSex() {
				return sex;
			}

			public void setSex(String sex) {
				this.sex = sex;
			}

			public Double getWigth() {
				return wigth;
			}

			public void setWigth(Double wigth) {
				this.wigth = wigth;
			}

			public Double getHeight() {
				return height;
			}

			public void setHeight(Double height) {
				this.height = height;
			}
		}

	}

}
