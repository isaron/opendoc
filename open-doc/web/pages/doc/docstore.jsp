<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.cloud.platform.*"%>

<%@ taglib prefix="cloud" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/uploadify.css" />">
	
	<script type="text/javascript" src="<c:url value="/scripts/docstore.js" />"></script>
	<script type="text/javascript" src="<c:url value="/scripts/jquery.uploadify.min.js" />"></script>
</head>

<body>
	<div class="wrapper">
		<div id="btnDiv" style="padding-bottom: 10px;border-bottom: 1px dotted #ccc;">
			<button id="returnBtn" onclick="goparent();" class="button button-rounded button-flat-primary">返回</button>
			<button onclick="reloadPage();" class="button button-rounded button-flat-primary">刷新</button>
			<span class="button-split"></span>
			
			<button onclick="createDir();" class="button button-rounded button-flat-primary">新建文件夹</button>
			<button onclick="showUpload();" class="button button-rounded button-flat-primary">上传文件</button>
			<button id="downloadBtn" onclick="download();" class="button button-rounded button-flat-primary" disabled="true">下载文件</button>
			<span class="button-split"></span>
			
			<button id="renameBtn" onclick="rename();stopBubble();" class="button button-rounded button-flat-primary" disabled="true">重命名</button>
			<button id="removeBtn" onclick="removeDoc();" class="button button-rounded button-flat-primary" disabled="true">删除</button>
		</div>
		
		<div id="fileDiv">
			<c:forEach items="${dirs}" var="dir">
				<div id="${dir.id}" sign="dir" class="attach_box" onclick="selectDoc($(this));" ondblclick="enter($(this));">
					<div class="attach_img">
						<c:if test="${dir.isDepartDir != 'Y'}">
							<div class="dir_bk"></div>
						</c:if>
						<c:if test="${dir.isDepartDir == 'Y'}">
							<div class="dir_depart_bk"></div>
						</c:if>
					</div>
					<div class="attach_name" <c:if test="${dir.isDepartDir != 'Y'}">ondblclick="rename();"</c:if>>${dir.name}</div>
				</div>
			</c:forEach>
			
			<c:forEach items="${files}" var="file">
				<div id="${file.id}" sign="file" class="attach_box" onclick="selectDoc($(this));" ondblclick="enter($(this));" storeFileName="${file.attach.id}.${file.attach.extendType}" realFileName="${file.name}">
					<div class="attach_img">
						<c:if test="${fn:toUpperCase(file.attach.extendType) == 'JPG' || fn:toUpperCase(file.attach.extendType) == 'JPEG' || fn:toUpperCase(file.attach.extendType) == 'GIF' || fn:toUpperCase(file.attach.extendType) == 'PNG' || fn:toUpperCase(file.attach.extendType) == 'BMP'}">
							<img src="<c:url value="/upload/${file.attach.id}.${file.attach.extendType}" />" />
						</c:if>
						<c:if test="${!(fn:toUpperCase(file.attach.extendType) == 'JPG' || fn:toUpperCase(file.attach.extendType) == 'JPEG' || fn:toUpperCase(file.attach.extendType) == 'GIF' || fn:toUpperCase(file.attach.extendType) == 'PNG' || fn:toUpperCase(file.attach.extendType) == 'BMP')}">
							<div class="<cloud:fileImg extend="${file.attach.extendType}" />"></div>
						</c:if>
					</div>
					<div class="attach_name" ondblclick="rename();" onmouseover="this.title=this.innerText">${file.name}</div>
				</div>
			</c:forEach>
			
			<c:if test="${(dirs == null || fn:length(dirs) == 0) && (files == null || fn:length(files) == 0)}">
				<div style="text-align: center;margin-top: 20%;font-size: 17px;color: #bbb;">文件夹下还没文件，赶紧上传吧~</div>
			</c:if>
		</div>
	</div>
	
	<div id="uploadDialog" class="hide"><p style="padding: 5px;">
		<div style="margin-left: 15px;"><input id="file_upload" type="file" /></div>
	</p></div>
	
	<input id="parentId" type="hidden" value="${param.parentId}" />
	<input id="grandId" type="hidden" value="${grandId}" />
</body>
</html>
