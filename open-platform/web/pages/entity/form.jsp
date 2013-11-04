<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cloud" tagdir="/WEB-INF/tags/" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head></head>

<body>
	<div class="wrapper">
	
		<form action="<c:url value="/entity/dispatch.do?operate=save&model=${param.model}" />" onsubmit="return initForm();" method="post">
		
		<div id="btnDiv">
			<input type="submit" class="button button-rounded button-flat-primary" value="保存" />
			<a href="<c:url value="/entity/dispatch.do?operate=list&model=${param.model}" />" class="button button-rounded button-flat-primary">返回</a>
		</div>
		
		<table class="edit-table"></table>
		
		<input id="_entityId" type="hidden" name="id" value="${param.entityId}" />
		</form>
	</div>
	
	<div id="relateModal" class="hide"><p style="padding: 5px;">
		<table id="relateTab" class="list-table full-table"></table>
	</p></div>
	
	<script>
		var pageInfo = ${pageInfo}, $relate;
		initPage();
		
		ppmDialog("#relateModal", "", {
	        "确认": function() { selectRelate(); },
	        "清空": function() { selectRelate(true); },
	        "取消": function() { $(this).dialog("close"); }
		});
		
		function initPage() {
			var html = "", f;
			
			for(var i in pageInfo.fields) {
				f = pageInfo.fields[i];
				
				html += "<tr>";
				html += "<td class='label-td'>" + f.label + "</td>";
				html += "<td>" + combineEditInput(f) + "</td>";
				html += "</tr>";
			}
			
			$("table.edit-table").append(html);
			
			// init select box
			$("input:checkbox").iCheck({checkboxClass: "icheckbox_square-blue"});
		}
		
		function combineEditInput(field) {
			var html = "";

			if(field.htmlType == "TEXT") {
				html = "<input name='" + field.name + "' value='" + (field.value ? field.value : "") + "' type='text' class='input-text" + (field.isRequire == "Y" ? " input-require" : "") + (field.validate_no_repeat == "Y" ? " input-norepeat" : "") + (field.validate_max_length ? (" " + field.validate_max_length) : "") + "' />";
			}
			else if(field.htmlType == "PASSWORD") {
				html = "<input name='" + field.name + "' value='" + (field.value ? field.value : "") + "' type='password' class='input-text" + (field.isRequire == "Y" ? " input-require" : "") + "' />";
			}
			else if(field.htmlType == "TEXTAREA") {
				html = "<textarea name='" + field.name + "' class='input-textarea" + (field.validate_max_length ? (" " + field.validate_max_length) : "") + "'>" + (field.value ? field.value : "") + "</textarea>";
			}
			else if(field.htmlType == "CHECKBOX") {
				_remoteCall("entity/synDispatch.do", {operate: "list", model: field.relateModel}, function(data) {
					html += "<div id='" + field.name + "' field='checkbox' class='chx-container'>";
					
					for(var i in data.datas) {
						var d = data.datas[i];
						html += "<span class='chx-item'><input id='" + d[0] + "' type='checkbox'";
						if(field.value && field.value.indexOf(d[0]) >= 0)  html += " checked='checked'";
						html += " />" + d[1] + "</span>";
					}
					
					html += "<input id='_" + field.name + "' type='hidden' name='" + field.name + "' />";
					html += "</div>";
				}, true, true);
			}
			else if(!field.htmlType && field.relateModel) {
				html = "<input id='" + field.name + "' field='relate' type='text' class='input-text" + (field.isRequire == "Y" ? " input-require" : "") + "' ondblclick='showRelate($(this), \"" + field.label + "\", \"" + field.relateModel + "\", \"" + field.relateMulti + "\");' onkeydown='return false;' val='" + (field.value ? field.value : "") + "' value='" + (field.relateValue ? field.relateValue : "") + "' /><i onclick='$(this).prev().dblclick();' class='icon-search input-icon'></i>";
				html += "<input id='_" + field.name + "' type='hidden' name='" + field.name + "' />";
			}
			
			return html;
		}
		
		function showRelate($inp, label, relateModel, relateMulti) {
			$relate = $inp;
			var id = $inp.attr("val"), id = id ? id : "";
			
			_remoteCall("entity/synDispatch.do", {operate: "list", model: relateModel}, function(data) {
				var html = "", fields = data.fields, datas = data.datas;
				
				// init table head html
				html += "<tr>";
				html += "<th width='60px'></th>";
				for(var i in fields) {
					html += "<th width='" + fields[i].width + "px'>" + fields[i].label + "</th>";
				}
				html += "</tr>";
				
				// init table body html
				for(var i in datas) {
					html += "<tr id='" + datas[i][0] + "' onclick='clickRelate($(this));' " + (relateMulti != "Y" ? "ondblclick='selectRelate();'" : "") + ">";
					
					html += "<td class='sn'>";
					html += "<input name='_relate' type='" + (relateMulti != "Y" ? "radio" : "checkbox") + "' " + (id.indexOf(datas[i][0]) >= 0 ? "checked" : "") + " />";
					html += "</td>";
					
					for(var j in fields) {
						html += "<td><div>" + datas[i][parseInt(j) + 1] + "</div></td>";
					}
					
					html += "</tr>";
				}
				
				$("#relateTab").html(html);
				
				// init select box
				$("#relateTab input").iCheck({radioClass: "iradio_square-blue"});
				
				$("#relateModal").attr("title", "选择" + label);
				$("#relateModal").dialog("open");				
			}, true);
		}
		
		function clickRelate($tr) {
			$("input", $tr).iCheck("check");
		}
		
		function selectRelate(isClear) {
			if(isClear) {
				$relate.attr("val", "").val("");
			} else {
				var $sel = $("#relateModal input:radio:checked").closest("tr");
				$relate.attr("val", $sel.attr("id")).val($("td:eq(1)", $sel).text());
			}
			
			$("#relateModal").dialog("close");
		}
		
		function initForm() {
			$("input[field='relate']").each(function() {
				var val = $(this).attr("val");
				$("#_" + $(this).attr("id")).val(val ? val : "");
			});
			
			$("div.select-div").each(function() {
				$("#_" + $(this).attr("id")).val($(this).selectVal());
			});
			
			$("div[field='checkbox']").each(function() {
				var ids = [];
				$("input:checkbox:checked").each(function() {
					ids.push($(this).attr("id"));
				});
				$("#_" + $(this).attr("id")).val(ids.join(","));
			});
			
			return checkForm();
		}
		
		// specially deal with user edit
		$(function() {
			if(pageInfo.model == "com.cloud.security.model.User" && pageInfo.operate == "edit") {
				$("input[name='password']").removeClass("input-require").val("");
			}
		});
	</script>
</body>
</html>
