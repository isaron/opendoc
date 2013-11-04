package com.cloud.doc.service;

import java.io.File;
import java.util.Map;

import org.apache.log4j.Logger;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.document.DefaultDocumentFormatRegistry;
import org.artofsolving.jodconverter.document.DocumentFormat;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.cloud.attach.Attach;
import com.cloud.doc.convert.pdf2swf;
import com.cloud.platform.DocConstants;

public class DocConvertJob implements Job {

	private static Logger logger = Logger.getLogger(DocConvertJob.class);
	private static final String OPENOFFICE_HOME = DocConstants.ROOTPATH + "include/OpenOffice";
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			// get datas
			Map data = context.getJobDetail().getJobDataMap();
			Attach attach = (Attach) data.get("attach");
			
			// convert office file to pdf file
			if(DocConstants.isOffice(attach.getExtendType())) {
				
				DefaultOfficeManagerConfiguration config = new DefaultOfficeManagerConfiguration();
		        config.setOfficeHome(OPENOFFICE_HOME);
		        OfficeManager officeManager = config.buildOfficeManager();
		        
		        try {  
		        	officeManager.start();
		        	OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
		        	DocumentFormat outputFormat = new DefaultDocumentFormatRegistry().getFormatByExtension("pdf");
		        	
		        	String srcPath = DocConstants.UPLOAD_PATH + attach.getId() + "." + attach.getExtendType();
		        	String destPath = DocConstants.UPLOAD_PATH + attach.getId() + ".pdf";
		        	
		        	converter.convert(new File(srcPath), new File(destPath), outputFormat);
		            
		        } catch (Exception e) {   
		        	logger.error("***** 异常信息 ***** 方法：switchDocToSwf at switch office to pdf", e);
		            return;
		            
		        } finally {   
		            // close the connection   
		            officeManager.stop();  
		        } 
			}
			
			// convert pdf file to swf file
			String docPath = DocConstants.UPLOAD_PATH + attach.getId();
			
			pdf2swf pdfconv = new pdf2swf();
			pdfconv.convert(docPath, "");
			
		} catch(Exception e) {
			logger.error("***** 异常信息 ***** 方法：DocConvertJob execute", e);
		}
	}
}
