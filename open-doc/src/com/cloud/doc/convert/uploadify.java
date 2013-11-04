package com.cloud.doc.convert;

import java.io.File;
import java.util.*;

import javax.servlet.http.*;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class uploadify {
    public uploadify() {
    }

	public void upload(HttpServletRequest request){
		try{
			Config con = new Config();
			File f_midir = new File(con.getConfig("path.pdf"));
            if(!f_midir.isDirectory() && !f_midir.mkdirs())
            	f_midir = new File("/");
			request.setCharacterEncoding("utf-8");
			DiskFileItemFactory  factory = new DiskFileItemFactory();
			factory.setSizeThreshold(4096);
			factory.setRepository(f_midir);
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(100 * 1024 * 1024);
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if(!isMultipart)
				return;
			List items = upload.parseRequest(request);
			Iterator iter = items.iterator();
			try{
				while(iter.hasNext()){
					FileItem item = (FileItem)iter.next();
					if(!item.isFormField()){
		                String fileName = item.getName();
		                if(!fileName.equals("") && fileName != null){
		                	int idx = fileName.lastIndexOf("\\");
		                	if (idx == -1) {
		                		idx = fileName.lastIndexOf("/");
		                	}
		                	fileName = fileName.substring(idx + 1);
		                	String tempfileName = fileName;
		                	File save_file = new File(f_midir.getPath(),fileName);
		                	if(save_file.exists()){
		                		for(int i = 1; true; i++){
		                			fileName = i  + "_" + tempfileName;
		                			save_file = new File(f_midir.getPath(),fileName);
		                			if(!save_file.exists()){
		                				break;
		                            }
			                     }
			                 }
		                	f_midir = new File(f_midir.getPath(),fileName);
			                 item.write(f_midir);
			            }
		            }
				}
			}catch(Exception e){
		        e.printStackTrace();
		    }
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
