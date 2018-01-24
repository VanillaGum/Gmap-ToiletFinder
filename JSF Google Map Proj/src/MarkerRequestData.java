import org.primefaces.model.map.LatLng;

//Used for suggestion markers
public class MarkerRequestData extends MarkerData {
    //For Suggesting Toilets
    public MarkerRequestData(LatLng latlng, int genderM, int rating) {
        super(latlng, genderM, rating);
        this.image = getImageStrings(genderM);
    }

//    //Database suggestion toilets
    public MarkerRequestData(LatLng latlng, int rating, int genderM, int toiletId, int toiletInfoId) {
        super(latlng, rating, genderM, toiletId, toiletInfoId);
        MarkerList ml = MarkerList.getInstance();
        this.randomId = ml.getUniqueId();
        this.image = getImageStrings(genderM);
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

}
