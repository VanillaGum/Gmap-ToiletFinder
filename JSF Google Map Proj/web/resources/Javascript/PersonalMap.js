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
function createFolderP1() {
    var screen = document.getElementById("groupScreen");
    screen.className = "creation";
    while (screen.firstChild) {
        screen.removeChild(screen.firstChild);
    }
    var title = document.createElement('p');
    title.className = "folderListTitle";
    title.innerHTML = "Choose Infowindow";
    var divider = document.createElement('div');
    divider.className = "folderListDivider";
    document.getElementById("groupScreen").append(title);
    document.getElementById("groupScreen").append(divider);
}
function addDivider(titleName, catagory) {
    var title = document.createElement('p');
    title.className = "folderListTitle";
    title.innerHTML = ""+titleName;
    var divider = document.createElement('div');
    divider.className = "folderListDivider";
    var container = document.createElement('div');
    container.className = "folderListContainer";
    container.id = ""+catagory;
    document.getElementById("groupScreen").append(title);
    document.getElementById("groupScreen").append(divider);
    document.getElementById("groupScreen").append(container);
}
function addNewGroupFolder() {
    var group = document.createElement('div');
    group.className = "groupMarker";
    group.style = "background:#96969d;";
    var img = document.createElement('img');
    img.src = "images/folder.png";
    img.alt = "Folder Icon";
    var paragraph = document.createElement('p');
    paragraph.innerHTML= "Create New Folder";
    paragraph.style = "color:white;"
    var breakLine = document.createElement('br');
    group.onclick= function() {createFolderP1()};
    group.append(img);
    group.append(paragraph);
    group.append(breakLine);
    document.getElementById("userFolder").append(group);
}
function addFolder(foldId, foldUser, foldName , type) {
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
    switch(type) {
        case 0:
            document.getElementById("sponsorFolder").append(group);
            break;
    }
}