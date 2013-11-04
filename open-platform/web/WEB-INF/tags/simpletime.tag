<%@ tag pageEncoding="UTF-8" import="com.cloud.platform.DateUtil" %>

<%@ attribute name="date" type="java.util.Date" %>

<%= DateUtil.getSimpleTimeStr(date) %>