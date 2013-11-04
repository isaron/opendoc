package com.cloud.security.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.platform.Constants;
import com.cloud.platform.HqlUtil;
import com.cloud.platform.IDao;
import com.cloud.platform.StringUtil;
import com.cloud.security.model.Resource;
import com.cloud.security.model.Role;
import com.cloud.security.model.User;
import com.cloud.security.util.RoleResUtil;

@Service
public class RoleService {
	
	public static Map<String, Set<String>> userResStrMap;

	@Autowired
	private IDao dao;
	
	/**
	 * check if has operate authority
	 * 
	 * @param res_op_str
	 * @return
	 */
	public boolean hasOperateAuth(String res_op_str) {
		
		if(Constants.isAdmin()) {
			return true;
		}
		
		String userId = Constants.getLoginUserId();
		
		if(userResStrMap == null) {
			userResStrMap = new HashMap();
		}
		
		if(!userResStrMap.containsKey(userId)) {
			// get user
			User u = (User) dao.getObject(User.class, userId);
			
			// get user roles
			if(StringUtil.isNullOrEmpty(u.getRoleIds())) {
				return false;
			}
			
			List<Role> roles = dao.getAllByHql("from Role where " + HqlUtil.combineInHql("id", u.getRoleIds(), true, true));
			
			// combine unique resource id set
			Set<String> resIds = new HashSet();
			
			for(Role r : roles) {
				if(StringUtil.isNullOrEmpty(r.getResourceIds())) {
					continue;
				}
				
				for(String resId : r.getResourceIds().split(",")) {
					resIds.add(resId);
				}
			}
			
			// get user operate resource strs
			List<String> list = dao.getAllByHql("select resStr from Resource where typeStr = ? and "
					+ HqlUtil.combineInHql("id", resIds, true, true), RoleResUtil.RES_TYPE_OP);
			
			// combine as resource str set
			Set<String> resStrs = new HashSet();
			
			for(String res : list) {
				resStrs.add(res);
			}
			
			userResStrMap.put(userId, resStrs);
		}
		
		return userResStrMap.get(userId).contains(res_op_str);
	}
	
	/**
	 * search all resources
	 * 
	 * @return
	 */
	public List<Resource> searchResources() {
		
		return dao.getAllByHql("from Resource order by sortSn");
	}
}
