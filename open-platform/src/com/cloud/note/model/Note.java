package com.cloud.note.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_sys_note")
public class Note {

	private String id;
	private String entityId;
	
	private String note;
	private String creatorId;
	private Date createTime;
	
	private String creator;
	
	@Id
	@Column(unique = true, nullable = false)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
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
	
	@Column(length = 2048)
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	@Column(length = 36)
	public String getCreatorId() {
		return creatorId;
	}
	
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	
	@Column
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Transient
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
}
