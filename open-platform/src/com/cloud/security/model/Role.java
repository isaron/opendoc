package com.cloud.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_user_role")
public class Role {

	private String id;
	private String name;
	private String intro;
	private String resourceIds;
	
	@Id
	@Column(unique = true, nullable = false)
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(length = 255)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(length = 2048)
	public String getIntro() {
		return intro;
	}
	
	public void setIntro(String intro) {
		this.intro = intro;
	}
	
	@Column(length = 2048)
	public String getResourceIds() {
		return resourceIds;
	}
	
	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}
}
