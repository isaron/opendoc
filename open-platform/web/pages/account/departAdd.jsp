<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cloud" tagdir="/WEB-INF/tags/" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<body>
	<div class="wrapper">
	
		<form action="<c:url value="/user/saveDepart.do" />" onsubmit="return initForm();" method="post">
		
		<div id="btnDiv">
			<input type="submit" class="button button-rounded button-flat-primary" value="保存" />
			<a href="<c:url value="/pages/account/structure.jsp" />" class="button button-rounded button-flat-primary">返回</a>
		</div>
		
		<table class="edit-table">
			<tr>
				<td class="label-td">部门名称</td>
				<td><input name="name" type="text" class="input-text input-require input-maxlen255" value="${depart.name}" /></td>
			</tr>
			<tr>
				<td class="label-td">部门经理</td>
				<td><input id="manager" type="text" class="input-text" ondblclick="showUser($(this));" onkeydown="return false;" val="${depart.managerId}" value="<cloud:user userId="${depart.managerId}" />" /><i onclick="$(this).prev().dblclick();" class="icon-search input-icon"></i></td>
			</tr>
			<tr>
				<td class="label-td">部门助理</td>
				<td><input id="assistant" type="text" class="input-text" ondblclick="showUsers($(this));" onkeydown="return false;" val="${depart.assistantIds}" value="<cloud:users ids="${depart.assistantIds}" />" /><i onclick="$(this).prev().dblclick();" class="icon-search input-icon"></i></td>
			</tr>
			<tr>
				<td class="label-td">上级部门</td>
				<td><input id="parent" type="text" class="input-text" ondblclick="showDepartment($(this));" onkeydown="return false;" val="${depart.parentId}" value="<cloud:depart departId="${depart.parentId}" />" /><i onclick="$(this).prev().dblclick();" class="icon-search input-icon"></i></td>
			</tr>
		</table>
		
		<input type="hidden" name="id" value="${depart.id}" />
		<input type="hidden" id="_managerId" name="managerId" />
		<input type="hidden" id="_assistantIds" name="assistantIds" />
		<input type="hidden" id="_parentId" name="parentId" />
		
		</form>
	</div>

	<jsp:include page="/pages/common/userSelect.jsp"></jsp:include>
	<jsp:include page="/pages/common/userSelectMul.jsp"></jsp:include>
	<jsp:include page="/pages/common/departSelect.jsp"></jsp:include>
	
	<script>
		function initForm() {
			$("#_managerId").val($("#manager").attr("val") ? $("#manager").attr("val") : "");
			$("#_assistantIds").val($("#assistant").attr("val") ? $("#assistant").attr("val") : "");
			$("#_parentId").val($("#parent").attr("val") ? $("#parent").attr("val") : "");
			
			return checkForm();
		}
	</script>
</body>
</html>
