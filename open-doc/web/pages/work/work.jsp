<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib prefix="cloud" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <script type="text/javascript" src="<c:url value="/scripts/docstore.js" />"></script>
    <style>
        .more-div {padding-left: 880px;}
        .more-div span {cursor: pointer;text-decoration: underline;}
    </style>
</head>

<body>
	<div class="wrapper" style="max-width: 950px;">
		<div class="title">我标注的文档</div>

        <div class="file-div">
        <c:forEach items="${starFiles}" var="file">
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
        </div>

        <c:if test="${starMore == 'Y'}">
            <div class="more-div" type="0"><span onclick="showMore(0);">更多</span></div>
        </c:if>

        <c:if test="${starFiles == null || fn:length(starFiles) == 0}">
            <div style="text-align: center;margin: 20px;font-size: 17px;color: #bbb;">暂无标注文档</div>
        </c:if>

        <div class="title">我上传的文档</div>

        <div class="file-div">
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
        </div>

        <c:if test="${uploadMore == 'Y'}">
            <div class="more-div" type="1"><span onclick="showMore(1);">更多</span></div>
        </c:if>

        <c:if test="${uploadFiles == null || fn:length(uploadFiles) == 0}">
            <div style="text-align: center;margin: 20px;font-size: 17px;color: #bbb;">暂无上传文档</div>
        </c:if>

        <div class="title">我操作的文档</div>

        <div class="file-div">
        <c:forEach items="${operateFiles}" var="file">
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
        </div>

        <c:if test="${operateMore == 'Y'}">
            <div class="more-div" type="2"><span onclick="showMore(2);">更多</span></div>
        </c:if>

        <c:if test="${operateFiles == null || fn:length(operateFiles) == 0}">
            <div style="text-align: center;margin: 20px;font-size: 17px;color: #bbb;">暂无操作文档</div>
        </c:if>
	</div>

    <script>
        var isWorkspace = "Y", isStore = false, pages = [1, 1, 1];

        function showMore(type) {
            openLoader();

            _remoteCall("work/showMore.do", {type: type, page: ++pages[type]}, function(data) {
                console.info(data);
                var html = "", hasMore = data.hasMore, files = data.files;

                for(var i in files) {
                    var f = files[i], a = f.attach;

                    html += "<div id='" + f.id + "' sign='file' class='attach_box' onclick='selectDoc($(this));' ondblclick='enter($(this));' storeFileName='" + a.id + "." + a.extendType + "' realFileName='" + f.name + "'>";
                    html += "<div class='attach_img'>";

                    if(isFileImg(a.extendType)) {
                        html += "<img src='" + top.basePath + "upload/" + a.id + "." + a.extendType + "' />";
                    } else {
                        html += "<div class='" + f.fileBgStyle + "'>";
                        if(f.status == 1)  html += "<img src='" + top.basePath + "img/refresh.png' class='checkout-img' title='已检出' />";
                        html += "</div>";
                    }

                    html += "</div>";
                    html += "<div class='attach_name' ondblclick='rename();' onmouseover='this.title=this.innerText'>" + f.name + "</div>";
                    html += "</div>";
                }

                if(hasMore == "N")  $("div.more-div[type=" + type + "]").remove();
                $("div.file-div:eq(" + type + ")").append(html);

                autoHeight();
                closeLoader();
            }, true);
        }
    </script>
</body>
</html>
