package com.cloud.doc.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloud.doc.service.DocOperateService;
import com.cloud.platform.Constants;

@Controller
@RequestMapping("docop")
public class DocOperateBean {

	private static Logger logger = Logger.getLogger(DocOperateBean.class);

	@Autowired
	private DocOperateService docOperateService;
	
	/**
	 * check in file
	 * 
	 * @param parentId
	 * @param attachIds
	 */
	@ResponseBody
	@RequestMapping("/checkin.do")
	public String checkin(@RequestParam("docFileId") String docFileId,
			@RequestParam("attachId") String attachId) {
		
		return docOperateService.checkin(docFileId, attachId);
	}
	
	/**
	 * check out file
	 * 
	 * @param docFileId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkout.do")
	public String checkout(@RequestParam("docFileId") String docFileId) {
		
		boolean isSuccess = docOperateService.checkout(docFileId);
		
		return isSuccess ? Constants.VALID_YES : Constants.VALID_NO;
	}
}
