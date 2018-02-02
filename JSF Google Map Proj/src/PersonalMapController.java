import org.primefaces.context.RequestContext;

public class PersonalMapController {
    public void resetMarkerList() {
        RequestContext.getCurrentInstance().execute("clearMarkerList();");
    }

//    public void displayApprovedMarker() {
//        for (PersonalMapMarker m:) {
//            RequestContext.getCurrentInstance().execute(
//                    "var infowindow"+m.getRandomId()+" = new google.maps.InfoWindow({" +
//                            "   content:createApprovedInfoWindow("+m.getRandomId()+","+m.getAvg_rating()+","+m.getGenderM()+","+m.getWheelchair()+","+m.getCost()+","+m.getAmt_of_ratings()+")" +
//                            "});" +
//                            "newMarker"+m.getRandomId()+" = " +
//                            "new google.maps.Marker({ " +
//                            "position:new google.maps.LatLng(" + m.getLatlng().getLat()+ ", " +  m.getLatlng().getLng() + "), " +
//                            "map:PF('mapDisplay').getMap()," +
//                            "icon:'"+m.getImage()+"'});" +
//                            "newMarker"+m.getRandomId()+".addListener('click',function() {" +
//                            "   infowindow"+m.getRandomId()+".open(map,newMarker"+m.getRandomId()+");" +
//                            "});"
//                            + "markers.push(newMarker"+m.getRandomId()+");");
//        }
//    }
}
