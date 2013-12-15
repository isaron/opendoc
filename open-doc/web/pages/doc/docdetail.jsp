<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cloud" tagdir="/WEB-INF/tags/" %>

<!DOCTYPE HTML PUBtdC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/uploadify.css" />">
	
	<script type="text/javascript" src="<c:url value="/flashpaper/flexpaper.js" />"></script>
	<script type="text/javascript" src="<c:url value="/flashpaper/config.js" />"></script>
	
	<script type="text/javascript" src="<c:url value="/scripts/docOperate.js" />"></script>
	<script type="text/javascript" src="<c:url value="/scripts/jquery.uploadify.min.js" />"></script>
</head>

<body>
	<div class="wrapper">
	
		<div id="btnDiv">
            <c:if test="${param.isWorkspace == 'N'}">
                <a href="<c:url value="/docstore/openDocstore.do?parentId=${param.parentId}" />" class="button button-rounded button-flat-primary">返回</a>
            </c:if>

            <c:if test="${param.isWorkspace == 'Y'}">
                <a href="<c:url value="/work/openWork.do" />" class="button button-rounded button-flat-primary">返回</a>
            </c:if>

			<span class="button-split"></span>

            <c:if test="${doc.status == 0 && doc.isLatest == 'Y'}">
                <button onclick="checkout($('#entityId').val());" class="button button-rounded button-flat-primary">检出</button>
            </c:if>

            <c:if test="${doc.status != 0 || doc.isLatest != 'Y'}">
                <button onclick="checkout($('#entityId').val());" class="button button-rounded button-flat-primary" disabled="disabled">检出</button>
            </c:if>

            <c:if test="${doc.status == 1 && doc.canCheckin && doc.isLatest == 'Y'}">
                <button onclick="checkin();" class="button button-rounded button-flat-primary">检入</button>
            </c:if>

            <c:if test="${doc.status != 1 || !doc.canCheckin || doc.isLatest != 'Y'}">
                <button onclick="checkin();" class="button button-rounded button-flat-primary" disabled="disabled">检入</button>
            </c:if>

			<span class="button-split"></span>
			
			<a id="downloadBtn" href="<c:url value="/download.action?fileName=${doc.attach.id}.${doc.attach.extendType}&realName=${doc.name}" />" class="button button-rounded button-flat-primary">下载文档</a>
		</div>
		
        <div class="row-fluid">
            <div class="span9">
                <div class="name">${doc.name} [${doc.docVersion}]</div>

                <div id="viewArea" class="doc-view">
                    <c:if test="${textContent != null}">
                        ${textContent}
                    </c:if>
                </div>

                <div class="title">基本信息</div>

                <table class="detail-table">
                    <tr>
                        <td class="label-td">状态</td>
                        <td class="half-td"><cloud:fileStatus status="${doc.status}" /></td>
                        <td class="label-td">版本</td>
                        <td class="half-td">${doc.docVersion}</td>
                    </tr>
                    <tr>
                        <td class="label-td">文件类型</td>
                        <td class="half-td">${doc.attach.extendType}</td>
                        <td class="label-td">文件大小</td>
                        <td class="half-td"><cloud:fileSize size="${doc.attach.fileSize}" /></td>
                    </tr>
                    <tr>
                        <td class="label-td">创建人</td>
                        <td class="half-td"><cloud:user userId="${doc.creator}" /></td>
                        <td class="label-td">创建时间</td>
                        <td class="half-td"><cloud:time date="${doc.createTime}" /></td>
                    </tr>
                </table>

                <cloud:record records="${records}" />

                <cloud:note />
            </div>

            <div class="span3 version-bar">
                <div class="name">文档版本</div>
                <ul>
                    <c:forEach var="file" items="${versionDocs}">
                        <li>
                            <c:if test="${doc.docVersion == file.docVersion}">
                                <span>${file.name} [${file.docVersion}]</span>
                            </c:if>
                            <c:if test="${doc.docVersion != file.docVersion}">
                                <a href="<c:url value="/docdetail/openDoc.do?docId=${file.id}&parentId=${param.parentId}" />">${file.name} [${file.docVersion}]</a>
                            </c:if>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>

		<input id="entityId" type="hidden" value="${param.docId}" />
		<input id="parentId" type="hidden" value="${param.parentId}" />
		<input id="extendFlag" type="hidden" value="<cloud:fileImg extend="${doc.attach.extendType}" />" />
	</div>
	
	<div id="checkinDialog" class="hide"><p style="padding: 5px;">
        <table class="edit-table" style="margin-top: 20px;margin-bottom: 10px;">
            <tr>
                <td class="label-td">备注</td>
                <td><textarea id='checkinNote' style='width: 550px;height: 100px;'></textarea></td></td>
            </tr>
        </table>
        <div style="margin-left: 15px;"><input id="file_upload" type="file" /></div>
	</p></div>

	<script>
		var extendFlag = $("#extendFlag").val().trim(), html = "";
		
		// if is image
		if(!extendFlag) {
			html += "<img src='" + top.basePath + "upload/" + "${doc.attach.id}" + "." + "${doc.attach.extendType}" + "' />";
		}
		// if is file that cann't preview
		else if(extendFlag == "file_bk") {
			html += "<div class='alert alert-info'>该文档不能在线浏览。</div>";
		}
		// if is text file
		else if(extendFlag == "txt_bk") {
			$("div.doc-view").addClass("doc-view-border");
		}
		// if is office or pdf file
		else {
			var doc = "${doc.attach.id}";
			
			_remoteCall("docdetail/getConvertStatus.do", {docId: doc}, function(status) {
				if(status == "Y") {
					initOnlineView(doc);
				} else {
					html += "<div class='alert alert-info'>该文档正在后台进行文档转换，这需要花费2~7分钟，过一段时间才能在线浏览。</div>";
				}
			}, false, true);
		}
		
		$("div.doc-view").append(html);
		
		// init checkin upload dialog
		ppmDialog("#checkinDialog", "上传文件", {
	        "关闭": function() { $(this).dialog("close"); }
		});
		
		// get attach size config
		var attachId = "";
		
		_remoteCall("system/getAttachConfig.do", null, function(size) {
			
			// init upload file
			$("#file_upload").uploadify({
				multi: false,
				buttonText: "上传文件",
				fileSizeLimit: size + "MB",
				removeCompleted: false,
				swf: parent.basePath + "/css/uploadify.swf",
				uploader : parent.basePath + "upload.action" + getJSessionCookie(),
				onUploadSuccess: function(file, data, response) {
					attachId = data;
				},
				onQueueComplete: function() {
					_remoteCall("docop/checkin.do", {docFileId: $("#entityId").val(), attachId: attachId, note: $("#checkinNote").val()}, function(data) {
						if(data)  reloadPage("docdetail/openDoc.do?docId=" + data + "&parentId=" + $("#parentId").val());
					});
				}
			});
		});
	</script>
</body>
</html>
