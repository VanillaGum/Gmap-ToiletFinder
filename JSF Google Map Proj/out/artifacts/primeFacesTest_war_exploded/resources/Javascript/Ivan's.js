mapdis = null;
addMarker = 0;
confirmationMarker = null;
confirmationMarkerLat = 0;
confirmationMarkerLng = 0;
confirmationInfowindow = null;
var contentItem = '<div>' +
    '         <button onclick="removeLocMarker()">Confirm Location</button>' +
    '      </div>'
confirmationInfowindow = new google.maps.InfoWindow({
    content: contentItem
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
                title:"User Loc"
            });
            alert(mapdis);
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
            confirmationMarker.addListener('click', function() {
                confirmationInfowindow.open(mapdis, confirmationMarker);
            });
            confirmationInfowindow.open(mapdis,confirmationMarker)
        }
    }
}
function removeLocMarker() {
    confirmationInfowindow.close();
    confirmationMarker.setMap(null);
    confirmationMarker = null;
    alert(mapdis);
    new google.maps.Marker( {
        position: {lat:confirmationMarkerLat,lng:confirmationMarkerLng},
        map:mapdis,
        icon:"images/toilet_female.png"
    });
    //alert("Latitude:" + confirmationMarkerLat + "\n Longitude:" + confirmationMarkerLng);
    document.getElementById("formSubmitToilet:locLng").value = confirmationMarkerLng;
    document.getElementById("formSubmitToilet:locLat").value = confirmationMarkerLat;
    tsubmit();
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
    });
}
//-End Of Map Ui-//
function addToiletLoc() {
    navigator.geolocation.getCurrentPosition(function (position) {
        var poslat = position.coords.latitude;
        var poslng = position.coords.longitude;
        alert("Latitude:" + poslat + "\n Longitude:" + poslng);
        document.getElementById("formSubmitToilet:locLng").value = poslng;
        document.getElementById("formSubmitToilet:locLat").value = poslat;
        tsubmit();
    });
}