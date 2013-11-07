/**
 * check out file
 */
function checkout(docFileId) {
    var buttons = [{text: "检出", click: function() {
        openLoader();

        _remoteCall("docop/checkout.do", {docFileId : docFileId}, function(data) {
            if(data == "Y") {
                location.href = $("#downloadBtn").attr("href");
                setTimeout(function() { reloadPage(); }, 500);
            } else {
                closeLoader();
                ppmAlert("提示", "该文档已被检出！", function() { reloadPage(); });
            }
        });
    }}];

    ppmDialog(null, "检出", "sss", buttons);
}

/**
 * check in file
 */
function checkin() {
	$("#checkinDialog").dialog("open");
}