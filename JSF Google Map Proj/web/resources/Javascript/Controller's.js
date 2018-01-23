markers = [];
requestMarkers = [];
function markerInit() {
//    Method for initialising the markerlist
}
function clearMarkerList() {
    // alert("Clearing Markers");
    markers.forEach(function(marker){
        marker.setMap(null);
    });
    markers=[];
}
function createSuggestionInfoWindow(id,rating,genderM) {
    var mClass = "";
    var fClass = "";
    if(genderM == 2) {
        mClass = "maleToiletSelectIcon-selected";
        fClass = "femaleToiletSelectIcon-selected";
    }else if(genderM== 1){
        mClass = "maleToiletSelectIcon-selected";
        fClass = "femaleToiletSelectIcon-unselected";
    }else {
        mClass = "maleToiletSelection-unselected";
        fClass = "femaleToiletSelectIcon-selected";
    }
    var content ='<div id="'+id+'">' +
        'rating='+rating +
        ' genderm='+ genderM +
        'upvoted=true' +
        '<img id="maleIcon0" class="'+mClass+'" src="images/male_icon.png" alt="maleToiletIcon" width="23.2" height="62.6px"/>' +
        '<img id="femaleIcon0" class="'+fClass+'" src="images/female_icon.png" alt="femaleToiletIcon" width="31px" height="62.6"/>' +
        '</div>';
    return content;
}
function addNewMarker(lat,lng,image) {
    newMarker = null;
    newMarker = new google.maps.Marker({
        position:{lat:lat,lng:lng},
        map:mapdis,
        title:"User Loc",
        icon:image
    });
    markers.push(newMarker);

    markers.push(new google.maps.Marker({
        
    }))
    var window = '<div>' +
        '   <p>Select:</p>' +
        '</div>'
}