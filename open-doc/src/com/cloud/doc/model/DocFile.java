package com.cloud.doc.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cloud.attach.Attach;
import com.cloud.platform.Tree;

@Entity
@Table(name = "t_doc_file")
public class DocFile extends Tree {

	private int status;
	private String uniqueId;
	private String isLatest;
	private String docVersion;
    private String checkoutor;

    private String creator;
	private Date createTime;

    // transparent
	private Attach attach;
    private boolean canCheckin;
    private String fileBgStyle;

    @Column(length = 1)
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(length = 36)
	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	@Column(length = 1)
	public String getIsLatest() {
		return isLatest;
	}

	public void setIsLatest(String isLatest) {
		this.isLatest = isLatest;
	}

	@Column(length = 100)
	public String getDocVersion() {
		return docVersion;
	}

	public void setDocVersion(String docVersion) {
		this.docVersion = docVersion;
	}

    @Column(length = 36)
    public String getCheckoutor() {
        return checkoutor;
    }

    public void setCheckoutor(String checkoutor) {
        this.checkoutor = checkoutor;
    }
	
	@Column(length = 36)
	public String getCreator() {
		return creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@Column
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Transient
	public Attach getAttach() {
		return attach;
	}

	public void setAttach(Attach attach) {
		this.attach = attach;
	}

    @Transient
    public boolean isCanCheckin() {
        return canCheckin;
    }

    public void setCanCheckin(boolean canCheckin) {
        this.canCheckin = canCheckin;
    }

    @Transient
    public String getFileBgStyle() {
        return fileBgStyle;
    }

    public void setFileBgStyle(String fileBgStyle) {
        this.fileBgStyle = fileBgStyle;
    }
}
