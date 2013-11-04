<%@ page import="java.io.*,com.cloud.platform.*,com.cloud.doc.convert.*" %>
<%
	BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());

	common conf = new common();

	String docPath = DocConstants.UPLOAD_PATH + request.getParameter("doc");
	String pdfPath = docPath + ".pdf";
	String swfPath = docPath + ".swf";
	
	String pages	= request.getParameter("page");
	String format	= request.getParameter("format");
	String resolution	= request.getParameter("resolution");
	String callback = request.getParameter("callback");

	if(pages == null)			{pages = "";}
	if(format == null)			{format="swf";}
	if(callback == null)		{callback = "";}

	if("swf".equals(format)){
		if("true".equals(conf.getConfig("allowcache", ""))){
			conf.setCacheHeaders(response);
		}

		if(conf.getConfig("allowcache") == null || "false".equals(conf.getConfig("allowcache", "")) || ("true".equals(conf.getConfig("allowcache", "")) && conf.endOrRespond(request,response))){
			response.setContentType("application/x-shockwave-flash");
			response.setHeader("Accept-Ranges", "bytes");
			byte[] content = conf.file_get_contents(swfPath);
			response.setContentLength(content.length);
			outs.write(content);
		}
	}
	
	outs.flush();
	outs.close();
	out.clear();
	out = pageContext.pushBody();
%>