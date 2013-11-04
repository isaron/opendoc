<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<style type="text/css">
		body {overflow: hidden;}
	</style>
</head>

<body>
	<div class="wrapper">
		<div id="btnDiv" class="hide">
			<a href="<c:url value="/user/createOrEditDepart.do" />" class="button button-rounded button-flat-primary">添加部门</a>
			<a href="<c:url value="/entity/dispatch.do?operate=add&model=com.cloud.security.model.User" />" class="button button-rounded button-flat-primary">添加用户</a>
		</div>
		
		<div id="listDiv">
			<table class="list-table">
				<tr>
					<th width="60px">序号</th>
					<th width="350px">部门/用户</th>
					<th width="150px">职位</th>
					<th width="200px">Email</th>
					<th width="300px">地址</th>
					<th width="50px"></th>
				</tr>
			</table>
		</div>
	</div>
	
	<script>
		search();
		
		function search($tr) {
			_remoteCall("user/getStructure.do", {parentId: $tr ? $tr.attr("id") : ""}, function(data) {
				var html = "";
				
				for(var i in data) {
					html += "<tr id='" + data[i].id + "' parentId='" + data[i].parentId + "' isDepart='" + data[i].isDepart + "'>";
					html += "<td class='sn'>" + (parseInt(i) + 1) + "</td>";
					html += "<td><div style='padding-left: " + data[i].pad + "px;" + (data[i].isDepart == "Y" ? "font-weight: bold;" : "") + "'><i onclick='toggleDepart($(this));' class='icon-plus tree-icon " + (data[i].hasChild == "Y" ? "" : "invisiable") + "'></i>" + (data[i].isDepart == "Y" ? data[i].departName : data[i].username) + "</div></td>";
					html += "<td><div>" + (data[i].positionName ? data[i].positionName : "") + "</div></td>";
					html += "<td><div>" + data[i].email + "</div></td>";
					html += "<td><div>" + data[i].address + "</div></td>";
					
					html += "<td><div class='hide'>";
					html += "<i class='icon-pencil' style='cursor: pointer;margin-right: 10px;' onclick='editItem($(this));'></i>";
					html += "<i class='icon-trash' style='cursor: pointer;' onclick='removeItem($(this));'></i>";
					html += "</div></td>";
					
					html += "</tr>";
				}
				
				var $trs = $(html);
				if($tr) { $tr.after($trs); } else { $("table.list-table").append($trs); }
				
				resetSn();
				initRole($trs);
				autoFrameHeight();
			}, true);
		}
		
		function initRole($trs) {
			_remoteCall("role/hasStrutManageAuth.do", null, function(data) {
				if(data == "Y") {
					$("#btnDiv").show();
					
					// bind mouse event
					$trs.hover(function() { $("td:last div", this).show(); },  function() { $("td:last div", this).hide(); });
				} else {
					$("#btnDiv").remove();					
				}
			});
		}
		
		function toggleDepart($i) {
			var $tr = $i.closest("tr");
			
			if(!$tr.attr("hasLoad")) {
				search($tr);
				$tr.attr("hasLoad", "Y");
			} else {
				$("table.list-table tr[parentId='" + $tr.attr("id") + "']").toggle();
				resetSn();
				autoFrameHeight();
			}
			
			if($i.hasClass("icon-plus")) { $i.removeClass("icon-plus").addClass("icon-minus"); } else { $i.removeClass("icon-minus").addClass("icon-plus"); }
		}
		
		function resetSn() {
			$("table.list-table tr:visible").each(function(i) {
				$("td:eq(0)", $(this)).text(i);
			});
		}
		
		function editItem($i) {
			var $tr = $i.closest("tr"), isDepart = $tr.attr("isDepart") == "Y";
			
			if(isDepart) {
				location.href = top.basePath + "user/createOrEditDepart.do?departId=" + $tr.attr("id");
			} else {
				location.href = top.basePath + "entity/dispatch.do?operate=edit&model=com.cloud.security.model.User&entityId=" + $tr.attr("id");
			}
		}
		
		function removeItem($i) {
			var $tr = $i.closest("tr"), isDepart = $tr.attr("isDepart") == "Y";
			
			if(isDepart) {
				ppmConfirm("删除部门", "确定要删除该部门么？删除该部门后，其子部门和用户自动归属到该部门的上级部门。", function() {
					location.href = top.basePath + "user/removeDepart.do?departId=" + $tr.attr("id");					
				});
			} else {
				ppmConfirm("删除用户", "确定要删除该用户么？", function() {
					_remoteCall("user/removeUser.do", {userId: $tr.attr("id")}, function() {
						$tr.remove();
					});
				});
			}
		}
	</script>
</body>
</html>
