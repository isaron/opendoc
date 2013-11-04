package com.cloud.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cloud.platform.Constants;

@Entity
@Table(name = "t_sys_user")
public class User {

	private String id;
	private String isValid = Constants.VALID_YES;
	private String username;
	private String password;
	private String email;
	private String address;
	private String departmentId;
	private String positionId;
	private String roleIds;
	
	private int pad = 5;
	private String positionName;
	
	@Id
	@Column(unique = true, nullable = false)
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(length = 1)
	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	
	@Column(length = 255)
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column(length = 255)
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(length = 255)
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(length = 255)
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(length = 36)
	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	
	@Column(length = 36)
	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
	
	@Column(length = 2048)
	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	
	@Transient
	public int getPad() {
		return pad;
	}

	public void setPad(int pad) {
		this.pad = pad;
	}
	
	@Transient
	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
}
