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
			public void login(String key, String username) {
			}

			@Override
			public void refresh() {
			}

			@Override
			public Date getCurrentDate() {
				return new Date();
			}

			@Override
			public HashSet<String> getUserRoles() {
				return Sets.newHashSet();
			}

			@Override
			public HashSet<String> getUserPermissions() {
				return Sets.newHashSet();
			}

			@Override
			public void logout() {

			}

			@Override
			public void login(String token, String username, Integer userType) {

			}

			@Override
			public void refresh(Integer userType) {

			}

		};
	}

}
