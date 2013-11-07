<%@ tag pageEncoding="UTF-8" import="com.cloud.platform.DateUtil" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="cloud" tagdir="/WEB-INF/tags/" %>

<%@ attribute name="records" type="java.util.List" %>

<div class="record">
    <div class="title">操作记录</div>

    <c:if test="${records == null || fn:length(records) == 0}"><div style="margin-left: 20px;">无</div></c:if>

    <c:forEach var="record" items="${records}">
        <div class="record_box">
            <div class="record_op">
                <span><cloud:user userId="${record.creator}" /></span>
                <span>${record.operate}</span>
                <span><cloud:time date="${record.createTime}" /></span>
            </div>
            <div class="record_note">${record.note}</div>
        </div>
    </c:forEach>
</div>