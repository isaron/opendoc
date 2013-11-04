package com.cloud.platform;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.cloud.security.model.Department;
import com.cloud.security.model.User;
import com.cloud.security.service.UserService;

public class Constants {

	public static final String VALID_YES = "Y";
	public static final String VALID_NO = "N";
	
	public static String BASEPATH;
	
	/*
	 * ================== name id map ==================
	 */
	public static Map<String, String> userNameIdMap;
	public static Map<String, String> userIdNameMap;
	public static Map<String, Department> departTreeMap;
	
	public static String getID() {
		return UUID.randomUUID().toString();
	}
	
	/**
	 * convert object to json string
	 */
	public static String toJSON(Object obj) {
		JSONObject o = JSONObject.fromObject(obj);
		return o.toString();
	}
	
	public static String toJSON(List list) {
		JSONArray arr = JSONArray.fromObject(list);
		return arr.toString();
	}
	
	/**
	 * check if is image
	 * 
	 * @param type
	 * @return
	 */
	public static boolean isImage(String type) {
		type = type.toUpperCase();
		
		return type.equals("JPG") || type.equals("JPEG") || type.equals("GIF")
				|| type.equals("PNG") || type.equals("BMP");
	}
	
	
	/**
	 * ========================= user operate =========================
	 */
	public static String getLoginUserId() {
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		
		String userId = getUserNameToIdMap().get(userDetails.getUsername());
		
		return StringUtil.isNullOrEmpty(userId) ? "" : userId;
	}
	
	public static boolean isAdmin() {
		
		Object userDetails = SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		
		if(!(userDetails instanceof UserDetails)) {
			return false;
		}
		
		return "admin".equals(((UserDetails) userDetails).getUsername());
	}

	public static String getUsernameById(String userId) {
		if(StringUtil.isNullOrEmpty(userId)) {
			return "";
		}
		
		return getUserIdToNameMap().get(userId);
	}
	
	public static String getUserIdByName(String username) {
		if(StringUtil.isNullOrEmpty(username)) {
			return "";
		}
		
		return getUserIdByName(username);
	}
	
	public static Map<String, String> getUserNameToIdMap() {
		
		if(userNameIdMap == null) {
			userNameIdMap = new HashMap();
			
			UserService userSerivce = (UserService) SpringUtil.getBean("userService");
			List<User> users = userSerivce.searchUsers();
			
			for(User u : users) {
				userNameIdMap.put(u.getUsername(), u.getId());
			}
			
			userNameIdMap.put("admin", "000000000000000000000000000000000000");
		}
		
		return userNameIdMap;
	}
	
	public static Map<String, String> getUserIdToNameMap() {
		
		if(userIdNameMap == null) {
			userIdNameMap = new HashMap();
			
			UserService userSerivce = (UserService) SpringUtil.getBean("userService");
			List<User> users = userSerivce.searchUsers();
			
			for(User u : users) {
				userIdNameMap.put(u.getId(), u.getUsername());
			}
			
			userIdNameMap.put("000000000000000000000000000000000000", "admin");
		}
		
		return userIdNameMap;
	}
	
	/**
	 * ========================= department operate =========================
	 */
	public static Map<String, Department> getDepartTreeMap() {
		
		if(departTreeMap == null) {
			departTreeMap = new HashMap();
			
			// get all departments
			IDao dao = (IDao) SpringUtil.getBean("dao");
			List<Department> departs = dao.getAllByHql("from Department");
			
			// sort departments
			List<Department> sorted = TreeUtil.sortTree(departs);
			
			// get user department map
			UserService userSerivce = (UserService) SpringUtil.getBean("userService");
			List<User> users = userSerivce.searchUsers();
			Set<String> userDepartSet = new HashSet();
			
			for(User u : users) {
				if(!StringUtil.isNullOrEmpty(u.getDepartmentId())) {
					userDepartSet.add(u.getDepartmentId());
				}
			}
			
			// add department
			for(Department d : sorted) {
				if(userDepartSet.contains(d.getId())) {
					d.setHasChild(true);
				}
				
				departTreeMap.put(d.getId(), d);
			}
		}
		
		return departTreeMap;
	}
} 
