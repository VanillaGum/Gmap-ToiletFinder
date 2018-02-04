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
            var windowType =  document.getElementById("formSubmitToilet:folderType").value;
            var infoWindowContent = null;
            mapdis.setCenter(new google.maps.LatLng(personalCreateMarkerLat,personalCreateMarkerLng));
            var infowindowCreate = null
            if (windowType == "1") {
                infoWindowContent =
                    '                        <div id="windowEdit" class="testWindows2 infoWindow1">\n' +
                    '                            <div id="testIWTitle1" class="testIWTitle " contenteditable="true">Title</div>\n' +
                    '                            <div id="editDesc1" class="testTA1 testDesc" contenteditable="true">Description</div>\n' +
                    '                            <div class="testImageExample">\n' +
                    '                                <img width="150px" height="150px" src="images/testImg.jpg" alt="Test Image"></img>\n' +
                    '                            </div>' +
                    '            <div class="addToiletSubmit">\n' +
                    '\n' +
                    '               <button class="markerSubmit" onclick="saveInfoWindow()">Confirm</button>\n' +
                    '               <button class="markerCancel" onclick="removePersonalInfoWindow()" style="margin-left:170px;">Cancel</button>\n' +
                    '            </div>' +
                    '                        </div>';
                infowindowCreate = new google.maps.InfoWindow({
                    content: infoWindowContent,
                    maxwidth: 440,
                    maxheight: 260
                });
            }else if(windowType == "2") {
                '</div>';
                    infoWindowContent =
                        '<div class="testWindows2 infoWindow2">' +
                        '                            <div id="testIWTitle2" class="testIWTitle IWTitle2" contenteditable="true">Title</div>' +
                        '                            <div id="editDesc2" class="testTA2 testDesc" contenteditable="true">Description</div>' +
                        '                            <div class="testImageExample">' +
                        '                                <img width="150px" height="150px" src="images/testImg.jpg" alt="Test Image"></img>' +
                        '                            </div>' +
                        '                            <div class="testRating">' +
                        '                                <img width="160" height="34" src="images/0-star-rating.png" title="Based On 0 Rating" alt="rating"></img>' +
                        '                            </div>' +
                        '            <div class="addToiletSubmit">\n' +
                        '               <button class="markerSubmit" onclick="saveInfoWindow()">Confirm</button>\n' +
                        '               <button class="markerCancel" onclick="removePersonalInfoWindow()" style="margin-left:170px;">Cancel</button>\n' +
                        '            </div>' +
                        '                        </div>' +
                        '</div>';
                    infowindowCreate = new google.maps.InfoWindow({
                        content: infoWindowContent
                    });
            }else if (windowType == "3") {
                var infoWindowContent = '                        <div class="testWindows2 infoWindow3">\n' +
                    '                            <div id="testIWTitle3" class="testIWTitle IWTitle3" contenteditable="true">Title</div>\n' +
                    '                            <div id="editDesc3" class="testTA3 testDesc" contenteditable="true">Description</div>\n' +
                    '                            <div class="testReviewSectionTitle">Reviews</div>\n' +
                    '                            <div class="testReviewSection">' +
                    '                            </div>\n' +
                    '                            <div class="testRating">\n' +
                    '                                <img width="197" height="34" src="images/0-star-rating.png" title="Based On 0 Rating" alt="rating"></img>\n' +
                    '                            </div>\n' +
                    '               <button class="markerSubmit" onclick="saveInfoWindow()" style="margin-top:5px;"">Confirm</button>\n' +
                    '               <button class="markerCancel" onclick="removePersonalInfoWindow()" style="margin-left:20px;margin-top:5px;">Cancel</button>\n' +
                    '                        </div>';
                infowindowCreate = new google.maps.InfoWindow({
                    content: infoWindowContent
                });
            }else {
                infoWindowContent ='                            <div id="testIWTitle4" class="testIWTitle" contenteditable="true">Title</div>' +
                    '                                    <div class="addToiletSubmit">' +
                    '                                       <button class="markerSubmit" onclick="saveInfoWindow()">Confirm</button>' +
                    '                                       <button class="markerCancel" onclick="removePersonalInfoWindow()" style="margin-left:170px;">Cancel</button>' +
                    '                                    </div>';
                infowindowCreate = new google.maps.InfoWindow({
                    content: infoWindowContent,
                    maxwidth:450,
                    maxheight:2
                });
            }
            personalCreateMarker.addListener('click', function() {
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
        displayFolders();
    }else {
        check.className = "left-controls-unselected";
        removePMapMarkers();
        document.getElementById("changeFolder").style.display = "none";
        document.getElementById("addPersonalMarkerButton").style.display = "none";
    }
}
function displayFolders() {
    document.getElementById("changeFolder").style.display = "none";
    document.getElementById("addPersonalMarkerButton").style.display = "none";
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
    document.getElementById("formSubmitToilet:folderId").value = id;
    importOthersFolder();
}
function importFailed() {
    alert("Folder already part of user's folders or imports");
}
function deleteFolder(id, folderHeader, folderName) {
    if (confirm("Delete folder " + folderName + "?")) {
        var doc = document.getElementById("folderDiv"+id);
        document.getElementById(folderHeader).removeChild(doc);
        document.getElementById("formSubmitToilet:folderId").value = id;
        deletePersonalFolder();
    }
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
function addFolder(foldId, foldUser, foldName , folderHeader) {
    var group = document.createElement('div');
    group.className = "groupMarker";
    group.title = "By "+foldUser;
    group.onclick = (function() {openFolder(foldId)});
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
    document.getElementById(folderHeader).append(group);
}
function addUserFolder(foldId, foldUser, foldName , folderHeader) {
    var group = document.createElement('div');
    group.className = "groupMarker";
    group.title = "By "+foldUser;
    group.id = "folderDiv"+foldId;
    group.onclick = (function() {openFolder(foldId)});
    var img = document.createElement('img');
    img.src = "images/folder.png";
    img.alt = "Folder Icon";
    var paragraph = document.createElement('p');
    paragraph.innerHTML= foldName;
    var breakLine = document.createElement('br');
    var deleteBut = document.createElement('div');
    deleteBut.innerHTML = "Delete";
    deleteBut.className = "importButton";
    $(deleteBut).click(function(e) {
        //do something
        e.stopPropagation();
        deleteFolder(foldId,folderHeader,foldName);
    })
    group.append(img);
    group.append(paragraph);
    group.append(breakLine);
    group.append(deleteBut);
    document.getElementById(folderHeader).append(group);
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
        '                            <div class="testAddReview testButton" >Add Review</div>\n' +
        '                        </div>\n' +
        '                    </div>' +
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


function saveInfoWindow() {
    document.getElementById("CancelAddMarker").className = "CancelAdd";
    var windowType =  document.getElementById("formSubmitToilet:folderType").value;
    document.getElementById("formSubmitToilet:pLat").value = personalCreateMarker.getPosition().lat();
    document.getElementById("formSubmitToilet:pLng").value = personalCreateMarker.getPosition().lng();
    if (windowType ==  "1") {
        document.getElementById("formSubmitToilet:field1").value = document.getElementById("testIWTitle1").innerHTML;
        document.getElementById("formSubmitToilet:field2").value = document.getElementById("editDesc1").innerHTML;

    }else if (windowType == "2") {
        document.getElementById("formSubmitToilet:field1").value = document.getElementById("testIWTitle2").innerHTML;
        document.getElementById("formSubmitToilet:field2").value = document.getElementById("editDesc2").innerHTML;
    }else if (windowType == "3") {
        document.getElementById("formSubmitToilet:field1").value = document.getElementById("testIWTitle3").innerHTML;
        document.getElementById("formSubmitToilet:field2").value = document.getElementById("editDesc3").innerHTML;
    }else if (windowType == "4") {
        document.getElementById("formSubmitToilet:field1").value = document.getElementById("testIWTitle4").innerHTML;
    }
    personalMarkerStatus = 0;
    personalCreateMarker.setMap(null);
    personalCreateMarker = null;
    createPersonalMarkerz();
}
function removePersonalInfoWindow() {
    personalCreateMarker.setMap(null);
    personalCreateMarker = null;
    personalMarkerStatus = 0;
    document.getElementById("CancelAddMarker").className = "CancelAdd";
}

function returnPersonalWindow (type, uniqueNo,field1, field2, owner,rating,amtRatings) {
    var imageSrc = "";
    switch(rating) {
        case 5:
            imageSrc="images/5-stars-rating.png";
            break;
        case 4:
            imageSrc="images/4-stars-rating.png";
            break;
        case 3:
            imageSrc="images/3-stars-rating.png";
            break;
        case 2:
            imageSrc="images/2-stars-rating.png";
            break;
        case 1:
            imageSrc="images/1-star-rating.png";
            break;
        case 0:
            imageSrc="images/0-star-rating.png";
            break;
        default:
            imageSrc="images/0-star-rating.png";
            break;
    }
    var contentRtn1;
    if(type == 1) {
        contentRtn1 = '                        <div class="testWindows2 infoWindow1">' +
            '                                              <div id="1Title'+uniqueNo+'" class="testIWTitle IWTitle1">'+field1+'</div>' +
            '                                               <div id="1Desc'+uniqueNo+'" class="testTA1 testDesc">'+field2+'</div>' +
            '                                               <div class="testImageExample">' +
            '                                                   <img width="150px" height="150px" src="images/testImg.jpg" alt="Test Image"></img>' +
            '                                               </div>';
    }else if(type == 2) {
        contentRtn1 = 		'<div class="testWindows2 infoWindow2">' +
            '                            <div id="2Title'+uniqueNo+'" class="testIWTitle IWTitle2">'+field1+'</div>' +
            '                            <div id="2Desc'+uniqueNo+'" class="testTA2 testDesc">'+field2+'</div>' +
            '                            <div class="testImageExample">' +
            '                                <img width="150px" height="150px" src="images/testImg.jpg" alt="Test Image"></img>' +
            '                            </div>' +
            '                            <div class="testRating">' +
            '                                <img width="160" height="34" src="'+imageSrc+'" title="Based On '+amtRatings+' Rating" alt="rating"></img>' +
            '                            </div>';
    }else if(type == 3) {
        contentRtn1 = '                        <div class="testWindows2 infoWindow3">' +
            '                            <div id="3Title'+uniqueNo+'" class="testIWTitle IWTitle3" >Title</div>' +
            '                            <div id="3Desc'+uniqueNo+'" class="testTA3 testDesc" >Description</div>' +
            '                            <div class="testReviewSectionTitle">Reviews</div>' +
            '                            <div class="testReviewSection">' +
            '                            </div>' +
            '                            <div class="testRating">' +
            '                                <img width="197" height="34" src="'+imageSrc+'" title="Based On '+amtRatings+' Rating" alt="rating"></img>' +
            '                            </div>';
    }

    else if(type == 4) {
        contentRtn1 = '                            <div id="4Title'+uniqueNo+'" class="testIWTitle" >Title</div>';
    }
    var contentRtn2 ="";
    if (owner == 1) {
        if (type == 1) {
            contentRtn2 = '                                               <img id="edit' + uniqueNo + '" class="edit-unselected personalEditButton" src="images/edit-icon.png" onclick="editPInfoWindow(1,' + uniqueNo + ')"></img>' +
                '                                               <img id="delete' + uniqueNo + '" class="delete-unselected personalDeleteButton" src="images/delete-icon.png" onclick="deletePInfoWindow(' + uniqueNo + ')"></img>' +
                '                                           </div>';
        }else if(type == 2) {
            contentRtn2 = '                                               <img id="edit' + uniqueNo +'" class="edit-unselected personalEditButton" src="images/edit-icon.png" onclick="editPInfoWindow(2,' + uniqueNo +')"></img>' +
                '                                               <img id="delete'+uniqueNo +'" class="delete-unselected personalDeleteButton" src="images/delete-icon.png" onclick="deletePInfoWindow(' + uniqueNo +')"></img>' +
                '                            <div class="testViewReviews testButton">View Reviews</div>' +
                '                        </div>';
        }else if(type == 3) {
            contentRtn2 = '                                               <img id="edit' + uniqueNo + '" class="edit-unselected personalEditButton" src="images/edit-icon.png" onclick="editPInfoWindow(3,' + uniqueNo + ')"></img>' +
                '                                               <img id="delete' + uniqueNo + '" class="delete-unselected personalDeleteButton3" src="images/delete-icon.png" onclick="deletePInfoWindow(' + uniqueNo + ')"></img>' +
                '                                           </div>';
        }else if(type == 4) {
            contentRtn2 = '                                    <div style="height:40px;">' +
                '                                               <img id="edit' + uniqueNo + '" class="edit-unselected personalEditButton4" src="images/edit-icon.png" onclick="editPInfoWindow(4,' + uniqueNo + ')"></img>' +
                '                                               <img id="delete' + uniqueNo + '" class="delete-unselected personalDeleteButton4" src="images/delete-icon.png" onclick="deletePInfoWindow(' + uniqueNo + ')"></img>' +
                '                                           </div>';
        }
    }else {
        if (type == 1) {
            contentRtn2 = '</div>';
        }else if(type == 2) {
            contentRtn2 = '                            <div class="testAddReview testButton" onclick="OpenReviewScreen('+uniqueNo+')">Add Review</div>' +
                '                            <div class="testViewReviews testButton">View Reviews</div>' +
                '                        </div>';
        }else if(type == 3) {
            contentRtn2 = '                            <div class="testAddReview testButton" onclick="OpenReviewScreen('+uniqueNo+')">Add Review</div>\n' +
                '                        </div>';
        }else if(type == 4) {
            contentRtn2 = '</div>';
        }
    }
    var contentRtn = contentRtn1.concat(contentRtn2);
    return contentRtn;
}
function editPInfoWindow(type, uniqueNo) {
    var checkEdit = document.getElementById("edit"+uniqueNo);
    if (checkEdit.classList.contains("edit-unselected")) {
        //Unselected
        checkEdit.classList.remove("edit-unselected");
        checkEdit.classList.add("edit-selected");
        var docTitle = document.getElementById(type+"Title"+uniqueNo);
        var docDesc = document.getElementById(type+"Desc"+uniqueNo);
        docTitle.contentEditable  = "true";
        docDesc.contentEditable  = "true";
    }else {
        //Selected
        checkEdit.classList.remove("edit-selected");
        checkEdit.classList.add("edit-unselected");
        var docTitle = document.getElementById(type+"Title"+uniqueNo);
        var docDesc = document.getElementById(type+"Desc"+uniqueNo);
        docTitle.contentEditable  = "false";
        docDesc.contentEditable  = "false";
        document.getElementById("formSubmitToilet:uniqueNoJs").value = uniqueNo;
        document.getElementById("formSubmitToilet:field1").value = docTitle.innerHTML;
        document.getElementById("formSubmitToilet:field2").value = docDesc.innerHTML;
        editMarkerInfo();

    }
}
function deletePInfoWindow(uniqueNo) {
    if (confirm("Delete This Marker and Info?")) {
        document.getElementById("formSubmitToilet:uniqueNoJs").value = uniqueNo;
        deletePersonalMarker();
    }
}
function openFolder(folderId) {
    var screen = document.getElementById("groupScreen");
    while (screen.firstChild) {
        screen.removeChild(screen.firstChild);
    }
    var overlay = document.getElementById("greyOverlay");
    overlay.removeChild(screen);
    overlay.className = "unselected-Overlay";
    document.getElementById("formSubmitToilet:folderId").value = folderId;
    displayFolderMarkerz();
}
function changeRating(starNo) {
    var filled = "images/star-filled.png";
    var unfilled = "images/star-unfilled.png";
    for(i=1; i <= starNo; i++) {
        document.getElementById("star"+i).src = filled
    }
    for(i=(starNo+1);i <= 5;i++) {
        document.getElementById("star"+i).src = unfilled
    }


}
function submitIWReview(uniqueNo) {
    var Reviewrating = 0;
    var starCurr = 5;
    while (Reviewrating == 0 && starCurr > 0) {
        if(document.getElementById("star"+starCurr).src = "images/star-filled.png") {
            Reviewrating = starCurr;
        }else {
            starCurr--;
        }
    }
    document.getElementById("formSubmitToilet:uniqueNoJs").value = uniqueNo;
    document.getElementById("formSubmitToilet:reviewRating").value = Reviewrating;
    document.getElementById("formSubmitToilet:reviewComment").value = document.getElementById("PersonalReviewComments").value;
    submitPReview();
    closeIWReview()

}
function closeIWReview(){
    var greyOverlay = document.getElementById("greyOverlay");
    greyOverlay.innerHTML = "";
    greyOverlay.className = "unselected-Overlay"
}
function OpenReviewScreen(uniqueNo) {
    var greyOverlay = document.getElementById("greyOverlay");
    greyOverlay.className = "selected-Overlay";
    var contentScreen = '<div id="iwReviewScreen">\n' +
        '            <div id="ReviewScreenRating">\n' +
        '                <img id="star1" src="images/star-unfilled.png" onclick="changeRating(1)"></img>\n' +
        '                <img id="star2" src="images/star-unfilled.png" onclick="changeRating(2)"></img>\n' +
        '                <img id="star3" src="images/star-unfilled.png" onclick="changeRating(3)"></img>\n' +
        '                <img id="star4" src="images/star-unfilled.png" onclick="changeRating(4)"></img>\n' +
        '                <img id="star5" src="images/star-unfilled.png" onclick="changeRating(5)"></img>\n' +
        '            </div>\n' +
        '            <div id="CommentHeader">Comment</div>\n' +
        '            <textarea id="PersonalReviewComments" cols="52" rows="12"></textarea>\n' +
        '            <div style="width:200px;height:30px;margin-left:33%;" class="testButton submitReviewIW" onclick="submitIWReview('+uniqueNo+')">Submit</div>\n' +
        '<div class="testCloseButton" onclick="closeIWReview()">✖</div>' +
        '        </div>'
    greyOverlay.innerHTML = contentScreen;
}
function submitSearch() {
    document.getElementById("formSubmitToilet:searchFolder").value = document.getElementById("SearchBox").innerHTML;
    searchForFolder();
}
function closeSearchFolder() {
    document.getElementById("viewToilets").className = "left-controls-selected";
    document.getElementById("greyOverlay").innerHTML = "";
    document.getElementById("greyOverlay").className = "unselected-Overlay";
}
function openFolderSearch() {
    var searchContent = '';
}