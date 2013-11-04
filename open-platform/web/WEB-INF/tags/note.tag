<%@ tag pageEncoding="UTF-8" %>

<div class="title">备注</div>
		
<div class="note">
	<div class="notes"></div>
	<textarea id="note" placeholder="请输入备注内容"></textarea>
	<button onclick="note();" class="button button-rounded button-flat-primary">备注</button>
</div>

<script>
	var notePage = 1;
	
	$(function() {
		refreshNote();		
	});
	
	function refreshNote(isReset) {
		if(isReset)  notePage = 1;
		
		_remoteCall("note/getNotes.do", {entityId: $("#entityId").val(), page: notePage++}, function(data) {
			var html = "", hasMore = data.hasMore, notes = data.notes;
			
			for(var i in notes) {
				html += "<div class='record_box'>";
				html += "<div class='record_op'>";
				html += "<span>" + notes[i].creator + "</span>";
				html += "<span>备注</span>";
				html += "<span>" + getTimeStr(notes[i].createTime) + "</span>";
				html += "</div>";
				html += "<div class='record_note'>" + notes[i].note + "</div>";					
				html += "</div>";
			}
			
			if(notePage == 2 && hasMore == "Y") {
				html += "<div id='hasMore' class='more' onclick='refreshNote();'>显示更多 <i class='icon-chevron-down' style='vertical-align: middle;margin-left: 3px;margin-bottom: 6px;'></i></div>";
			} else if(hasMore == "N") {
				$("#hasMore").remove();
			}
			
			if(notePage == 2) { $("div.notes").html(html); } else { $("div.notes").append(html); }
			
			$("#note").val("");
			autoHeight();
			closeLoader();
		}, true);
	}
	
	function note() {
		openLoader();
		
		_remoteCall("note/saveNote.do", {entityId: $("#entityId").val(), note: $("#note").val()}, function() {
			refreshNote(true);
		});
	}
</script>