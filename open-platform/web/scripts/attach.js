$(function() {
	$(".fancybox").fancybox({
		openEffect	: 'elastic',
		closeEffect	: 'elastic'
	});
})

function downloadAttach(attachName, realName) {
	location.href = parent.basePath + "download.action?fileName=" + attachName + "&realName=" + realName;
}