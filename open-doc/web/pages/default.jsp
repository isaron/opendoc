<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<style>
		#header {height: 30px;background: rgb(0, 51, 102);color: white;padding: 10px 15px 0 10px;}
		#header .logo {font-size: 22px;font-weight: bold;}
		#header .operate {float: right;display: inline-block;margin-right: 20px;}
		#header .operate .dropdown {display: inline-block;}
		#header .operate .head-link {color: white;margin-right: 15px;}
		#header .dropdown-toggle {color: white!important;text-decoration: none;}
		#header .dropdown-toggle:hover {text-decoration: underline;}
		#mainContent {margin: 5px 10px 15px 10px;}
		.tabbable {margin-bottom: -19px;}
		#footer {clear: both;border-top: 1px solid #cccccc;text-align: center;padding: 5px;}
		#footer span {float: right;margin-right: 20px;}
	</style>
</head>

<body>
	<div id="header">
		<span id="logoTxt" class="logo"></span>
		<span class="operate">
			<a href="http://www.ppm123.cn/bbs/forum.php" target="_blank" class="head-link">问题反馈</a>
			
			当前登录：
			<div class="dropdown">
				<a data-toggle="dropdown" class="dropdown-toggle" href="#"><sec:authentication property="name"/><i class="icon-chevron-down icon-white" style="vertical-align: middle;margin-left: 3px;margin-bottom: 3px;"></i></a>
				
				<ul class="dropdown-menu" style="max-width: 60px;min-width: 60px;">
					<li><a href="#" style="padding: 3px 15px;" onclick="showLock();return false;">锁定</a></li>
					<li><a href="<c:url value="/user/logout.do" />" style="padding: 3px 15px;">退出</a></li>
				</ul>
			</div>
		</span>
	</div>
	
	<div id="mainContent">
		<ul id="navtab">
			<li url="<c:url value="/pages/work/work.jsp" />">我的文档</li>
			<li url="<c:url value="/docstore/openDocstore.do" />">文档仓库</li>
			<li url="<c:url value="/pages/account/account.jsp" />">组织架构</li>
			<li url="<c:url value="/pages/system/system.jsp" />">系统管理</li>
		</ul>
		
		<iframe id="mainFrame" name="mainFrame" width="100%" scroll="no" onload="autoHeight();"
			frameborder="0" style="border: 1px solid #ccc;border-top: 0;border-radius: 0 0 10px 10px;"></iframe>
	</div>
	
	<div id="loader">
		<div>
			<i class="icon-spinner icon-spin icon-3x"></i><br />
			<span id="loaderTip"></span>
		</div>
	</div>
	
	<div id="dialog-alert" class="hide"><p style="padding: 5px;">
		<i class="icon-ok" style="margin-right: 15px;"></i><span id="alert-content"></span>
	</p></div>
	
	<div id="dialog-confirm" class="hide"><p style="padding: 5px;">
		<i class="icon-trash" style="margin-right: 15px;"></i><span id="confirm-content"></span>
	</p></div>
	
	<div class="hide">
		<span id="loaderTip1">正在加载...</span>
		<span id="selectText">请选择</span>
	</div>
	
	<div id="lockModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header">
			<h3 id="myModalLabel">系统锁定</h3>
		</div>
		
		<div class="modal-body">
			<span style="margin: 0 20px;">密码：</span><input id="lockPwd" type="password" style="width: 380px;height: 28px;" />
			<span id="lockTip" class="outline hide">密码错误</span>
		</div>
		
		<div class="modal-footer">
			<button onclick="unLock();" class="btn btn-primary">确认</button>
		</div>
	</div>
	
	<script>
		var basePath = "${BASEPATH}";
		
		// init logo text
		_remoteCall("system/getSystemName.do", null, function(data) {
			$("#logoTxt").text(data);
		});
		
		// init nav menu event
		$("#navtab").navtab({frame: "mainFrame"});
		
		// system lock
		$("#lockModal").modal({backdrop: "static", show: false});
		
		function showLock() {
			$("#lockPwd").val("");
			$("#lockTip").hide();
			$("#lockModal").modal("show");
		}
		
		function unLock() {
			_remoteCall("user/unlock.do", {password: $("#lockPwd").val()}, function(data) {
				if(data == "Y") {
					$("#lockTip").hide();
					$("#lockModal").modal("hide");
				} else {
					$("#lockPwd").val("").focus();
					$("#lockTip").show();
				}
			});
		}
	</script>
</body>
</html>
