package com.github.hualuomoli.base.shiro;

import java.util.Collection;
import java.util.Set;

import org.apache.shiro.authz.SimpleAuthorizationInfo;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * implements demo
 * @author liubaoquan
 *
 */
public class DefaultUserPermission implements UserPermission {

	@Override
	public Set<String> getRoles(String userName) {
		return Sets.newHashSet("admin");
	}

	@Override
	public Collection<String> getPermissions(String userName) {
		return Lists.newArrayList("index");
	}

	@Override
	public void extend(SimpleAuthorizationInfo info, String userName) {
		// add demo permission
		info.addStringPermission("demo");
	}

}
