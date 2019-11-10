package com.hotent.mini.web.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.mini.controller.util.PlatformConsts;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.service.IUserService;
import com.hotent.org.persistence.dao.UserRoleDao;
import com.hotent.org.persistence.model.UserRole;

/**
 * 实现UserDetailsService 接口获取UserDetails 接口实例对象。
 * @author ray
 *
 */
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Resource
	IUserService userService ;
	@Resource
	UserRoleDao userRoleDao;
	
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		IUserService userService = AppUtil.getBean(IUserService.class);
		
		IUser user = userService.getUserByAccount(username);
		if (BeanUtils.isEmpty(user)) {  
            throw new UsernameNotFoundException("用户：" + username + "不存在");  
        }
		
		if(!user.isEnable()){
			throw new DisabledException("用户：" + username + "已经被禁用");
		}
		
		Collection< GrantedAuthority> collection=new ArrayList<GrantedAuthority>();
		if(user.isAdmin()){
			collection.add(PlatformConsts.ROLE_GRANT_SUPER);
		}
		else{
			//构建用户角色。
			List<UserRole> userRoleList= userRoleDao.getListByUserId(user.getUserId());
			for(UserRole userRole:userRoleList){
				GrantedAuthority role=new SimpleGrantedAuthority(userRole.getAlias());
				collection.add(role);
			}
		}
		UserDetails userDetails = getDefault(user,collection);
		return userDetails;
	}
	
	private UserDetails getDefault(IUser user, Collection< GrantedAuthority> collection){
		return new User(user.getAccount(), user.getPassword(), collection);
	}
}
