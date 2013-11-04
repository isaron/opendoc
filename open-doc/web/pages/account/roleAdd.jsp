<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cloud" tagdir="/WEB-INF/tags/" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<style>
		.chx-container {padding: 0 15px 8px 15px;}
	</style>
</head>

<body>
	<div class="wrapper">
	
		<form action="<c:url value="/entity/dispatch.do?operate=save&model=com.cloud.security.model.Role" />" onsubmit="return initForm();" method="post">
		
		<div id="btnDiv">
			<input type="submit" class="button button-rounded button-flat-primary" value="保存" />
			<a href="<c:url value="/entity/dispatch.do?operate=list&model=com.cloud.security.model.Role" />" class="button button-rounded button-flat-primary">返回</a>
		</div>
		
		<table class="edit-table">
			<tr>
				<td class="label-td">角色名称</td>
				<td><input id="name" name="name" type="text" class="input-text input-require input-maxlen255" value="${role.name}" /></td>
			</tr>
			<tr>
				<td class="label-td">角色描述</td>
				<td><input id="intro" name="intro" type="text" class="input-text input-maxlen2048" value="${role.intro}" /></td>
			</tr>
		</table>
		
		<div class="title">组织架构</div>
		<div id="ORG" class="chx-container"></div>
		<div class="title">系统管理</div>
		<div id="SYS" class="chx-container"></div>
		
		<input type="hidden" name="id" value="${param.entityId}" />
		<input type="hidden" id="_resourceIds" name="resourceIds" />
		
		</form>
	</div>
	
	<script>
		var pageInfo = ${pageInfo};
		initResource();
		
		function initResource() {
			_remoteCall("role/getResources.do", null, function(data) {
				$("#ORG").html(combineResHtml(data.ORG));
				$("#SYS").html(combineResHtml(data.SYS));
				
				initPage();
				
				$("input:checkbox").iCheck({checkboxClass: "icheckbox_square-blue"});
				autoFrameHeight();
			}, true);
		}
		
		function initPage() {
			for(var i in pageInfo.fields) {
				f = pageInfo.fields[i];
				
				if(f.name == "resourceIds") {
					var resIds = f.value ? f.value.split(",") : [];
					
					for(var j in resIds) {
						$("#" + resIds[j]).attr("checked", true);						
					}
				} else {
					$("#" + f.name).val(f.value);
				}
			}
		}
		
		function combineResHtml(d) {
			var html = "";
			for(var i in d) {
				html += "<span class='chx-item'><input id='" + d[i].id + "' type='checkbox' />" + d[i].name + "</span>";
			}
			return html;
		}
		
		function initForm() {
			var resIds = [];
			$("input:checkbox:checked").each(function() {
				resIds.push($(this).attr("id"));
			});
			
			$("#_resourceIds").val(resIds.join(","));
			
			return checkForm();
		}
	</script>
</body>
</html>
