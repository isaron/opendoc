<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head></head>

<body>
	<div class="wrapper">
		<div id="btnDiv">
			<button class="button button-rounded button-flat-primary" onclick="modifySystemName();">确认修改</button>
		</div>
		
		<table class="edit-table">
			<tr>
				<td class="label-td">系统名称</td>
				<td><input id="systemName" type="text" class="input-text input-require" /></td>
			</tr>
		</table>
	</div>
	
	<script>
		_remoteCall("system/getSystemName.do", null, function(data) {
			$("#systemName").val(data);
		});
		
		function modifySystemName() {
			if(!checkForm())  return;
			
			_remoteCall("system/modifySystemName.do", {systemName: $("#systemName").val()}, function() {
				ppmAlert("修改密码", "修改系统名称成功，重新登录系统后生效！");
			});
		}
	</script>
</body>
</html>
