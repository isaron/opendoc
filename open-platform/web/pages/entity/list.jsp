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
		<div id="btnDiv">
			<a href="<c:url value="/entity/dispatch.do?operate=add&model=${pageInfo.model}" />" class="button button-rounded button-flat-primary">添加${pageInfo.modelName}</a>
		</div>
		
		<div id="listDiv">
			<table id="listTab" class="list-table"></table>
		</div>
	</div>
	
	<script>
		var pageInfo = ${pageInfo};
		initPage();
		
		function initPage() {
			var html = "", fields = pageInfo.fields, datas = pageInfo.datas;
			
			// init table head html
			html += "<tr>";
			html += "<th width='60px'>序号</th>";
			for(var i in fields) {
				html += "<th width='" + fields[i].width + "px'>" + fields[i].label + "</th>";
			}
			html += "<th width='50px'></th>";
			html += "</tr>";
			
			// init table body html
			for(var i in datas) {
				html += "<tr id='" + datas[i][0] + "'>";
				html += "<td class='sn'>" + (parseInt(i) + 1) + "</td>";
				
				for(var j in fields) {
					html += "<td><div>" + datas[i][parseInt(j) + 1] + "</div></td>";
				}
				
				// init operate td
				html += "<td><div class='hide'>";
				html += "<i class='icon-pencil' style='cursor: pointer;margin-right: 10px;' onclick='editItem($(this));'></i>";
				html += "<i class='icon-trash' style='cursor: pointer;' onclick='removeItem($(this));'></i>";
				html += "</div></td>";
				
				html += "</tr>";
			}
			
			$("#listTab").html(html);
			
			// bind operate td mouse event
			$("#listTab tr:gt(0)").hover(function() { $("td:last div", this).show(); }, function() { $("td:last div", this).hide(); });
		}
		
		function editItem($i) {
			var entityId = $i.closest("tr").attr("id");
			location.href = top.basePath + "entity/dispatch.do?operate=edit&model=" + pageInfo.model + "&entityId=" + entityId;
		}
		
		function removeItem($i) {
			var entityId = $i.closest("tr").attr("id");
			
			ppmConfirm("删除" + pageInfo.modelName, "确定要删除该" + pageInfo.modelName + "么？", function() {
				location.href = top.basePath + "entity/dispatch.do?operate=remove&model=" + pageInfo.model + "&entityId=" + entityId;		
			});
		}
	</script>
</body>
</html>
