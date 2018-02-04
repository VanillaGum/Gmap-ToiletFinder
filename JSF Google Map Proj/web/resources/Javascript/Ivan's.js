userLevel = 0 //Permission Level Of User
//0 = Not Logged In
//1 = Logged in User
//2 = Trusted Logged In User/Admin
mapdis = null; //Reference to map in here
addMarker = 0; //Marker Type To Add
confirmationMarker = null; //Put Toilet Confirmation Marker Here
confirmationMarkerLat = 0; //Latitude of Confirmation marker
confirmationMarkerLng = 0; //Longitude of Confirmation marker
confirmationInfowindow = null; //Infowindow for confirmation marker
imageList = ["images/toilet_male.png","images/toilet_female.png","images/help-hovered.png","images/help-unselected.png"]; //To Instantiate The Images(Had image loading issues)
confirmationInfowindow = new google.maps.InfoWindow({
    content: document.getElementById("confirmBox0")
});
confirmationInfowindow1 = new google.maps.InfoWindow({
    content: document.getElementById("confirmBox1")
});
confirmationInfowindow2 = new google.maps.InfoWindow({
    content: document.getElementById("confirmBox2")
});
Usermarker = null;
$(function() {
    resetDisplayed();
    mapdis = PF('mapDisplay').getMap();
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            //Set Map View User Current Loc
            var poslat = position.coords.latitude;
            var poslng = position.coords.longitude;
            mapdis.setCenter(new google.maps.LatLng(poslat, poslng));
            //Display Marker At User Position
             Usermarker = new google.maps.Marker({
                position:{lat:poslat,lng:poslng},
                map:mapdis,
                title:"User Loc",
                icon:"images/toilet_male.png",
                 zIndex: -99999999
            });
            // var infoWindowContentz = '                            <div id="testIWTitle4" class="testIWTitle" contenteditable="true">Title</div>' +
            //     '                                    <div style="height:40px;">' +
            //     '                                               <img  class="edit-unselected personalEditButton4" src="images/edit-icon.png" "></img>' +
            //     '                                               <img  class="delete-unselected personalDeleteButton4" src="images/delete-icon.png""></img>' +
            //     '                                           </div>';
            // var infowindowCreatez = new google.maps.InfoWindow({
            //     content: infoWindowContentz
            // });
            // Usermarker.addListener('click', function() {
            //     infowindowCreatez.open(mapdis, Usermarker);
            // });
            // infowindowCreatez.open(mapdis,Usermarker);
        });
    }
    drawMapUi();
});
function setMarkerAddType(num) {
    var checkToilet = document.getElementById("viewToilets");
    if (checkToilet.className == "left-controls-selected") {
        document.getElementById("CancelAddToilet").className = "CancelAdd show";
        if (num == 1) {
            if (navigator.geolocation) {
                addCurrentLocMarker();
            } else {
                alert("No Geolocaton Detected, Switching To Selection Add");
                addMarker = 2;
            }
        } else {
            addMarker = 2;
        }
    }else {
        if (num == 1) {
            createPersonalMarkerCurrentLoc
        }else {
            document.getElementById("CancelAddMarker").className = "CancelAdd show";
            personalMarkerStatus = num;
        }
    }
}
function addCurrentLocMarker() {
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

function addLocMarker(event) {
    if(addMarker == 2) {
        if (confirmationMarker == null) {
            confirmationMarkerLat=event.latLng.lat();
            confirmationMarkerLng= event.latLng.lng();
            confirmationMarker = new google.maps.Marker( {
                position: {lat:confirmationMarkerLat,lng:confirmationMarkerLng},
                map:mapdis
            });
            mapdis.setCenter(new google.maps.LatLng(confirmationMarkerLat,confirmationMarkerLng));
            confirmationMarker.addListener('click', function() {
                confirmationInfowindow.open(mapdis, confirmationMarker);
            });
            confirmationInfowindow.open(mapdis,confirmationMarker);
            }
    }

}

//Remove Toilet Suggestion Marker When Confirmed
function toiletSuggestionConfirmed() {
    //Submit Info About Toilet
    document.getElementById("formSubmitToilet:locLng").value = confirmationMarkerLng;
    document.getElementById("formSubmitToilet:locLat").value = confirmationMarkerLat;
    var gM = document.getElementById("formSubmitToilet:genderM").value;
    var gF = document.getElementById("formSubmitToilet:genderF").value;
    // switch(userLevel){
    //     case 0:
    //         document.getElementById("formSubmitToilet:toiletGender").value = document.getElementById("toiletGenderSelect0").value;
    //         //Close The Marker And Infowindow
    //         break;
    //     case 1:
    //         document.getElementById("formSubmitToilet:toiletGender").value = document.getElementById("toiletGenderSelect1").value;
    //         //Close The Marker And Infowindow
    //         break;
    //     case 2:
    //         document.getElementById("formSubmitToilet:toiletGender").value = document.getElementById("toiletGenderSelect2").value;
    //         //Close The Marker And Infowindow
    //         break;
    // }
    //Initiate the ManagedBean MapController AddToilet function
    if (gM == 1 || gF == 1) {
        tsubmit();
        resetInfoWindow();
        document.getElementById("CancelAddToilet").className = "CancelAdd";
    }else {
        alert("Please select minimun 1 gender for creating toilet");
    }
}

//Suggesting Location Cancelled
function cancelLocMarker() {
    confirmationInfowindow.close();
    confirmationMarker.setMap(null);
    confirmationMarker = null;
    document.getElementById("CancelAddToilet").className = "CancelAdd";
}


function changeIconSelection(no) {
    switch(no) {
        case 0:
            //Female Toilet Selection
            var femaleToiletCheck= document.getElementsByClassName("femaleToiletSelectIcon-unselected");
            var femaleToiletImage = document.getElementsByClassName("fToilet");
            var femaleToiletUnselected = "femaleToiletSelectIcon-unselected";
            var femaleToiletSelected = "femaleToiletSelectIcon-selected";
            var femaleToiletDefault = "fToilet ";
            if (femaleToiletCheck.length > 0) {
                //Unselected Image change to selected
                femaleToiletImage[0].className = femaleToiletDefault + femaleToiletSelected;
                document.getElementById("formSubmitToilet:genderF").value = 1;
            } else {
                //Selected Image change to unselected
                femaleToiletImage[0].className = femaleToiletDefault + femaleToiletUnselected;
                document.getElementById("formSubmitToilet:genderF").value = 0;
            }
            break;
        case 1:
            //Male Toilet Selection
            var maleToiletCheck= document.getElementsByClassName("maleToiletSelectIcon-unselected");
            var maleToiletImage = document.getElementsByClassName("mToilet");
            var maleToiletUnselected = "maleToiletSelectIcon-unselected";
            var maleToiletSelected = "maleToiletSelectIcon-selected";
            var maleToiletDefault = "mToilet ";
            if (maleToiletCheck.length > 0) {
                //Unselected Image change to selected
                maleToiletImage[0].className = maleToiletDefault + maleToiletSelected;
                document.getElementById("formSubmitToilet:genderM").value = 1;
            } else {
                //Selected Image change to unselected
                maleToiletImage[0].className = maleToiletDefault + maleToiletUnselected;
                document.getElementById("formSubmitToilet:genderM").value = 0;
            }
            break;
    }
}

//Reset InfoWindows
function resetInfoWindow() {
    confirmationInfowindow.open(mapdis,confirmationMarker);
    document.getElementById("femaleIcon0").className = "fToilet femaleToiletSelectIcon-unselected";
    document.getElementById("maleIcon0").className = "mToilet maleToiletSelectIcon-unselected";
    var icon6Reset = document.getElementsByClassName("icon6");
    icon6Reset[0].src = "images/wheelchair-neutral.png";
    icon6Reset[0].className = "icon6 icon6-unselected";
    var icon7Reset = document.getElementsByClassName("icon7");
    icon7Reset[0].className = "icon7 icon7-unselected unselected";
    document.getElementById("formSubmitToilet:genderM").value = 0;
    document.getElementById("formSubmitToilet:genderF").value = 0;
    document.getElementById("formSubmitToilet:icon6").value = 0;
    document.getElementById("formSubmitToilet:icon7").value = 0;
    confirmationInfowindow.close();
    confirmationMarker.setMap(null);
    confirmationMarker = null;
    document.getElementById("CancelAddToilet").className = "CancelAdd";
    addMarker=0;
}
function resetInfoWindowCheck() {
    if (confirmationMarker != null) {
        alert("Not Null")
        resetInfoWindow();
    } else {
        document.getElementById("CancelAddToilet").className = "CancelAdd";
        addMarker=0;
    }
}


//-Start of Map Ui-//
//Draw Map User Interface for non-logged in users
function drawMapUi() {
    var viewToilets = $("#viewToilets");
    var viewPMap = $("#viewPersonalMap");
    var centerControl = $("#centerLoc");
    var zoomoutControl = $("#zoomoutControl");
    var zoominControl = $("#zoominControl");
    mapdis.controls[google.maps.ControlPosition.LEFT_BOTTOM].push(viewToilets[0]);
    mapdis.controls[google.maps.ControlPosition.LEFT_BOTTOM].push(viewPMap[0]);
    mapdis.controls[google.maps.ControlPosition.TOP_CENTER].push(centerControl[0]);
    mapdis.controls[google.maps.ControlPosition.RIGHT_BOTTOM].push(zoomoutControl[0]);
    mapdis.controls[google.maps.ControlPosition.RIGHT_BOTTOM].push(zoominControl[0]);
    var toiletControl = $("#addToiletButton");
    var addSuggested = $("#displaySuggestedToilets");
    var addPersonalMarker = $("#addPersonalMarkerButton");
    var changeFolder = $("#changeFolder");
    mapdis.controls[google.maps.ControlPosition.RIGHT_BOTTOM].push(addPersonalMarker[0]);
    mapdis.controls[google.maps.ControlPosition.RIGHT_BOTTOM].push(addSuggested[0]);
    mapdis.controls[google.maps.ControlPosition.RIGHT_BOTTOM].push(toiletControl[0]);
    mapdis.controls[google.maps.ControlPosition.RIGHT_BOTTOM].push(changeFolder[0]);
    document.getElementById("displaySuggestedToilets").style.display = "none";
    document.getElementById("addToiletButton").style.display = "none";
    document.getElementById("addPersonalMarkerButton").style.display = "none";
    document.getElementById("changeFolder").style.display = "none";
}
function displayToiletUi() {
    document.getElementById("displaySuggestedToilets").style.display = "block";
}
function hideToiletUi() {
    document.getElementById("displaySuggestedToilets").style.display = "none";
}

//Zooms in Map
function zoomIn() {
    mapdis.setZoom((mapdis.getZoom() + 1));
}
//Zooms Out Map
function zoomOut() {
    mapdis.setZoom((mapdis.getZoom() - 1));
}
//Move Map Position To User
function goToUserLoc() {
    navigator.geolocation.getCurrentPosition(function (position) {
        var poslat = position.coords.latitude;
        var poslng = position.coords.longitude;
        mapdis.setCenter(new google.maps.LatLng(poslat, poslng));
        mapdis.setZoom(14);
    });
}
//-End Of Map Ui-//
function addToiletLoc() {
    navigator.geolocation.getCurrentPosition(function (position) {
        var poslat = position.coords.latitude;
        var poslng = position.coords.longitude;
        document.getElementById("formSubmitToilet:locLng").value = poslng;
        document.getElementById("formSubmitToilet:locLat").value = poslat;
        tsubmit();
    });
}

function displayApproved() {
    var change = document.getElementById("viewPersonalMap");
    if (change.className = "left-controls-selected") {
        change.className = "left-controls-unselected";
        removePMapMarkers();
        document.getElementById("changeFolder").style.display = "none";
        document.getElementById("addPersonalMarkerButton").style.display = "none";

    }

    var check = document.getElementById("viewToilets");
    if (check.className == "left-controls-unselected") {
        check.className = "left-controls-selected";
        document.getElementById("displaySuggestedToilets").style.display = "block";
    }else {
        check.className = "left-controls-unselected";
        removeToiletUi()
    }
    displayApprovedToilet();
}
function displaySuggested() {
    displaySuggestionToilet();
    var check = document.getElementById("displaySuggestedToilets");
    //Change Personal map From Selected To Unselected
    if (check.className == "right-controls-unselected") {
        check.className = "right-controls-selected";
        document.getElementById("addToiletButton").style.display = "block";
        document.getElementById("addToiletButton").style.bottom =  "188px";
    }else {
        check.className = "right-controls-unselected";
        document.getElementById("addToiletButton").style.display = "none";
    }
}
function removeToiletUi() {
    var check = document.getElementById("displaySuggestedToilets");
    if (check.className == "right-controls-selected") {
        check.className = "right-controls-unselected";
        document.getElementById("addToiletButton").style.display = "none";
        document.getElementById("CancelAddToilet").className = "CancelAdd";
        addMarker = 0;
        if (confirmationMarker != null) {
            resetInfoWindow();
        }
        displaySuggestionToilet();
    }

    document.getElementById("displaySuggestedToilets").style.display = "none";
}
