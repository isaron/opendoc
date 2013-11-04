<%@ tag pageEncoding="UTF-8" import="com.cloud.platform.Constants" %>

<%@ attribute name="departId" %>

<%
	if(Constants.getDepartTreeMap().containsKey(departId)) {
		out.println(Constants.getDepartTreeMap().get(departId).getName());
	} else {
		out.println("");
	}
%>