<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<div id="departModal" class="hide"><p style="padding: 5px;">
	<table id="departTab" class="list-table full-table">
		<tr>
			<th width="80px"></th>
			<th width="310px">部门名称</th>
			<th width="150px">部门经理</th>
		</tr>
	</table>
</p></div>

<script>
	var $_d_src;
	
	ppmDialog("#departModal", "选择部门", {
        "确认": function() { selectDepart(); },
        "清空": function() { selectDepart(true); },
        "取消": function() { $(this).dialog("close"); }
	});
	
	function loadDepartData($inp) {
		$_d_src = $inp;
		var id = $inp.attr("val"), id = id ? id : "";
		
		_remoteCall("user/getDeparts.do", null, function(data) {
			var info = eval(data), html = "", $tab = $("#departTab");
			
			// remove origin rows
			$("tr:gt(0)", $tab).remove();
			
			for(var i in info) {
				html += "<tr id='" + info[i].id + "' onclick='clickDepart($(this));' ondblclick='selectDepart();'>";
				html += "<td class='sn'><input name='_depart' type='radio' " + (id == info[i].id ? "checked" : "") + " /></td>";
				html += "<td><div>" + info[i].name + "</div></td>";
				html += "<td><div>" + info[i].manager + "</div></td>";
				html += "</tr>";
			}
			
			$tab.append(html);
			
			// init radio box
			$("#departTab input").iCheck({radioClass: "iradio_square-blue"});
		});
	}
	
	function clickDepart($tr) {
		$("input", $tr).iCheck("check");
	}
	
	function selectDepart(isClear) {
		if(isClear) {
			$_d_src.attr("val", "").val("");
		} else {
			var $sel = $("#departModal input:radio:checked").closest("tr");
			$_d_src.attr("val", $sel.attr("id")).val($("td:eq(1)", $sel).text());
		}
		
		// if has filter, call search to refresh table
		if(typeof(search) == "function")  search();
		
		$("#departModal").dialog("close");
	}
</script>
