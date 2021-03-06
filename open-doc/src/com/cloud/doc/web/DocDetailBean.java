package com.cloud.doc.web;

import java.io.File;
import java.util.List;

import com.cloud.doc.model.DocRecord;
import com.cloud.doc.service.DocOperateService;
import com.cloud.platform.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cloud.doc.model.DocFile;
import com.cloud.doc.service.DocDetailService;
import com.cloud.platform.DocConstants;
import com.cloud.platform.FileUtil;
import com.cloud.platform.StringUtil;

@Controller
@RequestMapping("docdetail")
public class DocDetailBean {
	
	private static Logger logger = Logger.getLogger(DocDetailBean.class);

	@Autowired
	private DocDetailService docDetailService;

    @Autowired
    private DocOperateService docOperateService;
	
	/**
	 * get document convert status
	 * 
	 * @param docId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getConvertStatus.do")
	public String getConvertStatus(@RequestParam("docId") String docId) {
		
		File swfFile = new File(DocConstants.UPLOAD_PATH + docId + ".swf");
		
		return swfFile.exists() ? "Y" : "N";
	}
	
	/**
	 * init doc detail before open detail page
	 * 
	 * @param docId
	 * @return
	 */
	@RequestMapping("/openDoc.do")
	public ModelAndView openDoc(@RequestParam("docId") String docId) {
		
		ModelAndView mv = new ModelAndView("doc/docdetail");
		
		try {
			DocFile doc = docDetailService.getDocFile(docId);
			mv.addObject("doc", doc);

            List<DocRecord> records = docDetailService.searchRecords(docId);
            mv.addObject("records", records);

            List<DocFile> versionDocs = docDetailService.searchVersionDocs(docId);
            mv.addObject("versionDocs", versionDocs);

            // get doc star status
            boolean starStatus = docOperateService.getStarStatus(doc);
            mv.addObject("starStatus", starStatus ? Constants.VALID_YES : Constants.VALID_NO);

			// get text file content
			if(DocConstants.isText(doc.getAttach().getExtendType())) {
				String path = DocConstants.UPLOAD_PATH + doc.getAttach().getId() + "." + doc.getAttach().getExtendType();
				String content = FileUtil.readFile(path);
				content = StringUtil.filterHtmlTag(content);
				
				mv.addObject("textContent", content);
			}
			
		} catch(Exception e) {
			logger.error("***** 异常信息 ***** 方法：openDoc", e);
		}
		
		return mv;
	}
}
