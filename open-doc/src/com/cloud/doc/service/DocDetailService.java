package com.cloud.doc.service;

import java.util.List;

import com.cloud.platform.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.attach.Attach;
import com.cloud.doc.model.DocFile;
import com.cloud.platform.IDao;
import com.cloud.platform.StringUtil;

@Service
public class DocDetailService {

	@Autowired
	private IDao dao;
	
	/**
	 * get doc file
	 * 
	 * @param docId
	 * @return
	 */
	public DocFile getDocFile(String docId) {
	
		if(StringUtil.isNullOrEmpty(docId)) {
			return new DocFile();
		}
		
		String hql = "select f,a from DocFile f,Attach a where f.id = a.entityId and f.id = ?";
		List<Object[]> list = dao.getAllByHql(hql, docId);
		
		if(list.isEmpty()) {
			return new DocFile();
		}
		
		Object[] info = list.get(0);
		DocFile docFile = (DocFile) info[0];
		
		docFile.setAttach((Attach) info[1]);
        docFile.setCanCheckin(Constants.getLoginUserId().equals(docFile.getCheckoutor()));
		
		return docFile;
	}
}
