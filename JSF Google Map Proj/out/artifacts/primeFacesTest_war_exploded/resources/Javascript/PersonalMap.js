personalMarkerStatus = 0;
personalCreateMarker = null;

function createPersonalMarker(event) {
    if(personalMarkerStatus == 2) {
        if (personalCreateMarker == null) {
            personalCreateMarkerLat = event.latLng.lat();
            personalCreateMarkerLng = event.latLng.lng();
            personalCreateMarker = new google.maps.Marker({
                position: {lat: personalCreateMarkerLat, lng: personalCreateMarkerLng},
                map: mapdis
            });
            var windowType = 1;
            var infoWindowContent = null;
            switch (windowType) {
                case 1:
                    infoWindowContent =
                        '                        <div id="windowEdit" class="testWindows2 InfoWindow1">\n' +
                        '                            <div id="testIWTitle1" class="testIWTitle " contenteditable="true">Title</div>\n' +
                        '                            <div class="testTA1 testDesc" contenteditable="true">Description</div>\n' +
                        '                            <div class="testImageExample">\n' +
                        '                                <img width="150px" height="150px" src="images/testImg.jpg" alt="Test Image"></img>\n' +
                        '                            </div>\n' +
                        '            <div class="addToiletSubmit">\n' +
                        '\n' +
                        '               <button class="markerSubmit" onclick="saveInfoWindow()">Confirm</button>\n' +
                        '               <button class="markerCancel" onclick="removePersonalInfoWindow()" style="margin-left:170px;">Cancel</button>\n' +
                        '            </div>' +
                        '                        </div>';
                    break;
                case 2:
            }
            mapdis.setCenter(new google.maps.LatLng(personalCreateMarkerLat,personalCreateMarkerLng));
            var infowindowCreate = new google.maps.InfoWindow({
               content: infoWindowContent,
                maxwidth:440,
                maxheight:260
            });
            infowindowCreate.addListener('click', function() {
                infowindowCreate.open(mapdis, personalCreateMarker);
            });
            infowindowCreate.open(mapdis,personalCreateMarker);
        }
    }

}
function createPersonalMarkerCurrentLoc() {
    navigator.geolocation.getCurrentPosition(function (position) {
        confirmationMarkerLat=position.coords.latitude;
        confirmationMarkerLng= position.coords.longitude;
        confirmationMarker = new google.maps.Marker( {
            position: {lat:confirmationMarkerLat,lng:confirmationMarkerLng},
            map:mapdis
        });
        mapdis.setCenter(new google.maps.LatLng(confirmationMarkerLat,confirmationMarkerLng));
        confirmationMarker.addListener('click', function() {
            confirmationInfowindow.open(mapdis, confirmationMarker);
        });
        confirmationInfowindow.open(mapdis,confirmationMarker);
    });
}

function personalMapDisplay() {
    var check = document.getElementById("viewPersonalMap");
    if (check.className == "left-controls-unselected") {
        var check2 = document.getElementById("viewToilets");
        if (check2.className == "left-controls-selected") {
            displayApproved();
        }

        check.className = "left-controls-selected";
        alert("Display");
        displayFolders();
    }else {
        check.className = "left-controls-unselected";

    }
}
function displayFolders() {
    var displayFolderScreen = document.createElement('div');
    displayFolderScreen.className = "group-selected";
    displayFolderScreen.id = "groupScreen";
    var greyOverlayScreen = document.getElementById("greyOverlay");
    greyOverlayScreen.className = "selected-Overlay";
    greyOverlayScreen.append(displayFolderScreen);
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
function importFolder(id) {
    alert(id);
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
function createFolderP1() {
// <p class="folderListTitle2" style="width:230px;">Set Folder Name:</p>
//     <div id="folderNameEntry" contenteditable="">Folder</div>
//         <div class="testViewReviews testButton" onclick="finishFolderP1()">Submit</div>

    var screen = document.getElementById("groupScreen");
    while (screen.firstChild) {
        screen.removeChild(screen.firstChild);
    }
    var promptTitle = document.createElement('p');
    promptTitle.style = "width:230px;";
    promptTitle.className = "folderListTitle2";
    promptTitle.innerHTML = "Set Folder Name:";
    var enterFolderName = document.createElement('div');
    enterFolderName.id = "folderNameEntry";
    enterFolderName.contentEditable = "true";
    var submitBut = document.createElement('div');
    submitBut.className = "testViewReviews testButton";
    submitBut.onclick = function() {finishFolderP1();};
    submitBut.innerHTML = "Submit"
    screen.append(promptTitle);
    screen.append(enterFolderName);
    screen.append(submitBut);
}
function finishFolderP1(){
    var getFolderName = document.getElementById("folderNameEntry").innerHTML;
    if(confirm("Set Folder Name To " + getFolderName + "?")) {

        if (getFolderName == "" || getFolderName.length > 20) {
            if (getFolderName == "") {
                alert("Can't have a empty folder name");
            }else {
                alert("Maximum Length Is 20(Including Spaces)");
            }
        } else {
            document.getElementById("formSubmitToilet:folderName").value = getFolderName;
            createFolderP2();

        }
    }
}
function createFolderP2() {
    var screen = document.getElementById("groupScreen");
    while (screen.firstChild) {
        screen.removeChild(screen.firstChild);
    }
    // var title = document.createElement('p');
    // title.className = "folderListTitle";
    // title.innerHTML = "Choose Infowindow And Tryout";
    // var divider = document.createElement('div');
    // divider.className = "folderListDivider";
    // screen.append(title);
    // screen.append(divider);
    //
    //
    // //Infowindow 1
    // var header1 = document.createElement('div');
    // header1.className = "testHeader";
    // header1.innerHTML = "Infowindow 1";
    // var select1 = document.createElement('div');
    // select1.className = "infowindowChoose";
    // select1.innerHTML = "Select";
    // header1.append(select1);
    // screen.append(header1);
    //
    // var container1 = document.createElement('div');
    // container1.className = "testInfoWindowContainer";
    //
    // var testWindow1 = document.createElement('div');
    // testWindow1.className = "testWindows";
    // testWindow1.id = "testInfoWindow1";
    // var testTitle1 = document.createElement('div');
    // testTitle1.className = "testIWTitle";
    // testTitle1.id = "testIWTitle1";
    // testTitle1.contentEditable = "true";
    // testTitle1.innerHTML = "Title";
    // var testDesc1 = document.createElement('div');
    // testDesc1.className = "testTA1 testDesc";
    // testDesc1.contentEditable = "true";
    // testDesc1.innerHTML = "Description";
    // var testImgDiv1 = document.createElement('div');
    // testImgDiv1.className = "testImageExample";
    // var testImg1 = document.createElement('img');
    // testImg1.width = 150;
    // testImg1.height = 150;
    // testImg1.alt = "Sample Image";
    // testImg1.src = "images/testImg.jpg";
    // testImgDiv1.append(testImg1);
    // var closeBut1 = document.createElement('div');
    // closeBut1.className = "testCloseButton";
    // closeBut1.innerHTML = "✖";
    //
    // testWindow1.append(testTitle1);
    // testWindow1.append(testDesc1);
    // testWindow1.append(testImgDiv1);
    // testWindow1.append(closeBut1);
    // container1.append(testWindow1);
    //
    // screen.append(container1);
    //
    //
    // //InfoWindow2
    // var divider1 = document.createElement('div');
    // divider1.className = "folderListDivider";
    // screen.append(divider1);
    // //     <!--&lt;!&ndash;Window 2&ndash;&gt;-->
    // var header2 = document.createElement('div');
    // header2.className = "testHeader";
    // header2.innerHTML = "Infowindow 2";
    // var select2 = document.createElement('div');
    // select2.className = "infowindowChoose";
    // select2.innerHTML = "Select";
    // header2.append(select2);
    // screen.append(header2);
    // var container2 = document.createElement('div');
    // container2.className = "testInfoWindowContainer";
    //
    // var testWindow2 = document.createElement('div');
    // testWindow2.className = "testWindows";
    // testWindow2.id = "testInfoWindow2";
    // var testTitle2 = document.createElement('div');
    // testTitle2.className = "testIWTitle";
    // testTitle2.id = "testIWTitle2";
    // testTitle2.contentEditable = "true";
    // testTitle2.innerHTML = "Title";
    // var testDesc2 = document.createElement('div');
    // testDesc2.className = "testTA2 testDesc";
    // testDesc2.contentEditable = "true";
    // testDesc2.innerHTML = "Description";
    // var testImgDiv2 = document.createElement('div');
    // testImgDiv2.className = "testImageExample";
    // var testImg2 = document.createElement('img');
    // testImg2.width = 150;
    // testImg2.height = 150;
    // testImg2.alt = "Sample Image";
    // testImg2.src = "images/testImg.jpg";
    // testImgDiv2.append(testImg2);
    // var testRating1 = document.createElement('div');
    // testRating1.className = "testRating";
    // var testRatingImg1 = document.createElement('img');
    // testRatingImg1.width = 160;
    // testRatingImg1.height = 34;
    // testRatingImg1.src = "images/5-stars-rating.png";
    // testRatingImg1.title = "Based On 10 Rating";
    // testRatingImg1.alt = "Ratings"
    // testRating1.append(testRatingImg1);
    // var closeBut2 = document.createElement('div');
    // closeBut2.className = "testCloseButton";
    // closeBut2.innerHTML = "✖";
    // var testAddReview = document.createElement('div');
    // testAddReview.className = "testAddReview testButton";
    // testAddReview.innerHTML = "Add Review";
    // var testViewReviews = document.createElement('div');
    // testViewReviews.innerHTML = "View Reviews";
    var str = '            <p class="folderListTitle">Choose Infowindow And Tryout</p>\n' +
        '                <div class="folderListDivider"></div>\n' +
        '                    <div class="testHeader">\n' +
        '                        Infowindow 1\n' +
        '                        <div class="infowindowChoose" onclick="finishFolderP2(1)">Select</div>\n' +
        '                    </div>\n' +
        '                    <div class="testInfoWindowContainer">\n' +
        '                        <div id="testInfoWindow1" class="testWindows">\n' +
        '                            <div id="testIWTitle1" class="testIWTitle" contenteditable="true">Title</div>\n' +
        '                            <div class="testTA1 testDesc" contenteditable="true">Description</div>\n' +
        '                            <div class="testImageExample">\n' +
        '                                <img width="150px" height="150px" src="images/testImg.jpg" alt="Test Image"></img>\n' +
        '                            </div>\n' +
        '                            <div class="testCloseButton">✖</div>\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '\n' +
        '\n' +
        '                <div class="folderListDivider"></div>\n' +
        '                    <!--Window 2-->\n' +
        '                    <div class="testHeader">\n' +
        '                        Infowindow 2\n' +
        '                        <div class="infowindowChoose" onclick="finishFolderP2(2)">Select</div>\n' +
        '                    </div>\n' +
        '                    <div class="testInfoWindowContainer">\n' +
        '                        <div id="testInfoWindow2" class="testWindows">\n' +
        '                            <div id="testIWTitle2" class="testIWTitle" contenteditable="true">Title</div>\n' +
        '                            <div class="testTA2 testDesc" contenteditable="true">Description</div>\n' +
        '                            <div class="testImageExample">\n' +
        '                                <img width="150px" height="150px" src="images/testImg.jpg" alt="Test Image"></img>\n' +
        '                            </div>\n' +
        '                            <div class="testRating">\n' +
        '                                <img width="160" height="34" src="images/5-stars-rating.png" title="Based On 10 Rating" alt="rating"></img>\n' +
        '                            </div>\n' +
        '                            <div class="testCloseButton">✖</div>\n' +
        '                            <div class="testAddReview testButton" >Add Review</div>\n' +
        '                            <div class="testViewReviews testButton">View Reviews</div>\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '\n' +
        '                    <div class="folderListDivider"></div>\n' +
        '                    <!--Window 3-->\n' +
        '                    <div class="testHeader">\n' +
        '                        Infowindow 3\n' +
        '                        <div class="infowindowChoose" onclick="finishFolderP2(3)">Select</div>\n' +
        '                    </div>\n' +
        '                    <div class="testInfoWindowContainer">\n' +
        '                        <div id="testInfoWindow3" class="testWindows">\n' +
        '                            <div id="testIWTitle3" class="testIWTitle" contenteditable="true">Title</div>\n' +
        '                            <div class="testTA3 testDesc" contenteditable="true">Description</div>\n' +
        '                            <div class="testReviewSectionTitle">Reviews</div>\n' +
        '                            <div class="testReviewSection">\n' +
        '                                <div>\n' +
        '                                    <img width="190" height="34" src="images/5-stars-rating.png" alt="rating"></img>\n' +
        '                                    <div style="color:white;font-size:19px;">Absolutely Delicious.</div>\n' +
        '                                    <div style="font-size:15px;color:white;">By Mdm.Haesh</div>\n' +
        '                                    <hr/>\n' +
        '                                </div>\n' +
        '                                <div>\n' +
        '                                    <img width="190" height="34" src="images/5-stars-rating.png" alt="rating"></img>\n' +
        '                                    <div style="color:white;font-size:19px;">Absolutely Delicious. Im definitely gonna come again</div>\n' +
        '                                    <div style="font-size:15px;color:white;">By Mdm.Haesh</div>\n' +
        '                                    <hr/>\n' +
        '                                </div>\n' +
        '                                <div>\n' +
        '                                    <img width="190" height="34" src="images/5-stars-rating.png" alt="rating"></img>\n' +
        '                                    <div style="color:white;font-size:19px;">Absolutely Delicious. Im definitely gonna come again</div>\n' +
        '                                    <div style="font-size:15px;color:white;">By Mdm.Haesh</div>\n' +
        '                                    <hr/>\n' +
        '                                </div>\n' +
        '                                <div>\n' +
        '                                    <img width="190" height="34" src="images/5-stars-rating.png" alt="rating"></img>\n' +
        '                                    <div style="color:white;font-size:19px;">Absolutely Delicious. Im definitely gonna come again</div>\n' +
        '                                    <div style="font-size:15px;color:white;">By Mdm.Haesh</div>\n' +
        '                                    <hr/>\n' +
        '                                </div>\n' +
        '                            </div>\n' +
        '                            <div class="testRating">\n' +
        '                                <img width="197" height="34" src="images/5-stars-rating.png" title="Based On 10 Rating" alt="rating"></img>\n' +
        '                            </div>\n' +
        '                            <div class="testCloseButton">✖</div>\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '\n' +
        '                    <div class="folderListDivider"></div>\n' +
        '                    <!--Window 4-->\n' +
        '                    <div class="testHeader">\n' +
        '                        Infowindow 4\n' +
        '                        <div class="infowindowChoose" onclick="finishFolderP2(4)">Select</div>\n' +
        '                    </div>\n' +
        '                    <div class="testInfoWindowContainer">\n' +
        '                        <div id="testInfoWindow4" class="testWindows">\n' +
        '                            <div id="testIWTitle4" class="testIWTitle" contenteditable="true">Title</div>\n' +
        '                            <div class="testCloseButton">✖</div>\n' +
        '                        </div>\n' +
        '                    </div>';
    screen.innerHTML = str;
}

function finishFolderP2(typeNo){
    if(confirm("Use This Infowindow " + typeNo + "?")) {
            document.getElementById("formSubmitToilet:folderType").value = typeNo;
            var screen = document.getElementById("groupScreen");
            while (screen.firstChild) {
                screen.removeChild(screen.firstChild);
            }
            document.getElementById("greyOverlay").removeChild(screen);
            document.getElementById("greyOverlay").className = "unselected-Overlay";
            createFolderBean();
            // document.getElementById("addPersonalMarkerButton").style.display = "block";
    }
}

function returnPersonalWindows() {

}
function saveInfoWindow() {
    var windowType =  document.getElementById("formSubmitToilet:folderType").value;
    alert(windowType)
}
function removePersonalInfoWindow() {
    personalCreateMarker.setMap(null);
    personalCreateMarker = null;
}