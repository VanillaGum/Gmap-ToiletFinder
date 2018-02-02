function personalMapDisplay() {
    var check = document.getElementById("viewPersonalMap");
    if (check.className == "left-controls-unselected") {
        check.className = "left-controls-selected";
        displayFolders();
    }else {
        check.className = "left-controls-unselected";

    }
}
function displayFolders() {
    var greyOverlayScreen = document.getElementById("greyOverlay");
    greyOverlayScreen.className = "selected-Overlay";
    var displayFolderScreen = document.getElementById("groupScreen");
    displayFolderScreen.className = "group-selected";

}
function hideFolders() {
    var folderScreen = document.getElementById("groupScreen");
    while (folderScreen.firstChild) {
        folderScreen.removeChild(folderScreen.firstChild);
    }
    folderScreen.className = "group-unselected";
    document.getElementById("greyOverlay").className = "unselected-overlay";
}

function custWindow1Display(title,desc,uniqueId) {
    var window= '<div style="width:200px;height:200px;">' +
        '<p style="font-size:20px;">'+title+'</p>' +
        '<textarea>' +
        ''+desc+'' +
        '</textarea>' +
        '</div>';
}
function custWindow1() {

}