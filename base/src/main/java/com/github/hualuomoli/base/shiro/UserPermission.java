package com.github.hualuomoli.base.shiro;

import java.util.Collection;
import java.util.Set;

import org.apache.shiro.authz.SimpleAuthorizationInfo;

public interface UserPermission {

	Set<String> getRoles(String userName);

	Collection<String> getPermissions(String userName);

	void extend(SimpleAuthorizationInfo info, String userName);

}
