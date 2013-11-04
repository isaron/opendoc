package com.cloud.doc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cloud.platform.Tree;

@Entity
@Table(name = "t_doc_dir")
public class Directory extends Tree {

	private String isDepartDir;
	
	public Directory() {}
	
	public Directory(String id) {
		super.setId(id);
	}

	@Column(length = 1)
	public String getIsDepartDir() {
		return isDepartDir;
	}

	public void setIsDepartDir(String isDepartDir) {
		this.isDepartDir = isDepartDir;
	}
}
