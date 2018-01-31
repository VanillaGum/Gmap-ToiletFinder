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
$(function() {
    mapdis = PF('mapDisplay').getMap();
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            //Set Map View User Current Loc
            var poslat = position.coords.latitude;
            var poslng = position.coords.longitude;
            mapdis.setCenter(new google.maps.LatLng(poslat, poslng));
            //Display Marker At User Position
            var marker = new google.maps.Marker({
                position:{lat:poslat,lng:poslng},
                map:mapdis,
                title:"User Loc",
                icon:"images/toilet_male.png"
            });
        });
    }
    drawMapUi();
});
function setMarkerAddType(num) {
    if (num == 1) {
        addToiletLoc();
    }else {
        addMarker = 2;
    }
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
            switch(userLevel) {
                case 0:
                    confirmationMarker.addListener('click', function() {
                        confirmationInfowindow.open(mapdis, confirmationMarker);
                    });
                    confirmationInfowindow.open(mapdis,confirmationMarker);
                    break;
                case 1:
                    confirmationMarker.addListener('click', function() {
                        confirmationInfowindow1.open(mapdis, confirmationMarker);
                    });
                    confirmationInfowindow1.open(mapdis,confirmationMarker);
                    break;
                case 2:
                    confirmationMarker.addListener('click', function() {
                        confirmationInfowindow2.open(mapdis, confirmationMarker);
                    });
                    confirmationInfowindow2.open(mapdis,confirmationMarker);
                    break;
            }
        }
    }
}

//Remove Toilet Suggestion Marker When Confirmed
function toiletSuggestionConfirmed() {
    //Submit Info About Toilet
    document.getElementById("formSubmitToilet:locLng").value = confirmationMarkerLng;
    document.getElementById("formSubmitToilet:locLat").value = confirmationMarkerLat;
    switch(userLevel){
        case 0:
            document.getElementById("formSubmitToilet:toiletGender").value = document.getElementById("toiletGenderSelect0").value;
            //Close The Marker And Infowindow
            break;
        case 1:
            document.getElementById("formSubmitToilet:toiletGender").value = document.getElementById("toiletGenderSelect1").value;
            //Close The Marker And Infowindow
            break;
        case 2:
            document.getElementById("formSubmitToilet:toiletGender").value = document.getElementById("toiletGenderSelect2").value;
            //Close The Marker And Infowindow
            break;
    }
    //Initiate the ManagedBean MapController AddToilet function
    tsubmit();
    resetInfoWindow();
}

//Suggesting Location Cancelled
function cancelLocMarker() {
    confirmationInfowindow.close();
    confirmationMarker.setMap(null);
    confirmationMarker = null;
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
        document.getElementById("femaleIcon0").className = "fToilet femaleToiletSelectIcon-unselected";
        document.getElementById("maleIcon0").className = "mToilet maleToiletSelectIcon-unselected";
        var icon6Reset = document.getElementsByClassName("icon6");
        icon6Reset[0].src = "images/wheelchair-neutral.png";
        icon6Reset[0].className = "icon6 icon6-unselected";
        var icon7Reset = document.getElementsByClassName("icon7");
        icon7Reset[0].className = "icon7 icon7-unselected unselected";
    confirmationInfowindow.close();
    confirmationMarker.setMap(null);
    confirmationMarker = null;
    addMarker=0;

}


//-Start of Map Ui-//
//Draw Map User Interface for non-logged in users
function drawMapUi() {
    var zoomoutControl = $("#zoomoutControl");
    var zoominControl = $("#zoominControl");
    var centerControl = $("#centerLoc");
    var toiletControl = $("#addToilet");
    mapdis.controls[google.maps.ControlPosition.RIGHT_BOTTOM].push(zoomoutControl[0]);
    mapdis.controls[google.maps.ControlPosition.RIGHT_BOTTOM].push(zoominControl[0]);
    mapdis.controls[google.maps.ControlPosition.RIGHT_BOTTOM].push(centerControl[0]);
    mapdis.controls[google.maps.ControlPosition.RIGHT_BOTTOM].push(toiletControl[0]);
}
//Draw Map User Interfaces for logged in users
function drawUserMapUi() {
//             For Logged In Users
    mapdis.controls.clear()
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
        mapdis.setZoom(7);
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
        var markerhaha41412 = new google.maps.Marker({
            position:{lat:poslat,lng:poslng},
            map:mapdis,
            title:"User Loc",
            icon:"images/toilet_male.png"
        });
        markers.add(markerhaha41412);
    });
}
