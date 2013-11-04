package com.cloud.attach;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.platform.HqlUtil;
import com.cloud.platform.IDao;
import com.cloud.platform.StringUtil;

@Service
public class AttachService {

	@Autowired
	private IDao dao;
	
	/**
	 * search entity's attachs
	 * 
	 * @param bugId
	 * @return
	 */
	public List<Attach> searchEntityAttachs(String entityId) {
		
		if(StringUtil.isNullOrEmpty(entityId)) {
			return new ArrayList();
		}
		
		String hql = "from Attach where entityId = ?";
		
		return dao.getAllByHql(hql, entityId);
	}
	
	/**
	 * save entity attach
	 * 
	 * @param attachId
	 * @param fileName
	 * @param extendType
	 * @param fileSize
	 */
	public void saveEntityAttach(String attachId, String fileName, String extendType, int fileSize) {
		
		Attach attach = new Attach();
		
		attach.setId(attachId);
		attach.setFileName(fileName);
		attach.setExtendType(extendType);
		attach.setFileSize(fileSize);
		
		dao.saveObject(attach);
	}
	
	/**
	 * update entityId attachs' entityId id
	 * 
	 * @param bugId
	 * @param attachIds
	 */
	public void updateEntityAttach(String entityId, String attachIds) {
		
		if(StringUtil.isNullOrEmpty(entityId, attachIds)) {
			return;
		}
		
		// filter attach ids content
		attachIds = attachIds.replaceAll("\r\n", "");
		
		// combine bug id
		String hql = "update Attach set entityId = ? where " + HqlUtil.combineInHql("id", attachIds, true, true);
		dao.updateByHql(hql, entityId);
	}
}
