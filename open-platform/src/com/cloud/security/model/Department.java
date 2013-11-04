package com.cloud.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cloud.platform.Tree;

@Entity
@Table(name = "t_user_depart")
public class Department extends Tree {

	private String managerId;
	private String assistantIds;
	
	private String manager;
	
	@Column(length = 36)
	public String getManagerId() {
		return managerId;
	}
	
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	
	@Column(length = 2048)
	public String getAssistantIds() {
		return assistantIds;
	}
	
	public void setAssistantIds(String assistantIds) {
		this.assistantIds = assistantIds;
	}
	
	@Transient
	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}
}
