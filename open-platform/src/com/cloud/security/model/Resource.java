package com.cloud.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_user_res")
public class Resource {

	private String id;
	private String typeStr;
	private String catStr;
	private String resStr;
	private String name;
	private String intro;
	private int sortSn;
	
	@Id
	@Column(unique = true, nullable = false)
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(length = 255)
	public String getTypeStr() {
		return typeStr;
	}
	
	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}
	
	@Column(length = 255)
	public String getCatStr() {
		return catStr;
	}

	public void setCatStr(String catStr) {
		this.catStr = catStr;
	}
	
	@Column(length = 255)
	public String getResStr() {
		return resStr;
	}
	
	public void setResStr(String resStr) {
		this.resStr = resStr;
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
	
	@Column(length = 5)
	public int getSortSn() {
		return sortSn;
	}

	public void setSortSn(int sortSn) {
		this.sortSn = sortSn;
	}
}
