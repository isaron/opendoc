package com.cloud.doc.web;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cloud.doc.model.Directory;
import com.cloud.doc.model.DocFile;
import com.cloud.doc.service.DocStoreService;
import com.cloud.platform.Constants;

@Controller
@RequestMapping("docstore")
public class DocStoreBean {
	
	private static Logger logger = Logger.getLogger(DocStoreBean.class);

	@Autowired
	private DocStoreService docStoreService;
	
	/**
	 * upload files
	 * 
	 * @param parentId
	 * @param attachIds
	 */
	@ResponseBody
	@RequestMapping("/uploadFiles.do")
	public void uploadFiles(@RequestParam("parentId") String parentId,
			@RequestParam("attachIds") String attachIds) {
		
		try {
			docStoreService.uploadFiles(parentId, attachIds);
			
		} catch(Exception e) {
			logger.error("***** 异常信息 ***** 方法：convert document", e);
		}
	}
	
	/**
	 * remove dir or file
	 * 
	 * @param docId
	 * @param isDir
	 */
	@ResponseBody
	@RequestMapping("/remove.do")
	public void remove(@RequestParam("docId") String docId, @RequestParam("isDir") String isDir) {
		
		docStoreService.remove(docId, Constants.VALID_YES.equals(isDir));
	}
	
	/**
	 * rename dir or file
	 * 
	 * @param docId
	 * @param newName
	 */
	@ResponseBody
	@RequestMapping("/rename.do")
	public void rename(@RequestParam("docId") String docId,
			@RequestParam("newName") String newName, @RequestParam("isDir") String isDir) {
		
		docStoreService.rename(docId, newName, Constants.VALID_YES.equals(isDir));
	}
	
	/**
	 * get dirs and files before open doc store
	 * 
	 * @return
	 */
	@RequestMapping("/openDocstore.do")
	public ModelAndView openDocstore(
			@RequestParam(value = "parentId", required = false) String parentId) {
		
		ModelAndView mv = new ModelAndView("doc/docstore");
		
		// get grand dir id
		Directory currentDir = docStoreService.getDirectory(parentId);
		mv.addObject("grandId", currentDir.getParentId());
		
		// search dirs
		List<Directory> dirs = docStoreService.searchDirs(parentId);
		mv.addObject("dirs", dirs);
		
		// search files
		List<DocFile> files = docStoreService.searchFiles(parentId);
		mv.addObject("files", files);
		
		return mv;
	}
	
	/**
	 * create new dir
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/createDir.do")
	public String createDir(@RequestParam("parentId") String parentId) {
		
		return docStoreService.createDir(parentId);
	}
}
