package com.cloud.security.service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.platform.Constants;
import com.cloud.platform.IDao;
import com.cloud.platform.MD5Util;
import com.cloud.platform.StringUtil;
import com.cloud.security.model.Department;
import com.cloud.security.model.Position;
import com.cloud.security.model.User;

@Service
public class UserService {

	@Autowired
	private IDao dao;
	
	/**
	 * remove department
	 * 
	 * @param departId
	 */
	public void removeDepart(String departId) {
		
		if(StringUtil.isNullOrEmpty(departId)) {
			return;
		}
		
		Department depart = getDepartment(departId);
		
		// reset its children departments
		String hql = "update Department set parentId = ? where parentId = ?";
		dao.updateByHql(hql, new Object[] {depart.getParentId(), departId});
		
		// reset its users
		hql = "update User set departmentId = ? where departmentId = ?";
		dao.updateByHql(hql, new Object[] {depart.getParentId(), departId});
		
		// remove department
		dao.removeObject(depart);
		Constants.departTreeMap = null;
	}
	
	/**
	 * search department and user structure data
	 * 
	 * @param parentId
	 * @return
	 */
	public List searchStructureData(String parentId) {
		
		// init root hql or child hql
		String hql = null, departHql = null, userHql = null;
		
		if(StringUtil.isNullOrEmpty(parentId)) {
			departHql = "parentId is null or parentId = ''";
			userHql = "(departmentId is null or departmentId = '')";
		} else {
			departHql = "parentId = '" + parentId + "'";
			userHql = "departmentId = '" + parentId + "'";
		}
		
		// search child departments
		hql = "select id from Department where " + departHql + " order by name";
		List<String> departIds = dao.getAllByHql(hql);
		
		List<Department> departs = new ArrayList();
		
		for(String departId : departIds) {
			departs.add(Constants.getDepartTreeMap().get(departId));
		}
		
		// search department users
		hql = "from User where " + userHql + " and username != 'admin'";
		hql += " and (isValid is null or isValid != 'N') order by username";
		List<User> users = dao.getAllByHql(hql);
		
		// get department level padding for user padding
		int pad = 5;
		
		if(!StringUtil.isNullOrEmpty(parentId) && Constants.getDepartTreeMap().containsKey(parentId)) {
			pad = Constants.getDepartTreeMap().get(parentId).getPad() + 18;
		}
		
		// get user position map
		hql = "from Position";
		List<Position> positions = dao.getAllByHql(hql);
		Map<String, String> userPosMap = new HashMap();
		
		for(Position p : positions) {
			userPosMap.put(p.getId(), p.getName());
		}
		
		// set user additional info
		for(User u : users) {
			u.setPad(pad);
			u.setPositionName(userPosMap.get(u.getPositionId()));
		}
		
		// add datas
		List datas = new ArrayList();
		datas.addAll(departs);
		datas.addAll(users);
		
		return datas;
	}
	
	/**
	 * search departments
	 * 
	 * @return
	 */
	public List<Department> searchDeparts() {
		
		List<Department> departs = dao.getAllByHql("from Department");
		
		for(Department depart : departs) {
			depart.setManager(Constants.getUsernameById(depart.getManagerId()));
		}
		
		return departs;
	}
	
	/**
	 * save department
	 * 
	 * @param depart
	 */
	public void saveDepart(Department depart) {
		
		if(StringUtil.isNullOrEmpty(depart.getId())) {
			depart.setId(Constants.getID());
		}
		
		dao.saveObject(depart);
		Constants.departTreeMap = null;
	}
	
	/**
	 * get department by id
	 * 
	 * @param departId
	 * @return
	 */
	public Department getDepartment(String departId) {
		
		if(StringUtil.isNullOrEmpty(departId)) {
			return new Department();
		}
		
		return (Department) dao.getObject(Department.class, departId);
	}
	
	/**
	 * remove user
	 * 
	 * @param userId
	 */
	public void removeUser(String userId) {
		
		if(StringUtil.isNullOrEmpty(userId)) {
			return;
		}
		
		User u = getUserById(userId);
		u.setIsValid(Constants.VALID_NO);
		
		dao.saveObject(u);
		
		// reset user cache
		Constants.userIdNameMap = null;
		Constants.userNameIdMap = null;
		Constants.departTreeMap = null;
	}
	
	/**
	 * change password
	 * 
	 * @param userId
	 * @param newPassword
	 * @throws NoSuchAlgorithmException 
	 */
	public void changePwd(String userId, String newPassword) throws NoSuchAlgorithmException {
		
		if(StringUtil.isNullOrEmpty(userId, newPassword)) {
			return;
		}
		
		User u = getUserById(userId);
		u.setPassword(MD5Util.MD5(newPassword));
		
		dao.saveObject(u);
	}
	
	/**
	 * get user by user id
	 * 
	 * @param userId
	 * @return
	 */
	public User getUserById(String userId) {
		
		if(StringUtil.isNullOrEmpty(userId)) {
			return new User();
		}
		
		return (User) dao.getObject(User.class, userId);
	}
	
	/**
	 * search type users
	 * 
	 * @param type
	 * @return
	 */
	public List<User> searchUsers() {
		
		// search users
		String hql = "from User where username != 'admin' and (isValid is null or isValid != 'N') order by username";
		List<User> users = dao.getAllByHql(hql);
		
		// combine user properties
		combineUser(users);
		
		return users;
	}
	
	/**
	 * search users which and me are in the same department
	 * 
	 * @param loginUser
	 * @return
	 */
	public List<User> searchDepartUsers(String loginUser) {
		
		// search users
		StringBuilder hql = new StringBuilder();
		hql.append("from User where departmentId = (select departmentId from User where id = ?)");
		hql.append(" and username != 'admin' and (isValid is null or isValid != 'N') order by username");
		
		List<User> users = dao.getAllByHql(hql.toString(), loginUser);
		
		// combine user properties
		combineUser(users);
		
		return users;
	}
	
	/**
	 * combine user properties
	 * 
	 * @param users
	 */
	private void combineUser(List<User> users) {
		// get user position map
		String hql = "from Position";
		List<Position> positions = dao.getAllByHql(hql);
		Map<String, String> userPosMap = new HashMap();
		
		for(Position p : positions) {
			userPosMap.put(p.getId(), p.getName());
		}
		
		// combine position name
		for(User u : users) {
			u.setPositionName(userPosMap.get(u.getPositionId()));
			
			// don't show user password to front page
			u.setPassword(null);
		}
	}
	
	/**
	 * save user
	 * 
	 * @param username
	 * @param password
	 * @param email
	 * @param address
	 */
	public void saveUser(User user) {
		
		if(StringUtil.isNullOrEmpty(user.getId())) {
			user.setId(Constants.getID());
		}
		
		user.setIsValid(Constants.VALID_YES);
		dao.saveObject(user);
		
		// reset user cache
		Constants.userIdNameMap = null;
		Constants.userNameIdMap = null;
	}
}
