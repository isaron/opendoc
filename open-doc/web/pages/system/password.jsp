<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>

<body>
	<div class="wrapper">
		<div id="btnDiv">
			<button class="button button-rounded button-flat-primary" onclick="modifyPwd();">确认修改</button>
		</div>
	
		<table class="edit-table">
			<tr>
				<td class="label-td">原始密码</td>
				<td><input id="pwd1" type="password" class="input-text input-require" /></td>
			</tr>
			<tr>
				<td class="label-td">新密码</td>
				<td><input id="pwd2" type="password" class="input-text input-require" /></td>
			</tr>
			<tr>
				<td class="label-td">确认密码</td>
				<td><input id="pwd3" type="password" class="input-text input-require" /></td>
			</tr>
		</table>
	</div>
		
	<div id="dialog-ok" class="hide" title="修改密码">
		<p>修改密码成功！</p>
	</div>
	
	<script>
		function modifyPwd() {
			if(!checkForm())  return;
			
			if($("#pwd2").val() != $("#pwd3").val()) {
				$("#pwd3").parent().append("<div class='outline validate'>新密码与确认密码不匹配</div>");
				return;
			}
			
			_remoteCall("user/unlock.do", {password: $("#pwd1").val()}, function(data) {
				if(data == "Y") {
					_remoteCall("user/changePwd.do", {password: $("#pwd2").val()}, function() {
						$( "#dialog-ok" ).dialog({
							resizable: false,
						    height: 200,
						    modal: true,
						    buttons: {
						        "确定": function() {
						        	$(this).dialog("close");
						        	top.location.href = top.basePath + "user/logout.do";
						        }
							}
						});
						
						$("#pwd1, #pwd2, #pwd3").val("");
					});	
					
				} else {
					$("#pwd1").parent().append("<div class='outline validate'>原始密码错误</div>");
				}
			});
		}
	</script>
</body>
</html>
