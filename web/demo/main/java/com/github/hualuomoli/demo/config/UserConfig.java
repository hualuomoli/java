package com.github.hualuomoli.demo.config;

import java.util.Date;
import java.util.HashSet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.hualuomoli.login.service.LoginUserService;
import com.google.common.collect.Sets;

@Configuration
public class UserConfig {

	@Bean
	public LoginUserService loginUserService() {
		return new LoginUserService() {

			@Override
			public String getUsername() {
				return "system";
			}

			@Override
			public void setUsername(String key, String username) {
			}

			@Override
			public void refreshUsername() {
			}

			@Override
			public Date getCurrentDate() {
				return new Date();
			}

			@Override
			public String getLoginUsername() {
				return "system";
			}

			@Override
			public HashSet<String> getLoginUserRoles() {
				return Sets.newHashSet();
			}

			@Override
			public HashSet<String> getLoginUserPermissions() {
				return Sets.newHashSet();
			}

		};
	}

}
