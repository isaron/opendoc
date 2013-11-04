(function($){
	
$.fn.navtab = function(options) {
	
	return this.each(function() {
		var $s = $(this);
		
		/**
		 * init config
		 */
		var defaults = {
			isSide: false,
			frame: null
		};

		$s.config = $.extend(defaults, options);
		
		// init tab html
		initTabHtml($s);
		
		// init nav event
		initNavEvent($s);
	});
};

function initTabHtml($s) {
	// init container
	var $con;
	
	if($s.config.isSide) {
		$con = $s.wrap("<div style='width: 110px;display: inline-block;' class='tabbable tabs-left'></div>");
	} else {
		$con = $s.wrap("<div class='tabbable'></div>");
	}
	
	// init tab page frame
	var $frame;
	
	if($s.config.frame) {
		$frame = $("#" + $s.config.frame);
	} else {
		$frame = $("<iframe onload='autoFrameHeight($(this), true);' scroll='no' frameborder='0' align='top' width='" + (document.body.clientWidth - 150) + "px'></iframe>").insertAfter($s.parent());
	}
	
	// init nav items' html
	$s.addClass("nav nav-tabs").children().each(function(i) {
		if(i == 0) {
			$(this).addClass("active");
			$frame.attr("src", $(this).attr("url"));
		}
		
		var txt = $(this).text();
		$(this).html("<a href='#'>" + txt + "</a>");
	});
}

function initNavEvent($s) {
	var $frame = $s.parent().next("iframe");
	
	$s.children().click(function() {
		if($(this).hasClass("active"))  return true;
		$s.children().removeClass("active");  $(this).addClass("active");
		
		if($frame.size() > 0)  $frame.attr("src", $(this).attr("url"));
	});
}

})(jQuery);   