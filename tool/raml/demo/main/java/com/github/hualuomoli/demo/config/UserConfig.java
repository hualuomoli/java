package com.github.hualuomoli.demo.config;

import java.util.Date;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.hualuomoli.LoginUserService;

@Configuration
public class UserConfig {

	@Bean
	public LoginUserService loginUserService() {
		return new LoginUserService() {

			@Override
			public String getUsername() {
				return "admin";
			}

			@Override
			public void setUsername(String key, String username) {
			}

			@Override
			public <T> T getObject() {
				return null;
			}

			@Override
			public <T> void setObject(String username, T t) {
			}

			@Override
			public void refresh() {
			}

			@Override
			public Date getCurrentDate() {
				return new Date();
			}

		};
	}

}
