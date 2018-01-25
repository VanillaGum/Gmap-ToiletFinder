

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
    private int upvoteToiletId = 0;
    private MarkerEntity me;
    private MarkerList ml;

    @PostConstruct
    public void init() {
        me = new MarkerEntity();
        ml = MarkerList.getInstance();
        displayApprovedMarker();
        displaySuggestionMarkers();
   }
    public void setUserLocMark() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Added", "Nothing much"));
        Map map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Float lat = (Float) map.get("latval");
        Float lng = (Float) map.get("lngval");
    }
    public void resetMarkerList() {
            RequestContext.getCurrentInstance().execute("clearMarkerList();");
    }
    public void displayApprovedMarker() {
        for (MarkerData m: ml.getApprovedMarkers()) {
            RequestContext.getCurrentInstance().execute("var newMarker = " +
                    "new google.maps.Marker({ " +
                    "position:new google.maps.LatLng(" + m.getLatlng().getLat()+ ", " +  m.getLatlng().getLng() + "), " +
                    "map:PF('mapDisplay').getMap()," +
                    "icon:'"+m.getImage()+"'});"
                    + "markers.push(newMarker);");

//            RequestContext.getCurrentInstance().execute("markers.push(" +
//                    "new google.maps.Marker({ " +
//                    "position:new google.maps.LatLng(" + m.getLatlng().getLat()+ ", " +  m.getLatlng().getLng() + "), " +
//                    "map:PF('mapDisplay').getMap()," +
//                    "icon:'"+m.getImage()+"'}));");
        }
    }
    public void displaySuggestedMarkers() {
        for (MarkerData m:ml.getSuggestedMarkers()) {
                RequestContext.getCurrentInstance().execute(
                                "var infowindow"+m.getRandomId()+" = new google.maps.InfoWindow({" +
                                "   content:createSuggestedInfoWindow("+m.getRandomId()+","+m.getRating()+","+m.getGenderM()+")" +
                                "});" +
                        "var newMarker"+m.getRandomId()+" = " +
                        "new google.maps.Marker({ " +
                        "position:new google.maps.LatLng(" + m.getLatlng().getLat()+ ", " +  m.getLatlng().getLng() + "), " +
                        "map:PF('mapDisplay').getMap()," +
                        "icon:'"+m.getImage()+"'});" +
                                "newMarker"+m.getRandomId()+".addListener('click',function() {" +
                                "   infowindow"+m.getRandomId()+".open(map,newMarker"+m.getRandomId()+");" +
                                "});"
                        + "markers.push(newMarker"+m.getRandomId()+");");
        }
    }
    public void displaySuggestionMarkers() {
        for (MarkerData m:ml.getSuggestionMarkers()) {
            System.out.println(m.getGenderM());
            RequestContext.getCurrentInstance().execute(
               "var infowindow"+m.getRandomId()+" = new google.maps.InfoWindow({" +
                    "   content:createSuggestionInfoWindow("+m.getRandomId()+","+m.getRating()+","+m.getGenderM()+","+m.getToiletId()+")" +
                    "});" +
                       "var newMarker"+m.getRandomId()+" = " +
                       "new google.maps.Marker({ " +
                       "position:new google.maps.LatLng(" + m.getLatlng().getLat()+ ", " +  m.getLatlng().getLng() + "), " +
                       "map:PF('mapDisplay').getMap()," +
                       "icon:'"+m.getImage()+"'});" +
                       "newMarker"+m.getRandomId()+".addListener('click',function() {" +
                       "   infowindow"+m.getRandomId()+".open(map,newMarker"+m.getRandomId()+");" +
                       "});"
                       + "markers.push(newMarker"+m.getRandomId()+");");
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
        MarkerRequestData newMarker= null;
        System.out.println("Rating:" + rating + "|" +"Gender: M|" +genderM+ " F|" + genderF);
        if (genderM == 1 && genderF == 0) {
            //Add Male Toilet
            newMarker = new MarkerRequestData(new LatLng(locLat,locLng),1, rating);
            me.createSingleMarker(newMarker);
        }else if (genderM == 0 && genderF == 1){
            //Add Female Toilet
            newMarker = new MarkerRequestData(new LatLng(locLat,locLng),0, rating);
            me.createSingleMarker(newMarker);
        }else if(genderM == 1 && genderF == 1) {
            //Add male and female toilet
            newMarker = new MarkerRequestData(new LatLng(locLat,locLng),2, rating);
        }
        ml.addSuggestedMarker(newMarker);
        displaySuggestedMarkers();
    }

    public void upvoteToilet() {
        if (upvote == 1) {
            //Upvoting Toilet
            System.out.println(upvoteToiletId);
            me.upvoteToilet(upvoteToiletId);
        }else if (upvote == 0) {
            //Remove Upvote
            me.downvoteToilet(upvoteToiletId);
        }
    }
    public void addRating() {

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

    public int getUpvoteToiletId() { return upvoteToiletId; }

    public void setUpvoteToiletId(int upvoteToiletId) { this.upvoteToiletId = upvoteToiletId; }
}

