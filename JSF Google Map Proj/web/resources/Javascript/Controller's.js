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
function createInfoWindow(rating,genderm) {
    var content ='<div id="'+rating+'">' +
        'rating='+rating +
        ' genderm='+ genderm +
        'upvoted=true' +
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