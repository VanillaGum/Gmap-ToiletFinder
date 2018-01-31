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
    public MarkerRequestData(LatLng latlng, int rating,int amt_ratings, int genderM, int toiletId, int toiletInfoId) {
        super(latlng, rating,amt_ratings, genderM, toiletId, toiletInfoId);
        this.image = getImageStrings(genderM);
    }

    public MarkerRequestData(int rating) {
        this.rating = rating;
    }

    private String getImageStrings(int genderM) {
        switch(genderM) {
            case 0:
                return "images/toilet_female.png";
            case 1:
                return "images/toilet_male_request.png";
            case 2:
                return "images/toilet_male_request.png";
        }
        return null;
    }

    public String getComment() { return comment; }

    public void setComment(String comment) { this.comment = comment; }
}
