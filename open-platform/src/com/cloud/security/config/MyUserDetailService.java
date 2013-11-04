package com.cloud.security.config;

 import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cloud.platform.Constants;
import com.cloud.platform.IDao;
import com.cloud.platform.StringUtil;
 
@SuppressWarnings("deprecation")
public class MyUserDetailService implements UserDetailsService {

	@Autowired
	private IDao dao;
	
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		User user = null;
		List list = dao.getAllByHql("from User where username = ?", username);
		
		if(list.isEmpty() || list.get(0) == null) {
			return user;
		}
		
		// get database user info
		com.cloud.security.model.User u = (com.cloud.security.model.User) list.get(0);
		
		// init authority
		Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		
		// init basic user authority
		GrantedAuthorityImpl auth = new GrantedAuthorityImpl("ROLE_USER");
		auths.add(auth);
		
		// init user role authority
		if(!StringUtil.isNullOrEmpty(u.getRoleIds())) {
			for(String roleId : u.getRoleIds().split(",")) {
				auth = new GrantedAuthorityImpl(roleId);
				auths.add(auth);
			}
		}
		
		/**
		 * Spring Security User construct
		 * 
		 * 1 String username
		 * 2 String password
		 * 3 boolean enabled
		 * 4 boolean accountNonExpired
		 * 5 boolean credentialsNonExpired
		 * 6 boolean accountNonLocked
		 * 7 Collection<GrantedAuthority> authorities
		 */
		user = new User(username, u.getPassword(), !Constants.VALID_NO.equals(u.getIsValid()), true, true, true, auths);
		
		return user;
	}
}
