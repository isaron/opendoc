/**
 * open doc detail page
 *
 * @param docId
 * @param parentId
 */
function openDoc(docId, parentId) {
    reloadPage("docdetail/openDoc.do?docId=" + docId + "&parentId=" + parentId);
}

/**
 * check out file
 */
function checkout(docFileId) {
    var buttons = [{text: "检出", click: function() {
        openLoader();

        _remoteCall("docop/checkout.do", {docFileId : docFileId, note: $("#checkoutNote").val()}, function(data) {
            if(data == "Y") {
                location.href = $("#downloadBtn").attr("href");
                setTimeout(function() { reloadPage(); }, 500);
            } else {
                closeLoader();
                ppmAlert("提示", "该文档已被检出！", function() { reloadPage(); });
            }
        });
    }}];

    var html = "<table class='edit-table' style='margin-top: 20px;'><tr>";
    html += "<td class='label-td'>备注</td>";
    html += "<td><textarea id='checkoutNote' style='width: 550px;height: 100px;'></textarea></td>";
    html += "</tr></table>";

    ppmDialog2(null, "检出", html, buttons, null, 320);
}

/**
 * check in file
 */
function checkin() {
	$("#checkinDialog").dialog("open");
}