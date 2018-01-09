import org.primefaces.model.map.LatLng;

public class MarkerData {
    private LatLng latlng;

    private int iconNo;

    private int rating = -1;

    private int amt_of_ratings;

    private double avg_rating = -1;

    private String title;

    private boolean genderM;

    public MarkerData(LatLng latlng, int rating, int amt_of_ratings, String title, Boolean genderM) {
        this.latlng = latlng;
        this.rating = rating;
        this.amt_of_ratings = amt_of_ratings;
        this.title = title;
        this.genderM = genderM;
        avg_rating = this.rating/this.amt_of_ratings;
        this.iconNo = iconNoInit();
    }
    //Used For Constructing a Object For Suggesting Toilet Loc
    public MarkerData(LatLng latlng, Boolean genderM) {
        this.latlng = latlng;
        this.genderM = genderM;
    }
    public MarkerData(LatLng latlng, int rating, boolean genderM) {
        this.latlng = latlng;
        this.rating = rating;
        this.genderM = genderM;
    }
    //End of Suggesting Toilet Loc Constructors

    public int iconNoInit() {
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

    public double getAvg_rating() {
        return avg_rating;
    }

    public void setAvg_rating(double avg_rating) {
        this.avg_rating = avg_rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isGenderM() {
        return genderM;
    }

    public void setGenderM(boolean genderM) {
        this.genderM = genderM;
    }
}
