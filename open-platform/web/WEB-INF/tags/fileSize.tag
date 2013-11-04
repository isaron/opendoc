<%@ tag pageEncoding="UTF-8" import="com.cloud.platform.NumberUtil" %>

<%@ attribute name="size" type="java.lang.Integer" %>

<%
	if(size < 1024) {
		out.print(size + " KB");
	} else {
		out.print(NumberUtil.decimalFormat(size / 1024.0) + " MB");
	}
%>