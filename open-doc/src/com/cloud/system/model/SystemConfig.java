package com.cloud.system.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_sys_config")
public class SystemConfig {

	private String id;
	private String version;
	private String systemName;
	private int codeSn;
	private int attachMaxSize;

	@Id
	@Column(unique = true, nullable = false)
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(length = 36)
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	@Column(length = 255)
	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	
	@Column(length = 10)
	public int getCodeSn() {
		return codeSn;
	}

	public void setCodeSn(int codeSn) {
		this.codeSn = codeSn;
	}
	
	@Column(length = 6)
	public int getAttachMaxSize() {
		return attachMaxSize;
	}

	public void setAttachMaxSize(int attachMaxSize) {
		this.attachMaxSize = attachMaxSize;
	}
}
