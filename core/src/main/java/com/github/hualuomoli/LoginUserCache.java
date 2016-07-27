package com.github.hualuomoli;

import org.springframework.stereotype.Service;

import com.github.hualuomoli.plugin.cache.DefaultSerializeCache;

@Service(value = "com.github.hualuomoli.LoginUserCache")
public class LoginUserCache extends DefaultSerializeCache {

}
