package com.github.hualuomoli.login.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.github.hualuomoli.commons.util.ServletUtils;
import com.github.hualuomoli.constant.Code;
import com.github.hualuomoli.exception.AuthException;
import com.github.hualuomoli.exception.CodeException;
import com.github.hualuomoli.plugin.cache.SerializeCache;
import com.google.common.collect.Maps;

/**
 * 基本实现
 * @author hualuomoli
 *
 */
public abstract class LoginUserServiceAdaptor implements LoginUserService {
    
    private static final ThreadLocal<String> LOCAL_TOKEN = new ThreadLocal<String>();

    private static final String PREFIX_TOKEN = "token_";
    private static final String TOKEN_NAME = "token";

    private static Map<Integer, Integer> userTypeExpireMap = Maps.newHashMap();

    // 添加用户类型的有效时间
    public static void addUserTypeExpire(Integer userType, Integer expire) {
        userTypeExpireMap.put(userType, expire);
    }

    // 缓存
    protected abstract SerializeCache getCache();

    // 获取token
    protected String getToken() {
        String token = null;
        HttpServletRequest req = ServletUtils.getRequest();
        
        // 1、get from local token
        token = LOCAL_TOKEN.get();
        
        // 2、get from request
        if(StringUtils.isBlank(token)) {
            token = req.getParameter(TOKEN_NAME);
        }

        // 3、get from header
        if (StringUtils.isBlank(token)) {
            token = req.getHeader(TOKEN_NAME);
        }
        
        if (StringUtils.isBlank(token)) {
            // get from cookie
            Cookie[] cookies = req.getCookies();
            if (cookies != null && cookies.length > 0) {
                for (Cookie cookie : cookies) {
                    if (StringUtils.equals(cookie.getName(), TOKEN_NAME)) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }
        }
        return token;
    }

    @Override
    public String getUsername() {
        // 未登录
        String token = this.getToken();
        if (StringUtils.isBlank(token)) {
            throw new CodeException(Code.TOKEN_EMPTY);
        }
        // 登陆超时
        String username = this.getCache().getSerializable(PREFIX_TOKEN + token);
        if (StringUtils.isBlank(username)) {
            throw new CodeException(Code.TOKEN_OVER_TIME);
        }

        return username;
    }

    @Override
    public void login(String token, String username) {
        this.getCache().setSerializable(PREFIX_TOKEN + token, username);
    }

    @Override
    public void login(String token, String username, Integer userType) {
        Integer expire = userTypeExpireMap.get(userType);
        if (expire == null) {
            this.getCache().setSerializable(PREFIX_TOKEN + token, username);
        } else {
            this.getCache().setSerializable(PREFIX_TOKEN + token, username, expire);
        }
    }

    @Override
    public void refresh() {
        // 未登录
        String token = this.getToken();
        if (StringUtils.isBlank(token)) {
            throw AuthException.NO_LOGIN;
        }
        this.getCache().getSerializableAndRefresh(PREFIX_TOKEN + token);
    }

    @Override
    public void refresh(Integer userType) {
        // 未登录
        String token = this.getToken();
        if (StringUtils.isBlank(token)) {
            throw AuthException.NO_LOGIN;
        }
        Integer expire = userTypeExpireMap.get(userType);
        if (expire == null) {
            this.getCache().getSerializableAndRefresh(PREFIX_TOKEN + token);
        } else {
            this.getCache().getSerializableAndRefresh(PREFIX_TOKEN + token, expire);
        }
    }

    @Override
    public void logout() {
        String token = this.getToken();
        this.getCache().remove(PREFIX_TOKEN + token);
    }

    @Override
    public Date getCurrentDate() {
        return new Date();
    }
    
    public static void setToken(String token) {
        LOCAL_TOKEN.set(token);
    }

}
