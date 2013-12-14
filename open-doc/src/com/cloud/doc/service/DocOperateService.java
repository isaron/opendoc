package com.cloud.doc.service;

import java.util.Date;

import com.cloud.doc.model.DocRecord;
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
	public String checkin(String docFileId, String attachId, String note) throws SchedulerException {
		
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
		
		newFile.setCreator(originFile.getCreator());
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

        // save checkin record
        saveCheckRecord(docFileId, note, "检入");
		
		return newFile.getId();
	}
	
	/**
	 * check out file
	 * 
	 * @param docFileId
	 * @return
	 */
	public synchronized boolean checkout(String docFileId, String note) {
		
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

        // save record
        saveCheckRecord(docFileId, note, "检出");

		return true;
	}

    /**
     * save checkout or checkin record
     *
     * @param docId
     * @param note
     * @param operate
     */
    public void saveCheckRecord(String docId, String note, String operate) {

        if(StringUtil.isNullOrEmpty(docId)) {
            return;
        }

        DocRecord record = new DocRecord();
        record.setDocId(docId);
        record.setNote(note);
        record.setOperate(operate);
        record.setCreator(Constants.getLoginUserId());
        record.setCreateTime(new Date());

        dao.saveObject(record);
    }
}
