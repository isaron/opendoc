<%@ tag pageEncoding="UTF-8" import="com.cloud.platform.*" %>

<%@ attribute name="extend" %>

<%
	if("xls".equals(extend.toLowerCase()) || "xlsx".equals(extend.toLowerCase())) {
		out.print("excel_bk");
	}
	else if("doc".equals(extend.toLowerCase()) || "docx".equals(extend.toLowerCase())) {
		out.print("doc_bk");
	}
	else if("ppt".equals(extend.toLowerCase()) || "pptx".equals(extend.toLowerCase())) {
		out.print("ppt_bk");
	}
	else if("pdf".equals(extend.toLowerCase())) {
		out.print("pdf_bk");
	}
	else if(DocConstants.isText(extend)) {
		out.print("txt_bk");
	}
	else if(Constants.isImage(extend)) {
		out.print("");
	}
	else {
		out.print("file_bk");
	}
%>