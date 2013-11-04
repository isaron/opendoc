<%@ tag pageEncoding="UTF-8" import="com.cloud.platform.*" %>

<%@ attribute name="ids" %>

<% 
	if(StringUtil.isNullOrEmpty(ids)) {
		out.print("");
	} else {
		StringBuffer names = new StringBuffer();
		
		String[] idArr = ids.split(",");
		
		for(String id : idArr) {
			if(names.length() != 0) {
				names.append(", ");
			}
			names.append(Constants.getUsernameById(id));	
		}
		
		out.print(names.toString());
	}
%>