import org.primefaces.model.map.LatLng;

import java.util.UUID;

public class MarkerData {
    protected LatLng latlng;

    protected int iconNo;

    protected int rating = -1;

    protected int amt_of_ratings;

    protected int avg_rating = 0;

    protected String title;

    protected int genderM;
    //0 = Female
    //1 = Male
    //2 = Both

    protected int toiletId;

    protected int toiletInfoId;

    protected String image;

    protected int randomId;

    protected int wheelchair;

    protected double cost;

    public MarkerData() {
    }

//    public MarkerData(LatLng latlng, int rating, int amt_of_ratings, String title, int genderM) {
//        this.latlng = latlng;
//        this.rating = rating;
//        this.amt_of_ratings = amt_of_ratings;
//        this.title = title;
//        this.genderM = genderM;
//        avg_rating = this.rating/this.amt_of_ratings;
//        this.iconNo = iconNoInit();
//    }

    //Database suggestiontoilets
    public MarkerData(LatLng latlng, int rating, int amt_of_ratings, int genderM, int toiletId, int toiletInfoId , int wheelchair, double cost) {
        this.latlng = latlng;
        this.rating = rating;
        this.amt_of_ratings = amt_of_ratings;
        this.genderM = genderM;
        this.toiletId = toiletId;
        this.toiletInfoId = toiletInfoId;
        this.wheelchair = wheelchair;
        this.cost = cost;
        MarkerList ml = MarkerList.getInstance();
        this.randomId = ml.getUniqueId();
        this.avg_rating = getAvgRating();
    }

    //For Suggesting Toilet Loc
    public MarkerData(LatLng latlng, int genderM, int rating) {
        this.latlng = latlng;
        this.rating = rating;
        this.genderM = genderM;
        MarkerList m = MarkerList.getInstance();
        this.randomId = m.getUniqueId();
    }

    public MarkerData(LatLng latlng, int rating, int genderM, String image, int randomId) {
        this.latlng = latlng;
        this.rating = rating;
        this.genderM = genderM;
        this.image = image;
        MarkerList m = MarkerList.getInstance();
        this.randomId = m.getUniqueId();
    }
    //End of Suggesting Toilet Loc Constructors

    private int iconNoInit() {
        if (avg_rating != -1) {
            if (avg_rating >= 4.8) {
                return 5;
            } else if (avg_rating >= 4) {
                return 4;
            } else if (avg_rating >= 3) {
                return 3;
            } else if (avg_rating >= 2) {
                return 2;
            } else if (avg_rating >= 1) {
                return 1;
            } else {
                return 0;
            }
        }else {
            //Mean No Ratings Yet
            return -1;
        }
    }

    private int getAvgRating() {
        try {
            return (rating/amt_of_ratings);
        }catch (ArithmeticException e) {
            //Will Run If Amt Of Rating = 0
            //Because cannot divide any number by 0
            return -1;
        }
    }

    //Set Image
    private String getImageString() {
        switch(this.getGenderM()) {
            case 0:
                //Female Gender
                return "images/toilet_female.png";
            case 1:
                //Male Gender
                return "images/toilet_male.png";
            case 2:
                //Both Genders
                return "images/toilet_female.png";
        }
        return null;
    }

    public LatLng getLatlng() {
        return latlng;
    }

    public void setLatlng(LatLng latlng) {
        this.latlng = latlng;
    }

    public int getIconNo() {
        return iconNo;
    }

    public void setIconNo(int iconNo) {
        this.iconNo = iconNo;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getAmt_of_ratings() {
        return amt_of_ratings;
    }

    public void setAmt_of_ratings(int amt_of_ratings) {
        this.amt_of_ratings = amt_of_ratings;
    }

    public int getAvg_rating() {
        return avg_rating;
    }

    public void setAvg_rating(int avg_rating) {
        this.avg_rating = avg_rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getGenderM() {
        return genderM;
    }

    public void setGenderM(int genderM) {
        this.genderM = genderM;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getRandomId() {
        return randomId;
    }

    public void setRandomId(int randomId) {
        this.randomId = randomId;
    }

    public int getToiletId() {
        return toiletId;
    }

    public void setToiletId(int toiletId) {
        this.toiletId = toiletId;
    }

    public int getToiletInfoId() {
        return toiletInfoId;
    }

    public void setToiletInfoId(int toiletInfoId) {
        this.toiletInfoId = toiletInfoId;
    }

    public int getWheelchair() { return wheelchair; }

    public void setWheelchair(int wheelchair) { this.wheelchair = wheelchair; }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
