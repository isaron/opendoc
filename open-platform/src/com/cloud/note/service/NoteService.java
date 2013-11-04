package com.cloud.note.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.note.model.Note;
import com.cloud.platform.Constants;
import com.cloud.platform.IDao;
import com.cloud.platform.SearchVo;
import com.cloud.platform.StringUtil;

@Service
public class NoteService {

	@Autowired
	private IDao dao;
	
	/**
	 * search entity notes
	 * 
	 * @param entityId
	 * @param searchVo
	 * @return
	 */
	public List<Note> searchNotes(String entityId, SearchVo searchVo) {
		
		String hql = "from Note where entityId = '" + entityId + "' order by createTime desc";
		
		List<Note> notes = dao.getPageByHql(hql, searchVo);
		
		// set creator name
		for(Note n : notes) {
			n.setCreator(Constants.getUsernameById(n.getCreatorId()));
		}
		
		return notes;
	}
	
	/**
	 * save note
	 * 
	 * @param entityId
	 * @param note
	 * @param creatorId
	 */
	public void saveNote(String entityId, String note, String creatorId) {
		
		if(StringUtil.isNullOrEmpty(entityId, note, creatorId)) {
			return;
		}
		
		Note n = new Note();
		n.setEntityId(entityId);
		n.setNote(note);
		n.setCreatorId(creatorId);
		n.setCreateTime(new Date());
		
		dao.saveObject(n);
	}
}
