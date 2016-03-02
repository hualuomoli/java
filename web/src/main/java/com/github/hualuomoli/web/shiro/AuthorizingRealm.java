package com.github.hualuomoli.web.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;

public class AuthorizingRealm extends org.apache.shiro.realm.AuthorizingRealm {

	private UserPermission userPermission;

	public void setUserPermission(UserPermission userPermission) {
		this.userPermission = userPermission;
	}

	// get user authorization
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		SimpleAuthorizationInfo info = null;
		String userName = (String) principalCollection.fromRealm(getName()).iterator().next();
		info = new SimpleAuthorizationInfo();
		// roles
		info.setRoles(userPermission.getRoles(userName));
		// permissions
		info.addStringPermissions(userPermission.getPermissions(userName));
		// extend Authorization info such as add test role permission
		userPermission.extend(info, userName);
		// return user info
		return info;
	}

	// set login userName and password
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		// UsernamePasswordToken对象用来存放提交的登录信息
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		return new SimpleAuthenticationInfo(token.getUsername(), null, getName());
	}

	// check login user
	@Override
	protected void assertCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) throws AuthenticationException {
		return;
	}

}
