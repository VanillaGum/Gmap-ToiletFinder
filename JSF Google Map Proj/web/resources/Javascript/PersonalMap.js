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
    displayFoldersControl();

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
function passFolder(foldI, foldU, foldN) {
    addFolder(foldI, foldU, foldN);
}
function importFolder(id) {
    alert(id);
}

function addFolder(foldUser, foldName, foldId) {
    var group = document.createElement('div');
    group.className = "groupMarker";
    group.title = "By "+foldUser;
    var img = document.createElement('img');
    img.src = "images/folder.png";
    img.alt = "Folder Icon";
    var paragraph = document.createElement('p');
    paragraph.innerHTML= foldName;
    var breakLine = document.createElement('br');
    var importBut = document.createElement('div');
    importBut.innerHTML = "Import";
    importBut.className = "importButton";
    importBut.onclick= function() {importFolder(foldId)};
    group.append(img);
    group.append(paragraph);
    group.append(breakLine);
    group.append(importBut);
    document.getElementById("sponsors").append(group);
}