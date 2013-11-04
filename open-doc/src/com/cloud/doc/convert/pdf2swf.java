package com.cloud.doc.convert;

import java.io.File;

import com.cloud.platform.DocConstants;

public class pdf2swf extends common{

	public String convert(String doc, String page) {
		String pdfFilePath	= doc + ".pdf";
		String swfFilePath	= doc;

		String command = DocConstants.ROOTPATH + "include/pdf2swf.exe ";
		command += "{path.pdf}{pdffile} -o {path.swf}{pdffile}.swf -f ";
		command += "-T 9 -G -s poly2bitmap";
		
		command = command.replace("{path.pdf}{pdffile}", pdfFilePath);
		command = command.replace("{path.swf}{pdffile}", swfFilePath);

		try {
			if (!isNotConverted(pdfFilePath ,swfFilePath)) {
				return "[Converted]";
			}
		} catch (Exception ex) {
			return "Error," + ex.toString();
		}

		boolean return_var = exec(command);

		if(return_var) {
			return "[Converted]";
		} else {
			return "Error converting document, make sure the conversion tool is installed and that correct user permissions are applied to the SWF Path directory" + getDocUrl();
		}
	}

	public boolean isNotConverted(String pdfFilePath,String swfFilePath) throws Exception {
		File f = new File(pdfFilePath);
		if (!f.exists()) {
			throw new Exception("Document does not exist");
		}
		if (swfFilePath == null) {
			throw new Exception("Document output file name not set");
		} else {
			File s = new File(swfFilePath);
			if (!s.exists()) {
				return true;
			} else {
				if(f.lastModified() > s.lastModified()) return true;
			}
		}
		return false;
	}
}