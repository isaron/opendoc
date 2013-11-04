(function($){
	
$.fn.select = function(options) {
	
	return this.each(function() {
		var $s = $(this);
		
		/**
		 * init config
		 */
		var defaults = {
			load: null,
			onchange: null
		};

		$s.config = $.extend(defaults, options);
		
		// init select-span and select-img
		var $show = $("<span class='select-text'></span>").appendTo($s);
		var $img = $("<span class='select-img'><img src='" + top.basePath + "img/select.png' /></span>").appendTo($s);
		
		// load select items
		loadDatas($s);
		
		var $u = $("ul", $s);
		
		// init select click event, show items
		initSelectClick($s, $show);
		
		// init select items
		initSelectItems($s, $u, $show);
	});
};

$.fn.selectVal = function(val) {
	var $s = this, $show = $("span.select-text", $s);
	
	// set value
	if(val == "" || val) {
		var $li = $("li[val='" + val + "']", this);
		
		if($li.size() > 0)  $show.attr("val", val).text($li.text());
		else if(val == "")  $show.attr("val", "").text("");
		
		return this;
	}
	// get value
	else {
		return $show.attr("val") ? $show.attr("val") : "";
	}
};

$.fn.selectText = function() {
	var $s = this, $show = $("span.select-text", $s);
	return $show.attr("val") ? $show.text() : "";
}

$.fn.selectRefresh = function(datas) {
	var $s = this, $u = $("ul", $s), $show = $("span.select-text", $s), items = "";
	
	// remove origin items first
	$show.attr("val", "").text("");
	$("li:gt(0)", $u).remove();
	
	// append new items
	for(var i in datas) {
		var d = datas[i];
		items += "<li val='" + d.i + "'>" + d.v + "</li>";
	}
	$u.append(items);
	
	// re-init events
	initSelectClick($s, $show);
	initSelectItems($s, $u, $show);
}

function loadDatas($s) {
	if(!$s.config.load)  return;
	
	var datas = eval($s.config.load()), items = "";
	
	items += "<ul class='menu-ul'>";
	items += "<li>" + $("#selectText", top.document).text() + "</li>";
	
	for(var i in datas) {
		var d = datas[i];
		items += "<li val='" + d.i + "'>" + d.v + "</li>";
	}
	
	items += "</ul>";
	
	$s.append(items);
}

function initSelectClick($s, $show) {
	$s.click(function() {
		var $u = $("ul", $s), pos = $s.offset(), lspan = 0, hspan = 0;
		
		if($s.closest("div[role='dialog']").size() > 0) {
			var mpos = $s.closest("div[role='dialog']").offset();
			lspan = mpos.left + 4;  hspan = mpos.top + 56;
		}
		
		$u.css({"left": pos.left - lspan, "top": pos.top - hspan + 28}).width($s.width());
		$u.toggle();
		
		$("ul.menu-ul").not($u).hide();
		
		return false;
	});
	
	$(document.body).click(function() { $("ul.menu-ul").hide(); });
}

function initSelectItems($s, $u, $show) {
	$u.children().each(function() {
		if(!$(this).attr("val")) {
			$(this).addClass("menu-empty");
		}
		
		if($(this).attr("checked") == "checked") {
			$show.attr("val", $(this).attr("val")).text($(this).text());
		}
		
		$(this).click(function() {
			var originVal = $show.attr("val"), newVal = $(this).attr("val");
			
			$show.attr("val", newVal);
			$show.text(newVal ? $(this).text() : "");
			
			if(originVal != newVal && $s.config.onchange)  $s.config.onchange(newVal);
		});
	});
}

})(jQuery);   