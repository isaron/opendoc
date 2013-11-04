<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<div id="usersModal" class="hide"><p style="padding: 5px;">
	<table id="usersTab" class="list-table full-table">
		<tr>
			<th width="40px" style="padding-left: 20px;"><input id="u_all" name="_user" type="checkbox" /></th>
			<th width="350px">用户名</th>
			<th width="150px">职位</th>
		</tr>
	</table>
</p></div>

<script>
	var $_us_src;
	
	ppmDialog("#usersModal", "选择用户", {
        "确认": function() { selectUsers(); },
        "清空": function() { selectUsers(true); },
        "取消": function() { $(this).dialog("close"); }
	});
	
	$("#usersModal").next().append("<span class='chx-container' style='float: left;'><input id='showStructure' type='checkbox' />组织架构</span>");
	
	function loadUsersData($inp, $tr) {
		$_us_src = $inp;
		var id = $inp.attr("val"), id = id ? id : "";
		
		if(!$("#showStructure").is(":checked")) {
			_remoteCall("user/getDepartUsers.do", {}, function(data) {
				var html = "", $tab = $("#usersTab");
				
				// remove origin rows
				$("tr:gt(0)", $tab).remove();
				
				for(var i in data) {
					html += "<tr id='" + data[i].id + "' onclick='clickUsers($(this));'>";
					html += "<td style='padding-left: 20px;'><input name='_user' type='checkbox' " + (id.indexOf(data[i].id) >= 0 ? "checked" : "") + " /></td>";
					html += "<td><div>" + data[i].username + "</div></td>";
					html += "<td><div>" + data[i].positionName + "</div></td>";
					html += "</tr>";
				}
				
				$tab.append(html);
				
				// init check box
				$("#usersTab input").not("#u_all, #showStructure").iCheck({checkboxClass: "icheckbox_square-blue"});
			}, true);
		}
		else {
			_remoteCall("user/getStructure.do", {parentId: $tr ? $tr.attr("id") : ""}, function(data) {
				var html = "", $tab = $("#usersTab");
				
				for(var i in data) {
					html += "<tr id='" + data[i].id + "' parentId='" + data[i].parentId + "' " + (data[i].isDepart != "Y" ? ("onclick='clickUsers($(this));'") : "") + ">";
					html += "<td style='padding-left: 20px;'>";
					
					if(data[i].isDepart != "Y") {
						html += "<input name='_user' type='checkbox' " + (id.indexOf(data[i].id) >= 0 ? "checked" : "") + " />";
					}
					
					html += "</td>";
					html += "<td><div style='padding-left: " + data[i].pad + "px;" + (data[i].isDepart == "Y" ? "font-weight: bold;" : "") + "'><i onclick='toggleDepart($(this));' class='icon-plus tree-icon " + (data[i].hasChild == "Y" ? "" : "invisiable") + "'></i>" + (data[i].isDepart == "Y" ? data[i].departName : data[i].username) + "</div></td>";
					html += "<td><div>" + (data[i].positionName ? data[i].positionName : "") + "</div></td>";
					html += "</tr>";
				}
				
				var $trs = $(html);
				if($tr) { $tr.after($trs); } else { $("tr:gt(0)", $tab).remove(); $tab.append($trs); }
				
				// init check box
				$("#usersTab input").not("#u_all, #showStructure").iCheck({checkboxClass: "icheckbox_square-blue"});
			}, true);
		}
	}
	
	function toggleDepart($i) {
		var $tr = $i.closest("tr");
		
		if(!$tr.attr("hasLoad")) {
			loadUsersData($_us_src, $tr);
			$tr.attr("hasLoad", "Y");
		} else {
			$("#usersTab tr[parentId='" + $tr.attr("id") + "']").toggle();
		}
		
		if($i.hasClass("icon-plus")) { $i.removeClass("icon-plus").addClass("icon-minus"); } else { $i.removeClass("icon-minus").addClass("icon-plus"); }
	}
	
	function clickUsers($tr) {
		$("input", $tr).iCheck("toggle");
	}
	
	function selectUsers(isClear) {
		if(isClear) {
			$_us_src.attr("val", "").val("");
		} else {
			var $sel = $("#usersTab input:checkbox:checked"), $tr, ids = [], names = [];
			
			$sel.each(function() {
				$tr = $(this).closest("tr");
				if(!$tr.attr("id"))  return true;
				
				ids.push($tr.attr("id"));
				names.push($("td:eq(1)", $tr).text());
			});
			
			$_us_src.attr("val", ids.join(",")).val(names.join(", "));
		}
		
		// if has filter, call search to refresh table
		if(typeof(search) == "function")  search();
		
		$("#usersModal").dialog("close");
	}
	
	// init select all
	$("#u_all").iCheck({checkboxClass: "icheckbox_square-blue"}).on("ifChecked", function() { $("#usersModal input").not("#showStructure").iCheck("check"); }).on("ifUnchecked", function() { $("#usersModal input").not("#showStructure").iCheck("uncheck"); });
	
	// init show struture users
	$("#showStructure").iCheck({checkboxClass: "icheckbox_square-blue"}).on("ifChecked", function() { loadUsersData($_us_src); }).on("ifUnchecked", function() { loadUsersData($_us_src); });
</script>
