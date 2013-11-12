<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<link href="/favicon.ico" type="image/ico" rel="shortcut icon" />
	
	<style>
		body {font-size: 13px;}
		table tr {height: 50px;}
		.input-text {width: 350px;background-color: rgba(255,255,255,.8);border-radius: 0!important;font-size: 12px!important;border: 0!important;}
	</style>
</head>

<body>
	<div style="height: 20px;padding: 50px 80px;font-size: 19px;color: #555;">
		PPM Doc 文档管理系统
	</div>

	<div class="row-fluid" style="background: #00adef;height: 290px;padding-top: 120px;">
		<div class="span4" style="padding: 0 90px;">
			<img src="img/logo.png" />
		</div>
		
		<div class="span8" style="text-align: center;">
			<form action="j_spring_security_check" method="POST">
			<table style="font-size: 13px;margin: 0 auto;">
				<tr>
					<td><input type="text" name="j_username" class="input-text" placeholder="PPM用户名" value="ross" /></td>
				</tr>
				<tr>
					<td><input type="password" name="j_password" class="input-text" placeholder="PPM密码" value="1" /></td>
				</tr>
				<tr style="height: 20px;">
					<td align="center" style="color: white;font-size: 12px;">
						<c:if test="${param.error != null}">用户名或密码输入错误！</c:if>
					</td>
				</tr>
				<tr>
					<td align="center">
						<input class="button button-pill button-flat-primary" type="submit" value="登录" style="width: 150px;border: 0;" />
					</td>
				</tr>
			</table>
			</form>
		</div>
	</div>
	
	<div id="navTip" style="text-align: center;color: white;font-size: 12px;background: #00adef;padding-bottom: 10px;">
		PPM关注您的用户体验，推荐你使用最流行，最前端的Chrome浏览器 &nbsp;&nbsp; <a href="http://pan.baidu.com/s/1sZjY3" target="_blank" style="color: white;text-decoration: underline;">下载Chrome</a>
	</div>
	
	<div style="padding-top: 30px;text-align: center;color: #999;font-size: 12px;">
		Copyright @2013 PPM 版权所有. &nbsp;&nbsp; <a href="http://www.ppm123.cn" target="_blank">PPM项目网站</a> ｜ <a href="http://www.ppm123.cn/bbs/forum.php" target="_blank">问题反馈</a> ｜ <a href="#">移动APP</a> &nbsp;&nbsp; 粤ICP备13023285号
	</div>
</body>
</html>
