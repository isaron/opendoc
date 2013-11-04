package com.cloud.note.web;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloud.note.model.Note;
import com.cloud.note.service.NoteService;
import com.cloud.platform.Constants;
import com.cloud.platform.SearchVo;

@Controller
@RequestMapping("note")
public class NoteBean {
	
	public final int NOTE_SIZE = 5;

	@Autowired
	private NoteService noteService;
	
	/**
	 * get entity notes
	 * 
	 * @param entityId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getNotes.do")
	public String getNotes(@RequestParam("entityId") String entityId, @RequestParam("page") int page) {
		
		// init note search vo
		SearchVo searchVo = new SearchVo();
		searchVo.setPage(page);
		searchVo.setPageSize(NOTE_SIZE);
		
		// search notes
		List<Note> notes = noteService.searchNotes(entityId, searchVo);
		
		String hasMore = searchVo.getPage() < searchVo.getPageNum() ? Constants.VALID_YES
				: Constants.VALID_NO;
		
		// combine result
		JSONObject result = new JSONObject();
		result.put("hasMore", hasMore);
		result.put("notes", notes);
		
		return result.toString();
	}
	
	/**
	 * save note
	 * 
	 * @param entityId
	 * @param param
	 */
	@ResponseBody
	@RequestMapping("/saveNote.do")
	public void saveNote(@RequestParam("entityId") String entityId,
			@RequestParam("note") String note) {
		
		noteService.saveNote(entityId, note, Constants.getLoginUserId());
	}
}
