package com.cloud.platform;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		doPost(req, resp);
	}

	@SuppressWarnings("deprecation")
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		// get download file address
		String fileName = req.getParameter("fileName");
		String realName = req.getParameter("realName");
		String fileAddr = req.getRealPath("/") + "upload/" + fileName; 
		
		File file = new File(fileAddr);
		
		if(!file.exists()) {
			return;
		}
		
		// set response info
		resp.setContentType("application/x-msdownload");
		resp.setHeader("Content-Disposition", "attachment;filename=" + realName);
		
		OutputStream out = null;
		BufferedInputStream bis = null;
		
		try {
			// init servlet output stream
			out = resp.getOutputStream();
			
			// init download file input stream
			bis = new BufferedInputStream(new FileInputStream(file));
			
			// init buffer byte array
			byte[] buffer = new byte[1024 * 5];
			int len;
			
			// write data
			while((len = bis.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			
			out.flush();
			
		} finally {
			if(bis != null) {
				bis.close();
			}
			if(out != null) {
				out.close();
			}
		}
	}
}
