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
	 * @param docFileId
	 * @param attachId
	 */
	@ResponseBody
	@RequestMapping("/checkin.do")
	public String checkin(@RequestParam("docFileId") String docFileId,
			@RequestParam("attachId") String attachId, @RequestParam("note") String note) {
        String newFileId = "";

        try {
            newFileId = docOperateService.checkin(docFileId, attachId, note);
        } catch (Exception e) {
            logger.error("***** 异常信息 ***** 方法：checkin", e);
        }

		return newFileId;
	}
	
	/**
	 * check out file
	 * 
	 * @param docFileId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkout.do")
	public String checkout(@RequestParam("docFileId") String docFileId, @RequestParam("note") String note) {
		
		boolean isSuccess = docOperateService.checkout(docFileId, note);
		
		return isSuccess ? Constants.VALID_YES : Constants.VALID_NO;
	}
}
