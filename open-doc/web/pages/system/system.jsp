<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head></head>

<body>
	<div class="wrapper">
		<ul id="navtab">
			<li url="<c:url value="/pages/system/password.jsp" />">修改密码</li>
			<li url="<c:url value="/pages/system/systemName.jsp" />">系统名称</li>
			<li url="<c:url value="/pages/system/attachConfig.jsp" />">附件配置</li>
		</ul>
	</div>
	
	<script>
		$("#navtab").navtab({isSide: true});		
	</script>
</body>
</html>
