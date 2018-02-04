import org.primefaces.model.map.LatLng;

//Used for suggestion markers
public class MarkerRequestData extends MarkerData {
    String comment;


    public MarkerRequestData() {
    }
    //For Suggesting Toilets
    public MarkerRequestData(LatLng latlng, int genderM, int rating) {
        super(latlng, genderM, rating);
        this.image = getImageStrings(genderM);
    }

//    //Database suggestion toilets

    public MarkerRequestData(LatLng latlng, int rating, int amt_of_ratings, int genderM, int toiletId, int toiletInfoId, int wheelchair, double cost) {
        super(latlng, rating, amt_of_ratings, genderM, toiletId, toiletInfoId, wheelchair, cost);
        this.image = getImageStrings(genderM);
    }

    public MarkerRequestData(int rating) {
        this.rating = rating;
    }

    public void setImageString() {
        System.out.println("Hello U getting images?" + this.genderM);
        switch(this.genderM) {
            case 0:
                this.image = "images/toilet_female_request.png";
                break;
            case 1:
                this.image = "images/toilet_male_request.png";
                break;
            case 2:
                this.image = "images/toilet_male_female_request.png";
                break;
        }
    }
    private String getImageStrings(int genderM) {
        switch(genderM) {
            case 0:
                return "images/toilet_female_request.png";
            case 1:
                return "images/toilet_male_request.png";
            case 2:
                return "images/toilet_male_female_request.png";
        }
        return null;
    }

    public String getComment() { return comment; }

    public void setComment(String comment) { this.comment = comment; }
}
