package com.cloud.security.web;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cloud.platform.Constants;
import com.cloud.platform.MD5Util;
import com.cloud.security.model.Department;
import com.cloud.security.model.User;
import com.cloud.security.service.UserService;

public class UserBean {
	
	private static Logger logger = Logger.getLogger(UserBean.class);
	
	@Autowired
	private UserService userService;
	
	/**
	 * remove department
	 * 
	 * @param departId
	 */
	@RequestMapping("/removeDepart.do")
	public String removeDepart(@RequestParam("departId") String departId) {
		
		userService.removeDepart(departId);
		
		return "account/structure";
	}
	
	/**
	 * get structure table data
	 * 
	 * @param parentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getStructure.do")
	public String getStructure(@RequestParam("parentId") String parentId) {
		
		List datas = userService.searchStructureData(parentId);
		
		// combine data for page
		JSONArray result = new JSONArray();
		JSONObject obj = null;
		Department depart = null;
		User user = null;
		boolean isDepart = true;
		
		for(Object data : datas) {
			obj = new JSONObject();
			
			if(data instanceof Department) {
				depart = (Department) data;
				isDepart = true;
			} else {
				user = (User) data;
				isDepart = false;
			}

			obj.put("isDepart", isDepart ? Constants.VALID_YES : Constants.VALID_NO);
			obj.put("id", isDepart ? depart.getId() : user.getId());
			obj.put("parentId", isDepart ? depart.getParentId() : user.getDepartmentId());
			
			obj.put("hasChild", (isDepart && depart.isHasChild()) ? Constants.VALID_YES : Constants.VALID_NO);
			obj.put("pad", !isDepart ? user.getPad() : depart.getPad());
			obj.put("departName", !isDepart ? "" : depart.getName());
			
			obj.put("username", !isDepart ? user.getUsername() : "");
			obj.put("positionName", !isDepart ? user.getPositionName() : "");
			obj.put("email", !isDepart ? user.getEmail() : "");
			obj.put("address", !isDepart ? user.getAddress() : "");
			
			result.add(obj);
		}
		
		return result.toString();
	}
	
	/**
	 * get departments list info
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDeparts.do")
	public String getDeparts() {

		// search users
		List<Department> departs = userService.searchDeparts();
		
		// convert to json format
		JSONArray departsArr = JSONArray.fromObject(departs);
		
		return departsArr.toString();
	}
	
	/**
	 * save department
	 * 
	 * @param depart
	 */
	@RequestMapping("/saveDepart.do")
	public String saveDepart(Department depart) {
		
		userService.saveDepart(depart);
		
		return "account/structure";
	}
	
	/**
	 * add department info before create or edit department
	 * 
	 * @param projectId
	 * @return
	 */
	@RequestMapping("/createOrEditDepart.do")
	public ModelAndView createOrEditDepart(
			@RequestParam(value = "departId", required = false) String departId) {
		
		ModelAndView mv = new ModelAndView("account/departAdd");
		
		Department depart = userService.getDepartment(departId);
		mv.addObject("depart", depart);
		
		return mv;
	}
	
	/**
	 * check if is admin
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/isAdmin.do")
	public String isAdmin() {
		
		return Constants.isAdmin() ? Constants.VALID_YES : Constants.VALID_NO;
	}
	
	/**
	 * get user's password
	 * 
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getPassword.do")
	public String getPassword(@RequestParam("userId") String userId) {
		
		User u = userService.getUserById(userId);
		
		return u.getPassword();
	}
	
	/**
	 * remove user
	 * 
	 * @param userId
	 */
	@ResponseBody
	@RequestMapping("/removeUser.do")
	public void removeUser(@RequestParam("userId") String userId) {
		
		userService.removeUser(userId);
	}
	
	/**
	 * chagne password
	 * 
	 * @param password
	 */
	@ResponseBody
	@RequestMapping("/changePwd.do")
	public void changePwd(@RequestParam("password") String password) {
		
		try {
			userService.changePwd(Constants.getLoginUserId(), password);
			
		} catch(Exception e) {
			logger.error("***** 异常信息 ***** 方法：changePwd", e);
		}
	}
	
	/**
	 * unlock system
	 * 
	 * @param password
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/unlock.do")
	public String unlock(@RequestParam("password") String password) {
		
		boolean isPwdEqual = false;
		
		try {
			User u = userService.getUserById(Constants.getLoginUserId());
			
			isPwdEqual = u != null && u.getPassword().equals(MD5Util.MD5(password));
			
		} catch(Exception e) {
			logger.error("***** 异常信息 ***** 方法：unlock", e);
		}
		
		return isPwdEqual ? Constants.VALID_YES : Constants.VALID_NO;
	}
	
	/**
	 * account logout
	 * 
	 * @param session
	 * @param response
	 */
	@RequestMapping("/logout.do")
	public void logout(HttpSession session, HttpServletResponse response) {

		try {
			if(session != null) {
				session.invalidate();
			}
			
			response.sendRedirect(Constants.BASEPATH + "index.jsp");
			
		} catch(Exception e) {
			logger.error("***** 异常信息 ***** 方法：logout", e);
		}
	}
	
	/**
	 * get type users list info
	 * 
	 * @param type
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getUsers.do")
	public String getUsers() {

		// search users
		List<User> users = userService.searchUsers();
		
		// convert to json format
		JSONArray usersArr = JSONArray.fromObject(users);
		
		return usersArr.toString();
	}
	
	/**
	 * search users which and me are in the same department
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDepartUsers.do")
	public String getDepartUsers() {
		
		List<User> users = userService.searchDepartUsers(Constants.getLoginUserId());
		
		return JSONArray.fromObject(users).toString();
	}

	/**
	 * save user
	 * 
	 * @param type
	 * @param username
	 * @param password
	 * @param email
	 * @param address
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveUser.do")
	public String saveUser(User user) {
		
		userService.saveUser(user);
		
		return "";
	}
}
