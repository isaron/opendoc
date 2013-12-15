<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib prefix="cloud" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <script type="text/javascript" src="<c:url value="/scripts/docstore.js" />"></script>
</head>

<body>
	<div class="wrapper">
		<div class="title">我标注的文档</div>

        <div class="title">我上传的文档</div>

        <c:forEach items="${uploadFiles}" var="file">
            <div id="${file.id}" sign="file" class="attach_box" onclick="selectDoc($(this));" ondblclick="enter($(this));" storeFileName="${file.attach.id}.${file.attach.extendType}" realFileName="${file.name}">
                <div class="attach_img">
                    <c:if test="${fn:toUpperCase(file.attach.extendType) == 'JPG' || fn:toUpperCase(file.attach.extendType) == 'JPEG' || fn:toUpperCase(file.attach.extendType) == 'GIF' || fn:toUpperCase(file.attach.extendType) == 'PNG' || fn:toUpperCase(file.attach.extendType) == 'BMP'}">
                        <img src="<c:url value="/upload/${file.attach.id}.${file.attach.extendType}" />" />
                    </c:if>
                    <c:if test="${!(fn:toUpperCase(file.attach.extendType) == 'JPG' || fn:toUpperCase(file.attach.extendType) == 'JPEG' || fn:toUpperCase(file.attach.extendType) == 'GIF' || fn:toUpperCase(file.attach.extendType) == 'PNG' || fn:toUpperCase(file.attach.extendType) == 'BMP')}">
                        <div class="<cloud:fileImg extend="${file.attach.extendType}" />">
                            <c:if test="${file.status == 1}"><img src="<c:url value="/img/refresh.png" />" class="checkout-img" title="已检出" /></c:if>
                        </div>
                    </c:if>
                </div>
                <div class="attach_name" ondblclick="rename();" onmouseover="this.title=this.innerText">${file.name}</div>
            </div>
        </c:forEach>

        <c:if test="${uploadFiles == null || fn:length(uploadFiles) == 0}">
            <div style="margin-left: 20px;">无</div>
        </c:if>

        <div class="title">我操作的文档</div>
	</div>
</body>
</html>
