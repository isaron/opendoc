package com.cloud.platform;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.cloud.attach.AttachService;

public class UploadServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		doPost(req, resp);
	}

	@SuppressWarnings("deprecation")
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		// init upload
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		upload.setHeaderEncoding("UTF-8");
		
		// init dest store dir
		String basePath = req.getRealPath("/") + "upload/";
		
		File baseDir = new File(basePath);
		
		if(!baseDir.exists()) {
			baseDir.mkdirs();
		}
		
		try {
			// init attach service
			AttachService attachService = (AttachService) SpringUtil.getBean("attachService");
			
			// get upload files
			List items = upload.parseRequest(req);
			
			for(Object o : items) {
				// get upload file
				FileItem file = (FileItem) o;
				
				if(file.getName() == null) {
					continue;
				}
				
				String fileId = Constants.getID();
				String fileName = file.getName();
				String extendType = fileName.substring(fileName.lastIndexOf(".") + 1);
				
				// get dest store file address
				String storeFile = basePath + fileId + "." + extendType;
				
				// store upload file
				file.write(new File(storeFile));
				
				// save bug attach info
				attachService.saveEntityAttach(fileId, fileName, extendType, convertFileSize(file.getSize()));
				
				// output file id to the front page
				PrintWriter out = resp.getWriter();
				out.println(fileId);
				out.flush();
				out.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * convert file size
	 * 
	 * @param size
	 * @return
	 */
	private int convertFileSize(long size) {
		return (int) (size / 1024);
	}
}
