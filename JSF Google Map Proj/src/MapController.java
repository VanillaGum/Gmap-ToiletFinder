

import org.primefaces.context.RequestContext;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.Marker;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.xml.registry.infomodel.User;
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
    private int flag; //Used For Flagging Toilets For Removal Because They Dont Exist Or Are Closed
    //Icon Rating System Variables
    private int icon1; //Toilet Cleaniness
    private int icon2; //Toilet Environment Dirty?
    private int icon3; //Slippery?
    private int icon4; //Insects?
    private int icon5; //Smelly?
    private int icon6; //Wheelchair
    private double icon7; //Cost money
    private int icon8; //Faulty Toilets
    private int uniqueId;
    private String comments;
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
           RequestContext.getCurrentInstance().execute(
            "var infowindow"+m.getRandomId()+" = new google.maps.InfoWindow({" +
                    "   content:createApprovedInfoWindow("+m.getRandomId()+","+m.getAvg_rating()+","+m.getGenderM()+","+m.getWheelchair()+","+m.getCost()+","+m.getAmt_of_ratings()+")" +
                    "});" +
                    "newMarker"+m.getRandomId()+" = " +
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
            System.out.println("Cost" + m.getCost());
            RequestContext.getCurrentInstance().execute(
               "var infowindow"+m.getRandomId()+" = new google.maps.InfoWindow({" +
                    "content:createSuggestionInfoWindow("+m.getRandomId()+","+m.getAvg_rating()+","+m.getGenderM()+","+m.getToiletId()+","+m.getWheelchair()+","+m.getCost()+","+m.getAmt_of_ratings()+")" +
                       "});" +
                       "newMarker"+m.getRandomId()+" = " +
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
    public void displaySingleMarker(MarkerData m) {
        System.out.println("Trying to display Single Marker" + m.getImage());
        RequestContext.getCurrentInstance().execute(
                "var infowindow"+m.getRandomId()+" = new google.maps.InfoWindow({" +
                        "   content:createApprovedInfoWindow("+m.getRandomId()+","+m.getAvg_rating()+","+m.getGenderM()+","+m.getWheelchair()+","+m.getCost()+","+m.getAmt_of_ratings()+")" +
                        "});" +
                        "newMarker"+m.getRandomId()+" = " +
                        "new google.maps.Marker({ " +
                        "position:new google.maps.LatLng(" + m.getLatlng().getLat()+ ", " +  m.getLatlng().getLng() + "), " +
                        "map:PF('mapDisplay').getMap()," +
                        "icon:'"+m.getImage()+"'});" +
                        "newMarker"+m.getRandomId()+".addListener('click',function() {" +
                        "   infowindow"+m.getRandomId()+".open(map,newMarker"+m.getRandomId()+");" +
                        "});"
                        + "markers.push(newMarker"+m.getRandomId()+");");
    }
    public void displayMarkersList(List<MarkerData> markerList) {
        for(MarkerData m:markerList) {
            RequestContext.getCurrentInstance().execute(
                    "var infowindow"+m.getRandomId()+" = new google.maps.InfoWindow({" +
                            "content:createSuggestionInfoWindow("+m.getRandomId()+","+m.getAvg_rating()+","+m.getGenderM()+","+m.getToiletId()+","+m.getWheelchair()+","+m.getCost()+","+m.getAmt_of_ratings()+")" +
                            "});" +
                            "newMarker"+m.getRandomId()+" = " +
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
        UserController uc = UserController.getInstance();
        MarkerRequestData newMarker= new MarkerRequestData();
        newMarker.setRandomId();
        System.out.println("Cost:" + icon7);
        System.out.println("Wheelchair:" + icon6);
        System.out.println("Location:" + locLat + locLng);
        if (genderM == 1 && genderF == 0) {
            //Add Male Toilet
            newMarker.setLatlng(new LatLng(locLat,locLng));
            newMarker.setGenderM(1);
            newMarker.setWheelchair(icon6);
            newMarker.setCost(icon7);
        }else if (genderM == 0 && genderF == 1){
            //Add Female Toilet
            newMarker.setLatlng(new LatLng(locLat,locLng));
            newMarker.setGenderM(0);
            newMarker.setWheelchair(icon6);
            newMarker.setCost(icon7);
        }else if(genderM == 1 && genderF == 1) {
            //Add male and female toilet
            newMarker.setLatlng(new LatLng(locLat,locLng));
            newMarker.setGenderM(2);
            newMarker.setWheelchair(icon6);
            newMarker.setCost(icon7);
        }
        newMarker.setToiletId(me.createSingleMarker(newMarker));

        if (uc.getUserLevel() == 2) {
            newMarker.getImageString();
        }else {
            newMarker.setImageString();
        }

        if (uc.getUserLevel() == 2) {
            ml.addApprovedMarker(newMarker);
        }else {
            ml.addSuggestedMarker(newMarker);
        }
        displaySingleMarker(newMarker);
        genderM=0;
        genderF=0;
        icon7=0;
        icon6=0;
    }

    public void upvoteToilet() {
        if (upvote == 1) {
            //Upvoting Toilet
            me.upvoteToilet(upvoteToiletId);
        }else if (upvote == 0) {
            //Remove Upvote
            me.downvoteToilet(upvoteToiletId);
        }

    }
    public void flagToilet() {
        //Flag Toilet
        int ToiletId = -1;
        for(MarkerData m:ml.getApprovedMarkers()) {
            if(m.getRandomId() == uniqueId) {
                ToiletId = m.getToiletId();
                me.flagApprovedToilet(ToiletId);
            }
        }
        for(MarkerData m:ml.getSuggestionMarkers()) {
            if(m.getRandomId() == uniqueId) {
                ToiletId = m.getToiletId();
                System.out.println("ToiletId:" + ToiletId);
                me.flagSuggestionToilet(ToiletId);
            }
        }
        for(MarkerData m:ml.getSuggestedMarkers()) {
            if(m.getRandomId() == uniqueId) {
                ml.resetSuggestedMarker();
                MarkerRequestData mlMRD = ml.getSuggestedMarker();
                mlMRD.setRating(reviewToiletRating());
            }
        }
    }

    public void reviewToilet() {
        System.out.println("Hi We're Reviewing Suggested Toilet");
        int ratingz = reviewToiletRating();
        int ToiletId = -1;
        for(MarkerData m:ml.getApprovedMarkers()) {
            System.out.println("Hi Approved Marker" + m.getToiletId());
            if(m.getRandomId() == uniqueId) {
                ToiletId = m.getToiletId();
                System.out.println("Approved Toilet");
                me.rateApprovedToilet(ToiletId,ratingz);
                m.addRating(ratingz);
                m.addReviewAmt();
                m.calAvg();
                RequestContext.getCurrentInstance().execute( "refreshInfoWindow("+uniqueId+","+m.getAvg_rating()+", "+m.getAmt_of_ratings()+")");
            }
        }
        for(MarkerData m:ml.getSuggestionMarkers()) {
            System.out.println("Hi Suggestion Marker" + m.getToiletId() +"|" + m.getRandomId());
            if(m.getRandomId() == uniqueId) {
                ToiletId = m.getToiletId();
                System.out.println("Suggestion Toilet");
                me.rateSuggestionToilet(ToiletId,ratingz);
                m.addRating(ratingz);
                m.addReviewAmt();
                m.calAvg();
                RequestContext.getCurrentInstance().execute( "refreshInfoWindow("+uniqueId+","+m.getAvg_rating()+", "+m.getAmt_of_ratings()+")");
            }
        }
    }
    public int reviewToiletRating() {
        double rating = 3;
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
        }else {
            rating+=0.25;
        }
        //Slippery
        if(icon3 == 1) {
            rating-=1;
        }else {
            rating+=0.5;
        }
        //Insects?
        if(icon4 == 1) {
            rating-=1;
        }else {
            rating+=0.25;
        }
        //Smelly?
        if(icon5==1) {
            rating-=1;
        }else {
            rating+=0.5;
        }
        //Faulty
        if(icon8==1) {
            rating-=3;
        }else {
            rating+=0.25;
        }
        //Rating To Be Submitted
        int rating2 = 0;

        //Round To Int
        double ratingDecimal = rating % 1;
        int ratingWhole = (int) (rating - ratingDecimal);
        if(ratingDecimal >= 0.5) {
            rating += 1;
        }
        icon1=0;
        icon2=0;
        icon3=0;
        icon4=0;
        icon5=0;
        icon8=0;
        rating2 += ratingWhole;
        if (rating2 > 5) {
            rating2 = 5;
        }
        if (rating2 <1) {
            rating2 = 1;
        }
        System.out.println("ratings:" + rating2);
        return rating2;
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

    public double getIcon7() { return icon7; }

    public void setIcon7(double icon7) { this.icon7 = icon7; }

    public int getIcon8() { return icon8; }

    public void setIcon8(int icon8) { this.icon8 = icon8; }

    public String getComments() { return comments; }

    public void setComments(String comments) { this.comments = comments; }

    public int getUniqueId() { return uniqueId; }

    public void setUniqueId(int uniqueId) { this.uniqueId = uniqueId; }

    public int getFlag() { return flag; }

    public void setFlag(int flag) { this.flag = flag; }
}

