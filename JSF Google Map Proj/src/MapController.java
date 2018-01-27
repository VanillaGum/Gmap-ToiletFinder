

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
    private int upvote = 0;
    private int upvoteToiletId = 0;
    private MarkerEntity me;
    private MarkerList ml;

    //Icon Rating System Variables
    private int icon1; //Toilet Cleaniness
    private int icon2; //Toilet Environment Dirty?
    private int icon3; //Slippery?
    private int icon4; //Insects?
    private int icon5; //Smelly?
    private int icon6; //Wheelchair
    private int icon7; //Cost money
    private int icon8; //Faulty Toilets
    //End

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
                    "content:createSuggestionInfoWindow("+m.getRandomId()+","+m.getRating()+","+m.getGenderM()+","+m.getToiletId()+")" +
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
        int rating = reviewToilet();
        System.out.println("|" +"Gender: M|" +genderM+ " F|" + genderF);
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
    public int reviewToilet() {
        int rating = 3;
        //Rating System
        //Out Of 5
        //Cleaniness
        if(icon1 == 2) {
            rating+=3;
        }else if (icon1 == 1) {
            rating+=2;
        }
        //Dirty Environment
        if(icon2 == 1) {
            rating-=1;
        }
        //Slippery
        if(icon3 == 1) {
            rating-=1;
        }
        //Insects?
        if(icon4 == 1) {
            rating-=1;
        }
        //Smelly?
        if(icon5==1) {
            rating-=1;
        }
        if(icon8==1) {
            rating-=3;
        }
        return rating;
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

    public int getUpvote() { return upvote; }

    public void setUpvote(int upvote) { this.upvote = upvote; }

    public int getUpvoteToiletId() { return upvoteToiletId; }

    public void setUpvoteToiletId(int upvoteToiletId) { this.upvoteToiletId = upvoteToiletId; }

    public int getIcon1() { return icon1; }

    public void setIcon1(int icon1) { this.icon1 = icon1; }

    public int getIcon2() { return icon2; }

    public void setIcon2(int icon2) { this.icon2 = icon2; }

    public int getIcon3() { return icon3; }

    public void setIcon3(int icon3) { this.icon3 = icon3; }

    public int getIcon4() { return icon4; }

    public void setIcon4(int icon4) { this.icon4 = icon4; }

    public int getIcon5() { return icon5; }

    public void setIcon5(int icon5) { this.icon5 = icon5; }

    public int getIcon6() { return icon6; }

    public void setIcon6(int icon6) { this.icon6 = icon6; }
}

