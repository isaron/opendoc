package com.cloud.doc.service;

import java.util.Date;

import com.cloud.platform.*;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.attach.Attach;
import com.cloud.doc.model.DocFile;
import com.cloud.doc.util.DocUtil;

@Service
public class DocOperateService {

	@Autowired
	private IDao dao;
	
	/**
	 * check in file
	 * 
	 * @param docFileId
	 * @param attachId
	 */
	public String checkin(String docFileId, String attachId) throws SchedulerException {
		
		if(StringUtil.isNullOrEmpty(docFileId, attachId)) {
			return "";
		}
		
		DocFile originFile = (DocFile) dao.getObject(DocFile.class, docFileId);
		
		// create new version file
		DocFile newFile = new DocFile();
		newFile.setId(Constants.getID());
		newFile.setName(originFile.getName());
		newFile.setParentId(originFile.getParentId());
		
		newFile.setStatus(DocUtil.DOC_STATUS_NORMAL);
		newFile.setUniqueId(originFile.getUniqueId());
		newFile.setIsLatest(Constants.VALID_YES);
		newFile.setDocVersion(DocUtil.getVersion(false, originFile.getDocVersion()));
		
		newFile.setCreator(Constants.getLoginUserId());
		newFile.setCreateTime(new Date());
		
		dao.saveObject(newFile);
		
		// update attach to new version file
		Attach newAttach = (Attach) dao.getObject(Attach.class, attachId.replaceAll("\r\n", ""));
		newAttach.setEntityId(newFile.getId());
		
		dao.saveObject(newAttach);

        // convert doc file for online view
        DocStoreService docService = (DocStoreService) SpringUtil.getBean("docStoreService");
        docService.convertOnlineDoc(newAttach);
		
		// update origin doc file status
		originFile.setStatus(DocUtil.DOC_STATUS_NORMAL);
		originFile.setIsLatest(Constants.VALID_NO);
		
		dao.saveObject(originFile);
		
		return newFile.getId();
	}
	
	/**
	 * check out file
	 * 
	 * @param docFileId
	 * @return
	 */
	public synchronized boolean checkout(String docFileId) {
		
		if(StringUtil.isNullOrEmpty(docFileId)) {
			return false;
		}

        DocFile file = (DocFile) dao.getObject(DocFile.class, docFileId);
		
		// check file if has been checkout-ed
        if(DocUtil.DOC_STATUS_CHECKOUT == file.getStatus()) {
            return false;
        }
		
		// set check out status
		file.setStatus(DocUtil.DOC_STATUS_CHECKOUT);
        file.setCheckoutor(Constants.getLoginUserId());
		
		dao.saveObject(file);
		return true;
	}
}
