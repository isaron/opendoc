<%@ tag pageEncoding="UTF-8" import="com.cloud.platform.DateUtil" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ attribute name="isEdit" %>
<%@ attribute name="attachs" type="java.util.List" %>

<div class="attach">
	<div class="title">附件</div>
	
	<% if(isEdit == "Y") { %>
		<div style="margin-left: 15px;"><input id="file_upload" type="file" /></div>
		<script>
			$("#file_upload").uploadify({
				buttonText: "上传",
				removeCompleted: false,
				swf: parent.basePath + "css/uploadify.swf",
				uploader : parent.basePath + "upload.action?" + getJSessionCookie(),
				onQueueComplete: function() {
					parent.autoHeight();
				},
				onUploadSuccess: function(file, data, response) {
					attachIds.push(data);
				}
			});
		</script>
	<% } %>
	
	<c:if test="${isEdit != 'Y' && (attachs == null || fn:length(attachs) == 0)}"><div style="margin-left: 20px;">无</div></c:if>
		
	<c:forEach var="attach" items="${attachs}">
	<div class="attach_box">
		<div class="attach_img">
			<c:if test="${fn:toUpperCase(attach.extendType) == 'JPG' || fn:toUpperCase(attach.extendType) == 'JPEG' || fn:toUpperCase(attach.extendType) == 'GIF' || fn:toUpperCase(attach.extendType) == 'PNG' || fn:toUpperCase(attach.extendType) == 'BMP'}">
				<a class="fancybox" rel="group" title="${attach.fileName}" href="<c:url value="/upload/${attach.id}.${attach.extendType}" />">
					<img src="<c:url value="/upload/${attach.id}.${attach.extendType}" />" />
				</a>
			</c:if>
			
			<c:if test="${!(fn:toUpperCase(attach.extendType) == 'JPG' || fn:toUpperCase(attach.extendType) == 'JPEG' || fn:toUpperCase(attach.extendType) == 'GIF' || fn:toUpperCase(attach.extendType) == 'PNG' || fn:toUpperCase(attach.extendType) == 'BMP')}">
				<div onclick="downloadAttach('${attach.id}.${attach.extendType}', '${attach.fileName}');" class="file_bk"></div>
			</c:if>
		</div>
		<div class="attach_name">${attach.fileName}</div>
	</div>
	</c:forEach>
</div>

<script>
	$(".fancybox").fancybox({
		openEffect	: 'elastic',
    	closeEffect	: 'elastic'
	});
	
	function downloadAttach(attachName, realName) {
		location.href = parent.basePath + "download.action?fileName=" + attachName + "&realName=" + realName;
	}
</script>