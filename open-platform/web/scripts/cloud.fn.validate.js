function checkForm() {
	var check = true;
	$("div.validate").hide();
	
	// check require
	$(".input-require").each(function() {
		if($(this).val().trim() == "") {
			$(this).focus().parent().append("<div class='outline validate'>不能为空</div>");
			check = false;
			return false;
		}
	});
	
	// check max length
	$("input[class*='input-maxlen']").each(function() {
		if(!check)  return false;
		var className = $(this)[0].className, names = className.split(" "), maxlen = 0;
		
		for(var i in names) {
			if(names[i].indexOf("input-maxlen") >= 0) {
				maxlen = parseInt(names[i].substring("input-maxlen".length, names[i].length));
			}
		}
		
		if(maxlen != 0 && $(this).val().length > maxlen) {
			$(this).focus().parent().append("<div class='outline validate'>长度不能超过" + maxlen + "</div>");
			check = false;
			return false;
		}
	});
	
	// check no repeat
	$(".input-norepeat").each(function() {
		if(!pageInfo || !check)  return false;
		var $inp = $(this);
		
		var entityId = $("#_entityId").size() == 0 ? "" : $("#_entityId").val();
		
		_remoteCall("validate/norepeat.do", {model: pageInfo.model, field: $(this).attr("name"), value: $(this).val(), entityId: entityId}, function(data) {
			if(data == "N") {
				$inp.focus().parent().append("<div class='outline validate'>" + $inp.parent().prev().text() + "已经存在</div>");
				check = false;
			}
		}, false, true);
		
		if(!check)  return false;
	});
	
	// check integer number
	var regInt = /^[1-9][0-9]*$/;
	$(".input-integer").each(function() {
		if(!check)  return false;
		var val = $(this).val();
		
		if(!regInt.test(val)) {
			$(this).focus().parent().append("<div class='outline validate'>请输入正整数</div>");
			check = false;
			return false;
		}
	});
	
	return check;
}