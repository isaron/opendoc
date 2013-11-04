package com.cloud.attach;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_sys_attach")
public class Attach {

	private String id;
	private String entityId;
	
	private String fileName;
	private String extendType;
	private int fileSize;
	
	@Id
	@Column(unique = true, nullable = false)
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(length = 36)
	public String getEntityId() {
		return entityId;
	}
	
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	
	@Column(length = 255)
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Column(length = 10)
	public String getExtendType() {
		return extendType;
	}
	
	public void setExtendType(String extendType) {
		this.extendType = extendType;
	}
	
	@Column(length = 5)
	public int getFileSize() {
		return fileSize;
	}
	
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
}
