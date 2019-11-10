package com.hotent.org.persistence.model;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SysUser extends User implements UserDetails
{
	

	private Collection<? extends GrantedAuthority> roles=new ArrayList<GrantedAuthority>();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4962121675615445357L;
	
	/**
	 * 设置角色。
	 * @param roles
	 */
	public void setAuthorities(Collection<? extends GrantedAuthority> roles){
		this.roles=roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return roles;
	}

	@Override
	public String getUsername() {
		return this.getAccount();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		int status=this.getStatus();
		if(status==1){
			return true;
		}
		return false;
	}

}
