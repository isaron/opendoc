<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head></head>

<body>
	<div class="wrapper">
		<ul id="navtab">
			<li url="<c:url value="/pages/account/structure.jsp" />">组织架构</li>
			<li url="<c:url value="/entity/dispatch.do?operate=list&model=com.cloud.security.model.Position" />">职位列表</li>
			<li url="<c:url value="/entity/dispatch.do?operate=list&model=com.cloud.security.model.Role" />">角色权限</li>
		</ul>
	</div>
	
	<script>
		$("#navtab").navtab({isSide: true});		
	</script>
</body>
</html>
