/**
 * 调用后台SpringMVC Action
 */
function _remoteCall(url, params, callback, isJson, isAsync) {
	$.ajax({
		type: "POST",
		url: top.basePath + url,
		data: params ? params : {},
		async: isAsync ? false : true,
		dataType: isJson ? "json" : "text",
		success: function(data) {
			if(typeof(callback) == "function")  callback(data);
		}
	});
}

/**
 * open wait loader
 * 
 * @param type
 */
function openLoader(type) {
	if(!type)  type = 1;
	$("#loaderTip", top.document).text($("#loaderTip" + type, top.document).text());
	
	$("#loader", top.document).show();
}

/**
 * close wait loader
 */
function closeLoader() {
	$("#loader", top.document).hide();
}

/**
 * iframe adjust height by page content
*/
function autoHeight() {
	var ifm = top.document.getElementById("mainFrame");
	var h = $("div.wrapper", ifm.contentDocument.body).outerHeight() + 20;
	var minH = top.document.body.clientHeight - 100;
	
	ifm.height = h < minH ? minH : h;
}

function autoFrameHeight($frm, resetScroll) {
	if(!$frm) $frm = $("iframe", parent.document);
	
	var h = $("div.wrapper", $frm[0].contentDocument.body).outerHeight();
	var minH = top.document.body.clientHeight - 140;
	
	$frm[0].height = h < minH ? minH : h;
	autoHeight();
	
	if(resetScroll)  top.document.body.scrollTop = 0;
}

function reloadPage(url) {
	if(url) {
		location.href = top.basePath + url;
	} else {
		location.href = location.href;
	}
}

function ppmAlert(title, content, func) {
	$("#alert-content", top.document).text(content);
	
	$("#dialog-alert", top.document).dialog({
		width: 350,
	    height: 220,
	    modal: true,
	    title: title,
	    draggable: false,
	    resizable: false,
	    position: ["center", 200],
	    buttons: {
	        "确定": function() {
	        	$(this).dialog("close");
	        	if(func && typeof(func) == "function")  func();
	        }
		}
	});
}

function ppmConfirm(title, content, func) {
	$("#confirm-content", top.document).text(content);
	
	$("#dialog-confirm", top.document).dialog({
		width: 350,
	    height: 220,
	    modal: true,
	    title: title,
	    draggable: false,
	    resizable: false,
	    position: ["center", 200],
	    buttons: {
	        "确定": function() {
	        	if(func && typeof(func) == "function")  func();
	        	$(this).dialog("close");
	        },
	        "取消": function() { $(this).dialog("close"); }
		}
	});
}

function showInfo(message) {
    top.Messenger({extraClasses: 'messenger-fixed messenger-theme-air messenger-on-top'})
        .post({message: message, hideAfter: 3});
}

function ppmDialog(selector, title, buttons) {
	$(selector).dialog({
		autoOpen: false,
		resizable: false,
		title: title,
		width: 700,
	    height: 480,
	    modal: true,
	    position: ["center", 100],
	    buttons: buttons
	});
}

function ppmDialog2(selector, title, html, buttons, w, h) {
    if(!selector) {
        selector = "#tempDialog";

        if($(selector).size() == 0) {
            $("<div id='tempDialog'></div>").appendTo(document.body);
        }
    }

    if(html)  $(selector).html(html);

    if(!buttons)  buttons = [];
    buttons.push({text: "取消", click: function() { $(selector).dialog("close"); }});

    $(selector).dialog({
        autoOpen: true,
        resizable: false,
        title: title,
        width: w ? w : 700,
        height: h ? h : 480,
        modal: true,
        position: ["center", 100],
        buttons: buttons
    });
}

/**
 * stop event bubble
 * ps: you can also use 'return false' instead
 */
function stopBubble() {
	var e = getEvent();
	e.stopPropagation();
	e.preventDefault();
}

/**
 * get event
 */
function getEvent() {
	if(document.all)  return window.event;
	
	func = getEvent.caller;
	while(func != null) {
		var arg0 = func.arguments[0];
		if (arg0) {
			if ((arg0.constructor == Event || arg0.constructor == MouseEvent)
					|| (typeof (arg0) == "object" && arg0.preventDefault && arg0.stopPropagation)) {
				return arg0;
			}
		}
		func = func.caller;
	}
	
	return null;
}

/**
 * key check
 */
function isEnterKey() {
	return getEvent().keyCode == 13;
}

/**
 * get session id from cookie, deal with 302 error when upload
 */
function getJSessionCookie() {
	var arr = document.cookie.split(";");
	
	for(var i in arr) {
		if(arr[i].indexOf("JSESSIONID") >= 0) {
			return ";" + arr[i].trim().replace("JSESSIONID", "jsessionid");
		}
	}
	
	return "";
}

/**
 * combine elements' ids
 * 
 * @param $els
 * @return
 */
function combineIds($els) {
	if(!$els || $els.size() == 0)  return "";
	
	var ids = [];
	$els.each(function() { ids.push($(this).attr("id") ? $(this).attr("id") : ""); });
	
	return ids.join(",");
}

/**
 * check file is image file by file extend
 *
 * @param extendType
 * @returns {boolean}
 */
function isFileImg(extendType) {
    extendType = extendType.toUpperCase();
    return extendType == "JPG" || extendType == "JPEG" || extendType == "GIF" || extendType == "PNG" || extendType == "BMP";
}

/**
 * ****************************************************************************
 * date operates
 * ****************************************************************************
 */
function getDateStr(date) {
	if(!date)  return "";
	
	// if is java date json, convert to javascript date
	if(date.time)  date = new Date(date.time);
	
	return date.getFullYear() + "-" + formatTime(date.getMonth() + 1) + "-" + formatTime(date.getDate());
}

function getTimeStr(date, isSimple) {
	if(!date)  return "";
	
	// if is java date json, convert to javascript date
	if(date.time)  date = new Date(date.time);
	
	return (isSimple ? "" : (date.getFullYear() + "-")) + formatTime(date.getMonth() + 1) + "-" + formatTime(date.getDate())
			+ " " + formatTime(date.getHours()) + ":" + formatTime(date.getMinutes());
}

function formatTime(d) {
	if(d < 10)  return "0" + d;
	return d;
}

/**
 * ****************************************************************************
 * page operates
 * ****************************************************************************
 */
function initPage(page) {
	var html = "<li" + (page.page == 1 ? " class='disabled'" : "") + " onclick='gotoPage($(this), -1, " + page.page + ");'><a href='#' onclick='return false;'>上一页</a></li>";
	
	for(var i = 0; i < page.pageNum; i++) {
		html += "<li" + (page.page == (parseInt(i) + 1) ? " class='disabled'" : "") + " onclick='gotoPage($(this));'><a href='#' onclick='return false;'>" + (parseInt(i) + 1) + "</a></li>";
	}
	
	html += "<li" + (page.page == page.pageNum ? " class='disabled'" : "") + " onclick='gotoPage($(this), 1, " + page.page + ");'><a href='#' onclick='return false;'>下一页</a></li>";
	
	$("div.pagination ul").html(html);
}

function gotoPage($li, next, page) {
	if($li.hasClass("disabled"))  return;
	if(typeof(search) != "function")  return;
	
	if(!next) {
		search($li.text().trim());
	}
	else if(next < 0) {
		search(page - 1);
	}
	else if (next > 0) {
		search(page + 1);
	}
}

function getPageSn(page, i) {
	return ((page.page - 1) * page.pageSize + parseInt(i) + 1);
}

/**
 * ****************************************************************************
 * select operates
 * ****************************************************************************
 */

/**
 * show select users dialog
 * 
 * @param $inp
 */
function showUser($inp) {
	loadUserData($inp);
	$("#userModal").dialog("open");
}

/**
 * show select multi users dialog
 * 
 * @param $inp
 */
function showUsers($inp) {
	loadUsersData($inp);
	$("#usersModal").dialog("open");
}

/**
 * show select department dialog
 * 
 * @param $inp
 */
function showDepartment($inp) {
	loadDepartData($inp);
	$("#departModal").dialog("open");
}

/**
 * ****************************************************************************
 * for test in chrome console
 * ****************************************************************************
 */
function __info() {
	for(var i in arguments)  console.info(arguments[i]);
}

function __error() {
	for(var i in arguments)  console.error(arguments[i]);
}