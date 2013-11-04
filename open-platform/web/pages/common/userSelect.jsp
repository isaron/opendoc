<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<div id="userModal" class="hide"><p style="padding: 5px;">
	<table id="userTab" class="list-table full-table">
		<tr>
			<th width="40px"></th>
			<th width="350px">用户名</th>
			<th width="150px">职位</th>
		</tr>
	</table>
</p></div>

<script>
	var $_u_src;
	
	ppmDialog("#userModal", "选择用户", {
        "确认": function() { selectUser(); },
        "清空": function() { selectUser(true); },
        "取消": function() { $(this).dialog("close"); }
	});
	
	$("#userModal").next().append("<span class='chx-container' style='float: left;'><input id='u_showStructure' type='checkbox' />组织架构</span>");
	
	function loadUserData($inp, $tr) {
		$_u_src = $inp;
		var id = $inp.attr("val"), id = id ? id : "";
		
		if(!$("#u_showStructure").is(":checked")) {
			_remoteCall("user/getDepartUsers.do", {}, function(data) {
				var html = "", $tab = $("#userTab");
				
				// remove origin rows
				$("tr:gt(0)", $tab).remove();
				
				for(var i in data) {
					html += "<tr id='" + data[i].id + "' onclick='clickUser($(this));' ondblclick='selectUser();'>";
					html += "<td class='sn'><input name='_user' type='radio' " + (id == data[i].id ? "checked" : "") + " /></td>";
					html += "<td><div>" + data[i].username + "</div></td>";
					html += "<td><div>" + data[i].positionName + "</div></td>";
					html += "</tr>";
				}
				
				$tab.append(html);
				
				// init check box
				$("#userTab input").not("#u_showStructure").iCheck({radioClass: "iradio_square-blue"});
			}, true);
		}
		else {
			_remoteCall("user/getStructure.do", {parentId: $tr ? $tr.attr("id") : ""}, function(data) {
				var html = "", $tab = $("#userTab");
				
				for(var i in data) {
					html += "<tr id='" + data[i].id + "' parentId='" + data[i].parentId + "' " + (data[i].isDepart != "Y" ? ("onclick='clickUser($(this));' ondblclick='selectUser();'") : "") + ">";
					html += "<td class='sn'>";
					
					if(data[i].isDepart != "Y") {
						html += "<input name='_user' type='radio' " + (id == data[i].id ? "checked" : "") + " />";
					}
					
					html += "</td>";
					html += "<td><div style='padding-left: " + data[i].pad + "px;" + (data[i].isDepart == "Y" ? "font-weight: bold;" : "") + "'><i onclick='u_toggleDepart($(this));' class='icon-plus tree-icon " + (data[i].hasChild == "Y" ? "" : "invisiable") + "'></i>" + (data[i].isDepart == "Y" ? data[i].departName : data[i].username) + "</div></td>";
					html += "<td><div>" + (data[i].positionName ? data[i].positionName : "") + "</div></td>";
					html += "</tr>";
				}
				
				var $trs = $(html);
				if($tr) { $tr.after($trs); } else { $("tr:gt(0)", $tab).remove(); $tab.append($trs); }
				
				// init check box
				$("#userTab input").not("#u_showStructure").iCheck({radioClass: "iradio_square-blue"});
			}, true);
		}
	}
	
	function u_toggleDepart($i) {
		var $tr = $i.closest("tr");
		
		if(!$tr.attr("hasLoad")) {
			loadUserData($_u_src, $tr);
			$tr.attr("hasLoad", "Y");
		} else {
			$("#userTab tr[parentId='" + $tr.attr("id") + "']").toggle();
		}
		
		if($i.hasClass("icon-plus")) { $i.removeClass("icon-plus").addClass("icon-minus"); } else { $i.removeClass("icon-minus").addClass("icon-plus"); }
	}
	
	function clickUser($tr) {
		$("input", $tr).iCheck("check");
	}
	
	function selectUser(isClear) {
		if(isClear) {
			$_u_src.attr("val", "").val("");
		} else {
			var $sel = $("#userModal input:radio:checked").closest("tr");
			$_u_src.attr("val", $sel.attr("id")).val($("td:eq(1)", $sel).text());
		}
		
		// if has filter, call search to refresh table
		if(typeof(search) == "function")  search();
		
		$("#userModal").dialog("close");
	}
	
	// init show struture users
	$("#u_showStructure").iCheck({checkboxClass: "icheckbox_square-blue"}).on("ifChecked", function() { loadUserData($_u_src); }).on("ifUnchecked", function() { loadUserData($_u_src); });
</script>
