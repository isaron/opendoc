var attachIds = [];

$(function() {
	// bind body click event for update rename
	$(document.body).click(function() { doRename(); });

    // init button status
    initBtnStatus("init");

    if(isStore) {
        // init upload dialog
        ppmDialog("#uploadDialog", "上传文件", {
            "关闭": function() { $(this).dialog("close"); }
        });

        // get attach size config
        _remoteCall("system/getAttachConfig.do", null, function(size) {

            // init upload file
            $("#file_upload").uploadify({
                buttonText: "上传文件",
                fileSizeLimit: size + "MB",
                removeCompleted: false,
                swf: parent.basePath + "/css/uploadify.swf",
                uploader : parent.basePath + "upload.action" + getJSessionCookie(),
                onUploadSuccess: function(file, data, response) {
                    attachIds.push(data);
                },
                onQueueComplete: function() {
                    _remoteCall("docstore/uploadFiles.do", {parentId: $("#parentId").val(), attachIds: attachIds.join(",")}, function() {
                        reloadPage();
                    });
                }
            });
        });
    }
});

function initBtnStatus(situation, $d) {
	if(situation == "init") {
		if(!$("#parentId").val())  $("#returnBtn").attr("disabled", true);
	}
	else if(situation == "selectDoc") {
		var sign = $d.attr("sign"), isDepartDir = $("div.dir_depart_bk", $d).size() > 0;
		
		$("#renameBtn, #removeBtn").attr("disabled", isDepartDir);
		$("#downloadBtn").attr("disabled", sign == "dir");
	}
	else if(situation == "remove") {
		$("#downloadBtn, #renameBtn, #removeBtn").attr("disabled", true);
	}
}

function createDir() {
	_remoteCall("docstore/createDir.do", {parentId: $("#parentId").val()}, function(id) {
		var html = "<div id='" + id + "' sign='dir' class='attach_box doc-selected' onclick='selectDoc($(this));' ondblclick='enter($(this));'>";
		html += "<div class='attach_img'>";
		html += "<div class='dir_bk'></div>";
		html += "</div>";
		html += "<div class='attach_name' ondblclick='rename()'>新建文件夹</div>";
		html += "</div>";
		
		var $d = $(html);
		
		$("div.attach_box").removeClass("doc-selected");
		$("#fileDiv").append($d);
		autoHeight();
		
		initBtnStatus("selectDoc", $d);
	});
}

function selectDoc($d) {
	$("div.attach_box").removeClass("doc-selected");
	$d.addClass("doc-selected");
	
	initBtnStatus("selectDoc", $d);
}

function rename() {
	var $d = $("div.doc-selected"), $name = $("div.attach_name", $d);
	if($d.size() == 0) { ppmAlert("信息", "请选择要操作的文件！");  return; }
	
	if($("input.input-rename").size() > 0)  return;
	
	var originName = $name.text();
	var $inp = $("<input type='text' class='input-rename' onkeydown='if(isEnterKey()) { doRename(); }' />").val(originName).appendTo($name.html(""));
	$inp.focus().click(function() { return false; });
	
	stopBubble();
}

function doRename() {
	var $inp = $("input.input-rename");
	if($inp.size() == 0)  return;
	
	var newName = $inp.val(), $d = $inp.closest("div.attach_box"), docId = $d.attr("id"), isDir = $d.attr("sign") == "dir";
	
	_remoteCall("docstore/rename.do", {docId: docId, newName: newName, isDir: isDir ? "Y" : "N"}, function() {
		$inp.parent().text(newName);  $inp.remove();
	});
}

function removeDoc() {
	var $d = $("div.doc-selected");
	if($d.size() == 0) { ppmAlert("信息", "请选择要操作的文件！");  return; }
	
	var docId = $d.attr("id"), isDir = $d.attr("sign") == "dir";
	var title = isDir ? "删除文件夹" : "删除文档";
	var content = isDir ? "确定要删除该文件夹及底下的所有文件么？" : "确定要删除该文档么？";
	
	ppmConfirm(title, content, function() {
		_remoteCall("docstore/remove.do", {docId: docId, isDir: isDir ? "Y" : "N"}, function() {
			$d.remove();
			initBtnStatus("remove");
		});
	});
}

function enter($d) {
	if($d.attr("sign") == "dir") {
		reloadPage("docstore/openDocstore.do?parentId=" + $d.attr("id"));
	} else {
		reloadPage("docdetail/openDoc.do?docId=" + $d.attr("id") + "&parentId=" + $("#parentId").val() + "&isWorkspace=" + isWorkspace);
	}
}

function goparent() {
	reloadPage("docstore/openDocstore.do?parentId=" + $("#grandId").val());
}

function showUpload() {
	$("#uploadDialog").dialog("open");
}

function download() {
	var $d = $("div.doc-selected");
	if($d.size() == 0) { ppmAlert("信息", "请选择要操作的文件！");  return; }
	
	reloadPage("download.action?fileName=" + $d.attr("storeFileName") + "&realName=" + $d.attr("realFileName"));
}