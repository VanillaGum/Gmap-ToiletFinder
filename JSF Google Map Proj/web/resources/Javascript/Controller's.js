markers = [];
function markerInit() {
//    Method for initialising the markerlist
}
function clearMarkerList() {
    markers.forEach(function(marker){
        marker.setMap(null);
    });
    markers=[];
}