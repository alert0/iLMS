package com.hotent.mini.web.security;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

/**
 * 无密码验证
 * @author heyifan
 *
 */
public class NoValidPasswordEncoder extends ShaPasswordEncoder{
    public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
        return true;
    }
    public NoValidPasswordEncoder() {
	}
}
