import org.primefaces.model.map.LatLng;

public class MarkerData {
    private LatLng latlng;

    private int iconNo;

    private int rating = -1;

    private int amt_of_ratings;

    private double avg_rating = 0;

    private String title;

    private int genderM;

    private int toiletId;

    private int toiletInfoId;

    private String image;
    public MarkerData() {
    }

    //For Database Usage
    public MarkerData(LatLng latlng, int rating, int amt_of_ratings, String title, int genderM, int toiletId, int toiletInfoId) {
        this.latlng = latlng;
        this.rating = rating;
        this.amt_of_ratings = amt_of_ratings;
        this.title = title;
        this.genderM = genderM;
        this.toiletId = toiletId;
        this.toiletInfoId = toiletInfoId;
        avg_rating = getAvgRating();
        this.iconNo = iconNoInit();
        this.image = getImageString();
    }

    public MarkerData(LatLng latlng, int rating, int amt_of_ratings, String title, int genderM) {
        this.latlng = latlng;
        this.rating = rating;
        this.amt_of_ratings = amt_of_ratings;
        this.title = title;
        this.genderM = genderM;
        avg_rating = this.rating/this.amt_of_ratings;
        this.iconNo = iconNoInit();
    }

    public MarkerData(LatLng latlng, String title, int genderM, int toiletId, int toiletInfoId) {
        this.latlng = latlng;
        this.title = title;
        this.genderM = genderM;
        this.toiletId = toiletId;
        this.toiletInfoId = toiletInfoId;
    }

    //For Suggesting Toilet Loc
    public MarkerData(LatLng latlng, int genderM, int rating) {
        this.latlng = latlng;
        this.rating = rating;
        this.genderM = genderM;
        this.iconNo = -1;
        this.image = getImageString();
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
                switch(this.iconNo) {
                    case -1:
                        return "images/toilet_female.png";
                    case 0:

                    case 1:

                    case 2:

                    case 3:

                    case 4:

                    case 5:
                }
                break;
            case 1:
                //Male Gender
                switch(this.iconNo) {
                    case -1:
                        return "images/toilet_male.png";
                    case 0:

                    case 1:

                    case 2:

                    case 3:

                    case 4:

                    case 5:
                }
                break;
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
}
