package com.cloud.platform;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public class Tree {

	private String id;
	private String name;
	private String parentId;
	
	private int level;
	private int pad;
	private boolean hasChild;
	
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

	@Column(length = 36)
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	@Transient
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	@Transient
	public int getPad() {
		return pad;
	}
	
	public void setPad(int pad) {
		this.pad = pad;
	}

	@Transient
	public boolean isHasChild() {
		return hasChild;
	}

	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}
}
