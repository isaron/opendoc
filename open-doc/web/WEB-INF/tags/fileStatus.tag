<%@ tag pageEncoding="UTF-8" %>

<%@ attribute name="status" %>

<%
	if(Integer.parseInt(status) == 0) {
		out.print("正常");
	} else {
		out.print("已检出");
	}
%>