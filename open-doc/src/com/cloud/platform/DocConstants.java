package com.cloud.platform;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.springframework.core.io.Resource;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.support.ServletContextResource;

import com.cloud.system.model.SystemConfig;

public class DocConstants implements ServletContextAware {

	private static ServletContext servletContext;
	
	public static SystemConfig systemConfig;
	public static String ROOTPATH = System.getProperty("ppmdoc");
	public static String UPLOAD_PATH = ROOTPATH + "upload/";
	public static String TEMP_PATH = ROOTPATH + "temp/";

	/**
	 * inject servlet context
	 */
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

    /**
     * get file background style class
     *
     * @param extend
     * @return
     */
    public static String getFileBgStyle(String extend) throws IOException {

        if(StringUtil.isNullOrEmpty(extend)) {
            return "";
        }

        if("xls".equals(extend.toLowerCase()) || "xlsx".equals(extend.toLowerCase())) {
            return "excel_bk";
        }
        else if("doc".equals(extend.toLowerCase()) || "docx".equals(extend.toLowerCase())) {
            return "doc_bk";
        }
        else if("ppt".equals(extend.toLowerCase()) || "pptx".equals(extend.toLowerCase())) {
            return "ppt_bk";
        }
        else if("pdf".equals(extend.toLowerCase())) {
            return "pdf_bk";
        }
        else if(isText(extend)) {
            return "txt_bk";
        }
        else if(Constants.isImage(extend)) {
            return "";
        }
        else {
            return "file_bk";
        }
    }
	
	/**
	 * check if is office file
	 * 
	 * @param extend
	 * @return
	 */
	public static boolean isOffice(String extend) {
		
		extend = extend.toLowerCase();
		
		return "xls".equals(extend) || "xlsx".equals(extend)
				|| "doc".equals(extend) || "docx".equals(extend)
				|| "ppt".equals(extend) || "pptx".equals(extend);
	}

	/**
	 * check if is text file
	 * 
	 * @param extend
	 * @return
	 * @throws IOException 
	 */
	public static boolean isText(String extend) throws IOException {
	
		Resource propRsr = new ServletContextResource(servletContext, "/WEB-INF/text.properties");
		
		Properties prop = new Properties();
		prop.load(propRsr.getInputStream());
		
		return prop.containsValue(extend.toLowerCase());
	}
	
	/**
	 * get system config
	 * 
	 * @return
	 */
	public static SystemConfig getSystemConfig() {
		
		if(systemConfig == null) {
			IDao dao = (IDao) SpringUtil.getBean("dao");
			List<SystemConfig> list = dao.getAllByHql("from SystemConfig");
			
			systemConfig = list.get(0);
		}
		
		return systemConfig;
	}
}
