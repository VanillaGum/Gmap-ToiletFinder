

import org.primefaces.context.RequestContext;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.Marker;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ManagedBean
public class MapController implements Serializable{
    private double locLng = 0;
    private double locLat = 0;
    private int toiletGender = 0;
    private int genderM = 0;
    private int genderF = 0;
    private int rating = 0;
    private int upvote = 0;
    private MarkerEntity me;
    private MarkerList ml;
    @PostConstruct
    public void init() {
        ml = MarkerList.getInstance();
        displayApprovedMarker();
   }
    public void setUserLocMark() {

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Added", "Nothing much"));
        Map map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Float lat = (Float) map.get("latval");
        Float lng = (Float) map.get("lngval");
    }
    public void displayApprovedMarker() {
        RequestContext.getCurrentInstance().execute("clearMarkerList();");
        for (MarkerData m: ml.getApprovedMarkers()) {
//            RequestContext.getCurrentInstance().execute("var newMarker = " +
//                    "new google.maps.Marker({ " +
//                    "position:new google.maps.LatLng(" + m.getLatlng().getLat()+ ", " +  m.getLatlng().getLng() + "), " +
//                    "map:PF('mapDisplay').getMap()," +
//                    "icon:'"+m.getImage()+"'});"
//                    + "markers.push(newMarker);");
            RequestContext.getCurrentInstance().execute("markers.push(" +
                    "new google.maps.Marker({ " +
                    "position:new google.maps.LatLng(" + m.getLatlng().getLat()+ ", " +  m.getLatlng().getLng() + "), " +
                    "map:PF('mapDisplay').getMap()," +
                    "icon:'"+m.getImage()+"'}));");
        }
    }
    public void displaySuggestedMarkers() {
        for (MarkerData m:ml.getSuggestedMarkers()) {
                RequestContext.getCurrentInstance().execute(
                                "var infowindow"+m.getRating()+" = new google.maps.InfoWindow({" +
                                "   content:createInfoWindow("+m.getRating()+","+m.getGenderM()+")" +
                                "});" +
                        "var newMarker"+m.getRating()+" = " +
                        "new google.maps.Marker({ " +
                        "position:new google.maps.LatLng(" + m.getLatlng().getLat()+ ", " +  m.getLatlng().getLng() + "), " +
                        "map:PF('mapDisplay').getMap()," +
                        "icon:'"+m.getImage()+"'});" +
                                "newMarker"+m.getRating()+".addListener('click',function() {" +
                                "   infowindow"+m.getRating()+".open(map,newMarker"+m.getRating()+");" +
                                "});"
                        + "markers.push(newMarker"+m.getRating()+");");
        }
    }
    public void displaySuggestionMarkers(List<MarkerData> mList) {
        RequestContext.getCurrentInstance().execute("clearMarkerList();");
        for (MarkerData m:mList) {
            RequestContext.getCurrentInstance().execute("var newMarker = " +
                    "new google.maps.Marker({ " +
                    "position:new google.maps.LatLng(" + m.getLatlng().getLat()+ ", " +  m.getLatlng().getLng() + "), " +
                    "map:PF('mapDisplay').getMap()," +
                    "icon:'"+m.getImage()+"'});"
                    + "markers.push(newMarker);");
        }
    }
    public String getImages(int ImgNo) {
        switch (ImgNo) {
            case 0:
                return "images/toilet_female.png";
            case 1:
                return "images/toilet_male.png";
            case 2:
                return "images/toilet_female.png";
        }
        return "";
    }
    public String getRequestImages(int gender) {
        switch (gender) {
            case 0:
                return "images/toilet_female_request.png";
            case 1:
                return "images/toilet_male_request.png";
            case 2:
        }
        return null;
    }
    public void removeAllMarkers() {

    }
    public void addToiletLoc() {
        System.out.println("Rating:" + rating + "|" +"Gender: M|" +genderM+ " F|" + genderF);
        if (genderM == 1 && genderF == 0) {
            //Add Male Toilet
            MarkerData newMarker = new MarkerData(new LatLng(locLat,locLng),1, rating);
            ml.addSuggestedMarker(newMarker);
            displaySuggestedMarkers();
        }else if (genderM == 0 && genderF == 1){
            //Add Female Toilet
            MarkerData newMarker = new MarkerData(new LatLng(locLat,locLng),0, rating);
            ml.addApprovedMarker(newMarker);
            displayApprovedMarker();
        }else if(genderM == 1 && genderF == 1) {
            //Add male and female toilet
            MarkerData newMarker = new MarkerData(new LatLng(locLat,locLng),2, rating);
            ml.addApprovedMarker(newMarker);
            displayApprovedMarker();
        }

    }
    public void onStateChange(StateChangeEvent event) {

    }
    public void setLocLng(double locLng) {
        this.locLng = locLng;
    }

    public void setLocLat(double locLat) {
        this.locLat = locLat;
    }

    public double getLocLng() {
        return locLng;
    }

    public double getLocLat() {
        return locLat;
    }

    public int getToiletGender() { return toiletGender; }

    public void setToiletGender(int toiletGender) { this.toiletGender = toiletGender; }

    public int getGenderM() { return genderM;}

    public void setGenderM(int genderM) { this.genderM = genderM;}

    public int getGenderF() { return genderF; }

    public void setGenderF(int genderF) { this.genderF = genderF; }

    public int getRating() { return rating; }

    public void setRating(int rating) { this.rating = rating; }

    public int getUpvote() { return upvote; }

    public void setUpvote(int upvote) { this.upvote = upvote; }
}

