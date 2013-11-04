<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head></head>

<body>
	<div class="wrapper">
		<div id="btnDiv">
			<button class="button button-rounded button-flat-primary" onclick="saveAttachConfig();">确认修改</button>
		</div>
		
		<table class="edit-table">
			<tr>
				<td class="label-td">附件最大容量</td>
				<td><input id="size" type="text" class="input-text input-require input-integer" /> M</td>
			</tr>
		</table>
	</div>
	
	<script>
		_remoteCall("system/getAttachConfig.do", null, function(data) {
			$("#size").val(data);
		});
		
		function saveAttachConfig() {
			if(!checkForm())  return;
			
			_remoteCall("system/saveAttachConfig.do", {size: $("#size").val()}, function() {
				ppmAlert("附件设置", "修改系统附件配置成功！");
			});
		}
	</script>
</body>
</html>
