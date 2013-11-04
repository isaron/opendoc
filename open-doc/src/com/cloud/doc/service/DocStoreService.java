package com.cloud.doc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.attach.Attach;
import com.cloud.doc.model.Directory;
import com.cloud.doc.model.DocFile;
import com.cloud.doc.util.DocUtil;
import com.cloud.platform.Constants;
import com.cloud.platform.DocConstants;
import com.cloud.platform.IDao;
import com.cloud.platform.SpringUtil;
import com.cloud.platform.StringUtil;
import com.cloud.platform.Tree;
import com.cloud.security.model.Department;

@Service
public class DocStoreService {

	@Autowired
	private IDao dao;
	
	/**
	 * create or update depart dir
	 * 
	 * @param depart
	 */
	public void saveDepartDir(Department depart) {
		
		Directory dir = getDirectory(depart.getId());
		
		if(dir == null) {
			dir = new Directory(depart.getId());
		}
		
		dir.setIsDepartDir(Constants.VALID_YES);
		dir.setName(depart.getName());
		dir.setParentId(depart.getParentId());
		
		dao.saveObject(dir);
	}
	
	/**
	 * upload files
	 * 
	 * @param parentId
	 * @param attachIds
	 */
	public void uploadFiles(String parentId, String attachIds) throws SchedulerException {
		
		if(StringUtil.isNullOrEmpty(attachIds)) {
			return;
		}
		
		for(String attachId : attachIds.split(",")) {
			attachId = attachId.trim();
			Attach attach = (Attach) dao.getObject(Attach.class, attachId);
			
			// save file
			DocFile file = new DocFile();
			file.setId(Constants.getID());
			file.setUniqueId(file.getId());
			file.setIsLatest(Constants.VALID_YES);
			file.setDocVersion(DocUtil.getVersion(true, null));
			file.setName(attach.getFileName());
			file.setParentId(parentId);
			file.setCreateTime(new Date());
			file.setCreator(Constants.getLoginUserId());
			
			dao.saveObject(file);
			
			// update attach entity relate
			attach.setEntityId(file.getId());
			
			// convert doc file for online view
			if (DocConstants.isOffice(attach.getExtendType())
					|| "pdf".equals(attach.getExtendType().toLowerCase())) {
				
				convertOnlineDoc(attach);
			}
			
			dao.saveObject(attach);
		}
	}
	
	/**
	 * convert document for online view
	 * 
	 * @param attach
	 */
	private void convertOnlineDoc(Attach attach) throws SchedulerException {
		
		// init mail send job
		JobDetail jobDetail = new JobDetail(Constants.getID(), DocConvertJob.class);
		
		jobDetail.getJobDataMap().put("attach", attach);
		
		// trigger once and now
		SimpleTrigger trigger = new SimpleTrigger(Constants.getID());
		trigger.setStartTime(new Date());
		trigger.setRepeatCount(0);
		trigger.setRepeatInterval(1000);  // no sense, for not throw exception
		
		// get scheduler
		Scheduler scheduler = (Scheduler) SpringUtil.getBean("scheduler");
		
		// schedule job
		scheduler.scheduleJob(jobDetail, trigger);
	}
	
	/**
	 * get dir by id
	 * 
	 * @param dirId
	 * @return
	 */
	public Directory getDirectory(String dirId) {
		
		if(StringUtil.isNullOrEmpty(dirId)) {
			return new Directory();
		}
		
		return (Directory) dao.getObject(Directory.class, dirId);
	}

	/**
	 * remove dir or file
	 * 
	 * @param docId
	 * @param isDir
	 */
	public void remove(String docId, boolean isDir) {
		
		if(isDir) {
			dao.removeById(Directory.class, docId);
		} else {
			dao.removeById(DocFile.class, docId);
		}
	}
	
	/**
	 * rename dir or file
	 * 
	 * @param docId
	 * @param newName
	 */
	public void rename(String docId, String newName, boolean isDir) {
		
		if(StringUtil.isNullOrEmpty(docId, newName)) {
			return;
		}
		
		Tree doc = null;
		
		if(isDir) {
			doc = (Tree) dao.getObject(Directory.class, docId);
		} else {
			doc = (Tree) dao.getObject(DocFile.class, docId);
		}
		
		doc.setName(newName);
		
		dao.saveObject(doc);
	}
	
	/**
	 * search dirs
	 * 
	 * @param parentId
	 * @return
	 */
	public List<Directory> searchDirs(String parentId) {
		
		if(StringUtil.isNullOrEmpty(parentId)) {
			return dao.getAllByHql("from Directory where parentId = '' or parentId is null order by isDepartDir,name");
		} else {
			return dao.getAllByHql("from Directory where parentId = ? order by isDepartDir,name", parentId);
		}
	}
	
	/**
	 * search files
	 * 
	 * @param parentId
	 * @return
	 */
	public List<DocFile> searchFiles(String parentId) {
		// search files
		StringBuffer hql = new StringBuffer();
		hql.append("select f,a from DocFile f,Attach a where f.id = a.entityId and f.isLatest = ?");
		
		if(StringUtil.isNullOrEmpty(parentId)) {
			hql.append(" and (f.parentId = '' or f.parentId is null)");
		} else {
			hql.append(" and f.parentId = '" + parentId + "'");
		}
		
		hql.append(" order by f.name");
		List<Object[]> list = dao.getAllByHql(hql.toString(), Constants.VALID_YES);
		
		// combine file attach
		List newList = new ArrayList();
		
		for(Object[] obj : list) {
			DocFile file = (DocFile) (obj[0]);
			Attach attach = (Attach) (obj[1]);
			
			file.setAttach(attach);
			newList.add(file);
		}
		
		return newList;
	}
	
	/**
	 * create new dir
	 * 
	 * @return
	 */
	public String createDir(String parentId) {
		String id = Constants.getID();
		
		Directory dir = new Directory(id);
		dir.setName(DocUtil.DEFAULT_DIR_NAME);
		dir.setParentId(parentId);
		
		dao.saveObject(dir);
		return id;
	}
}
